package com.vt.o2f.binder;

import com.vt.o2f.exceptions.PersistenceException;
import com.vt.o2f.PersistenceProperties;
import com.vt.o2f.annotations.DataFileMappedBean;
import com.vt.o2f.annotations.DataFileMappedField;
import com.vt.o2f.converters.ConversionFactory;
import com.vt.o2f.converters.Converter;
import com.vt.o2f.dataReader.FileDataReader;
import com.vt.o2f.dataReader.DataTable;
import com.vt.o2f.exceptions.UnmappedBeanException;
import com.vt.o2f.util.PersistenceUtil;
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
    private DataTable fileDataStore;
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

    public void bindFileDataToBean() throws UnmappedBeanException, PersistenceException {
        if(isConfigurationCorrect()){
            getFileNameToBeRead();
            loadDataFromFileToDataStore();
            convertDataRowsToObjects();
        }else{
            log.error("Could not complete bind due to an configuration error.");
            throw new PersistenceException("Error binding data to bean, the qualified name provided for bean is in correct. className=" + fullyQualifiedClassName);
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
                    PersistenceUtil.capitalizeFirstLetter(field.getName());
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

    public DataTable getFileDataStore() {
        return fileDataStore;
    }

    public void setFileDataStore(DataTable fileDataStore) {
        this.fileDataStore = fileDataStore;
    }

    public List<Object> getRecordList() {
        return recordList;
    }

    public FileDataReader getFileDataReader() {
        return fileDataReader;
    }

    public List<Object> readData(String fullyQualifiedClassName) throws UnmappedBeanException, PersistenceException {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        bindFileDataToBean();
        return getRecordList();
    }
}
