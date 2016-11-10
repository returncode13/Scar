/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collector;

import db.model.Ancestors;
import db.model.Child;
import db.model.Descendants;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Parent;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Volume;
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
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionModel;
import fend.session.node.jobs.JobStepModel;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    ObservableList<JobStepModel> feJobModel=FXCollections.observableArrayList();
    private ArrayList<VolumeSelectionModel> feVolume=new ArrayList<>();
       
    
    
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
        
         for (Iterator<JobStepModel> iterator = feJobModel.iterator(); iterator.hasNext();) {
             JobStepModel next = iterator.next();
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
        
        
        
        
        
        //for every session
       // for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();) {
           Sessions sess = currentSession;//iterator.next();
            
           //for each jobStep from fe
            for (Iterator<JobStepModel> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepModel jsm = jit.next();
                 JobStep jobStep=new JobStep();
                 jobStep.setNameJobStep(jsm.getJobStepText());
                 jobStep.setIdJobStep(jsm.getId());
                 System.out.println("Coll: JSM ID: "+jsm.getId());
                 jobStep.setAlert(Boolean.FALSE);
                 
                 System.out.println("collector.Collector.setupEntries(): jobStep: "+jobStep.getNameJobStep()+" :ID: "+jobStep.getIdJobStep());
                 
                 //add to db
                 //if(!dbJobSteps.contains(jobStep))dbJobSteps.add(jobStep);
                 if(jsServ.getJobStep(jobStep.getIdJobStep())==null){
                     System.out.println("collector.Collector.setupEntries(): New jobStep: Adding to dbJobSteps: "+jobStep.getNameJobStep());
                     dbJobSteps.add(jobStep);
                 }
                 
                 
                 
                 
                 //setup SessionJob details. and add to db
                 SessionDetails sd=new SessionDetails(jobStep, sess);
                 if(ssdServ.getSessionDetails(jobStep, sess)==null){dbSessionDetails.add(sd);}
               //  if(!dbSessions.contains(sd))dbSessionDetails.add(sd);
                 
                 
                 //add To parents
                
                 
                 
               
                 
                 ObservableList<VolumeSelectionModel> vsmlist= jsm.getVolList();
                 
                 for (Iterator<VolumeSelectionModel> vit = vsmlist.iterator(); vit.hasNext();) {
                    VolumeSelectionModel vsm = vit.next();
                    Volume vp=new Volume();
                    vp.setIdVolume(vsm.getId());
                    vp.setNameVolume(vsm.getLabel());
                    vp.setAlert(Boolean.FALSE);
                    vp.setMd5Hash(null);                                //figure a way to calculate MD5
                    vp.setPathOfVolume(vsm.getVolumeChosen().getAbsolutePath());
                    
                    
                    if(volServ.getVolume(vp.getIdVolume())==null){dbVolumes.add(vp);}
                    
                    
                   // if(!dbVolumes.contains(vp))dbVolumes.add(vp);
                    
                    JobVolumeDetails jvd=new JobVolumeDetails(jobStep, vp);
                    
                   // if(!dbJobVolumeDetails.contains(jvd))dbJobVolumeDetails.add(jvd);
                    if(jvdServ.getJobVolumeDetails(jobStep, vp)==null){dbJobVolumeDetails.add(jvd);}
                }
                      
                 
                 
            }
       // }
        
        
         //for (Iterator<Sessions> iterator = dbSessions.iterator(); iterator.hasNext();){
            
          
             
         //}
        
        
        
        commitEntries();
    }
    
    
    
    //codes for commiting the transactions
       
    private void commitEntries(){
        
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
            if(jsServ.getJobStep(js.getIdJobStep())==null)jsServ.createJobStep(js);
        }
        
        //add to the SessionDetails Table
        for (Iterator<SessionDetails> iterator = dbSessionDetails.iterator(); iterator.hasNext();) {
            SessionDetails next = iterator.next();
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
       createAllAncestors();
        createAllDescendants();
    }
   
     private void createAllAncestors() {
         
         /*
         Delete ALL entries from the Parent table for the current Session
         */
         
         
         System.out.println("collector.Collector.createAllAncestors(): DELETING ALL ENTRIES FROM THE PARENT TABLE FOR THE SESSION : "+currentSession.getNameSessions());
         List<SessionDetails> sL=ssdServ.getSessionDetails(currentSession);
         
         for (Iterator<SessionDetails> sli = sL.iterator(); sli.hasNext();) {
             SessionDetails sdn = sli.next();
             
             List<Parent> pl=pServ.getParentsFor(sdn);
             
                for (Iterator<Parent> pit = pl.iterator(); pit.hasNext();) {
                 Parent pan = pit.next();
                 Long pin=pan.getIdParent();
                 
                 pServ.deleteParent(pin);
                 
             }
             
         }
         
         
         
         
         //load the dbAncestor List
            for (Iterator<JobStepModel> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepModel jsm = jit.next();
              //  System.out.println("collector.Collector.createAllAncestors()  :JobStepModel: "+jsm.getJobStepText()+" id: "+jsm.getId());
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbAncestors=new ArrayList<>();
                dbParent=new ArrayList<>();
              //  System.out.println("collector.Collector.createAllAncestors() : JobStep: "+js.getNameJobStep()+ " :id: "+js.getIdJobStep());
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                   // System.out.println("collector.Collector.createAllAncestors(): CurrentSession: "+currentSession.getNameSessions());
                   // System.out.println("collector.Collector.createAllAncestors(): SessionDetails: "+sd.getSessions().getNameSessions());// +" :currentSession:  "+currentSession.getNameSessions()+" :jobStep: "+js.getNameJobStep());
                      ArrayList<JobStepModel> listOfParents=jsm.getJsParents();
                 
                 for (Iterator<JobStepModel> pit = listOfParents.iterator(); pit.hasNext();) {
                     JobStepModel par = pit.next();
              //       System.out.println("collector.Collector.createAllAncestors(): "+par.getJobStepText());
                     
                   //  Ancestors ancestor=new Ancestors();  //
                     Parent parent=new Parent();
                     parent.setSessionDetails(sd);
                     JobStep parJs=jsServ.getJobStep(par.getId());
                     
               //      System.out.println("collector.Collector.createAllAncestors()  ParentJobStep: "+parJs.getNameJobStep());
               //      System.out.println("collector.Collector.createAllAncestors() CurrentSession: "+currentSession.getNameSessions());
                     SessionDetails parSSd=ssdServ.getSessionDetails(parJs, currentSession);
                     parent.setParent(parSSd.getIdSessionDetails());
                    
                     if(pServ.getParentFor(parent.getSessionDetails(), parent.getParent())==null){dbParent.add(parent);}
              
                    
                }
                 
                    
                    for (Iterator<Parent> iterator = dbParent.iterator(); iterator.hasNext();) {
                    Parent next = iterator.next();
                    
                    //if(pServ.getParentFor(next.getSessionDetails(), next.getParent())==null){pServ.addParent(next);}
                    pServ.addParent(next);
                }
       
            }
         
         
         
        
            
            
         
            
         
        //figure out what goes where and who is who's ancestor    ..store in a map
            
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
         
         for (Iterator<JobStepModel> jit = feJobModel.iterator(); jit.hasNext();){
             JobStepModel jsm = jit.next();
             
             ArrayList<JobStepModel> children=jsm.getJsChildren();
             for (Iterator<JobStepModel> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepModel ch = iterator.next();
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
         
         
         
         
         
         for (Iterator<JobStepModel> jit = feJobModel.iterator(); jit.hasNext();) {
                JobStepModel jsm = jit.next();
                JobStep js=jsServ.getJobStep(jsm.getId());
               // dbDescendants=new ArrayList<>();
                dbChild=new ArrayList<>();
            
                    SessionDetails sd=ssdServ.getSessionDetails(js, currentSession);
                   // System.out.println("collector.Collector.createAllDescendants(): CurrentSession: "+currentSession.getNameSessions());
                      ArrayList<JobStepModel> listOfChildren=jsm.getJsChildren();
                 
                 for (Iterator<JobStepModel> cit = listOfChildren.iterator(); cit.hasNext();) {
                     JobStepModel child = cit.next();
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
         
         
          //figure out what goes where and who is who's ancestor    ..store in a map
            
           
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
     
    
}
