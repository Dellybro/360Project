/*
  Date: October 22nd
  Creator: Travis Delly
*/

import java.io.*;
import java.util.*;

public class Main {

   public static Scanner input = new Scanner(System.in);

   public static void main(String[] argv){
     FileAnalyzer file = new FileAnalyzer();
     DatabaseHandler.insertRow(file);
   }

   public static void showMenu(){
      /* Add gui stuff here*/
   }
}
