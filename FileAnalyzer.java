public class FileAnalyzer {
    public int lines;
    public int blankLines;
    public int spaces;
    public int words;
    public int avgCharsPerLine;
    public int avgWordLength;
    public int mostCommonWords;
    static int totalFiles = 0;
    public FileAnalyzer()
    {
        lines = 0;
        blankLines = 0;
        spaces = 0;
        words = 0;
        avgCharsPerLine = 0;
        avgWordLength = 0;
        mostCommonWords = 0;
    }
    public void setLines(int newLines)
    {
        lines = newLines;
    }
    public int getLines()
    {
        return lines;
    }
    public void setBlankLines(int newBlankLines)
    {
        blankLines = newBlankLines;
    }
    public int getBlankLines()
    {
        return blankLines;
    }
    public void setSpaces(int newSpaces)
    {
        spaces = newSpaces;
    }
    public int getSpaces()
    {
        return spaces;
    }
    public void setWords(int newWords)
    {
        words = newWords;
    }
    public int getWords()
    {
        return words;
    }
    public void setAvgCharsPerLine(int newAvg)
    {
        avgCharsPerLine = newAvg;
    }
    public int getAvgCharsPerLine()
    {
        return avgCharsPerLine;
    }
    public void setAvgWordLength(int newAvg)
    {
        avgWordLength = newAvg;
    }
    public void setMostCommonWords(int newCommon)
    {
        mostCommonWords = newCommon;
    }
}

