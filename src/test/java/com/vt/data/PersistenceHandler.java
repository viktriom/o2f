package com.vt.data;

import com.vt.o2f.exceptions.PersistenceException;
import com.vt.o2f.exceptions.UnmappedBeanException;

import java.util.List;

/**
 * Created by sonu on 27/06/16.
 */
public interface PersistenceHandler {
    List<Object> readData(String fullyQualifiedClassName) throws UnmappedBeanException, PersistenceException;
    void writeData(String fullyQualifiedClassName, List<Object> objects) throws UnmappedBeanException, PersistenceException;
}
