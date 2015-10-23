package parsers.jaxb.unmarshal;

import parsers.jaxb.marshal.JAXBHelper;
import parsers.jaxb.marshal.JAXBMarshaller;
import parsers.jaxb.model.Flights;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBUnmarshaller {

    private static final String XML_PATH = "data/data3.xml";

    public static void main(String[] args){
        try {
            File file = new File(XML_PATH);
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Flights flights = (Flights) jaxbUnmarshaller.unmarshal(file);
            JAXBHelper.modifyObjectStructure(flights);
            JAXBMarshaller.marshalObjectsToXml(flights);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
