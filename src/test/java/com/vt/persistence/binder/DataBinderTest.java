package com.vt.persistence.binder;

import com.xyz.trader.TraderTestSetUp;
import com.xyz.trader.bean.Order;
import com.xyz.trader.util.PropertyReader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sonu on 27/06/16.
 */
public class DataBinderTest  extends TraderTestSetUp {

    private FileDataBinder dataBinder;
    private List<Object> orders;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dataBinder = new FileDataBinder("com.xyz.trader.bean.Order");
        propertyReader = new PropertyReader("trader.properties","com.xyz.trader.properties.ApplicationProperties");
        propertyReader.initializeClassFieldsFromPropertiesFile(true);
        dataBinder.bindFileDataToBean();
        orders = dataBinder.getRecordList();
        orders = dataBinder.getRecordList();
        prepareTestBeans();
    }

    @Test
    public void testHeadersRetrievedCorrectly() throws Exception {

    }

    @Test
    public void testNumberOfRows(){
        assertEquals(orders.size(),5);
    }

    @Test
    public void testNumberOfRowsRetrieved(){
        int rowCount = dataBinder.getFileDataReader().getNumberOfRowsInFile();
        if(rowCount != 5){
            fail("The number of data rows in file and the number of data rows read is not same.");
        }
    }

    @Test
    public void testDataBean(){
        for(Object obj: orders){
            Order order1 = (Order) obj;
            boolean found = false;
            for(Order order2 : orderList){
                if(order1.equals(order2)){
                    found = true;
                }
            }
            if(!found){
                fail("Data in file and data in mapped list not same.");
            }
        }
    }

    @Test
    public void validateHeaderCount(){
        int headerCount = dataBinder.getFileDataReader().getNumberOfColumnsInFile();
        if(headerCount != dataBinder.getFileDataStore().getHeaderNames().size()){
            fail("Number of headers available in file is not same as number of headers read.");
        }
        if(headerCount != 4){
            fail("Number of headers available in file is not same as number of headers read.");
        }
    }
}