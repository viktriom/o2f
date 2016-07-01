package com.vt.o2f.converters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sonu on 27/06/16.
 */
public class StringToStringConverter implements Converter {
    public void convert(Object instance, Method method, String data) throws InvocationTargetException, IllegalAccessException {
        method.invoke(instance, data);
    }
}
