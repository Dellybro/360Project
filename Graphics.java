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
    public static AveragesPanel averagePanel;
    public static boolean showNumLines, showNumBlankLines, showNumSpaces, showNumWords, showAveChar, showAveWord, showComWord;

    /*

      The main method.

    */
    public static void startProgram() {
      frame = new JFrame("Text File Analyzer");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      showNumLines = true;
      showNumBlankLines = true;
      showNumSpaces = true;
      showNumWords = true;
      showAveChar = true;
      showAveWord = true;
      showComWord = true;
      
      tabPanel = new JTabbedPane();

      /* Create panels */
      choosePanel = new ChooseFilePanel();
      analysisPanel = new AnalysisPanel();
      historyPanel = new HistoryPanel();
      averagePanel = new AveragesPanel();

      JScrollPane analysisScroller = new JScrollPane(analysisPanel);
      /* Add panels to tabPanel */
      tabPanel.addTab("Choose File", choosePanel);
      tabPanel.addTab("Analysis", analysisScroller);
      tabPanel.addTab("History", historyPanel);
      tabPanel.addTab("Averages", averagePanel);
      tabPanel.addTab("Help", new HelpPanel());

      frame.getContentPane().add(tabPanel);
      frame.setSize(500, 160);
      frame.setVisible(true);


      tabPanel.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if(tabPanel.getSelectedIndex() == 0){
            frame.setSize(500, 160);
          } else if(tabPanel.getSelectedIndex() == 1){
            frame.setSize(700, 360);
          }else if(tabPanel.getSelectedIndex() == 2){
            frame.setSize(700, 360);
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            historyPanel.setHistory(fileList);
          }else if(tabPanel.getSelectedIndex() == 3){
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            averagePanel.setAverages(fileList);
            frame.setSize(500, 160);
          }else if(tabPanel.getSelectedIndex() == 4){
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
class ChooseFilePanel extends JPanel implements ActionListener, ItemListener{
    /**
    Instances variables.
     */
    private JButton chooseButton, analyzeButton;
    private JLabel descriptionLabel, fileLabel;
    private JTextField choosenFileName;
    private JPanel topPanel, middlePanel, bottomPanel;
    JCheckBox button1, button2, button3, button4, button5, button6, button7, button8;

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
        
        button1 = new JCheckBox("Number of Lines:");
        button1.setMnemonic(KeyEvent.VK_C);
        button1.setSelected(true);
        button1.addItemListener(this);
        
        button2 = new JCheckBox("Number of blank lines:");
        button2.setMnemonic(KeyEvent.VK_C);
        button2.setSelected(true);
        button2.addItemListener(this);
        
        button3 = new JCheckBox("Number of spaces:");
        button3.setMnemonic(KeyEvent.VK_C);
        button3.setSelected(true);
        button3.addItemListener(this);
        
        button4 = new JCheckBox("Number of words:");
        button4.setMnemonic(KeyEvent.VK_C);
        button4.setSelected(true);
        button4.addItemListener(this);

        button5 = new JCheckBox("Number of words:");
        button5.setMnemonic(KeyEvent.VK_C);
        button5.setSelected(true);
        button5.addItemListener(this);
        
        button6 = new JCheckBox("Average chars per line:");
        button6.setMnemonic(KeyEvent.VK_C);
        button6.setSelected(true);
        button6.addItemListener(this);
        
        button7 = new JCheckBox("Average word length:");
        button7.setMnemonic(KeyEvent.VK_C);
        button7.setSelected(true);
        button7.addItemListener(this);
        
        button8 = new JCheckBox("Most common words:");
        button8.setMnemonic(KeyEvent.VK_C);
        button8.setSelected(true);
        button8.addItemListener(this);
        
        topPanel.add(descriptionLabel);
        middlePanel.add(fileLabel, BorderLayout.WEST);
        middlePanel.add(choosenFileName, BorderLayout.CENTER);
        middlePanel.add(chooseButton, BorderLayout.EAST);
        bottomPanel.add(analyzeButton);
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);
        bottomPanel.add(button4);
        bottomPanel.add(button5);
        bottomPanel.add(button6);
        bottomPanel.add(button7);
        bottomPanel.add(button8);

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
    
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
 
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (source == button1) {
            	Graphics.showNumLines = true;
            } else if (source == button2) {
            	Graphics.showNumBlankLines = true;
            } else if (source == button3) {
                Graphics.showNumSpaces = true;
            } else if (source == button4) {
                Graphics.showNumWords = true;
            }else if(source == button5){
            	Graphics.showAveChar = true;
            }else if(source == button6){
            	Graphics.showAveWord = true;
            }else if(source == button7){
            	Graphics.showComWord = true;
            }
        }
 
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            if (source == button1) {
            	Graphics.showNumLines = false;
            } else if (source == button2) {
            	Graphics.showNumBlankLines = false;
            } else if (source == button3) {
                Graphics.showNumSpaces = false;
            } else if (source == button4) {
                Graphics.showNumWords = false;
            }else if(source == button5){
            	Graphics.showAveChar = false;
            }else if(source == button6){
            	Graphics.showAveWord = false;
            }else if(source == button7){
            	Graphics.showComWord = false;
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
    private JTextArea table;

    private ArrayList<FileAnalyzer> validFiles;
    private ArrayList<FileAnalyzer> invalidFiles;

    /**
    Constructor.
     */
    public AnalysisPanel() {
        setLayout(new GridLayout(1,1));
        table = new JTextArea();
        add(table);
    }
    //change data sets
    public void setValid(ArrayList<FileAnalyzer> validFiles){
      this.validFiles = validFiles;

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

}

/**
Averages Panel
*/


class AveragesPanel extends JPanel {
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
  public AveragesPanel() {
      setLayout(new GridLayout(6,1));
      numberOfLinesLabel = new JLabel("Number of lines:");
      numberOfBlankLinesLabel = new JLabel("Number of blank lines:");
      numberOfSpacesLabel = new JLabel("Number of spaces:");
      numberOfWordsLabel = new JLabel("Number of words:");
      averageCharsPerLinesLabel = new JLabel("Average number of characters per line: ");
      averageWordLengthLabel = new JLabel("Average word length: ");
      // mostCommonWordLabel = new JLabel("Most common word: ");

      add(numberOfLinesLabel);
      add(numberOfBlankLinesLabel);
      add(numberOfSpacesLabel);
      add(numberOfWordsLabel);
      add(averageCharsPerLinesLabel);
      add(averageWordLengthLabel);
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
    private JLabel number4Label;

    /**
    Constructor.
     */
    public HelpPanel() {
        setLayout(new GridLayout(4,1));
        number1Label = new JLabel("Choose File Tab - Select a file from your computer.");
        number2Label = new JLabel("Analysis Tab - Shows the analysis of the file choosen.");
        number3Label = new JLabel("History Tab - Shows the history of all files that have been analyzed.");
        number4Label = new JLabel("Averages Tab - Shows the averages of all analysis that has been analyzed.");
        add(number1Label);
        add(number2Label);
        add(number3Label);
        add(number4Label);

    }

}
// End of GUI.java.
