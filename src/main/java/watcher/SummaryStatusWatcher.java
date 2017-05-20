/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import db.model.Logs;
import db.model.Volume;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 *
 * @author sharath nair
 */

public class SummaryStatusWatcher {
    private LogsService lserv=new LogsServiceImpl();
    private VolumeService vserv=new VolumeServiceImpl();
    private VolumeSelectionModel volumeSelectionModel;
    private Volume volume;
    private List<Logs> logsWithDistinctSeq;
    private List<Long> seqs=new ArrayList<>();
    private Map<Long,List<Logs>> seqSubLogsDistinct=new HashMap<>();
    private Map<Long,String> seqstat=new HashMap<>();
    private ObservableMap<Long,String> seqrunStatus=FXCollections.observableHashMap();
    
    
    TimerTask task;
    Timer timer;

    public SummaryStatusWatcher(VolumeSelectionModel volumeSelectionModel) {
        this.volumeSelectionModel = volumeSelectionModel;
        this.volume=vserv.getVolume(this.volumeSelectionModel.getId());
           
        ExecutorService executorserv= Executors.newFixedThreadPool(1);
        try{
            executorserv.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    
                    task=new TimerTask(){
                        @Override
                        public void run() {
                            System.out.println("watcher.SummaryStatusWatcher.<init>(). Checking for runstatus of logs for Volume: "+SummaryStatusWatcher.this.volumeSelectionModel.getLabel());
                            logsWithDistinctSeq=lserv.getSequencesFor(volume);
                            if(logsWithDistinctSeq==null){
                                System.out.println("watcher.SummaryStatusWatcher.<init>().run(): NULL value encountered while retrieving sequence list from the Log table");
                            }
                            for (Iterator<Logs> iterator = logsWithDistinctSeq.iterator(); iterator.hasNext();) {
                                Logs log = iterator.next();
                                seqs.add(log.getSequence());
                            }
                            for (Iterator<Long> iterator = seqs.iterator(); iterator.hasNext();) {
                                Long seq = iterator.next();
                                List<Logs> subs=lserv.getSubsurfacesFor(volume, seq);
                                seqSubLogsDistinct.put(seq, subs);
                            }
                            for (Map.Entry<Long, List<Logs>> entry : seqSubLogsDistinct.entrySet()) {
                                Long seq = entry.getKey();
                                String runstatus=new String();
                                Boolean error=false;
                                Boolean success=true;
                                Boolean cancelled=false;
                                Boolean running=false;
                                
                                List<Logs> subLogs = entry.getValue();
                                for (Iterator<Logs> iterator = subLogs.iterator(); iterator.hasNext();) {
                                    Logs sublog = iterator.next();
                                    String subline=sublog.getSubsurfaces();
                                    Logs latestLog=lserv.getLatestLogFor(volume, subline);
                                    error=error || latestLog.getErrored();                      //if any have errored. set error =true
                                    success=success && latestLog.getCompletedsuccessfully();    //if all have succeeded . set success=true
                                    cancelled=cancelled || latestLog.getCancelled();            //if any
                                    running=running || latestLog.getRunning();                  //if any
                                    
                                }
                                if(error){
                                    runstatus="Errored";
                                }
                                if(!error && cancelled){
                                    runstatus="Cancelled";
                                }
                                if(!error && !cancelled && running){
                                    runstatus="Running";
                                }
                                if(!error && !cancelled && !running && success){
                                    runstatus="Success";
                                }
                                
                                StringProperty runprop=new SimpleStringProperty(runstatus);
                                if(seqrunStatus==null){
                                    System.out.println("watcher.SummaryStatusWatcher.<init>().run(): seqrunStatus"+seqrunStatus==null?" is NULL":"");
                                }
                                if(seq==null){
                                    System.out.println(".watcher.SummaryStatusWatcher.<init>().run(): value of seq: is NULL");
                                }
                                if(runstatus==null){
                                    System.out.println("watcher.SummaryStatusWatcher.<init>().run(): value of runstatus: is NULL");
                                }
                                
                                seqrunStatus.put(seq, runstatus);
                                
                            }
                            
                            volumeSelectionModel.setLogstatusMapForSeq(seqrunStatus);
                            
                        }
                        
                    };
                    timer=new Timer();
                    timer.schedule(task, new Date(),100000);                        //check for log updates every ten minutes
                    
                    
                    return null;
                }
                    
                }).get();
        
        }   catch (InterruptedException ex) {
            Logger.getLogger(SummaryStatusWatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(SummaryStatusWatcher.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
    
}
