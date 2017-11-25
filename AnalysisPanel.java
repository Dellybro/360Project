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
    private String[] columnNames;
    /**
    Constructor.
    */
    public AnalysisPanel() {
        super(new GridLayout(1, 0));

        analysisTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        columnNames = new String[] {
          "Punctuation Removed",
          "Name"
        };

        tableModel.setColumnIdentifiers(columnNames);
        analysisTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(analysisTable);
        add(scrollPane);
    }

    /**
    Change data sets.
    */

    public void setValid(ArrayList<FileAnalyzer> validFiles, ArrayList<String>tableHeaders){
      this.validFiles = validFiles;

      String[] tb = tableHeaders.toArray(new String[tableHeaders.size()]);
      String[] bothHeaders = Utility.mergeStringArrays(columnNames, tb);

      DefaultTableModel model = (DefaultTableModel) analysisTable.getModel();
      model.setColumnIdentifiers(bothHeaders);
      model.setRowCount(0);

      for (int x = 0; x < validFiles.size(); x++) {
        FileAnalyzer currentfile = validFiles.get(x);
        Object[] object = new Object[] {
          "No",
          currentfile.getName()
        };

        for(int y = 0; y < tableHeaders.size(); y++){
          object = tableHeaderSwitchCase(tableHeaders.get(y), object, currentfile);
        }

        model.addRow(object);


        currentfile = FileIO.removePunctuation(currentfile.getName(), currentfile.getFilePath());
        object = new Object[] {
          "Yes",
          currentfile.getName()
        };

        for(int y = 0; y < tableHeaders.size(); y++){
          object = tableHeaderSwitchCase(tableHeaders.get(y), object, currentfile);
        }


        model.addRow(object);

      }
    }

    public Object[] tableHeaderSwitchCase(String word, Object[] object, FileAnalyzer currentfile){
      String mostCommonWord = currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x";
      switch (word){
        case Utility.lines:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getLines() });
        case Utility.blankLines:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getBlankLines() });
        case Utility.spaces:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getSpaces() });
        case Utility.words:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getWords() });
        case Utility.averageCharPerLine:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getAvgCharsPerLine() });
        case Utility.averageWordLength:
          return Utility.mergeObjectArrays(object, new Object[]{ currentfile.getAvgWordLength() });
        case Utility.mostCommonWord:
          return Utility.mergeObjectArrays(object, new Object[]{ mostCommonWord });
        default:
          return object;
      }
    }

    public void setInvalid(ArrayList<FileAnalyzer> invalidFiles){
      this.invalidFiles = invalidFiles;
    }
}
