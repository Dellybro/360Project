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
HistoryPanel inner class creates the panel that is in the Analysis tab.
 */
class HistoryPanel extends JPanel {
    /**
    Instances variables.
     */
    private JTable historyTable;
    private JScrollPane scrollbar;
    String[] columnNames;

    /**
    Constructor.
     */
    public HistoryPanel() {
        super(new GridLayout(1, 0));

        historyTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        columnNames = new String[]{
          "Created Date",
          "Name"
        };

        tableModel.setColumnIdentifiers(columnNames);
        historyTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(historyTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

    }

    public void setHistory(ArrayList<FileAnalyzer> fileList, ArrayList<String> tableHeaders){


      String[] tb = tableHeaders.toArray(new String[tableHeaders.size()]);
      String[] bothHeaders = Utility.mergeStringArrays(columnNames, tb);

      DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
      model.setColumnIdentifiers(bothHeaders);
      model.setRowCount(0);

      for (int x = 0; x < fileList.size(); x++) {

        FileAnalyzer currentfile = fileList.get(x);

        String mostCommonWord = currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x";

        Object[] object = new Object[] {
          currentfile.getCreatedAt(),
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
}
