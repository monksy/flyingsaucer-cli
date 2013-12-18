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
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        processXmlXslToPDF(inputs.getXmlFile(), inputs.getXSLFile(), inputs.getPDFOutput());
    }

    private static void processXmlXslToPDF(String xmlFile, String xslFile, String pdfOutput) {
        // parse the markup into an xml Document

        try (OutputStream os = new FileOutputStream(pdfOutput, false)) {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source source = new StreamSource(new FileInputStream(xmlFile));
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document resultingDocument = builder.newDocument();

            //Load the XML, and XSL files
            Templates template = factory.newTemplates(new StreamSource(new FileInputStream(xslFile)));
            Transformer xformer = template.newTransformer();
            Result result = new DOMResult(resultingDocument);

            //Transform the XML file into an XHMTL formated XML document
            xformer.transform(source, result);

            //Send the result XML out to the PDf writer, Java autoclose closes the PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(resultingDocument, null);
            renderer.layout();
            renderer.createPDF(os);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    private static void writeOut(Document doc) {
        // Use a Transformer for output
        TransformerFactory tFactory =
                TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
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
