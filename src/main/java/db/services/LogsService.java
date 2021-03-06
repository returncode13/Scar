/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.model.Workflow;
import java.util.List;

/**
 *
 * @author naila0152
 */
public interface LogsService {
    public void createLogs(Logs l);
    public Logs getLogs(Long lid);
    public void updateLogs(Long lid, Logs newL);
    public void deleteLogs(Long lid);
    
    public List<Logs> getLogsFor(Headers h);  //get the logs for which the foreign key=h.id
    public List<Logs> getLogsFor(Volume v);
    public List<Logs> getLogsFor(Volume v,Boolean completed,Boolean running,Boolean errored,Boolean cancelled);
    public List<Logs> getLogsFor(Volume v,String subline);
    public List<Logs> getLogsFor(Volume v,String subline,Boolean completed,Boolean running,Boolean errored,Boolean cancelled);
    public Logs getLatestLogFor(Volume v, String subline);
    public List<Logs> getLogsFor(Volume v, Workflow workflow);
    public List<Logs> getLogsFor(Volume v,Long seq);       //get logs for seq
    public List<Logs> getSequencesFor(Volume v);           //get distinct sequences in volume
    public List<Logs> getSubsurfacesFor(Volume v, Long seq); //get distinct subsurfaces for  volume,seq

    public Logs getLogsFor(Volume volume, String linename, String timstamp, String filename) throws Exception;
}
