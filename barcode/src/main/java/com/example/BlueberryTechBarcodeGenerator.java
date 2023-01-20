package com.example;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

/*
 * ZXING Imports
 */

 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.aztec.AztecWriter;
 import com.google.zxing.client.j2se.MatrixToImageWriter;
 import com.google.zxing.oned.Code128Writer;
 import com.google.zxing.qrcode.QRCodeWriter;
 import com.google.zxing.common.BitMatrix;

public class BlueberryTechBarcodeGenerator{
    


    String printerName = "";
    String printerIP = "";
    String userChoice = "";
    String userChoiceCode = "";
    String comboSelected = "";
    String finalPath = "";
    String textToWrite;
    String targetFile;
    String customLocation;
    String photoLocation;
    boolean isGenerated = false;
    boolean selectedImage = false;
    ImageIcon image;
    File file;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static final File user_home = new File(System.getProperty("user.home"));
    File customFileLocation;
    File defaultDirectoryToWrite = new File("");

    public void GenerateBarcodes(String text, String algo){
        /*
         * This method will take user input in the form of a text field and convert that to various barcodes or codes.
         * This method will also take two parameters:
         *              -> String
         *              -> String
         */
        try{
            File barcodeImages = new File(getDirectory(), "/barcode_images"); // Establishes a directory to save the barcode photos to.
            if (!barcodeImages.exists()){ // If the folder doesn't exist in the specified directory, then it creates one.
                barcodeImages.mkdirs();
            }
            String path = barcodeImages + "/" + text + ".png"; // The path that is set for every barcode created.

            /*
             * If algorithm is set as different, then it will generate a different barcode.
             */
            if (algo.equals("CODE128")){
                Code128Writer code128Writer = new Code128Writer();
                BitMatrix code128Matrix = code128Writer.encode(text, BarcodeFormat.CODE_128, 100, 100);
                MatrixToImageWriter.writeToPath(code128Matrix, "jpg", Paths.get(path));
                System.out.println("CODE128 Created");
                JOptionPane.showMessageDialog(null,"CODE128 Code Created...", "Success...",JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("AZTEC")){
                AztecWriter aztecWriter = new AztecWriter();
                BitMatrix aztecMatrix = aztecWriter.encode(text, BarcodeFormat.AZTEC, 100, 100);
                MatrixToImageWriter.writeToPath(aztecMatrix, "jpg", Paths.get(path));
                System.out.println("AZTEC Created");
                JOptionPane.showMessageDialog(null,"AZTEC Code Created...", "Success...",JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("QR Codes")){
                QRCodeWriter qrWriter = new QRCodeWriter(); 
                BitMatrix qrMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
                MatrixToImageWriter.writeToPath(qrMatrix, "jpg", Paths.get(path));
                System.out.println("QR Created");
                JOptionPane.showMessageDialog(null,"QR Code Created...", "Success...",JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("PLAIN TEXT")){
                PrintBarcode(path, true);
            }else if (algo.equals("IMAGE")){
                JOptionPane.showMessageDialog(null,"How did you get here? Interesting...", "Aborting...",JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,"Barcode Failed", "Aborting...",JOptionPane.WARNING_MESSAGE);
            }
            finalPath = path;
        }catch(Exception e){
            System.out.println("An exception has been thrown:\n> " + e.getMessage());
            JOptionPane.showMessageDialog(null,"There has been an error, please check the console.\n" + e.getMessage(), "Aborting...",JOptionPane.WARNING_MESSAGE);
        }
        
    }

    public void PrintBarcode(String path, boolean plainText){
        try{
            if(plainText){
                DocFlavor textFlavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                PrintRequestAttributeSet().add(new Copies(1));
                PrintService[] printService = FindPrintService(PrintRequestAttributeSet());
                String printerName = "";
                if (printService.length == 0){ // If the print service is zero then throws exception.
                throw new RuntimeException("No printer services available.");
                }
                PrintService ps = printService[0];
                System.out.println("=======================\n= Printing to " + printerName + "... =\n=======================");
                DocPrintJob job = ps.createPrintJob(); // Creates a printing job to print to
                String text = textToWrite;    // These are for printing with text
                byte[] bytes = text.getBytes("UTF-8");
                Doc doc = new SimpleDoc(bytes, textFlavor, null);
                job.print(doc, PrintRequestAttributeSet()); // Initiates the printing job with the selected parameters
                System.out.println("Printing Finished.");
                JOptionPane.showMessageDialog(null,
                            "Printed Text...", "Success...",
                            JOptionPane.WARNING_MESSAGE);
            }else if(!plainText){
                DocFlavor imageFlavor = DocFlavor.INPUT_STREAM.GIF; // Format for images.
                PrintRequestAttributeSet().add(new Copies(1));
                PrintService[] printService = FindPrintService(PrintRequestAttributeSet());
                String printerName = "";
                if (printService.length == 0){ // If the print service is zero then throws exception.
                throw new RuntimeException("No printer services available.");
                }
                PrintService ps = printService[0];
                System.out.println("=======================\n= Printing to " + printerName + "... =\n=======================");
                DocPrintJob job = ps.createPrintJob(); // Creates a printing job to print to
                FileInputStream fin = new FileInputStream(path);
                Doc doc = new SimpleDoc(fin, imageFlavor, null);
                job.print(doc, PrintRequestAttributeSet()); // Initiates the printing job with the selected parameters
                fin.close(); // Closes the file input stream once it's done
                System.out.println("Printing Finished.");
                JOptionPane.showMessageDialog(null,
                            "Printed Code...", "Success...",
                            JOptionPane.WARNING_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("An exception has been thrown:\n> " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                            e.getMessage(), "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
        }
        
    }

    public PrintService[] FindPrintService(PrintRequestAttributeSet printRequestAttributeSet){
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.GIF, printRequestAttributeSet);
        return printService;
    }

    public PrintRequestAttributeSet PrintRequestAttributeSet(){
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        return printRequestAttributeSet;
    }

    public String ReturnPrinterName(){
        PrintRequestAttributeSet().add(new Copies(1));
        PrintService[] printService = FindPrintService(PrintRequestAttributeSet());
        if (printService.length == 0){ // If the print service is zero then throws exception.
            JOptionPane.showMessageDialog(null,"No printer services available", "Aborting...",JOptionPane.WARNING_MESSAGE);
            return "No Printer Found";
        }
        PrintService ps = printService[0];
        return ps.getName();
    }

    public void selectImage(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
            setImageFile(selectedFile.getAbsolutePath());
		}
    }

    public void setImageFile(String fileThatWasSelected){
        targetFile = fileThatWasSelected;
    }
    public String getImageFile(){
        return targetFile;
    }

    public String getReturnPath(){
        return finalPath;
    }
    public String getImageSavePath(){
        return getDirectory();
    }



    /*
     * Setting and getting directories
     */
    public String getDirectory(){
        try{
            if (getDefaultDirectory() == null){

            }
            File fileToReadFrom = new File(getDefaultDirectory() + "/customPath.txt");
            if (!fileToReadFrom.exists()){
                setDirectory(true);
                JOptionPane.showMessageDialog(null, "A Custom File Text has been generated and placed in: " + getDefaultDirectory(), "Warning...", JOptionPane.WARNING_MESSAGE);
            }
            FileReader readingFile = new FileReader(fileToReadFrom);
            Scanner readingFileScanner = new Scanner(readingFile);
            String data = readingFileScanner.nextLine();
            readingFileScanner.close();
            return data;
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        return null;
    }
    public File getDefaultDirectory(){
        setDefaultDirectory();
        return defaultDirectoryToWrite;
    }
    private void setDefaultDirectory(){
        File defaultDirectory = new File("");
        if (OS.equals("win")){
            JOptionPane.showMessageDialog(null,"Windows not supported yet", "Aborting...",JOptionPane.WARNING_MESSAGE);
        }else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
            defaultDirectory = new File(user_home, "BBTBE");
            if(!defaultDirectory.exists()){
                JOptionPane.showMessageDialog(null, "The default directory doesn't exist.\nIt has been created.", "Warning", JOptionPane.WARNING_MESSAGE);
                defaultDirectory.mkdirs();
            }
        }else if (OS.contains("mac")){
            JOptionPane.showMessageDialog(null,"MacOS not supported yet", "Aborting...",JOptionPane.WARNING_MESSAGE); 
        }else {
            JOptionPane.showMessageDialog(null,"Operating System Not Found", "Aborting...",JOptionPane.WARNING_MESSAGE); 
        }
        defaultDirectoryToWrite = defaultDirectory;
    }
    public File setDirectory(boolean isDefault){
            if (!isDefault){
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    customLocation = selectedFile.getAbsolutePath();
                    try{
                        FileWriter writingFile = new FileWriter(getDefaultDirectory() + "/customPath.txt");
                        writingFile.write(customLocation);
                        writingFile.close();
                        return selectedFile;
                    }catch(IOException e){
                        JOptionPane.showMessageDialog(null,"There was an error setting the custom directory.\nPlease check the console.", "Aborting...",JOptionPane.WARNING_MESSAGE); 
                        System.out.println("There was an error setting the custom directory.\n> " + e.getMessage());
                    }
                }
            }else{
                try{
                    customLocation = getDefaultDirectory().getAbsolutePath();
                    FileWriter writingFile = new FileWriter(getDefaultDirectory() + "/customPath.txt");
                    writingFile.write(customLocation);
                    writingFile.close();
                    return getDefaultDirectory();
                }catch(IOException e){
                    JOptionPane.showMessageDialog(null,"There was an error setting the default directory.\nPlease check the console.", "Aborting...",JOptionPane.WARNING_MESSAGE); 
                    System.out.println("There was an error setting the default directory.\n> " + e.getMessage());
                }
            }
            return null;
    }
}