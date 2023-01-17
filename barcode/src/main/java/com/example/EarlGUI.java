package com.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;

public class EarlGUI implements ActionListener{
    GenerateBarcode generateBarcode = new GenerateBarcode();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    String printerName = "";
    String printerIP = "";
    String userChoice = "";
    String userChoiceCode = "";
    boolean isGenerated = false;
    JTextField userInputCode = new JTextField();
    JTextField userInput = new JTextField();
    String comboSelected = "";
    JTextArea output;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu jMenu;
    JMenuItem menuButton1, menuButton2, menuButton3;
    ImageIcon image;
    boolean selectedImage = false;
    JLabel printerNameLabel;
    JLabel printingLocation;

    public EarlGUI(){
        
        final String[] dropdownChoices = { "QR Codes", "CODE128", "AZTEC", "PLAIN TEXT", "IMAGE" };
        final JComboBox<String> comboBox = new JComboBox<String>(dropdownChoices);
        JButton generateButton = new JButton("Generate Code");
        JButton printButton = new JButton("Print");
        JButton imageButton = new JButton("Select Image");
        imageButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){
                    generateBarcode.selectImage();
                    selectedImage = true;
                }else {
                    JOptionPane.showMessageDialog(null,
                            "Please use the dropdown box and select image.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        userInputCode.setToolTipText("What kind of barcode would you like?");
        userInput.setToolTipText("Enter text you would like to print (Max 20 char)");
        printerName = generateBarcode.ReturnPrinterName();
        //printerIP  = generateBarcode.ReturnPrinterIP();
        printerNameLabel = new JLabel("Printer Name: " + printerName);
        printingLocation = new JLabel("Printing Location: " + generateBarcode.getImageSavePath());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        menuBar = new JMenuBar();
        jMenu = new JMenu("Menu");
        menuButton1 = new JMenuItem("Printer Settings");
        menuButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                new ChooseDirectory();
                frame.dispose();
            }
        });
        menuButton2 = new JMenuItem("About");
        menuButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                JOptionPane.showMessageDialog(null,
                            "Blueberry Technologies Barcode Editor.\n© Blueberry Technologies - 2022", "About...",
                            JOptionPane.WARNING_MESSAGE);
            }
        });
        jMenu.add(menuButton1);
        jMenu.add(menuButton2);

        menuBar.add(jMenu);
        frame.setJMenuBar(menuBar);
        
        generateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if(!dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE") && !selectedImage){
                    if(userInput.getText().length() > 255 && !dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){
                        JOptionPane.showMessageDialog(null,
                            "The amount of characters exceeds 255.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                    }else if (userInput.getText().length() > 50 && dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){
                        JOptionPane.showMessageDialog(null,
                        "The amount of characters exceeds 50.", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
                    }else{
                        generateBarcode.GenerateBarcodes(" " + userInput.getText(), dropdownChoices[comboBox.getSelectedIndex()]);
                        isGenerated = true;
                    }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "There is nothing to generate.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
                
            }
        });
        
        printButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (isGenerated && !selectedImage){
                    generateBarcode.PrintBarcode(generateBarcode.getReturnPath(), false);;
                }else if (selectedImage){
                    generateBarcode.PrintBarcode(generateBarcode.getImageFile(), false);
                    selectedImage = false;
                }else{
                    JOptionPane.showMessageDialog(null,
                            "The code wasn't generated.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        frame.setPreferredSize(new Dimension(300,400));
        frame.setLocationRelativeTo(null);
        
        panel.add(printerNameLabel);
        panel.add(printingLocation);
        panel.add(userInput);
        panel.add(comboBox);
        panel.add(imageButton);
        panel.add(generateButton);
        panel.add(printButton);
        panel.setLayout(new GridLayout(0,1));
        frame.add(panel, BorderLayout.CENTER);
        
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                JFrame frame = (JFrame)e.getSource();

                int result = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit the Blueberry Tech Barcode Editor?",
                "Exit Application",
                JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION){
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }else{
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
                
            }
        });
        frame.setTitle("Blueberry Technologies Barcode Editor");
        frame.pack();
        frame.setVisible(true);
    }
    public static void main (String args[]){
        GenerateBarcode.generateDirectory();
        if(GenerateBarcode.getImageSavePath() == null){
            new ChooseDirectory();
        }else{
            new EarlGUI();
        }
    }
    
    public void actionPerformed(ActionEvent arg0){

    }
}