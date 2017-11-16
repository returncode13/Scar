/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import dugex.DugioScripts;

import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import landing.AppProperties;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair
 */

class LogWatchHolder{
    String date;
    String filename;
    String linename;
    String insightVersion;
    Long seqno;

    public LogWatchHolder(String date, String filename,Long seqn, String linename,String insightVersion) {
        this.date = date;
        this.filename = filename;
        this.seqno=seqn;
        this.linename = linename;
        this.insightVersion=insightVersion;
    }

    public String getInsightVersion() {
        return insightVersion;
    }

    public void setInsightVersion(String insightVersion) {
        this.insightVersion = insightVersion;
    }
    
    
    
}

public  class LogWatcher {
    private String logsLocation;          // path to the dugio volume logs folder
    private Long numberOfruns;
    TimerTask task;
    Timer timer;
    private static int counter=0;
    private String filter=new String();
    
    VolumeSelectionModelType1 volumeModel;
    HeadersService hserv=new HeadersServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    LogsService lserv=new LogsServiceImpl();
    List<LogWatchHolder> lwatchHolderList=new ArrayList<>();
    MultiMap<String, LogWatchHolder> maplineLog=new MultiValueMap<>();
    Map<String,String> mapSubInsightVersion=new HashMap<>();                //map that will contain the Insight version number extracted from the latest log for the subsurface
     private Volume volume;
     private List<LogWatchHolder> listOfExistingLogs=new ArrayList<>();
     private Map<String,LogWatchHolder> mapOfExistingLogs=new HashMap<>();
     LogStatusWatcher logstatuswatcher=null;
     WorkflowWatcher workflowWatcher=null;
    public LogWatcher(){
    }
    
