package com.vt.persistence.dataWriter;

import com.xyz.trader.TraderTestSetUp;
import org.junit.Before;

/**
 * Created by sonu on 28/06/16.
 */
public class PersistenceTestSetup extends TraderTestSetUp {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        prepareTestBeans();
    }



}
