/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author naila0152
 */
public interface LogsDAO {
    public void createLogs(Logs l);
    public Logs getLogs(Long lid);
    public void updateLogs(Long lid, Logs newL);
    public void deleteLogs(Long lid);
    
    public List<Logs> getLogsFor(Headers h);  //get the logs for which the foreign key=h.id

    
}
