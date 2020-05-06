package ru.kdp.core;

public class Util {

	public static int GetIntFromString(String value) {
        return GetIntFromString(value,0);
    }
        public static int GetIntFromString(String value,int defvalue) {
        int result;

        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result = defvalue;
        }

        return result;
    }

}
