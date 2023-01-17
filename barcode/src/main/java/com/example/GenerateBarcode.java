package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class GenerateBarcode 
{   
    String finalPath = "";
    String textToWrite;
    File file;
    String targetFile;
    String customLocation;
    String photoLocation;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static final File user_home = new File(System.getProperty("user.home"));
    File customFileLocation;
    


    public void GenerateBarcodes(String text, String algo){
        try{
            readingDirectory(customFileLocation);
            File barcodeImages = new File(photoLocation, "/barcode_images");
            if (!barcodeImages.exists()){
                barcodeImages.mkdirs();
            }
            readingDirectory(generateDirectory());
            textToWrite = text;
            String path = barcodeImages + "/" + textToWrite + ".png";
            if (algo.equals("CODE128")){
                Code128Writer code128Writer = new Code128Writer();
                BitMatrix code128Matrix = code128Writer.encode(text, BarcodeFormat.CODE_128, 100, 100);
                MatrixToImageWriter.writeToPath(code128Matrix, "jpg", Paths.get(path));
                System.out.println("CODE128 Created");
                JOptionPane.showMessageDialog(null,
                            "CODE128 Code Created...", "Success...",
                            JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("AZTEC")){
                AztecWriter aztecWriter = new AztecWriter();
                BitMatrix aztecMatrix = aztecWriter.encode(text, BarcodeFormat.AZTEC, 100, 100);
                MatrixToImageWriter.writeToPath(aztecMatrix, "jpg", Paths.get(path));
                System.out.println("AZTEC Created");
                JOptionPane.showMessageDialog(null,
                            "AZTEC Code Created...", "Success...",
                            JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("QR Codes")){
                QRCodeWriter qrWriter = new QRCodeWriter(); 
                BitMatrix qrMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
                MatrixToImageWriter.writeToPath(qrMatrix, "jpg", Paths.get(path));
                System.out.println("QR Created");
                JOptionPane.showMessageDialog(null,
                            "QR Code Created...", "Success...",
                            JOptionPane.WARNING_MESSAGE);
            }else if (algo.equals("PLAIN TEXT")){
                PrintBarcode(path, true);
            }else if (algo.equals("IMAGE")){
                JOptionPane.showMessageDialog(null,
                            "How did you get here? Interesting...", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,
                            "Barcode Failed", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
            }
            finalPath = path;
        }catch(Exception e){
            System.out.println("An exception has been thrown:\n> " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                            e.getMessage(), "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
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
                if (ps.getName().equals("Earl")){
                    printerName = "Earl";
                    System.out.println("=======================\n= Printing to " + printerName + "... =\n=======================");
                }else{
                    System.out.println("Printing to " + ps);
                }
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
                if (ps.getName().equals("Earl")){
                    printerName = "Earl";
                    System.out.println("=======================\n= Printing to " + printerName + "... =\n=======================");
                }else{
                    System.out.println("Printing to " + ps);
                }
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

    public static PrintService[] FindPrintService(PrintRequestAttributeSet printRequestAttributeSet){
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.GIF, printRequestAttributeSet);
        return printService;
    }

    public static PrintRequestAttributeSet PrintRequestAttributeSet(){
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        return printRequestAttributeSet;
    }

    public String ReturnPrinterName(){
        PrintRequestAttributeSet().add(new Copies(1));
        PrintService[] printService = FindPrintService(PrintRequestAttributeSet());
        if (printService.length == 0){ // If the print service is zero then throws exception.
            JOptionPane.showMessageDialog(null,
                            "No printer services available", "Aborting...",
                            JOptionPane.WARNING_MESSAGE);
                            return "No Printer Found";
        }
        PrintService ps = printService[0];
        
        return ps.getName();
    }
    public void ReturnPrinterIP(){
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService[] printServiceIP = FindPrintService(PrintRequestAttributeSet());
        

        System.out.println(printServiceIP[0]);

        //return output;
        
    }
    public void selectImage(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
            setImageFile(selectedFile.getAbsolutePath());
		}
    }
    public void selectDirectory(File fileToAppend, boolean isDefault){
        if(!isDefault){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);
    
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                
                customLocation = selectedFile.getAbsolutePath();
                
                try{
                    FileWriter writingFile = new FileWriter(fileToAppend + "/customPath.txt");
                    writingFile.write(customLocation);
                    writingFile.close();
                }catch(IOException e){
    
                }
                
            }
        }else{
            try{
                FileWriter writingFile = new FileWriter(fileToAppend + "/customPath.txt");
                writingFile.write(fileToAppend.getAbsolutePath());
                writingFile.close();
            }catch(IOException e){

            }
        }
        
    }
    public void readingDirectory(File photoFileLocation){
        try{
            FileReader readingFile = new FileReader(photoFileLocation + "/customPath.txt");
            Scanner readingFileScanner = new Scanner(readingFile);
            while(readingFileScanner.hasNextLine()){
                String data = readingFileScanner.nextLine();
                photoLocation = data;
            }
            readingFileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
    }

    public static File generateDirectory(){
        File customFileStorage = new File("");
        File defaultFileStorage;
        if (OS.equals("win")){
            JOptionPane.showMessageDialog(null,
            "Windows not supported yet", "Aborting...",
            JOptionPane.WARNING_MESSAGE);
        }else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
            customFileStorage = new File(user_home, "BBTBE");
            if(!customFileStorage.exists()){
                customFileStorage.mkdirs();
            }
        }else if (OS.contains("mac")){
            JOptionPane.showMessageDialog(null,
            "MacOS not supported yet", "Aborting...",
            JOptionPane.WARNING_MESSAGE); 
        }else if (OS.contains("sunos")){
            JOptionPane.showMessageDialog(null,
            "Solaris not supported yet", "Aborting...",
            JOptionPane.WARNING_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null,
                        "Operating System Not Found", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
        }
        return customFileStorage;
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
        readingDirectory(generateDirectory());
        return photoLocation;
    }
}