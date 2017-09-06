/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import core.Seq;
import core.Sub;
import db.handler.ObpManagerLogDatabaseHandler;
import db.model.Acquisition;
import db.model.Headers;
import db.model.Logs;
import db.model.Sequence;
import db.model.Volume;
import db.model.Workflow;
import db.services.AcquisitionServiceImpl;

import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.LogsService;
import db.services.LogsServiceImpl;
import db.services.SequenceService;
import db.services.SequenceServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import dugex.DugioHeaderValuesExtractor;
import fend.session.dialogs.DialogModel;
import fend.session.dialogs.DialogNode;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import fend.session.node.volumes.type4.VolumeSelectionModelType4;
//import fend.session.node.volumes.type0.VolumeSelectionModelType0;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
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
import java.util.logging.LogManager;
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
    Logger logger=Logger.getLogger(HeaderCollector.class.getName());
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    //private VolumeSelectionModelType1 feVolumeSelModel;
    private VolumeSelectionModelType0 feVolumeSelModel;
    //private AcquisitionVolumeModel acqmodel;
    private HeadersModel headersModel;                                                                      //the headers model corresponding to this volume
    Set<SubSurfaceHeaders> sl;                                                                                     // the SET of subsurfaces in the volume. Note this DOES NOT account for more than one occurrence of the sub
    List<SequenceHeaders> seqList;
