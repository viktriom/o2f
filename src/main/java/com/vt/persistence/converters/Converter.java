package com.vt.persistence.converters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sonu on 27/06/16.
 */
public interface Converter {
    public void convert(Object instance, Method method, String data) throws InvocationTargetException, IllegalAccessException;
}
