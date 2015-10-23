package parsers.jaxb.marshal;

import parsers.jaxb.model.Flights;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JAXBMarshaller {

    private static final String XML_PATH = "src/parsers/jaxb/marshal/result/result.xml";

    public static void marshalObjectsToXml(Flights flights){
        File file = new File(XML_PATH);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(flights, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
