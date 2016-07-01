package com.vt.persistence.dataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sonu on 28/06/16.
 */
public class DataFileWriter {

    private String completeFileName;
    private BufferedWriter bufferedWriter;


    public DataFileWriter(String completeFilename) throws IOException {
        this.completeFileName = completeFilename;
        initializeFileWritingSystem();
    }

    private void initializeFileWritingSystem() throws IOException {
        File file = new File(completeFileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fw);
    }

    public void writeStringToFile(String data) throws IOException {
        bufferedWriter.write(data);
    }

    public void writeEmptyLine() throws IOException {
        bufferedWriter.write("\n");
    }

    public void completeWriting() throws IOException {
        bufferedWriter.close();
    }


}
