package ru.spbstu.java.ui.filter;

import javafx.scene.control.TextFormatter;

import java.awt.event.KeyEvent;
import java.util.function.UnaryOperator;

public class TextFieldFilters {
    public static UnaryOperator<TextFormatter.Change> getDoubleFilter() {
        return c -> {
            String text = c.getText();
            if (text.isEmpty()) {
                return c;
            }

            if (Character.isDigit(text.charAt(0)) || KeyEvent.getExtendedKeyCodeForChar(text.charAt(0)) == 46) {
                return c;
            } else {
                return null;
            }
        };
    }

    public static UnaryOperator<TextFormatter.Change> getIntegerFilter() {
        return c -> {
            String text = c.getText();
            if (text.isEmpty()) {
                return c;
            }

            if (Character.isDigit(text.charAt(0))) {
                return c;
            } else {
                return null;
            }
        };
    }

    public static UnaryOperator<TextFormatter.Change> getStringFilter() {
        return c -> {
            String text = c.getText();
            if (text.isEmpty()) {
                return c;
            }

            if (Character.isAlphabetic(text.charAt(0)) && !Character.isDigit(text.charAt(0))) {
                return c;
            } else {
                return null;
            }
        };
    }


}
