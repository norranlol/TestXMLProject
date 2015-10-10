package parsers.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Curly on 08.10.2015.
 */
public class SAXParserRunner {

    private static final String XML_PATH = "data/data1.xml";
    private static final String HTML_PATH = "output/html/testHTML2.html";

    public static void main(String[] args){
        try {
            File xmlFile = new File(XML_PATH);
            FileInputStream xmlInputStream = new FileInputStream(xmlFile);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            SAXParserHandler saxParserHandler = new SAXParserHandler();
            parser.parse(xmlInputStream, saxParserHandler);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
