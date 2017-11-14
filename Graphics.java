import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.File;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
/**
GUI class contains the main method.
 */
public class Graphics {
    public static JFrame frame;
    public static JTabbedPane tabPanel;
    public static ChooseFilePanel choosePanel;
    public static AnalysisPanel analysisPanel;
    public static HistoryPanel historyPanel;

    /*

      The main method.

    */
    public static void startProgram() {
      frame = new JFrame("Text File Analyzer");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      tabPanel = new JTabbedPane();

      /* Create panels */
      choosePanel = new ChooseFilePanel();
      analysisPanel = new AnalysisPanel();
      historyPanel = new HistoryPanel();

      JScrollPane analysisScroller = new JScrollPane(analysisPanel);
      /* Add panels to tabPanel */
      tabPanel.addTab("Choose File", choosePanel);
      tabPanel.addTab("Analysis", analysisScroller);
      tabPanel.addTab("History", historyPanel);
      tabPanel.addTab("Help", new HelpPanel());

      frame.getContentPane().add(tabPanel);
      frame.setSize(500, 160);
      frame.setVisible(true);


      tabPanel.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if(tabPanel.getSelectedIndex() == 0){
            frame.setSize(500, 160);
          } else if(tabPanel.getSelectedIndex() == 1){
            frame.setSize(700, 560);
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            analysisPanel.setAverages(fileList);
          }else if(tabPanel.getSelectedIndex() == 2){
            frame.setSize(700, 560);
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            historyPanel.setHistory(fileList);
          }else if(tabPanel.getSelectedIndex() == 3){
            frame.setSize(500, 160);
          }
        }
      });
    }

    public static void sendFilesToAnalysis(ArrayList<FileAnalyzer> validFiles, ArrayList<String> invalidFiles){
      analysisPanel.setValid(validFiles);

      if(validFiles.size() > 0){
        tabPanel.setSelectedIndex(1);
        /* TODO: go to tab */
      }

      for(int x = 0; x < invalidFiles.size(); x++){
        JOptionPane.showMessageDialog(frame, "Invalid File Name: " + invalidFiles.get(x));
        // JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
      }

    }
}
/**
ChooseFilePanel inner class creates the panel that is in the Choose File tab.
 */
class ChooseFilePanel extends JPanel implements ActionListener {
    /**
    Instances variables.
     */
    private JButton chooseButton, analyzeButton;
    private JLabel descriptionLabel, fileLabel;
    private JTextField choosenFileName;
    private JPanel topPanel, middlePanel, bottomPanel;

    private File[] files;
    /**
    Constructor.
     */
    public ChooseFilePanel() {
        setLayout(new GridLayout(3,1));
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        middlePanel.setLayout(new BorderLayout());

        descriptionLabel = new JLabel("Choose a file to be analyzed.");
        fileLabel = new JLabel("File:");
        chooseButton = new JButton("Choose File(s)");
        analyzeButton = new JButton("Analyze");
        choosenFileName = new JTextField();
        choosenFileName.setEnabled(false);

        topPanel.add(descriptionLabel);
        middlePanel.add(fileLabel, BorderLayout.WEST);
        middlePanel.add(choosenFileName, BorderLayout.CENTER);
        middlePanel.add(chooseButton, BorderLayout.EAST);
        bottomPanel.add(analyzeButton);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        chooseButton.addActionListener(this);
        analyzeButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){

      if(e.getActionCommand().equals("Analyze")){
        if(files == null || files.length == 0 ){
          JOptionPane.showMessageDialog(null, "No files selected");
          return;
        }

        /* Analyze Files */
        ArrayList<FileAnalyzer> validFiles = new ArrayList<FileAnalyzer>();
        ArrayList<String> invalidFiles = new ArrayList<String>();

        for (int x = 0; x < files.length; x++) {
          String filePath = files[x].toPath().toString();
          String fileName = files[x].getName();
          FileAnalyzer analyzedFile = FileIO.readFile(fileName, filePath);
          if(analyzedFile != null){
            analyzedFile.printToConsole();
            /* Insert into database, and add to validfiles array */
            DatabaseHandler.insertRow(analyzedFile);
            validFiles.add(analyzedFile);
          } else {
            /* Add to invalid array */
            invalidFiles.add(fileName);
          }
        }

        /* Send files back to main ? */
        Graphics.sendFilesToAnalysis(validFiles, invalidFiles);
      } else {
        /* File chooser */
        JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        fc.setMultiSelectionEnabled(true);

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
          files = fc.getSelectedFiles();

          String fileNames = "";
          for(int i = 0; i < files.length; i++){
            System.out.println(files[i].toPath());
            if(i == files.length - 1){
              fileNames = fileNames + files[i].getName();
            } else {
              fileNames = fileNames + files[i].getName() + ", ";
            }
          }
          choosenFileName.setText(fileNames);
        } else {
          files = null;
          choosenFileName.setText("No Files Selected");
        }
      }
    }
}
/**
AnalysisPanel inner class creates the panel that is in the Analysis tab.
 */
class AnalysisPanel extends JPanel {
    /**
    Instances variables.
     */
    private JLabel numberOfLinesLabel, numberOfBlankLinesLabel, numberOfSpacesLabel, numberOfWordsLabel, averageCharsPerLinesLabel;
    private JLabel averageWordLengthLabel, mostCommonWordLabel;
    private String mostCommonWord = "";
    private JTextArea table;

    private ArrayList<FileAnalyzer> validFiles;
    private ArrayList<FileAnalyzer> invalidFiles;

