package com.vt;

import com.xyz.trader.bean.Order;
import com.xyz.trader.bean.OrderStatus;
import com.xyz.trader.data.PersistenceHandler;
import com.xyz.trader.data.PersistenceHandlerFactory;
import com.xyz.trader.persistence.PersistenceProperties;
import com.xyz.trader.properties.ApplicationProperties;
import com.xyz.trader.util.PropertyReader;
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
public class TraderTestSetUp {
    protected PropertyReader propertyReader;
    protected List<Order> orderList;
    protected BufferedReader reader;
    protected List<Object> orderStatusList;
    protected PersistenceHandler persistenceHandler;

    @Before
    public void setUp() throws Exception {
        propertyReader = new PropertyReader(
                "trader.properties",
                "com.xyz.trader.properties.ApplicationProperties");
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
        orderList = new LinkedList<Order>();
        Order order;
        order = new Order(1,"Buy","ABC",10);
        orderList.add(0,order);
        order = new Order(2,"Sell","XYZ",15);
        orderList.add(1,order);
        order = new Order(3,"Sell","ABC",13);
        orderList.add(2,order);
        order = new Order(4,"Buy","XYZ",10);
        orderList.add(3,order);
        order = new Order(5,"Buy","XYZ",8);
        orderList.add(4,order);

        orderStatusList = new LinkedList<Object>();
        OrderStatus orderStatus;
        orderStatus = new OrderStatus(1,"Buy","ABC",10,0,"Closed");
        orderStatusList.add(0,orderStatus);
        orderStatus = new OrderStatus(2,"Sell","XYZ",15,0,"Closed");
        orderStatusList.add(1,orderStatus);
        orderStatus = new OrderStatus(3,"Sell","ABC",13,3,"Open");
        orderStatusList.add(2,orderStatus);
        orderStatus = new OrderStatus(4,"Buy","XYZ",10,0,"Closed");
        orderStatusList.add(3,orderStatus);
        orderStatus = new OrderStatus(5,"Buy","XYZ",8,3,"Open");
        orderStatusList.add(4,orderStatus);
    }

    protected void createFileReader(String completeFileName) throws FileNotFoundException {
        File file = new File(completeFileName);
        reader = new BufferedReader(new FileReader(file));
    }
}
