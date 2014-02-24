# flyingsaucer-cli #

A CLI for the flying-saucer XSL->PDF rendering library. This application creates a PDF from  specified XML and XSL file.
The application performs the XSLT transformation into HTML and then renders the HTML page into a PDF form.

## How to build and run ##

To build this application, assuming that Maven is installed within your path, type:
"mvn package" in the root directory of the project.

**Example:**

     flyingsaucer-cli $ mvn clean package

The example cleans a previous build and builds a new copy. The clean argument is not necessary for a successful build,
but it does clean up the environment prior to the build. If the build succeeded you will see a message that states
"BUILD SUCCESS" and no test cases failed. The executable, flyingsaucer-cli-1.0-SNAPSHOT.jar, is then built within the
"target" directory.

Running the application assumes that the java executable is within your path on the command line.
java -jar target/flyingsaucer-cli-1.0-SNAPSHOT.jar -xmlFile sampleXMLFile.xml -xslFile sampleXSLFile.xsl
-pdfOutput sample.pdf

The output of this application should be a the result of the transformation fo the sampleXMLFile.xml with the
sampleXSL.xsl file into the sample pdf.

## Dependencies ##

* Java 7 - Platform used for building and running the application.
* JUnit 4
* Maven 3
* Jewelcli - Used for parsing CLI inputs. Defined in the Maven POM file.
* Flying Saucer - Used for the transformation of the XHTML information into a PDF.



