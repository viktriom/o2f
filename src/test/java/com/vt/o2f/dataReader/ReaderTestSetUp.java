package com.vt.o2f.dataReader;

import com.vt.PersistenceTestSetUp;
import com.vt.properties.ApplicationProperties;
import org.junit.Before;

/**
 * Created by sonu on 27/06/16.
 */
public class ReaderTestSetUp extends PersistenceTestSetUp {

    protected FileDataReader dataReader;
    protected DataTable dataStore;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dataReader = new FileDataReader(ApplicationProperties.getDataFileDirectory()+
                ApplicationProperties.getTraderDataFileName(),
                ApplicationProperties.getTraderDataFileDelimiter());
        dataStore = dataReader.readFile();
    }
}
