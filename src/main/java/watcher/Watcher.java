/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;

/**
 *
 * @author naila0152
 */
public abstract class Watcher extends TimerTask{
    private String volumePath;
    private File[] subsArray;
    private HashMap volume=new HashMap();
    private WatcherFilter volumeFilterWatcher;
    
    
    
    public Watcher(String volumePath){
        this.volumePath=volumePath;
    }
    
    public Watcher(String volumePath,String filter){
        this.volumePath=volumePath;
        volumeFilterWatcher=new WatcherFilter(filter);
        subsArray=new File(volumePath).listFiles(volumeFilterWatcher);
        
        
        
        for(File sub:subsArray){
            volume.put(sub, new Long(sub.lastModified()));
        }
        
    }
    
    
    public final void run(){
        HashSet checkedsubs=new HashSet();
        subsArray=new File(volumePath).listFiles(volumeFilterWatcher);
        
        
        for(File sub:subsArray){
            Long currentList=(Long) volume.get(sub);
            checkedsubs.add(sub);
            
            if(currentList==null){
                volume.put(sub,new Long(sub.lastModified()));
                onChange(sub,"Added");
            }
            else if(currentList.longValue() != sub.lastModified()){
                volume.put(sub, new Long(sub.lastModified()));
                onChange(sub, "Modified");
            }
        }
        
        
        
        Set reference= ((HashMap)volume.clone()).keySet();
        reference.removeAll((Set)checkedsubs);
        for (Iterator iterator = reference.iterator(); iterator.hasNext();) {
            File deletedFile = (File) iterator.next();
            volume.remove(deletedFile);
            onChange(deletedFile, "Deleted");
            
        }
    }

    protected abstract void onChange(File sub, String added);
}
