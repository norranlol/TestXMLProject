package parsers.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parsers.helpers.HTMLConvertHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Timestamp;

public class SAXParserHandler extends DefaultHandler {

    private static final String HTML_PATH = "output/html/testHTML2.html";
    private SAXConverter saxConverter = new SAXConverter();
    private String value = null;
    public static String currentRootElement = null;
    private Timestamp timeOfDeparture = null;
    private Timestamp timeOfArrival = null;

    public SAXParserHandler() throws FileNotFoundException {
        System.setOut(new PrintStream(new File(HTML_PATH)));
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        System.out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        System.out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        System.out.println("<head>");
        System.out.println("    <meta charset=\"UTF-8\"/>");
        System.out.println("    <title>Моя тестовая HTML-страница</title>");
        System.out.println("</head>");
        System.out.println("<body>");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("</body>");
        System.out.println("<style>td { vertical-align: top }</style>");
        System.out.println("</html>");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //Определяем внутри какого элемента находимся
        if (HTMLConvertHelper.ROOT_ELEMENTS.contains(qName))
            currentRootElement = qName;
        if (qName.equals(HTMLConvertHelper.FLIGHT_FIELD)){
            String flightCertificates = saxConverter.getValueOfAttributeByName(attributes, "flightCertificates");
            System.out.println("    <table border=\"1\" id=\"flight_certificates_" + flightCertificates + "\" width=\"100%\">");
            System.out.println("        <thead>");
            System.out.println("            <tr>");
            for (String header : HTMLConvertHelper.MAIN_COLUMNS)
                System.out.println("                <th>" + header  + "</th>");
            System.out.println("            </tr>");
            System.out.println("        </thead>");
            System.out.println("        <tbody>");
            System.out.println("            <tr>");
        } else if (qName.equals(HTMLConvertHelper.PILOT_FIELD)){
            saxConverter.fillPilotAttributes(attributes);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals(HTMLConvertHelper.FLIGHT_FIELD)){
            System.out.println("            </tr>");
            System.out.println("        </tbody>");
            System.out.println("    </table>");
            System.out.println("    <br/>");
            System.out.println("    <br/>");
        } else if (qName.equals(HTMLConvertHelper.ID_FIELD)){
            saxConverter.convertAllIdFields(qName, value, currentRootElement);
        } else if (qName.equals(HTMLConvertHelper.TIME_OF_DEPARTURE_FIELD)){
            String timeOfDepartureStr = HTMLConvertHelper.getReadableDateTime(value);
            timeOfDeparture = Timestamp.valueOf(timeOfDepartureStr);
            saxConverter.createSimpleElementWithCurrentWhitespaces("td", timeOfDepartureStr, 4);
        } else if (qName.equals(HTMLConvertHelper.TIME_OF_ARRIVAL_FIELD)){
            String timeOfArrivalStr = HTMLConvertHelper.getReadableDateTime(value);
            timeOfArrival = Timestamp.valueOf(timeOfArrivalStr);
            saxConverter.createSimpleElementWithCurrentWhitespaces("td", timeOfArrivalStr, 4);
            String difference = HTMLConvertHelper.calculateDifferenceBetweenTimestamps(timeOfDeparture, timeOfArrival);
            saxConverter.createSimpleElementWithCurrentWhitespaces("td", difference, 4);
        } else if (HTMLConvertHelper.PILOT_FIELDS.get(qName) != null){ //Поля пилота
            saxConverter.convertPilotFields(qName, value);
        } else if (HTMLConvertHelper.PLANE_FIELDS.get(qName) != null){ //Поля самолета
            saxConverter.convertPlaneFields(qName, value);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}
