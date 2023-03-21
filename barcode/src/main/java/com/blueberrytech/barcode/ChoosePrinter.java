/*
 * Blueberry Technologies Barcode Editor
 * Choose Printer Class
 * 
 * This class is used for the UI of selecting a printer then setting
 * the default printer used by the program.
 * 
 * This program chooses from a list of printers determined
 * by the user's computer.
 * 
 * 
 * Last Date Modified: 03/20/2023
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

public class ChoosePrinter {

    // Class references
    BarcodeGenerator mainGenerator = new BarcodeGenerator(); // References the BarcodeGenerator class.
    MenuBar menuBar = new MenuBar(); // References the MenuBar class.

    // Java Swing Variables
    JFrame printerListFrame = new JFrame(); // Creates a new JFrame for the printer UI.
    JPanel printerListPanel = new JPanel(); // Creates a new JPanel for the printerListFrame.
    JLabel currentPrinter = new JLabel(""); // Sets the currentPrinter no an empty string because it is assigned later.

    // JComboBox variables
    String[] printerChoices = BarcodeGenerator.getPrinterServiceNameList().toArray(new String[0]); // This gets the user's printers and creates a pick box for the user to select which printer they would like to use.
    JComboBox<String> printerComboBox = new JComboBox<String>(printerChoices);


    public ChoosePrinter(){ // ChoosePrinter Constructor
        
        renderPrinterFrame(); // Calls renderPrinterFrame to generate and display the frame.
        
        /*
         * Action Listener and WindowListener for the printerFrame and comboBox
         * 
         * The ActionListener updates the frame and the default printer with the user selected choices.
         * The WindowListener listens for when the window is closed and updates the main frame via BarcodeEditor.updateMainFrame()
         */
        printerComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainGenerator.updatePrinter(printerChoices[printerComboBox.getSelectedIndex()]); // Updates the Generator Class with the printer that the user selected.
                printerListPanel.removeAll(); // Removes all objects from the panel.
                currentPrinter = new JLabel("Current Printer: " + BarcodeGenerator.returnPrinterName()); // Sets the currentPrinter label to the current printer from the Barcode Generator class.
                printerListPanel.add(currentPrinter); // Adds the current printer as a JLabel to the panel.
                printerListPanel.add(printerComboBox); // Adds the printerComboBox onto the panel.
                printerListPanel.revalidate(); // Re-validates the frame.
                printerListPanel.repaint(); // Re-paints the frame.
            }
        });

        /*
         * Updates the main frame when the ChoosePrinter window closes.
         */
        printerListFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) // When windowClosing.
            {
                /*
                 * We're updating the BarcodeEditor's current printer here but this is different than
                 * updating the BarcodeGenerator's current printer.
                 * 
                 * The Editor's printer is for the user to see, whereas the generator's printer is for the program to see.
                 * 
                 * BarcodeEditor = UI;
                 * BarcodeGenerator = Program;
                 * 
                 * 
                 * We update the program's printer earlier in this class in the ActionListener.
                 */
                BarcodeEditor.currPrinter = new JLabel("Current Printer: " + BarcodeGenerator.returnPrinterName()); // Sets the BarcodeEditor's current printer to the user selected one.
                BarcodeEditor.updateMainFrame(); // Updates the main frame.
            }
        });
    } // Leaving the constructor.

    private void renderPrinterFrame(){
        /*
         * This is where the printerFrame is generated and rendered for the user to see.
         */

        /*
         * Panel Assignments.
         */
        currentPrinter = new JLabel("Current Printer: " + BarcodeGenerator.returnPrinterName()); // This sets the JLabel to the current printer set by the BarcodeGenerator class.
        printerListPanel.add(currentPrinter); // The currentPrinter JLabel is added to the panel.
        printerListPanel.add(printerComboBox); // The printerComboBox (Printer Selector) is added to the panel.
        printerListPanel.setLayout(new GridLayout(0,1)); // A layout is applied to the panel to have the JLabel on top of the comboBox.
        printerListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Makes a border around the panel and the elements inside.
        
        /*
         * Frame Assignments.
         */
        printerListFrame.add(printerListPanel, BorderLayout.CENTER); // The printerListPanel is added to the frame with a CENTER layout applied. This is going to generate in the center of the window that appears.
        printerListFrame.setSize(new Dimension(400,200)); // Sets the size of the window that is displayed.
        printerListFrame.setLocationRelativeTo(null); // This makes the window centered on the user's screen.
        printerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // When the window is closed, it doesn't terminate the whole program, it only closes the window. If you wanted it to exit the program that would be JFrame.EXIT_ON_CLOSE.
        printerListFrame.setTitle("Printers On Network"); // This sets the title of the window.
        printerListFrame.setVisible(true); // This sets the window visible to the user.
    } // Exit method.
} // End of class.