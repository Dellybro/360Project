import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class FileIOTest {

	File f = new File("fileIOtest.txt");

	@Test
	public void numberOfWords(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(10, fileTested.getWords());
	}

	@Test
	public void numberOfSpaces(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getSpaces());
	}

	@Test
	public void numberOfLines(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getLines());
	}

	@Test
	public void numberOfBlankLines(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(2, fileTested.getBlankLines());
	}

	@Test
	public void averageCharsPerLine(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(9, fileTested.getAvgCharsPerLine());
	}

	@Test
	public void averageWordLength(){
		FileAnalyzer fileTested = FileIO.readFile(f.getName(),f.toPath().toString());
		assertEquals(5, fileTested.getAvgWordLength());
	}

	@Test
	public void numberOfWordsPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(10, fileTested.getWords());
	}

	@Test
	public void numberOfSpacesPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getSpaces());
	}

	@Test
	public void numberOfLinesPuncRemoved(){

		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(7, fileTested.getLines());
	}

	@Test
	public void numberOfBlankLinesPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(3, fileTested.getBlankLines());
	}

	@Test
	public void averageCharsPerLinePuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(8, fileTested.getAvgCharsPerLine());
	}

	@Test
	public void averageWordLengthPuncRemoved(){
		FileAnalyzer fileTested = FileIO.removePunctuation(f.getName(),f.toPath().toString());
		assertEquals(4, fileTested.getAvgWordLength());
	}

}
