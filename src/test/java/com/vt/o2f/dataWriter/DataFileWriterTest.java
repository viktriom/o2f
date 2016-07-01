package com.vt.o2f.dataWriter;

import com.vt.PersistenceTestSetUp;
import com.vt.properties.ApplicationProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sonu on 28/06/16.
 */
public class DataFileWriterTest extends PersistenceTestSetUp {
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