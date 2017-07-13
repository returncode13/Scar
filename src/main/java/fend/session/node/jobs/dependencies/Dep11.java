 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.dependencies;

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
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import midend.doubt.Doubt;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Dependency class between parent=JobStepType1 and child=JobStepType1 nodes
 * 
 */
public class Dep11 {
    JobStepType1Model parent;
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
    
    
    SessionModel session;
           
    public Dep11(JobStepType0Model parent, JobStepType0Model child,SessionModel model) {
        this.session=model;
        this.parent = (JobStepType1Model) parent;
        this.child = (JobStepType1Model)child;
        
         if(parent.getType().equals(1L) && child.getType().equals(1L)){
            
        
        //If Child has been traversed then return.   Create a "traversed" flag in JobStepModel and set in each time the node is returning "upwards". i.e it and all of its descendants have been traversed, set its "traversed" flag to True
        Boolean passTraceCounts=true;            //defualt QC status is false
        Boolean passTimeStamps=true;
        //Boolean insightFail=false;
        
        
        /*
        Inherit the parents doubts .
        If the child's doubts are in the state "Y" and parent in "O" then set child to "Y"
        if child is "N or O" and parent is Y then child set to Y
        
        */
        
             System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>(): called");
        String childDoubtStatus= this.child.getDoubt().getStatus();
        String parentDoubtStatus=this.parent.getDoubt().getStatus();
                System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>() parentDoubtStatus: "+parentDoubtStatus+" childDoubtStatus: "+childDoubtStatus);
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("O") ) this.child.getDoubt().setStatus("O");
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("Y") ) this.child.getDoubt().setStatus("Y");
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("N") ) this.child.getDoubt().setStatus("O");
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("O") ) this.child.getDoubt().setStatus("Y");
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("Y") ) this.child.getDoubt().setStatus("Y");
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("N") ) this.child.getDoubt().setStatus("Y");
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("O") ) this.child.getDoubt().setStatus("O");
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("Y") ) this.child.getDoubt().setStatus("Y");
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("N") ) this.child.getDoubt().setStatus("N");
        
        
        
          this.child.setDependency(Boolean.FALSE);
               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>() Calculating subs in parent and child");
            calculateSubsInJob(this.child);
            calculateSubsInJob(this.parent);
       
         Set<SubSurfaceHeaders> psubs=this.parent.getSubsurfacesInJob();
         Set<SubSurfaceHeaders> csubs=this.child.getSubsurfacesInJob();
            System.out.println("fend.session.SessionController.D11(): size of child and parent subs: "+csubs.size()+" : "+psubs.size());
            
         List<VolumeSelectionModelType1> cVolList=this.child.getVolList();
         List<VolumeSelectionModelType1> pVolList=this.parent.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
        
         
         
         for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders c = iterator.next();
            VolumeSelectionModelType1 targetVol=new VolumeSelectionModelType1(1L,this.child);
            VolumeSelectionModelType1 refVol=new VolumeSelectionModelType1(1L,this.parent);
            SequenceHeaders targetSeq=new SequenceHeaders();
            SubSurfaceHeaders targetSub=c;
            SubSurfaceHeaders refSub=new SubSurfaceHeaders();
            SequenceHeaders refSeq=new SequenceHeaders();
            
            
            //Finding the volume in the child which contains the current sub...i.e  targetSub=c
                        for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
                           System.out.println("fend.session.SessionController.D11(): Checking if Job: "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                              System.out.println("fend.session.SessionController.D11(): SUCCESS!!Job: "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
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
                          System.out.println("fend.session.SessionController.D11(): SUCCESS!!Job: "+this.child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
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
                              System.out.println("fend.session.SessionController.D11(): SUCCESS!!found : "+p.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                              refSub=p;
                              refSeq=refSub.getSequenceHeader();
                              
                              
                              break;
                              }
                             
                          }
                        
                        }
                        
                        
                        //Find the volume that contains refSub in the parent..refVol
                        
                         for (Iterator<VolumeSelectionModelType1> iterator1 = pVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vp = iterator1.next();
                            Set<SubSurfaceHeaders> vpSub=vp.getSubsurfaces();
                           System.out.println("fend.session.SessionController.D11(): Checking if Job: "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                            if(vpSub.contains(refSub)){
                              System.out.println("fend.session.SessionController.D11(): SUCCESS!!Job: "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                                refVol=vp;
                                refVol.setVolumeType(vp.getVolumeType());
                                break;
                            }
                 
                        }
                        
                        
                         
                         //begin  the definitions of the dependency 
                         
                         
                        if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                 //trace mismatch between parent and child  
                                passTraceCounts=false;
                               
                                errorMessage+="Child "+targetSub.getSubsurface()+" in child job: "+this.child.getJobStepText()+" has more traces than in its parent job "+this.parent.getJobStepText();
                                System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>() "+"Sub "+targetSub.getSubsurface()+" in child job: "+this.child.getJobStepText()+" have non-equal traces compared to its parent job "+this.parent.getJobStepText());
                                }
                        else{
                            passTraceCounts=true;                           //parent has more or equal number of traces as child
                            
                        }
                        
                        
                        if(refSub.getTimeStamp().compareToIgnoreCase(targetSub.getTimeStamp())==-1){         //parent created after child
                            passTimeStamps=false;
                            System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>():  Sub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+" created after the child job: "+this.child.getJobStepText());
                            errorMessage+="\nSub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+"("+refSub.getTimeStamp()+") created after the child job: "+this.child.getJobStepText()+" ("+targetSub.getTimeStamp()+")";
                        }
                        if(refSub.getTimeStamp().compareToIgnoreCase(targetSub.getTimeStamp())==0){         //parent created after child
                            passTimeStamps=false;
                            System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>():  sub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+" has the same timestamp as in the child job: "+this.child.getJobStepText());
                            errorMessage+="\nSub: "+refSub.getSubsurface()+ " in parent job: "+this.parent.getJobStepText()+"("+refSub.getTimeStamp()+") has the same timestamp as in the child job: "+this.child.getJobStepText()+" ("+targetSub.getTimeStamp()+")";
                        }
                        if(refSub.getTimeStamp().compareToIgnoreCase(targetSub.getTimeStamp())==1){
                            passTimeStamps=true;
                        }
                        
                        
                        // End of definition
                        
                        
                        
                        //Start setting flags based on pass/fail of dependencies
                               passDependency=passTimeStamps && passTraceCounts;
                               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  passTimeStamps : "+passTimeStamps);
                               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  passTraceCounts: "+passTraceCounts);
                               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  passDependency : "+passDependency);
                               
                        
                        //During the current walk. if the node(parent or child) was traversed before , then its dependency flag must have been set to true or false.
                        //   FALSE takes precedence over TRUE  in the case of qc.(Dependency)
                        // So save the existing dependency state and do a logic AND with the current dependency . the result is the new dependency state of the node
                        
                               Boolean existingParentDependency=this.parent.getDependency().get();
                                System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  existingparentDependency : "+existingParentDependency);
                               this.parent.setDependency(existingParentDependency && passDependency);
                               
                               Boolean existingChildDependency=this.child.getDependency().get();
                               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  existingChildDependency : "+existingChildDependency);
                               this.child.setDependency(existingChildDependency && passDependency);
                               
                               Boolean existingTargetVolDependency=targetVol.getDependency().get();
                               System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>()  existingTargetVolDependency : "+existingTargetVolDependency);
                               targetVol.setDependency(existingTargetVolDependency && passDependency);
                               
                         //Similar logic for targetsub,targetSeq,refSub,refSeq,refVol
                               
                               
                         /*targetSeq.setQcAlert(targetSeq.dependencyProperty().get() && passDependency);
                         targetSub.setQcAlert(targetSub.dependencyProperty().get() && passDependency);*/
                         
                               targetSeq.setDependency(targetSeq.dependencyProperty().get() && passDependency);
                               targetSub.setDependency(targetSub.dependencyProperty().get() && passDependency);
                               
                               //Comment the below three lines if the dependencies need to be monitored one way and the doubt needs to be monitored both ways.
                               //if both the dependencies and the doubt needs to be monitored for both parent and child, then leave uncommented
                               
                               refSub.setDependency(refSub.dependencyProperty().get() && passDependency);
                               refSeq.setDependency(refSeq.dependencyProperty().get() && passDependency);
                               refVol.setDependency(refVol.getDependency().get() && passDependency);
                               
                               if(!passDependency){
                                   //check if this doubt for each dependency failure has already been overridden in the database table. if not then set the doubts to "Y". if they are overridden, set the values to "O".
                                   // if there is no entry in the table, then set the values to "Y" and write to table
                                   
                                   
                                   //Stuff required to make the query!
                                   
                                   Sessions sess=sessServ.getSessions(session.getId());
                                   DoubtType dtime=dstypeServ.getDoubtTypeByName(Doubt.doubtTime);
                                   DoubtType dtrace=dstypeServ.getDoubtTypeByName(Doubt.doubtTraces);
                                   
                                   System.out.println("fend.session.node.jobs.dependencies.Dep11.<init>(): inside the failed block: ");
                                   //<<--Block for child! start...
                                   
                                    JobStep childjs=jserv.getJobStep(child.getId());
                                    Subsurface tsub=subserv.getSubsurfaceObjBysubsurfacename(targetSub.getSubsurface());
                                    SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
                                    Volume tarv=vserv.getVolume(targetVol.getId());
                                    List<Headers> hdr=hserv.getHeadersFor(tarv, tsub);               //should be of size 1
                                    Headers chdr=hdr.get(0);
                                    List<DoubtStatus> childDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(childSsd, dtime,chdr);   //should be of size 1 or null
                                    List<DoubtStatus> childDoubtStatTrace= dsServ.getDoubtStatusListForJobInSession(childSsd, dtrace,chdr);   //should be of size 1 or null
                                    
                                    
                                    DoubtStatus chdstime;
                                    DoubtStatus chdstrace;
                                    boolean chdoverride1=false;
                                    boolean chdoverride2=false;
                                
                                    
                                   if(!childDoubtstatTime.isEmpty() ){           //an entry exists for the child 
                                       chdstime=childDoubtstatTime.get(0);
                                       if(chdstime.getStatus().equals("O")){
                                           chdoverride1=true;
                                       }
                                       if(chdstime.getStatus().equals("Y")){
                                           chdoverride1=false;
                                      }
                                   }
                                   if(!childDoubtStatTrace.isEmpty() ){         //an entry exists for the child 
                                       chdstrace=childDoubtStatTrace.get(0);
                                       if(chdstrace.getStatus().equals("O")){
                                           chdoverride2=true;
                                       }
                                       if(chdstrace.getStatus().equals("Y")){
                                           chdoverride2=false;
                                       }
                                   }
                                   
                                   boolean chdoverride= chdoverride1 && chdoverride2; //if both are overriden then state is chdoverride. else its Y
                                   if(chdoverride){
                                       targetSub.getDoubt().setStatus("O");
                                       targetSeq.getDoubt().setStatus("O");
                                       this.child.getDoubt().setStatus("O");
                                   }else{                   //this will correspond to when there is no entry for this doubt in the db or when atleast one of them hasn't been overriden
                                       targetSub.getDoubt().setStatus("Y");
                                       targetSeq.getDoubt().setStatus("Y");
                                       this.child.getDoubt().setStatus("Y");
                                      
                                       
                                       
                                       if(!passTimeStamps){ //if passtimeStamps is false  ..failed timestamp checks
                                           
                                           if(childDoubtstatTime.isEmpty()){         //no entry for the child exists .. time doubt in table  .so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtime);
                                               ds.setHeaders(chdr);
                                               ds.setSessionDetails(childSsd);
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               ds.setErrorMessage(targetSub.getSubsurface()+"in parent: "+parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+child.getJobStepText()
                                                   +" ("+targetSub.getTimeStamp()+")");
                                               
                                               
                                               dsServ.createDoubtStatus(ds);
                                            }
                                           
                                           
                                           targetSeq.getDoubt().addToDoubtMap(Doubt.doubtTime, targetSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTimeStamp()+")");
                                           targetSub.getDoubt().addToDoubtMap(Doubt.doubtTime, targetSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTimeStamp()+")");
                                       }
                                       
                                       if(!passTraceCounts){ //if traces is false  ..failed tracecounts checks
                                           
                                           if(childDoubtStatTrace.isEmpty()){         //no entry for the child exists..time doubt in table..so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtrace);
                                               ds.setHeaders(chdr);
                                               ds.setSessionDetails(childSsd);
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               ds.setErrorMessage(targetSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has ("+refSub.getTraceCount()+") traces while the one in the child job: "+this.child.getJobStepText()
                                                   +" has ("+targetSub.getTraceCount()+") traces");
                                               
                                               
                                               dsServ.createDoubtStatus(ds);  
                                            }
                                           
                                           targetSeq.getDoubt().addToDoubtMap(Doubt.doubtTraces, targetSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has ("+refSub.getTraceCount()+") traces while the one in the child job: "+this.child.getJobStepText()
                                                   +" has ("+targetSub.getTraceCount()+") traces");
                                           targetSub.getDoubt().addToDoubtMap(Doubt.doubtTraces, targetSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has ("+refSub.getTraceCount()+") traces while the one in the child job: "+this.child.getJobStepText()
                                                   +" has ("+targetSub.getTraceCount()+") traces");
                                       }
                                       
                                       
                                       
                                       
                                   }
                                   
                                   
                                   // END of block for child
                                   
                                   
                                   //start of block for parent
                                   
                                    JobStep parentjs=jserv.getJobStep(parent.getId());
                                    SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
                                    Subsurface rsub=subserv.getSubsurfaceObjBysubsurfacename(refSub.getSubsurface());
                                    Volume refV=vserv.getVolume(refVol.getId());
                                    List<Headers> pdhrl=hserv.getHeadersFor(refV, rsub);
                                    Headers phdr=pdhrl.get(0);
                                    List<DoubtStatus> parentDoubtstatTime= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtime,phdr);   //should be of size 1 or null
                                    List<DoubtStatus> parentDoubtStatTrace= dsServ.getDoubtStatusListForJobInSession(parentSsd, dtrace,phdr);   //should be of size 1 or null
                                    
                                    
                                    DoubtStatus pdstime;
                                    DoubtStatus pdstrace;
                                    boolean poverride1=false;
                                    boolean poverride2=false;
                                
                                    
                                   if(!parentDoubtstatTime.isEmpty() ){           //an entry exists for the parent 
                                       pdstime=parentDoubtstatTime.get(0);
                                       if(pdstime.getStatus().equals("O")){
                                           poverride1=true;
                                       }
                                       if(pdstime.getStatus().equals("Y")){
                                           poverride1=false;
                                      }
                                   }
                                   if(!parentDoubtStatTrace.isEmpty()){         //an entry exists for the parent 
                                       pdstrace=parentDoubtStatTrace.get(0);
                                       if(pdstrace.getStatus().equals("O")){
                                           poverride2=true;
                                       }
                                       if(pdstrace.getStatus().equals("Y")){
                                           poverride2=false;
                                       }
                                   }
                                   
                                   boolean poverride= poverride1 && poverride2; //if both are overriden then state is poverride. else its Y
                                   if(poverride){
                                       refSub.getDoubt().setStatus("O");
                                       refSeq.getDoubt().setStatus("O");
                                       parent.getDoubt().setStatus("O");
                                   }else{                   //this will correspond to when there is no entry for this doubt in the db or when atleast one of them hasn't been overriden
                                       refSub.getDoubt().setStatus("Y");
                                       refSeq.getDoubt().setStatus("Y");
                                       parent.getDoubt().setStatus("Y");
                                      
                                       
                                       
                                       if(!passTimeStamps){ //if passtimeStamps is false  ..failed timestamp checks
                                           
                                           if(parentDoubtstatTime.isEmpty()){         //no entry for the parent exists .. time doubt in table  .so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtime);
                                               ds.setHeaders(phdr);
                                               ds.setSessionDetails(parentSsd);
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               ds.setErrorMessage(refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+refSub.getTimeStamp()+")");
                                               
                                               
                                               dsServ.createDoubtStatus(ds);
                                            }
                                           
                                           
                                           refSeq.getDoubt().addToDoubtMap(Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+refSub.getTimeStamp()+")");
                                           refSub.getDoubt().addToDoubtMap(Doubt.doubtTime, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has an earlier timestamp ("+refSub.getTimeStamp()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+refSub.getTimeStamp()+")");
                                       }
                                       
                                       if(!passTraceCounts){ //if traces is false  ..failed tracecounts checks
                                           
                                           if(parentDoubtStatTrace.isEmpty()){         //no entry for the parent exists..time doubt in table..so creating one
                                               DoubtStatus ds=new DoubtStatus();
                                               ds.setDoubtType(dtrace);
                                               ds.setHeaders(phdr);
                                               ds.setSessionDetails(parentSsd);
                                               ds.setStatus("Y");
                                               ds.setUser(null);
                                               ds.setErrorMessage(refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has ("+refSub.getTraceCount()+") traces while the one in the child job: "+this.child.getJobStepText()
                                                   +" has ("+targetSub.getTraceCount()+") traces");
                                               
                                               
                                               dsServ.createDoubtStatus(ds);  
                                            }
                                           
                                           refSeq.getDoubt().addToDoubtMap(Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                           refSub.getDoubt().addToDoubtMap(Doubt.doubtTraces, refSub.getSubsurface()+"in parent: "+this.parent.getJobStepText()+" has lesser traces ("+refSub.getTraceCount()+") than the one in the child job: "+this.child.getJobStepText()
                                                   +" ("+targetSub.getTraceCount()+")");
                                       }
                                       
                                       
                                       
                                       
                                   }
        
        

                                   //end of parent Block
                                   
                                  
                               }
                              
                               System.out.println("fend.session.SessionController.D11(): ErrorMessage "+errorMessage);
                               targetSeq.setErrorMessage(errorMessage);
                               targetSub.setErrorMessage(errorMessage);
                        
                        
                       
        }
         
       
         
        
         
         
         
    }    
        
        
    }
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        System.out.println("fend.session.node.jobs.dependencies.Dep11.calculateSubsInJob(): entered");
        if(job instanceof JobStepType1Model){                   //for 2D case
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
        
        System.out.println("fend.session.node.jobs.dependencies.Dep11.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
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
    
    
    

}
