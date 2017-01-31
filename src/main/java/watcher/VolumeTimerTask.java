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
public class VolumeTimerTask {
    
    private String volume;          // path to the dugio volume
    TimerTask task;
    Timer timer;
    final private String filter="idx";
    public VolumeTimerTask(String volume)
    {
        System.out.println("watcher.VolumeTimerTask: started to watch");
        this.volume=volume;
        
        task=new VolumeWatcher(volume, filter) {
            
            @Override
            protected void onChange(File sub, String action) {
                System.out.println("Volume? "+sub.getParentFile().getName()+" : sub: "+sub.getName().split("\\.")[0].split("-")[1]+" was "+action);
            }
        };
        
     timer=new Timer();
     timer.schedule(task,new Date(),1000);                          //every second.
    }
    
}
