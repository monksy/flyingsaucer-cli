package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lowagie.text.DocumentException;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

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
        int result = processXmlXslToPDF(inputs.getXmlFile(), inputs.getXSLFile(), inputs.getPDFOutput());
        System.exit(result);
    }

    /**
     * This method handles the majority of the work of the application. This takes an XMl file, transforms it into
     * XHTML output with the XSL file given, and then
     *
     * @param xmlFile   The file path to the XML file.
     * @param xslFile   The file path to the XSL file.
     * @param pdfOutput The file path to the PDF.
     * @return Returns a success code if this succeeded or not. 0 if it did, a positive number if it didn't.
     */
    private static int processXmlXslToPDF(String xmlFile, String xslFile, String pdfOutput) {
        // parse the markup into an xml Document
        int result = 0;
        try (OutputStream os = new FileOutputStream(pdfOutput, false)) {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source source = new StreamSource(new FileInputStream(xmlFile));
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document resultingDocument = builder.newDocument();

            //Load the XML, and XSL files
            Templates template = factory.newTemplates(new StreamSource(new FileInputStream(xslFile)));
            Transformer xformer = template.newTransformer();
            Result domResult = new DOMResult(resultingDocument);

            //Transform the XML file into an XHMTL formated XML document
            xformer.transform(source, domResult);

            //Send the result XML out to the PDf writer, Java autoclose closes the PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(resultingDocument, null);
            renderer.layout();
            renderer.createPDF(os);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        } catch (DocumentException e) {
            e.printStackTrace();
            result = 3;
        } catch (IOException e) {
            e.printStackTrace();
            result = 3;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            result = 3;
        } catch (TransformerException e) {
            e.printStackTrace();
            result = 3;
        }
        return result;
    }

    /**
     * This method attempts to check if a file exists and can be read.
     *
     * @param filePath The file path that is to be checked.
     * @return True if the file can be read by the executing user, and the file exists.
     */
    private static boolean doesFileExist(String filePath) {
        File fl = new File(filePath);
        return fl.isFile() && fl.canRead();
    }
}
