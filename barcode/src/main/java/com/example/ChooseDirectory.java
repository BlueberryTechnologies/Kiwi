package com.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;

public class ChooseDirectory {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JFileChooser chooser = new JFileChooser();
    GenerateBarcode generateBarcode = new GenerateBarcode();
    JLabel currLocation = new JLabel(" Current Location: " + generateBarcode.getImageSavePath());


    public ChooseDirectory(){
        JButton dirButton = new JButton("Select Directory");
        dirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                generateBarcode.selectDirectory(generateBarcode.generateDirectory(), false);
                frame.dispose();
                new ChooseDirectory();
            }
        });

        JButton defDirButton = new JButton("Use Default Directory");
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                generateBarcode.selectDirectory(generateBarcode.generateDirectory(), true);
                JOptionPane.showMessageDialog(null,
                        "Path Changed", "Action Successful...",
                        JOptionPane.WARNING_MESSAGE);
                        SwingUtilities.updateComponentTreeUI(frame);
                        frame.dispose();
                        new ChooseDirectory();
            }
        });
        frame.addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                new EarlGUI();
            }
        });
        panel.add(currLocation);
        panel.add(dirButton);
        panel.add(defDirButton);
        panel.setLayout(new GridLayout(0,1));
        frame.add(panel, BorderLayout.CENTER);
        /*
         * Building the frame
         */
        frame.setPreferredSize(new Dimension(400,200));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Choose Default Directory");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new ChooseDirectory();
    }
}
