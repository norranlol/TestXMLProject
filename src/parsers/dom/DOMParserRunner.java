package parsers.dom;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMParserRunner {

    private static final String XML_PATH = "data/data1.xml";

    public static void main(String[] args){
        try {
            File xml = new File(XML_PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            ErrorHandler handler = new DOMErrorHandler();
            builder.setErrorHandler(handler);
            Document document = builder.parse(xml);
            System.out.println("Test");
        } catch (ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch (SAXException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


}
