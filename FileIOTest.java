import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class FileIOTest {

	File f = new File("fileIOtest.txt");

	//test of number of words
	@Test
	public void numberOfWords(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(10, fileTested.getWords());
	}

	//test of number of spaces
	@Test
	public void numberOfSpaces(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getSpaces());
	}

	//test of number of lines
	@Test
	public void numberOfLines(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getLines());
	}

	//test of number of blank lines
	@Test
	public void numberOfBlankLines(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(2, fileTested.getBlankLines());
	}

	//test of average characters per line
	@Test
	public void averageCharsPerLine(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(9, fileTested.getAvgCharsPerLine());
	}

	//test of average word length
	@Test
	public void averageWordLength(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(5, fileTested.getAvgWordLength());
	}

	//test of number of words with puncutation removed
	@Test
	public void numberOfWordsPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(10, fileTested.getWords());
	}

	//test of number of spaces with puncutation removed
	@Test
	public void numberOfSpacesPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getSpaces());
	}

	//test of number of lines with puncutation removed
	@Test
	public void numberOfLinesPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getLines());
	}

	//test of number of blank lines with puncutation removed
	@Test
	public void numberOfBlankLinesPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(3, fileTested.getBlankLines());
	}

	//test of average characters per line with puncutation removed
	@Test
	public void averageCharsPerLinePuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(8, fileTested.getAvgCharsPerLine());
	}

	//test of average word length with puncutation removed
	@Test
	public void averageWordLengthPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(4, fileTested.getAvgWordLength());
	}

}