//    private AcquisitionService acqServ=new AcquisitionServiceImpl();
    private TempArrayHolder headerholder=new TempArrayHolder();
    
    private DugioHeaderValuesExtractor dugHve=new DugioHeaderValuesExtractor();
    
    //for db
    private ArrayList<Headers> dbHeaders=new ArrayList<>();
    
    //for db Transactions
    final private static HeadersService hdrServ=new HeadersServiceImpl();
    final private static VolumeService volServ=new VolumeServiceImpl();
    final private static SequenceService seqserv=new SequenceServiceImpl();
    final private static SubsurfaceService subserv=new SubsurfaceServiceImpl();
    final private static AcquisitionServiceImpl acserv=new AcquisitionServiceImpl();
    
    private Volume dbVolume;
    private String logLocation;
    //LogWatcher logForSub;
    final private static LogsService lserv=new LogsServiceImpl();

    public HeaderCollector() {
        
        //LogManager.getLogManager().reset();
        logger.addHandler(obpManagerLogDatabaseHandler);
        logger.setLevel(Level.SEVERE);
        
    }
    
    
    
    
    
     public void setFeVolumeSelModel(AcquisitionVolumeModel vmod) {
         try{
       this.feVolumeSelModel=vmod;
       this.headersModel=feVolumeSelModel.getHeadersModel();
        dbVolume=volServ.getVolume(feVolumeSelModel.getId());
        if(dbVolume==null){
           System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): dbVol is null");
            DialogModel dm=new DialogModel();
                            String message="Please save the session before attempting to extract headers ";
                            dm.setMessage(message);
                            DialogNode dn=new DialogNode(dm);
                            return;
       }
         System.out.println("collector.HeaderCollector.setFeVolumeSelModel: Set the volume Sel model "+feVolumeSelModel.getLabel());
         logger.info("Set the volume Sel model "+feVolumeSelModel.getLabel());
        System.out.println("collector.HeaderCollector.setFeVolumeSelModel: volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        logger.info("volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
                
       
        ExecutorService executorService = Executors.newFixedThreadPool(2);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               calculateAndCommitHeaders();
            //logForSub.commitToDb();
            return null;
            }
            
        });
         }catch(Exception ex){
             logger.severe(ex.getMessage());
         }
    }
    
    
    
    public void setFeVolumeSelModel(VolumeSelectionModelType1 feVolumeSelModel) {
        try{
        this.feVolumeSelModel = feVolumeSelModel;
        this.headersModel=this.feVolumeSelModel.getHeadersModel();
        dbVolume = volServ.getVolume(feVolumeSelModel.getId());                                 //retrieve the correct dbVolume from the db. This would mean that the dbVolume table needs to exist before Headers are retrieved
        if(dbVolume==null){
           System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): dbVol is null");
            DialogModel dm=new DialogModel();
                            String message="Please save the session before attempting to extract headers ";
                            dm.setMessage(message);
                            DialogNode dn=new DialogNode(dm);
                            return;
       }
        Long type=this.feVolumeSelModel.getType();
        System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): Volume of Type "+type+" found");
       
            logger.info("Volume of Type "+type+" found");
        
        System.out.println("HeaderColl: Set the volume Sel model "+feVolumeSelModel.getLabel());
        System.out.println("HeaderColl: volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        
        logger.info("volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        logLocation=dbVolume.getPathOfVolume()+"/../../000scratch/logs";
                
        System.out.println("collector.HeaderCollector: looking for logs in "+logLocation);
        logger.info("looking for logs in "+logLocation);
        
        
        
        
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               calculateAndCommitHeaders();
            //logForSub.commitToDb();
            return null;
            }
            
        });
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }         
        
    }
    
    
    public void setFeVolumeSelModel(VolumeSelectionModelType2 model) {
        
        try{
         this.feVolumeSelModel = model;
        this.headersModel=this.feVolumeSelModel.getHeadersModel();
        dbVolume = volServ.getVolume(model.getId());                                 //retrieve the correct dbVolume from the db. This would mean that the dbVolume table needs to exist before Headers are retrieved
       if(dbVolume==null){
           System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): dbVol is null");
            DialogModel dm=new DialogModel();
                            String message="Please save the session before attempting to extract headers ";
                            dm.setMessage(message);
                            DialogNode dn=new DialogNode(dm);
                            return;
       }
        
        Long type=this.feVolumeSelModel.getType();
        System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): Volume of Type "+type+" found");
       logger.info("Volume of Type "+type+" found");
            
        
        
                
        System.out.println("collector.HeaderCollector: skipping logs!");
        logger.info(" skipping logs!");
        
        
        
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               calculateAndCommitHeaders();
            //logForSub.commitToDb();
            return null;
            }
            
        });
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }
    
    
    public void setFeVolumeSelModel(VolumeSelectionModelType4 vmod) {
        try{
       this.feVolumeSelModel=vmod;
       this.headersModel=feVolumeSelModel.getHeadersModel();
        dbVolume=volServ.getVolume(feVolumeSelModel.getId());
        if(dbVolume==null){
           System.out.println("collector.HeaderCollector.setFeVolumeSelModel(): dbVol is null");
            DialogModel dm=new DialogModel();
                            String message="Please save the session before attempting to extract headers ";
                            dm.setMessage(message);
                            DialogNode dn=new DialogNode(dm);
                            return;
       }
         System.out.println("collector.HeaderCollector.setFeVolumeSelModel: Set the volume Sel model "+feVolumeSelModel.getLabel());
        System.out.println("collector.HeaderCollector.setFeVolumeSelModel: volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
        logger.info("volume retrieved from db id:  "+dbVolume.getIdVolume()+ " name: "+dbVolume.getNameVolume());
       
                
       
        ExecutorService executorService = Executors.newFixedThreadPool(2);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               calculateAndCommitHeaders();
            //logForSub.commitToDb();
            return null;
            }
            
        });
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }
    
    private void calculateAndCommitHeaders(){
                        

        
            try{
            
           
               System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): started");
           
            
            
            
            File volume=feVolumeSelModel.getVolumeChosen();
            System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): started");
            logger.info("started");
            final Long volumeType=feVolumeSelModel.getVolumeType();
            dugHve.setVolume(volume);
         //   logForSub=new LogWatcher(logLocation,"", feVolumeSelModel, Boolean.TRUE);
       //     while(true)
      //  {
      System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): before try loop");
            
        try {
           
            List<Headers> existingHeaders=null;
            //Map<String,Headers> subsurfaceHeaderMap=new HashMap<>();
            Map<Sub,Headers> subsurfaceHeaderMap=new HashMap<>();
            Map<Seq,Headers> seqHeaderMap=new HashMap<>();
            
            System.out.println("collector.HeaderCollector: calculating headers for "+volume.getAbsolutePath());
            logger.info("calculating headers for "+volume.getAbsolutePath());
            if(dbVolume.getHeaderExtracted()){
                System.out.println("collector.HeaderCollector: Headers have been extracted for Volume: "+dbVolume.getNameVolume());
                logger.info("Headers have been extracted for Volume: "+dbVolume.getNameVolume());
                existingHeaders=hdrServ.getHeadersFor(dbVolume);
                for(Headers h:existingHeaders){
                    Seq seq=new Seq();
                    seq.setSeqno(h.getSequence().getSequenceno());
                    Sub sub=new Sub();
                    sub.setSeq(seq);
                    sub.setSubsurfaceName(h.getSubsurface().getSubsurface());
                    subsurfaceHeaderMap.put(sub, h);
                    seqHeaderMap.put(seq,h);
                } 
                
                
                
            }
             ArrayList<Headers> headerList=new ArrayList<>();
            ExecutorService exec=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            /* final Map<String,Headers> finalsubMap=subsurfaceHeaderMap;
            final List<Headers> finalExistingHeaders=existingHeaders;*/
            
            
             final Map<Sub,Headers> finalsubMap=subsurfaceHeaderMap;
            final List<Headers> finalExistingHeaders=existingHeaders;
             System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): running on Thread: "+Thread.currentThread().getName()+" forking");
          //   logger.info("running on Thread: "+Thread.currentThread().getName()+" forking");
                       
                       ExecutorService exec1=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                       exec1.submit(new Callable<ArrayList<Headers>>(){
                           @Override
                           public ArrayList<Headers> call() throws Exception {
                               System.out.println("collector.HeaderCollector.calculateAndCommitHeaders() about to call dugHve.calculateHeaders(): running on Thread: "+Thread.currentThread().getName()+" forking");
                              // logger.info("about to call dugHve.calculateHeaders(): running on Thread: "+Thread.currentThread().getName()+" forking");
                               headerList.addAll(dugHve.calculatedHeaders(finalsubMap, finalExistingHeaders,volumeType));
                               return headerList;
                           }
                       }).get();
                       
                   
            System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): running on Thread: "+Thread.currentThread().getName()+" joining");
            //logger.info("running on Thread: "+Thread.currentThread().getName()+" joining");
            if(finalExistingHeaders!=null){
                headerList.addAll(finalExistingHeaders);                                                         //append any old headers
            }
            if (headerList.isEmpty()){
                System.out.println("collector.HeaderCollector: headerList is empty");
                logger.info("headerList is empty");
               // return null;                     // the while loop will break when there are no more headers to process.
            }
            else
                System.out.println("collector.HeaderCollector: headerList is NOT empty : size: "+headerList.size()); 
            logger.info("headerList is NOT empty : size: "+headerList.size());
            /*sl=new ArrayList<>();*/
            sl=new HashSet<>();
           seqList=new ArrayList<>();
            MultiMap<Long,SubSurfaceHeaders> seqSubMap=new MultiValueMap<>();                                             //for creating association between SequenceHeaders and Subsurfaces
            int aci=0;
            
           
                    
           // Map<String,String> subInsightVersionFromLogMap=logForSub.getsubInsightVersionMap();
            System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): committing headers to the database");
            logger.info("committing headers to the database");
            for (Iterator<Headers> iterator = headerList.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                next.setVolume(dbVolume);
                
                if(volumeType.equals(1L)){
                
                String lineN=next.getSubsurface().getSubsurface();
                List<Logs> logs=lserv.getLogsFor(dbVolume, lineN);
                Logs latestLog=lserv.getLatestLogFor(dbVolume, lineN);
                Boolean errored=latestLog.getErrored();
                Boolean running=latestLog.getRunning();
                Boolean cancelled=latestLog.getCancelled();
                Boolean success=latestLog.getCompletedsuccessfully();
                
                
             
                Long wfMaxVersion=0L;
                if(latestLog!=null){
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): LatestLog for line: "+lineN+" is: "+latestLog.getLogpath()+" created at: "+latestLog.getTimestamp()+" with insight version: "+latestLog.getInsightVersion());
                    logger.info("LatestLog for line: "+lineN+" is: "+latestLog.getLogpath()+" created at: "+latestLog.getTimestamp()+" with insight version: "+latestLog.getInsightVersion());
                    next.setInsightVersion(latestLog.getInsightVersion());
                    wfMaxVersion=latestLog.getWorkflow().getWfversion();
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): Workflow from the latest log is: "+wfMaxVersion);
                    logger.info("Workflow from the latest log is: "+wfMaxVersion);
                }else{
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): I couldn't find the latest log entry for "+dbVolume.getNameVolume()+" : "+lineN);
                    logger.info("I couldn't find the latest log entry for "+dbVolume.getNameVolume()+" : "+lineN);
                    next.setInsightVersion(new String("no logs found"));
                    
                }
                    //String latestInsightVersion=lserv.getInsightVersionFromLatestLog();
                    
                    if(logs!=null){
                        next.setNumberOfRuns(new Long(logs.size()));
                    }else{
                        next.setNumberOfRuns(-1L);
                    }
                
                /* for (Iterator<Logs> iterator1 = logs.iterator(); iterator1.hasNext();) {
                Logs logsForLineN = iterator1.next();
                Workflow wf=logsForLineN.getWorkflow();
                Long version=wf.getWfversion();
                if(version>wfMaxVersion){
                wfMaxVersion=version;
                }
                
                }*/
                next.setWorkflowVersion(wfMaxVersion);
                
                /// Code up a method to set id of headers based on the hash generated from fields (subsurface,tracecount,.....) of the headers. 
                if(next.getModified()) 
                {
                    System.out.println(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be updated!");
                    logger.info(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be updated!");
                    
                    hdrServ.updateHeaders(next.getIdHeaders(), next);       
                }
                else{
                    logger.info(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be created!");
                hdrServ.createHeaders(next);                             //commit to the db
                }
                
                
                for (Iterator<Logs> iterator1 = logs.iterator(); iterator1.hasNext();) {
                    Logs next1 = iterator1.next();
                    next1.setHeaders(next);
                    lserv.updateLogs(next1.getIdLogs(), next1);
                }
            }
                
                
                
                /*
                    For volume type 2: SEGD Load 
                    Start
                */
                if(volumeType.equals(2L)){
                
                String lineN=next.getSubsurface().getSubsurface();
                
                
                
             
                Long wfMaxVersion=-1L;
                next.setNumberOfRuns(-1L);
               
                next.setWorkflowVersion(wfMaxVersion);
                
                /// Code up a method to set id of headers based on the hash generated from fields (subsurface,tracecount,.....) of the headers. 
                if(next.getModified()) 
                {
                    System.out.println(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be updated!");
                    logger.info(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be updated!");
                    hdrServ.updateHeaders(next.getIdHeaders(), next);       
                }
                else{
                    logger.info(next.getSubsurface()+ " : with id : "+next.getIdHeaders()+" : is about to be created!");
                hdrServ.createHeaders(next);                             //commit to the db
                }
                
                
               
            }
                
                 /*
                    For volume type 2: SEGD Load 
                    End
                */
                
                
                
                
                 /*
                    For volume type 3: Acquisition Node 
                    Start
                */
                
                
                
                
                if(volumeType.equals(3L)){
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): Checking to see if headers exist for acquisition volume type: "+dbVolume.getNameVolume());
                    logger.info("Checking to see if headers exist for acquisition volume type: "+dbVolume.getNameVolume());
                    List<Headers> lhdr=hdrServ.getHeadersFor(dbVolume,next.getSubsurface());
                    next.setWorkflowVersion(-100L);
                    next.setInsightVersion("0.0");
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders() size of headers for Acqvol: "+lhdr.size());
                    if(lhdr.size()==0 ){
                        System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): creating headers for "+next.getSubsurface().getSubsurface());
                        logger.info("creating headers for "+next.getSubsurface().getSubsurface());
                        hdrServ.createHeaders(next);
                    }
                }
                
                 /*
                    For volume type 3: Acquisition Node 
                    End
                */
                 
                  /*
                    For volume type 4: Text Node 
                    Start
                 
                */
                  if(volumeType.equals(4L)){
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): Checking to see if headers exist for Text type : "+dbVolume.getNameVolume());
                    logger.info("Checking to see if headers exist for Text type : "+dbVolume.getNameVolume());
                    List<Headers> lhdr=hdrServ.getHeadersFor(dbVolume,next.getSubsurface());
                    next.setWorkflowVersion(-100L);
                    next.setInsightVersion("0.0");
                    System.out.println("collector.HeaderCollector.calculateAndCommitHeaders() size of headers for TextVolume : "+lhdr.size());
                    if(lhdr.size()==0 ){
                        System.out.println("collector.HeaderCollector.calculateAndCommitHeaders(): creating headers for "+next.getSubsurface().getSubsurface());
                        logger.info("creating headers for "+next.getSubsurface().getSubsurface());
                        hdrServ.createHeaders(next);
                    }
                }
                  
                   /*
                    For volume type 4: Text Node 
                    End
                */
                SubSurfaceHeaders s= new SubSurfaceHeaders();
          
                s.setSequenceNumber(next.getSequence().getSequenceno());
                
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
                
                s.setSubsurface(next.getSubsurface().getSubsurface());
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
                s.setNumberOfRuns(next.getNumberOfRuns());
                s.setInsightVersion(next.getInsightVersion());
                s.setWorkflowVersion(next.getWorkflowVersion());
          
                seqSubMap.put(s.getSequenceNumber(), s);
                
                
                 sl.add(s);
                
                
                
                
                
            }
            
            
            Set<Long> seqNos=seqSubMap.keySet();
      
      
      for (Iterator<Long> iterator = seqNos.iterator(); iterator.hasNext();) {
          Long next = iterator.next();
          SequenceHeaders sq=new SequenceHeaders();
          ArrayList<SubSurfaceHeaders> ssubs=(ArrayList<SubSurfaceHeaders>) seqSubMap.get(next);
          sq.setSubsurfaces(ssubs);
          sq.setSequenceNumber(ssubs.get(0).getSequenceNumber());
          sq.setSubsurface(ssubs.get(0).getSubsurface().split("_")[0]);
          
          seqList.add(sq);
      }
            
//      LogWatcher logForSub=new LogWatcher(logLocation,"", feVolumeSelModel, Boolean.TRUE);
     feVolumeSelModel.setSubsurfaces(sl);
       //feVolumeSelModel.setSeqSubsMap(seqSubMap);
       
      ObservableList<SequenceHeaders> obseq=FXCollections.observableArrayList(seqList);
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
            }catch(Exception ex){
              //ex.printStackTrace();
              if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
                    
            }
    }

  public List<SequenceHeaders> getHeaderListForVolume(VolumeSelectionModelType0 vm){
      try{
      Long type=vm.getType();
      if(type.equals(1L) || type.equals(2L)){
      headersModel=vm.getHeadersModel();
      return headersModel.getSequenceListInHeaders();                                                //the observable List is the list of sequences. which contains all the header information
      }
      else{
          System.out.println("collector.HeaderCollector.getHeaderListForVolume(): not implemented for volume type: "+type);
          logger.info(" Throwing  not implemented for volume type: "+type);
           throw new UnsupportedOperationException("collector.HeaderCollector.getHeaderListForVolume(): Implementation pending for volume type: "+type); 
         
      }
      }
      catch(Exception ex){
          if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
          throw ex; 
      }
  }

    

   

    
    
    
    
}
