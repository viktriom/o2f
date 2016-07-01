package com.vt.persistence.dataWriter;

import com.xyz.trader.exceptions.TraderException;
import com.xyz.trader.persistence.PersistenceProperties;
import com.xyz.trader.persistence.annotations.DataFileMappedBean;
import com.xyz.trader.persistence.annotations.DataFileMappedField;
import com.xyz.trader.persistence.exceptions.UnmappedBeanException;
import com.xyz.trader.util.ApplicationUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by sonu on 28/06/16.
 */
public class BeanToFileConverter {

    private Logger log = Logger.getLogger(BeanToFileConverter.class);

    private String fullyQualifiedClassName;
    private String dataFileName;
    private String dataFileHeader;
    private Class dataBean;
    private DataFileWriter dataFileWriter;

    public BeanToFileConverter(){
    }

    public BeanToFileConverter(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public void writeData(String fullyQualifiedClassName, List<Object> objects) throws UnmappedBeanException, TraderException {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        writeBeansToFile(objects);
    }

    public void writeBeansToFile(List<Object> objects) throws UnmappedBeanException, TraderException {
        try {
            validateEnvironment();
            obtainFileNameFromClass();
            obtainHeaderFromClass();
            writeToFile(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateEnvironment() throws TraderException {
        if(fullyQualifiedClassName == null || fullyQualifiedClassName.isEmpty() || (!fullyQualifiedClassName.contains("."))){
            throw new TraderException("Exception while writing data. Fully qaulified name of the bean to be written not set properly. -> \'" + fullyQualifiedClassName +"\'");
        }
    }

    private void writeToFile(List<Object> objects) throws IOException {
        dataFileWriter.writeStringToFile(dataFileHeader);
        if(null != objects) {
            for (Object obj : objects) {
                StringBuilder recordBuilder = new StringBuilder();
                for (Field field : dataBean.getDeclaredFields()) {
                    prepareDataRowAndWriteToFile(obj, field, recordBuilder);
                }
                dataFileWriter.writeEmptyLine();
                dataFileWriter.writeStringToFile(recordBuilder.toString().substring(1, recordBuilder.toString().length()));
            }
        }
        dataFileWriter.completeWriting();
    }

    private void prepareDataRowAndWriteToFile(Object obj, Field field, StringBuilder recordBuilder) {
        String methodName = PersistenceProperties.getGetterMethodPrefix() +
                ApplicationUtil.capitalizeFirstLetter(field.getName());
        try {
            Method method = dataBean.getDeclaredMethod(methodName);
            String value = String.valueOf(method.invoke(obj));
            recordBuilder.append(",");
            recordBuilder.append(value);
        } catch (NoSuchMethodException e) {
            log.error("The getter method\'"+methodName+"\' NOT found for the field \'"+field.getName()+"\'");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            log.error("UNABLE to invoke getter method\'"+methodName+"\' for the field \'"+field.getName()+"\'");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.error("The getter method\'"+methodName+"\' NOT found for the field \'"+field.getName()+"\'");
            e.printStackTrace();
        }

    }

    private void obtainHeaderFromClass() {
        StringBuilder fileHeaderBuilder = new StringBuilder();
        for(Field field: dataBean.getDeclaredFields()){
            prepareHeaderFromBean(field, fileHeaderBuilder);
        }
        dataFileHeader = fileHeaderBuilder.toString().substring(1,fileHeaderBuilder.toString().length());
    }

    private void prepareHeaderFromBean(Field field, StringBuilder fileHeaderBuilder) {
        if(field.isAnnotationPresent(DataFileMappedField.class)){
            DataFileMappedField annotation = (DataFileMappedField)field.getAnnotation(DataFileMappedField.class);
            fileHeaderBuilder.append(",");
            fileHeaderBuilder.append(annotation.mappedToColumnName());
        }
    }

    private void obtainFileNameFromClass() throws UnmappedBeanException, IOException {
        try {
            dataBean = Class.forName(fullyQualifiedClassName);
            if(dataBean.isAnnotationPresent(DataFileMappedBean.class)){
                DataFileMappedBean dataFileMappedBean = (DataFileMappedBean)dataBean.getAnnotation(DataFileMappedBean.class);
                dataFileName = dataFileMappedBean.mappedToFileName() + dataFileMappedBean.mappedToFileType();
                dataFileWriter = new DataFileWriter(PersistenceProperties.getDataFileDirectory()+dataFileName);
            }else{
                throw new UnmappedBeanException("The bean \""+fullyQualifiedClassName + "\" is not mapped to any file" );
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

}
