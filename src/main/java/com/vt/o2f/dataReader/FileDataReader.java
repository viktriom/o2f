package com.vt.o2f.dataReader;

import com.vt.o2f.PersistenceProperties;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by sonu on 25/06/16.
 */
public class FileDataReader {

    private Logger log = Logger.getLogger(FileDataReader.class);

    private int numberOfColumnsInFile;
    private int numberOfRowsInFile;
    private String completeFileName;
    private BufferedReader reader;
    private FileDataStore dataStore;
    private String delimiter;
    private String[] columnNames;


    public FileDataReader(String completeFileName, String delimiter) {
        this(delimiter);
        this.completeFileName = completeFileName;
    }

    public FileDataReader(String delimiter){
        this.delimiter = delimiter;
        initialize();
    }

    private void initialize(){
        reader = null;
        dataStore = new FileDataStore();
        numberOfColumnsInFile = 0;
        numberOfRowsInFile = 0;
        columnNames = null;
    }


    public FileDataStore readFile(){
        try{
            initialize();
            validateFileName();
            createInputStreamFromFile();
            readAndLoadHeader();
            readAndLoadData();
        }catch(FileNotFoundException exception){
            log.error("Error while reading the data file \'" + completeFileName + "\', The file was not found");
            exception.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return dataStore;
    }

    private void readAndLoadData() throws IOException {
        String currentLine;
        while((currentLine = reader.readLine()) != null){
            processRecord(currentLine);
            numberOfRowsInFile++;
        }

    }

    private void processRecord(String currentLine) {
        String[] fields = currentLine.split(delimiter);
        if(fields.length < columnNames.length)
            return;
        int i;
        for(i =0; i < columnNames.length; i++){
            dataStore.setValueForHeader(columnNames[i],fields[i],numberOfRowsInFile);
        }
    }

    private void readAndLoadHeader() throws IOException {
        if(PersistenceProperties.getIsFirstRowHeader()) {
            setNumberOfColumnsInFile(readHeaderFromFile());
        }else{
            setNumberOfColumnsInFile(assignHeaderToColumns());
        }
    }

    private int assignHeaderToColumns() throws IOException {
        String headerLine = reader.readLine();
        columnNames = headerLine.split(delimiter);
        int i;
        for (i = 0; i < columnNames.length; i++) {
            columnNames[i] = "column" + String.valueOf(i);
        }
        reader.reset();
        return i;
    }

    private int readHeaderFromFile() throws IOException {
        String headerLine = reader.readLine();
        columnNames = headerLine.split(delimiter);
        int i;
        for (i = 0; i < columnNames.length; i++) {
            if (columnNames[i] == null || columnNames[i].isEmpty()) {
                columnNames[i] = "column" + String.valueOf(i);
            }
        }
        return i;
    }

    private void createInputStreamFromFile() throws FileNotFoundException {
        File file = new File(completeFileName);
        reader = new BufferedReader(new FileReader(file));
    }

    private boolean validateFileName(){
        return true;
    }

    public int getNumberOfColumnsInFile() {
        return numberOfColumnsInFile;
    }

    private void setNumberOfColumnsInFile(int numberOfColumnsInFile) {
        this.numberOfColumnsInFile = numberOfColumnsInFile;
    }

    public int getNumberOfRowsInFile() {
        return numberOfRowsInFile;
    }

    private void setNumberOfRowsInFile(int numberOfRowsInFile) {
        this.numberOfRowsInFile = numberOfRowsInFile;
    }

    public String getCompleteFileName() {
        return completeFileName;
    }

    public void setCompleteDataFileName(String completeFileName) {
        this.completeFileName = completeFileName;
    }
}
