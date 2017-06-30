package com.vt.o2f.dataReader;

import java.util.*;

/**
 * Created by sonu on 25/06/16.
 */
public class DataTable {

    private Map<String,List<String>> table;


    public DataTable() {
        table = new HashMap<>();
    }

    public void setValueForHeader(String header, String value, int index){
        if(isHeaderPresent(header)){
            addValueToExistingHeader(header, value, index);
        }else{
            addNewHeaderAndValue(header, value, index);
        }
    }

    public List<String> getValuesForHeader(String header){
        return table.get(header);
    }

    public String getValueForHeaderAtIndex(String header, int index){
        return validateHeaderAndFindValue(header,index);
    }

    private String validateHeaderAndFindValue(String header, int index){
        List<String> values = table.get(header);
        if(values == null||values.size() == 0)
            return null;
        else
            return values.get(index);
    }

    private void addNewHeaderAndValue(String header, String value, int index) {
        List<String> values = new LinkedList<String>();
        values.add(index,value);
        table.put(header,values);
    }

    private void addValueToExistingHeader(String header, String value, int index) {
        table.get(header).add(index, value);
    }

    public void saveTableDataToFile(){
    	
    }

    public boolean isHeaderPresent(String headerName){
        return table.get(headerName) != null;
    }

    public void clearDataInDataStore(){
        table.clear();
    }

    public Set<String> getHeaderNames(){
        return table.keySet();
    }
}
