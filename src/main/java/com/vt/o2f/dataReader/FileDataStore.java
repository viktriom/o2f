package com.vt.o2f.dataReader;

import java.util.*;

/**
 * Created by sonu on 25/06/16.
 */
public class FileDataStore {

    private Map<String,List<String>> dataStore;


    public FileDataStore() {
        dataStore = new HashMap<String, List<String>>();
    }

    public void setValueForHeader(String header, String value, int index){
        if(isHeaderPresent(header)){
            addValueToExistingHeader(header, value, index);
        }else{
            addNewHeaderAndValue(header, value, index);
        }
    }

    public List<String> getValuesForHeader(String header){
        return dataStore.get(header);
    }

    public String getValueForHeaderAtIndex(String header, int index){
        return validateHeaderAndFindValue(header,index);
    }

    private String validateHeaderAndFindValue(String header, int index){
        List<String> values = dataStore.get(header);
        if(values == null||values.size() == 0)
            return null;
        else
            return values.get(index);
    }

    private void addNewHeaderAndValue(String header, String value, int index) {
        List<String> values = new LinkedList<String>();
        values.add(index,value);
        dataStore.put(header,values);
    }

    private void addValueToExistingHeader(String header, String value, int index) {
        dataStore.get(header).add(index, value);
    }


    public boolean isHeaderPresent(String headerName){
        return dataStore.get(headerName) != null;
    }

    public void clearDataInDataStore(){
        dataStore.clear();
    }

    public Set<String> getHeaderNames(){
        return dataStore.keySet();
    }
}
