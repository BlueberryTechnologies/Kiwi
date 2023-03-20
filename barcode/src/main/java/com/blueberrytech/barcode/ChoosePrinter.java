package com.blueberrytech.barcode;

/*
 * Awt + Misc Imports
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;


/*
 * Java Swing Imports
 */

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;




/*
 * Java IO Imports
 */

public class ChoosePrinter {

    /*
     * Printer Frame and Panel
     */
    JFrame printerListFrame = new JFrame();
    JPanel printerListPanel = new JPanel();
    JTextArea printerList = new JTextArea();
    MenuBar menuBar = new MenuBar();
    BlueberryTechBarcodeEditor mainClass = new BlueberryTechBarcodeEditor();
    BlueberryTechBarcodeGenerator mainGenerator = new BlueberryTechBarcodeGenerator();
    final String[] printerChoices = BlueberryTechBarcodeGenerator.getPrinterServiceNameList().toArray(new String[0]);
    final JComboBox<String> printerComboBox = new JComboBox<String>(printerChoices);

    public ChoosePrinter(){
        printerListFrame.setPreferredSize(new Dimension(400,200));
        printerListFrame.setLocationRelativeTo(null);
        printerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        printerListFrame.setTitle("Printers On Network");
        menuBar.selectPrinter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                BlueberryTechBarcodeEditor.mainFrame.dispose();
                mainClass.currPrinter = new JLabel("Current Printer: " + mainGenerator.returnPrinterName());
                printerListPanel.add(mainClass.currPrinter);
                printerListPanel.add(printerComboBox);
                printerListFrame.add(printerListPanel, BorderLayout.CENTER);
                printerListFrame.pack();
                printerListPanel.setLayout(new GridLayout(0,1));
                printerListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
                printerListFrame.setVisible(true);
            }
        });
        
        printerComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainGenerator.updatePrinter(printerChoices[printerComboBox.getSelectedIndex()]);
                System.out.println("Changed values");
                printerListPanel.removeAll();
                mainClass.currPrinter = new JLabel("Current Printer: " + mainGenerator.returnPrinterName());
                printerListPanel.add(mainClass.currPrinter);
                printerListPanel.add(printerComboBox);
                printerListPanel.revalidate();
                printerListPanel.repaint();
            }
        });
        printerListFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                new BlueberryTechBarcodeEditor();
            }
        });
    }
}
