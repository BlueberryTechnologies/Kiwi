package com.blueberrytech.barcode;

import com.formdev.flatlaf.util.SystemInfo;
import com.blueberrytech.barcode.generator.BarcodeGenerator;
import com.blueberrytech.barcode.editor.BarcodeEditor;
public class BarcodeMain {
    static BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    public static void main(String[] args) {
        if( SystemInfo.isMacOS ) {
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
            System.setProperty( "apple.awt.application.name", "Blueberry Tech Barcode Editor" );
            System.setProperty( "apple.awt.application.appearance", "system" );
            //FlatMonocaiIJTheme.setup();
        }else if(SystemInfo.isWindows_10_orLater){
            System.setProperty("flatlaf.menuBarEmbedded", "false");
            System.setProperty("flatlaf.useWindowDecorations", "true");
            //FlatMonocaiIJTheme.setup();
        }
        
        barcodeGenerator.setInitialPrinter(); // Sets an initial printer so the user can print to something.
        BarcodeGenerator.setDefaultDimensions();
        if(BarcodeGenerator.getImageSavePath() == null){ // If the path is null then prints.
            System.out.println("This is null"); // lol
        }else{
            new BarcodeEditor(); // If the path is not null then the program runs.
        }
    }
}
