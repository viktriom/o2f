package com.vt.o2f.dataReader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by sonu on 27/06/16.
 */
public class FileDataStoreTest extends ReaderTestSetUp {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testGetValuesForHeader() throws Exception {
        String[] stockValues = {"1","2","3","4","5"};
        int i=0;
        for(String value:  dataStore.getValuesForHeader("stockId")){
            if(!value.equals(stockValues[i++])){
                fail("Incorrect values found for stockId column in data store.");
            }
        }
    }

    @Test
    public void testGetValueForHeaderAtIndex() throws Exception {
        assertEquals(dataStore.getValueForHeaderAtIndex("stockId", 1), "2");
        assertEquals(dataStore.getValueForHeaderAtIndex("side",2),"Sell");
        assertEquals(dataStore.getValueForHeaderAtIndex("company",2),"ABC");
        assertEquals(dataStore.getValueForHeaderAtIndex("quantity",4),"8");
    }

    @Test
    public void testIsHeaderPresent() throws Exception {
        assertEquals(dataStore.isHeaderPresent("stockId"),true);
        assertEquals(dataStore.isHeaderPresent("testHeader"),false);
    }

    @Test
    public void testGetHeaderNames() throws Exception {
        String[] headers = {"stockId","side","company","quantity"};
        if(headers.length == dataStore.getHeaderNames().size()+1){
            fail("Number of header in the file and number of headers read is not same.");
        }
        for(String header : dataStore.getHeaderNames()){
            boolean found = false;
            for(String fileHeader: headers){
                if((fileHeader.equals(header))){
                    found = true;
                    break;
                }
            }
            if(!found)
                fail("The header \'"+header +"\' not found in file.");

        }
    }
}