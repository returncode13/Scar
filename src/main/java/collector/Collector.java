/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import core.Seq;
import core.Sub;
import db.handler.ObpManagerLogDatabaseHandler;
import db.model.Acquisition;
import db.model.Ancestors;
import db.model.Child;
import db.model.Descendants;
import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import db.model.NodeType;
import db.model.OrcaView;
import db.model.Parent;
import db.model.PropertyType;
import db.model.QcMatrix;
import db.model.QcTable;
import db.model.Sequence;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Subsurface;
import db.model.Volume;
import db.services.AcquisitionService;
import db.services.AcquisitionServiceImpl;

import db.services.AncestorsService;
import db.services.AncestorsServiceImpl;
import db.services.ChildService;
import db.services.ChildServiceImpl;
import db.services.DescendantsService;
import db.services.DescendantsServiceImpl;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.NodePropertyService;
import db.services.NodePropertyServiceImpl;
import db.services.NodePropertyValueService;
import db.services.NodePropertyValueServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.OrcaViewService;
import db.services.OrcaViewServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.PropertyTypeService;
import db.services.PropertyTypeServiceImpl;
import db.services.QcMatrixService;
import db.services.QcMatrixServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SequenceService;
import db.services.SequenceServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionModel;
import fend.session.dialogs.DialogModel;
import fend.session.dialogs.DialogNode;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.nodeproperty.JobModelProperty;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.jobs.types.type4.JobStepType4Model;
import fend.session.node.volumes.acquisition.AcqHeaders;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.qcTable.QcTableModel;
import fend.session.node.qcTable.QcTableSequences;
import fend.session.node.qcTable.QcTableSubsurfaces;
import fend.session.node.qcTable.QcTypeModel;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import fend.session.node.volumes.type4.VolumeSelectionModelType4;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
import landing.AppProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 *
 * @author sharath nair
 */
public class Collector {
    //from frontEnd. user
    Logger logger=Logger.getLogger(Collector.class.getName());
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    
    private SessionModel feSessionModel;
    //private ArrayList<JobStepModel> feJobModel=new ArrayList<>();
    ObservableList<JobStepType0Model> feJobModel=FXCollections.observableArrayList();
   // private ArrayList<VolumeSelectionModelType1> feVolume=new ArrayList<>();
    private ArrayList<VolumeSelectionModelType0> feVolume=new ArrayList<>();   
    
    
    //for db////
    private ArrayList<JobStep> dbJobSteps=new ArrayList<>();
    private ArrayList<Volume> dbVolumes  =new ArrayList<>();
    private ArrayList<Sessions> dbSessions=new ArrayList<>();
    private ArrayList<SessionDetails> dbSessionDetails=new ArrayList<>();
    private ArrayList<JobVolumeDetails> dbJobVolumeDetails=new ArrayList<>();
    private ArrayList<Parent> dbParent=new ArrayList<>();
    private ArrayList<Child> dbChild = new ArrayList<>();
   // private ArrayList<Ancestors> dbAncestors=new ArrayList<>();
    private ArrayList<Descendants> dbDescendants = new ArrayList<>();
    private ArrayList<NodePropertyValue> dbNodePropertyValues=new ArrayList<>();
    
     private Sessions currentSession;
    
    
    //for db Transactions
    final private static JobStepService jsServ=new JobStepServiceImpl();
    final private static SessionsService sesServ = new SessionsServiceImpl();
      
    final private static SessionDetailsService ssdServ=new SessionDetailsServiceImpl();
    final private static AncestorsService ancServ= new AncestorsServiceImpl();
    final private static DescendantsService descServ=new DescendantsServiceImpl();
    
    final private static VolumeService volServ=new VolumeServiceImpl();
    final private static JobVolumeDetailsService jvdServ=new JobVolumeDetailsServiceImpl();
    
    final private static ParentService pServ=new ParentServiceImpl();
    final private static ChildService cServ=new ChildServiceImpl();
  //  final private static AcquisitionService acqServ=new AcquisitionServiceImpl();
    final private static OrcaViewService oserv=new OrcaViewServiceImpl();
    final private static SequenceService seqserv=new SequenceServiceImpl();
    final private static SubsurfaceService subserv=new SubsurfaceServiceImpl();
    final private static AcquisitionService acqserv=new AcquisitionServiceImpl();
    final private HeadersService hserv=new HeadersServiceImpl();
    final private QcMatrixService qcmatserv=new QcMatrixServiceImpl();
    final private QcTableService qctabServ=new QcTableServiceImpl();
    final private NodeTypeService nserv=new NodeTypeServiceImpl();
    final private PropertyTypeService proServ=new PropertyTypeServiceImpl();
    final private NodePropertyService npserv=new NodePropertyServiceImpl();
    final private NodePropertyValueService npvserv=new NodePropertyValueServiceImpl();
    
    public Collector(){
       // dbSessions.add(new Sessions("+twoSessions", "gamma123"));                               //fixing on one session for the presentation
      // obpManagerLogDatabaseHandler.clear();                    //remove existing logs before starting to log   ---moved to the main class (Landing.class)
     // LogManager.getLogManager().reset(); 
      logger.addHandler(obpManagerLogDatabaseHandler);
       logger.setLevel(Level.SEVERE);
    }

