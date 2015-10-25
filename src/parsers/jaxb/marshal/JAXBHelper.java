package parsers.jaxb.marshal;

import org.xml.sax.InputSource;
import parsers.jaxb.model.*;
import parsers.xslt.XSLTErrorListener;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JAXBHelper {

    private static final String XSL_PATH = "xslt-transformation/transformation.xsl";
    public static final String XSD_PATH = "config/xsd/airport.xsd";
    private static final String XML_PATH = "src/parsers/jaxb/marshal/result/result.xml";
    private static final String HTML_PATH = "src/parsers/jaxb/marshal/result/result.html";

    public static void modifyObjectStructure(Flights flights){
        if (!flights.getFlightList().isEmpty()){
            Flight flight = flights.getFlightList().get(flights.getFlightList().size() - 1);
            if (flight.getTicketList() == null)
                flight.setTicketList(new ArrayList<Ticket>());
            Client client1 = new Client(1, "Попов", "Иван", "Иванович", 1408, 553421, "89331435533", "popov@gmail.ru", "ул. Гороховая д.33 кв.54");
            Client client2 = new Client(2, "Бурундуков", "Василий", "Васильевич", 1409, 214345, "89215334266", "burunduk@gmail.ru", "ул. Василисина д.14 кв.35");
            AnonimousClient anonClient = new AnonimousClient("FRB", 443, "YES");
            Ticket ticket1 = new Ticket(1, 3, 4, "З", 7900, "left", client1, null);
            Ticket ticket2 = new Ticket(2, 5, 6, "З", 10100, "right", client2, null);
            Ticket ticket3 = new Ticket(3, 8, 9, "З", 9000, "center", null, anonClient);
            flight.getTicketList().add(ticket1);
            flight.getTicketList().add(ticket2);
            flight.getTicketList().add(ticket3);
        }
    }

    public static void transformToHTML(){
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setErrorListener(new XSLTErrorListener());
            Transformer transformer = tf.newTransformer(new StreamSource(XSL_PATH));
            tf.setErrorListener(new XSLTErrorListener());
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            SAXSource saxSource = null;
            try {
                saxSource = new SAXSource(new InputSource(new FileInputStream(new File(XML_PATH))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            transformer.transform(saxSource, new StreamResult(HTML_PATH));
        } catch(TransformerException e) {
            e.printStackTrace();
        }
    }
}
