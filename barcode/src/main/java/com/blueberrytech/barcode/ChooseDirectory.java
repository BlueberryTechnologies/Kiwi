package com.blueberrytech.barcode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




/*
 * Java IO Imports
 */

public class ChooseDirectory {
    // Directory Frame and Panel
    BarcodeGenerator mainGenerator = new BarcodeGenerator();
    JFrame directoryFrame = new JFrame();
    JPanel directoryPanel = new JPanel();

    // Directory buttons
    JButton dirButton = new JButton("Select Directory");
    JButton defDirButton = new JButton("Use Default Directory");

    // Current location label
    JLabel currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath());
    

    public ChooseDirectory(){

        directoryPanel.add(currLocation);
        directoryPanel.add(dirButton);
        directoryPanel.add(defDirButton);
        directoryPanel.setLayout(new GridLayout(0,1));
        directoryPanel.setSize(new Dimension(400,200));
        directoryFrame.add(directoryPanel, BorderLayout.CENTER);
        /*
            * Building the frame
        */
        directoryFrame.setSize(new Dimension(400,200));
        directoryFrame.setLocationRelativeTo(null);
        directoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        directoryFrame.setTitle("Choose Default Directory");
        directoryFrame.setVisible(true);

        
        dirButton.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent arg0) {
                BarcodeGenerator.setDirectory(false);
                currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath());
                directoryPanel.removeAll();
                directoryPanel.add(currLocation);
                directoryPanel.add(dirButton);
                directoryPanel.add(defDirButton);
                directoryPanel.revalidate();
                directoryPanel.repaint();
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE);
            }
        });
        
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                BarcodeGenerator.setDirectory(true);
                currLocation = new JLabel(" Current Location: " + BarcodeGenerator.getImageSavePath());
                directoryPanel.removeAll();
                directoryPanel.add(currLocation);
                directoryPanel.add(dirButton);
                directoryPanel.add(defDirButton);
                directoryPanel.revalidate();
                directoryPanel.repaint();
                JOptionPane.showMessageDialog(null,"Path Changed", "Action Successful...",JOptionPane.WARNING_MESSAGE);
            }
        });
        directoryFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                BarcodeEditor.comboBox.setSelectedIndex(0);
                BarcodeEditor.updateMainFrame();
            }
        });
    }
}