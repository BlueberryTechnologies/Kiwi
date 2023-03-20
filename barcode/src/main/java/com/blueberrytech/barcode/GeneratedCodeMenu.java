package com.blueberrytech.barcode;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GeneratedCodeMenu {
    JLabel generatedCodeLabel = new JLabel();
    JFrame generatedCodeFrame = new JFrame();
    JPanel generatedCodePanel = new JPanel();
    ImageIcon icon;

    public GeneratedCodeMenu(ImageIcon icon){
        generatedCodeLabel.setIcon(icon);
        generatedCodeFrame.add(generatedCodePanel);
        generatedCodeFrame.setSize(new Dimension(210,230));
        generatedCodeFrame.setLocationRelativeTo(null);
        generatedCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        generatedCodeFrame.setTitle("Generated Code");
        generatedCodeFrame.add(generatedCodeLabel);
        generatedCodeFrame.setResizable(false);
        generatedCodeFrame.revalidate();
        generatedCodeFrame.repaint();
        generatedCodeFrame.setVisible(true);
    }
}