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
    JCheckBox numLinesCB, blankLinesCB, numSpacesCB, numWordsCB, aveCharCB, aveWordCB, comWordCB, button8;

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

        numLinesCB = new JCheckBox("Number Of Lines");
        numLinesCB.setMnemonic(KeyEvent.VK_C);
        numLinesCB.setSelected(true);
        numLinesCB.addItemListener(this);

        blankLinesCB = new JCheckBox("Number Of Blank Lines");
        blankLinesCB.setMnemonic(KeyEvent.VK_C);
        blankLinesCB.setSelected(true);
        blankLinesCB.addItemListener(this);

        numSpacesCB = new JCheckBox("Number Of Spaces");
        numSpacesCB.setMnemonic(KeyEvent.VK_C);
        numSpacesCB.setSelected(true);
        numSpacesCB.addItemListener(this);

        numWordsCB = new JCheckBox("Number Of Words");
        numWordsCB.setMnemonic(KeyEvent.VK_C);
        numWordsCB.setSelected(true);
        numWordsCB.addItemListener(this);

        aveCharCB = new JCheckBox("Average Chars Per Line");
        aveCharCB.setMnemonic(KeyEvent.VK_C);
        aveCharCB.setSelected(true);
        aveCharCB.addItemListener(this);

        aveWordCB = new JCheckBox("Average Word Length");
        aveWordCB.setMnemonic(KeyEvent.VK_C);
        aveWordCB.setSelected(true);
        aveWordCB.addItemListener(this);

        comWordCB = new JCheckBox("Most Common Word");
        comWordCB.setMnemonic(KeyEvent.VK_C);
        comWordCB.setSelected(true);
        comWordCB.addItemListener(this);

        topPanel.add(descriptionLabel);
        middlePanel.add(fileLabel, BorderLayout.WEST);
        middlePanel.add(choosenFileName, BorderLayout.CENTER);
        middlePanel.add(chooseButton, BorderLayout.EAST);
        bottomPanel.add(analyzeButton);
        bottomPanel.add(numLinesCB);
        bottomPanel.add(blankLinesCB);
        bottomPanel.add(numSpacesCB);
        bottomPanel.add(numWordsCB);
        bottomPanel.add(aveCharCB);
        bottomPanel.add(aveWordCB);
        bottomPanel.add(comWordCB);

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

        // System.out.println("Item event ");
        // System.out.println("Does source == numLinesCB " + String.valueOf(source == numLinesCB));

        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (source == numLinesCB) {
            	Graphics.showNumLines = true;
              Graphics.addHeader(Utility.lines);
            } else if (source == blankLinesCB) {
            	Graphics.showNumBlankLines = true;
              Graphics.addHeader(Utility.blankLines);
            } else if (source == numSpacesCB) {
              Graphics.showNumSpaces = true;
              Graphics.addHeader(Utility.spaces);
            } else if (source == numWordsCB) {
              Graphics.showNumWords = true;
              Graphics.addHeader(Utility.words);
            }else if(source == aveCharCB){
            	Graphics.showAveChar = true;
              Graphics.addHeader(Utility.averageCharPerLine);
            }else if(source == aveWordCB){
            	Graphics.showAveWord = true;
              Graphics.addHeader(Utility.averageWordLength);
            }else if(source == comWordCB){
            	Graphics.showComWord = true;
              Graphics.addHeader(Utility.mostCommonWord);
            }
        }

        if (e.getStateChange() == ItemEvent.DESELECTED) {
            if (source == numLinesCB) {
              System.out.println("Removing header");
            	Graphics.showNumLines = false;
              Graphics.removeHeader(Utility.lines);
            } else if (source == blankLinesCB) {
            	Graphics.showNumBlankLines = false;
              Graphics.removeHeader(Utility.blankLines);
            } else if (source == numSpacesCB) {
              Graphics.showNumSpaces = false;
              Graphics.removeHeader(Utility.spaces);
            } else if (source == numWordsCB) {
              Graphics.showNumWords = false;
              Graphics.removeHeader(Utility.words);
            }else if(source == aveCharCB){
            	Graphics.showAveChar = false;
              Graphics.removeHeader(Utility.averageCharPerLine);
            }else if(source == aveWordCB){
            	Graphics.showAveWord = false;
              Graphics.removeHeader(Utility.averageWordLength);
            }else if(source == comWordCB){
            	Graphics.showComWord = false;
              Graphics.removeHeader(Utility.mostCommonWord);
            }
        }
    }
}
