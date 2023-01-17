package com.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class ChooseDirectory {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JFileChooser chooser = new JFileChooser();
    GenerateBarcode generateBarcode = new GenerateBarcode();


    public ChooseDirectory(){
        

        JButton dirButton = new JButton("Select Directory");
        dirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                generateBarcode.selectDirectory(generateBarcode.generateDirectory(), false);
                System.exit(0);
            }
        });

        JButton defDirButton = new JButton("Use Default Directory");
        defDirButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                generateBarcode.selectDirectory(generateBarcode.generateDirectory(), true);
                System.exit(0);
            }
        });
        

        panel.add(dirButton);
        panel.add(defDirButton);
        panel.setLayout(new GridLayout(0,1));
        frame.add(panel, BorderLayout.CENTER);
        /*
         * Building the frame
         */
        frame.setPreferredSize(new Dimension(400,200));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Choose Default Directory");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new ChooseDirectory();
    }
}
