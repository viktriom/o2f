package com.vt.o2f.binder;

import com.vt.PersistenceTestSetUp;
import com.vt.bean.TestBeanOne;
import com.vt.util.PropertyReader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by sonu on 27/06/16.
 */
public class DataBinderTest  extends PersistenceTestSetUp {

    private FileDataBinder dataBinder;
    private List<Object> orders;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dataBinder = new FileDataBinder("com.vt.bean.TestBeanOne");
        propertyReader = new PropertyReader("trader.properties","com.vt.properties.ApplicationProperties");
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
            TestBeanOne testBeanOne1 = (TestBeanOne) obj;
            boolean found = false;
            for(TestBeanOne testBeanOne2 : testBeanOneList){
                if(testBeanOne1.equals(testBeanOne2)){
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