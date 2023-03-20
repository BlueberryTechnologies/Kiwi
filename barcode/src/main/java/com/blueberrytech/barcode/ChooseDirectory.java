package com.blueberrytech.barcode;

/*
 * Awt + Misc Imports
 */
import java.awt.*;


/*
 * Java Swing Imports
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




/*
 * Java IO Imports
 */

public class ChooseDirectory {
    // Directory Frame and Panel
    BlueberryTechBarcodeGenerator mainGenerator = new BlueberryTechBarcodeGenerator();
    JFrame directoryFrame = new JFrame();
    JPanel directoryPanel = new JPanel();

    // Directory buttons
    JButton dirButton = new JButton("Select Directory");
    JButton defDirButton = new JButton("Use Default Directory");

    // Current location label
    JLabel currLocation = new JLabel(" Current Location: " + mainGenerator.getImageSavePath());
    

    public ChooseDirectory(){

        GenerateDirectoryWindow();
        

        /*
        dirButton.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent arg0) {
                mainGenerator.setDirectory(false);
                currLocation = new JLabel(" Current Location: " + mainGenerator.getImageSavePath());
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
                mainGenerator.setDirectory(true);
                currLocation = new JLabel(" Current Location: " + mainGenerator.getImageSavePath());
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
                new BlueberryTechBarcodeEditor();
            }
        });
     */   
    }
    public void GenerateDirectoryWindow(){
        directoryPanel.add(currLocation);
        directoryPanel.add(dirButton);
        directoryPanel.add(defDirButton);
        directoryPanel.setLayout(new GridLayout(0,1));
        directoryFrame.add(directoryPanel, BorderLayout.CENTER);
        /*
            * Building the frame
        */
        directoryFrame.setPreferredSize(new Dimension(400,200));
        directoryFrame.setLocationRelativeTo(null);
        directoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        directoryFrame.setTitle("Choose Default Directory");
    }

}