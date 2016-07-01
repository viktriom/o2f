package com.vt.persistence.converters;

/**
 * Created by sonu on 27/06/16.
 */
public class ConversionFactory {

    public Converter getConverter(String type){
        Converter converter = null;
        if(type.equalsIgnoreCase("Integer")){
            converter = new StringToIntConverter();
        }else if(type.equalsIgnoreCase("String")){
            converter = new StringToStringConverter();
        }
        return converter;
    }
}
