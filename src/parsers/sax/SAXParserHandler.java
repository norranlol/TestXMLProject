package parsers.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Curly on 08.10.2015.
 */
public class SAXParserHandler extends DefaultHandler {

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("Start Document TEST");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("End Document TEST");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        this.
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        System.out.println("End Element TEST");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        System.out.println("Characters TEST");
    }
}
