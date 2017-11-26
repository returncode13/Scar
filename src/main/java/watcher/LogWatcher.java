/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.services.AcquisitionService;
import db.services.AcquisitionServiceImpl;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import dugex.DugioScripts;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;

import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.openide.util.Exceptions;
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
    
    VolumeSelectionModelType0 volumeModel;
    HeadersService hserv=new HeadersServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    LogsService lserv=new LogsServiceImpl();
    List<LogWatchHolder> lwatchHolderList=new ArrayList<>();
    MultiMap<String, LogWatchHolder> maplineLog=new MultiValueMap<>();
    Map<String,String> mapSubInsightVersion=new HashMap<>();                //map that will contain the Insight version number extracted from the latest log for the subsurface
    AcquisitionService acqserv=new AcquisitionServiceImpl();
    List<Long> cables;
    List<Long> guns;
    List<String> logNamesForSegD=new ArrayList<>();
    
    private Volume volume;
     private List<LogWatchHolder> listOfExistingLogs=new ArrayList<>();
     private Map<String,LogWatchHolder> mapOfExistingLogs=new HashMap<>();
     LogStatusWatcher logstatuswatcher=null;
     WorkflowWatcher workflowWatcher=null;
    public LogWatcher(){
    }
    
    public LogWatcher(String logsLocation,String filter,VolumeSelectionModelType0 vmod) 
    {
        System.out.println("watcher.LogWatcher.<init>(): Inside the constructor for LogWatcher : "+logsLocation+" vmodType: "+vmod.getType());
        guns=acqserv.getGuns();
        System.out.println("watcher.LogWatcher.<init>(): Test Separator");
       cables=acqserv.getCables();
       
       for(Long cable:cables){
           for(Long gun:guns){
               String gunCableLog="gun"+gun+"_cable"+cable+".log";                  // the logs are named as gun2_cable6.log
               logNamesForSegD.add(gunCableLog);
           }
       }
        
        if(vmod instanceof VolumeSelectionModelType1){
            
       
        
        
        System.out.println("watcher.LogWatcher.<init>(): called for : "+vmod.getLabel());
        counter++;
        
        /*See if any logs are already present in the database.
        account for those logs , and put those sublines into a list. A.
        
        */
        LogWatcher.this.volumeModel=(VolumeSelectionModelType1)vmod;
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
        
        if(vmod instanceof VolumeSelectionModelType2){   //SEGDLoad
                 /*
            For SeqgVolumes, the logs are not added to. but appended.
            Get the total number of cables and guns for the survey from the database.
            the logs present are of the format gun_x_cable_y.log where x=1,2..lastgun and y=1,2..,lastcable
            
            
            */
           List<File> logs=new ArrayList<>();   // the log files under volume/logs of format gun2_cable6.log
        
        System.out.println("watcher.LogWatcher.<init>(): called for : "+vmod.getLabel());
            System.out.println("watcher.LogWatcher.<init>: looking under : "+logsLocation);
        counter++;
        
                LogWatcher.this.volumeModel=(VolumeSelectionModelType2)vmod;
                volume=vserv.getVolume(volumeModel.getId());
                LogWatcher.this.logsLocation=logsLocation;     //location of segd logs folder.  under segdloadVolume/logs
                for(String log: logNamesForSegD){
                    File logfile=new File(this.logsLocation+log);
                    System.out.println("watcher.LogWatcher.<init>(): Adding log file to list to watch out for : "+log);
                    logs.add(logfile);
                }
                
                for(final File gcfile:logs){
                    
                    System.out.println("watcher.LogWatcher.<init>(): Performing the first check for logfile: "+gcfile.getName());
                    /* this first check is done when the s/w is fired up.
                    */
                    if(checkIfSegDLogIsDone(gcfile)){
                                        //get the list of contents that are not present in the database
                                        List<LogWatchHolder> modifiedList=getModifiedContents(gcfile); 
                                        getInsightVersionsFromLog(gcfile,modifiedList);
                                        maplineLog.clear();
                                        for(LogWatchHolder mod:modifiedList){
                                            maplineLog.put(mod.linename, mod);
                                        }
                                        
                                        commitToDb();
                                    }
                    
                    
                    ExecutorService executorService= Executors.newFixedThreadPool(1);
        try {
            executorService.submit(new Callable<Void>() {
                
                @Override
                public Void call() throws Exception {
                   
                    
                    System.out.println("watcher.LogWatcher: started to watch");
                    
                    LogWatcher.this.filter=filter;
                    
                    
             
                        task=new Watcher(LogWatcher.this.logsLocation, filter) {
                            
                            @Override
                            protected void onChange(File logfile, String action) {
                                
                                
                                
                                if(action.equals("Added")){
                                    
                                    System.out.println(".onChange() : "+logfile+" added. Implementation Pending. Contact dev");
                                    
                                }
                                if(action.equals("Modified")){
                                    System.out.println(".onChange() : "+logfile+" modified");
                                    
                                    if(checkIfSegDLogIsDone(gcfile)){
                                        //get the list of contents that are not present in the database
                                        List<LogWatchHolder> modifiedList=getModifiedContents(gcfile); 
                                        getInsightVersionsFromLog(gcfile,modifiedList);
                                        maplineLog.clear();
                                        for(LogWatchHolder mod:modifiedList){
                                            maplineLog.put(mod.linename, mod);
                                        }
                                        
                                        commitToDb();
                                    }else{
                                        //go to sleep
                                    }
                                }
                            }

                  

                       
                        };
                        
                        
                        
                        
                        timer=new Timer();
                        timer.schedule(task,new Date(),100000);                          //every 10 mins
                   //}
                    
                   
                    return null;
                }
            }).get();
                } catch (InterruptedException ex) {
                   Exceptions.printStackTrace(ex);
               } catch (ExecutionException ex) {
                   Exceptions.printStackTrace(ex);
               }
                
        
        }
        
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
             //   System.out.println("watcher.LogWatcher.extract(): found existing log for  : "+next.getSubsurfaces()+" log under: "+next.getLogpath()+" adding to map");
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
         /*
        //for 2DProcess volumes and SEGDLOAD volumes
        */
        if(volume.getVolumeType().equals(1L) || volume.getVolumeType().equals(2L) ){
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
                           // System.out.println("watcher.LogWatcher.commitToDb(): I found "+lgw.linename+" with Log: "+lgw.filename+" made on: "+lgw.date+"" );//" with version: "+preVersion);
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
                System.out.println("watcher.LogWatcher.commitToDb(): starting a new workflowWatcher for volume :"+volume.getNameVolume());
                workflowWatcher=new WorkflowWatcher(volume);
               
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                            Date date=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(oldDate);                   // dateTime set to 201001010000
                            for (Iterator<LogWatchHolder> iterator1 = lgwl.iterator(); iterator1.hasNext();) {   //loop to find the insight version from the latest log
                                LogWatchHolder lgw = iterator1.next();
                               // System.out.println("watcher.LogWatcher.getsubInsightVersionMap(): "+lgw.dateTime);
                                Date d=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(lgw.date);
                                if(d.after(date)){
                                    date=d;
                                    insver=lgw.insightVersion;
                                 //   System.out.println("watcher.LogWatcher.getsubInsightVersionMap(): "+dateTime+"  inv: "+insver);
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

    
    /*
    check if the gun_cable.log files are done updating
    */
    private boolean checkIfSegDLogIsDone(File f) {
                            DugioScripts ds=new DugioScripts();
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(ds.getSegdLoadCheckIfGCLogsFinished().getAbsolutePath(),f.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            boolean isdone=false;
                                try {
                                    while((line=br.readLine())!=null){    //script will return either 0(success) or 1 (fail)
                                        int done=Integer.valueOf(line);
                                        System.out.println(".checkIfSegDLogIsDone(): linevalue is: "+line);
                                            if(done==0){
                                                isdone=true;
                                            }else{
                                                isdone=false;
                                            }
                                    }
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            return isdone;
                        }
    
    /*
    check contents of file not present in db
    */
    
    private List<LogWatchHolder> getModifiedContents(File gcfile) {
                            DugioScripts ds=new DugioScripts();
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(ds.getSegdLoadLinenameTimeFromGCLogs().getAbsolutePath(),gcfile.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            List<LogWatchHolder> allResults=new ArrayList<>();
                      //      System.out.println(".getModifiedContents():Script results");
                                try {
                                    while((line=br.readLine())!=null){          //line will be of form  01-Nov-2017T10:50:41 0327-1P11234A082_cable2_gun2
                                        String timeStamp=line.substring(0,line.indexOf(" "));
                                        String linename=line.substring(line.indexOf(" ")+1,line.length());
                                        String seq=line.substring(line.indexOf("_")-3,line.indexOf("_"));
                                     //   /* String dateTime=timeStamp.substring(0,timeStamp.indexOf("T"));
                                    //    String timeOfDay=timeStamp.substring(timeStamp.indexOf("T")+1,timeStamp.length());*/
                                        
                                        
                                        
                                        DateTimeFormatter formatter=DateTimeFormat.forPattern("dd-MMM-yyyy'T'HH:mm:ss");
                                        DateTime dt=formatter.parseDateTime(timeStamp);
      
                                        DateTimeFormatter opformat=new DateTimeFormatterBuilder()
                                                .appendDayOfWeekShortText()
                                                .appendLiteral(" ")
                                                .appendMonthOfYearShortText()
                                                .appendLiteral(" ")
                                                .appendDayOfMonth(2)
                                                .appendLiteral(" ")
                                                .appendHourOfDay(2)
                                                .appendLiteral(":")
                                                .appendMinuteOfHour(2)
                                                .appendLiteral(":")
                                                .appendSecondOfMinute(2)
                                                .appendLiteral(" ")
                                                .appendTimeZoneShortName()
                                                .appendLiteral(" ")
                                                .appendYear(4, 4)
                                                .toFormatter();
                                        
                                        
                                        
                                        
                                        
                                        
                                        String dateTime=opformat.print(dt);
                            //            System.out.println(".getModifiedContents(): TimeStamp: "+timeStamp+" line: "+linename+" jodadatetime: "+dateTime);
                                        LogWatchHolder lw=new LogWatchHolder(dateTime,gcfile.getAbsolutePath() , Long.valueOf(seq), linename, "");
                                        allResults.add(lw);
                                    }   } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                                
                                
                                List<LogWatchHolder> modifiedContents=new ArrayList<>();
                                for(LogWatchHolder l:allResults){
                                try {
                                    Logs log=lserv.getLogsFor(volume,l.linename,l.date,l.filename);
                                    if(log==null){              //if the database doesn't contain any log for the above params, then add to the list of modified.
                                        modifiedContents.add(l);
                                    }
                                } catch (Exception ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                                }
                                
                               return modifiedContents;
                            
                        }
    
    /*
    get insight version and link it to the subline
    */
    
   private void getInsightVersionsFromLog(File gcfile,List<LogWatchHolder> modifiedList) {
                            DugioScripts ds=new DugioScripts();
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(ds.getSegdLoadSaillineInsightFromGCLogs().getAbsolutePath(),gcfile.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            Map<String,String> sailInsMap=new HashMap<>();  //sailline insight Map
                                try {
                                    while((line=br.readLine())!=null){  //line now will read "5.0-707143-plcs  0327-1P1205B081"
                                         String insVersion=line.substring(0,line.indexOf(" "));
                                         String sailline=line.substring(line.indexOf(" ")+1,line.length());
                                 //        System.out.println(".getInsightVersionsFromLog(): sailline: "+sailline+" insight: "+insVersion);
                                         sailInsMap.put(sailline, insVersion);
                                    }   
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                                
                            for (Map.Entry<String, String> entry : sailInsMap.entrySet()) {
                                String saill = entry.getKey();
                                String ins = entry.getValue();
                                
                                for(LogWatchHolder l:modifiedList){
                                    if(l.linename.contains(saill)){
                                   //     System.out.println(".getInsightVersionsFromLog(): adding insight version"+ins+" to "+l.linename+" which belongs to sailline: "+saill);
                                        l.insightVersion=ins;
                                    }
                                }
                                
                            }     
                        }

    
}
