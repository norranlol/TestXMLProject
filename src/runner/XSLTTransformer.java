package runner;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import parsers.xslt.XSLTErrorListener;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XSLTTransformer {

    private static final String XSL_PATH = "xslt-transformation/transformation.xsl";
    private static final String XML_PATH = "xslt-transformation/data1.xml";
    private static final String HTML_PATH = "xslt-transformation/resultHTML.html";
    private static final String ERROR_FILE_PATH = "xslt-transformation/transformation_errors.txt";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setErrorListener(new XSLTErrorListener());
            Transformer transformer = tf.newTransformer(new StreamSource(XSL_PATH));
            tf.setErrorListener(new XSLTErrorListener());
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            SAXSource saxSource = new SAXSource(new InputSource(new FileInputStream(new File(XML_PATH))));
            transformer.transform(saxSource, new StreamResult(HTML_PATH));
        } catch(TransformerException e) {
            e.printStackTrace();
        }
//        TransformerFactory factory = TransformerFactory.newInstance();
//        Transformer transformer = factory.newTransformer(new StreamSource(XSL_PATH));
//        SAXSource saxInput = new SAXSource(new InputSource(XML_PATH));
//        DOMResult domOutput = new DOMResult();
//        transformer.transform(saxInput, domOutput);
//        Document document = (Document) domOutput.getNode();
//        DOMSource domInput = new DOMSource(document);
//        StreamResult streamOutput = new StreamResult(new FileOutputStream(new File(HTML_PATH)));
//        transformer.transform(domInput, streamOutput);
    }
}
