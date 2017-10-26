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

  public static String filename = "database.txt";
  public static String tempFilename = "databaseTemp.txt";

  public static boolean insertRow(FileAnalyzer fileToWrite){

    try {
      FileWriter fw = new FileWriter(filename, true);
      /* Insert row */
      String uniqueIdentifier = UUID.randomUUID().toString();
      //id, name, lines, blanklines, spaces, words
      String toInsert = String.format("%s,%s,%d,%d,%d,%d%n", uniqueIdentifier, fileToWrite.getName(), fileToWrite.getLines(), fileToWrite.getBlankLines(), fileToWrite.getSpaces(), fileToWrite.getWords());
      fw.write(toInsert);
      fw.close();
      return true;
  	} catch (IOException e) {
      System.out.println("Error occured writing file, " + e);
      /* Create file try again */
      return false;
  	}
  }

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

  public static FileAnalyzer getRow(String uuid){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null){
        String[] arrayOfStrings = line.split(",");
        if(arrayOfStrings[0].equals(uuid)){
          FileAnalyzer file = new FileAnalyzer(arrayOfStrings);
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


  public static ArrayList<FileAnalyzer> getAllRows() {
    ArrayList<FileAnalyzer> files = new ArrayList<FileAnalyzer>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null){
        /**/
        String[] arrayOfStrings = line.split(",");
        FileAnalyzer currentFile = new FileAnalyzer(arrayOfStrings);
        files.add(currentFile);
      }

      return files;
    } catch (IOException e) {
      System.out.println("Error finding files, " + e);
      /* Create file try again */
      return null;
    }
  }
}
