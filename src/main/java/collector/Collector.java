/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import core.Seq;
import core.Sub;
import db.model.Acquisition;
import db.model.Ancestors;
import db.model.Child;
import db.model.Descendants;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.OrcaView;
import db.model.Parent;
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
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.OrcaViewService;
import db.services.OrcaViewServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
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
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.volumes.acquisition.AcqHeaders;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
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
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair
 */
public class Collector {
    //from frontEnd. user
    
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
    
    public Collector(){
       // dbSessions.add(new Sessions("+twoSessions", "gamma123"));                               //fixing on one session for the presentation
    }

    public void setFeJobGraphModel(SessionModel feJobGraphModel) {
        this.feSessionModel = feJobGraphModel;
        System.out.println("Collector: Set the graphModel ");
        setupEntries();
    }


    
     public void saveCurrentSession(SessionModel smod) {
        
         feSessionModel=smod;
         
         
        currentSession=new Sessions();
        currentSession.setIdSessions(feSessionModel.getId());
        currentSession.setNameSessions(feSessionModel.getName());
        
     
        
        feJobModel=feSessionModel.getListOfJobs();
        
         for (Iterator<JobStepType0Model> iterator = feJobModel.iterator(); iterator.hasNext();) {
             JobStepType0Model next = iterator.next();
             System.out.println("collector.Collector.saveCurrentSession(): List of Jobs in session: "+next.getJobStepText()+" :ID: "+next.getId());
         }
        dbSessions.add(currentSession);
       // if(sesServ.getSessions(currentSession.getIdSessions())==null)dbSessions.add(currentSession);
        setupEntries();
    }
    
    /*
    Set up the data base entry datastructures
    */
    
    
    //The code for setting up the sessions datastructures go here   <=== DONT FORGET.
    
    private void setupEntries(){
        
        dbSessionDetails.clear();
        dbJobSteps.clear();                         //clear previous jobmodel array. set current entries here
       dbVolumes.clear();
        dbJobVolumeDetails.clear();
        
        //for every session
       // for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();) {
           Sessions sess = currentSession;//iterator.next();
            
           //for each jobStep from fe
            for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                
                JobStepType0Model jsm=jit.next();
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
                    jobstep.setType(jsm.getType());
                    //jsServ.updateJobStep(jobstep.getIdJobStep(), jobstep);
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
                     versionString=versionString.concat(next+";");
                }
                 System.out.println("collector.Collector.setupEntries()  Concatenated String : "+versionString); 
                 jobstep.setInsightVersions(versionString);
                 
                 
                 System.out.println("collector.Collector.setupEntries(): jobStep: "+jobstep.getNameJobStep()+" :ID: "+jobstep.getIdJobStep());
                 
                 //add to db
                 //if(!dbJobSteps.contains(jobStep))dbJobSteps.add(jobStep);
                 
               //  dbJobSteps.clear();
                 
               //  if(jsServ.getJobStep(jobStep.getIdJobStep())==null){
                     System.out.println("collector.Collector.setupEntries(): New / Existing jobStep: Adding to dbJobSteps: "+jobstep.getNameJobStep());
                     dbJobSteps.add(jobstep);
                 //}
                 
                 
                 
                 
                 //setup SessionJob details. and add to db
                 SessionDetails sd=new SessionDetails(jobstep, sess);
                 if(ssdServ.getSessionDetails(jobstep, sess)==null){
                     System.out.println("collector.Collector.setupEntries(): Adding to dbSessionDetails: ");dbSessionDetails.add(sd);}
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
                    vp.setIdVolume(vsm.getId());
                    vp.setNameVolume(vsm.getLabel());
                    vp.setVolumeType(vsm.getVolumeType());
                    vp.setAlert(Boolean.FALSE);
                    //vp.setHeaderExtracted(Boolean.FALSE);
                    vp.setHeaderExtracted(vsm.getHeaderButtonStatus());
                    vp.setMd5Hash(null);                                //figure a way to calculate MD5
                    if(!vsm.getType().equals(3L)){
                        vp.setPathOfVolume(vsm.getVolumeChosen().getAbsolutePath());
                    }else{
                        vp.setPathOfVolume("no volume for acq");
                    }
                    
                    
                    dbVolumes.add(vp);
                    
                   // if(volServ.getVolume(vp.getIdVolume())==null){dbVolumes.add(vp);}
                    
                    
                   // if(!dbVolumes.contains(vp))dbVolumes.add(vp);
                    
                    JobVolumeDetails jvd=new JobVolumeDetails(jobstep, vp);
                    
