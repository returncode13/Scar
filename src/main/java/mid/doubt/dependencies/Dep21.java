/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.dependencies;

import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Subsurface;
import db.model.Volume;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import db.services.DoubtTypeService;
import db.services.DoubtTypeServiceImpl;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.util.ArrayList;
//import fend.session.node.jobs.types.type1.JobStepType1Model;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import landing.AppProperties;
import mid.doubt.Doubt;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Dependency class between parent=JobStepType2 and child=JobStepType1 nodes
 */
public class Dep21 {
    JobStepType2Model parent;
    JobStepType1Model child;
    Boolean passDependency=true;
    String errorMessage=new String();
    DoubtStatusService dsServ=new DoubtStatusServiceImpl();
    DoubtTypeService dstypeServ=new DoubtTypeServiceImpl();
    SessionDetailsService ssdServ= new SessionDetailsServiceImpl();
    SessionsService sessServ=new SessionsServiceImpl();
    JobStepService jserv=new JobStepServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    HeadersService hserv=new HeadersServiceImpl();
    SubsurfaceService subserv=new SubsurfaceServiceImpl();
    ParentService parserv=new ParentServiceImpl();
    
    
    SessionModel session;
    static Boolean Debug=Boolean.FALSE;
    /**
     * @params: 
     * parent : Parent Job
     * child: Child job
     * subsToSummarize: null for first summary, subs in child job to be summarized for later queries
     * model: SessionsModel . current session
     */
    
    public Dep21(JobStepType0Model parent, JobStepType0Model child,List<SubSurfaceHeaders> subsToSummarize,SessionModel model) {
        this.session=model;
        this.parent = (JobStepType2Model) parent;
        this.child = (JobStepType1Model)child;
        JobStep parentjs=jserv.getJobStep(this.parent.getId());
        Sessions sess=sessServ.getSessions(session.getId());
        SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
        DoubtType dtime=dstypeServ.getDoubtTypeByName(Doubt.doubtTime);
        
        
        JobStep childjs=jserv.getJobStep(this.child.getId());
        SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
        
        List<Headers> hdrsToBeUpdated=new ArrayList<>();
        
        
         if(parent.getType().equals(2L) && child.getType().equals(1L)){
            
        
        //If Child has been traversed then return.   Create a "traversed" flag in JobStepModel and set in each time the node is returning "upwards". i.e it and all of its descendants have been traversed, set its "traversed" flag to True
        Boolean passTraceCounts=true;            //defualt QC status is false
        Boolean passTimeStamps=true;
        //Boolean insightFail=false;
        
        
        /*
        Inherit the parents doubts .
        If the child's doubts are in the state "Y" and parent in "O" then set child to "Y"
        if child is "N or O" and parent is Y then child set to Y
        
        */
        
             if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): called");
       
        
        
        
          //this.child.setDependency(Boolean.FALSE);
              if(Debug) System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() Calculating subs in parent and child");
            
       
        Set<SubSurfaceHeaders> psubs;
         Set<SubSurfaceHeaders> csubs;
         if(subsToSummarize==null){                 //for initial summary
             calculateSubsInJob(this.child);
            calculateSubsInJob(this.parent);
             psubs=this.parent.getSubsurfacesInJob();
             csubs=this.child.getSubsurfacesInJob();
         }else{                                     //for later summaries
            // psubs=new HashSet<>(subsToSummarize);
            // csubs=new HashSet<>(subsToSummarize);
            
            psubs=lookupSubsFromMap(this.parent,subsToSummarize);
             csubs=lookupSubsFromMap(this.child,subsToSummarize);
         }
             
            if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): size of child and parent subs: "+csubs.size()+" : "+psubs.size());
            
