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
    public static ArrayList<String> tableHeaders = new ArrayList<String>();

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

      tableHeaders.add(Utility.lines);
      tableHeaders.add(Utility.blankLines);
      tableHeaders.add(Utility.spaces);
      tableHeaders.add(Utility.words);
      tableHeaders.add(Utility.averageCharPerLine);
      tableHeaders.add(Utility.averageWordLength);
      tableHeaders.add(Utility.mostCommonWord);

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
      frame.setSize(700, 250);
      frame.setVisible(true);


      tabPanel.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          if(tabPanel.getSelectedIndex() == 0){
            frame.setSize(700, 250);
          } else if(tabPanel.getSelectedIndex() == 1){
            frame.setSize(600, 250);
          }else if(tabPanel.getSelectedIndex() == 2){
            frame.setSize(600, 250);
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            historyPanel.setHistory(fileList, tableHeaders);
          }else if(tabPanel.getSelectedIndex() == 3){
            ArrayList<FileAnalyzer> fileList = DatabaseHandler.getAllRows();
            averagePanel.setAverages(fileList);
            frame.setSize(600, 250);
          }else if(tabPanel.getSelectedIndex() == 4){
            frame.setSize(600, 250);
          }
        }
      });
    }

    public static void removeHeader(String headerToRemove){
      for ( int i = 0;  i < tableHeaders.size(); i++){
        if(tableHeaders.get(i).equals(headerToRemove)){
            tableHeaders.remove(i);
            return;
        }
      }
    }

    public static void addHeader(String headerToAdd){
      tableHeaders.add(headerToAdd);
    }

    public static void sendFilesToAnalysis(ArrayList<FileAnalyzer> validFiles, ArrayList<String> invalidFiles){
      analysisPanel.setValid(validFiles,tableHeaders);

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
  Help Panel doesn't need it's only file, pretty simply tab.
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
