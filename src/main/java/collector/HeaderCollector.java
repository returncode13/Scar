/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import db.model.Acquisition;
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
import dugex.DugioHeaderValuesExtractor;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import watcher.LogWatcher;

/**
 *
 * @author sharath nair
 */

class TempArrayHolder{
    ArrayList<Headers> hdrlist=new ArrayList();

    public ArrayList<Headers> getHdrlist() {
        return hdrlist;
    }

    public void setHdrlist(ArrayList<Headers> hdrlist) {
        this.hdrlist = hdrlist;
    }
    
    
}
public class HeaderCollector {
    //from frontEnd. user
    
    private VolumeSelectionModel feVolumeSelModel;
    private HeadersModel headersModel;                                                                      //the headers model corresponding to this volume
    Set<SubSurface> sl;                                                                                     // the SET of subsurfaces in the volume. Note this DOES NOT account for more than one occurrence of the sub
    List<Sequences> seqList;
    private AcquisitionService acqServ=new AcquisitionServiceImpl();
    private TempArrayHolder headerholder=new TempArrayHolder();
    
    private DugioHeaderValuesExtractor dugHve=new DugioHeaderValuesExtractor();
    
    //for db
    private ArrayList<Headers> dbHeaders=new ArrayList<>();
    
    //for db Transactions
    final private static HeadersService hdrServ=new HeadersServiceImpl();
    final private static VolumeService volServ=new VolumeServiceImpl();
    private Volume dbVolume;
    private String logLocation;
    //LogWatcher logForSub;
    final private static LogsService lserv=new LogsServiceImpl();
    
    
    public void setFeVolumeSelModel(VolumeSelectionModel feVolumeSelModel) {
        this.feVolumeSelModel = feVolumeSelModel;
        this.headersModel=this.feVolumeSelModel.getHeadersModel();
        dbVolume = volServ.getVolume(feVolumeSelModel.getId());                                 //retrieve the correct dbVolume from the db. This would mean that the dbVolume table needs to exist before Headers are retrieved
        
        
        System.out.println("HeaderColl: Set the volume Sel model "+feVolumeSelModel.getLabel());
        System.out.println("HeaderColl: volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        logLocation=dbVolume.getPathOfVolume()+"/../../000scratch/logs";
                
        System.out.println("collector.HeaderCollector: looking for logs in "+logLocation);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               calculateAndCommitHeaders();
            //logForSub.commitToDb();
            return null;
            }
            
        });
                 
        
    }
    
    private void calculateAndCommitHeaders(){
                        

        
            
            
           
               
           
            
            
            
            File volume=feVolumeSelModel.getVolumeChosen();
            Long volumeType=feVolumeSelModel.getVolumeType();
            dugHve.setVolume(volume);
         //   logForSub=new LogWatcher(logLocation,"", feVolumeSelModel, Boolean.TRUE);
       //     while(true)
      //  {
        try {
           
            List<Headers> existingHeaders=null;
            Map<String,Headers> subsurfaceHeaderMap=new HashMap<>();
            System.out.println("collector.HeaderCollector: calculating headers for "+volume.getAbsolutePath());
            
            if(dbVolume.getHeaderExtracted()){
                System.out.println("collector.HeaderCollector: Headers have been extracted for Volume: "+dbVolume.getNameVolume());
                existingHeaders=hdrServ.getHeadersFor(dbVolume);
                for(Headers h:existingHeaders){
                    subsurfaceHeaderMap.put(h.getSubsurface(), h);
                } 
                
                
                
            }
             ArrayList<Headers> headerList=new ArrayList<>();
            ExecutorService exec=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            final Map<String,Headers> finalsubMap=subsurfaceHeaderMap;
            final List<Headers> finalExistingHeaders=existingHeaders;
             System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): running on Thread: "+Thread.currentThread().getName()+" forking");
          
                       
                       ExecutorService exec1=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                       exec1.submit(new Callable<ArrayList<Headers>>(){
                           @Override
                           public ArrayList<Headers> call() throws Exception {
                               System.out.println("collector.HeaderCollector.calculateAndCommitHeaders() about to call dugHve.calculateHeaders(): running on Thread: "+Thread.currentThread().getName()+" forking");
                               headerList.addAll(dugHve.calculatedHeaders(finalsubMap, finalExistingHeaders));
                               return headerList;
                           }
                       }).get();
                       
                   
            System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): running on Thread: "+Thread.currentThread().getName()+" joining");
            if(finalExistingHeaders!=null){
                headerList.addAll(finalExistingHeaders);                                                         //append any old headers
            }
            if (headerList.isEmpty()){
                System.out.println("collector.HeaderCollector: headerList is empty");
               // return null;                     // the while loop will break when there are no more headers to process.
            }
            else
                System.out.println("collector.HeaderCollector: headerList is NOT empty : size: "+headerList.size());                    
            /*sl=new ArrayList<>();*/
            sl=new HashSet<>();
           seqList=new ArrayList<>();
            MultiMap<Long,SubSurface> seqSubMap=new MultiValueMap<>();                                             //for creating association between Sequences and Subsurfaces
            int aci=0;
            
           
                    
           // Map<String,String> subInsightVersionFromLogMap=logForSub.getsubInsightVersionMap();
            System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): committing headers to the database");
            for (Iterator<Headers> iterator = headerList.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                next.setVolume(dbVolume);
                
                String lineN=next.getSubsurface();
                List<Logs> logs=lserv.getLogsFor(dbVolume, lineN);
                Logs latestLog=lserv.getLatestLogFor(dbVolume, lineN);
                if(latestLog!=null){
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): LatestLog for line: "+lineN+" is: "+latestLog.getLogpath()+" created at: "+latestLog.getTimestamp());
                    next.setInsightVersion(latestLog.getInsightVersion());
                }else{
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): I couldn't find the latest log entry for "+dbVolume.getNameVolume()+" : "+lineN);
                    next.setInsightVersion(new String("no logs found"));
                }
                    //String latestInsightVersion=lserv.getInsightVersionFromLatestLog();
                    
                    if(logs!=null){
                        next.setNumberOfRuns(new Long(logs.size()));
                    }else{
                        next.setNumberOfRuns(-1L);
                    }
                
                
                
                /// Code up a method to set id of headers based on the hash generated from fields (subsurface,tracecount,.....) of the headers. 
                if(next.getModified()) 
                {
                    System.out.println(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be updated!");
                    hdrServ.updateHeaders(next.getIdHeaders(), next);       
                }
                else{
                hdrServ.createHeaders(next);                             //commit to the db
                }
                
                
                for (Iterator<Logs> iterator1 = logs.iterator(); iterator1.hasNext();) {
                    Logs next1 = iterator1.next();
                    next1.setHeaders(next);
                    lserv.updateLogs(next1.getIdLogs(), next1);
                }
                SubSurface s= new SubSurface();
          
                s.setSequenceNumber(next.getSequenceNumber());
                
                /*
                DEBUG ONLY!! START 
                */
                /*Acquisition ac=new Acquisition();
                ++aci;
                Long l=new Long(aci);
                ac.setId(l);
                ac.setSubsurfaceLines(next.getSubsurface());
                acqServ.createAcquisition(ac);*/
                
                /*
                DEBUG END
                */
                
                s.setSubsurface(next.getSubsurface());
                s.setTimeStamp(next.getTimeStamp());
          
                s.setCmpInc(next.getCmpInc());
                s.setCmpMax(next.getCmpMax());
                s.setCmpMin(next.getCmpMin());
                s.setDugChannelInc(next.getDugChannelInc());
                s.setDugChannelMax(next.getDugChannelMax());
                s.setDugChannelMin(next.getDugChannelMin());
                s.setDugShotInc(next.getDugShotInc());
                s.setDugShotMax(next.getDugShotMax());
                s.setDugShotMin(next.getDugShotMin());
                s.setInlineInc(next.getInlineInc());
                s.setInlineMax(next.getInlineMax());
                s.setInlineMin(next.getInlineMin());
                s.setOffsetInc(next.getOffsetInc());
                s.setOffsetMax(next.getOffsetMax());
                s.setOffsetMin(next.getOffsetMin());
          
                s.setTraceCount(next.getTraceCount());
                s.setXlineInc(next.getXlineInc());
                s.setXlineMax(next.getXlineMax());
                s.setXlineMin(next.getXlineMin());
                s.setModified(next.getModified());
                s.setDeleted(next.getDeleted());
                s.setVersion(next.getNumberOfRuns());
                s.setInsightVersion(next.getInsightVersion());
                
          
                seqSubMap.put(s.getSequenceNumber(), s);
                
                
                 sl.add(s);
                
                
                
                
                
            }
            
            
            Set<Long> seqNos=seqSubMap.keySet();
      
      
      for (Iterator<Long> iterator = seqNos.iterator(); iterator.hasNext();) {
          Long next = iterator.next();
          Sequences sq=new Sequences();
          ArrayList<SubSurface> ssubs=(ArrayList<SubSurface>) seqSubMap.get(next);
          sq.setSubsurfaces(ssubs);
          seqList.add(sq);
      }
            
