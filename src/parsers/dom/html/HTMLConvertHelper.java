package parsers.dom.html;

public class HTMLConvertHelper {

    public static String getReadableDateTime(String dateTime){
        return dateTime.replace("T"," ");
    }

}
