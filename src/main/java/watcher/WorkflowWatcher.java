/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import db.model.Logs;
import db.model.Volume;
import db.model.Workflow;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import dugex.DugioScripts;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author sharath nair
 */
class WorkflowHolder{
    String md5;
    String context;
    
    
}
public class WorkflowWatcher {
    
    private Volume volume;
    private final VolumeService vserv=new VolumeServiceImpl();
    private final LogsService lserv=new LogsServiceImpl();
    private final WorkflowService wserv=new WorkflowServiceImpl();
    private List<Logs> loglist;
    private Map<Logs,WorkflowHolder> mlogwfholder=new HashMap<>();
    private WorkflowHolder workflowHolder;
    private MessageDigest md;
    private DugioScripts dugioscripts;
    private TimerTask task;
    private Timer timer;
    
    
    
    public WorkflowWatcher(Volume volume)  {
        this.volume = volume;
        dugioscripts=new DugioScripts();
      task=new TimerTask(){
            @Override
            public void run() {
                watchForWorkflows();
                
            }
          
      };
      
      timer=new Timer();
      timer.schedule(task, new Date(),10000);
          
    }
    
    private void watchForWorkflows(){
          Workflow wnull=null;
        loglist=lserv.getLogsFor(volume,wnull);
        try {
            
         ExecutorService executorService= Executors.newFixedThreadPool(1);
        if(volume.getVolumeType().equals(1L)){
            
        
            executorService.submit(new Callable<Void>(){
             @Override
             public Void call() throws Exception {
                for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
            
                Logs next = iterator.next();
                String logpath=next.getLogpath();
                Process process=new ProcessBuilder(dugioscripts.getWorkflowExtractor().getAbsolutePath(),logpath).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                workflowHolder=new WorkflowHolder();
                String value;
                String content=new String();
                while((value=br.readLine())!=null){
                   // System.out.println("watcher.WorkflowWatcher.init<>.call(): "+value);
                    content+=value;
                    content+="\n";
                };
                
                workflowHolder.context=content;
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=workflowHolder.context.getBytes("UTF8");
                md.update(workflowHolder.context.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
                workflowHolder.md5=new String(sbuf.toString());
                   // System.out.println("watcher.WorkflowWatcher.init<>.call(): checking for  : md5"+workflowHolder.md5+" and content size: "+workflowHolder.context.length());
                List<Workflow> wlist=wserv.getWorkFlowWith(workflowHolder.md5, WorkflowWatcher.this.volume);
                
                if(wlist==null){                    // no workflow for vol with md5, so create a new entry for such a workflow
                    Workflow wver=wserv.getWorkFlowVersionFor(WorkflowWatcher.this.volume); //get the  workflow with the highest version for this volume
                    Long vers=-1L;
                    if(wver==null){
                        vers=0L;
                    }
                    else{
                        vers=wver.getWfversion();               //get the version from the workflow
                    }
                    Workflow newWorkflow=new Workflow();
                    newWorkflow.setContents(workflowHolder.context);
                    newWorkflow.setMd5sum(workflowHolder.md5);
                    newWorkflow.setWfversion(++vers);       //increment the version
                    newWorkflow.setVolume(WorkflowWatcher.this.volume);
                    System.out.println("watcher.WorkflowWatcher.init<>.call(): creating entry with : md5 "+workflowHolder.md5+" and version: "+vers);
                    wserv.createWorkFlow(newWorkflow);      //create the workflow in the db table
                    next.setWorkflow(newWorkflow);          //set the log in question to have this new workflow
                }
                else{                                       //found a workflow for vol with md5 . use that 
                    Workflow w=wlist.get(0);                //there should be just one such entry. Put a check for this 
                    next.setWorkflow(w);                    //the log in question now has the workflow
                    
                }
                
             //   mlogwfholder.put(next, workflowHolder);
                    
            lserv.updateLogs(next.getIdLogs(), next);       //update all these logs . all logs now have a workflow assigned
             }
                return null;
             }
                
         }).get();
        }
        
        if(volume.getVolumeType().equals(2L)){
           // System.out.println("watcher.WorkflowWatcher.watchForWorkflows()");
        
            executorService.submit(new Callable<Void>(){
             @Override
             public Void call() throws Exception {
                for (Iterator<Logs> iterator = loglist.iterator(); iterator.hasNext();) {
            
                Logs next = iterator.next();
                String logpath=volume.getPathOfVolume();                //type 2 workflow information stored in volPath/notes.txt
                Process process=new ProcessBuilder(dugioscripts.getSegdLoadNotesTxtTimeWorkflowExtractor().getAbsolutePath(),logpath).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                workflowHolder=new WorkflowHolder();
                String value;
                String content=new String();
                String delimiter="--Contents--";  //Split time and contents here. This is based on a word hardcoded in the extraction script
                String time =new String();
                
                while((value=br.readLine())!=null){
                    time= value.substring(0,value.indexOf(delimiter));
                    content+=value.substring(value.indexOf(delimiter)+delimiter.length(),value.length());
                   // System.out.println("watcher.WorkflowWatcher.init<>.call(): "+value);
                    //content+=value;
                    //content+="\n";
                };
                    System.out.println("watcher.WorkflowWatcher.watchForWorkflows().SGDLOAD Volume: call(): Time: "+time);
                    System.out.println("watcher.WorkflowWatcher.watchForWorkflows().SGDLOAD Volume: call(): Content: "+content);
                workflowHolder.context=content;
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=workflowHolder.context.getBytes("UTF8");
                md.update(workflowHolder.context.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
                workflowHolder.md5=new String(sbuf.toString());
                    System.out.println("watcher.WorkflowWatcher.watchForWorkflows().SGDLOAD Volume: call(): MD5: "+md);
                   // System.out.println("watcher.WorkflowWatcher.init<>.call(): checking for  : md5"+workflowHolder.md5+" and content size: "+workflowHolder.context.length());
                   List<Workflow> wlist=wserv.getWorkFlowWith(workflowHolder.md5, WorkflowWatcher.this.volume);
                   
                   if(wlist==null){                    // no workflow for vol with md5, so create a new entry for such a workflow
                   Workflow wver=wserv.getWorkFlowVersionFor(WorkflowWatcher.this.volume); //get the  workflow with the highest version for this volume
                   Long vers=-1L;
                   if(wver==null){
                   vers=0L;
                   }
                   else{
                   vers=wver.getWfversion();               //get the version from the workflow
                   }
                   Workflow newWorkflow=new Workflow();
                   newWorkflow.setContents(workflowHolder.context);
                   newWorkflow.setMd5sum(workflowHolder.md5);
                   newWorkflow.setWfversion(++vers);       //increment the version
                   newWorkflow.setVolume(WorkflowWatcher.this.volume);
                   System.out.println("watcher.WorkflowWatcher.init<>.call(): creating entry with : md5 "+workflowHolder.md5+" and version: "+vers);
                   wserv.createWorkFlow(newWorkflow);      //create the workflow in the db table
                   next.setWorkflow(newWorkflow);          //set the log in question to have this new workflow
                   }
                   else{                                       //found a workflow for vol with md5 . use that
                   Workflow w=wlist.get(0);                //there should be just one such entry. Put a check for this
                   next.setWorkflow(w);                    //the log in question now has the workflow
                   
                   }
                   
                   //   mlogwfholder.put(next, workflowHolder);
                   
                   lserv.updateLogs(next.getIdLogs(), next);       //update all these logs . all logs now have a workflow assigned*/   //UNOMMENT THIS
             }
                return null;
             }
                
         }).get();
        }
            
        
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkflowWatcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(WorkflowWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
