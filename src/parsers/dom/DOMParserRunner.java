package parsers.dom;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class DOMParserRunner {

    private static final String XML_PATH = "data/data1.xml";
    private static final String HTML_PATH = "output/html/testHTML1.html";

    public static void main(String[] args){
        try {
            File xml = new File(XML_PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            ErrorHandler handler = new DOMErrorHandler();
            builder.setErrorHandler(handler);
            Document document = builder.parse(xml);
            DOMConverter domConverter = new DOMConverter(document, HTML_PATH);
            domConverter.convertToHTML();
        } catch (ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch (SAXException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


}
