package parsers.sax;

import org.xml.sax.Attributes;
import parsers.helpers.HTMLConvertHelper;

import java.util.*;

public class SAXConverter {

    private int pilotCounter = 0;
    private int pointCounter = 0;
    private int ticketCounter = 0;
    private boolean isPilotClosed = false;
    private HashMap<String, String> pilotAttributes = new LinkedHashMap<String, String>();
    private String imagePoint = null;
    private String position = null;

    public String getValueOfAttributeByName(Attributes attributes, String attributeName){
        for (int i = 0; i < attributes.getLength(); i++){
            String qName = attributes.getQName(i);
            if (qName.equals(attributeName))
                return attributes.getValue(i);
        }
        return "";
    }

    public void createSimpleElementWithCurrentWhitespaces(String elementName, String elementValue, int whitespacesCount){
        String newElement = getWhitespaceString(whitespacesCount);
        newElement += "<" + elementName + ">" + elementValue + "</" + elementName + ">";
        System.out.println(newElement);
    }

    private String getWhitespaceString(int whitespacesCount){
        String newElement = "";
        String whiteSpace = "    ";
        for (int i = 0; i < whitespacesCount; i++)
            newElement += whiteSpace;
        return newElement;
    }

    public void createComplexTrElementWithCurrentWhitespaces(String localizedName, String value, int whitespacesCount){
        String whitespacesStr = getWhitespaceString(whitespacesCount);
        String innerWhitespacesStr = getWhitespaceString(whitespacesCount + 1);
        System.out.println(whitespacesStr + "<tr>");
        System.out.println(innerWhitespacesStr + "<td>" + localizedName + "</td>");
        System.out.println(innerWhitespacesStr + "<td>" + value + "</td>");
        System.out.println(whitespacesStr + "</tr>");
    }

    public String findLatestKeyForMap(HashMap<String, String> fieldMap){
        Set<String> keySet = fieldMap.keySet();
        String lastElement = null;
        for (Iterator<String> collectionItr = keySet.iterator(); collectionItr.hasNext(); ) {
            lastElement = collectionItr.next();
        }
        return lastElement;
    }

    public void convertPilotFields(String qName, String value){
        //Костыль, чтобы поля surname, name, patronymic не попадали в HTML
        if (SAXParserHandler.currentRootElement.equals(HTMLConvertHelper.CLIENT_FIELD))
            return;
        //Описываем элементы
        String localizedName = HTMLConvertHelper.PILOT_FIELDS.get(qName);
        createComplexTrElementWithCurrentWhitespaces(localizedName, value, 6);

        String latestKey = findLatestKeyForMap(HTMLConvertHelper.PILOT_FIELDS);
        //Если последняя строка элемента pilot, то описываем атрибуты и закрываем таблицу
        if (qName.equals(latestKey)){
            for (String key : pilotAttributes.keySet())
                createComplexTrElementWithCurrentWhitespaces(key, pilotAttributes.get(key), 6);
            System.out.println("                    </table>");
        }
    }

    public void convertPlaneFields(String qName, String value){
        String localizedName = HTMLConvertHelper.PLANE_FIELDS.get(qName);
        createComplexTrElementWithCurrentWhitespaces(localizedName, value, 6);
        String latestKey = findLatestKeyForMap(HTMLConvertHelper.PLANE_FIELDS);
        if (qName.equals(latestKey))
            endInnerTable();
    }

    private void endInnerTable(){
        System.out.println("                    </table>");
        System.out.println("                </td>");
    }

    public void convertRouteFields(String qName, String value){
        String localizedName = HTMLConvertHelper.ROUTE_FIELDS.get(qName);
        createComplexTrElementWithCurrentWhitespaces(localizedName, value, 6);
        String latestKey = findLatestKeyForMap(HTMLConvertHelper.ROUTE_FIELDS);
        if (qName.equals(latestKey))
            endInnerTable();
    }

