package com.vt.o2f.dataWriter;

import com.vt.PersistenceTestSetUp;
import org.junit.Before;

/**
 * Created by sonu on 28/06/16.
 */
public class PersistenceTestSetup extends PersistenceTestSetUp {
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        prepareTestBeans();
    }



}
