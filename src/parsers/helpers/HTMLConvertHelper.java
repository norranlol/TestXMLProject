package parsers.helpers;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class HTMLConvertHelper {

    public static final String[] MAIN_COLUMNS = new String[]{"ID", "Время отправления", "Время прибытия",
            "Длит. полета", "Пилот", "Самолет", "Маршрут", "Билет"};
    public static final HashMap<String, String> PLANE_FIELDS = new HashMap<String, String>();
    public static final HashMap<String, String> PILOT_FIELDS = new HashMap<String, String>();
    private static final HashMap<String, String> ROUTE_FIELDS = new HashMap<String, String>();
    private static final HashMap<String, String> POINT_FIELDS = new HashMap<String, String>();
    public static final HashMap<String, String> TICKET_FIELDS = new HashMap<String, String>();
    private static final ArrayList<String> UNPARSED_ELEMENTS = new ArrayList<>();

    private static final String PILOT_N = "Пилот №";
    public static final String PRICE = "Цена";

    static {
        //Локализация полей для самолёта
        PLANE_FIELDS.put("id", "ID");
        PLANE_FIELDS.put("model", "Модель");
        PLANE_FIELDS.put("year", "Год");
        PLANE_FIELDS.put("countOfRows", "Количество рядов");
        PLANE_FIELDS.put("placesInRow", "Мест в ряду");
        //Локализация полей для пилота
        PILOT_FIELDS.put("certificateNumber","Номер сертификата");
        PILOT_FIELDS.put("phoneNumber", "Номер телефона");
        PILOT_FIELDS.put("eMail", "EMail");
        PILOT_FIELDS.put("address", "Адрес");
        PILOT_FIELDS.put("id", "ID");
        PILOT_FIELDS.put("surname", "Фамилия");
        PILOT_FIELDS.put("name", "Имя");
        PILOT_FIELDS.put("patronymic", "Отчество");
        PILOT_FIELDS.put("category", "Категория");
        //Локализация полей для маршрута
        ROUTE_FIELDS.put("id", "ID");
        ROUTE_FIELDS.put("title", "Название");
        ROUTE_FIELDS.put("cipher", "Шифр");
        ROUTE_FIELDS.put("distance", "Дистанция");
        //Локализация полей для точки
        POINT_FIELDS.put("id","ID");
        POINT_FIELDS.put("country","Страна");
        POINT_FIELDS.put("city","Город");
        POINT_FIELDS.put("image","Картинка");
        //Локализация полей для билета
        TICKET_FIELDS.put("id","ID");
        TICKET_FIELDS.put("row","Ряд");
        TICKET_FIELDS.put("place","Место");
        TICKET_FIELDS.put("status","Статус");
        TICKET_FIELDS.put("price","Цена");
        TICKET_FIELDS.put("position", "Расположение");
        //Элементы, которые не будут парситься
        UNPARSED_ELEMENTS.add("point");
        UNPARSED_ELEMENTS.add("anonimousClient");
        UNPARSED_ELEMENTS.add("client");
    }

    public static String getReadableDateTime(String dateTime){
        return dateTime.replace("T"," ");
    }

    public static String calculateDifferenceBetweenTimestamps(Timestamp ts1, Timestamp ts2){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(ts1.toString());
            date2 = dateFormat.parse(ts2.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String duration = getDurationBreakdown(date2.getTime() - date1.getTime());
        return duration;
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
            if (key.equals("#text") || UNPARSED_ELEMENTS.contains(key))
                continue;
            String localizedName = localizedHashMap.get(key);
            dataMap.put(localizedName, dataMap.get(key));
        }
        for (String initialKey : initialKeyList)
            dataMap.remove(initialKey);
        return dataMap;
    }

    public static HashMap<String, String> getLocalizedDataForRoute(Node routeNode){
        HashMap<String, String> routeMap = getLocalizedDataForNode(routeNode, ROUTE_FIELDS);
        Element routeElement = (Element) routeNode;
        NodeList pointList = routeElement.getElementsByTagName("point");
        for (int i = 0; i < pointList.getLength(); i++){
            routeMap.put("Точка " + (i + 1), " ");
            HashMap<String, String> pointMap = getLocalizedDataForNode(pointList.item(i), POINT_FIELDS);
            for (String key : pointMap.keySet()){
                String value = pointMap.get(key);
                routeMap.put(key + (i + 1), value);
            }
        }
        return routeMap;
    }

    private static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
            throw new IllegalArgumentException("Duration must be greater than zero!");
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(hours);
        sb.append(" Ч ");
        sb.append(minutes);
        sb.append(" М ");
        return(sb.toString());
    }
}
