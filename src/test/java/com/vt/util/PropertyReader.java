package com.vt.util;

import com.vt.exceptions.AppInitException;
import com.vt.properties.ApplicationProperties;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by sonu on 25/06/16.
 */
public class PropertyReader {

    private Logger log = Logger.getLogger(PropertyReader.class);

    String propertyFileName;
    String fullyQualifiedClassName;
    private Properties properties;
    private Class applicationPropertyClass;
    private Object newInstance;


    public PropertyReader(String propertyFileName, String fullyQualifiedClassName) {
        this.propertyFileName = propertyFileName;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        properties = new Properties();
    }

    public void initializeClassFieldsFromPropertiesFile(boolean isStatic) throws AppInitException{

        if(!fullyQualifiedClassName.contains(".")){

        }
        try{
            loadPropertiesFile();
            instantiateClassAndInjectProperties(isStatic);
        } catch (FileNotFoundException e) {
            String errorMessage = "Error while loading the application properties, Unable to find the properties file.\'"
                    + propertyFileName +"\'";
            log.error(errorMessage);
            e.printStackTrace();
            throw new AppInitException(errorMessage);
        } catch (IOException e) {
            log.error("Error while loading the application properties, Unable to find the properties file.");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void instantiateClassAndInjectProperties(boolean isStatic)  {
        instantiateClass();
        for(String propertyName : properties.stringPropertyNames()){
            injectValueForAttribute(propertyName, isStatic);
        }
    }

    private void instantiateClass(){
        try {
            applicationPropertyClass = Class.forName(fullyQualifiedClassName);
        } catch (ClassNotFoundException e) {
            log.error("Error while loading system properties, unable to create instance of property class.");
            e.printStackTrace();
        }
    }

    private void injectValueForAttribute(String propertyName, Boolean isStatic) {
        String methodName = ApplicationProperties.getSetterMethodPrefix() +ApplicationUtil.capitalizeFirstLetter(propertyName);
        String parameter = properties.getProperty(propertyName);
        Method method;

        try {
            method = applicationPropertyClass.getDeclaredMethod(methodName, String.class);
            if(isStatic){
                Constructor constructor = applicationPropertyClass.getConstructor();
                newInstance = constructor.newInstance();
                method.invoke(null, parameter);
            }else{
                method.invoke(newInstance, parameter);
            }
        } catch (IllegalAccessException e) {
            log.error("[IllegalAccessException] - The method \"" + methodName +"\" of Class \"" + fullyQualifiedClassName + "\"");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            log.error("[InvocationTargetException] - The method \"" + methodName +"\" of Class \"" + fullyQualifiedClassName + "\"");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            log.error("[NoSuchMethodException] - The method \"" + methodName +"\" does not exist in Class \"" + fullyQualifiedClassName + "\"");
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void loadPropertiesFile() throws URISyntaxException, IOException, AppInitException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(propertyFileName);
        if(resource == null || resource.toURI() == null){
            String errorMessage = "Unable to create property file URI, Property file \'"+ propertyFileName +
                    "\' not found in class path, cannot proceed further.";
            log.error(errorMessage);
            throw new AppInitException(errorMessage);
        }
        FileInputStream propertyInputStream = new FileInputStream(new File(resource.toURI()));
        properties.load(propertyInputStream);
    }

    public String getPropertyFileName() {
        return propertyFileName;
    }

    public void setPropertyFileName(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }
}