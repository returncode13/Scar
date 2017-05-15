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
import dugex.DugioScripts;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adira0150
 */
class LogStatusHolder{
    String filename;
    Boolean green;      //completed successfully
    Boolean red;        //errored
    Boolean orange;     //running
    Boolean purple;     //cancelled
    
}
public class LogStatusWatcher {
    private LogsService lserv=new LogsServiceImpl();
    private List<Logs>  listOfdbLogs;
    private List<LogStatusHolder> logstatusHolderList=new ArrayList<>();
    private VolumeService volserv=new VolumeServiceImpl();
    private VolumeSelectionModel volselmodel;
    private Volume volume;
    private DugioScripts dugioscripts;
    
    TimerTask task;
    Timer timer;
    
    
    public LogStatusWatcher(VolumeSelectionModel volselmod){
        this.volselmodel=volselmod;
        dugioscripts=new DugioScripts();
        volume = volserv.getVolume(this.volselmodel.getId());
        
        ExecutorService executorserv= Executors.newFixedThreadPool(1);
        try{
            executorserv.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    /* if(listOfdbLogs!=null){
                    for (Iterator<Logs> iterator = listOfdbLogs.iterator(); iterator.hasNext();) {
                    Logs log = iterator.next();
                    String logpath=log.getLogpath();
                    String linename=log.getSubsurfaces();
                    //Start of green block
                    Process processg=new ProcessBuilder(new DugioScripts().getLogStatusCompletedSuccessfully().getAbsolutePath(),logpath).start();
                    InputStream isg = processg.getInputStream();
                    InputStreamReader isrg=new InputStreamReader(isg);
                    BufferedReader brg=new BufferedReader(isrg);
                    String valuesg;
                    while((valuesg=brg.readLine())!=null){
                    System.out.println("watcher.LogStatusWatcher().init<>().call(): "+(valuesg!=null?valuesg+" : "+linename:" Values are null for "+linename));
                    log.setGreen(Boolean.TRUE);
                    log.setRed(Boolean.FALSE);
                    log.setOrange(Boolean.FALSE);
                    log.setPurple(Boolean.FALSE);
                    }
                    //End of green block
                    //Start of red block
                    Process processr=new ProcessBuilder(new DugioScripts().getLogStatusErrored().getAbsolutePath(),logpath).start();
                    InputStream isr = processr.getInputStream();
                    InputStreamReader isrr=new InputStreamReader(isr);
                    BufferedReader brr=new BufferedReader(isrr);
                    String valuesr;
                    while((valuesr=brr.readLine())!=null){
                    System.out.println("watcher.LogStatusWatcher().init<>().call(): "+(valuesr!=null?valuesr+" : "+linename:" Values are null for "+linename));
                    log.setGreen(Boolean.FALSE);
                    log.setRed(Boolean.TRUE);
                    log.setOrange(Boolean.FALSE);
                    log.setPurple(Boolean.FALSE);
                    
                    }
                    //End of red block
                    
                    //Start of purple block
                    Process processp=new ProcessBuilder(new DugioScripts().getLogStatusCancelled().getAbsolutePath(),logpath).start();
                    InputStream isp = processp.getInputStream();
                    InputStreamReader isrp=new InputStreamReader(isp);
                    BufferedReader brp=new BufferedReader(isrp);
                    String valuesp;
                    while((valuesp=brp.readLine())!=null){
                    System.out.println("watcher.LogStatusWatcher().init<>().call(): "+(valuesp!=null?valuesp+" : "+linename:" Values are null for "+linename));
                    log.setGreen(Boolean.FALSE);
                    log.setRed(Boolean.FALSE);
                    log.setOrange(Boolean.FALSE);
                    log.setPurple(Boolean.TRUE);
                    }
                    //End of purple block
                    
                    //Start of orange block
                    //End of orange block
                    
                    }
                    
                    
                    for (Iterator<Logs> iterator = listOfdbLogs.iterator(); iterator.hasNext();) {
                    Logs log=iterator.next();
                    lserv.updateLogs(log.getIdLogs(), log);
                    }
                    
                    
                    }
                    
                    */
                    
                    task=new TimerTask() {
                        @Override
                        public void run() {
                        listOfdbLogs=lserv.getLogsFor(volume,false,true,false,false);                   //get only logs that are running
                             if(listOfdbLogs!=null){
                        for (Iterator<Logs> iterator = listOfdbLogs.iterator(); iterator.hasNext();) {
                            try {
                                Logs log = iterator.next();
                                String logpath=log.getLogpath();
                                String linename=log.getSubsurfaces();
                                //Start of green block
                                Process processg=new ProcessBuilder(dugioscripts.getLogStatusCompletedSuccessfully().getAbsolutePath(),logpath).start();
                                InputStream isg = processg.getInputStream();
                                InputStreamReader isrg=new InputStreamReader(isg);
                                BufferedReader brg=new BufferedReader(isrg);
                                String valuesg;
                                while((valuesg=brg.readLine())!=null){
                                    System.out.println("watcher.LogStatusWatcher().init<>().call() Green: "+(valuesg!=null?valuesg+" : "+linename:" Values are null for "+linename));
                                    log.setCompletedsuccessfully(Boolean.TRUE);
                                    log.setErrored(Boolean.FALSE);
                                    log.setRunning(Boolean.FALSE);
                                    log.setCancelled(Boolean.FALSE);
                                }
                                //End of green block
                                //Start of red block
                                Process processr=new ProcessBuilder(dugioscripts.getLogStatusErrored().getAbsolutePath(),logpath).start();
                                InputStream isr = processr.getInputStream();
                                InputStreamReader isrr=new InputStreamReader(isr);
                                BufferedReader brr=new BufferedReader(isrr);
                                String valuesr;
                                while((valuesr=brr.readLine())!=null){
                                    System.out.println("watcher.LogStatusWatcher().init<>().call() Red: "+(valuesr!=null?valuesr+" : "+linename:" Values are null for "+linename));
                                    log.setCompletedsuccessfully(Boolean.FALSE);
                                    log.setErrored(Boolean.TRUE);
                                    log.setRunning(Boolean.FALSE);
                                    log.setCancelled(Boolean.FALSE);
                                    
                                }
                                //End of red block
                                
                                //Start of purple block
                                Process processp=new ProcessBuilder(dugioscripts.getLogStatusCancelled().getAbsolutePath(),logpath).start();
                                InputStream isp = processp.getInputStream();
                                InputStreamReader isrp=new InputStreamReader(isp);
                                BufferedReader brp=new BufferedReader(isrp);
                                String valuesp;
                                while((valuesp=brp.readLine())!=null){
                                    System.out.println("watcher.LogStatusWatcher().init<>().call() Purple: "+(valuesp!=null?valuesp+" : "+linename:" Values are null for "+linename));
                                    log.setCompletedsuccessfully(Boolean.FALSE);
                                    log.setErrored(Boolean.FALSE);
                                    log.setRunning(Boolean.FALSE);
                                    log.setCancelled(Boolean.TRUE);
                                }
                                //End of purple block
                                
                                //Start of orange block
                                //End of orange block
                            } catch (IOException ex) {
                                Logger.getLogger(LogStatusWatcher.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        }
                    
                        
                        for (Iterator<Logs> iterator = listOfdbLogs.iterator(); iterator.hasNext();) {
                            Logs log=iterator.next();
                            lserv.updateLogs(log.getIdLogs(), log);
                        }
                        
                        
                    }
                    
                        }
                    };
                        
                        timer=new Timer();
                        timer.schedule(task,new Date(),30000);       
                    
                   
                    
                    return null;
                }
            }).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(LogStatusWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LogStatusWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
