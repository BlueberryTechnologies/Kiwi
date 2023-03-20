package com.blueberrytech.barcode;


/*
 * Java Swing Imports
 */






/*
 * Java IO Imports
 */

public class ChoosePrinter {
    /*

    JFrame printerListFrame = new JFrame();
    JPanel printerListPanel = new JPanel();
    JTextArea printerList = new JTextArea();
    MenuBar menuBar = new MenuBar();
    BarcodeEditor mainClass = new BarcodeEditor();
    BarcodeGenerator mainGenerator = new BarcodeGenerator();
    final String[] printerChoices = BarcodeGenerator.getPrinterServiceNameList().toArray(new String[0]);
    final JComboBox<String> printerComboBox = new JComboBox<String>(printerChoices);

    public ChoosePrinter(){
        printerListFrame.setPreferredSize(new Dimension(400,200));
        printerListFrame.setLocationRelativeTo(null);
        printerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        printerListFrame.setTitle("Printers On Network");
        menuBar.selectPrinter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                mainClass.mainFrame.dispose();
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
                new BarcodeEditor();
            }
        });
    }
    */
}
