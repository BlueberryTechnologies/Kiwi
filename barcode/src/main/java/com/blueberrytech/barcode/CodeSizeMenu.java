/*
 * Kiwi Barcode Editor
 * Code Size Menu Class
 * 
 * This class is for specifying the size of the generated code.
 * 
 * 
 * Last Date Modified: 03/25/2023
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */

 package com.blueberrytech.barcode;

 // Java Imports
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.BorderFactory;
 import javax.swing.JComboBox;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import java.awt.event.*;
 
 public class CodeSizeMenu {
 
     // Class references
     BarcodeGenerator mainGenerator = new BarcodeGenerator(); // References the BarcodeGenerator class.
     
     // Java Swing Variables
     JFrame chooseCodeFrame = new JFrame(); // Creates a new JFrame for the chooseCodeFrame.
     JPanel chooseCodePanel = new JPanel(); // Creates a new JPanel for the chooseCodePanel.
     JLabel currentSize = new JLabel(""); // Sets the currentSize no an empty string because it is assigned later.
    JLabel defaultSize = new JLabel("Default Size: 200x200"); // Default Size Label
     // JComboBox variables
     String[] sizeChoices = {"100x100","200x200", "300x300","400x400","500x500","600x600","700x700","800x800","900x900","1000x1000"}; // This gets the user's printers and creates a pick box for the user to select which printer they would like to use.
     JComboBox<String> sizeComboBox = new JComboBox<String>(sizeChoices);
 
 
     public CodeSizeMenu(){ // CodeSizeMenu Constructor
         
         renderCodeSizeFrame(); // Calls renderCodeSizeFrame to generate and display the frame.
         
         /*
          * Action Listener and WindowListener for the chooseCodeFrame and sizeComboBox
          * 
          * The ActionListener updates the frame and the size with the user selected choices.
          * The WindowListener listens for when the window is closed and updates the main frame via BarcodeEditor.updateMainFrame()
          */
        sizeComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(sizeChoices[sizeComboBox.getSelectedIndex()] != "1000x1000"){
                    String currentChoice = sizeChoices[sizeComboBox.getSelectedIndex()];
                    currentChoice = currentChoice.replace("x","");
                    System.out.println("CURRENT CHOICE: " + currentChoice);
                    BarcodeGenerator.setCodeDimensions(Integer.parseInt(currentChoice.substring(0, 3)), Integer.parseInt(currentChoice.substring(3, 6)));   
                }else{
                    String currentChoice = sizeChoices[sizeComboBox.getSelectedIndex()];
                    currentChoice = currentChoice.replace("x","");
                    System.out.println("CURRENT CHOICE: " + currentChoice);
                    BarcodeGenerator.setCodeDimensions(Integer.parseInt(currentChoice.substring(0, 4)), Integer.parseInt(currentChoice.substring(4, 8)));   
                }
                chooseCodePanel.removeAll(); // Removes all objects from the panel.
                currentSize = new JLabel("Current Size: " + BarcodeGenerator.getCodeHeight() + "x" + BarcodeGenerator.getCodeWidth()); // Sets the currentSize label to the current size from the Barcode Generator class.
                chooseCodePanel.add(currentSize); // Adds the currentSize as a JLabel to the panel.
                chooseCodePanel.add(defaultSize); // Adds defaultSize to panel.
                chooseCodePanel.add(sizeComboBox); // Adds the sizeComboBox onto the panel.
                chooseCodeFrame.revalidate(); // Re-validates the frame.
                chooseCodeFrame.repaint(); // Re-paints the frame.

                
            }
        });
 
         /*
          * Updates the main frame when the Choose Code Size window closes.
          */
         chooseCodeFrame.addWindowListener(new WindowAdapter(){
             public void windowClosing(WindowEvent e) // When windowClosing.
             {
                /*
                 * On close we just update the main frame.
                 */
                BarcodeEditor.updateMainFrame(); // Updates the main frame.
             }
         });
     } // Leaving the constructor.
 
     private void renderCodeSizeFrame(){
         /*
          * This is where the chooseCodeFrame is generated and rendered for the user to see.
          */
 
         /*
          * Panel Assignments.
          */
         currentSize = new JLabel("Current Size: " + BarcodeGenerator.getCodeHeight() + "x" + BarcodeGenerator.getCodeWidth()); // This sets the JLabel to the current size set by the BarcodeGenerator class.
         chooseCodePanel.add(currentSize); // The currentSize JLabel is added to the panel.
         chooseCodePanel.add(defaultSize); // Adds defaultSize to panel.
         sizeComboBox.setSelectedItem(BarcodeGenerator.getCodeHeight() + "x" + BarcodeGenerator.getCodeWidth());
         chooseCodePanel.add(sizeComboBox); // The sizeComboBox (Size Selector) is added to the panel.
         chooseCodePanel.setLayout(new GridLayout(0,1)); // A layout is applied to the panel to have the JLabel on top of the comboBox.
         chooseCodePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Makes a border around the panel and the elements inside.
         
         /*
          * Frame Assignments.
          */
         chooseCodeFrame.add(chooseCodePanel, BorderLayout.CENTER); // The chooseCodePanel is added to the frame with a CENTER layout applied. This is going to generate in the center of the window that appears.
         chooseCodeFrame.setSize(new Dimension(400,200)); // Sets the size of the window that is displayed.
         chooseCodeFrame.setLocationRelativeTo(null); // This makes the window centered on the user's screen.
         chooseCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // When the window is closed, it doesn't terminate the whole program, it only closes the window. If you wanted it to exit the program that would be JFrame.EXIT_ON_CLOSE.
         chooseCodeFrame.setTitle("Set Generated Code Size"); // This sets the title of the window.
         chooseCodeFrame.setVisible(true); // This sets the window visible to the user.
     } // Exit method.
 } // End of class.