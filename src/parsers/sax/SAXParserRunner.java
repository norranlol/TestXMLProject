package parsers.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SAXParserRunner {

    private static final String XML_PATH = "data/data1.xml";

    public static void main(String[] args){
        try {
            File xmlFile = new File(XML_PATH);
            InputStream xmlInputStream = new FileInputStream(xmlFile);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
           // parser.getXMLReader().setErrorHandler(new DOMErrorHandler());
            SAXParserHandler saxParserHandler = new SAXParserHandler();
            parser.parse(xmlInputStream, saxParserHandler);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
