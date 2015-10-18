package parsers.helpers;

import java.io.PrintWriter;

public class FileHelper {

    public static void writeToFile(PrintWriter printWriter, String str){
        printWriter.println(str);
        printWriter.flush();
    }
}
