package com.vt.persistence.dataReader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sonu on 27/06/16.
 */
public class FileDataReaderTest extends ReaderTestSetUp {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testReadFile() throws Exception {
        assertEquals(dataStore.getHeaderNames().size(), 4);
    }

    @Test
    public void testGetNumberOfColumnsInFile() throws Exception {
        assertEquals(dataReader.getNumberOfColumnsInFile(),4);
    }
}