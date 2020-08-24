package ui.console.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatUtils {

    public static DecimalFormat DecimalFormat =new DecimalFormat("##.##");
    public static final String DATE_PATTERN = "dd/mm-hh:mm";
    public static DateFormat DateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
    public static String messageFormat(String i_InputString){
        final String separationLine = "============================================";
        StringBuilder outputString = new StringBuilder();
        outputString.append("\n").append(separationLine).append("\n").append(i_InputString).append("\n").append(separationLine).append("\n");
        return outputString.toString();
    }
}
