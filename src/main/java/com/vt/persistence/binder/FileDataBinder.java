package com.vt.persistence.binder;

import com.xyz.trader.exceptions.TraderException;
import com.xyz.trader.persistence.PersistenceProperties;
import com.xyz.trader.persistence.annotations.DataFileMappedBean;
import com.xyz.trader.persistence.annotations.DataFileMappedField;
import com.xyz.trader.persistence.converters.ConversionFactory;
import com.xyz.trader.persistence.converters.Converter;
import com.xyz.trader.persistence.dataReader.FileDataReader;
import com.xyz.trader.persistence.dataReader.FileDataStore;
import com.xyz.trader.persistence.exceptions.UnmappedBeanException;
import com.xyz.trader.util.ApplicationUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sonu on 26/06/16.
 */
public class FileDataBinder {

    private Logger log = Logger.getLogger(FileDataBinder.class);

    private String fullyQualifiedClassName;
    private FileDataStore fileDataStore;
    private FileDataReader fileDataReader;
    private Class dataBean;
    private String dataFileName;
    private List<Object> recordList;

    public FileDataBinder(){
        fileDataReader = new FileDataReader(PersistenceProperties.getDataFileDelimiter());
        recordList = new LinkedList<Object>();
    }

    public FileDataBinder(String fullyQualifiedClassName) {
        this();
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public void bindFileDataToBean() throws UnmappedBeanException, TraderException {
        if(isConfigurationCorrect()){
            getFileNameToBeRead();
            loadDataFromFileToDataStore();
            convertDataRowsToObjects();
        }else{
            log.error("Could not complete bind due to an configuration error.");
            throw new TraderException("Error binding data to bean, the qualified name provided for bean is in correct. className=" + fullyQualifiedClassName);
        }
    }

    private void convertDataRowsToObjects() {

        for(int i = 0; i < fileDataReader.getNumberOfRowsInFile();i++){
            instantiateAndPopulateBean(i);
        }
    }

    private void instantiateAndPopulateBean(int rowNumber) {
        try {
            Constructor constructor = dataBean.getConstructor();
            Object newInstance = constructor.newInstance();
            for(Field field: dataBean.getDeclaredFields()){
                convertDataAndPopulateBean(newInstance, field, rowNumber);
            }
            recordList.add(rowNumber, newInstance);
        }catch(NoSuchMethodException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void convertDataAndPopulateBean(Object newInstance, Field field, int rowNumber) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        if(field.isAnnotationPresent(DataFileMappedField.class)){
            String methodName = PersistenceProperties.getSetterMethodPrefix() +
                    ApplicationUtil.capitalizeFirstLetter(field.getName());
            DataFileMappedField annotation = (DataFileMappedField)field.getAnnotation(DataFileMappedField.class);
            String mappedColumnName = annotation.mappedToColumnName();
            String fieldType = annotation.mappedToFieldType();
            Method method = dataBean.getDeclaredMethod(methodName, Class.forName("java.lang."+fieldType));
            String fieldValue = fileDataStore.getValueForHeaderAtIndex(mappedColumnName, rowNumber);
            Converter converter = getConverter(fieldType);
            converter.convert(newInstance, method, fieldValue);
        }
    }

    private Converter getConverter(String fieldType){
        ConversionFactory conversionFactory = new ConversionFactory();
        return conversionFactory.getConverter(fieldType);
    }

    private void loadDataFromFileToDataStore() {
        fileDataReader.setCompleteDataFileName(PersistenceProperties.getDataFileDirectory() + dataFileName);
        fileDataStore = fileDataReader.readFile();
    }

    private void getFileNameToBeRead() throws UnmappedBeanException {
        if(dataBean.isAnnotationPresent(DataFileMappedBean.class)){
            DataFileMappedBean dataFileMappedBean = (DataFileMappedBean)dataBean.getAnnotation(DataFileMappedBean.class);
            dataFileName = dataFileMappedBean.mappedToFileName() + dataFileMappedBean.mappedToFileType();
        }else{
            throw new UnmappedBeanException("The bean \""+fullyQualifiedClassName + "\" is not mapped to any file" );
        }
    }

    private boolean isConfigurationCorrect() {
        if(fullyQualifiedClassName == null || fullyQualifiedClassName.length() == 0 || (!fullyQualifiedClassName.contains("."))){
            log.error("Error binding data to bean, the qualified name provided for bean is in correct. className=" + fullyQualifiedClassName);
            return false;
        }
        try {
            dataBean = Class.forName(fullyQualifiedClassName);
        } catch (ClassNotFoundException e) {
            log.error("Error binding data to bean, the qualified name provided for bean is not correct. className=" + fullyQualifiedClassName);
            return false;
        }
        return true;
    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
    }

    public FileDataStore getFileDataStore() {
        return fileDataStore;
    }

    public void setFileDataStore(FileDataStore fileDataStore) {
        this.fileDataStore = fileDataStore;
    }

    public List<Object> getRecordList() {
        return recordList;
    }

    public FileDataReader getFileDataReader() {
        return fileDataReader;
    }

    public List<Object> readData(String fullyQualifiedClassName) throws UnmappedBeanException, TraderException {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        bindFileDataToBean();
        return getRecordList();
    }
}
