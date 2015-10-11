package parsers.helpers;

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
    public static final HashMap<String, String> PLANE_FIELDS = new LinkedHashMap<String, String>();
    public static final HashMap<String, String> PILOT_FIELDS = new LinkedHashMap<String, String>();
    public static final HashMap<String, String> ROUTE_FIELDS = new LinkedHashMap<String, String>();
    public static final HashMap<String, String> POINT_FIELDS = new LinkedHashMap<String, String>();
    public static final HashMap<String, String> TICKET_FIELDS = new LinkedHashMap<String, String>();
    public static final ArrayList<String> UNPARSED_ELEMENTS = new ArrayList<>();
    public static final String FLIGHTS_FIELD = "flights";
    public static final String FLIGHT_FIELD = "flight";
    public static final String ID_FIELD = "id";
    public static final String TIME_OF_DEPARTURE_FIELD = "timeOfDeparture";
    public static final String TIME_OF_ARRIVAL_FIELD = "timeOfArrival";
    public static final String PILOT_FIELD = "pilot";
    public static final String PLANE_FIELD = "plane";
    public static final String ROUTE_FIELD = "route";
    public static final String POINT_FIELD = "point";
    public static final String TICKET_FIELD = "ticket";
    public static final String CLIENT_FIELD = "client";
    public static final ArrayList<String> ROOT_ELEMENTS = new ArrayList<>();

    public static final String PRICE = "Цена";
    public static final String ID_TEXT = "ID";
    public static final String IMAGE_TITLE = "image";
    public static final String POSITION_TITLE = "position";

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
        POINT_FIELDS.put("image","Картинка");
        POINT_FIELDS.put("country","Страна");
        POINT_FIELDS.put("city","Город");
        //Локализация полей для билета
        TICKET_FIELDS.put("id","ID");
        TICKET_FIELDS.put("position", "Расположение");
        TICKET_FIELDS.put("row","Ряд");
        TICKET_FIELDS.put("place","Место");
        TICKET_FIELDS.put("status","Статус");
        TICKET_FIELDS.put("price", "Цена");
        //Элементы, которые не будут парситься
        UNPARSED_ELEMENTS.add("point");
        UNPARSED_ELEMENTS.add("anonimousClient");
        UNPARSED_ELEMENTS.add("client");
        //Корневые элементы
        ROOT_ELEMENTS.add("flight");
        ROOT_ELEMENTS.add("pilot");
        ROOT_ELEMENTS.add("plane");
        ROOT_ELEMENTS.add("route");
        ROOT_ELEMENTS.add("point");
        ROOT_ELEMENTS.add("ticket");
        ROOT_ELEMENTS.add("client");
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
