import static org.junit.Assert.*;

import org.junit.Test;


public class FileIOTest {

	@Test
	public void numberOfWords(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(10, fileTested.getWords());
	}

	@Test
	public void numberOfSpaces(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(7, fileTested.getSpaces());
	}

	@Test
	public void numberOfLines(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(7, fileTested.getLines());
	}

	@Test
	public void numberOfBlankLines(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(2, fileTested.getBlankLines());
	}

	@Test
	public void averageCharsPerLine(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(9, fileTested.getAvgCharsPerLine());
	}

	@Test
	public void averageWordLength(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.readFile("fileIOtest.txt");
		assertEquals(5, fileTested.getAvgWordLength());
	}

	@Test
	public void numberOfWordsPuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(10, fileTested.getWords());
	}

	@Test
	public void numberOfSpacesPuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(7, fileTested.getSpaces());
	}

	@Test
	public void numberOfLinesPuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(7, fileTested.getLines());
	}

	@Test
	public void numberOfBlankLinesPuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(3, fileTested.getBlankLines());
	}

	@Test
	public void averageCharsPerLinePuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(8, fileTested.getAvgCharsPerLine());
	}

	@Test
	public void averageWordLengthPuncRemoved(){
		FileIO fileTester = new FileIO();
		FileAnalyzer fileTested = fileTester.removePunctuation("fileIOtest.txt");
		assertEquals(4, fileTested.getAvgWordLength());
	}

}
