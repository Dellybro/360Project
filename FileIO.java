import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class FileIO {
	
	private String f;
	
	public FileIO(String name){
		f = name;
	}
	
	public FileAnalyzer readFile() {
		PriorityQueue<WordTotal> commonWords = new PriorityQueue<WordTotal>(); //Used to keep track of the most used words
		int numOfLines = 0; //Used to keep track of the number of lines
		int numOfBlankLines = 0;//Used to keep track of the number of blank lines
		int numOfSpaces = 0;//Used to keep track of the number of spaces
		int numOfWords = 0;//Used to keep track of the number of words
		int averageCharsPerLine = 0; //Used to keep a char count
		int averageWordLength = 0; //Used to keep a count of the word lengths
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(f)));//Used to read the file
			String line;//Current line in the file
			HashMap<String, Integer> wordCount = new HashMap<String, Integer>();//Keeps track of the word with its' count
			while((line = bf.readLine()) != null){//Reads in the current line
				if(line.length() != 0){//May need additional checks for new line
					averageCharsPerLine += line.length();//Adding the char count
					int leftP = -1;
					for(int i = 0; i < line.length(); i++){
						if(line.charAt(i) == ' '){//Check if the current char is a space
							numOfSpaces++;//Increment the aount of spaces
						}
						if(leftP == -1 && Character.isLetter(line.charAt(i))){//Start the current word
							leftP = i;
						}else if((!Character.isLetter(line.charAt(i))|| i == line.length()-1) && leftP != -1){//reached end of word, add to HashMap
							String toAdd;
							if(i == line.length()-1 && Character.isLetter(line.charAt(i))){
								toAdd = line.substring(leftP, i+1).toLowerCase(); //Case that the end of line ends the word
							}else{
								toAdd = line.substring(leftP, i).toLowerCase(); //Case that space ends the word
							}
							numOfWords++;//Keeps track of how many words in the text file
							averageWordLength += toAdd.length(); //Adding the word length
							if(wordCount.containsKey(toAdd)){//Check if the word has been seen before
								wordCount.put(toAdd, wordCount.get(toAdd) + 1);//Increment the word's count
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
			for(String s: wordCount.keySet()){//Move the words from a map to a priority queue
				WordTotal w = new WordTotal(s, wordCount.get(s));//Class which contains a word and its' count
				commonWords.offer(w);//Adding the word and count to the priority Queue
			}
		} catch (FileNotFoundException e) {
			System.out.println("File was invalid/not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Returning a fileanalyzer
		return new FileAnalyzer(numOfLines, numOfBlankLines, numOfSpaces, numOfWords, averageCharsPerLine, averageWordLength, commonWords, f);
	}
	
	
}

