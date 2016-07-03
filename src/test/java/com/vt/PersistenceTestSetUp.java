package com.vt;

import com.vt.bean.TestBeanOne;
import com.vt.bean.TestBeanTwo;
import com.vt.data.PersistenceHandler;
import com.vt.data.PersistenceHandlerFactory;
import com.vt.o2f.PersistenceProperties;
import com.vt.properties.ApplicationProperties;
import com.vt.util.PropertyReader;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sonu on 27/06/16.
 */
public class PersistenceTestSetUp {
    protected PropertyReader propertyReader;
    protected List<TestBeanOne> testBeanOneList;
    protected BufferedReader reader;
    protected List<Object> orderStatusList;
    protected PersistenceHandler persistenceHandler;

    @Before
    public void setUp() throws Exception {
        propertyReader = new PropertyReader(
                "trader.properties",
                "com.vt.properties.ApplicationProperties");
        propertyReader.initializeClassFieldsFromPropertiesFile(true);
        initializePersistenceSystem();
    }


    private void initializePersistenceSystem() {
        PersistenceProperties.setDataFileDelimiter(ApplicationProperties.getTraderDataFileDelimiter());
        PersistenceProperties.setDataFileDirectory(ApplicationProperties.getDataFileDirectory());
        PersistenceProperties.setGetterMethodPrefix(ApplicationProperties.getGetterMethodPrefix());
        PersistenceProperties.setSetterMethodPrefix(ApplicationProperties.getSetterMethodPrefix());
        PersistenceProperties.setIsFirstRowHeader(ApplicationProperties.getIsFirstRowHeader());
        PersistenceHandlerFactory factory = new PersistenceHandlerFactory();
        persistenceHandler = factory.getPersistenceHandler("File");
    }

    protected void prepareTestBeans(){
        testBeanOneList = new LinkedList<>();
        TestBeanOne testBeanOne;
        testBeanOne = new TestBeanOne(1,"Buy","ABC",10);
        testBeanOneList.add(0, testBeanOne);
        testBeanOne = new TestBeanOne(2,"Sell","XYZ",15);
        testBeanOneList.add(1, testBeanOne);
        testBeanOne = new TestBeanOne(3,"Sell","ABC",13);
        testBeanOneList.add(2, testBeanOne);
        testBeanOne = new TestBeanOne(4,"Buy","XYZ",10);
        testBeanOneList.add(3, testBeanOne);
        testBeanOne = new TestBeanOne(5,"Buy","XYZ",8);
        testBeanOneList.add(4, testBeanOne);

        orderStatusList = new LinkedList<>();
        TestBeanTwo testBeanTwo;
        testBeanTwo = new TestBeanTwo(1,"Buy","ABC",10,0,"Closed","Sell");
        orderStatusList.add(0, testBeanTwo);
        testBeanTwo = new TestBeanTwo(2,"Sell","XYZ",15,0,"Closed","Buy");
        orderStatusList.add(1, testBeanTwo);
        testBeanTwo = new TestBeanTwo(3,"Sell","ABC",13,3,"Open","Buy");
        orderStatusList.add(2, testBeanTwo);
        testBeanTwo = new TestBeanTwo(4,"Buy","XYZ",10,0,"Closed","Sell");
        orderStatusList.add(3, testBeanTwo);
        testBeanTwo = new TestBeanTwo(5,"Buy","XYZ",8,3,"Open","Sell");
        orderStatusList.add(4, testBeanTwo);
    }

    protected void createFileReader(String completeFileName) throws FileNotFoundException {
        File file = new File(completeFileName);
        reader = new BufferedReader(new FileReader(file));
    }
}
