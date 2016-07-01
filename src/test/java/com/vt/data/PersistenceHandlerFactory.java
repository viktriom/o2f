package com.vt.data;

import com.vt.o2f.DataPersistence;

/**
 * Created by sonu on 27/06/16.
 */
public class PersistenceHandlerFactory {

    public PersistenceHandler getPersistenceHandler(String dataReaderType){
        PersistenceHandler persistenceHandler = null;
        if(dataReaderType.equals("File")){
            persistenceHandler = new DataPersistence();
        }
        return persistenceHandler;
    }

}
