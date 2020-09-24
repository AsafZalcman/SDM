package ui.javafx.utils;

import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class FormatUtils {
    public static DecimalFormat DecimalFormat = new DecimalFormat("##.##");

    public static TextFormatter<Integer> getIntegerFormatter() {
        UnaryOperator<TextFormatter.Change> intFilter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        return new TextFormatter<>(intFilter);
    }

    public static TextFormatter<Double> getDoubleFormatter() {
       Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String text = change.getControlNewText();
            if (text.isEmpty() || (validEditingState.matcher(text).matches() && text.length() != text.chars().filter(ch -> ch == '0' || ch == '.').count())) {
                return change;
            } else {
                return null;
            }
        };

        return new TextFormatter<>(doubleFilter);
    }

    public static TextFormatter<Double> getLetterFormatter() {
        UnaryOperator<TextFormatter.Change> letterFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("[a-zA-Z]*")) {
                return change;
            }

            return null;
        };

        return new TextFormatter<>(letterFilter);
    }
}