                   // if(!dbJobVolumeDetails.contains(jvd))dbJobVolumeDetails.add(jvd);
                   // if(jvdServ.getJobVolumeDetails(jobStep, vp)==null){dbJobVolumeDetails.add(jvd);}
                   dbJobVolumeDetails.add(jvd);
                }
                      
                 
                 
            }
       // }
        
        
         //for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();){
            
          
             
         //}
        
        
        
        commitEntries();
    }
    
    //codes for commiting the transactions
       
    private void commitEntries(){
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
                System.out.println("collector.Collector.commitEntries(): Looking for "+ov.getDugSubsurface()+"  sub: "+ov.getSubsurfaceLineNames()+" cab:"+ ov.getCables()+" gun:"+ov.getGuns());
                if(subserv.getSubsurfaceObjBysubsurfacename(ov.getDugSubsurface())==null){
                    System.out.println("collector.Collector.commitEntries(): creating "+ov.getDugSubsurface());
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
                sesServ.createSessions(sess);
            }
            
        }
        
        
        //add to the Jobs Table
        for (Iterator<JobStep> jsit = dbJobSteps.iterator(); jsit.hasNext();) {
            JobStep js = jsit.next();
            if(jsServ.getJobStep(js.getIdJobStep())==null){jsServ.createJobStep(js);}
            else{
                System.out.println("collector.Collector.commitEntries() Updating "+js.getNameJobStep());
                String jsv=js.getInsightVersions();
                System.out.println("collector.Collector.commitEntries() About to commit the string of Versions: "+jsv); 
                
                jsServ.updateJobStep(js.getIdJobStep(), js);
            }
                
            
           
        }
        
        //add to the SessionDetails Table
        for (Iterator<SessionDetails> iterator = dbSessionDetails.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
            System.out.println("collector.Collector.commitEntries(): About to create SessionDetails for: job: "+next.getJobStep().getNameJobStep() +" with id: "+next.getJobStep().getIdJobStep()+" :for session: "+next.getSessions().getNameSessions()+" id: "+next.getSessions().getIdSessions());
            if(ssdServ.getSessionDetails(next.getJobStep(), next.getSessions())==null)ssdServ.createSessionDetails(next);
        }
       /* 
        
       
        
        */
        //add to the Volumes Table
        for (Iterator<Volume> iterator = dbVolumes.iterator(); iterator.hasNext();) {
           Volume next = iterator.next();
           if(volServ.getVolume(next.getIdVolume())==null) volServ.createVolume(next);
            
        }
        
        
        //add to the JobVolumeDetails Table
        for (Iterator<JobVolumeDetails> iterator = dbJobVolumeDetails.iterator(); iterator.hasNext();) {
            JobVolumeDetails next = iterator.next();
            if(jvdServ.getJobVolumeDetails(next.getJobStep(), next.getVolume())==null)jvdServ.createJobVolumeDetails(next);
            
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
    }
   
     private void createAllAncestors() {
         
         /*
         Delete ALL entries from the Parent table for the current Session
         */
         
         
         System.out.println("collector.Collector.createAllAncestors(): DELETING ALL ENTRIES FROM THE PARENT TABLE FOR THE SESSION : "+currentSession.getNameSessions());
         List<SessionDetails> sL=ssdServ.getSessionDetails(currentSession);
         
         for (Iterator<SessionDetails> sli = sL.iterator(); sli.hasNext();) {
             SessionDetails sdn = sli.next();
             System.out.println("collector.Collector.createAllAncestors(): Searching for parent of sessionDetails: "+sdn.getIdSessionDetails());
             List<Parent> pl=pServ.getParentsFor(sdn);
             
                for (Iterator<Parent> pit = pl.iterator(); pit.hasNext();) {
                 Parent pan = pit.next();
                 Long pin=pan.getIdParent();
                 System.out.println("collector.Collector.createAllAncestors(): deleting parent id: "+pin);
                 pServ.deleteParent(pin);
                 
             }
             
         }
         
         System.out.println("collector.Collector.createAllAncestors(): Done deleting parents");
         
         
         //load the dbAncestor List
            for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepType0Model jsm = jit.next();
              //  System.out.println("collector.Collector.createAllAncestors()  :JobStepModel: "+jsm.getJobStepText()+" id: "+jsm.getId());
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbAncestors=new ArrayList<>();
                dbParent=new ArrayList<>();
              //  System.out.println("collector.Collector.createAllAncestors() : JobStep: "+js.getNameJobStep()+ " :id: "+js.getIdJobStep());
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                   // System.out.println("collector.Collector.createAllAncestors(): CurrentSession: "+currentSession.getNameSessions());
                   // System.out.println("collector.Collector.createAllAncestors(): SessionDetails: "+sd.getSessions().getNameSessions());// +" :currentSession:  "+currentSession.getNameSessions()+" :jobStep: "+js.getNameJobStep());
                      ArrayList<JobStepType0Model> listOfParents=(ArrayList<JobStepType0Model>) jsm.getJsParents();
                 
                 for(Iterator<JobStepType0Model> pit = listOfParents.iterator(); pit.hasNext();) {
                     JobStepType0Model par = pit.next();
              //       System.out.println("collector.Collector.createAllAncestors(): "+par.getJobStepText());
                     
                   //  Ancestors ancestor=new Ancestors();  //
                     Parent parent=new Parent();
                     parent.setSessionDetails(sd);
                     JobStep parJs=jsServ.getJobStep(par.getId());
                     
               //      System.out.println("collector.Collector.createAllAncestors()  ParentJobStep: "+parJs.getNameJobStep());
               //      System.out.println("collector.Collector.createAllAncestors() CurrentSession: "+currentSession.getNameSessions());
                     SessionDetails parSSd=ssdServ.getSessionDetails(parJs, currentSession);
                    if(parSSd!=null)parent.setParent(parSSd.getIdSessionDetails());
                    
                     if(pServ.getParentRowFor(parent.getSessionDetails(), parent.getParent())==null){dbParent.add(parent);}
              
                    
                }
                 
                    
                    for (Iterator<Parent> iterator = dbParent.iterator(); iterator.hasNext();) {
                    Parent next = iterator.next();
                    
                    //if(pServ.getParentRowFor(next.getSessionDetails(), next.getParent())==null){pServ.addParent(next);}
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
        }
        ancestorMap.put(ssd, ancestorTableList);
         }
         
         
        //commit the map entries to the db OVERWRITING the table 
         
         for (Map.Entry<SessionDetails, Set<Long>> entrySet : ancestorMap.entrySet()) {
          SessionDetails key = entrySet.getKey();
          Set<Long> value = entrySet.getValue();
          ancServ.makeAncestorsTableFor(key, value);
          
      }
                   
    }
     
     private void createAllDescendants() {
         
         /*
         Debug
         */
         
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
         
         //System.out.println("collector.Collector.createAllDescendants() : DELETING ALL ENTRIES FROM THE Child TABLE FOR THE SESSION : "+currentSession.getNameSessions());
         
         
         List<SessionDetails> sL=ssdServ.getSessionDetails(currentSession);
         
        // System.out.println("collector.Collector.createAllDescendants(): deleting ALL CHILD entries for the session: "+currentSession.getIdSessions());
                
         
         for (Iterator<SessionDetails> sli = sL.iterator(); sli.hasNext();) {
             SessionDetails sdn = sli.next();
             List<Child> cl=cServ.getChildrenFor(sdn);
             
                for (Iterator<Child> cit = cl.iterator(); cit.hasNext();) {
                 Child chn = cit.next();
                 Long cid=chn.getIdChild();
                 
                  //  System.out.println("deleting entry for : "+ssdServ.getSessionDetails(chn.getChild()).getJobStep().getNameJobStep()+ " :for job: "+sdn.getJobStep().getNameJobStep());
                 
                  cServ.deleteChild(cid);
                    
             }
         }
         
         
         
         
         
         for (Iterator<JobStepType0Model> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepType0Model jsm = jit.next();
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbDescendants=new ArrayList<>();
                dbChild=new ArrayList<>();
            
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                   // System.out.println("collector.Collector.createAllDescendants(): CurrentSession: "+currentSession.getNameSessions());
                      ArrayList<JobStepType0Model> listOfChildren=(ArrayList<JobStepType0Model>) jsm.getJsChildren();
                 
                 for (Iterator<JobStepType0Model> cit = listOfChildren.iterator(); cit.hasNext();) {
                     JobStepType0Model child = cit.next();
                   //  System.out.println("collector.Collector.createAllDescendants() :" +jsm.getJobStepText()+" : has child: "+child.getJobStepText() );
                   
                     Child c=new Child();
                  
                     c.setSessionDetails(sd);                            //This is the same as setting the parent of this child; in this case is the sessiondetails to which "js" belongs to.
                  //   System.out.println("collector.Collector.createAllDescendants(): sessionDetailsID(Parent): "+c.getSessionDetails().getIdSessionDetails());
                     JobStep childJs=jsServ.getJobStep(child.getId());
                   //  System.out.println("collector.Collector.createAllDescendants(): childJobStep: "+childJs.getNameJobStep()+" :ID: "+childJs.getIdJobStep());
                     SessionDetails childSSd=ssdServ.getSessionDetails(childJs, currentSession);   //get the sessiondetails corresponding to the child.
                   //  System.out.println("collector.Collector.createAllDescendants() : sessionDetailsID(Child): "+childSSd.getIdSessionDetails());
                     c.setChild(childSSd.getIdSessionDetails());   // this adds the sessiondetails of the child to the table Child.
                                                                   // so the table entry will look like:      SSDofAjob, SSDofTheChild
                  
                    if(cServ.getChildFor(c.getSessionDetails(), c.getChild())==null){dbChild.add(c);}
                    //dbChild.add(c);
                    // System.out.println("collector.Collector.createAllDescendants(): sizeof DbChild: "+dbChild.size());
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
        }
        descendantMap.put(ssd, descTableList);
       }
        
         //commit the map entries to the db OVERWRITING the table 
        for (Map.Entry<SessionDetails, Set<Long>> entrySet : descendantMap.entrySet()) {
          SessionDetails key = entrySet.getKey();
          Set<Long> value = entrySet.getValue();
         descServ.makeDescendantsTableFor(key, value);
        
          
      }
    }

    private void startWatching() {
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
             next.startWatching();
             
             }
        }
    }

    
     
     
    
    
}
