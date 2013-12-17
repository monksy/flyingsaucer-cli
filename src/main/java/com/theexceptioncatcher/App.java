package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.CliFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
            System.exit(2);
        }


        //Perform transformation, overwrite the pdf file if neccesary
        processXmlXslToPDF(inputs.getXmlFile(), inputs.getXSLFile(), inputs.getPDFOutput());
    }

    private static void processXmlXslToPDF(String xmlFile, String xslFile, String pdfOutput) {
        // parse the markup into an xml Document
     /*   DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);

            OutputStream os = new FileOutputStream();
            renderer.layout();
            renderer.createPDF(os);
            os.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }                    */

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
