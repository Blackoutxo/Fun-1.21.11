package me.blackout.util;

public class StringSeperator {

    public static String letters(String string) {
        StringBuilder letters = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isLetter(c))
               letters.append(c);
        }

        return letters.toString();
    }

    public static int numbers(String string) {
        StringBuilder numbers = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c))
                numbers.append(c);
        }

        return Integer.parseInt(numbers.toString());
    }
}
