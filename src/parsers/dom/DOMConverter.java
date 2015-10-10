package parsers.dom;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import parsers.dom.html.HTMLDocumentImpl;
import parsers.helpers.HTMLConvertHelper;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DOMConverter {

    private Document xmlDocument;
    private Document htmlDocument;
    private String fileName;
    private LSSerializer lsSerializer;


    public DOMConverter(Document xmlDocument, String fileName){
        this.xmlDocument = xmlDocument;
        this.fileName = fileName;
    }

    public void convertToHTML(){
        htmlDocument = HTMLDocumentImpl.makeBasicHtmlDoc("Моя тестовая HTML-страница");
        Element flightsElement = xmlDocument.getDocumentElement();
        Node bodyElement = htmlDocument.getElementsByTagName("body").item(0);
        NodeList nodeList = flightsElement.getElementsByTagName(HTMLConvertHelper.FLIGHT_FIELD);
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
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, bodyElement, "br", null);
            createTableHeaders(htmlDocument, tableElement);
            fillTableData(htmlDocument, tableElement, node);
        }
        initializeAndWriteToHTML();
    }

    private void createTableHeaders(Document htmlDocument, Element tableElement){
        Element tHeadElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "thead", null);
        Element trElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tHeadElement, "tr", null);
        for (String col : HTMLConvertHelper.MAIN_COLUMNS)
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "th", col);
    }

    private void fillTableData(Document htmlDocument, Element tableElement, Node node){
        String firstStamp = "";
        String secondStamp = "";
        Element flightElement = (Element) node;
        int pilotCount = flightElement.getElementsByTagName("pilot").getLength();
        int ticketCount = flightElement.getElementsByTagName("ticket").getLength();
        NodeList childNodes = node.getChildNodes();
        Element tBodyElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "tbody", null);
        Element trElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tBodyElement, "tr", null);
        ArrayList<Node> pilotNodes = new ArrayList<Node>();
        ArrayList<Node> ticketNodes = new ArrayList<Node>();
        for (int j = 0; j < childNodes.getLength(); j++){
            switch (childNodes.item(j).getNodeName()){
                case HTMLConvertHelper.ID_FIELD:
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", childNodes.item(j).getTextContent());
                    break;
                case HTMLConvertHelper.TIME_OF_DEPARTURE_FIELD:
                    firstStamp = HTMLConvertHelper.getReadableDateTime(childNodes.item(j).getTextContent());
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", firstStamp);
                    break;
                case HTMLConvertHelper.TIME_OF_ARRIVAL_FIELD:
                    secondStamp = HTMLConvertHelper.getReadableDateTime(childNodes.item(j).getTextContent());
                    HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td",
                            secondStamp);
                    fillDurationOfFlight(firstStamp, secondStamp, htmlDocument, trElement);
                    break;
                case HTMLConvertHelper.PILOT_FIELD:
                    pilotNodes.add(childNodes.item(j));
                    if (pilotNodes.size() == pilotCount)
                        fillInfoAboutPilots(htmlDocument, trElement, pilotNodes);
                    break;
                case HTMLConvertHelper.PLANE_FIELD:
                    fillInfoAboutPlane(htmlDocument, trElement, childNodes.item(j));
                    break;
                case HTMLConvertHelper.ROUTE_FIELD:
                    fillInfoAboutRoute(htmlDocument, trElement, childNodes.item(j));
                    break;
                case HTMLConvertHelper.TICKET_FIELD:
                    ticketNodes.add(childNodes.item(j));
                    if (ticketNodes.size() == ticketCount)
                        fillInfoAboutTickets(htmlDocument, trElement, ticketNodes);
                    break;
            }
        }
    }

    private void fillDurationOfFlight(String firstStamp, String secondStamp, Document htmlDocument,
                                      Element trElement){
        String difference = HTMLConvertHelper.calculateDifferenceBetweenTimestamps(Timestamp.valueOf(firstStamp),
                Timestamp.valueOf(secondStamp));
        HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", difference);
    }

    private void fillInfoAboutPilots(Document htmlDocument, Element trElement, ArrayList<Node> pilotNodes){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        for (int i = 0; i < pilotNodes.size(); i++){
            Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "caption", "Пилот " + (i + 1));
            setWidthAndBorderForTable(tableElement);
            HashMap<String, String> dataMap = getLocalizedDataForNode(pilotNodes.get(i),
                    HTMLConvertHelper.PILOT_FIELDS);
            createTableForDataMap(dataMap, tableElement);
        }
    }

    private void fillInfoAboutRoute(Document htmlDocument, Element trElement, Node routeNode){
        HashMap<String, String> dataMap = getLocalizedDataForRoute(routeNode);
        fillInfoAboutElement(htmlDocument, trElement, routeNode, dataMap);
    }

    private void fillInfoAboutPlane(Document htmlDocument, Element trElement, Node planeNode){
        HashMap<String, String> dataMap = getLocalizedDataForNode(planeNode, HTMLConvertHelper.PLANE_FIELDS);
        fillInfoAboutElement(htmlDocument, trElement, planeNode, dataMap);
    }

    private void fillInfoAboutElement(Document htmlDocument, Element trElement, Node planeNode,
                                      HashMap<String, String> dataMap){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
        setWidthAndBorderForTable(tableElement);
        createTableForDataMap(dataMap, tableElement);
    }

    private void fillInfoAboutTickets(Document htmlDocument, Element trElement, ArrayList<Node> ticketNodes){
        Element tdElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", null);
        int ticketCounter = 0;
        int priceCounter = 0;
        for (int i = 0; i < ticketNodes.size(); i++){
            Element tableElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tdElement, "table", null);
            HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "caption", "Билет № " + (i + 1));
            setWidthAndBorderForTable(tableElement);
            HashMap<String, String> dataMap = getLocalizedDataForNode(ticketNodes.get(i),
                    HTMLConvertHelper.TICKET_FIELDS);
            ticketCounter++;
            priceCounter += Integer.valueOf(dataMap.get(HTMLConvertHelper.PRICE));
            createTableForDataMap(dataMap, tableElement);
        }
        Element tableElement = (Element) trElement.getParentNode().getParentNode();
        Element tFootElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tableElement, "tfoot", null);
        fillValueForFooter(tFootElement, String.valueOf("Итого продано: " + ticketCounter));
        fillValueForFooter(tFootElement, String.valueOf("Итоговая сумма: " + priceCounter));
    }

    private void fillValueForFooter(Element tFootElement, String value){
        Element trElement = HTMLDocumentImpl.createAndAppendElement(htmlDocument, tFootElement, "tr", null);
        HTMLDocumentImpl.createAndAppendElement(htmlDocument, trElement, "td", value);
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

    public static HashMap<String, String> getSimpleDataFromNode(Node rootNode){
        HashMap<String, String> dataMap = new LinkedHashMap<String, String>();
        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            dataMap.put(node.getNodeName(), node.getTextContent());
        }
        NamedNodeMap attributesMap = rootNode.getAttributes();
        for (int i = 0; i < attributesMap.getLength(); i++){
            Node node = attributesMap.item(i);
            dataMap.put(node.getNodeName(), node.getTextContent());
        }
        return dataMap;
    }

    public static HashMap<String, String> getLocalizedDataForNode(Node rootNode, HashMap<String, String> localizedHashMap){
        HashMap<String, String> dataMap = getSimpleDataFromNode(rootNode);
        ArrayList<String> initialKeyList = new ArrayList<String>(dataMap.keySet());
        for (String key : initialKeyList){
            if (key.equals("#text") || HTMLConvertHelper.UNPARSED_ELEMENTS.contains(key))
                continue;
            String localizedName = localizedHashMap.get(key);
            dataMap.put(localizedName, dataMap.get(key));
        }
        for (String initialKey : initialKeyList)
            dataMap.remove(initialKey);
        return dataMap;
    }

    public static HashMap<String, String> getLocalizedDataForRoute(Node routeNode){
        HashMap<String, String> routeMap = getLocalizedDataForNode(routeNode, HTMLConvertHelper.ROUTE_FIELDS);
        Element routeElement = (Element) routeNode;
        NodeList pointList = routeElement.getElementsByTagName("point");
        for (int i = 0; i < pointList.getLength(); i++){
            routeMap.put("Точка " + (i + 1), " ");
            HashMap<String, String> pointMap = getLocalizedDataForNode(pointList.item(i), HTMLConvertHelper.POINT_FIELDS);
            for (String key : pointMap.keySet()){
                String value = pointMap.get(key);
                routeMap.put(key + (i + 1), value);
            }
        }
        return routeMap;
    }
}
