/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author naila0152
 */
public class VolumeWatcher {
    
    private String volume;          // path to the dugio volume
    TimerTask task;
    Timer timer;
    final private String filter;
    public VolumeWatcher(String volume,String filter)
    {
        System.out.println("watcher.VolumeTimerTask: started to watch");
        this.volume=volume;
        this.filter=filter;
        task=new Watcher(volume, filter) {
            
            @Override
            protected void onChange(File sub, String action) {
                 if(action.equals("Added")){
                System.out.println("Volume? "+sub.getParentFile().getName()+" : sub: "+sub.getName().split("\\.")[0].split("-")[1]+" was "+action+" completeName: "+sub.getName());
                 }
            }
        };
        
     timer=new Timer();
     timer.schedule(task,new Date(),1000);                          //every second.
    }
    
}
