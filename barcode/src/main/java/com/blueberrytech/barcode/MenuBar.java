package com.blueberrytech.barcode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * Java Swing Imports
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;





/*
 * Java IO Imports
 */

import java.net.URI;
import java.net.URISyntaxException;

/*
 * This class is for the menu bar.
 */

public class MenuBar {
    BlueberryTechBarcodeEditor mainClass = new BlueberryTechBarcodeEditor();
    BlueberryTechBarcodeGenerator mainGenerator = new BlueberryTechBarcodeGenerator();
    GeneratedCodeMenu generatedCodeMenu = new GeneratedCodeMenu();
    ChooseDirectory chooseDirectory = new ChooseDirectory();

    ImageIcon icon;

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
    
    // Generated Code section of the Code Options menu
    JLabel generatedCodeLabel = new JLabel();
    JFrame generatedCodeFrame = new JFrame();
    JPanel generatedCodePanel = new JPanel();


    public MenuBar(){

        /*
         * 
         * ABOUT
         * 
         */
        // Adding the elements to the about section
        about.add(aboutButton);
        about.add(printerResources);
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
                    BlueberryTechBarcodeEditor.openWebpage(website);
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
        settings.add(changeDirectory);
        settings.add(codeOptions);
        settings.add(selectPrinter);
        codeOptions.add(previewGeneratedCodeButton); // Adding a nested menu item for the code options

        changeDirectory.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                disposeMainFrame();
                new ChooseDirectory();
            }
        });

        previewGeneratedCodeButton = new JMenuItem("Preview Generated Code");
        previewGeneratedCodeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                // Code preview
                disposeMainFrame();
                mainGenerator.selectCodeImage();
                icon = new ImageIcon(mainGenerator.getImageFile());
                if(mainGenerator.getCanceledImage()){
                    new BlueberryTechBarcodeEditor();
                }else{
                    new GeneratedCodeMenu();
                }
            }
        });

        // Adding the JMenu elements to the menu bar
        menuBar.add(about);
        menuBar.add(settings);
    }

    private void disposeMainFrame(){
        mainClass.getMainFrame().dispose();
    }
}