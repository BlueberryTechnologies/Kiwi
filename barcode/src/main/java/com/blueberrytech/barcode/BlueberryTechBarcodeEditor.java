package com.blueberrytech.barcode;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFileChooser;




/*
 * Java IO Imports
 */

import java.io.File;
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
    static JFrame mainFrame = new JFrame();
    JPanel mainPanel = new JPanel();

    /*
     * Directory Frame and Directory Panel Variables
     */
    
    

    /* */
    JFileChooser chooser = new JFileChooser();
    JLabel printerNameLabel;
    JLabel printingLocation;
    JTextField userInputCode = new JTextField();
    JTextField userInput = new JTextField();
    JTextArea output;
    JScrollPane scrollPane;
    File customFileLocation;
    JButton generateButton = new JButton("Generate Code");
    JButton printButton = new JButton("Print");
    JButton imageButton = new JButton("Select Image");
    JLabel currPrinter;
    ImageIcon icon;

    //MenuBar menuBarClass = new MenuBar();

    

    public BlueberryTechBarcodeEditor(){
        
        final String[] dropdownChoices = { "QR Codes", "CODE128", "AZTEC", "PLAIN TEXT", "IMAGE" };
        final JComboBox<String> comboBox = new JComboBox<String>(dropdownChoices);
        
        
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

        
        
            
        
        userInputCode.setToolTipText("What kind of barcode would you like?");
        userInput.setToolTipText("Enter text you would like to print (Max 20 char)");
        printerName = barcodeGenerator.returnPrinterName();
        //printerIP  = generateBarcode.ReturnPrinterIP();
        printerNameLabel = new JLabel("Printer Name: " + printerName);
        printingLocation = new JLabel("Printing Location: " + barcodeGenerator.getImageSavePath());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));


        


        //mainFrame.setJMenuBar(menuBarClass.menuBar);
        
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

    
    public JFrame getMainFrame(){
        return mainFrame;
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