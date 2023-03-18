package com.blueberrytech.barcode;

/*
 * Awt + Misc Imports
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.imageio.ImageIO;

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
import java.util.ArrayList;


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
    /*
     * Printer Frame and Panel
     */
    JFrame printerListFrame = new JFrame();
    JPanel printerListPanel = new JPanel();
    JTextArea printerList = new JTextArea();

    /* Generated Code Image Frame and Panel */
    JFrame generatedCodeFrame = new JFrame();
    JPanel generatedCodePanel = new JPanel();

    /* */
    JFileChooser chooser = new JFileChooser();
    JLabel currLocation = new JLabel(" Current Location: " + barcodeGenerator.getImageSavePath());
    JLabel printerNameLabel;
    JLabel printingLocation;
    JTextField userInputCode = new JTextField();
    JTextField userInput = new JTextField();
    JTextArea output;
    JScrollPane scrollPane;
    JMenu settingsMenu;
    JMenu about;
    JMenu codeOptions;
    JMenuBar menuBar;
    JMenuItem changeDirectoryButton, printerResourcesButton, selectPrinterButton, aboutButton, previewGeneratedCodeButton;
    File customFileLocation;
    JButton generateButton = new JButton("Generate Code");
    JButton printButton = new JButton("Print");
    JButton imageButton = new JButton("Select Image");
    JLabel currPrinter;
    ImageIcon icon;

    public BlueberryTechBarcodeEditor(){
        
        final String[] dropdownChoices = { "QR Codes", "CODE128", "AZTEC", "PLAIN TEXT", "IMAGE" };
        final JComboBox<String> comboBox = new JComboBox<String>(dropdownChoices);
        
        final String[] printerChoices = barcodeGenerator.getPrinterServiceNameList().toArray(new String[0]);
        final JComboBox<String> printerComboBox = new JComboBox<String>(printerChoices);

        imageButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){
                    barcodeGenerator.selectImage();
                }
            }
        });
        comboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){
                    mainPanel.remove(userInput);
                    mainPanel.remove(generateButton);
                    mainPanel.remove(printButton);
                    
                    mainPanel.add(imageButton);
                    mainPanel.add(printButton);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }else if (dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){
                    mainPanel.remove(imageButton);
                    mainPanel.remove(printButton);

                    mainPanel.add(userInput);
                    generateButton.setText("Print Text");
                    mainPanel.add(generateButton);
                    
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }else if (!dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){
                    mainPanel.remove(imageButton);
                    mainPanel.remove(printButton);

                    mainPanel.add(userInput);
                    generateButton.setText("Generate Code");
                    mainPanel.add(generateButton);
                    mainPanel.add(printButton);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }else {
                    JOptionPane.showMessageDialog(null,
                            "Please use the dropdown box and select image.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        final JButton dirButton = new JButton("Select Directory");
        final JButton defDirButton = new JButton("Use Default Directory");
        dirButton.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent arg0) {
                barcodeGenerator.setDirectory(false);
                currLocation = new JLabel(" Current Location: " + barcodeGenerator.getImageSavePath());
                directoryPanel.removeAll();
                directoryPanel.add(currLocation);
                directoryPanel.add(dirButton);
                directoryPanel.add(defDirButton);
                directoryPanel.revalidate();
                directoryPanel.repaint();
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE);
            }
        });
        
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                barcodeGenerator.setDirectory(true);
                currLocation = new JLabel(" Current Location: " + barcodeGenerator.getImageSavePath());
                directoryPanel.removeAll();
                directoryPanel.add(currLocation);
                directoryPanel.add(dirButton);
                directoryPanel.add(defDirButton);
                directoryPanel.revalidate();
                directoryPanel.repaint();
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
        
        /*
         * 
         * 
         * 
         * 
         * 
         * 
         */

        



        printerListFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                new BlueberryTechBarcodeEditor();
            }
        });
        
            
        
        userInputCode.setToolTipText("What kind of barcode would you like?");
        userInput.setToolTipText("Enter text you would like to print (Max 20 char)");
        printerName = barcodeGenerator.returnPrinterName();
        //printerIP  = generateBarcode.ReturnPrinterIP();
        printerNameLabel = new JLabel("Printer Name: " + printerName);
        printingLocation = new JLabel("Printing Location: " + barcodeGenerator.getImageSavePath());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        menuBar = new JMenuBar();
        settingsMenu = new JMenu("Settings");
        about = new JMenu("About");
        aboutButton = new JMenuItem("About");
        codeOptions = new JMenu("Code Options");
        aboutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                JOptionPane.showMessageDialog(null,
                            "Blueberry Technologies Barcode Editor.\nVersion 1.0.8\n© Blueberry Technologies, 2022-2023", "About...",
                            JOptionPane.WARNING_MESSAGE);
            }
        });
        changeDirectoryButton = new JMenuItem("Change Code Directory");
        changeDirectoryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainFrame.dispose();
                directoryFrame.pack();
                directoryFrame.setVisible(true);
            }
        });
        previewGeneratedCodeButton = new JMenuItem("Preview Generated Code");
        previewGeneratedCodeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // Code preview
                mainFrame.dispose();
                barcodeGenerator.selectCodeImage();
                icon = new ImageIcon(barcodeGenerator.getImageFile());
                if(barcodeGenerator.getCanceledImage()){
                    new BlueberryTechBarcodeEditor();
                }else{
                    JLabel generatedCodeLabel = new JLabel();
                    generatedCodeLabel.setIcon(icon);
                    generatedCodeFrame.add(generatedCodePanel);
                    generatedCodeFrame.setSize(new Dimension(210,230));
                    generatedCodeFrame.setLocationRelativeTo(null);
                    generatedCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    generatedCodeFrame.setTitle("Generated Code");
                    generatedCodeFrame.add(generatedCodeLabel);
                    generatedCodeFrame.setResizable(false);
                    generatedCodeFrame.revalidate();
                    generatedCodeFrame.repaint();
                    generatedCodeFrame.setVisible(true);
                }
            }
        });
        generatedCodeFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                new BlueberryTechBarcodeEditor();
            }
        });
        printerResourcesButton = new JMenuItem("Printer Resources");
        printerResourcesButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                String websiteString = "https://blueberry.dev/products/barcode-editor/";
                URI website;
                try {
                    website = new URI(websiteString);
                    openWebpage(website);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                
                
            }
        });

        selectPrinterButton = new JMenuItem("Select Printer");
        printerListFrame.setPreferredSize(new Dimension(400,200));
        printerListFrame.setLocationRelativeTo(null);
        printerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        printerListFrame.setTitle("Printers On Network");
        selectPrinterButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainFrame.dispose();
                currPrinter = new JLabel("Current Printer: " + barcodeGenerator.returnPrinterName());
                printerListPanel.add(currPrinter);
                printerListPanel.add(printerComboBox);
                printerListFrame.add(printerListPanel, BorderLayout.CENTER);
                printerListFrame.pack();
                printerListPanel.setLayout(new GridLayout(0,1));
                printerListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
                printerListFrame.setVisible(true);
            }
        });
        
        printerComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                barcodeGenerator.updatePrinter(printerChoices[printerComboBox.getSelectedIndex()]);
                System.out.println("Changed values");
                printerListPanel.removeAll();
                currPrinter = new JLabel("Current Printer: " + barcodeGenerator.returnPrinterName());
                printerListPanel.add(currPrinter);
                printerListPanel.add(printerComboBox);
                printerListPanel.revalidate();
                printerListPanel.repaint();
            }
        });

        settingsMenu.add(changeDirectoryButton);
        about.add(printerResourcesButton);
        settingsMenu.add(selectPrinterButton);
        settingsMenu.add(codeOptions);
        about.add(aboutButton);
        codeOptions.add(previewGeneratedCodeButton);

        menuBar.add(about);
        menuBar.add(settingsMenu);
        //menuBar.add(codeOptions);
        mainFrame.setJMenuBar(menuBar);
        
        generateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                if(!dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE") && !selectedImage){
                    if(!barcodeGenerator.checkIfTextValid(userInput.getText())){
                        JOptionPane.showMessageDialog(null,
                            "Please enter a valid file name.\n[A-Z, 0-9, _]", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);    
                    }else{
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
                }else if (!isGenerated && !dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE") && !selectedImage){
                    JOptionPane.showMessageDialog(null,"The Code Was Not Generated.", "Aborting...",JOptionPane.WARNING_MESSAGE);
                }
                
                
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")) {
                    if (!barcodeGenerator.selectedImage){
                        JOptionPane.showMessageDialog(null, "An Image Was Not Selected", "Aborting...",JOptionPane.WARNING_MESSAGE);
                    }else{
                        barcodeGenerator.PrintBarcode(barcodeGenerator.getImageFile(), false);
                        selectedImage = false; // No image was selected
                    }
                }
            }
        });
        
        mainFrame.setPreferredSize(new Dimension(400,400));
        mainFrame.setLocationRelativeTo(null);
        
        mainPanel.add(printerNameLabel);
        mainPanel.add(printingLocation);
        
        mainPanel.add(comboBox);
        mainPanel.add(userInput);
        //mainPanel.add(imageButton);
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
        barcodeGenerator.setInitialPrinter();
        if(barcodeGenerator.getImageSavePath() == null){
            System.out.println("This is null");
        }else{
            new BlueberryTechBarcodeEditor();
        }
    }
    
    public void actionPerformed(ActionEvent arg0){

    }

    //https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}