package com.vt.o2f;

/**
 * Created by sonu on 28/06/16.
 */
public class PersistenceProperties {
    private static boolean isFirstRowHeader;
    private static String setterMethodPrefix;
    private static String getterMethodPrefix;
    private static String dataFileDirectory;
    private static String dataFileDelimiter;

    public static boolean getIsFirstRowHeader() {
        return isFirstRowHeader;
    }

    public static void setIsFirstRowHeader(boolean isFirstRowHeader) {
        PersistenceProperties.isFirstRowHeader = isFirstRowHeader;
    }

    public static String getSetterMethodPrefix() {
        return setterMethodPrefix;
    }

    public static void setSetterMethodPrefix(String setterMethodPrefix) {
        PersistenceProperties.setterMethodPrefix = setterMethodPrefix;
    }

    public static String getGetterMethodPrefix() {
        return getterMethodPrefix;
    }

    public static void setGetterMethodPrefix(String getterMethodPrefix) {
        PersistenceProperties.getterMethodPrefix = getterMethodPrefix;
    }

    public static String getDataFileDirectory() {
        return dataFileDirectory;
    }

    public static void setDataFileDirectory(String dataFileDirectory) {
        PersistenceProperties.dataFileDirectory = dataFileDirectory;
    }

    public static String getDataFileDelimiter() {
        return dataFileDelimiter;
    }

    public static void setDataFileDelimiter(String dataFileDelimiter) {
        PersistenceProperties.dataFileDelimiter = dataFileDelimiter;
    }
}
