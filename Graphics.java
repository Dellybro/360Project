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
      frame.setSize(1300, 250);
      frame.setVisible(true);


      tabPanel.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if(tabPanel.getSelectedIndex() == 0){
            frame.setSize(1300, 250);
          } else if(tabPanel.getSelectedIndex() == 1){
            frame.setSize(1300, 250);
          }else if(tabPanel.getSelectedIndex() == 2){
            frame.setSize(1300, 250);
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            historyPanel.setHistory(fileList);
          }else if(tabPanel.getSelectedIndex() == 3){
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            averagePanel.setAverages(fileList);
            frame.setSize(1300, 250);
          }else if(tabPanel.getSelectedIndex() == 4){
            frame.setSize(1300, 250);
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

        descriptionLabel = new JLabel("Choose A File To Analyze");
        fileLabel = new JLabel("File:");
        chooseButton = new JButton("Choose File(s)");
        analyzeButton = new JButton("Analyze");
        choosenFileName = new JTextField();
        choosenFileName.setEnabled(false);

        button1 = new JCheckBox("Number Of Lines");
        button1.setMnemonic(KeyEvent.VK_C);
        button1.setSelected(true);
        button1.addItemListener(this);

        button2 = new JCheckBox("Number Of Blank Lines");
        button2.setMnemonic(KeyEvent.VK_C);
        button2.setSelected(true);
        button2.addItemListener(this);

        button3 = new JCheckBox("Number Of Spaces");
        button3.setMnemonic(KeyEvent.VK_C);
        button3.setSelected(true);
        button3.addItemListener(this);

        button4 = new JCheckBox("Number Of Words");
        button4.setMnemonic(KeyEvent.VK_C);
        button4.setSelected(true);
        button4.addItemListener(this);

        button5 = new JCheckBox("Number Of Words");
        button5.setMnemonic(KeyEvent.VK_C);
        button5.setSelected(true);
        button5.addItemListener(this);

        button6 = new JCheckBox("Average Chars Per Line");
        button6.setMnemonic(KeyEvent.VK_C);
        button6.setSelected(true);
        button6.addItemListener(this);

        button7 = new JCheckBox("Average Word Length");
        button7.setMnemonic(KeyEvent.VK_C);
        button7.setSelected(true);
        button7.addItemListener(this);

        button8 = new JCheckBox("Most Common Words");
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
    private JTable analysisTable;
    private ArrayList<FileAnalyzer> validFiles;
    private ArrayList<FileAnalyzer> invalidFiles;

    /**
    Constructor.
    */
    public AnalysisPanel() {
        super(new GridLayout(1, 0));

        analysisTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        String[] columnNames = {"Punctuation Removed", "Name", "Lines", "Blank Lines", "Spaces", "Words", "Average Chars Per Line", "Average Word Length", "Most Common Word"};

        tableModel.setColumnIdentifiers(columnNames);
        analysisTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(analysisTable);
        add(scrollPane);
    }

    /**
    Change data sets.
    */

    public void setValid(ArrayList<FileAnalyzer> validFiles){
      this.validFiles = validFiles;

      DefaultTableModel model = (DefaultTableModel) analysisTable.getModel();
      model.setRowCount(0);

      for (int x = 0; x < validFiles.size(); x++) {
        FileAnalyzer currentfile = validFiles.get(x);

        String mostCommonWord = currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x";

        model.addRow(new Object[] {
          "No",
          currentfile.getName(),
          currentfile.getLines(),
          currentfile.getBlankLines(),
          currentfile.getSpaces(),
          currentfile.getWords(),
          currentfile.getAvgCharsPerLine(),
          currentfile.getAvgWordLength(),
          mostCommonWord
        });

        currentfile = FileIO.removePunctuation(currentfile.getName(), currentfile.getFilePath());
        mostCommonWord = currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x";

        model.addRow(new Object[] {
          "Yes",
          currentfile.getName(),
          currentfile.getLines(),
          currentfile.getBlankLines(),
          currentfile.getSpaces(),
          currentfile.getWords(),
          currentfile.getAvgCharsPerLine(),
          currentfile.getAvgWordLength(),
          mostCommonWord
        });

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
  private String mostCommonWord = "";
  private JTextArea table;
  private JTable averagesTable;
  private ArrayList<FileAnalyzer> validFiles;
  private ArrayList<FileAnalyzer> invalidFiles;


  /**
  Constructor.
   */
  public AveragesPanel() {
      super(new GridLayout(1, 0));

      averagesTable = new JTable();
      DefaultTableModel tableModel = new DefaultTableModel(0, 0);

      String[] columnNames = {"Average Number Of Lines", "Average Number Of Blank Lines", "Average Number Of Spaces", "Average Number Of Words", "Average Number Of Chars Per Line", "Average Number Of Word :ength"};
      tableModel.setColumnIdentifiers(columnNames);
      averagesTable.setModel(tableModel);

      JScrollPane scrollPane = new JScrollPane(averagesTable);
      add(scrollPane);

  }

  public void setAverages(ArrayList<FileAnalyzer> fileList){
    int avgLines = 0, avgBlankLines = 0, avgSpaces = 0, avgWords = 0, avgCharsPerLine = 0, avgWordLength = 0;

    DefaultTableModel model = (DefaultTableModel) averagesTable.getModel();
    model.setRowCount(0);

    for(int x = 0; x < fileList.size(); x++){
      FileAnalyzer file = fileList.get(x);

      avgLines += file.getLines();
      avgBlankLines += file.getBlankLines();
      avgSpaces += file.getSpaces();
      avgWords += file.getWords();
      avgCharsPerLine += file.getAvgCharsPerLine();
      avgWordLength += file.getAvgWordLength();
    }

    model.addRow(new Object[] {
      avgLines/fileList.size(),
      avgBlankLines/fileList.size(),
      avgSpaces/fileList.size(),
      avgWords/fileList.size(),
      avgCharsPerLine/fileList.size(),
      avgWordLength/fileList.size()
    });
  }

}
/**
HistoryPanel inner class creates the panel that is in the Analysis tab.
 */
class HistoryPanel extends JPanel {
    /**
    Instances variables.
     */
    private JTable historyTable;
    private JScrollPane scrollbar;
    private Object[][] date = {};

    /**
    Constructor.
     */
    public HistoryPanel() {
        super(new GridLayout(1, 0));

        historyTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        String[] columnNames = {"Name", "Lines", "Blank Lines", "Spaces", "Words", "Average Chars Per Line", "Average Word Length", "Created At"};

        tableModel.setColumnIdentifiers(columnNames);
        historyTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane);

    }

    public void setHistory(ArrayList<FileAnalyzer> fileList){
      DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
      model.setRowCount(0);

      for (int x = 0; x < fileList.size(); x++) {

        FileAnalyzer currentfile = fileList.get(x);

        model.addRow(new Object[] {
          currentfile.getName(),
          currentfile.getLines(),
          currentfile.getBlankLines(),
          currentfile.getSpaces(),
          currentfile.getWords(),
          currentfile.getAvgCharsPerLine(),
          currentfile.getAvgWordLength(),
          currentfile.getCreatedAt()
        });
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
