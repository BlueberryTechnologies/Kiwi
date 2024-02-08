/*
 * Blueberry Technologies Barcode Editor
 * Menu Bar Class
 * 
 * This class is for the menu bar shown above the main frame.
 * This houses all of the Settings and the About section.
 * 
 * 
 * Last Date Modified: 03/20/2023
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */

package com.blueberrytech.barcode;

// Java Imports

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuBar {

    BarcodeGenerator mainGenerator = new BarcodeGenerator();  // BarcodeGenerator Reference

    JMenuBar menuBar = new JMenuBar(); // Creates a new MenuBar Object.

    /*
     * ABOUT SECTION OF THE MENU BAR
     */
    JMenu about = new JMenu("About"); // Initializing the about section as a JMenu Component.
    JMenuItem aboutButton = new JMenuItem("About"); // Initializing the about section as a JMenuItem Component.
    JMenuItem printerResources = new JMenuItem("Printer Resources"); // Initializing the about section as a JMenuItem Component.
    
    /*
     * SETTINGS SECTION OF THE MENU BAR
     */
    JMenu settings = new JMenu("Settings"); // Initialize the settings portion of the menu bar.
    JMenu codeOptions = new JMenu("Code Options"); // Initialize the code options portion of the menu bar, this will be used as a nested menu bar inside of the settings bar.
    JMenuItem previewGeneratedCodeButton = new JMenuItem("Preview Generated Code"); // This is a JMenuItem for previewing generated barcodes.
    JMenuItem selectCodeSize = new JMenuItem("Change Code Size"); // This is a JMenuItem for previewing generated barcodes.
    JMenuItem changeDirectory = new JMenuItem("Change Code Directory"); // This is a JMenuItem for changing the current directory to save the images of the generated barcodes.
    JMenuItem selectPrinter = new JMenuItem("Select Printer"); // This is a JMenuItem for selecting a printer to print to.

    // Generated Code History
    JMenuItem generatedCodeHistory = new JMenuItem("Generated Code History");

    String latestVersion = "1.0.0"; // This is a string of the latest version.
    /*   After 1.0.9 the version scheme has changed. We are using semantic versioning now.
    *         1.0.000
    *    Major.Minor.Patch
    * 
    *  The Major is for major changes in the program. (New features, UI changes, etc).
    *  Minor is for tiny additions. (This can include minor features, bug fixes, etc).
    *  Patch is for important bug fixes / features that were supposed to be added but were forgotten.
    *
    *  We are also renaming the previous versions to Beta Builds. They will be b1.0.0 - b1.0.9.
    *  03/29/2023
    */

    public MenuBar(){ // Menu Bar constructor

        // This is an action listener for the about button inside of the about container on the menu bar.
        aboutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // This creates a JOptionPane giving about information of the program.
                JOptionPane.showMessageDialog(null, "Blueberry Technologies Barcode Editor.\nVersion " + latestVersion + "\n© Blueberry Technologies, 2022-2023", "About...", JOptionPane.WARNING_MESSAGE);
            }
        });

        // This is an action listener for the printer resources button inside of the about container on the menu bar.
        printerResources.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                String websiteString = "https://github.com/BlueberryTechnologies/Blueberry-Tech-Barcode-Editor/wiki"; // This sets a string to where the user is going to be taken for printer resources.
                URI website; // This makes a new URI called website.
                try { // This is a try/catch for opening a webpage.
                    website = new URI(websiteString); // Sets website to websiteString but then makes it into a URI.
                    BarcodeEditor.openWebpage(website); // This calls openWebpage using the website URI.
                } catch (URISyntaxException e) { // If breaks then catch.
                    e.printStackTrace(); // And prints the stack trace.
                }
            }
        });
        
        // This is an action listener for the change directory button in the settings container on the menu bar.
        changeDirectory.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                new ChooseDirectory(); // This calls a new instance of ChooseDirectory() using the constructor.
            }
        });

        // This is an action listener for the select printer button in the settings container on the menu bar.
        selectPrinter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                new ChoosePrinter(); // This calls a new instance of ChoosePrinter() using the constructor.
            }
        });
        
        
        // This is an action listener for the preview generated code button in the settings container on the menu bar.
        previewGeneratedCodeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainGenerator.selectCodeImage(); // Prompts the user with a file explorer having them pick an image (code) they would like displayed. This defaults to the current directory set in the BarcodeGenerator class.
                ImageIcon icon = new ImageIcon(BarcodeGenerator.getImageFile()); // Makes a new ImageIcon and sets it to the image that was selected by the user.
                if(!mainGenerator.getCanceledImage()){ // If the image selection was not canceled then display the code menu using a new instance of GeneratedCodeMenu() and icon as a parameter.
                    new GeneratedCodeMenu(icon);
                }
                icon.getImage().flush();
            }
        });

        selectCodeSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0){
                new CodeSizeMenu();
            }
        });
        
        // Adding the JMenu elements to the menu bar
        codeOptions.add(changeDirectory); // Adds the change directory button to the code options container.
        codeOptions.add(previewGeneratedCodeButton); // Adding a nested menu item for the code options
        codeOptions.add(selectCodeSize); // Adds selectCodeSize to the codeOptions container.
        about.add(aboutButton); // Adds the about button to the about container.
        about.add(printerResources); // Adds the printer resources to the about container.
        settings.add(codeOptions); // Adds the code options container to the settings container.
        settings.add(selectPrinter); // Adds the select printer button to the settings container.
        settings.add(generatedCodeHistory); // Adds the generated code history section to the menu bar.
        menuBar.add(about); // Adds the about container to the menu bar.
        menuBar.add(settings); // Adds the settings container to the menu bar.
    }

    /*
     * Previous Generated Code List as Menu Bar items.
     */

    private void updatePreviousGeneratedCode(){

    }

    public static void initializeDefaultGeneratedCodeHistory(){
        FileWriter writeCodeHistory;
        File historyTxt = new File(BarcodeGenerator.getDefaultDirectory() + "/history.txt");
        try {
            if (!historyTxt.exists()){
                writeCodeHistory = new FileWriter(historyTxt);
                writeCodeHistory.close();
            }else{
                System.out.println("The default history file exists");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeGeneratedCodeHistory(String pathOfCode){
        FileWriter writeCodeHistory;
        File historyTxt = new File(BarcodeGenerator.getDefaultDirectory() + "/history.txt");
        
        try{
            if(historyTxt.exists()){
                writeCodeHistory = new FileWriter(historyTxt);
                writeCodeHistory.append(pathOfCode);
                writeToMenuBar();
            }else{
                System.out.println("The history path was attempted to be created and it hasn't.");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void writeToMenuBar(){ // Writes the generated code history to the menu bar from a file.
        File historyTxt = new File(BarcodeGenerator.getDefaultDirectory() + "/history.txt"); // Takes the file.
        BufferedReader readingHistoryFile; // Makes a reader.
        try{
            readingHistoryFile = new BufferedReader (new FileReader(historyTxt)); // Sets the reader.
            String[] menuBarItems = new String[(int) historyTxt.length() /* Casting int to long */]; // Making a fixed array to the length of the txt file.
            for (int i = 0; i < historyTxt.length(); i++){ // Loops through the text file.
                try{
                    menuBarItems[i] = readingHistoryFile.readLine(); // Every line (every code) is set to a place in the array.
                    //JMenuItem i = new JMenuItem("d");
                }
                catch(IOException e){
                    e.printStackTrace(); // Prints errors.
                }
            }
        }catch(IOException e){
            e.printStackTrace(); // Prints errors.
        }
    }

    public JMenuBar getMenuBar(){ // This is a method to return the menu bar object.
        return menuBar; // This returns the menu bar in the current scope as a JMenuBar object.
    }
}