    /**
    Constructor.
     */
    public AnalysisPanel() {
        setLayout(new GridLayout(7,1));
        numberOfLinesLabel = new JLabel("Number of lines:");
        numberOfBlankLinesLabel = new JLabel("Number of blank lines:");
        numberOfSpacesLabel = new JLabel("Number of spaces:");
        numberOfWordsLabel = new JLabel("Number of words:");
        averageCharsPerLinesLabel = new JLabel("Average number of characters per line: ");
        averageWordLengthLabel = new JLabel("Average word length: ");
        // mostCommonWordLabel = new JLabel("Most common word: ");

        table = new JTextArea();

        add(numberOfLinesLabel);
        add(numberOfBlankLinesLabel);
        add(numberOfSpacesLabel);
        add(numberOfWordsLabel);
        add(averageCharsPerLinesLabel);
        add(averageWordLengthLabel);
        add(table);
    }

    public void setValid(ArrayList<FileAnalyzer> validFiles){
      this.validFiles = validFiles;
      System.out.println("Being called");

      table.setText("Punctuation Removed, Name, Lines, Blank Lines, Spaces, Words, AVG Chars Per Line, AVG Word Length, Most Common Word\n");

      for (int x = 0; x < validFiles.size(); x++) {
        FileAnalyzer currentfile = validFiles.get(x);
        table.append(
          "No," +
          currentfile.getName() + ", " +
          currentfile.getLines() + ", " +
          currentfile.getBlankLines() + ", " +
          currentfile.getSpaces() + ", " +
          currentfile.getWords() + ", " +
          currentfile.getAvgCharsPerLine() + ", " +
          currentfile.getAvgWordLength() + ", " +
          currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x" + ", " +
          "\n"
        );

        currentfile = FileIO.removePunctuation(currentfile.getName(), currentfile.getFilePath());
        table.append(
          "Yes," +
          currentfile.getName() + ", " +
          currentfile.getLines() + ", " +
          currentfile.getBlankLines() + ", " +
          currentfile.getSpaces() + ", " +
          currentfile.getWords() + ", " +
          currentfile.getAvgCharsPerLine() + ", " +
          currentfile.getAvgWordLength() + ", " +
          currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x" + ", " +
          "\n"
        );
      }
    }
    public void setInvalid(ArrayList<FileAnalyzer> invalidFiles){
      this.invalidFiles = invalidFiles;
    }


    public void setAverages(ArrayList<FileAnalyzer> fileList){
      int avgLines = 0, avgBlankLines = 0, avgSpaces = 0, avgWords = 0, avgCharsPerLine = 0, avgWordLength = 0;

      for(int x = 0; x < fileList.size(); x++){
        FileAnalyzer file = fileList.get(x);
        avgLines += file.getLines();
        avgBlankLines += file.getBlankLines();
        avgSpaces += file.getSpaces();
        avgWords += file.getWords();
        avgCharsPerLine += file.getAvgCharsPerLine();
        avgWordLength += file.getAvgWordLength();
      }


      numberOfLinesLabel.setText("Average # of lines: " + avgLines / (float)fileList.size());
      numberOfBlankLinesLabel.setText("Average # of blank lines: " + avgBlankLines / (float)fileList.size());
      numberOfSpacesLabel.setText("Average # of spaces: " + avgSpaces / (float)fileList.size());
      numberOfWordsLabel.setText("Average # of words: " + avgWordLength / (float)fileList.size());
      averageCharsPerLinesLabel.setText("Average # of characters per line: " + avgCharsPerLine / (float)fileList.size());
      averageWordLengthLabel.setText("Average # of word length: " + avgWordLength / (float)fileList.size());
    }

}
/**
HistoryPanel inner class creates the panel that is in the Analysis tab.
 */
class HistoryPanel extends JPanel {
    /**
    Instances variables.
     */
    private JTextArea historyTextField;
    private JScrollPane scrollbar;

    /**
    Constructor.
     */
    public HistoryPanel() {
        setLayout(new BorderLayout());
        historyTextField = new JTextArea();
        // This code for the scroll bar may or may not work. If it does not work I can fix it.
        scrollbar = new JScrollPane (historyTextField);
        scrollbar.setVerticalScrollBarPolicy(JScrollPane. VERTICAL_SCROLLBAR_ALWAYS);
        add(historyTextField, BorderLayout.CENTER);

    }

    public void setHistory(ArrayList<FileAnalyzer> fileList){
      historyTextField.setText("Name, Lines, Blank Lines, Spaces, Words, AVG Chars Per Line, AVG Word Length, Created At\n");
      for (int x = 0; x < fileList.size(); x++) {
        FileAnalyzer currentfile = fileList.get(x);
        historyTextField.append(
          currentfile.getName() + ", " +
          currentfile.getLines() + ", " +
          currentfile.getBlankLines() + ", " +
          currentfile.getSpaces() + ", " +
          currentfile.getWords() + ", " +
          currentfile.getAvgCharsPerLine() + ", " +
          currentfile.getAvgWordLength() + ", " +
          currentfile.getCreatedAt() +
          "\n"
        );
      }
    }

}
/**
HelpPanel inner class creates the panel that is in the Analysis tab.
 */
class HelpPanel extends JPanel {
    /**
    Instances variables.
     */
    private JLabel number1Label;
    private JLabel number2Label;
    private JLabel number3Label;

    /**
    Constructor.
     */
    public HelpPanel() {
        setLayout(new GridLayout(3,1));
        number1Label = new JLabel("Choose File Tab - Select a file from your computer.");
        number2Label = new JLabel("Analysis Tab - Shows the analysis of the file choosen.");
        number3Label = new JLabel("History Tab - Shows the history of all files that have been analyzed.");
        add(number1Label);
        add(number2Label);
        add(number3Label);

    }

}
// End of GUI.java.
