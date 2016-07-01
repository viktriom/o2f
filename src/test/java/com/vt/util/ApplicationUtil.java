package com.vt.util;

/**
 * Created by sonu on 28/06/16.
 */
public class ApplicationUtil {

    public static String capitalizeFirstLetter(String word){
        int intChar = word.charAt(0);
        if(intChar > 96 && intChar < 124){
            char c = (char) (intChar - 32);
            word = String.valueOf(c) + word.substring(1,word.length());
        }
        return word;
    }
}
