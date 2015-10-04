package runner;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DTDRunner {

    private static final String XML_PATH = "data/data1.xml";

    public static void main(String[] args){
        try {
            File xml = new File(XML_PATH);
            DocumentBuilderFactory factory
                    = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            ErrorHandler handler = new MyErrorHandler();
            builder.setErrorHandler(handler);
            Document document = builder.parse(xml);
            System.out.println("Success!!!");
        } catch (ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch (SAXException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private static class MyErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Warning: ");
            printInfo(e);
        }
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Error: ");
            printInfo(e);
        }
        public void fatalError(SAXParseException e)
                throws SAXException {
            System.out.println("Fattal error: ");
            printInfo(e);
        }
        private void printInfo(SAXParseException e) {
            System.out.println("   Public ID: "+e.getPublicId());
            System.out.println("   System ID: "+e.getSystemId());
            System.out.println("   Line number: "+e.getLineNumber());
            System.out.println("   Column number: "+e.getColumnNumber());
            System.out.println("   Message: "+e.getMessage());
        }
    }
}
