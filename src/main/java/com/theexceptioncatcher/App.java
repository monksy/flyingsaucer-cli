package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.CliFactory;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AppParameters inputs = CliFactory.parseArguments(AppParameters.class, args);

        //TODO: Verify that the XSlT file exists

        //TODO: Verify that the XML File exists

        //TODO: Check to see if the overwrite is neccessary.
           //TODO: Check to see if the output file exists if so then through an error

        //TODO: Perform transformation
    }
}
