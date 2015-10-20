package runner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTTransformer {

    private static final String XSL_PATH = "xslt-transformation/transformation.xsl";
    private static final String XML_PATH = "xslt-transformation/data1.xml";
    private static final String HTML_PATH = "xslt-transformation/resultHTML.html";
    private static final String ERROR_FILE_PATH = "xslt-transformation/transformation_errors.txt";

    public static void main(String[] args) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource(XSL_PATH));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new StreamSource(XML_PATH), new StreamResult(HTML_PATH));
            System.out.println("complete");
        } catch(TransformerException e) {
            e.printStackTrace();
        }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
