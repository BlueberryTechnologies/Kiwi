/*
 * Kiwi Barcode Editor
 * Barcode Main Class
 *
 * This class is responsible for the first steps in UI generation.
 * It obtains the user's operating system and determines which UI to draw.
 *
 * Last Date Modified: 01/25/2025
 * Last User Modified: gh/rileyrichard
 * License: GPL-3.0
 */
package com.blueberrytech.barcode;

import com.formdev.flatlaf.util.SystemInfo;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

public class BarcodeMain {
    static BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    public static void main(String[] args) {

        if( SystemInfo.isMacOS ) {
            /*
            Adds specific options for macOS
             */
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
            System.setProperty( "apple.awt.application.name", "Blueberry Tech Barcode Editor" );
            System.setProperty( "apple.awt.application.appearance", "system" );
            FlatMacDarkLaf.setup();
        }else if(SystemInfo.isWindows_10_orLater){
            System.setProperty("flatlaf.menuBarEmbedded", "false");
            System.setProperty("flatlaf.useWindowDecorations", "true");
            FlatGitHubDarkIJTheme.setup();
        } // We aren't directly supporting Linux at this time for themes, but the user should be able to run it with minor UI inconsistencies.

        
        barcodeGenerator.setInitialPrinter(); // Sets an initial printer so the user can print to something.
        BarcodeGenerator.setDefaultDimensions();
        if(BarcodeGenerator.getImageSavePath() == null){ // If the path is null then prints.
            System.out.println("This is null"); // lol
        }else{
            new BarcodeEditor(); // If the path is not null then the program runs.
        }
    }
}