//      LogWatcher logForSub=new LogWatcher(logLocation,"", feVolumeSelModel, Boolean.TRUE);
     feVolumeSelModel.setSubsurfaces(sl);
       //feVolumeSelModel.setSeqSubsMap(seqSubMap);
       
      ObservableList<Sequences> obseq=FXCollections.observableArrayList(seqList);
      headersModel.setSequenceListInHeaders(obseq);                                     //set the headersModel that will be used to launch the header table
      dbVolume.setHeaderExtracted(Boolean.TRUE);
      volServ.setHeaderExtractionFlag(dbVolume);
          
        } catch (InterruptedException ex) {
        Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
        Logger.getLogger(HeaderCollector.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
       // feVolumeSelModel.setHeaderButtonStatus(Boolean.TRUE);
   // }
    }

  public List<Sequences> getHeaderListForVolume(VolumeSelectionModel vm){
    /*  List<Headers> hl=hdrServ.getHeadersFor(dbVolume);
      List<SubSurface> sl=new ArrayList<>();
      List<Sequences> seqList=new ArrayList<>();
      MultiMap<Long,SubSurface> seqSubMap=new MultiValueMap<>();                                             //for creating association between Sequences and Subsurfaces
      
      for (Iterator<Headers> iterator = hl.iterator(); iterator.hasNext();) {
          Headers next = iterator.next();
          SubSurface s= new SubSurface();
          
          s.setSequenceNumber(next.getSequenceNumber());
          s.setSubsurface(next.getSubsurface());
          s.setTimeStamp(next.getTimeStamp());
          
          s.setCmpInc(next.getCmpInc());
          s.setCmpMax(next.getCmpMax());
          s.setCmpMin(next.getCmpMin());
          s.setDugChannelInc(next.getDugChannelInc());
          s.setDugChannelMax(next.getDugChannelMax());
          s.setDugChannelMin(next.getDugChannelMin());
          s.setDugShotInc(next.getDugShotInc());
          s.setDugShotMax(next.getDugShotMax());
          s.setDugShotMin(next.getDugShotMin());
          s.setInlineInc(next.getInlineInc());
          s.setInlineMax(next.getInlineMax());
          s.setInlineMin(next.getInlineMin());
          s.setOffsetInc(next.getOffsetInc());
          s.setOffsetMax(next.getOffsetMax());
          s.setOffsetMin(next.getOffsetMin());
          
          s.setTraceCount(next.getTraceCount());
          s.setXlineInc(next.getXlineInc());
          s.setXlineMax(next.getXlineMax());
          s.setXlineMin(next.getXlineMin());
          
          seqSubMap.put(s.getSequenceNumber(), s);
         
          
          sl.add(s);
          
          
          
      }
      
      Set<Long> seqNos=seqSubMap.keySet();
      
      
      for (Iterator<Long> iterator = seqNos.iterator(); iterator.hasNext();) {
          Long next = iterator.next();
          Sequences sq=new Sequences();
          ArrayList<SubSurface> ssubs=(ArrayList<SubSurface>) seqSubMap.get(next);
          sq.setSubsurfaces(ssubs);
          seqList.add(sq);
      }
      
      feVolumeSelModel.setSubsurfaces(sl);
      ObservableList<Sequences> obseq=FXCollections.observableArrayList(seqList);
      headersModel.setObsHList(obseq);
      
      System.out.println("HColl: done setting the headerList here");
*/
      headersModel=vm.getHeadersModel();
      return headersModel.getSequenceListInHeaders();                                                //the observable List is the list of sequences. which contains all the header information
  }
    
    
    
}