    public void setFeJobGraphModel(SessionModel feJobGraphModel) {
        try{
        this.feSessionModel = feJobGraphModel;
        System.out.println("Collector: Set the graphModel ");
        logger.info("set the graphModel");
        setupEntries();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }


    
     public void saveCurrentSession(SessionModel smod) {
        try{
         feSessionModel=smod;
         
         Sessions cs=sesServ.getSessions(feSessionModel.getId());
         if(cs==null){
             
             System.out.println("collector.Collector.saveCurrentSession(): No existing entry for current session found with id: "+feSessionModel.getId());
             logger.info("No existing entry for current session found with id: "+ feSessionModel.getId()+"  so creating a new Sessions object");
             
             currentSession=new Sessions();
        currentSession.setIdSessions(feSessionModel.getId());
        currentSession.setNameSessions(feSessionModel.getName());
         }else{
             System.out.println("collector.Collector.saveCurrentSession(): Found an entry for existing session with id: "+feSessionModel.getId()+" setting it to the current Session");
             logger.info("Found an entry for existing session with id: "+feSessionModel.getId()+" setting it to the current Session");
             currentSession=cs;
         }
        
        
     
        
        feJobModel=feSessionModel.getListOfJobs();
        
         for (Iterator<JobStepType0Model> iterator = feJobModel.iterator(); iterator.hasNext();) {
             JobStepType0Model next = iterator.next();
             System.out.println("collector.Collector.saveCurrentSession(): List of Jobs in session: "+next.getJobStepText()+" :ID: "+next.getId());
             //logger.log(Level.INFO, "List of Jobs in session: {0} :ID: {1}", new Object[]{next.getJobStepText(), next.getId()});
             logger.info("List of Jobs in session: "+next.getJobStepText()+" :ID: "+next.getId());
             
             List<VolumeSelectionModelType0> vl=next.getVolList();
             if(vl.isEmpty()){
                 System.out.println("collector.Collector.saveCurrentSession(): vl is empty for job "+next.getJobStepText());
                 DialogModel dm=new DialogModel();
                 String message="Please add a volume to the node "+next.getJobStepText()+" before attempting to save the session ";
                 dm.setMessage(message);
                 DialogNode dn=new DialogNode(dm);
                 return;
             }
             if(!vl.isEmpty()){
                 for (Iterator<VolumeSelectionModelType0> iterator1 = vl.iterator(); iterator1.hasNext();) {
                     VolumeSelectionModelType0 next1 = iterator1.next();
                     if(next1.getVolumeChosen()==null){
                            DialogModel dm=new DialogModel();
                            String message="Please add a volume to the node "+next.getJobStepText()+" before attempting to save the session ";
                            dm.setMessage(message);
                            DialogNode dn=new DialogNode(dm);
                            return;
                     }
                 }
                 
             }
             InsightVersionsModel inm=next.getInsightVersionsModel();
             if(inm==null){
                 System.out.println("collector.Collector.saveCurrentSession() inm is null");
                 DialogModel dm=new DialogModel();
                 String message="Please associate an insight version with the node "+next.getJobStepText()+" before attempting to save the session";
                 dm.setMessage(message);
                 DialogNode dn =new DialogNode(dm);
                 return;
             }
             List<String> ins=next.getInsightVersionsModel().getCheckedVersions();
             if(ins==null||ins.isEmpty()){
                 System.out.println("collector.Collector.saveCurrentSession(): ins is empty for job: "+next.getJobStepText());
                 DialogModel dm=new DialogModel();
                 String message="Please associate an insight version with the node "+next.getJobStepText()+" before attempting to save the session";
                 dm.setMessage(message);
                 DialogNode dn =new DialogNode(dm);
                 return;
             }
         }
        dbSessions.add(currentSession);
        
        
        
        
        
        
        
       // if(sesServ.getSessions(currentSession.getIdSessions())==null)dbSessions.add(currentSession);
        setupEntries();
        }catch(Exception ex){
           if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
        }
    }
    
    /*
    Set up the data base entry datastructures
    */
    
    
    //The code for setting up the sessions datastructures go here   <=== DONT FORGET.
    
    private void setupEntries(){
       DateTimeFormatter formatter=DateTimeFormat.forPattern("dd-MMM-yyyy HH:mm:ss");
        //DateTime summaryT=formatter.parseDateTime("01-Jan-1970 01:01:01").toString(AppProperties.TIMESTAMP_FORMAT);
        DateTime summaryT=DateTime.parse("01-Jan-1970 01:01:01",DateTimeFormat.forPattern("dd-MMM-yyyy HH:mm:ss"));
        String summaryTime=summaryT.toString(AppProperties.TIMESTAMP_FORMAT);
        try{
        dbSessionDetails.clear();
        dbJobSteps.clear();                         //clear previous jobmodel array. set current entries here
       dbVolumes.clear();
        dbJobVolumeDetails.clear();
        dbNodePropertyValues.clear();
        
        //for every session
       // for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();) {
           Sessions sess = currentSession;//iterator.next();
            
           //for each jobStep from fe
            for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                
                JobStepType0Model jsm=jit.next();
                NodeType ntype=nserv.getNodeTypeObjForType(jsm.getType());
                List<JobModelProperty> jobPropertiesFe=jsm.getJobProperties();
                
                JobStep jjb=jsServ.getJobStep(jsm.getId());
                if(jjb!=null){              //job has been previously saved in the db
                    
                List<NodePropertyValue> nodePropertyValues=npvserv.getNodePropertyValuesFor(jjb);
                   
                    
                for (Iterator<JobModelProperty> iterator = jobPropertiesFe.iterator(); iterator.hasNext();) {
                    JobModelProperty jmp = iterator.next();
                    
                     for (Iterator<NodePropertyValue> npviterator = nodePropertyValues.iterator(); npviterator.hasNext();) {
                        NodePropertyValue npv = npviterator.next();
                        if(npv.getNodeProperty().getPropertyType().getName().equals(jmp.getPropertyName())){
                                                     System.out.println("collector.Collector.setupEntries(): Before updating found nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());

                            npv.setValue(jmp.getPropertyValue());
                            System.out.println("collector.Collector.setupEntries(): After updating found nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                        npvserv.updateNodePropertyValue(npv.getIdNodePropertyValue(), npv);
                        }
                         

                        
                    }
                }
                
               
                
                
            }
                
                
                 /*
                for jobtype=1 . 2DProcess
                Start
                */
                if(jsm.getType().equals(1L)){
                    JobStepType1Model j1=(JobStepType1Model) jsm;
                    
                    //this block should exit if this job and session arent saved to begin with..
                    JobStep job=jsServ.getJobStep(j1.getId());
                    if(job!=null){
                        
                //    }
                    
                    QcTableModel qctabmod=j1.getQcTableModel();
                    List<QcTableSequences> qctabSeqList=qctabmod.getQcTableSequences();
                        System.out.println("collector.Collector.setupEntries(): Size of qcTabSeqList :  "+qctabSeqList.size());
                    for (Iterator<QcTableSequences> iterator = qctabSeqList.iterator(); iterator.hasNext();) {
                        QcTableSequences qcseq = iterator.next();
                        System.out.println("collector.Collector.setupEntries(): Inside QSeq: "+qcseq.getSequence().getSequenceNumber());
                        List<QcTableSubsurfaces> qcsubList=qcseq.getQcSubs();
                        for (Iterator<QcTableSubsurfaces> iterator1 = qcsubList.iterator(); iterator1.hasNext();) {
                            QcTableSubsurfaces qcsub = iterator1.next();
                            System.out.println("QSub: "+qcsub.getSub().getSubsurface());
                            SubSurfaceHeaders subh=qcsub.getSub();
                            subh.resetPassQC();
                            
                            Subsurface subobj=subserv.getSubsurfaceObjBysubsurfacename(subh.getSubsurface());
                            Headers hdr=null;
                            Volume vol=null;
                            
                            List<VolumeSelectionModelType1> volList=j1.getVolList();
                            for (Iterator<VolumeSelectionModelType1> iterator2 = volList.iterator(); iterator2.hasNext();) {
                                VolumeSelectionModelType1 volm = iterator2.next();
                                vol=volServ.getVolume(volm.getId());
                                List<Headers> hlist=hserv.getHeadersFor(vol, subobj);
                                if(hlist.isEmpty()){
                                    
                                }else if(hlist.size()==1){
                                    hdr=hlist.get(0);
                                    break;
                                }else if(hlist.size()>1){
                                    System.out.println("collector.Collector.setupEntries(): Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j1.getJobStepText()+" and volume: "+vol.getNameVolume());
                                   // logger.log(Level.WARNING, "Found multiple header entries for seq :{0} sub: {1} for job: {2} and volume: {3}", new Object[]{subh.getSequenceHeader().getSequenceNumber(), subh.getSubsurface(), j1.getJobStepText(), vol.getNameVolume()});
                                   logger.severe("Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j1.getJobStepText()+" and volume: "+vol.getNameVolume());
                                }
                                
                            }
                            SessionDetails sd=ssdServ.getSessionDetails(job, sess);
                            List<QcMatrix> qcmatrixList=qcmatserv.getQcMatrixForSessionDetails(sd,true);
                            for (Iterator<QcMatrix> iterator2 = qcmatrixList.iterator(); iterator2.hasNext();) {
                                QcMatrix qcmat = iterator2.next();
                                
                                QcTypeModel qctmod=null;    
                                List<QcTypeModel> qctmodList=qcsub.getQctypes();            //finding the value of the qctype for this particular subsurface
                                for (Iterator<QcTypeModel> iterator3 = qctmodList.iterator(); iterator3.hasNext();) {
                                    QcTypeModel qcty = iterator3.next();
                                    if(qcty.getId().equals(qcmat.getQctype().getIdQcType()) && qcty.getName().equals(qcmat.getQctype().getName())){
                                        qctmod=qcty;
                                    }
                                    
                                    
                                }
                                
                                


                                //check if an entry exists..if not create else update
                                QcTable qctEntry=qctabServ.getQcTableFor(qcmat, hdr);
                                if(qctEntry==null){
                                    QcTable qct=new QcTable();
                                    qct.setHeaders(hdr);
                                    qct.setQcmatrix(qcmat);
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                  //  qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    qct.setSummaryTime(summaryTime);   //set summary to 01-Jan-1970 01:01:01
                                    System.out.println("collector.Collector.setupEntries(): creating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j1.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+")  to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    qctabServ.createQcTable(qct);
                                    
                                }else {
                                    QcTable qct=qctEntry;
                                    //qct.setHeaders(hdr);
                                    Boolean oldval=qct.getResult();
                                    
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                    
                                    
                                    //qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    System.out.println("collector.Collector.setupEntries(): updating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j1.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    //logger.log(Level.INFO, "updating seq: {0} sub: {1} in job: {2} qctype: (id,name) : ({3},{4}) from: {5} to: {6}", new Object[]{subh.getSequenceHeader().getSequenceNumber(), subh.getSubsurface(), j1.getJobStepText(), qctmod.getId(), qctmod.getName(), oldval, qctmod.isPassQc()});
                                    logger.info(subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j1.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc());
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                       subh.qcStatus(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        subh.qcStatus(true);
                                    }else{
                                        subh.qcStatus(false);
                                    }
                                    
                                    
                                    //subh.qcStatus(qctmod.isPassQc());
                                   
                                    qctabServ.updateQcTable(qct.getIdQcTable(), qct);
                                }
                                
                                
                                
                                
                            }
                            
                            
                            
                        }
                        
                     }
                  }
                }
                
                
                /*
                for jobtype=2 . SEGD LOAD
                Start
                */
                if(jsm.getType().equals(2L)){
                    JobStepType2Model j2=(JobStepType2Model) jsm;
                    
                    //this block should exit if this job and session arent saved to begin with..
                    JobStep job=jsServ.getJobStep(j2.getId());
                    if(job!=null){
                        
                //    }
                    
                    QcTableModel qctabmod=j2.getQcTableModel();
                    List<QcTableSequences> qctabSeqList=qctabmod.getQcTableSequences();
                    for (Iterator<QcTableSequences> iterator = qctabSeqList.iterator(); iterator.hasNext();) {
                        QcTableSequences qcseq = iterator.next();
                         System.out.println("collector.Collector.setupEntries(): Inside QSeq: "+qcseq.getSequence().getSequenceNumber());
                        List<QcTableSubsurfaces> qcsubList=qcseq.getQcSubs();
                        for (Iterator<QcTableSubsurfaces> iterator1 = qcsubList.iterator(); iterator1.hasNext();) {
                            QcTableSubsurfaces qcsub = iterator1.next();
                            System.out.println("QcSub: "+qcsub.getSub().getSubsurface());
                            SubSurfaceHeaders subh=qcsub.getSub();
                            subh.resetPassQC();
                            
                            Subsurface subobj=subserv.getSubsurfaceObjBysubsurfacename(subh.getSubsurface());
                            Headers hdr=null;
                            Volume vol=null;
                            
                            List<VolumeSelectionModelType2> volList=j2.getVolList();
                            for (Iterator<VolumeSelectionModelType2> iterator2 = volList.iterator(); iterator2.hasNext();) {
                                VolumeSelectionModelType2 volm = iterator2.next();
                                vol=volServ.getVolume(volm.getId());
                                List<Headers> hlist=hserv.getHeadersFor(vol, subobj);
                                if(hlist.isEmpty()){
                                    
                                }else if(hlist.size()==1){
                                    hdr=hlist.get(0);
                                    break;
                                }else if(hlist.size()>1){
                                    System.out.println("collector.Collector.setupEntries(): Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j2.getJobStepText()+" and volume: "+vol.getNameVolume());
                                    logger.warning("Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j2.getJobStepText()+" and volume: "+vol.getNameVolume());
                                }
                                
                            }
                            SessionDetails sd=ssdServ.getSessionDetails(job, sess);
                            List<QcMatrix> qcmatrixList=qcmatserv.getQcMatrixForSessionDetails(sd,true);
                            for (Iterator<QcMatrix> iterator2 = qcmatrixList.iterator(); iterator2.hasNext();) {
                                QcMatrix qcmat = iterator2.next();
                                System.out.println("QCMatrix: "+qcmat.getQctype().getName());
                                QcTypeModel qctmod=null;    
                                List<QcTypeModel> qctmodList=qcsub.getQctypes();            //finding the value of the qctype for this particular subsurface
                                for (Iterator<QcTypeModel> iterator3 = qctmodList.iterator(); iterator3.hasNext();) {
                                    QcTypeModel qcty = iterator3.next();
                                    if(qcty.getId().equals(qcmat.getQctype().getIdQcType()) && qcty.getName().equals(qcmat.getQctype().getName())){
                                        qctmod=qcty;
                                    }
                                    
                                    
                                }
                                
                                


                                //check if an entry exists..if not create else update
                                QcTable qctEntry=qctabServ.getQcTableFor(qcmat, hdr);
                                if(qctEntry==null){
                                    QcTable qct=new QcTable();
                                    qct.setHeaders(hdr);
                                    qct.setQcmatrix(qcmat);
                                    System.out.println("QctMod.IsPassQC= "+qctmod.isPassQc());
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                    
                                    
                                    //qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    qct.setSummaryTime(summaryTime);   //set summary to 01-Jan-1970 01:01:01
                                    System.out.println("collector.Collector.setupEntries(): creating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j2.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+")  to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    qctabServ.createQcTable(qct);
                                    
                                }else {
                                    QcTable qct=qctEntry;
                                    //qct.setHeaders(hdr);
                                    Boolean oldval=qct.getResult();
                                    System.out.println("QctMod.IsPassQC= "+qctmod.isPassQc());
                                     if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                    
                                    //qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    System.out.println("collector.Collector.setupEntries(): updating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j2.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    //logger.log(Level.INFO, "updating seq: {0} sub: {1} in job: {2} qctype: (id,name) : ({3},{4}) from: {5} to: {6}", new Object[]{subh.getSequenceHeader().getSequenceNumber(), subh.getSubsurface(), j1.getJobStepText(), qctmod.getId(), qctmod.getName(), oldval, qctmod.isPassQc()});
                                    logger.info(subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j2.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc());
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                       subh.qcStatus(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        subh.qcStatus(true);
                                    }else{
                                        subh.qcStatus(false);
                                    }        
                                    //subh.qcStatus(qctmod.isPassQc());
                                   
                                    qctabServ.updateQcTable(qct.getIdQcTable(), qct);
                                }
                                
                                
                                
                                
                            }
                            
                            
                            
                        }
                        
                     }
                  }
                }
                /*
                for jobtype=2. SEGD LOAD
                End
                */
                /*
                for jobtype=4. Text Nodes
                Start
                */
                if(jsm.getType().equals(4L)){
                    JobStepType4Model j4=(JobStepType4Model) jsm;
                    
                    //this block should exit if this job and session arent saved to begin with..
                    JobStep job=jsServ.getJobStep(j4.getId());
                    if(job!=null){
                        
                //    }
                    
                    QcTableModel qctabmod=j4.getQcTableModel();
                    List<QcTableSequences> qctabSeqList=qctabmod.getQcTableSequences();
                    for (Iterator<QcTableSequences> iterator = qctabSeqList.iterator(); iterator.hasNext();) {
                        QcTableSequences qcseq = iterator.next();
                        List<QcTableSubsurfaces> qcsubList=qcseq.getQcSubs();
                        for (Iterator<QcTableSubsurfaces> iterator1 = qcsubList.iterator(); iterator1.hasNext();) {
                            QcTableSubsurfaces qcsub = iterator1.next();
                            SubSurfaceHeaders subh=qcsub.getSub();
                            
                            Subsurface subobj=subserv.getSubsurfaceObjBysubsurfacename(subh.getSubsurface());
                            Headers hdr=null;
                            Volume vol=null;
                            
                            List<VolumeSelectionModelType4> volList=j4.getVolList();
                            for (Iterator<VolumeSelectionModelType4> iterator2 = volList.iterator(); iterator2.hasNext();) {
                                VolumeSelectionModelType4 volm = iterator2.next();
                                vol=volServ.getVolume(volm.getId());
                                List<Headers> hlist=hserv.getHeadersFor(vol, subobj);
                                if(hlist.isEmpty()){
                                    
                                }else if(hlist.size()==1){
                                    hdr=hlist.get(0);
                                    break;
                                }else if(hlist.size()>1){
                                    System.out.println("collector.Collector.setupEntries(): Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j4.getJobStepText()+" and volume: "+vol.getNameVolume());
                                    logger.warning("Found multiple header entries for seq :"+subh.getSequenceHeader().getSequenceNumber() +" sub: "+subh.getSubsurface()+" for job: "+j4.getJobStepText()+" and volume: "+vol.getNameVolume());
                                }
                                
                            }
                            SessionDetails sd=ssdServ.getSessionDetails(job, sess);
                            List<QcMatrix> qcmatrixList=qcmatserv.getQcMatrixForSessionDetails(sd,true);
                            for (Iterator<QcMatrix> iterator2 = qcmatrixList.iterator(); iterator2.hasNext();) {
                                QcMatrix qcmat = iterator2.next();
                                
                                QcTypeModel qctmod=null;    
                                List<QcTypeModel> qctmodList=qcsub.getQctypes();            //finding the value of the qctype for this particular subsurface
                                for (Iterator<QcTypeModel> iterator3 = qctmodList.iterator(); iterator3.hasNext();) {
                                    QcTypeModel qcty = iterator3.next();
                                    if(qcty.getId().equals(qcmat.getQctype().getIdQcType()) && qcty.getName().equals(qcmat.getQctype().getName())){
                                        qctmod=qcty;
                                    }
                                    
                                    
                                }
                                
                                


                                //check if an entry exists..if not create else update
                                QcTable qctEntry=qctabServ.getQcTableFor(qcmat, hdr);
                                if(qctEntry==null){
                                    QcTable qct=new QcTable();
                                    qct.setHeaders(hdr);
                                    qct.setQcmatrix(qcmat);
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                    //qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    qct.setSummaryTime(summaryTime);   //set summary to 01-Jan-1970 01:01:01 . saved as 19700101010101
                                    System.out.println("collector.Collector.setupEntries(): creating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j4.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+")  to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    qctabServ.createQcTable(qct);
                                    
                                }else {
                                    QcTable qct=qctEntry;
                                    //qct.setHeaders(hdr);
                                    Boolean oldval=qct.getResult();
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                        qct.setResult(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        qct.setResult(true);
                                    }else{
                                        qct.setResult(false);
                                    }
                                    
                                   //qct.setResult(qctmod.isPassQc());
                                    qct.setUpdateTime(qcsub.getUpdateTime());      //stored as a string  yyyyMMddHHmmss
                                    System.out.println("collector.Collector.setupEntries(): updating seq: "+subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j4.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc()+" with Uptime: "+qct.getUpdateTime());
                                    //logger.log(Level.INFO, "updating seq: {0} sub: {1} in job: {2} qctype: (id,name) : ({3},{4}) from: {5} to: {6}", new Object[]{subh.getSequenceHeader().getSequenceNumber(), subh.getSubsurface(), j1.getJobStepText(), qctmod.getId(), qctmod.getName(), oldval, qctmod.isPassQc()});
                                    logger.info(subh.getSequenceHeader().getSequenceNumber()+" sub: "+subh.getSubsurface()+" in job: "+j4.getJobStepText()+" qctype: (id,name) : ("+qctmod.getId()+","+qctmod.getName()+") from: "+oldval+" to: "+qctmod.isPassQc());
                                    if(qctmod.isPassQc().equals(QcTypeModel.isInDeterminate)){
                                       subh.qcStatus(null);
                                    }else if(qctmod.isPassQc().equals(Boolean.TRUE.toString())){
                                        subh.qcStatus(true);
                                    }else{
                                        subh.qcStatus(false);
                                    }   
                                    //subh.qcStatus(qctmod.isPassQc());
                                   
                                    qctabServ.updateQcTable(qct.getIdQcTable(), qct);
                                }
                                
                                
                                
                                
                            }
                            
                            
                            
                        }
                        
                     }
                  }
                    
                    
                    
                    //save job properties in the table nodepropertyvalue;
                }
                /*
                for jobtype=4. Text Node
                End
                */
                
                JobStep jobstep;
                /*if((jobstep=jsServ.getJobStep(jsm.getId()))==null){
                jobstep=new JobStep();
                jobstep.setNameJobStep(jsm.getJobStepText());
                jobstep.setAlert(Boolean.FALSE);
                }*/
                //else{
                    jobstep=new JobStep();
                    jobstep.setNameJobStep(jsm.getJobStepText());
                    jobstep.setIdJobStep(jsm.getId());

                    jobstep.setAlert(Boolean.FALSE);
                   
                    //jobstep.setType(jsm.getType());
                    jobstep.setType(ntype);
                    //jsServ.updateJobStep(jobstep.getIdJobStep(), jobstep);
                    if(jsServ.getJobStep(jsm.getId())==null){       //first time saving this job
                        for (Iterator<JobModelProperty> iterator = jobPropertiesFe.iterator(); iterator.hasNext();) {
                    JobModelProperty jmp = iterator.next();
                    
                    NodePropertyValue npv=new NodePropertyValue();
                    npv.setJobStep(jobstep);
                    PropertyType pro=proServ.getPropertyTypeObjForName(jmp.getPropertyName());
                    NodeProperty nprop=npserv.getNodeProperty(ntype, pro);
                    npv.setNodeProperty(nprop);
                    npv.setValue(jmp.getPropertyValue());
                    System.out.println("collector.Collector.setupEntries(): Adding new entries nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                    logger.info("Adding new entries nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                    dbNodePropertyValues.add(npv);
                    
                        }
                    }
                    
                    
                    
                    
               // }
                //JobStepModel jsm = jit.next();
                /*JobStep jobStep=new JobStep();
                jobStep.setNameJobStep(jsm.getJobStepText());
                jobStep.setIdJobStep(jsm.getId());
                //System.out.println("Coll: JSM ID: "+jsm.getId());
                jobStep.setAlert(Boolean.FALSE);*/
                 /*jobStep.setPending(Boolean.);*/
                 List<String> insightVers=jsm.getInsightVersionsModel().getCheckedVersions();
                 /*
                 if(!jsm.getType().equals(3L)){                     //if not acquisition node
                 insightVers=jsm.getInsightVersionsModel().getCheckedVersions();
                 }else{
                 insightVers=new ArrayList<>();
                 }*/
                 
                 String versionString="";                                                              //this string will be of form v1;v2;v3;.. where v1,v2.. are the chosen versions
                 for (Iterator<String> iterator = insightVers.iterator(); iterator.hasNext();) {
                    String next = iterator.next();
                     System.out.println("collector.Collector.setupEntries(): InsVersL : "+next);
                     logger.info("InsVersL : "+next);
                     versionString=versionString.concat(next+";");
                }
                 System.out.println("collector.Collector.setupEntries()  Concatenated String : "+versionString); 
                 logger.info("Concatenated String : "+versionString);
                 jobstep.setInsightVersions(versionString);
                 
                 
                 System.out.println("collector.Collector.setupEntries(): jobStep: "+jobstep.getNameJobStep()+" :ID: "+jobstep.getIdJobStep());
                 logger.info("jobStep: "+jobstep.getNameJobStep()+" :ID: "+jobstep.getIdJobStep());
                 //add to db
                 //if(!dbJobSteps.contains(jobStep))dbJobSteps.add(jobStep);
                 
               //  dbJobSteps.clear();
                 
               //  if(jsServ.getJobStep(jobstep.getIdJobStep())==null){
                     System.out.println("collector.Collector.setupEntries(): New / jobStep: Adding to dbJobSteps: "+jobstep.getNameJobStep());
                     logger.info("New / jobStep: Adding to dbJobSteps: "+jobstep.getNameJobStep());
                     dbJobSteps.add(jobstep);
                // }
                 
                 
                 
                 
                 //setup SessionJob details. and add to db
                 
                 //List<SessionDetails> test=ssdServ.getSessionDetails(jobstep, sess);
                 
                 if(ssdServ.getSessionDetails(jobstep, sess)==null){
                     
                     SessionDetails sd=new SessionDetails(jobstep, sess);
                     System.out.println("collector.Collector.setupEntries(): Adding to dbSessionDetails: ");
                     logger.info("Adding to dbSessionDetails: "+sd.getIdSessionDetails());
                     dbSessionDetails.add(sd);}
               //  if(!dbSessions.contains(sd))dbSessionDetails.add(sd);
                 
                 
                 //add To parents
                
                 
                 
               
                 
                 //ObservableList<VolumeSelectionModelType1> vsmlist= jsm.getVolList();
                 ObservableList<VolumeSelectionModelType0> vsmlist= jsm.getVolList();
                 //for (Iterator<VolumeSelectionModelType1> vit = vsmlist.iterator(); vit.hasNext();) {
                 for (Iterator<VolumeSelectionModelType0> vit = vsmlist.iterator(); vit.hasNext();) {
                    //VolumeSelectionModelType1 vsm = vit.next();
                    VolumeSelectionModelType0 vsm = vit.next();
                    Volume vp=new Volume();
                     System.out.println("collector.Collector.setupEntries(): Volume: "+vsm.getLabel()+" :id: "+vsm.getId());
                     logger.info("Volume: "+vsm.getLabel()+" :id: "+vsm.getId());
                    vp.setIdVolume(vsm.getId());
                    vp.setNameVolume(vsm.getLabel());
                    vp.setVolumeType(vsm.getVolumeType());
                    vp.setAlert(Boolean.FALSE);
                    //vp.setHeaderExtracted(Boolean.FALSE);
                    vp.setHeaderExtracted(vsm.getHeaderButtonStatus());
                    vp.setMd5Hash(null);                                //figure a way to calculate MD5
                    if(vsm.getType().equals(1L)){
                        vp.setPathOfVolume(vsm.getVolumeChosen().getAbsolutePath());
                    }
                    if(vsm.getType().equals(2L)){
                        vp.setPathOfVolume(vsm.getVolumeChosen().getAbsolutePath());
                    }
                    if(vsm.getType().equals(3L)){
                        vp.setPathOfVolume("no volume for acq");
                    }
                    if(vsm.getType().equals(4L)){
                        vp.setPathOfVolume(vsm.getVolumeChosen().getAbsolutePath());
                    }
                    
                    
                    dbVolumes.add(vp);
                    
                   // if(volServ.getVolume(vp.getIdVolume())==null){dbVolumes.add(vp);}
                    
                    
                   // if(!dbVolumes.contains(vp))dbVolumes.add(vp);
                    
                    JobVolumeDetails jvd=new JobVolumeDetails(jobstep, vp);
                    
                   // if(!dbJobVolumeDetails.contains(jvd))dbJobVolumeDetails.add(jvd);
                   // if(jvdServ.getJobVolumeDetails(jobStep, vp)==null){dbJobVolumeDetails.add(jvd);}
                   
                   if(jvdServ.getJobVolumeDetails(jobstep, vp)==null){
                       System.out.println("collector.Collector.setupEntries(): Adding a new entry to jobvolumedetails: for job: "+jobstep.getIdJobStep()+" : "+jobstep.getNameJobStep()+" vol: id: "+vp.getIdVolume()+" : "+vp.getNameVolume() );
                       logger.info("Adding a new entry to jobvolumedetails: for job: "+jobstep.getIdJobStep()+" : "+jobstep.getNameJobStep()+" vol: id: "+vp.getIdVolume()+" : "+vp.getNameVolume());
                       dbJobVolumeDetails.add(jvd);
                       
                   }
                   
                }
                      
                 
                 
            }
       // }
        
        
         //for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();){
            
          
             
         //}
        
        
        
        commitEntries();
        }catch(Exception ex){
            if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
        }
    }
    
    //codes for commiting the transactions
       
    private void commitEntries(){
        try{
        //update list of sequences;
        //get entries of shot lines from orcaview
        List<OrcaView> orcaList=oserv.getOrcaView();            //list of acquisitions
        List<Long> orcaListSeq=oserv.getSeqOrcaView();
        for (Iterator<Long> iterator = orcaListSeq.iterator(); iterator.hasNext();) {
            Long next = iterator.next();
            System.out.println("collector.Collector.commitEntries(): seq "+next);
            
        }
        List<Sequence> refsequences=seqserv.getSequenceList();
        
        if(refsequences!=null && orcaListSeq!=null)
        if(refsequences.size()!=orcaListSeq.size()){                //add to databse if new sequences are added to the orcaview
            
        
        for (Iterator<OrcaView> iterator = orcaList.iterator(); iterator.hasNext();) {
            OrcaView ov = iterator.next();
            Sequence seq=new Sequence();
            seq.setSequenceno(ov.getSequences());
            if(seqserv.getSequenceObjByseqno(ov.getSequences())==null)seqserv.createSequence(seq);
            else continue;
            
        }
        
        List<Sequence> sequences=seqserv.getSequenceList();     //list of sequences
        List<Seq> coreSeqList=new ArrayList<>();
        List<Acquisition> acqlist=new ArrayList<>();

        for (Iterator<Sequence> iterator = sequences.iterator(); iterator.hasNext();) {
            Sequence seq = iterator.next();
            Seq cseq=new Seq();
            cseq.setSeqno(seq.getSequenceno());
            List<Sub> sublist=new ArrayList();
            List<OrcaView> ocsForSeq=oserv.getOrcaViewsForSeq(seq);
            for (Iterator<OrcaView> iterator1 = ocsForSeq.iterator(); iterator1.hasNext();) {
                OrcaView ov = iterator1.next();
                Subsurface sub=new Subsurface();
                sub.setSequence(seq);
                sub.setSubsurface(ov.getDugSubsurface());
                //System.out.println("collector.Collector.commitEntries(): Looking for "+ov.getDugSubsurface()+"  sub: "+ov.getSubsurfaceLineNames()+" cab:"+ ov.getCables()+" gun:"+ov.getGuns());
               // logger.info("Looking for "+ov.getDugSubsurface()+"  sub: "+ov.getSubsurfaceLineNames()+" cab:"+ ov.getCables()+" gun:"+ov.getGuns());
                if(subserv.getSubsurfaceObjBysubsurfacename(ov.getDugSubsurface())==null){
                    System.out.println("collector.Collector.commitEntries(): creating "+ov.getDugSubsurface());
                    logger.info("creating "+ov.getDugSubsurface());
                    subserv.createSubsurface(sub);
                    Sub sb=new Sub();
                    sb.setSeq(cseq);
                    sb.setSubsurfaceName(sub.getSubsurface());
                    sublist.add(sb);
                    
                    Acquisition ah=new Acquisition();
                    ah.setSequence(seq);
                    ah.setSubsurface(sub);
                    ah.setCable(ov.getCables());
                    ah.setFgsp(ov.getFgsp());
                    ah.setFirstChannel(ov.getFirstChannel());
                    ah.setFirstFFID(ov.getFirstFFID());
                    ah.setFirstGoodFFID(ov.getFgFFID());
                    ah.setFirstShot(ov.getFirstSHOT());
                    ah.setGun(ov.getGuns());
                    ah.setLastChannel(ov.getLastChannel());
                    ah.setLastFFID(ov.getLastFFID());
                    ah.setLastGoodFFID(ov.getLgFFID());
                    ah.setLastShot(ov.getLastSHOT());
                    ah.setLgsp(ov.getLgsp());
                    
                    acqlist.add(ah);
                    
                    
                    
                    }
                    else continue;
                }
                cseq.setSubsurfaceList(sublist);
                coreSeqList.add(cseq);
            }
        
      
        
            for (Iterator<Acquisition> iterator = acqlist.iterator(); iterator.hasNext();) {
                Acquisition acq = iterator.next();
                acqserv.createAcquisition(acq);

            }
        }
        
        //add to the Sessions Table
        for(Iterator<Sessions> ssit = dbSessions.iterator();ssit.hasNext();){
            Sessions sess= ssit.next();
           
            if(sesServ.getSessions(sess.getIdSessions())==null) 
            {
              
                System.out.println("Coll: SessID: "+sess.getIdSessions()+" returned null");
                logger.info("SessID: "+sess.getIdSessions()+" returned null");
                sesServ.createSessions(sess);
            }
            
        }
        
        
        //add to the Jobs Table
        for (Iterator<JobStep> jsit = dbJobSteps.iterator(); jsit.hasNext();) {
            JobStep js = jsit.next();
            if(jsServ.getJobStep(js.getIdJobStep())==null){
                System.out.println("collector.Collector.commitEntries(): Creating new jobstep: id: "+js.getIdJobStep()+" name: "+js.getNameJobStep());
                logger.info("Creating new jobstep: id: "+js.getIdJobStep()+" name: "+js.getNameJobStep());
                jsServ.createJobStep(js);}
            else{
                System.out.println("collector.Collector.commitEntries() Updating "+js.getNameJobStep());
                logger.info("Updating "+js.getNameJobStep());
                String jsv=js.getInsightVersions();
                System.out.println("collector.Collector.commitEntries() About to commit the string of Versions: "+jsv); 
                
                jsServ.updateJobStep(js.getIdJobStep(), js);
            }
                
            
           
        }
        
        //add to the SessionDetails Table
        for (Iterator<SessionDetails> iterator = dbSessionDetails.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            System.out.println("collector.Collector.commitEntries(): About to create SessionDetails for: job: "+next.getJobStep().getNameJobStep() +" with id: "+next.getJobStep().getIdJobStep()+" :for session: "+next.getSessions().getNameSessions()+" id: "+next.getSessions().getIdSessions());
            
            if(ssdServ.getSessionDetails(next.getJobStep(), next.getSessions())==null){
                logger.info("About to create SessionDetails for: job: "+next.getJobStep().getNameJobStep() +" with id: "+next.getJobStep().getIdJobStep()+" :for session: "+next.getSessions().getNameSessions()+" id: "+next.getSessions().getIdSessions());
                ssdServ.createSessionDetails(next);
            }
        }
       /* 
        
       
        
        */
        //add to the Volumes Table
        for (Iterator<Volume> iterator = dbVolumes.iterator(); iterator.hasNext();) {
           Volume next = iterator.next();
           if(volServ.getVolume(next.getIdVolume())==null) {
               logger.info("Creating volume entry : Volume : name: "+next.getPathOfVolume()+" id: "+ next.getIdVolume());
               volServ.createVolume(next);
           }
            
        }
        
        
        //add to the JobVolumeDetails Table
        for (Iterator<JobVolumeDetails> iterator = dbJobVolumeDetails.iterator(); iterator.hasNext();) {
            JobVolumeDetails next = iterator.next();
            if(jvdServ.getJobVolumeDetails(next.getJobStep(), next.getVolume())==null){
                logger.info("Creating jobVolumeDetails entry: id "+ next.getIdJobVolumeDetails());
                jvdServ.createJobVolumeDetails(next);
            }
            
        }
        
        
        //add to the NodePropertyValue table
        for (Iterator<NodePropertyValue> iterator = dbNodePropertyValues.iterator(); iterator.hasNext();) {
            NodePropertyValue npv = iterator.next();
            if(npvserv.getNodePropertyValueFor(npv.getJobStep(),npv.getNodeProperty())==null){
               System.out.println("collector.Collector.setupEntries(): New Entries been created nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
               logger.info("New Entries been created nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                npvserv.createNodePropertyValue(npv);
            }
            
        }
        
        
        //
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    createAllAncestors();
                    createAllDescendants();
                    return null;
                }
                
            }).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(Collector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Collector.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        startWatching();
        }catch(Exception ex){
            if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
        }
    }
   
     private void createAllAncestors() {
         try{
         
         /*
         Delete ALL entries from the Parent table for the current Session
         */
         
         
         System.out.println("collector.Collector.createAllAncestors(): DELETING ALL ENTRIES FROM THE PARENT TABLE FOR THE SESSION : "+currentSession.getNameSessions());
         logger.info("deleting all entries from the parent table for session "+ currentSession.getNameSessions());
         List<SessionDetails> sL=ssdServ.getSessionDetails(currentSession);
         
         for (Iterator<SessionDetails> sli = sL.iterator(); sli.hasNext();) {
             SessionDetails sdn = sli.next();
             System.out.println("collector.Collector.createAllAncestors(): Searching for parent of sessionDetails: "+sdn.getIdSessionDetails());
             logger.info("Searching for parent of sessionDetails: "+ sdn.getIdSessionDetails());
             List<Parent> pl=pServ.getParentsFor(sdn);
             
                for (Iterator<Parent> pit = pl.iterator(); pit.hasNext();) {
                 Parent pan = pit.next();
                 Long pin=pan.getIdParent();
                 System.out.println("collector.Collector.createAllAncestors(): deleting parent id: "+pin);
                 logger.info( "deleting parent id: "+ pin);
                 pServ.deleteParent(pin);
                 
             }
             
         }
         
         System.out.println("collector.Collector.createAllAncestors(): Done deleting parents");
         logger.info("done deleting parents");
         
         
         //load the dbAncestor List
            for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepType0Model jsm = jit.next();
              //  System.out.println("collector.Collector.createAllAncestors()  :JobStepModel: "+jsm.getJobStepText()+" id: "+jsm.getId());
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbAncestors=new ArrayList<>();
                dbParent=new ArrayList<>();
                System.out.println("collector.Collector.createAllAncestors() : JobStep: "+js.getNameJobStep()+ " :id: "+js.getIdJobStep());
                logger.info("JobStep: "+js.getNameJobStep()+ " :id: "+js.getIdJobStep());
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                    System.out.println("collector.Collector.createAllAncestors(): CurrentSession: "+currentSession.getNameSessions());
                    logger.info( "CurrentSession: "+currentSession.getNameSessions());
                    System.out.println("collector.Collector.createAllAncestors(): SessionDetails: "+sd.getSessions().getNameSessions());// +" :currentSession:  "+currentSession.getNameSessions()+" :jobStep: "+js.getNameJobStep());
                      ArrayList<JobStepType0Model> listOfParents=(ArrayList<JobStepType0Model>) jsm.getJsParents();
                 
                 for(Iterator<JobStepType0Model> pit = listOfParents.iterator(); pit.hasNext();) {
                     JobStepType0Model par = pit.next();
                     System.out.println("collector.Collector.createAllAncestors(): "+par.getJobStepText());
                     
                   //  Ancestors ancestor=new Ancestors();  //
                     Parent parent=new Parent();
                     parent.setSessionDetails(sd);
                     JobStep parJs=jsServ.getJobStep(par.getId());
                     
                     System.out.println("collector.Collector.createAllAncestors()  ParentJobStep: "+parJs.getNameJobStep());
                     logger.info("ParentJobStep: "+parJs.getNameJobStep());
                     System.out.println("collector.Collector.createAllAncestors() CurrentSession: "+currentSession.getNameSessions());
                     SessionDetails parSSd=ssdServ.getSessionDetails(parJs, currentSession);
                    if(parSSd!=null)parent.setParent(parSSd.getIdSessionDetails());
                    
                     if(pServ.getParentRowFor(parent.getSessionDetails(), parent.getParent())==null){dbParent.add(parent);}
              
                    
                }
                 
                    
                    for (Iterator<Parent> iterator = dbParent.iterator(); iterator.hasNext();) {
                    Parent next = iterator.next();
                    
                    //if(pServ.getParentRowFor(next.getSessionDetails(), next.getParent())==null){pServ.addParent(next);}
                    logger.info("Adding parent: "+next.getIdParent());
                    pServ.addParent(next);
                }
       
            }
         
         
         
        
            
            
         
            
         
        //figure out what goes where and who is who'sb ancestor    ..store in a map
            
         Map<SessionDetails,Set<Long>> ancestorMap=new HashMap<>();
         List<SessionDetails> sdList= ssdServ.getSessionDetails(currentSession);
         
         for(SessionDetails ssd:sdList){
             Set<Long> ancestorTableList=new LinkedHashSet<>();                 // list to hold all jobSteps that are ancestors  belonging to ssd. i.e all these jobs belong to the same session
             //System.out.println(" Coll: SdID: "+ssd.getIdSessionDetails()+" size: "+sdList.size());
             ancServ.getInitialAncestorsListFor(ssd, ancestorTableList);
             for(Long a:ancestorTableList){
           // System.out.println("ssd: "+ssd.getIdSessionDetails()+" ancestorId: "+a);
                 System.out.println("job: "+ssd.getJobStep().getNameJobStep()+" has ancestor: "+ssdServ.getSessionDetails(a).getJobStep().getNameJobStep());
                 logger.info("job: "+ssd.getJobStep().getNameJobStep()+" has ancestor: "+ssdServ.getSessionDetails(a).getJobStep().getNameJobStep());
        }
        ancestorMap.put(ssd, ancestorTableList);
         }
         
         
        //commit the map entries to the db OVERWRITING the table 
         
         for (Map.Entry<SessionDetails, Set<Long>> entrySet : ancestorMap.entrySet()) {
          SessionDetails key = entrySet.getKey();
          Set<Long> value = entrySet.getValue();
          logger.info("making the ancestor table for "+key.getJobStep().getNameJobStep()+" in session "+key.getSessions().getNameSessions()+" value=ancestors sessionId = "+value);
          ancServ.makeAncestorsTableFor(key, value);
          
      }
         }catch(Exception ex){
            if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
         }
         
    }
     
     private void createAllDescendants() {
         
         /*
         Debug
         */
         try{
         for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();){
             JobStepType0Model jsm = jit.next();
             
             ArrayList<JobStepType0Model> children=(ArrayList<JobStepType0Model>) jsm.getJsChildren();
             for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model ch = iterator.next();
             //    System.out.println("Coll: FE Parent : "+jsm.getJobStepText()+ "  has child : "+ch.getJobStepText() +" is Leaf: "+jsm.isLeaf());
                 
             }
                 
             }
         
         
         
         
         
         /*
         END debug
         */
         
         
         
         // Delete ALL entries from the Child table  for the current Session
         
         System.out.println("collector.Collector.createAllDescendants() : DELETING ALL ENTRIES FROM THE Child TABLE FOR THE SESSION : "+currentSession.getNameSessions());
         logger.info("deleting all entries from the Child Table for the session "+ currentSession.getNameSessions());
         
         List<SessionDetails> sL=ssdServ.getSessionDetails(currentSession);
         
         System.out.println("collector.Collector.createAllDescendants(): deleting ALL CHILD entries for the session: "+currentSession.getIdSessions());
                
         
         for (Iterator<SessionDetails> sli = sL.iterator(); sli.hasNext();) {
             SessionDetails sdn = sli.next();
             List<Child> cl=cServ.getChildrenFor(sdn);
             
                for (Iterator<Child> cit = cl.iterator(); cit.hasNext();) {
                 Child chn = cit.next();
                 Long cid=chn.getIdChild();
                 
//                    System.out.println("deleting entry for : "+ssdServ.getSessionDetails(chn.getChild()).getJobStep().getNameJobStep()+ " :for job: "+sdn.getJobStep().getNameJobStep());
                 logger.info("deleting entry for "+cid+"from child table");
                  cServ.deleteChild(cid);
                    
             }
         }
         
         
         
         
         
         for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepType0Model jsm = jit.next();
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbDescendants=new ArrayList<>();
                dbChild=new ArrayList<>();
            
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                    System.out.println("collector.Collector.createAllDescendants(): CurrentSession: "+currentSession.getNameSessions());
                      ArrayList<JobStepType0Model> listOfChildren=(ArrayList<JobStepType0Model>) jsm.getJsChildren();
                 
                 for (Iterator<JobStepType0Model> cit = listOfChildren.iterator(); cit.hasNext();) {
                   JobStepType0Model child = cit.next();
                     System.out.println("collector.Collector.createAllDescendants() :" +jsm.getJobStepText()+" : has child: "+child.getJobStepText() );
                     logger.info(jsm.getJobStepText()+" : has child: "+child.getJobStepText());
                   
                     Child c=new Child();
                  
                     c.setSessionDetails(sd);                            //This is the same as setting the parent of this child; in this case is the sessiondetails to which "js" belongs to.
                     System.out.println("collector.Collector.createAllDescendants(): sessionDetailsID(Parent): "+c.getSessionDetails().getIdSessionDetails());
                     //logger.info("sessionDetailsID(Parent): "+c.getSessionDetails().getIdSessionDetails());
                     logger.info("sessionDetailsID(Parent): "+c.getSessionDetails().getIdSessionDetails());
                     JobStep childJs=jsServ.getJobStep(child.getId());
                     System.out.println("collector.Collector.createAllDescendants(): childJobStep: "+childJs.getNameJobStep()+" :ID: "+childJs.getIdJobStep());
                     logger.info("childJobStep: "+childJs.getNameJobStep()+" :ID: "+childJs.getIdJobStep());
                     SessionDetails childSSd=ssdServ.getSessionDetails(childJs, currentSession);   //get the sessiondetails corresponding to the child.
                     System.out.println("collector.Collector.createAllDescendants() : sessionDetailsID(Child): "+childSSd.getIdSessionDetails());
                     logger.info("sessionDetailsID(Child): "+childSSd.getIdSessionDetails());
                     c.setChild(childSSd.getIdSessionDetails());   // this adds the sessiondetails of the child to the table Child.
                                                                   // so the table entry will look like:      SSDofAjob, SSDofTheChild
                  
                    if(cServ.getChildFor(c.getSessionDetails(), c.getChild())==null){
                        logger.info( "Adding child: "+ c.getSessionDetails().getJobStep().getNameJobStep()+" to the list of Children");
                        dbChild.add(c);
                    }
                    //dbChild.add(c);
                     System.out.println("collector.Collector.createAllDescendants(): sizeof DbChild: "+dbChild.size());
                     
                }
                  //write the initial dbAncestors List to the database . for each jobStepModel
                
                        
                    for (Iterator<Child> iterator = dbChild.iterator(); iterator.hasNext();) {
                 Child next = iterator.next();
                       // System.out.println("Collector: child id "+next.getIdChild()+" sessDetailsID: "+next.getSessionDetails().getIdSessionDetails()+ " childID: "+next.getChild());
                       // System.out.println("collector.Collector.createAllDescendants(): "+jsServ.getJobStep(next.getChild()).getIdJobStep()+" for job: "+jsm.getJobStepText());
                     //  System.out.println("collector.Collector.createAllDescendants(): "+next.getChild()+" for job: "+jsm.getJobStepText());
                     //   System.out.println("collector.Collector.createAllDescendants():  "+ssdServ.getSessionDetails(next.getChild()).getJobStep().getNameJobStep()+ " for Parent job: "+jsm.getJobStepText());
                     //   System.out.println("collector.Collector.createAllDescendants():  for session: "+ssdServ.getSessionDetails(next.getChild()).getSessions().getIdSessions() );
                        cServ.addChild(next);
             }
       
            }
         
         
          //figure out what goes where and who is who'sb ancestor    ..store in a map
            
           
        Map<SessionDetails,Set<Long>> descendantMap=new HashMap<>();
        List<SessionDetails> sdList= ssdServ.getSessionDetails(currentSession);
        
        for(SessionDetails ssd:sdList){
            
            // Start Debug
              // System.out.println("collector.Collector.createAllDescendants(): jobs in current session: "+ssd.getJobStep().getNameJobStep());
               
            // End Debug
            
            
          Set<Long> descTableList=new LinkedHashSet<>();
          descServ.getInitialDescendantsListFor(ssd, descTableList);
          for(Long a:descTableList){
            
             System.out.println("job: "+ssd.getJobStep().getNameJobStep()+" has descendant: "+ssdServ.getSessionDetails(a).getJobStep().getNameJobStep());
             logger.info(ssd.getJobStep().getNameJobStep()+" has descendant: "+ssdServ.getSessionDetails(a).getJobStep().getNameJobStep());
        }
        descendantMap.put(ssd, descTableList);
       }
        
         //commit the map entries to the db OVERWRITING the table 
        for (Map.Entry<SessionDetails, Set<Long>> entrySet : descendantMap.entrySet()) {
          SessionDetails key = entrySet.getKey();
          Set<Long> value = entrySet.getValue();
         descServ.makeDescendantsTableFor(key, value);
        
          
      }
         }catch(Exception ex){
             if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
         }
    }

    private void startWatching() {
        try{
        for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
            JobStepType0Model jsm = jit.next();
            //ObservableList<VolumeSelectionModelType1> vsmlist= jsm.getVolList();
             ObservableList<VolumeSelectionModelType0> vsmlist= jsm.getVolList();
             /*for (Iterator<VolumeSelectionModelType1> iterator = vsmlist.iterator(); iterator.hasNext();) {
             VolumeSelectionModelType1 next = iterator.next();
             System.out.println("collector.Collector.startWatching(): "+next.getLabel());
             next.startWatching();
             
             }*/
             for (Iterator<VolumeSelectionModelType0> iterator = vsmlist.iterator(); iterator.hasNext();) {
             VolumeSelectionModelType0 next = iterator.next();
             System.out.println("collector.Collector.startWatching(): "+next.getLabel());
             logger.info("startWatching(): "+next.getLabel());
             next.startWatching();
             
             }
        }
        }catch(Exception ex){
            if ( ex instanceof NullPointerException){
                  logger.severe("Null pointer exception encountered");
              }else{
                  logger.severe(ex.getMessage());
              }
        }
    }

    
     
     
    
    
}
