package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.CliFactory;

import java.io.File;

/**
 * This simple utiltiy is responsible for wrapping the XSLT/XML flying-saucer library into a command line utility. If
 * this application succeeded the XML and XSL files will be transformed into the specified PDF file. The return code
 * will yield a 0 result. If the process did not succeeded then the application will display an error, and yield an
 * error code as the application's result.
 */
public class App {
    public static void main(String[] args) {
        AppParameters inputs = CliFactory.parseArguments(AppParameters.class, args);

        //Verify that the XSlT file exists
        if (!doesFileExist(inputs.getXSLFile())) {
            System.err.printf("Error: The XSL file '%s' does not exist.%n", inputs.getXSLFile());
            System.exit(1);
        }

        //Verify that the XML File exists
        if (!doesFileExist(inputs.getXmlFile())) {
            System.err.printf("Error: The XML file '%s' does not exist.%n", inputs.getXmlFile());
            System.exit(1);
        }

        //TODO: Check to see if the overwrite is neccessary.
        //TODO: Check to see if the output file exists if so then through an error

        //TODO: Perform transformation
    }

    /**
     * This method attempts to check if a file exists and can be read.
     * @param filePath The file path that is to be checked.
     * @return True if the file can be read by the executing user, and the file exists.
     */
    private static boolean doesFileExist(String filePath) {
        File fl = new File(filePath);
        return fl.isFile() && fl.canRead();
    }
}
