import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
GUI class contains the main method.
 */
public class Graphics {
    /**
    The main method.
     */
    public static void startProgram() {
        JFrame frame = new JFrame("Text File Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.addTab("Choose File", new ChooseFilePanel());
        tabPanel.addTab("Analysis", new AnalysisPanel());
        tabPanel.addTab("History", new HistoryPanel());
        tabPanel.addTab("Help", new HelpPanel());

        frame.getContentPane().add(tabPanel);
        frame.setSize(500, 160);
        frame.setVisible(true);
    }
}
/**
ChooseFilePanel inner class creates the panel that is in the Choose File tab.
 */
class ChooseFilePanel extends JPanel {
    /**
    Instances variables.
     */
    private JButton chooseButton, analyzeButton;
    private JLabel descriptionLabel, fileLabel;
    private JTextField choosenFileName;
    private JPanel topPanel, middlePanel, bottomPanel;

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
        chooseButton = new JButton("Choose");
        choosenFileName = new JTextField();
        analyzeButton = new JButton("Analyze");

        topPanel.add(descriptionLabel);
        middlePanel.add(fileLabel, BorderLayout.WEST);
        middlePanel.add(choosenFileName, BorderLayout.CENTER);
        middlePanel.add(chooseButton, BorderLayout.EAST);
        bottomPanel.add(analyzeButton);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        chooseButton.addActionListener(new ChooseButtonListener());
    }

    /**
    Listener inner class for chooseButton.
     */
    private class ChooseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //String text = yearField.getText();
            //int year = Integer.parseInt(text) + 4;
            //graduationLabel.setText(" " + year);
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
    private int numberOfLines = 0, numberOfBlankLines = 0, numberOfSpaces = 0, numberOfWords = 0, averageCharsPerLines = 0;
    private int averageWordLength = 0;
    private String mostCommonWord = "";

    /**
    Constructor.
     */
    public AnalysisPanel() {
        setLayout(new GridLayout(7,1));
        numberOfLinesLabel = new JLabel("Number of lines: " + numberOfLines);
        numberOfBlankLinesLabel = new JLabel("Number of blank lines: " + numberOfBlankLines);
        numberOfSpacesLabel = new JLabel("Number of spaces:" + numberOfSpaces);
        numberOfWordsLabel = new JLabel("Number of words:" + numberOfWords);
        averageCharsPerLinesLabel = new JLabel("Average number of characters per line: " + averageCharsPerLines);
        averageWordLengthLabel = new JLabel("Average word length: " + averageWordLength);
        mostCommonWordLabel = new JLabel("Most common word: " + mostCommonWord);

        add(numberOfLinesLabel);
        add(numberOfBlankLinesLabel);
        add(numberOfSpacesLabel);
        add(numberOfWordsLabel);
        add(averageCharsPerLinesLabel);
        add(averageWordLengthLabel);
        add(mostCommonWordLabel);
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
