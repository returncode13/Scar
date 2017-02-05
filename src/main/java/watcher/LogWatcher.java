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

import fend.session.node.volumes.VolumeSelectionModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author naila0152
 */

class LogWatchHolder{
    String date;
    String filename;
    String linename;

    public LogWatchHolder(String date, String filename, String linename) {
        this.date = date;
        this.filename = filename;
        this.linename = linename;
    }
    
    
}

public class LogWatcher {
    private String logsLocation;          // path to the dugio volume logs folder
    
    TimerTask task;
    Timer timer;
    private static int counter=0;
    private String filter=new String();
    
    VolumeSelectionModel volume;
    HeadersService hserv=new HeadersServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    LogsService lserv=new LogsServiceImpl();
    List<LogWatchHolder> lwatchHolderList=new ArrayList<>();
    MultiMap<String, LogWatchHolder> maplineLog=new MultiValueMap<>();
    public LogWatcher(){
    }
    
    public LogWatcher(String logsLocation,String filter,VolumeSelectionModel vmod,Boolean tableExtraction) 
    {
        counter++;
        
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        try {
            executorService.submit(new Callable<Void>() {
                
                @Override
                public Void call() throws Exception {
                    LogWatcher.this.volume=vmod;
                    Volume v=vserv.getVolume(volume.getId());
                    
                    System.out.println("watcher.LogWatcher: started to watch");
                    LogWatcher.this.logsLocation=logsLocation;
                    LogWatcher.this.filter=filter;
                    
                    
                    if(!tableExtraction){
                        task=new Watcher(LogWatcher.this.logsLocation, filter) {
                            
                            @Override
                            protected void onChange(File logfile, String action) {
                                
                                
                                
                                if(action.equals("Added")){
                                    
                                    
                                    System.out.println(logfile.getName() + " Added called:  "+counter);
                                    
                                    try {
                                        Process process=new ProcessBuilder(new DugioScripts().getSubsurfaceLog().getAbsolutePath(),logfile.getParentFile().getAbsolutePath()).start();
                                        InputStream is = process.getInputStream();
                                        InputStreamReader isr=new InputStreamReader(is);
                                        BufferedReader br=new BufferedReader(isr);
                                        lwatchHolderList.clear();
                                        String value;
                                        while((value=br.readLine())!=null){
                                            Long maxVersion=0L;
                                            
                                            String[] parts=value.split("\\s");
                                            String date=parts[0].substring(parts[0].indexOf(":")+1);
                                            
                                            String time=parts[1];
                                            DateFormat jdate=new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                                            Date convjdate=jdate.parse(date+":"+time);
                                            String filename=parts[0].substring(parts[0].lastIndexOf("/")+1,parts[0].indexOf(":"));
                                            String linename=parts[2].split("=")[1];
                                            

                                            
                                            //   System.out.println("got: "+linename+" logsLocation: "+filename+" ");
                                            
                                            LogWatchHolder lwatchholder=new LogWatchHolder(convjdate.toString(), filename, linename);
                                            lwatchHolderList.add(lwatchholder);
                                            
                                            
                                            
                                        }
                                        
                                        maplineLog.clear();
                                        
                                        for (Iterator<LogWatchHolder> iterator = lwatchHolderList.iterator(); iterator.hasNext();) {
                                            LogWatchHolder next = iterator.next();
                                            //  System.out.println("Inside for loop: "+"got: "+next.linename+" logsLocation: "+next.filename+" "+next.date);
                                            maplineLog.put(next.linename, next);
                                        }
                                        
                                        Set<String> keys=maplineLog.keySet();
                                        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                                            String next = iterator.next();
                                            //  System.out.println("Keys: "+next);
                                        }
                                        
                                        
                                        for (Iterator<LogWatchHolder> iterator = lwatchHolderList.iterator(); iterator.hasNext();) {
                                            LogWatchHolder next = iterator.next();
                                            List<Headers> hlist=hserv.getHeadersFor(v, next.linename);
                                            
                                            
                                            
                                            Boolean newLog=false;
                                            Long maxVersion=0L;
                                            
                                            if(hlist.size()==0){
                                                
                                            }
                                            else if(hlist.size()==1){
                                                Headers h=hlist.get(0);
                                                
                                                List<Logs>logsList=lserv.getLogsFor(h);
                                                if(logsList.isEmpty()){
                                                    System.out.println("watcher.LogWatcher: Loglist is EMPTY");
                                                    
                                                    newLog=true;
                                                    Long preVersion=0L;
                                                    
                                                    
                                                    
                                                    List<LogWatchHolder> logwList=(List<LogWatchHolder>) maplineLog.get(next.linename);
                                                    
                                                    for (Iterator<LogWatchHolder> iterator1 = logwList.iterator(); iterator1.hasNext();) {
                                                        LogWatchHolder lgw = iterator1.next();
                                                        // System.out.println("watcher.LogWatcher: I found "+lgw.linename+" with Log: "+lgw.filename+" made on: "+lgw.date+ " with version: "+preVersion);
                                                        Logs l=new Logs();
                                                        l.setHeaders(h);
                                                        l.setLogpath(logfile.getParentFile().getAbsolutePath()+"/"+lgw.filename);
                                                        l.setVersion(preVersion);
                                                        l.setTimestamp(lgw.date);
                                                        lserv.createLogs(l);
                                                        
                                                        preVersion++;
                                                    }
                                                    
                                                    newLog=false;
                                                    
                                                    
                                                    /*Logs l=new Logs();
                                                    l.setHeaders(h);
                                                    l.setLogpath(logfile.getParentFile().getAbsolutePath()+"/"+next.filename);
                                                    l.setVersion(maxVersion);
                                                    l.setTimestamp(next.date);
                                                    lserv.createLogs(l);
                                                    newLog=false;*/
                                                    ////
                                                    
                                                   
                                                    
                                                    
                                                    
                                                    //////
                                                    
                                                    
                                                    
                                                    
                                                    
                                                    
                                                }
                                                else{
                                                    System.out.println("Loglist is NOT empty");
                                                    
                                                    Boolean exists=false;
                                                    for (Iterator<Logs> iterator1 = logsList.iterator(); iterator1.hasNext();) {
                                                        Logs log = iterator1.next();
                                                        Long currentVersion=log.getVersion();
                                                        String filenameInDb=log.getLogpath();
                                                        if(filenameInDb.equals(logfile.getParentFile().getAbsolutePath()+"/"+next.filename)){
                                                            exists=true;
                                                            continue;
                                                        }
                                                        if(currentVersion>=maxVersion){
                                                            maxVersion=currentVersion;
                                                        }
                                                        
                                                        System.out.println("Contents: ");
                                                        
                                                    }
                                                    
                                                    if(!exists){
                                                        
                                                        
                                                        maxVersion++;
                                                        Logs l=new Logs();
                                                        l.setHeaders(h);
                                                        l.setLogpath(logfile.getParentFile().getAbsolutePath()+"/"+next.filename);
                                                        l.setVersion(maxVersion);
                                                        l.setTimestamp(next.date);
                                                        lserv.createLogs(l);
                                                        exists=true;
                                                    }
                                                    
                                                    
                                                    
                                                }
                                                
                                            }
                                            else if(hlist.size()>1){
                                                //Error
                                            }
                                        }
                              
                                        
                                        
                                        
                                        
                                        
                                        
                                    } catch (IOException ex) {
                                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        };
                        
                        
                        
                        
                        timer=new Timer();
                        timer.schedule(task,new Date(),2000);                          //every second.
                    }
                    
                    else{System.out.println("Inside tableExtraction=true block");
                    
                    
                    
                    try {
                        String logpath=logsLocation;                                     //../000scratch/logs
                        System.out.println("watcher.LogWatcher: LogLocation: "+logpath);
                        Process process=new ProcessBuilder(new DugioScripts().getSubsurfaceLog().getAbsolutePath(),logpath).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        lwatchHolderList.clear();
                        String value;
                        while((value=br.readLine())!=null){
                            Long maxVersion=0L;
                            
                            String[] parts=value.split("\\s");
                            String date=parts[0].substring(parts[0].indexOf(":")+1);
                           
                            String time=parts[1];
                            DateFormat jdate=new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                            Date convjdate=jdate.parse(date+":"+time);
                            String filename=parts[0].substring(parts[0].lastIndexOf("/")+1,parts[0].indexOf(":"));
                            String linename=parts[2].split("=")[1];
                            
                            
                            
                            //   System.out.println("got: "+linename+" logsLocation: "+filename+" ");
                            
                            LogWatchHolder lwatchholder=new LogWatchHolder(convjdate.toString(), filename, linename);
                            lwatchHolderList.add(lwatchholder);
                            
                            
                            
                        }
                        
                        maplineLog.clear();
                        
                        for (Iterator<LogWatchHolder> iterator = lwatchHolderList.iterator(); iterator.hasNext();) {
                            LogWatchHolder next = iterator.next();
                            //  System.out.println("Inside for loop: "+"got: "+next.linename+" logsLocation: "+next.filename+" "+next.date);
                            maplineLog.put(next.linename, next);
                        }
                        
                        Set<String> keys=maplineLog.keySet();
                        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
                            String next = iterator.next();
                            //  System.out.println("Keys: "+next);
                        }
                        
                        
                        for (Iterator<LogWatchHolder> iterator = lwatchHolderList.iterator(); iterator.hasNext();) {
                            LogWatchHolder next = iterator.next();
                            List<Headers> hlist=hserv.getHeadersFor(v, next.linename);
                            
                            
                            
                            Boolean newLog=false;
                            Long maxVersion=0L;
                            
                            if(hlist.size()==0){
                                
                            }
                            else if(hlist.size()==1){
                                Headers h=hlist.get(0);
                                
                                List<Logs>logsList=lserv.getLogsFor(h);
                                if(logsList.isEmpty()){
                                    System.out.println("Loglist is EMPTY");
                                    
                                    newLog=true;
                                    Long preVersion=0L;
                                    
                                    
                                    
                                    List<LogWatchHolder> logwList=(List<LogWatchHolder>) maplineLog.get(next.linename);
                                    
                                    for (Iterator<LogWatchHolder> iterator1 = logwList.iterator(); iterator1.hasNext();) {
                                        LogWatchHolder lgw = iterator1.next();
                                        // System.out.println("watcher.LogWatcher: I found "+lgw.linename+" with Log: "+lgw.filename+" made on: "+lgw.date+ " with version: "+preVersion);
                                        Logs l=new Logs();
                                        l.setHeaders(h);
                                        l.setLogpath(logpath+"/"+lgw.filename);
                                        l.setVersion(preVersion);
                                        l.setTimestamp(lgw.date);
                                        lserv.createLogs(l);
                                        
                                        preVersion++;
                                    }
                                    
                                    newLog=false;
                                    
                                    
                                    /*Logs l=new Logs();
                                    l.setHeaders(h);
                                    l.setLogpath(logfile.getParentFile().getAbsolutePath()+"/"+next.filename);
                                    l.setVersion(maxVersion);
                                    l.setTimestamp(next.date);
                                    lserv.createLogs(l);
                                    newLog=false;*/
                                    
                                    /////
                                    
                                   
                                    
                                    
                                        
                                        
                                        
                                    
                                  
                                    
                                    
                                    /////
                                    
                                    
                                    
                                }
                                else{
                                    System.out.println("Loglist is NOT empty");
                                    
                                    Boolean exists=false;
                                    for (Iterator<Logs> iterator1 = logsList.iterator(); iterator1.hasNext();) {
                                        Logs log = iterator1.next();
                                        Long currentVersion=log.getVersion();
                                        String filenameInDb=log.getLogpath();
                                        if(filenameInDb.equals(logpath+"/"+next.filename)){
                                            exists=true;
                                            continue;
                                        }
                                        if(currentVersion>=maxVersion){                              
                                            maxVersion=currentVersion;
                                        }
                                        
                                        System.out.println("Contents: ");
                                        
                                    }
                                    
                                    if(!exists){
                                        
                                        
                                        maxVersion++;
                                        Logs l=new Logs();
                                        l.setHeaders(h);
                                        l.setLogpath(logpath+"/"+next.filename);
                                        l.setVersion(maxVersion);
                                        l.setTimestamp(next.date);
                                        lserv.createLogs(l);
                                        exists=true;
                                    }
                                    
                                    
                                    
                                }
                                
                            }
                            else if(hlist.size()>1){
                                //Error
                                
                                System.out.println("watcher.LogWatcher: "+next.linename+" has more than one entry for the volume : "+v.getNameVolume());
                            }
                        }
                        
                        
                        
                        
                        
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                    }
                    return null;
                }
            }).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LogWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
    }
    
    
   

    
}
