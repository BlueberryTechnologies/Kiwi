package com.example;

/*
 * Awt + Misc Imports
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;


/*
 * Java Swing Imports
 */

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
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;




/*
 * Java IO Imports
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/*
 * Print Imports
 */




public class BlueberryTechBarcodeEditor implements ActionListener{
    
    static BlueberryTechBarcodeGenerator barcodeGenerator = new BlueberryTechBarcodeGenerator();

    /*
     * Variables
     */
    
    String printerName = "";
    String printerIP = "";
    String userChoice = "";
    String userChoiceCode = "";
    String comboSelected = "";
    String finalPath = "";
    String textToWrite;
    String targetFile;
    String customLocation;
    String photoLocation;
    boolean isGenerated = false;
    boolean selectedImage = false;
    ImageIcon image;
    File file;

    /*
     * Main Frame and Main Panel Variables
     */
    JFrame mainFrame = new JFrame();
    JPanel mainPanel = new JPanel();

    /*
     * Directory Frame and Directory Panel Variables
     */
    JFrame directoryFrame = new JFrame();
    JPanel directoryPanel = new JPanel();

    /* */
    JFileChooser chooser = new JFileChooser();
    JLabel currLocation = new JLabel(" Current Location: " + barcodeGenerator.getImageSavePath());
    JLabel printerNameLabel;
    JLabel printingLocation;
    JTextField userInputCode = new JTextField();
    JTextField userInput = new JTextField();
    JTextArea output;
    JScrollPane scrollPane;
    JMenu jMenu;
    JMenuBar menuBar;
    JMenuItem menuButton1, menuButton2, menuButton3;
    File customFileLocation;
    public BlueberryTechBarcodeEditor(){
        
        final String[] dropdownChoices = { "QR Codes", "CODE128", "AZTEC", "PLAIN TEXT", "IMAGE" };
        final JComboBox<String> comboBox = new JComboBox<String>(dropdownChoices);
        JButton generateButton = new JButton("Generate Code");
        JButton printButton = new JButton("Print");
        JButton imageButton = new JButton("Select Image");



        imageButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){
                    barcodeGenerator.selectImage();
                    selectedImage = true;
                }else {
                    JOptionPane.showMessageDialog(null,
                            "Please use the dropdown box and select image.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton dirButton = new JButton("Select Directory");
        dirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                barcodeGenerator.setDirectory(false);
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE);
                directoryFrame.invalidate();
                directoryFrame.revalidate();
                directoryFrame.repaint();
                directoryFrame.validate();
            }
        });

        JButton defDirButton = new JButton("Use Default Directory");
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                barcodeGenerator.setDirectory(true);
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE);
            }
        });
        directoryFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                new BlueberryTechBarcodeEditor();
            }
        });
        directoryPanel.add(currLocation);
        directoryPanel.add(dirButton);
        directoryPanel.add(defDirButton);
        directoryPanel.setLayout(new GridLayout(0,1));
        directoryFrame.add(directoryPanel, BorderLayout.CENTER);
        /*
            * Building the frame
            */
        directoryFrame.setPreferredSize(new Dimension(400,200));
        directoryFrame.setLocationRelativeTo(null);
        directoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        directoryFrame.setTitle("Choose Default Directory");
        
        
            
    
            
        
        userInputCode.setToolTipText("What kind of barcode would you like?");
        userInput.setToolTipText("Enter text you would like to print (Max 20 char)");
        printerName = barcodeGenerator.ReturnPrinterName();
        //printerIP  = generateBarcode.ReturnPrinterIP();
        printerNameLabel = new JLabel("Printer Name: " + printerName);
        printingLocation = new JLabel("Printing Location: " + barcodeGenerator.getImageSavePath());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        menuBar = new JMenuBar();
        jMenu = new JMenu("Menu");
        menuButton1 = new JMenuItem("Printer Settings");
        menuButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainFrame.dispose();
                directoryFrame.pack();
                directoryFrame.setVisible(true);
            }
        });
        menuButton2 = new JMenuItem("Printer Resources");
        menuButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                openWebpage("https://blueberry.dev");
            }
        });
        menuButton3 = new JMenuItem("About");
        menuButton3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                JOptionPane.showMessageDialog(null,
                            "Blueberry Technologies Barcode Editor.\n© Blueberry Technologies - 2022", "About...",
                            JOptionPane.WARNING_MESSAGE);
            }
        });
        jMenu.add(menuButton1);
        jMenu.add(menuButton2);
        jMenu.add(menuButton3);

        menuBar.add(jMenu);
        mainFrame.setJMenuBar(menuBar);
        
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
                        barcodeGenerator.GenerateBarcodes(" " + userInput.getText(), dropdownChoices[comboBox.getSelectedIndex()]);
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
                    barcodeGenerator.PrintBarcode(barcodeGenerator.getReturnPath(), false);;
                }else if (selectedImage){
                    barcodeGenerator.PrintBarcode(barcodeGenerator.getImageFile(), false);
                    selectedImage = false;
                }else{
                    JOptionPane.showMessageDialog(null,"The code wasn't generated.", "Aborting...",JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        mainFrame.setPreferredSize(new Dimension(300,400));
        mainFrame.setLocationRelativeTo(null);
        
        mainPanel.add(printerNameLabel);
        mainPanel.add(printingLocation);
        mainPanel.add(userInput);
        mainPanel.add(comboBox);
        mainPanel.add(imageButton);
        mainPanel.add(generateButton);
        mainPanel.add(printButton);
        mainPanel.setLayout(new GridLayout(0,1));
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter(){
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
        mainFrame.setTitle("Blueberry Technologies Barcode Editor");
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main (String args[]){
        if(barcodeGenerator.getImageSavePath() == null){
            System.out.println("This is null");
        }else{
            new BlueberryTechBarcodeEditor();
        }
    }
    
    public void actionPerformed(ActionEvent arg0){

    }

    //https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    
    public void openWebpage(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}