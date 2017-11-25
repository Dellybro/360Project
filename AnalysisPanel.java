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

        String[] columnNames = {
          "Punctuation Removed",
          "Name",
          "Lines",
          "Blank Lines",
          "Spaces",
          "Words",
          "Average Chars Per Line",
          "Average Word Length",
          "Most Common Word"
        };

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
