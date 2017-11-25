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

      String[] columnNames = {
        "Average Number Of Lines",
        "Average Number Of Blank Lines",
        "Average Number Of Spaces",
        "Average Number Of Words",
        "Average Number Of Chars Per Line",
        "Average Number Of Word length"
      };
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
