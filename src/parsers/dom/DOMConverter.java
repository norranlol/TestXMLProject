package parsers.dom;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import parsers.helpers.HTMLConvertHelper;
import parsers.dom.html.HTMLDocumentImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DOMConverter {

    private Document xmlDocument;
    private Document htmlDocument;
    private String fileName;
    private LSSerializer lsSerializer;
    private final String ID_FIELD = "id";
    private final String TIME_OF_DEPARTURE_FIELD = "timeOfDeparture";
    private final String TIME_OF_ARRIVAL_FIELD = "timeOfArrival";
    private final String PILOT_FIELD = "pilot";
    private final String PLANE_FIELD = "plane";
    private final String ROUTE_FIELD = "route";
    private final String TICKET_FIELD = "ticket";

    public DOMConverter(Document xmlDocument, String fileName){
        this.xmlDocument = xmlDocument;
        this.fileName = fileName;
    }

    public void convertToHTML(){
        htmlDocument = HTMLDocumentImpl.makeBasicHtmlDoc("Моя тестовая HTML-страница");
        Element flightsElement = xmlDocument.getDocumentElement();
        Node bodyElement = htmlDocument.getElementsByTagName("body").item(0);
        NodeList nodeList = flightsElement.getElementsByTagName("flight");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, bodyElement, "table", null);
            NamedNodeMap namedNodeMap = node.getAttributes();
            for (int j = 0; j < namedNodeMap.getLength(); j++){
                Node attrNode = namedNodeMap.item(j);
                HTMLDocumentImpl.createAndAppendAttribute(tableElement, "id", "flight_certificates_" + attrNode.getNodeValue());
                setWidthAndBorderForTable(tableElement);
            }
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, bodyElement, "br", null);
            createTableHeaders(htmlDocument, tableElement);
            fillTableData(htmlDocument, tableElement, node);
        }
        initializeAndWriteToHTML();
    }

    private void createTableHeaders(Document htmlDocument, Element tableElement){
        Element trElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "tr", null);
        for (String col : HTMLConvertHelper.MAIN_COLUMNS)
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "th", col);
    }

    private void fillTableData(Document htmlDocument, Element tableElement, Node node){
        Element flightElement = (Element) node;
        int pilotCount = flightElement.getElementsByTagName("pilot").getLength();
        int ticketCount = flightElement.getElementsByTagName("ticket").getLength();
        NodeList childNodes = node.getChildNodes();
        Element trElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "tr", null);
        ArrayList<Node> pilotNodes = new ArrayList<Node>();
        ArrayList<Node> ticketNodes = new ArrayList<Node>();
        for (int j = 0; j < childNodes.getLength(); j++){
            switch (childNodes.item(j).getNodeName()){
                case ID_FIELD:
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", childNodes.item(j).getTextContent());
                    System.out.println("ID FOUND");
                    break;
                case TIME_OF_DEPARTURE_FIELD:
                    System.out.println("TIME_OF_DEPARTURE_FOUND");
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td",
                            HTMLConvertHelper.getReadableDateTime(childNodes.item(j).getTextContent()));
                    break;
                case TIME_OF_ARRIVAL_FIELD:
                    System.out.println("TIME_OF_ARRIVAL_FOUND");
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td",
                            HTMLConvertHelper.getReadableDateTime(childNodes.item(j).getTextContent()));
                    break;
                case PILOT_FIELD:
                    System.out.println("PILOT_FOUND");
                    pilotNodes.add(childNodes.item(j));
                    if (pilotNodes.size() == pilotCount)
                        fillInfoAboutPilots(htmlDocument, trElement, pilotNodes);
                    break;
                case PLANE_FIELD:
                    System.out.println("PLANE_FOUND");
                    fillInfoAboutPlane(htmlDocument, trElement, childNodes.item(j));
                    break;
                case ROUTE_FIELD:
                    System.out.println("ROUTE_FOUND");
                    fillInfoAboutRoute(htmlDocument, trElement, childNodes.item(j));
                    break;
                case TICKET_FIELD:
                    System.out.println("TICKET_FOUND");
                    ticketNodes.add(childNodes.item(j));
                    if (ticketNodes.size() == ticketCount)
                        fillInfoAboutTickets(htmlDocument, trElement, ticketNodes);
                    break;
            }
        }
    }

    private void fillInfoAboutPilots(Document htmlDocument, Element trElement, ArrayList<Node> pilotNodes){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        for (int i = 0; i < pilotNodes.size(); i++){
            Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "caption", "Пилот " + (i + 1));
            setWidthAndBorderForTable(tableElement);
            HashMap<String, String> dataMap = HTMLConvertHelper.getLocalizedDataForPilot(pilotNodes.get(i));
            createTableForDataMap(dataMap, tableElement);
        }
    }

    private void fillInfoAboutRoute(Document htmlDocument, Element trElement, Node routeNode){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
        setWidthAndBorderForTable(tableElement);
        HashMap<String, String> dataMap = HTMLConvertHelper.getLocalizedDataForRoute(routeNode);
        createTableForDataMap(dataMap, tableElement);
    }

    private void fillInfoAboutPlane(Document htmlDocument, Element trElement, Node planeNode){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
        setWidthAndBorderForTable(tableElement);
        HashMap<String, String> dataMap = HTMLConvertHelper.getLocalizedDataForPlane(planeNode);
        createTableForDataMap(dataMap, tableElement);
    }

    private void fillInfoAboutTickets(Document htmlDocument, Element trElement, ArrayList<Node> ticketNodes){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        for (int i = 0; i < ticketNodes.size(); i++){
            Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "caption", "Билет № " + (i + 1));
            setWidthAndBorderForTable(tableElement);
            HashMap<String, String> dataMap = HTMLConvertHelper.getLocalizedDataForTicket(ticketNodes.get(i));
            createTableForDataMap(dataMap, tableElement);
        }
    }

    private void createTableForDataMap(HashMap<String, String> dataMap, Element tableElement){
        for (String key : dataMap.keySet()) {
            Element innerTrElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "tr", null);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, innerTrElement, "td", key);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, innerTrElement, "td", dataMap.get(key));
        }
    }

    private void setWidthAndBorderForTable(Element tableElement){
        HTMLDocumentImpl.createAndAppendAttribute(tableElement, "border", "1");
        HTMLDocumentImpl.createAndAppendAttribute(tableElement, "width", "100%");
    }

    public void initializeAndWriteToHTML(){
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS domImplLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
            lsSerializer = domImplLS.createLSSerializer();
            DOMConfiguration domConfig = lsSerializer.getDomConfig();
            domConfig.setParameter("format-pretty-print", true);  //if you want it pretty and indented
            LSOutput lsOutput = domImplLS.createLSOutput();
            lsOutput.setEncoding(xmlDocument.getInputEncoding());
            try (OutputStream os = new FileOutputStream(new File(fileName), false)) {
                lsOutput.setByteStream(os);
                lsSerializer.write(htmlDocument, lsOutput);
            } catch (FileNotFoundException fe) {
                System.out.println("File Not Found");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (Exception e){
            System.out.println("Some problems with instantiations");
        }
    }
}
