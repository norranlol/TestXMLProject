package parsers.dom;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DOMParserRunner {

    private static final String XML_PATH = "data/data1.xml";
    private static final String HTML_PATH = "output/html/testHTML1.html";

    public static void main(String[] args){
        try {
            File xmlFile = new File(XML_PATH);
            FileInputStream xmlInputStream = new FileInputStream(xmlFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new DOMErrorHandler());
            Document document = builder.parse(xmlInputStream);
            //document.getDomConfig().setParameter("error-handler", new MyDOMErrorHandler());
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
