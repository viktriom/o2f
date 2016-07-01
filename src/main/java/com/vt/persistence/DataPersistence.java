package com.vt.persistence;

import com.xyz.trader.data.PersistenceHandler;
import com.xyz.trader.exceptions.TraderException;
import com.xyz.trader.persistence.binder.FileDataBinder;
import com.xyz.trader.persistence.dataWriter.BeanToFileConverter;
import com.xyz.trader.persistence.exceptions.UnmappedBeanException;

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
    public List<Object> readData(String fullyQualifiedClassName) throws UnmappedBeanException, TraderException {
        return fileDataBinder.readData(fullyQualifiedClassName);
    }

    @Override
    public void writeData(String fullyQualifiedClassName, List<Object> objects) throws UnmappedBeanException, TraderException {
        beanToFileConverter.writeData(fullyQualifiedClassName, objects);
    }
}
