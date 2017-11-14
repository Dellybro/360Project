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

public class FileIO {

	public static FileAnalyzer readFile(String name, String path) {
		if(name == null) return null;
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
			//FileAnalyzer stats = new FileAnalyzer();
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
			for(String s: wordCount.keySet()){
				WordTotal w = new WordTotal(s, wordCount.get(s));
				commonWords.offer(w);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File was invalid/not found");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return new FileAnalyzer(numOfLines, numOfBlankLines, numOfSpaces, numOfWords, averageCharsPerLine, averageWordLength, commonWords, name, path);
	}

	public static FileAnalyzer removePunctuation(String name, String path){
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
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException ex){
			return null;
		}
		FileAnalyzer fileToReturn = readFile(newFile, path);
		/* Clean up */
		File file = new File(newFile);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}

		return fileToReturn;
	}

}
