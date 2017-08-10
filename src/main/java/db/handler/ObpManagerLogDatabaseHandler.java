/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.handler;

import db.model.ObpManagerLog;
import db.model.Sessions;
import db.services.ObpManagerLogService;
import db.services.ObpManagerLogServiceImpl;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ObpManagerLogDatabaseHandler extends Handler{
    
    ObpManagerLogService oserv=new ObpManagerLogServiceImpl();
  //  Sessions sessions;

    /*public ObpManagerLogDatabaseHandler(Sessions sessions) {
    this.sessions = sessions;
    }*/

    
    
    /* public Sessions getSessions() {
    return sessions;
    }
    
    public void setSessions(Sessions sessions) {
    this.sessions = sessions;
    }
    */
    

    @Override
    public void publish(LogRecord record) {
        if(getFilter()!=null){
            if(!getFilter().isLoggable(record)){
                return;
            }
        }
        
        ObpManagerLog obpl=new ObpManagerLog();
        obpl.setLevel(Long.valueOf(record.getLevel().intValue()));
        obpl.setLogger(record.getLoggerName());
        obpl.setMessage(record.getMessage());
       // obpl.setSessions(sessions);
        obpl.setSourceClass(record.getSourceClassName());
        obpl.setSourceMethod(record.getSourceMethodName());
        obpl.setThreadId(Long.valueOf(record.getThreadID()));
        obpl.setTimeEntered((new Date(System.currentTimeMillis())).toString());
        
        oserv.createObpManagerLog(obpl);
    }
    

    @Override
    public void flush() {
        
    }

    @Override
    public void close() throws SecurityException {
       
    }
    
    public void clear(){
        List<ObpManagerLog> existingLogs=oserv.getObpManagerLogs();
        for (Iterator<ObpManagerLog> iterator = existingLogs.iterator(); iterator.hasNext();) {
            ObpManagerLog next = iterator.next();
            oserv.deleteObpManagerLog(next.getIdObpManagerLog());
            
        }
    }
    
}
