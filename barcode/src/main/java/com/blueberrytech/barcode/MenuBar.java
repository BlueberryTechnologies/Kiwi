/*
 * Kiwi Barcode Editor
 * Menu Bar Class
 * 
 * This class is for the menu bar shown above the main frame.
 * This houses all of the Settings and the About section.
 * 
 * 
 * Last Date Modified: 01/25/2025
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */
package com.blueberrytech.barcode;

// Java Imports

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JMenuItem previewCurrentCode = new JMenuItem("Preview Current Code");
    JMenuItem previewPreviouslyGeneratedCodeButton = new JMenuItem("Preview Previously Generated Codes"); // This is a JMenuItem for previewing generated barcodes.
    JMenuItem selectCodeSize = new JMenuItem("Change Code Size"); // This is a JMenuItem for previewing generated barcodes.
    JMenuItem changeDirectory = new JMenuItem("Change Code Directory"); // This is a JMenuItem for changing the current directory to save the images of the generated barcodes.
    JMenuItem selectPrinter = new JMenuItem("Select Printer"); // This is a JMenuItem for selecting a printer to print to.

    String latestVersion = "1.1"; // This is a string of the latest version

    public MenuBar(){ // Menu Bar constructor

        // This is an action listener for the about button inside the about container on the menu bar.
        aboutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // This creates a JOptionPane giving about information of the program.
                JOptionPane.showMessageDialog(null, "Kiwi Barcode Editor.\nVersion " + latestVersion + "\n© Blueberry Technologies, 2022-2025", "About...", JOptionPane.WARNING_MESSAGE);
            }
        });

        // This is an action listener for the printer resources button inside of the about container on the menu bar.
        printerResources.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                String websiteString = "https://github.com/BlueberryTechnologies/Kiwi/wiki"; // This sets a string to where the user is going to be taken for printer resources.
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
        previewPreviouslyGeneratedCodeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainGenerator.selectCodeImage(); // Prompts the user with a file explorer having them pick an image (code) they would like displayed. This defaults to the current directory set in the BarcodeGenerator class.
                ImageIcon icon = new ImageIcon(BarcodeGenerator.getImageFile()); // Makes a new ImageIcon and sets it to the image that was selected by the user.
                if(!mainGenerator.getCanceledImage()){ // If the image selection was not canceled then display the code menu using a new instance of GeneratedCodeMenu() and icon as a parameter.
                    new CurrentGeneratedCodeMenu(icon);
                }
                icon.getImage().flush();
            }
        });

        previewCurrentCode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // When the user types into the box to generate a code,
                // this will give a real time look onto what the code will look like.

                // This can be achieved by generating a new code and storing it as a temp
                // value each time the user types a new char into the box.

                // The new code will then be shown onto a box and refreshed and deleted
                // everytime there is a new char.
            }
        });

        selectCodeSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0){
                new CodeSizeMenu();
            }
        });
        
        // Adding the JMenu elements to the menu bar
        codeOptions.add(changeDirectory); // Adds the change directory button to the code options container.
        codeOptions.add(previewCurrentCode);
        codeOptions.add(previewPreviouslyGeneratedCodeButton); // Adding a nested menu item for the code options
        codeOptions.add(selectCodeSize); // Adds selectCodeSize to the codeOptions container.
        about.add(aboutButton); // Adds the about button to the about container.
        about.add(printerResources); // Adds the printer resources to the about container.
        settings.add(codeOptions); // Adds the code options container to the settings container.
        settings.add(selectPrinter); // Adds the select printer button to the settings container.
        menuBar.add(about); // Adds the about container to the menu bar.
        menuBar.add(settings); // Adds the settings container to the menu bar.
    }

    public JMenuBar getMenuBar(){ // This is a method to return the menu bar object.
        return menuBar; // This returns the menu bar in the current scope as a JMenuBar object.
    }
}