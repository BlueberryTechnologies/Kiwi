/*
 * Blueberry Technologies Barcode Editor
 * Choose Directory Class
 * 
 * This class is for the user to select a new directory for the barcodes to be generated.
 * There are two options:
 *      > Select custom directory
 *      > Select default directory
 * 
 * The default directory is the user's home directory. This is set on install.
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
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChooseDirectory {

    // Directory Frame and Panel.
    BarcodeGenerator mainGenerator = new BarcodeGenerator(); //  BarcodeGenerator reference as mainGenerator.
    JFrame directoryFrame = new JFrame(); // Creates a new JFrame called directoryFrame.
    JPanel directoryPanel = new JPanel(); // Creates a new JPanel called directoryPanel.

    // Directory buttons.
    JButton dirButton = new JButton("Select Directory"); // Makes a new JButton called dirButton (directoryButton).
    JButton defDirButton = new JButton("Use Default Directory"); // Makes a new JButton called defDirButton (Default Directory Button).

    // Current location label.
    JLabel currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath()); // Creates a new JLabel called currLocation and uses BarcodeGenerator.getImageSavePath() to return the current path to save the generated barcodes.
    

    public ChooseDirectory(){ // ChooseDirectory Constructor.

        // Panel
        directoryPanel.add(currLocation); // Adds the current location label to the panel.
        directoryPanel.add(dirButton); // Adds the directory button to the panel.
        directoryPanel.add(defDirButton); // Adds the default directory button to the panel.
        directoryPanel.setLayout(new GridLayout(0,1)); // Sets the layout of the panel with a stacked grid layout.
        directoryPanel.setSize(new Dimension(400,200)); // Sets the size of the panel.
        
        
        // Frame
        directoryFrame.add(directoryPanel, BorderLayout.CENTER); // Adds the directoryPanel to the directoryFrame with a BorderLayout of CENTER applied. This generates the panel centered to the frame.
        directoryFrame.setSize(new Dimension(400,200)); // Sets the size of the frame.
        directoryFrame.setLocationRelativeTo(null); // Sets the location of the frame centered on the user's screen.
        directoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Sets what happens when the user closes the window. This is dispose not exit so this will not close the program. If you wanted to close the program that would be JFrame.EXIT_ON_CLOSE.
        directoryFrame.setTitle("Choose Default Directory"); // Sets the title of the frame.
        directoryFrame.setVisible(true); // Sets the frame to visible so the user can see it.

        // Adds an Action listener for the directory button when it is clicked.
        dirButton.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent arg0) {
                BarcodeGenerator.setDirectory(false); // Calls a folder picker for the user to select where they want the files to be stored. The parameter is a boolean and it determines if the default file path is used. False is no, the default is not being used, and True is yes the default is being used.
                currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath()); // Sets the JLabel to the current file path.
                directoryPanel.removeAll(); // Removes all from the panel.
                directoryPanel.add(currLocation); // Adds the current location to the panel.
                directoryPanel.add(dirButton); // Adds the directory button back to the panel.
                directoryPanel.add(defDirButton); // Adds the default directory button back to the panel.
                directoryPanel.revalidate(); // Re-validates the panel.
                directoryPanel.repaint(); // Re-paints the panel.
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE); // Shows a JOptionPane saying that the path was changed.
            }
        });
        
        // Adds an action listener to the default directory button.
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                BarcodeGenerator.setDirectory(true); // Sets the directory to true which doesn't prompt the user to select one.
                currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath()); // Sets the label to the current directory.
                directoryPanel.removeAll(); // Removes all from the panel.
                directoryPanel.add(currLocation); // Adds the current location back to the panel.
                directoryPanel.add(dirButton); // Adds the directory button back to the panel.
                directoryPanel.add(defDirButton); // Adds the default directory button back to the panel.
                directoryPanel.revalidate(); // Re-validates the panel.
                directoryPanel.repaint(); // Re-paints the panel.
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE); // Shows a JOptionPane telling the user that the path was changed back to default.
            }
        });

        // Adds a window listener to the frame. So when the frame is closed the main frame is updated with the correct path.
        directoryFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                BarcodeEditor.comboBox.setSelectedIndex(0); // Sets the comboBox to the 0th index (start).
                BarcodeEditor.updateMainFrame(); // Updates the main frame.
            }
        });
    }
}