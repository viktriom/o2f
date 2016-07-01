package com.vt.persistence.dataWriter;

import com.xyz.trader.TraderTestSetUp;
import com.xyz.trader.properties.ApplicationProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sonu on 28/06/16.
 */
public class DataFileWriterTest extends TraderTestSetUp {
    DataFileWriter writer;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        writer = new DataFileWriter(ApplicationProperties.getDataFileDirectory()+"testData.csv");
        createFileReader(ApplicationProperties.getDataFileDirectory() + "testData.csv");
    }
    @Test
    public void testWriteStringToFile() throws Exception {
        String line = "This is a test line.";
        writer.writeStringToFile(line);
        writer.completeWriting();
        String readLine = reader.readLine();
        assertEquals(line, readLine);
    }
}