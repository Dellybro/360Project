/*
  Date: October 22nd
  Creator: Travis Delly
*/

import java.io.*;
import java.util.*;
import java.text.*;

/* How to use FileAnalyzer */
//  FileAnalyzer file = new FileAnalyzer("filename-3");
//  file.set..set..set..set..
//  DatabaseHandler.insertRow(file);
//  FileAnalyzer foundFile = DatabaseHandler.getRow(file.uuid);
//  DatabaseHandler.removeRow(foundFile);
//  ArrayList<FileAnalyzer> files = DatabaseHandler.getAllRows();

public class DatabaseHandler{

  public static String filename = "file-analyzers.txt";
  public static String wordTotalFileName = "word-totals.txt";
  public static String tempFilename = "databaseTemp.txt";

  /* This function will add FileAnalyzer to the database.txt */
  public static boolean insertRow(FileAnalyzer fileToWrite){

    try {
      FileWriter fw = new FileWriter(filename, true);
      /* Insert row */
      String uniqueIdentifier = UUID.randomUUID().toString();

      String createdAtDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());

      //id, name, lines, blanklines, spaces, words, avgwords, avgCharsPerLine
      String toInsert = String.format("%s,%s,%d,%d,%d,%d,%d,%d,%s,%s%n",
        uniqueIdentifier,
        fileToWrite.getName(),
        fileToWrite.getLines(),
        fileToWrite.getBlankLines(),
        fileToWrite.getSpaces(),
        fileToWrite.getWords(),
        fileToWrite.getAvgWordLength(),
        fileToWrite.getAvgCharsPerLine(),
        fileToWrite.getFilePath(),
        createdAtDate
      );

      fw.write(toInsert);
      fw.close();


      fw = new FileWriter(wordTotalFileName, true);
      int index = 0;
      for (WordTotal e : fileToWrite.getMostCommonWords()) {
        toInsert = String.format("%s,%d,%s,%d%n",
          uniqueIdentifier,
          e.getCount(),
          e.getWord(),
          index
        );
        fw.write(toInsert);

        index++;
        // do something with e
      }
      fw.close();
      return true;
  	} catch (IOException e) {
      System.out.println("Error occured writing file, " + e);
      /* Create file try again */
      return false;
  	}
  }

  /* This function will remove a FileAnalyzer from database.txt */
  public static boolean removeRow(FileAnalyzer fileToDelete){

    try {
      File inputFile = new File(filename);
      File tempFile = new File(tempFilename);

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      String currentLine;

      while((currentLine = reader.readLine()) != null) {
        String[] arrayOfStrings = currentLine.split(",");
        if(arrayOfStrings[0].equals(fileToDelete.getUUID())){
          continue;
        }

        String trimmedLine = currentLine.trim();
        writer.write(currentLine + System.getProperty("line.separator"));
      }

      writer.close();
      reader.close();
      return tempFile.renameTo(inputFile);
    }catch(IOException e){
      System.out.println("Error occured writing file, " + e);
      /* Create file try again */
      return false;
    }
  }

  /* This function queries a row based on uuid from database.txt */
  public static FileAnalyzer getRow(String uuid){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null){
        String[] arrayOfStrings = line.split(",");
        if(arrayOfStrings[0].equals(uuid)){
          FileAnalyzer file = new FileAnalyzer(arrayOfStrings);
          file.setMostCommonWords(DatabaseHandler.getWordTotals(file));
          // loopThroughAndPrintMostCommonWords(file);
          return file;
        }
      }

      return null;
    } catch (IOException e) {
      System.out.println("Error finding file, " + e);
      /* Create file try again */
      return null;
    }
  }

  /* This function all rows based on uuid from database.txt */
  public static ArrayList<FileAnalyzer> getAllRows() {
    ArrayList<FileAnalyzer> files = new ArrayList<FileAnalyzer>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null){
        /**/
        String[] arrayOfStrings = line.split(",");
        FileAnalyzer currentFile = new FileAnalyzer(arrayOfStrings);
        currentFile.setMostCommonWords(DatabaseHandler.getWordTotals(currentFile));
        // loopThroughAndPrintMostCommonWords(currentFile);
        files.add(currentFile);
      }

      return files;
    } catch (IOException e) {
      System.out.println("Error finding files, " + e);
      /* Create file try again */
      return null;
    }
  }

  public static PriorityQueue<WordTotal> getWordTotals(FileAnalyzer file){
    try {
      PriorityQueue<WordTotal> wordTotals = new PriorityQueue<WordTotal>();



      BufferedReader reader = new BufferedReader(new FileReader(wordTotalFileName));
      String line;
      while ((line = reader.readLine()) != null){
        String[] arrayOfStrings = line.split(",");
        if(arrayOfStrings[0].equals(file.getUUID())){
          WordTotal total = new WordTotal(arrayOfStrings[2], Integer.parseInt(arrayOfStrings[1]));
          wordTotals.offer(total);
        }
      }

      return wordTotals;
    } catch (IOException e) {
      System.out.println("Error finding file, " + e);
      /* Create file try again */
      return null;
    }
  }

  /* For resting purposes */
  // public static void loopThroughAndPrintMostCommonWords(FileAnalyzer file){
  //
  //   System.out.println("Printing most common words ---- ");
  //   for (WordTotal e : file.getMostCommonWords()) {
  //
  //     System.out.println(e.getWord());
  //     System.out.println(e.getCount());
  //     // do something with e
  //   }
  //   System.out.println("End printing most common words ---- ");
  // }

}
