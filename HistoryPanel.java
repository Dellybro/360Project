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
    private Object[][] date = {};

    /**
    Constructor.
     */
    public HistoryPanel() {
        super(new GridLayout(1, 0));

        historyTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);

        String[] columnNames = {
          "Name",
          "Lines",
          "Blank Lines",
          "Spaces",
          "Words",
          "Average Chars Per Line",
          "Average Word Length",
          "Most common Word",
          "Created At"
        };

        tableModel.setColumnIdentifiers(columnNames);
        historyTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(historyTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

    }

    public void setHistory(ArrayList<FileAnalyzer> fileList){
      DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
      model.setRowCount(0);

      for (int x = 0; x < fileList.size(); x++) {

        FileAnalyzer currentfile = fileList.get(x);

        String mostCommonWord = currentfile.getMostCommonWords().peek().getWord() + " : " + currentfile.getMostCommonWords().peek().getCount() + "x";

        model.addRow(new Object[] {
          currentfile.getName(),
          currentfile.getLines(),
          currentfile.getBlankLines(),
          currentfile.getSpaces(),
          currentfile.getWords(),
          currentfile.getAvgCharsPerLine(),
          currentfile.getAvgWordLength(),
          mostCommonWord,
          currentfile.getCreatedAt()
        });
      }
    }
}
