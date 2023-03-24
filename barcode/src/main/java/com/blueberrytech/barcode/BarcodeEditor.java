/*
 * Blueberry Technologies Barcode Editor
 * Barcode Editor Class
 * 
 * This class generates the main UI for the program.
 * This houses mostly user input but does reference the main program class called BarcodeGenerator.
 * 
 * Last Date Modified: 03/23/2023
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */
package com.blueberrytech.barcode;

// Java Imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;




public class BarcodeEditor{ // Implements ActionListener

    // Referencing Barcode Generator Class
    static BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    // Frame & Panel
    static JFrame mainFrame = new JFrame(); // Creates the mainFrame.
    static JPanel mainPanel = new JPanel(); // Creates the mainPanel.
    static JButton generateButton = new JButton("Generate Code"); // Makes a button for generating the code.
    static JButton printButton = new JButton("Print"); // Makes a button for printing the codes.
    static JButton imageButton = new JButton("Select Image"); // Makes a button for selecting an image.
    static JTextField userInputCode = new JTextField(); // Makes a new JTextField for userInput code.
    static JTextField userInput = new JTextField(); // Makes a new JTextField for user input.
    static JLabel printerNameLabel; // Makes a new JLabel for the printer name.
    static JLabel printingLocation; // Makes a new JLabel for the printer location.
    static JLabel currPrinter; // Makes a new JLabel for the current printer.
    String printerName = ""; // A string for the current printer name.
    boolean isGenerated = false; // A boolean for if the code is generated or not.
    boolean selectedImage = false; // A boolean for if the image is selected.
    
    
    MenuBar menuBarClass = new MenuBar(); // Makes a reference to the menuBar class.
    
    final static String[] dropdownChoices = { "QR Codes", "CODE128", "AZTEC", "PLAIN TEXT", "IMAGE" }; // An array of the choices of things the user can print.
    final static JComboBox<String> comboBox = new JComboBox<String>(dropdownChoices); // Makes a comboBox of the choices listed above.
    
    public BarcodeEditor(){ // BarcodeEditor Constructor.
        
        
        
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
        printerName = BarcodeGenerator.returnPrinterName();
        //printerIP  = generateBarcode.ReturnPrinterIP();
        printerNameLabel = new JLabel("Printer Name: " + printerName);
        printingLocation = new JLabel("Printing Location: " + BarcodeGenerator.getImageSavePath());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));


        

        mainFrame.setJMenuBar(menuBarClass.getMenuBar());
        
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
                    if (!BarcodeGenerator.selectedImage){
                        JOptionPane.showMessageDialog(null, "An Image Was Not Selected", "Aborting...",JOptionPane.WARNING_MESSAGE);
                    }else{
                        barcodeGenerator.PrintBarcode(BarcodeGenerator.getImageFile(), false);
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
        mainFrame.setTitle("Blueberry Technologies Barcode Editor");
        mainFrame.pack();
        mainFrame.setVisible(true);
    } // End of constructor.

    public static void main (String args[]){
        barcodeGenerator.setInitialPrinter();
        if(BarcodeGenerator.getImageSavePath() == null){
            System.out.println("This is null");
        }else{
            new BarcodeEditor();
        }
    }

    public static JFrame getMainFrame(){
        return mainFrame;
    }
    public static void updateMainFrame(){
        mainPanel.removeAll();
        printingLocation = new JLabel("Printing Location: " + BarcodeGenerator.getImageSavePath());
        
        mainPanel.add(printerNameLabel);
        mainPanel.add(printingLocation);
        
        comboBox.setSelectedIndex(0);
        mainPanel.add(comboBox);
        mainPanel.add(userInput);
        //mainPanel.add(imageButton);
        mainPanel.add(generateButton);
        mainPanel.add(printButton);
        mainPanel.setLayout(new GridLayout(0,1));
        
        mainPanel.revalidate();
        mainPanel.repaint();
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