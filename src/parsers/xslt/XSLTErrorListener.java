package parsers.xslt;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

public class XSLTErrorListener implements ErrorListener {

    @Override
    public void warning(TransformerException e) throws TransformerException {
        System.out.println("Warning: ");
        printInfo(e);
    }

    @Override
    public void error(TransformerException e) throws TransformerException {
        System.out.println("Error: ");
        printInfo(e);
        System.exit(1);
    }

    @Override
    public void fatalError(TransformerException e) throws TransformerException {
        System.out.println("Fatal error: ");
        printInfo(e);
        System.exit(2);
    }

    private void printInfo(TransformerException e) {
/*        System.out.println("System ID: " + e.getLocator().getSystemId());
        System.out.println("Line number: " + e.getLocator().getLineNumber());
        System.out.println("Column number: " + e.getLocator().getColumnNumber());*/
        System.out.println("Message: " + e.getMessage());
    }
}
