package com.theexceptioncatcher;

import com.lexicalscope.jewel.cli.CliFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AppParameters inputs = CliFactory.parseArguments(AppParameters.class, args);
        System.out.println( "Hello World!" );
    }
}