    public LogWatcher(String logsLocation,String filter,VolumeSelectionModelType1 vmod) 
    {
        System.out.println("watcher.LogWatcher.<init>(): called for : "+vmod.getLabel());
        counter++;
        
        /*See if any logs are already present in the database.
        account for those logs , and put those sublines into a list. A.
        
        */
        LogWatcher.this.volumeModel=vmod;
        volume=vserv.getVolume(volumeModel.getId());
        LogWatcher.this.logsLocation=logsLocation;
        /*List<Logs> logL=lserv.getLogsFor(this.volume);
        if(!logL.isEmpty()){
        for (Iterator<Logs> iterator = logL.iterator(); iterator.hasNext();) {
        Logs next = iterator.next();
        LogWatchHolder lw=new LogWatchHolder(next.getTimestamp(), next.getLogpath(), next.getSequence(),next.getSubsurfaces(), next.getInsightVersion());
        //listOfExistingLogs.add(lw);
        mapOfExistingLogs.put(next.getLogpath(), lw);
        System.out.println("watcher.LogWatcher.<init>(): found existing log for  : "+next.getSubsurfaces()+" log under: "+next.getLogpath()+" adding to map");
        }
        }
        else{
        System.out.println("watcher.LogWatcher.<init>(): LogList was empty! Is this the first time this volume has been run? Volume in question: "+this.volume.getPathOfVolume());
        }
        */
            
                extract();
                commitToDb();
        
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        try {
            executorService.submit(new Callable<Void>() {
                
                @Override
                public Void call() throws Exception {
                   
                    
                    System.out.println("watcher.LogWatcher: started to watch");
                    
                    LogWatcher.this.filter=filter;
                    
                    
                //    if(false&&!tableExtraction){
                        task=new Watcher(LogWatcher.this.logsLocation, filter) {
                            
                            @Override
                            protected void onChange(File logfile, String action) {
                                
                                
                                
                                if(action.equals("Added")){
                                    
                                    System.out.println("watcher.LogWatcher. "+logfile+" was added");   
                                    //check if the file is big enough to start reading.
                                    boolean hasTooFewLines=true;
                                    while(hasTooFewLines){
                                    try(BufferedReader br=new BufferedReader(new FileReader(logfile))){
                                        String line=br.readLine();
                                        int count=0;
                                        while(line!=null){
                                            line=br.readLine();
                                            count++;
                                            if(count > 60) {
                                                hasTooFewLines=false;
                                                System.out.println("watcher.LogWatcher.onChange(): more than 60 lines present. breaking from loop. moving to call extract() and commit()");
                                                break;
                                            }
                                        }
                                    } catch (IOException ex) {
                                        //Exceptions.printStackTrace(ex);
                                        ex.printStackTrace();
                                    }
                                    
                                    }
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    try{
                                        extract();
                                        commitToDb();
                                        
                                    }catch(ArrayIndexOutOfBoundsException aobe){
                                            System.out.println("I think the log is still building..Will try again in 60 secs");
                                        try {
                                            Thread.sleep(100000);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        //if(counter<5){
                                            extract();
                                            commitToDb();
                                        //}
                                    }
                                    
                                    
                                }
                            }
                        };
                        
                        
                        
                        
                        timer=new Timer();
                        timer.schedule(task,new Date(),2000);                          //every 2 seconds
                   //}
                    
                   
                    return null;
                }
            }).get();
        } 
          catch (InterruptedException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
    }

    private void extract(){
        mapOfExistingLogs.clear();
                      List<Logs> logL=lserv.getLogsFor(this.volume);
        if(!logL.isEmpty()){
            for (Iterator<Logs> iterator = logL.iterator(); iterator.hasNext();) {
            Logs next = iterator.next();
            LogWatchHolder lw=new LogWatchHolder(next.getTimestamp(), next.getLogpath(), next.getSequence(),next.getSubsurfaces(), next.getInsightVersion());
            //listOfExistingLogs.add(lw);
            mapOfExistingLogs.put(next.getLogpath(), lw);
                System.out.println("watcher.LogWatcher.extract(): found existing log for  : "+next.getSubsurfaces()+" log under: "+next.getLogpath()+" adding to map");
            }
        }
        else{
            System.out.println("watcher.LogWatcher.extract(): LogList was empty! Is this the first time this volume has been run? Volume in question: "+this.volume.getPathOfVolume());
            }
                    
                    
                    
                    try {
                        String logpath=logsLocation;                                     //../000scratch/logs
                        System.out.println("watcher.LogWatcher.extract(): LogLocation: "+logpath);
                        System.out.println("watcher.Logwatcher.extract(): Processing logs");
                        
                        
                        
                        
                        Process process=new ProcessBuilder(new DugioScripts().getSubsurfaceLog().getAbsolutePath(),logpath).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        lwatchHolderList.clear();
                        String value;
                        while((value=br.readLine())!=null){
                            Long maxVersion=0L;
                            System.out.println("watcher.LogWatcher.extract(): whileLoop: "+value);
                         
                             
                             
                             boolean skipped=true;
                             
                             
                                            String[] parts=value.split("\\s");
                                            System.out.println("watcher.LogWatcher.extract(): paths.length: "+parts.length);
                                            /* if(parts.length!=16){
                                            continue;
                                            }*/
                                            /* for(int iii=0;iii<parts.length;iii++){
                                            System.out.println("wacther.Logwatcher():  "+iii+" = "+parts[iii]);
                                            }*/
                                            
                                            if(parts.length==15){
                                                
                                            skipped=false;
                                            String filename=parts[0];
                                            String date=parts[1];                                            
                                            String time=parts[2];
                                            DateFormat jdate=new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                                            Date convjdate=jdate.parse(date+":"+time);
                                            
                                            String linename=parts[7].split("=")[1];
                                            String qes=new StringBuilder(linename.split("_")[0]).reverse().toString();
                                          //  System.out.println("watcher.LogWatcher.extract(): qes: "+qes);
                                            String seq=qes.substring(0,3);
                                            //System.out.println("watcher.LogWatcher.extract(): seq: "+seq);
                                            seq=new StringBuilder(seq).reverse().toString();
                                            Long seqno=Long.valueOf(seq);
                                            //System.out.println("watcher.LogWatcher.extract(): seqno: "+seqno);
                                            String baseInsVersion=parts[14];
                                            //String revInsVersion=parts[15];
                                        //    String insVersion=baseInsVersion.concat(revInsVersion);
                                            System.out.println("watcher.LogWatcher.extract(): got: "+convjdate+" "+linename+" logsLocation: "+filename+" seq: "+seqno+" sub:"+linename+" insVersion: "+baseInsVersion);
                                            
                            
                            LogWatchHolder lwatchholder=new LogWatchHolder(convjdate.toString(), filename,seqno, linename,baseInsVersion);
                            System.out.print(".");
                           // if(!listOfExistingSubs.contains(linename)){
                           Set<String> existingLogs=mapOfExistingLogs.keySet();
                           if(existingLogs.contains(filename)){
                               System.out.println("watcher.LogWatcher.extract(): MapOfExistingLogs: contains "+filename);
                           }
                           if(!existingLogs.contains(filename)){
                               System.out.println("watcher.LogWatcher.extract():"+filename+" Adding to lwatcholder");
                               lwatchHolderList.add(lwatchholder);
                           }else{
                               //System.out.println("watcher.LogWatcher.extract(): log "+filename+" already present in database...skipping");
                               
                           }
                                
                           // }
                            }
                                            
                                            
                                            
                                            
                                            
                                            
                                            if(parts.length==16){
                                                
                                            skipped=false;
                                            String filename=parts[0];
                                            String date=parts[1];                                            
                                            String time=parts[2];
                                            DateFormat jdate=new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                                            Date convjdate=jdate.parse(date+":"+time);
                                            
                                            String linename=parts[7].split("=")[1];
                                            String qes=new StringBuilder(linename.split("_")[0]).reverse().toString();
                                          //  System.out.println("watcher.LogWatcher.extract(): qes: "+qes);
                                            String seq=qes.substring(0,3);
                                            //System.out.println("watcher.LogWatcher.extract(): seq: "+seq);
                                            seq=new StringBuilder(seq).reverse().toString();
                                            Long seqno=Long.valueOf(seq);
                                            //System.out.println("watcher.LogWatcher.extract(): seqno: "+seqno);
                                            String baseInsVersion=parts[14];
                                            String revInsVersion=parts[15];
                                            String insVersion=baseInsVersion.concat(revInsVersion);
                                            System.out.println("got: "+convjdate+" "+linename+" logsLocation: "+filename+" seq: "+seqno+" sub:"+linename+" insVersion: "+baseInsVersion+" insbuild: "+revInsVersion+" insight: "+insVersion);
                                            
                            
                            LogWatchHolder lwatchholder=new LogWatchHolder(convjdate.toString(), filename,seqno, linename,insVersion);
                            System.out.print(".");
                           // if(!listOfExistingSubs.contains(linename)){
                           Set<String> existingLogs=mapOfExistingLogs.keySet();
                           if(existingLogs.contains(filename)){
                               System.out.println("watcher.LogWatcher.extract(): MapOfExistingLogs: contains "+filename);
                           }
                           if(!existingLogs.contains(filename)){
                               System.out.println("watcher.LogWatcher.extract():"+filename+" Adding to lwatcholder");
                               lwatchHolderList.add(lwatchholder);
                           }else{
                               //System.out.println("watcher.LogWatcher.extract(): log "+filename+" already present in database...skipping");
                               
                           }
                                
                           // }
                            }
                            
                            System.out.println("watcher.LogWatcher.extract(): Skipped status for log: "+skipped);
                            
                        }
                        
                        maplineLog.clear();
                        
                        for (Iterator<LogWatchHolder> iterator = lwatchHolderList.iterator(); iterator.hasNext();) {
                            LogWatchHolder next = iterator.next();
                              System.out.println("watcher.LogWatcher.: Inside for loop: "+"got: "+next.linename+" logsLocation: "+next.filename+" "+next.date);
                            maplineLog.put(next.linename, next);
                        }
                        
                        Set<String> keys=maplineLog.keySet();
                        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                            String next = iterator.next();
                              System.out.println("watcher.LogWatcher.: Keys: "+next);
                        }
                       
                      
                        
                        
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                    
    }
    
    private void commitToDb(){
        
        try {
           
            //List<Logs>logsList=lserv.getLogsFor(volume);
            Set<String> keys=maplineLog.keySet();
            if(keys.size()>0){
                 System.out.println("watcher.LogWatcher.commitToDb(): Committing "+keys.size()+" logs ");
            }else{
                System.out.println("watcher.LogWatcher.commitToDb(): Nothing to commit..yet");
            }
            
            ExecutorService executorService= Executors.newFixedThreadPool(1);
            
            executorService.submit(new Callable<Void>(){
                @Override
                public Void call() throws Exception {
                    for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                        String next = iterator.next();
                        List<LogWatchHolder> logwList=(List<LogWatchHolder>) maplineLog.get(next);
                        
                        for (Iterator<LogWatchHolder> iterator1 = logwList.iterator(); iterator1.hasNext();) {
                            LogWatchHolder lgw = iterator1.next();
                            
                            //if(lgw.filename.equals())
                            System.out.println("watcher.LogWatcher.commitToDb(): I found "+lgw.linename+" with Log: "+lgw.filename+" made on: "+lgw.date+"" );//" with version: "+preVersion);
                            Logs l=new Logs();
                            
                            l.setLogpath(lgw.filename);
                            l.setTimestamp(lgw.date);
                            l.setVolume(LogWatcher.this.volume);
                            l.setInsightVersion(lgw.insightVersion);
                            l.setSubsurfaces(lgw.linename);
                           l.setSequence(lgw.seqno);
                           l.setUpdateTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));      //stored as a string  yyyyMMddHHmmss
                            lserv.createLogs(l);
                            
                            //   preVersion++;
                        }
                        // }
                        
                        
                    }
                    return null;
                }
            }).get();
            
           
            
            
            if(logstatuswatcher==null){
            logstatuswatcher=new LogStatusWatcher(volumeModel);
            }
            if(workflowWatcher==null){
                workflowWatcher=new WorkflowWatcher(volume);
               
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    public Map<String, String> getsubInsightVersionMap() throws ParseException {
                        Set<String> keys=maplineLog.keySet();
                        mapSubInsightVersion.clear();
                        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                            String linename = iterator.next();
                            List<LogWatchHolder> lgwl=(List<LogWatchHolder>)maplineLog.get(linename);
                            String insver=new String();
                            String oldDate="Mon Jan 01 01:01:01 UTC 2001";
                            Date date=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(oldDate);                   // date set to 201001010000
                            for (Iterator<LogWatchHolder> iterator1 = lgwl.iterator(); iterator1.hasNext();) {   //loop to find the insight version from the latest log
                                LogWatchHolder lgw = iterator1.next();
                               // System.out.println("watcher.LogWatcher.getsubInsightVersionMap(): "+lgw.date);
                                Date d=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(lgw.date);
                                if(d.after(date)){
                                    date=d;
                                    insver=lgw.insightVersion;
                                 //   System.out.println("watcher.LogWatcher.getsubInsightVersionMap(): "+date+"  inv: "+insver);
                                }
                            }
                            
                            mapSubInsightVersion.put(linename, insver);
                        }
     return mapSubInsightVersion;
    }

    public Long getNumberOfruns(String linename) {
        List<LogWatchHolder> logwatchList= (List<LogWatchHolder>) maplineLog.get(linename);
        try{
            numberOfruns=Long.valueOf(logwatchList.size());
        }catch(NullPointerException npe){
            numberOfruns=0L;
        }
        return numberOfruns;
    }
    
    
   

    
}
