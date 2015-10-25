package parsers.jaxb.unmarshal;

import org.xml.sax.SAXException;
import parsers.jaxb.marshal.JAXBHelper;
import parsers.jaxb.marshal.JAXBMarshaller;
import parsers.jaxb.model.Flights;
import parsers.jaxb.validation.JAXBValidationEventHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBUnmarshaller {

    private static final String XML_PATH = "data/data3.xml";

    public static void main(String[] args){
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(JAXBHelper.XSD_PATH));
            File file = new File(XML_PATH);
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            jaxbUnmarshaller.setEventHandler(new JAXBValidationEventHandler());
            Flights flights = (Flights) jaxbUnmarshaller.unmarshal(file);
            JAXBHelper.modifyObjectStructure(flights);
            JAXBMarshaller.marshalObjectsToXml(flights);
            JAXBHelper.transformToHTML();
        } catch (JAXBException e) {
            System.out.println("You have some problems with unmarshalling!!!");
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
