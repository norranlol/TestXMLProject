package parsers.dom.html;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class HTMLDocumentImpl extends DocumentImpl {

    private static final long serialVersionUID = 1658286253541962623L;

    /**
     * Creates an Document with basic elements required to meet
     * the <a href="http://www.w3.org/TR/xhtml1/#strict">XHTML standards</a>.
     * <pre>
     * {@code
     * <?xml version="1.0" encoding="UTF-8"?>
     * <!DOCTYPE html
     *     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     *     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
     * <html xmlns="http://www.w3.org/1999/xhtml">
     *     <head>
     *         <title>My Title</title>
     *     </head>
     *     <body/>
     * </html>
     * }
     * </pre>
     *
     * @param title desired text content for title tag. If null, no text will be added.
     * @return basic HTML Document.
     */
    public static Document makeBasicHtmlDoc(String title) {
        Document htmlDoc = new HTMLDocumentImpl();
        DocumentType docType = new DocumentTypeImpl(null, "html",
                "-//W3C//DTD XHTML 1.0 Strict//EN",
                "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");
        htmlDoc.appendChild(docType);
        Element htmlElement = htmlDoc.createElementNS("http://www.w3.org/1999/xhtml", "html");
        htmlDoc.appendChild(htmlElement);
        Element headElement = htmlDoc.createElement("head");
        htmlElement.appendChild(headElement);
        Element titleElement = htmlDoc.createElement("title");
        Element metaElement = htmlDoc.createElement("meta");
        metaElement.setAttribute("charset", "UTF-8");
        if(title != null)
            titleElement.setTextContent(title);
        headElement.appendChild(metaElement);
        headElement.appendChild(titleElement);
        Element bodyElement = htmlDoc.createElement("body");
        htmlElement.appendChild(bodyElement);
        Element styleElement = htmlDoc.createElement("style");
        styleElement.setTextContent("td { vertical-align: top }");
        htmlElement.appendChild(styleElement);
        return htmlDoc;
    }

    private HTMLDocumentImpl() {
        super();
    }

    public static Element createAndAppendElement(Document htmlDocument, Node parentElement, String childElementTitle,
                                                 String childElementValue){
        Element childElement = htmlDocument.createElement(childElementTitle);
        if (childElementValue != null)
            childElement.setTextContent(childElementValue);
        parentElement.appendChild(childElement);
        return childElement;
    }

    public static void createAndAppendAttribute(Element element, String attributeName, String attributeValue){
        element.setAttribute(attributeName, attributeValue);
    }

}
