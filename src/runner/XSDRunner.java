package runner;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XSDRunner {

    private static final String XSD_PATH = "config/xsd/airport.xsd";
    private static final String XML_PATH = "data/data2.xml";

    public static void main(String[] args){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = null;
            schema = factory.newSchema(new File(XSD_PATH));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(XML_PATH)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
