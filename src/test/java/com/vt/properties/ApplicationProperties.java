package com.vt.properties;

/**
 * Created by sonu on 25/06/16.
 */
public class ApplicationProperties {

    private static String traderDataFileName;
    private static boolean isFirstRowHeader;
    private static String traderDataFileDelimiter;
    private static String dataFileDirectory;
    private static final String setterMethodPrefix= "set";
    private static final String getterMethodPrefix= "get";

    private static String orderOpenStatus;
    private static String orderCloseStatus;
    private static String orderGainSide;
    private static String orderLossSide;

    public static String getTraderDataFileName() {
        return traderDataFileName;
    }

    public static void setTraderDataFileName(String traderDataFileName) {
        ApplicationProperties.traderDataFileName = traderDataFileName;
    }

    public static boolean getIsFirstRowHeader() {
        return isFirstRowHeader;
    }

    public static void setIsFirstRowHeader(String isFirstRowHeader) {
        ApplicationProperties.isFirstRowHeader = isFirstRowHeader.equalsIgnoreCase("1");
    }

    public static String getTraderDataFileDelimiter() {
        return traderDataFileDelimiter;
    }

    public static void setTraderDataFileDelimiter(String traderDataFileDelimiter) {
        ApplicationProperties.traderDataFileDelimiter = traderDataFileDelimiter;
    }

    public static String getDataFileDirectory() {
        return dataFileDirectory;
    }

    public static void setDataFileDirectory(String dataFileDirectory) {
        ApplicationProperties.dataFileDirectory = dataFileDirectory;
    }

    public static String getSetterMethodPrefix() {
        return setterMethodPrefix;
    }

    public static String getGetterMethodPrefix() {
        return getterMethodPrefix;
    }

    public static String getOrderOpenStatus() {
        return orderOpenStatus;
    }

    public static void setOrderOpenStatus(String orderOpenStatus) {
        ApplicationProperties.orderOpenStatus = orderOpenStatus;
    }

    public static String getOrderCloseStatus() {
        return orderCloseStatus;
    }

    public static void setOrderCloseStatus(String orderCloseStatus) {
        ApplicationProperties.orderCloseStatus = orderCloseStatus;
    }

    public static String getOrderGainSide() {
        return orderGainSide;
    }

    public static void setOrderGainSide(String orderGainSide) {
        ApplicationProperties.orderGainSide = orderGainSide;
    }

    public static String getOrderLossSide() {
        return orderLossSide;
    }

    public static void setOrderLossSide(String orderLossSide) {
        ApplicationProperties.orderLossSide = orderLossSide;
    }
}
