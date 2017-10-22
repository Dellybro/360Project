/*
  Date: October 22nd
  Creator: Travis Delly
*/

import java.io.*;
import java.util.*;
import java.text.*;


public class DatabaseHandler{


	public static PrintStream stdout = System.out; //Get what system out is at runtime.

  public static void getRow(String name){

  }
  public static boolean insertRow(FileAnalyzer fileToWrite){

    try {
      System.out.println("Writing file to database.txt");

      PrintStream o = new PrintStream(new BufferedOutputStream(new FileOutputStream("database.txt")));
      System.setOut(o);

      System.out.format("%s, %d, %d, %d, %d", fileToWrite.getName, fileToWrite.getLines(), fileToWrite.getBlankLines(), fileToWrite.getSpaces(), fileToWrite.getWords());
      /* Insert row */

      o.close();
  		System.setOut(stdout);

      return true;
  	} catch (IOException e) {
      System.out.println("Error occured writing file, " + e);
      /* Create file try again */
      return false;
  	}
  }
  public static void getAllRows() {

  }
}
