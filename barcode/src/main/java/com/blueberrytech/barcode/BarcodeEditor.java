/*
 * Blueberry Technologies Barcode Editor
 * Barcode Editor Class
 * 
 * This class generates the main UI for the program.
 * This houses mostly user input but does reference the main program class called BarcodeGenerator.
 * 
 * Last Date Modified: 03/24/2023
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
import javax.swing.SwingUtilities;



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

        /*
         * 
         *  ACTION LISTENERS
         * 
         */
        
        imageButton.addActionListener(new ActionListener(){ // ActionListener for the image button.
            public void actionPerformed(ActionEvent arg0) {
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){ // Checks if the comboBox is selected on IMAGE.
                    barcodeGenerator.selectImage(); // Then prompts the user to select an image.
                }
            }
        });

        comboBox.addActionListener(new ActionListener(){ // ActionListener for comboBox.
            public void actionPerformed(ActionEvent arg0){
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){ // When the comboBox is selected on IMAGE.
                    mainPanel.remove(userInput); // Removes userInput from mainPanel.
                    mainPanel.remove(generateButton); // Removes generateButton from mainPanel.
                    mainPanel.remove(printButton); // Removes printButton from mainPanel.
                    mainPanel.add(imageButton); // Adds image button to the mainPanel.
                    mainPanel.add(printButton); // Adds printButton to mainPanel.
                    mainPanel.revalidate(); // Re-validates the mainPanel.
                    mainPanel.repaint(); // Re-paints the mainPanel.
                }else if (dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){ // When the comboBos is selected on PLAIN TEXT.
                    mainPanel.remove(imageButton); // imageButton is removed.
                    mainPanel.remove(printButton); // printButton is removed.

                    mainPanel.add(userInput); // userInput is added to the mainPanel.
                    generateButton.setText("Print Text"); // The text on the generateButton is changed to print text.
                    mainPanel.add(generateButton); // The generateButton is added to the mainPanel.
                    
                    mainPanel.revalidate(); // Re-validate panel.
                    mainPanel.repaint(); // Re-paint panel.
                }else if (!dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")){ // If the comboBox is anything but image.
                    mainPanel.remove(imageButton); // imageButton is removed.
                    mainPanel.remove(printButton); // printButton is removed.

                    mainPanel.add(userInput); // userInput is added.
                    generateButton.setText("Generate Code"); // The generateButton's text is set to Generate Code.
                    mainPanel.add(generateButton); // generateButton is added.
                    mainPanel.add(printButton); // printButton is added.
                    mainPanel.revalidate(); // Re-validate panel.
                    mainPanel.repaint(); // Re-paint panel.
                }else {
                    JOptionPane.showMessageDialog(null,"Please use the dropdown box and select image.", "Aborting...",JOptionPane.WARNING_MESSAGE); // If something else is selected.
                }
            }
        });

        generateButton.addActionListener(new ActionListener(){ // ActionListener for generateButton
            public void actionPerformed(ActionEvent arg0) {
                if(!dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE") && !selectedImage){ // If IMAGE is not selected in the comboBox and and Image hasn't been selected.
                    if(!barcodeGenerator.checkIfTextValid(userInput.getText())){ // Checks if the text has valid characters if not then displays JOptionPane.
                        JOptionPane.showMessageDialog(null,
                            "Please enter a valid file name.\n[A-Z, 0-9, _:/-.]", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);    
                    }else{
                        if(userInput.getText().length() > 255 && !dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){ // If the text is valid and the comboBox is not selected on PLAIN TEXT and the text entered exceeds 255 characters, then shows JOptionPane showing too many characters
                            JOptionPane.showMessageDialog(null,
                                "The amount of characters exceeds 255.", "Aborting...",
                                JOptionPane.WARNING_MESSAGE);
                        }else if (userInput.getText().length() > 50 && dropdownChoices[comboBox.getSelectedIndex()].equals("PLAIN TEXT")){ // If the text is valid and the comboBox is selected on PLAIN TEXT and the text exceeds 50 characters then a JOptionPane is displayed.
                            JOptionPane.showMessageDialog(null,
                            "The amount of characters exceeds 50.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                        }else{ // A code is generated if else
                            barcodeGenerator.GenerateBarcodes(" " + userInput.getText(), dropdownChoices[comboBox.getSelectedIndex()]); // Generates a code and displays the path to the user.
                            isGenerated = true; // isGenerated is set to true when a code is generated.
                        }
                    }
                }else{ // When nothing is entered in the userInput box then a JOptionPane is displayed.
                    JOptionPane.showMessageDialog(null,
                            "There is nothing to generate.", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                }
                
            }
        });

        printButton.addActionListener(new ActionListener(){ // ActionListener for printButton
            public void actionPerformed(ActionEvent arg0) {
                if (isGenerated && !selectedImage){ // If a code is generated and an image is not selected
                    barcodeGenerator.PrintBarcode(barcodeGenerator.getReturnPath(), false); // A code is printed with the return path and if the contents is plain text as parameters.
                }else if (!isGenerated && !dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE") && !selectedImage){ // If the code is not generated and the comboBox is selected on image and and image hasn't been selected.
                    JOptionPane.showMessageDialog(null,"The Code Was Not Generated.", "Aborting...",JOptionPane.ERROR_MESSAGE); // Code was not generated JOptionPane
                }
                if (dropdownChoices[comboBox.getSelectedIndex()].equals("IMAGE")) { // If comboBox is selected on image.
                    if (!BarcodeGenerator.selectedImage){ // If an image was not selected
                        JOptionPane.showMessageDialog(null, "An Image Was Not Selected", "Aborting...",JOptionPane.ERROR_MESSAGE); // Tell the user that an image was not selected with a JOptionPane
                    }else{ // If an image was selected
                        barcodeGenerator.PrintBarcode(BarcodeGenerator.getImageFile(), false); // Print the image
                        selectedImage = false; // Reset the boolean for the next image.
                    }
                }
            }
        });

        /*
         * 
         * END OF ACTION LISTENERS
         * 
         * 
         */

        
        
            
        
        userInput.setToolTipText("Enter text you would like to print (Max 20 char)"); // Sets a tooltip for the userInput text box.
        printerName = BarcodeGenerator.returnPrinterName(); // Sets the printer name string with the printer returned from BarcodeGenerator.
        printerNameLabel = new JLabel("Printer Name: " + printerName); // Assigns a label with the printerName.
        printingLocation = new JLabel("Printing Location: " + BarcodeGenerator.getImageSavePath()); // Assigns a label with the printing location.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Makes a border for the mainPanel.
        mainFrame.setJMenuBar(menuBarClass.getMenuBar()); // Sets the menuBar of the mainFrame with the menuBar from the menuBarClass.
        
        mainFrame.setPreferredSize(new Dimension(400,400)); // Sets the preferred size of the mainFrame.
        mainFrame.setLocationRelativeTo(null); // Makes the frame centered on the user's screen.
        
        mainPanel.add(printerNameLabel); // Add the printerNameLabel to the panel.
        mainPanel.add(printingLocation); // Add the printing location to the panel.
        
        mainPanel.add(comboBox); // Add the comboBox to the panel.
        mainPanel.add(userInput); // Add the userInput to the panel.
        mainPanel.add(generateButton); // Add the generateButton to the panel.
        mainPanel.add(printButton); // Add the printButton to the panel.
        mainPanel.setLayout(new GridLayout(0,1)); // Sets the layout of the panel so things are stacked.
        mainFrame.add(mainPanel, BorderLayout.CENTER); // Add the panel to the frame with a center layout.
        
        
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set the default behavior for closing a window to disposing not exiting.
        mainFrame.setTitle("Blueberry Technologies Barcode Editor"); // Set the default title for the mainFrame
        mainFrame.pack(); // Pack everything close together in the frame.
        mainFrame.setVisible(true); // Set the frame to visible so the user can see.
    } // End of constructor.



    /*
     * METHODS
     */

    public static JFrame getMainFrame(){ // Returns the mainFrame
        return mainFrame; // Return
    }

    public static void updateMainFrame(){ // Updates the mainFrame
        mainPanel.removeAll(); // Removes everything from the frame
        printingLocation = new JLabel("Printing Location: " + BarcodeGenerator.getImageSavePath()); // Re-gets the image path.
        printerNameLabel = new JLabel("Current Printer: " + BarcodeGenerator.returnPrinterName()); // Sets the BarcodeEditor's current printer to the user selected one.
        mainPanel.add(printerNameLabel); // Adds the printerName to the mainPanel
        mainPanel.add(printingLocation); // Adds the printing location to the mainPanel
        
        comboBox.setSelectedIndex(0); // Sets the comboBox back to the default position of 0
        mainPanel.add(comboBox); // Adds the comboBox to the mainPanel
        mainPanel.add(userInput); // Adds the userInput box to the mainPanel
        mainPanel.add(generateButton); // Adds the generateButton to the mainPanel
        mainPanel.add(printButton); // Adds the printButton to the mainPanel
        mainPanel.setLayout(new GridLayout(0,1)); //  Sets the layout of the panel so things are stacked.
        
        mainPanel.revalidate(); // Re-validates panel
        mainPanel.repaint(); // Re-paints panel.
    }

    /*
     * 
     * END OF METHODS
     * 
     */

    /*
     * 
     * OPEN WEBPAGE
     * //https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
     * 
     */
    
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