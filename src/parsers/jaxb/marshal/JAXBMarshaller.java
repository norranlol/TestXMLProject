package parsers.jaxb.marshal;

import org.xml.sax.SAXException;
import parsers.jaxb.model.Flights;
import parsers.jaxb.validation.JAXBValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBMarshaller {

    private static final String XML_PATH = "src/parsers/jaxb/marshal/result/result.xml";

    public static void marshalObjectsToXml(Flights flights){
        try {
            File file = new File(XML_PATH);
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(JAXBHelper.XSD_PATH));
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setSchema(schema);
            jaxbMarshaller.setEventHandler(new JAXBValidationEventHandler());
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(flights, file);
        } catch (JAXBException e) {
            System.out.println("You have some problems with marshalling!!!");
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