    public void convertPointFields(String qName, String value){
        String localizedName = HTMLConvertHelper.POINT_FIELDS.get(qName);
        createComplexTrElementWithCurrentWhitespaces(localizedName, value, 6);
        String latestKey = findLatestKeyForMap(HTMLConvertHelper.POINT_FIELDS);
        if (qName.equals(latestKey)){
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.POINT_FIELDS.get
                    (HTMLConvertHelper.IMAGE_TITLE), imagePoint, 6);
        }
    }

    public void convertTicketFields(String qName, String value){
        String localizedName = HTMLConvertHelper.TICKET_FIELDS.get(qName);
        createComplexTrElementWithCurrentWhitespaces(localizedName, value, 6);
        String latestKey = findLatestKeyForMap(HTMLConvertHelper.TICKET_FIELDS);
        //Если последняя строка элемента ticket, то описываем атрибуты и закрываем таблицу
        if (qName.equals(latestKey)){
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.TICKET_FIELDS.get
                    (HTMLConvertHelper.POSITION_TITLE), position, 6);
            System.out.println("                    </table>");
        }
    }

    public void fillPilotAttributes(Attributes attributes){
        for (String keyStr : HTMLConvertHelper.PILOT_FIELDS.keySet()){
            String attributeValue = getValueOfAttributeByName(attributes, keyStr);
            if (!attributeValue.equals(""))
                pilotAttributes.put(HTMLConvertHelper.PILOT_FIELDS.get(keyStr), attributeValue);
        }
    }

    public void fillPointAttributes(Attributes attributes){
        imagePoint = getValueOfAttributeByName(attributes, HTMLConvertHelper.IMAGE_TITLE);
    }

    public void fillTicketAttributes(Attributes attributes){
        position = getValueOfAttributeByName(attributes, HTMLConvertHelper.POSITION_TITLE);
    }

    public void convertAllIdFields(String qName, String value, String currentRootElement){
        if (currentRootElement.equals(HTMLConvertHelper.PILOT_FIELD)){
            //Создаем единый td для всех таблиц пилотов
            if (pilotCounter == 0)
                System.out.println("                <td>");
            pilotCounter++;
            System.out.println("                    <table border=\"1\" width=\"100%\">");
            createSimpleElementWithCurrentWhitespaces("caption", "Пилот " + pilotCounter, 6);
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.ID_TEXT, value, 6);
        } else if (currentRootElement.equals(HTMLConvertHelper.PLANE_FIELD)) {
            //Закрываем таблицу пилотов
            if (!isPilotClosed) {
                System.out.println("                </td>");
                isPilotClosed = true;
            }
            startInnerTable();
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.ID_TEXT, value, 6);
        } else if (currentRootElement.equals(HTMLConvertHelper.ROUTE_FIELD)){
            startInnerTable();
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.ID_TEXT, value, 6);
        } else if (currentRootElement.equals(HTMLConvertHelper.POINT_FIELD)){
            createComplexTrElementWithCurrentWhitespaces("Точка " + (pointCounter + 1), "", 6);
            pointCounter++;
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.ID_TEXT, value, 6);
        } else if (currentRootElement.equals(HTMLConvertHelper.TICKET_FIELD)){
            if (ticketCounter == 0)
                System.out.println("                <td>");
            ticketCounter++;
            System.out.println("                    <table border=\"1\" width=\"100%\">");
            createSimpleElementWithCurrentWhitespaces("caption", "Билет " + ticketCounter, 6);
            createComplexTrElementWithCurrentWhitespaces(HTMLConvertHelper.ID_TEXT, value, 6);
        } else if (currentRootElement.equals(HTMLConvertHelper.FLIGHT_FIELD)){
            createSimpleElementWithCurrentWhitespaces("td", value, 4);
            pilotCounter = 0;
            pointCounter = 0;
            isPilotClosed = false;
            imagePoint = null;
        }
    }

    private void startInnerTable(){
        System.out.println("                <td>");
        System.out.println("                    <table border=\"1\" width=\"100%\">");
    }

}
