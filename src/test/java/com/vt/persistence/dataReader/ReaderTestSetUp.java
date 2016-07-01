package com.vt.persistence.dataReader;

import com.xyz.trader.TraderTestSetUp;
import com.xyz.trader.properties.ApplicationProperties;
import org.junit.Before;

/**
 * Created by sonu on 27/06/16.
 */
public class ReaderTestSetUp extends TraderTestSetUp {

    protected FileDataReader dataReader;
    protected FileDataStore dataStore;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dataReader = new FileDataReader(ApplicationProperties.getDataFileDirectory()+
                ApplicationProperties.getTraderDataFileName(),
                ApplicationProperties.getTraderDataFileDelimiter());
        dataStore = dataReader.readFile();
    }
}
