package parsers.dom;

import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;

public class MyDOMErrorHandler implements DOMErrorHandler {

    private void printInfo(DOMError error){
        System.out.println("Line number = " + error.getLocation().getLineNumber());
        System.out.println("Column number = " + error.getLocation().getColumnNumber());
        System.out.println("Message = " + error.getMessage());
        System.out.println("Related data = " + error.getRelatedData());
    }

    @Override
    public boolean handleError(DOMError error) {
        if (error.getSeverity() == DOMError.SEVERITY_WARNING){
            System.out.println("Warning!");
            printInfo(error);
            return true;
        } else if (error.getSeverity() == DOMError.SEVERITY_ERROR){
            System.out.println("Error!");
            printInfo(error);
            return true;
        } else if (error.getSeverity() == DOMError.SEVERITY_FATAL_ERROR){
            System.out.println("Fatal error!");
            printInfo(error);
            return true;
        }
        return true;
    }
}
