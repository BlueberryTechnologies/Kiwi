package com.blueberrytech.barcode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * Java Swing Imports
 */

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;






/*
 * Java IO Imports
 */

import java.net.URI;
import java.net.URISyntaxException;

/*
 * This class is for the menu bar.
 */

public class MenuBar {
    //BarcodeEditor mainClass = new BarcodeEditor();
    BarcodeGenerator mainGenerator = new BarcodeGenerator();
    //ImageIcon icon;

    JMenuBar menuBar = new JMenuBar();

    // The about section of the menu bar
    JMenu about = new JMenu("About"); // Initializing the bar section
    JMenuItem aboutButton = new JMenuItem("About"); // Initializing the element of the bar section
    JMenuItem printerResources = new JMenuItem("Printer Resources");
    
    // This is the settings section of the menu bar
    JMenu settings = new JMenu("Settings");
    JMenu codeOptions = new JMenu("Code Options");
    JMenuItem previewGeneratedCodeButton = new JMenuItem("Preview Generated Code");
    JMenuItem changeDirectory = new JMenuItem("Change Code Directory");
    JMenuItem selectPrinter = new JMenuItem("Select Printer");


    public MenuBar(){

        /*
         * 
         * ABOUT
         * 
         */
        
        // Adding the elements to the about section
        
        // When you click the about element of the about section of the menu bar, this happens:
        aboutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                JOptionPane.showMessageDialog(null,
                            "Blueberry Technologies Barcode Editor.\nVersion 1.0.8\n© Blueberry Technologies, 2022-2023", "About...",
                            JOptionPane.WARNING_MESSAGE);
            }
        });
        // This is the printer resources button added into the about section of the menu bar
        printerResources.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                String websiteString = "https://blueberry.dev/products/barcode-editor/";
                URI website;
                try {
                    website = new URI(websiteString);
                    BarcodeEditor.openWebpage(website);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        
        /*
         * 
         * SETTINGS
         * 
         */
        
        changeDirectory.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                new ChooseDirectory();
            }
        });
        selectPrinter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                new ChoosePrinter();
            }
        });
        
        previewGeneratedCodeButton = new JMenuItem("Preview Generated Code");
        
        codeOptions.add(previewGeneratedCodeButton); // Adding a nested menu item for the code options
        previewGeneratedCodeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // Code preview
                mainGenerator.selectCodeImage();
                ImageIcon icon = new ImageIcon(mainGenerator.getImageFile());
                System.out.println("The button was pressed " + icon);
                if(mainGenerator.getCanceledImage()){
                    //GeneratedCodeMenu.generatedCodeFrame.dispose();
                }else{
                    new GeneratedCodeMenu(icon);
                }
            }
        });
        
        

        

        // Adding the JMenu elements to the menu bar
        about.add(aboutButton);
        about.add(printerResources);
        settings.add(changeDirectory);
        settings.add(codeOptions);
        settings.add(selectPrinter);
        menuBar.add(about);
        menuBar.add(settings);
    }

    public JMenuBar getMenuBar(){
        return menuBar;
    }
}