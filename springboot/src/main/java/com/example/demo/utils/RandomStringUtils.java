package com.example.demo.utils;

import java.util.Random;

public class RandomStringUtils {
    public static String randomString(int Len) {
        String[] baseString = {"0", "1", "2", "3",
                "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e",
                "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y",
                "z", "A", "B", "C", "D",
                "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        Random random = new Random();
        int length = baseString.length;
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += baseString[random.nextInt(length)];
        }
        random = new Random(System.currentTimeMillis());
        String resultStr = "";
        for (int i = 0; i < Len; i++) {
            resultStr += randomString.charAt(random.nextInt(randomString.length() - 1));
        }
        return resultStr;
    }
}