         List<VolumeSelectionModelType1> cVolList=this.child.getVolList();
         List<VolumeSelectionModelType2> pVolList=this.parent.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            if(subsToSummarize==null){
                next.setDependency(Boolean.FALSE);   //first set all the volumes to false. then check each one below . Only to be done for the first summary
            } 
            
        }
        
         
         
         for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders c = iterator.next();
            VolumeSelectionModelType1 targetVol=new VolumeSelectionModelType1(1L,this.child);
            
            VolumeSelectionModelType2 refVol=new VolumeSelectionModelType2(2L,this.parent);
            SequenceHeaders targetSeq=new SequenceHeaders();
            SubSurfaceHeaders targetSub=c;
             if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() Checking dependency(): parent:child: "+this.parent.getJobStepText()+" <-> "+this.child.getJobStepText()+" sub: "+targetSub.getSubsurface()+" dependency: "+targetSub.dependencyProperty().get());
            SubSurfaceHeaders refSub=new SubSurfaceHeaders();
            SequenceHeaders refSeq=new SequenceHeaders();
            
            
            //Finding the volume in the child which contains the current sub...i.e  targetSub=c
                        for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
                           if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): Checking if Job: "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                              if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVol=vc;
                                targetVol.setVolumeType(vc.getVolumeType());
                                break;
                            }
                 
                        }
                        
                       
                        
            //finding the sequence to which this sub belongs to.
                        
                        if(targetVol!=null){
                            HeadersModel hmod=targetVol.getHeadersModel();
                            List<SequenceHeaders> seqList=hmod.getSequenceListInHeaders();
                            
                for (SequenceHeaders seq : seqList) {
                    if(targetSub.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeq=seq;
                          if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): "+this.child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
                    }
                    
                   
                }
                
                /* //Alternatively
                
                targetSeq=targetSub.getSequenceHeaders();
                */
                            
                        }else
                        {
                                try {
                                    throw new Exception("Subline: "+targetSub.getSubsurface()+" :not found in any of the child job: "+child.getJobStepText()+" : volumes!!");
                                } catch (Exception ex) {
                                    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                      
                 
                        
                        //Find the refsub in the parent that corresponds to the targetSub..basically the same subline in the parent. 
                        //Also find the seq to which it corresponds to ..refseq contains refsub
                        for (Iterator<SubSurfaceHeaders> iterator1 = psubs.iterator(); iterator1.hasNext();) {
                          SubSurfaceHeaders p = iterator1.next();
                          if(csubs.contains(p))
                          {
                              if(p.equals(c)){
                              if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): "+p.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                              refSub=p;
                              refSeq=refSub.getSequenceHeader();
                              
                              
                              break;
                              }
                             
                          }
                        
                        }
                        
                        
                        //Find the volume that contains refSub in the parent..refVol
                        
                         for (Iterator<VolumeSelectionModelType2> iterator1 = pVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType2 vp = iterator1.next();
                            Set<SubSurfaceHeaders> vpSub=vp.getSubsurfaces();
                           if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                            if(vpSub.contains(refSub)){
                              if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                                refVol=vp;
                                refVol.setVolumeType(vp.getVolumeType());
                                break;
                            }
                 
                        }
                        
                        
                         
                         //begin  the definitions of the dependency 
                         
                         
                        if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                 //trace mismatch between parent and child  
                                passTraceCounts=false;
                               
                                errorMessage+="Child "+targetSub.getSubsurface()+" in child job: "+this.child.getJobStepText()+" has more traces than in its parent job "+this.parent.getJobStepText();
                                if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() "+"Sub "+targetSub.getSubsurface()+" in child job: "+this.child.getJobStepText()+" have non-equal traces compared to its parent job "+this.parent.getJobStepText());
                                }
                        else{
                            passTraceCounts=true;                           //parent has more or equal number of traces as child
                            
                        }
                        
                        Long refTime=Long.valueOf(refSub.getTimeStamp());
                        Long targetTime=Long.valueOf(targetSub.getTimeStamp());
                       
                        Boolean laterTimestamp=false;
                        Boolean sameTimestamp=false;
                        
                        if(refTime > targetTime){         //parent created after child
                            passTimeStamps=false;
                            if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>():  Sub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+" created after the child job: "+this.child.getJobStepText());
                            errorMessage+="\nSub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+"("+refSub.getTimeStamp()+") created after the child job: "+this.child.getJobStepText()+" ("+targetSub.getTimeStamp()+")";
                            laterTimestamp=true;
                        }
                        if(refTime.equals(targetTime)){         //parent created after child
                            passTimeStamps=false;
                            if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>():  sub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+" has the same timestamp as in the child job: "+this.child.getJobStepText());
                            errorMessage+="\nSub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+"("+refSub.getTimeStamp()+") has the same timestamp as in the child job: "+this.child.getJobStepText()+" ("+targetSub.getTimeStamp()+")";
                            sameTimestamp=true;
                        }
                        if(refTime<targetTime){
                            passTimeStamps=true;
                        }
                        
                        
                        
                        // End of definition
                        
                        
                        
                        //Start setting flags based on pass/fail of dependencies
                               passDependency=passTimeStamps && passTraceCounts;
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  passTimeStamps : "+passTimeStamps);
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  passTraceCounts: "+passTraceCounts);
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  passDependency : "+passDependency);
                               
                        
                        //During the current walk. if the node(parent or child) was traversed before , then its dependency flag must have been set to true or false.
                        //   FALSE takes precedence over TRUE  in the case of qc.(Dependency)
                        // So save the existing dependency state and do a logic AND with the current dependency . the result is the new dependency state of the node
                        
                               Boolean existingParentDependency=this.parent.getDependency().get();
                                if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  existingparentDependency for parent:"+ this.parent.getJobStepText()+" : "+existingParentDependency);
                               
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  existingTargetVolDependency for volume : "+refVol.getLabel()+" : "+refVol.getDependency().get() );
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() existingRefSeqDependency: for seq: "+refSeq.getSequenceNumber()+" : "+refSeq.dependencyProperty().get() );
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() existingRefSubDependency: for sub:"+refSub.getSubsurface()+" : "+refSub.dependencyProperty().get() );
                               
                               
                               this.parent.setDependency(existingParentDependency && passDependency);
                               refSub.setDependency(refSub.dependencyProperty().get() && passDependency);
                               refSeq.setDependency(refSeq.dependencyProperty().get() && passDependency);
                               refVol.setDependency(refVol.getDependency().get() && passDependency);
                               
                               Boolean existingTargetVolDependency=targetVol.getDependency().get();
                               Boolean existingChildDependency=this.child.getDependency().get();
                               
                               
                               
                                
                               
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  existingChildDependency for child: "+this.child.getJobStepText()+" : "+existingChildDependency);
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>()  existingTargetVolDependency for volume : "+targetVol.getLabel()+" : "+existingTargetVolDependency);
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() existingTargetSeqDependency for seq: "+targetSeq.getSequenceNumber()+" : "+targetSeq.dependencyProperty().get() );
                               if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>() existingTargetSubDependency for sub: "+targetSub.getSubsurface()+" : "+targetSub.dependencyProperty().get() );                             
                               
                               
                               this.child.setDependency(existingChildDependency && passDependency);
                               targetVol.setDependency(existingTargetVolDependency && passDependency);
                               
                         //Similar logic for targetsub,targetSeq,refSub,refSeq,refVol
                               
                               
                         /*targetSeq.setQcAlert(targetSeq.dependencyProperty().get() && passDependency);
                         targetSub.setQcAlert(targetSub.dependencyProperty().get() && passDependency);*/
                              
                               targetSeq.setDependency(targetSeq.dependencyProperty().get() && passDependency);
                               
                               targetSub.setDependency(targetSub.dependencyProperty().get() && passDependency);
                               
                               //Comment the below three lines if the dependencies need to be monitored one way and the doubt needs to be monitored both ways.
                               //if both the dependencies and the doubt needs to be monitored for both parent and child, then leave uncommented
                               
                               
                               
                               if(passTimeStamps){              //parent and child meets time definitions. 
                                //If this happens, then check if  entries exists for this under the db table for which the error messages are the same. i.e the common doubt. If yes then delete them. it No, then do nothing
                                //set the doubt status of the node.sub and seq to "N"
                                //remove the doubt from the doubtmap of the node,sub and seq
                                
                                    
                                   //DoubtType dtrace=dstypeServ.getDoubtTypeByName(Doubt.doubtTraces);
                                   
                                   if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): inside the failed block: ");
                                   //<--Prerequired Parent block start
                                   //JobStep parentjs=jserv.getJobStep(parent.getId());
                                    //SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
                                    Subsurface rsub=subserv.getSubsurfaceObjBysubsurfacename(refSub.getSubsurface());
                                    Volume refV=vserv.getVolume(refVol.getId());
                                    List<Headers> pdhrl=hserv.getHeadersFor(refV, rsub);
                                    Headers phdr=pdhrl.get(0);
                                    phdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                    
                                    hdrsToBeUpdated.add(phdr);
                                 //   List<DoubtStatus> parentChildDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtime,phdr);   //should be of size 1 or 0
                                 
                                   //<--Prerequired Parent block end
                                   
                                   
                                   
                                   
                                   //<<--Prerequired Block for child! start...
                                   
                                    //obStep childjs=jserv.getJobStep(this.child.getId());
                                    //SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
                                    Subsurface tsub=subserv.getSubsurfaceObjBysubsurfacename(targetSub.getSubsurface());
                                    
                                    Volume tarv=vserv.getVolume(targetVol.getId());
                                    List<Headers> hdr=hserv.getHeadersFor(tarv, tsub);               //should be of size 1
                                    Headers chdr=hdr.get(0);
                                    chdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                    hdrsToBeUpdated.add(chdr);
                                   // List<DoubtStatus> childDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(childSsd, dtime,chdr);   //should be of size 1 or 0
                                  
                                    
                                   
                                    
                                    //<--Prerequired Block for child end..
                                     List<DoubtStatus> parentDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dtime,phdr);   //should be of size 1 or 0
                                    
                                    
                                    
                                    
                                    if(parentDoubtstatTime.size()==1){  //means an entry exists for the parent-child doubt of type time
                                       DoubtStatus pds=parentDoubtstatTime.get(0);
                                       String perr=pds.getErrorMessage();
                                       
                                       dsServ.deleteDoubtStatus(pds.getIdDoubtStatus());  //delete it since the doubt no longer exists
                                       
                                       
                                       
                                      
                                    }
                                    
                                    //this.parent.getDoubt().setStatus("N");
                                    //refSeq.getDoubt().setStatus("N");
                                    //refSub.getDoubt().setStatus("N");
                                    
                                    this.parent.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    refSeq.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    refSub.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    setSeqDoubtStatus(refSub);
                                    //this.child.getDoubt().setStatus("N");
                                    //targetSeq.getDoubt().setStatus("N");
                                    //targetSub.getDoubt().setStatus("N");
                                    
                                    this.child.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    targetSeq.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    targetSub.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTime);
                                    setSeqDoubtStatus(targetSub);
                                    
                                    
                                    
                                  
                                
                                   
                               }
                               
                               if(passTraceCounts){             //parent and child meets trace definitions
                                    //If this happens, then check if  entries exists for this under the db table for which the error messages are the same. i.e the common doubt. If yes then delete them. it No, then do nothing
                                //set the doubt status of the node.sub and seq to "N"
                                //remove the doubt from the doubtmap of the node,sub and seq
                                
                                   //Sessions sess=sessServ.getSessions(session.getId());
                                   //DoubtType dtime=dstypeServ.getDoubtTypeByName(Doubt.doubtTime);
                                   DoubtType dtrace=dstypeServ.getDoubtTypeByName(Doubt.doubtTraces);
                                   
                                   if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): inside the failed block: ");
                                   //<--Prerequired Parent block start
                                   //JobStep parentjs=jserv.getJobStep(this.parent.getId());
                                    //SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
                                    Subsurface rsub=subserv.getSubsurfaceObjBysubsurfacename(refSub.getSubsurface());
                                    Volume refV=vserv.getVolume(refVol.getId());
                                    List<Headers> pdhrl=hserv.getHeadersFor(refV, rsub);
                                    Headers phdr=pdhrl.get(0);
                                    phdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                    hdrsToBeUpdated.add(phdr);
                                   // List<DoubtStatus> parentDoubtstatTrace= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtrace,phdr);   //should be of size 1 or 0
                                   //<--Prerequired Parent block end
                                   
                                   
                                   
                                   
                                   //<<--Prerequired Block for child! start...
                                   
                                    //JobStep childjs=jserv.getJobStep(this.child.getId());
                                    Subsurface tsub=subserv.getSubsurfaceObjBysubsurfacename(targetSub.getSubsurface());
                                    //SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
                                    Volume tarv=vserv.getVolume(targetVol.getId());
                                    List<Headers> hdr=hserv.getHeadersFor(tarv, tsub);               //should be of size 1
                                    Headers chdr=hdr.get(0);
                                    chdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                    hdrsToBeUpdated.add(chdr);
                                  //  List<DoubtStatus> childDoubtstatTrace= dsServ.getDoubtStatusListForJobInSession(childSsd, dtrace,chdr);   //should be of size 1 or 0
                                  List<DoubtStatus> parentDoubtstatTrace= dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dtrace,phdr);   //should be of size 1 or 0
                                    
                                   
                                    
                                    //<--Prerequired Block for child end..
                                    
                                    
                                    
                                    
                                    
                                    if(parentDoubtstatTrace.size()==1){  //means an entry exists for the parent-child for this 
                                       DoubtStatus pds=parentDoubtstatTrace.get(0);
                                      // String perr=pds.getErrorMessageList();
                                       
                                       dsServ.deleteDoubtStatus(pds.getIdDoubtStatus());
                                       
                                       
                                       
                                      
                                    }
                                    
                                    //this.parent.getDoubt().setStatus("N");
                                    //refSeq.getDoubt().setStatus("N");
                                    //refSub.getDoubt().setStatus("N");
                                    
                                    this.parent.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    refSeq.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    refSub.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    setSeqDoubtStatus(refSub);
                                     //this.child.getDoubt().setStatus("N");
                                    //targetSeq.getDoubt().setStatus("N");
                                    //targetSub.getDoubt().setStatus("N");
                                    
                                    this.child.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    targetSeq.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    targetSub.getDoubt().removeFromDoubtMap(this.parent,this.child,Doubt.doubtTraces);
                                    setSeqDoubtStatus(targetSub);
                                    
                                    
                                    
                                    //<--Block for parent end..
                                
                                   
                               }
                               
                               
                               
                               
                               
                               
                               if(!passDependency){
                                   //check if this doubt for each dependency failure has already been overridden in the database table. if not then set the doubts to "Y". if they are overridden, set the values to "O".
                                   // if there is no entry in the table, then set the values to "Y" and write to table
                                   
                                   
                                   //Stuff required to make the query!
                                   
                                   //Sessions sess=sessServ.getSessions(session.getId());
                                   //DoubtType dtime=dstypeServ.getDoubtTypeByName(Doubt.doubtTime);
                                   DoubtType dtrace=dstypeServ.getDoubtTypeByName(Doubt.doubtTraces);
                                   
                                   if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): inside the failed block: ");
                                   //<<--Block for child! start...
                                   
                                    //JobStep childjs=jserv.getJobStep(this.child.getId());
                                    Subsurface tsub=subserv.getSubsurfaceObjBysubsurfacename(targetSub.getSubsurface());
                                    //SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
                                    Volume tarv=vserv.getVolume(targetVol.getId());
                                    List<Headers> hdr=hserv.getHeadersFor(tarv, tsub);               //should be of size 1
                                    Headers chdr=hdr.get(0);
                                    chdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                  hdrsToBeUpdated.add(chdr);
                                   // List<DoubtStatus> childDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(childSsd, dtime,chdr);   //should be of size 1 or null
                                  //  List<DoubtStatus> childDoubtStatTrace= dsServ.getDoubtStatusListForJobInSession(childSsd, dtrace,chdr);   //should be of size 1 or null
                                    
                                 
                                  
                                   
                                   
                                   //start of block for parent
                                   
                                    //JobStep parentjs=jserv.getJobStep(parent.getId());
                                    //SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
                                    Subsurface rsub=subserv.getSubsurfaceObjBysubsurfacename(refSub.getSubsurface());
                                    Volume refV=vserv.getVolume(refVol.getId());
                                    List<Headers> pdhrl=hserv.getHeadersFor(refV, rsub);
                                    Headers phdr=pdhrl.get(0);
                                    hdrsToBeUpdated.add(phdr);
                                    phdr.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                    //List<DoubtStatus> parentChildDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtime,phdr);   //should be of size 1 or null
                                    //List<DoubtStatus> parentChildDoubtStatTrace= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtrace,phdr);   //should be of size 1 or null
                                    List<DoubtStatus> parentChildDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dtime,phdr); 
                                    List<DoubtStatus> parentChildDoubtStatTrace= dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dtrace,phdr);   //should be of size 1 or null
                                    List<String> doubtMessageInChildSub=new ArrayList<>(); 
                                      
                                       
                                       
                                       if(!passTimeStamps){ //if passtimeStamps is false  ..failed timestamp checks
                                           
                                           if(parentChildDoubtstatTime.isEmpty()){         //no entry for the parent-child exists .. time doubt in table  .so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtime);
                                               ds.setHeaders(phdr);
                                               ds.setParentSessionDetails(parentSsd);
                                               ds.setChildSessionDetailsId(childSsd.getIdSessionDetails());
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               
                                               
                                               
                                               if(laterTimestamp){
                                                   ds.setErrorMessage(refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                   +" ("+targetSub.getTimeStamp()+")");
                                               }if(sameTimestamp){
                                                   ds.setErrorMessage(refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSub.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                   +" ("+targetSub.getTimeStamp()+")");
                                               }
                                               
                                               
                                               dsServ.createDoubtStatus(ds);
                                               
                                                    this.parent.getDoubt().setStatus("Y");
                                                    //refSeq.getDoubt().setStatus("Y");
                                                    refSub.getDoubt().setStatus("Y");
                                                    setSeqDoubtStatus(refSub);
                                                    

                                                    this.child.getDoubt().setStatus("Y");
                                                    //targetSeq.getDoubt().setStatus("Y");
                                                    targetSub.getDoubt().setStatus("Y");
                                                    setSeqDoubtStatus(targetSub);
                                                    
                                                                if(laterTimestamp){
                                                           this.parent.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSeq.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSeq.getTimeStamp()+")");
                                                           refSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSeq.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSeq.getTimeStamp()+")");
                                                       refSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSub.getTimeStamp()+")");


                                                              this.child.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSeq.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSeq.getTimeStamp()+")");
                                                              targetSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSeq.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSeq.getTimeStamp()+")");
                                                              targetSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSub.getTimeStamp()+")");
                                                              
                                                               String err=refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has a later timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                               +" ("+targetSub.getTimeStamp()+")";

                                                               doubtMessageInChildSub.add(err);
                                                              
                                                             // refSub.setErrorMessageList(doubtMessageInChildSub);
                                                             // targetSub.setErrorMessageList(doubtMessageInChildSub);


                                                       }
                                           
                                                        if(sameTimestamp){
                                                            this.parent.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSeq.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSeq.getTimeStamp()+")");
                                                            refSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSeq.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSeq.getTimeStamp()+")");
                                                        refSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSub.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSub.getTimeStamp()+")");

                                                                this.child.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSeq.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSeq.getTimeStamp()+")");
                                                               targetSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSeq.getSequenceNumber()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSeq.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSeq.getTimeStamp()+")");
                                                               targetSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSub.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSub.getTimeStamp()+")");

                                                                String err=refSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has the same timestamp ("+refSub.getTimeStamp()+") as the one in the child job: "+child.getJobStepText()
                                                                +" ("+targetSub.getTimeStamp()+")";
                                                                
                                                         doubtMessageInChildSub.add(err);
                                                        // refSub.setErrorMessageList(doubtMessageInChildSub);
                                                        // targetSub.setErrorMessageList(doubtMessageInChildSub);


                                                        } 
                                                   
                                                    
                                            }   
                                           else{            //When there is an entry in the db 
                                                String status=parentChildDoubtstatTime.get(0).getStatus();
                                                String error=parentChildDoubtstatTime.get(0).getErrorMessage();
                                                String dtype=parentChildDoubtstatTime.get(0).getDoubtType().getName();
                                                this.parent.getDoubt().setStatus(status);
                                                //refSeq.getDoubt().setStatus(status);
                                                refSub.getDoubt().setStatus(status);
                                                setSeqDoubtStatus(refSub);
                                                    
                                                this.child.getDoubt().setStatus(status);
                                                //targetSeq.getDoubt().setStatus(status);
                                                targetSub.getDoubt().setStatus(status);
                                                setSeqDoubtStatus(targetSub);
                                                    
                                                this.parent.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                refSeq.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                refSub.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error); 
                                                        
                                                this.child.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                targetSeq.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                targetSub.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error); 
                                                
                                                 
                                                doubtMessageInChildSub.add(error);
                                                //refSub.setErrorMessageList(doubtMessageInChildSub);
                                               // targetSub.setErrorMessageList(doubtMessageInChildSub);
                                                    
                                           }
                                           
                                           
                                           
                                       }
                                       
                                       if(!passTraceCounts){ //if traces is false  ..failed tracecounts checks
                                           
                                           if(parentChildDoubtStatTrace.isEmpty()){         //no entry for the parent-chil exists..trace doubt in table..so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtrace);
                                               ds.setHeaders(phdr);
                                               ds.setParentSessionDetails(parentSsd);
                                               ds.setChildSessionDetailsId(childSsd.getIdSessionDetails());
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               ds.setErrorMessage(refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has ("+refSub.getTraceCount()+") traces while the one in the child job: "+this.child.getJobStepText()
                                                   +" has ("+targetSub.getTraceCount()+") traces");
                                               
                                               
                                               dsServ.createDoubtStatus(ds);  
                                               
                                           this.parent.getDoubt().setStatus("Y");
                                           //refSeq.getDoubt().setStatus("Y");
                                           refSub.getDoubt().setStatus("Y");
                                           setSeqDoubtStatus(refSub);
                                           
                                           this.child.getDoubt().setStatus("Y");
                                           //targetSeq.getDoubt().setStatus("Y");
                                           targetSub.getDoubt().setStatus("Y");
                                           setSeqDoubtStatus(targetSub);
                                           
                                           this.parent.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                           refSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                           refSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                           
                                           
                                           this.child.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                                  targetSeq.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                                  targetSub.getDoubt().addToDoubtMap(this.parent,this.child,Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                                  
                                                  String err=refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")";
                                                  
                                                  doubtMessageInChildSub.add(err);
                                                 // refSub.setErrorMessageList(doubtMessageInChildSub);
                                                 // targetSub.setErrorMessageList(doubtMessageInChildSub);
                                           
                                           
                                            }else{            //When there is an entry in the db 
                                                String status=parentChildDoubtStatTrace.get(0).getStatus();
                                                String error=parentChildDoubtStatTrace.get(0).getErrorMessage();
                                                String dtype=parentChildDoubtStatTrace.get(0).getDoubtType().getName();
                                                this.parent.getDoubt().setStatus(status);
                                                //refSeq.getDoubt().setStatus(status);
                                                refSub.getDoubt().setStatus(status);
                                                setSeqDoubtStatus(refSub);
                                                    
                                                this.child.getDoubt().setStatus(status);
                                                //targetSeq.getDoubt().setStatus(status);
                                                targetSub.getDoubt().setStatus(status);
                                                setSeqDoubtStatus(targetSub);
                                                    
                                                this.parent.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                refSeq.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                refSub.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error); 
                                                        
                                                this.child.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                targetSeq.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error);
                                                targetSub.getDoubt().addToDoubtMap(this.parent,this.child,dtype, error); 
                                                
                                                
                                                doubtMessageInChildSub.add(error);
                                                //refSub.setErrorMessageList(doubtMessageInChildSub);
                                                //targetSub.setErrorMessageList(doubtMessageInChildSub);
                                                
                                           }
                                           
                                           
                                           
                                           
                                                  
                                                  
                                                  
                                           
                                       }
                                       
                                       
                                       
                                       
                                   //}
        
        

                                   //end of parent Block
                                   
                                  
                               }
                              
                               if(Debug)System.out.println("fend.session.SessionController.D21(): ErrorMessage "+errorMessage);
                               //targetSeq.setErrorMessage(errorMessage);
                               //targetSub.setErrorMessage(errorMessage);
                        
                        
                       
        }


         //At the end of this loop
             if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.<init>(): at the end of the iteration for "+parent.getJobStepText()+" : "+child.getJobStepText());
            for (Iterator<SubSurfaceHeaders> iterator = psubs.iterator(); iterator.hasNext();) {
                 SubSurfaceHeaders next = iterator.next();
                 System.out.println(parent.getJobStepText()+" :    "+next.getSubsurface()+" :dep: "+next.isDependency()+" :doubt: "+next.getDoubt().isDoubt()+ " :stat: "+next.getDoubt().getStatus());
                 
             }for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
                 SubSurfaceHeaders next = iterator.next();
                 System.out.println(child.getJobStepText()+" :    "+next.getSubsurface()+" :dep: "+next.isDependency()+" :doubt: "+next.getDoubt().isDoubt()+ " :stat: "+next.getDoubt().getStatus());
                 
             }
       
         
        updateSummaryTimes(hdrsToBeUpdated);
         
         
         
    }    
        
        
    }
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.calculateSubsInJob(): entered");
        if(job instanceof JobStepType1Model){                   //for 2D case (denoise etc)
            List<VolumeSelectionModelType1> volList=job.getVolList();
        Set<SubSurfaceHeaders> subsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModelType1> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurfaceHeaders> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            
            
        }
        
        
        
        
        job.setSubsurfacesInJob(subsInJob);
        /*for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
        SubSurfaceHeaders subinJob = iterator.next();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        
       if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
        return subsInJob;
        }
        
         if(job instanceof JobStepType2Model){                   //for 2D case (Segd Load node)
            List<VolumeSelectionModelType2> volList=job.getVolList();
        Set<SubSurfaceHeaders> subsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModelType2> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType2 vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurfaceHeaders> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            
            
        }
        
        
        
        
        job.setSubsurfacesInJob(subsInJob);
        /*for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
        SubSurfaceHeaders subinJob = iterator.next();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        
        if(Debug)System.out.println("fend.session.node.jobs.dependencies.Dep21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
        return subsInJob;
        }
        
        
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
    }

    public SessionModel getSession() {
        return session;
    }

    public void setSession(SessionModel session) {
        this.session = session;
    }
    
    private void setSeqDoubtStatus(SubSurfaceHeaders chsub) {
        SequenceHeaders seq=chsub.getSequenceHeader();
        boolean seqover=false;
        boolean seqno=true;
        boolean seqyes=false;
        
        List<SubSurfaceHeaders> subs=seq.getSubsurfaces();
        for (Iterator<SubSurfaceHeaders> iterator = subs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders next = iterator.next();
            boolean over=true;
            boolean no=false;
            boolean yes=true;
            if(next.getDoubt().getStatus().equals("O")){
                over=true;
                no=false;
                yes=false;
            }
            if(next.getDoubt().getStatus().equals("N")){
                over=false;
                no=true;
                yes=false;
            }
            if(next.getDoubt().getStatus().equals("Y")){
                over=false;
                no=false;
                yes=true;
            }
            
            seqover= seqover || over;       //if atleast one of the subs is overriden then the seq is overriden
            seqno=seqno && no;              //if all of the subs are NO then the seq is No;
            seqyes=seqyes || yes;           //if atleast one of the subs is doubtful (Y) then the seq is doubtful (Y)
            
            
        }
        
        if(seqyes){
            seq.getDoubt().setStatus("Y");
            seq.getDoubt().setDoubt(true);
            return;
        }
        
        if(seqover){
            seq.getDoubt().setStatus("O");
            seq.getDoubt().setDoubt(true);
            return;
        }
        if(seqno){
            seq.getDoubt().setStatus("N");
            seq.getDoubt().setDoubt(false);
        }
     
        chsub.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
    }

    private void updateSummaryTimes(List<Headers> hdrsToBeUpdated) {
        for (Iterator<Headers> iterator = hdrsToBeUpdated.iterator(); iterator.hasNext();) {
            
            Headers next = iterator.next();
            hserv.updateHeaders(next.getIdHeaders(), next);
        }
    }
    
    
    
    private Set<SubSurfaceHeaders> lookupSubsFromMap(JobStepType0Model job, List<SubSurfaceHeaders> subsToSummarize) {
        
        if(job instanceof JobStepType2Model){                   //for 2D case
            List<VolumeSelectionModelType2> volList=job.getVolList();
        Set<SubSurfaceHeaders> correspondingSubsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModelType2> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType2 vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                                   
                    Map<String,SubSurfaceHeaders>map=vol.getSubsurfaceNameSubSurfaceHeaderMap();
                    for (Iterator<SubSurfaceHeaders> iterator1 = subsToSummarize.iterator(); iterator1.hasNext();) {
                        SubSurfaceHeaders requiredSub = iterator1.next();
                        correspondingSubsInJob.add(map.get(requiredSub.getSubsurface()));
                        
                    }
                }
            
            
            
        }
       // job.setSubsurfacesInJob(correspondingSubsInJob);
        /*for (Iterator<SubSurface> iterator = correspondingSubsInJob.iterator(); iterator.hasNext();) {
        SubSurfaceHeaders subinJob = iterator.next();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        
        System.out.println("mid.doubt.dependencies.Dep11.lookupSubsFromMap(): returning sublist of size: "+correspondingSubsInJob.size());
        return correspondingSubsInJob;
        }
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
    }
    
    
}
