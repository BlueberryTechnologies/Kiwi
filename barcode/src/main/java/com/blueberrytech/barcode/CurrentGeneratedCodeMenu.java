/*
 * Blueberry Technologies Barcode Editor
 * Generated Code Menu Class
 * 
 * This class is used for displaying a previously generated code before printing.
 * 
 * The user chooses from a file picker set in the current save directory.
 * 
 * 
 * Last Date Modified: 03/20/2023
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */

package com.blueberrytech.barcode;

// Java Imports
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CurrentGeneratedCodeMenu {

    // Java Swing Variables
    JLabel generatedCodeLabel = new JLabel();
    JFrame generatedCodeFrame = new JFrame();
    JPanel generatedCodePanel = new JPanel();

    // Height and Width Variables for the window
    int height = BarcodeGenerator.getCodeHeight() + 30; // + 30 Pixels for a border.
    int width = BarcodeGenerator.getCodeWidth() + 10; // + 10 Pixels for a border.

    
    ImageIcon icon; // This is where the generated code image will be set to.

    public CurrentGeneratedCodeMenu(ImageIcon icon){ // Generated Code Constructor, takes ImageIcon as a parameter.
        System.out.println("Height: " + height + "\nWidth: " + width);
        renderGeneratedCodeFrame(icon, height, width); // Render Frame method.
    }

    /*
     * This takes an ImageIcon object as a parameter, this is ensuring
     * that an image will be passed and something will generate.
     */
    private void renderGeneratedCodeFrame(ImageIcon icon, int height, int width){
        generatedCodeLabel.setIcon(icon); // Adds the image to the label
        generatedCodeFrame.add(generatedCodePanel); // Adds the Panel to the frame.
        generatedCodeFrame.setSize(new Dimension(width,height)); // This sets the size of the window based on user height and width.
        generatedCodeFrame.setLocationRelativeTo(null); // This makes the window generate in the center of the user's screen.
        generatedCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // When the window is closed, it doesn't terminate the whole program, it only closes the window. If you wanted it to exit the program that would be JFrame.EXIT_ON_CLOSE.
        generatedCodeFrame.setTitle("Generated Code"); // This sets the title of the window.
        generatedCodeFrame.add(generatedCodeLabel); // This adds the label of the image to the frame.
        generatedCodeFrame.setResizable(false); // Doesn't allow the user to resize this window.
        generatedCodeFrame.revalidate(); // Re-validates the frame after information has changed.
        generatedCodeFrame.repaint(); // Re-paints the frame after information has changed.
        generatedCodeFrame.setVisible(true); // Sets the frame visible for the user to see.
    }
}