package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.Option;

/**
 * Created by steven on 12/15/13.
 */
public interface AppParameters {
    @Option(longName = "xmlFile", description = "This parameter is the location of the XML file used in the " +
            "transformation.")
    String getXmlFile();

    @Option(longName = "xslFile", description = "This parameter is the location of the XSLT file used in the " +
            "transformation.")
    String getXSLFile();

    @Option(longName = "pdfOutput", description = "This parameter describes where the PDF formatted output will be " +
            "stored.")
    String getPDFOutput();

}
