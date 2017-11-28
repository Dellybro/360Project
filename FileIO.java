import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;

//Used to get the statistics from files
public class FileIO {

	//Reads a file and return the statistics in a fileanalyzer
	public static FileAnalyzer readFile(String name, String path) {
		FileAnalyzer rF = null; 
		if(name != null){
			PriorityQueue<WordTotal> commonWords = new PriorityQueue<WordTotal>();
			int numOfLines = 0; //Used to keep track of the number of lines
			int numOfBlankLines = 0;//Used to keep track of the number of blank lines
			int numOfSpaces = 0;//Used to keep track of the number of spaces
			int numOfWords = 0;//Used to keep track of the number of words
			int averageCharsPerLine = 0; //Used to keep a char count
			int averageWordLength = 0; //Used to keep a count of the word lengths
			try {
				BufferedReader bf = new BufferedReader(new FileReader(new File(name)));
				String line;
				HashMap<String, Integer> wordCount = new HashMap<String, Integer>();//add words
				while((line = bf.readLine()) != null){
					if(line.length() != 0){//May need additional checks for new line
						averageCharsPerLine += line.length();//Adding the char count
						int leftP = -1;
						for(int i = 0; i < line.length(); i++){
							if(line.charAt(i) == ' '){
								numOfSpaces++;
							}
							if(leftP == -1 && Character.isLetter(line.charAt(i))){
								leftP = i;
							}else if(line.charAt(i) != 39 && (!Character.isLetter(line.charAt(i))|| i == line.length()-1) && leftP != -1){//reached end of word, add to HashMap
								String toAdd;
								if(i == line.length()-1 && Character.isLetter(line.charAt(i))){
									toAdd = line.substring(leftP, i+1).toLowerCase(); //Case that word ends the line
								}else{
									toAdd = line.substring(leftP, i).toLowerCase(); //Case that space ends the line
								}
								numOfWords++;//Keeps track of how many words in the text file
								averageWordLength += toAdd.length(); //Adding the word length
								if(wordCount.containsKey(toAdd)){
									wordCount.put(toAdd, wordCount.get(toAdd) + 1);
								}else{
									wordCount.put(toAdd, 1);//Word only appears once
								}
								leftP = -1;
							}
						}
					}else{
						numOfBlankLines++;//Incrementing the amount of blank lines
					}
					numOfLines++;//Incrementing the number of lines
				}
				bf.close();//Closing the buffered reader
				if(numOfLines != 0){//Divide by 0 check
					averageCharsPerLine /= numOfLines; //Averaging the chars per line
				}
				if(numOfWords != 0){//Divide by 0 check
					averageWordLength /= numOfWords; //Averaging the word length
				}
				for(String s: wordCount.keySet()){//Adding words to a priority queue
					WordTotal w = new WordTotal(s, wordCount.get(s));//Adding word and count
					commonWords.offer(w);
				}
				rF = new FileAnalyzer(numOfLines, numOfBlankLines, numOfSpaces, numOfWords, averageCharsPerLine, averageWordLength, commonWords, name, path);
			} catch (FileNotFoundException e) {
				rF = null;
			} catch (IOException e) {
				rF = null;
			}
		}
		
		return rF;
	}

	//Removes the punctuation from the file
	//Then calls readFile to get statistics
	public static FileAnalyzer removePunctuation(String name, String path){
		FileAnalyzer fileToReturn = null;
		BufferedReader bf;
		PrintWriter pw;
		String newFile = "removedPunctuation.txt";
		try {
			bf = new BufferedReader(new FileReader(new File(name)));
			pw = new PrintWriter(newFile);
			String line;
			while((line = bf.readLine()) != null){
				for(int i = 0; i < line.length(); i++){
					char c = line.charAt(i);
				    if(c != '!' && c != '.' && c != '?'  && c != ';' && c != ':' && c != ',' && c != 39){//39 is a single quote
						pw.print(c);
				    }
				}
				pw.println();
			}
			pw.close();
			bf.close();
			fileToReturn = readFile(newFile, path);
		} catch (FileNotFoundException ex) {
			fileToReturn = null;
		} catch (IOException ex){
			fileToReturn = null;
		}
		/* Clean up */
		File toRemove = new File(newFile);
		toRemove.delete();
		return fileToReturn;
	}

}
