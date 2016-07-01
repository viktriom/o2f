package com.vt.o2f;

import com.vt.data.PersistenceHandler;
import com.vt.o2f.binder.FileDataBinder;
import com.vt.o2f.dataWriter.BeanToFileConverter;
import com.vt.o2f.exceptions.PersistenceException;
import com.vt.o2f.exceptions.UnmappedBeanException;

import java.util.List;

/**
 * Created by sonu on 28/06/16.
 */
public class DataPersistence implements PersistenceHandler {
    BeanToFileConverter beanToFileConverter;
    FileDataBinder fileDataBinder;

    public DataPersistence(){
        beanToFileConverter = new BeanToFileConverter();
        fileDataBinder = new FileDataBinder();
    }


    @Override
    public List<Object> readData(String fullyQualifiedClassName) throws UnmappedBeanException, PersistenceException {
        return fileDataBinder.readData(fullyQualifiedClassName);
    }

    @Override
    public void writeData(String fullyQualifiedClassName, List<Object> objects) throws UnmappedBeanException, PersistenceException {
        beanToFileConverter.writeData(fullyQualifiedClassName, objects);
    }
}
