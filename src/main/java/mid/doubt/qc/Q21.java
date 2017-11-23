/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.qc;

import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.QcTable;
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
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
//import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.qcTable.QcTableSequences;
import fend.session.node.qcTable.QcTableSubsurfaces;
import fend.session.node.qcTable.QcTypeModel;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import landing.AppProperties;
import mid.doubt.Doubt;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Qc check between JobStepType2Model and JobStepType1Model jobs. 
 * Set child's doubt=true, if the child's pre-existing doubt=true OR if the parent has failed QC checks for all qctypes defined in parent
 */
public class Q21 {
    private JobStepType2Model parent;
    private JobStepType1Model child;
    DoubtStatusService dsServ=new DoubtStatusServiceImpl();
    DoubtTypeService dstypeServ=new DoubtTypeServiceImpl();
    SessionDetailsService ssdServ= new SessionDetailsServiceImpl();
    SessionsService sessServ=new SessionsServiceImpl();
    JobStepService jserv=new JobStepServiceImpl();
    VolumeService vserv=new VolumeServiceImpl();
    HeadersService hserv=new HeadersServiceImpl();
    SubsurfaceService subserv=new SubsurfaceServiceImpl();
    JobVolumeDetailsService jvserv=new JobVolumeDetailsServiceImpl();
    QcTableService qctserv=new QcTableServiceImpl();
    
    SessionModel session;
    /**
     * @params: 
     * parent : Parent Job
     * child: Child job
     * subsToSummarize: null for first summary, subs in child job to be summarized for later queries
     * 
     */
    public Q21(JobStepType0Model parent, JobStepType0Model child,List<SubSurfaceHeaders> subsToSummarize) {
        this.parent = (JobStepType2Model) parent;
        this.child = (JobStepType1Model) child;
        this.session=this.parent.getSessionModel();
        
        //JobStep parentJs=jserv.getJobStep(this.parent.getId());
        //List<JobVolumeDetails> pjvList=jvserv.getJobVolumeDetails(parentJs);
        
        JobStep childjs=jserv.getJobStep(this.child.getId());
        List<JobVolumeDetails> chjvList=jvserv.getJobVolumeDetails(childjs);
        
        JobStep parentJs=jserv.getJobStep(this.parent.getId());
         List<JobVolumeDetails> pjvList=jvserv.getJobVolumeDetails(parentJs);
        
        Sessions sess=sessServ.getSessions(session.getId());
        DoubtType dqc=dstypeServ.getDoubtTypeByName(Doubt.doubtQc);
        JobStep parentjs=jserv.getJobStep(this.parent.getId());
        SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);
        
        
        SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
        
        List<Headers> hdrsToBeUpdated=new ArrayList<>();
        
        System.out.println("mid.doubt.qc.Q21.<init>(): parentJob: "+parent.getJobStepText()+" childJob: "+child.getJobStepText());
        //List<QcTableSequences> childqcseqs=this.child.getQcTableModel().getQcTableSequences();
        Set<SubSurfaceHeaders> chsubs;
        if(subsToSummarize==null){
         calculateSubsInJob(this.child);
         chsubs=this.child.getSubsurfacesInJob();
        }else{
             //chsubs=new HashSet<>(subsToSummarize);
             chsubs=lookupSubsFromMap(this.child,subsToSummarize);
        }
         
         for (Iterator<SubSurfaceHeaders> iterator = chsubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders chsub = iterator.next();
            chsub.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
            Boolean hasPassed=true;
                Boolean currentlyDoubtful=chsub.getDoubt().isDoubt();                                      //get current doubtboolean state of child subObj. this can be set by the previous step. dependencyChecks()
                String currentDoubtStatus=new String("N");
                String currentError=new String();
                //check if the database has an entry for this header and for this doubtype.qc.
                //need to find out the correct doubt status if there is an entry
                                  Boolean exists=false;  
                        
                                  /* Sessions sess=sessServ.getSessions(session.getId());
                                  DoubtType dqc=dstypeServ.getDoubtTypeByName(Doubt.doubtQc);
                                  JobStep parentjs=jserv.getJobStep(parent.getId());
                                  SessionDetails parentSsd=ssdServ.getSessionDetails(parentjs, sess);*/
                                    Subsurface subObj=subserv.getSubsurfaceObjBysubsurfacename(chsub.getSubsurface());
                                    
                                    /*JobStep childjs=jserv.getJobStep(this.child.getId());
                                    SessionDetails childSsd =ssdServ.getSessionDetails(childjs, sess);
                                    */
                                    //Volume pVol=null;
                                    Volume chVol=null;
                                    Headers ch=null;
                                    Volume pVol=null;
                                    Headers ph=null;
                                    Integer once=0;
                                    List<String> doubtMessage=new ArrayList<>();
                                    
                                   /* for (Iterator<JobVolumeDetails> pjviterator = pjvList.iterator(); pjviterator.hasNext();) {
                                    JobVolumeDetails jv = pjviterator.next();
                                    pVol=jv.getVolume();
                                    List<Headers> hdrlist=hserv.getHeadersFor(pVol, subObj);
                                    if(hdrlist.isEmpty()){
                                    
                                    }else if(hdrlist.size()==1){
                                    ph=hdrlist.get(0);
                                    
                                    once++;
                                    }
                                    
                                    
                                    }*/
                                        for (Iterator<JobVolumeDetails> chjviterator = chjvList.iterator(); chjviterator.hasNext();) {
                                            JobVolumeDetails jv = chjviterator.next();
                                            chVol=jv.getVolume();
                                            List<Headers> hdrlist=hserv.getHeadersFor(chVol, subObj);
                                            if(hdrlist.isEmpty()){
                                                
                                            }else if(hdrlist.size()==1){
                                                ch=hdrlist.get(0);
                                                //ch.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                                hdrsToBeUpdated.add(ch);
                                                once++;
                                            }
                                        

                                        }
                                        if(once>1){
                                            System.out.println("mid.doubt.qc.Q21.<init>(): sub: "+subObj.getSubsurface()+" found multiple times in job: "+this.parent.getJobStepText());
                                            return;
                                        }
                                    once=0;    
                                    List<DoubtStatus> dst=dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dqc, ch);  //looking for doubt in child based on qc failure in parent.
                                   // List<DoubtStatus> dst=dsServ.getDoubtStatusListForJobInSession(parentSsd,childSsd.getIdSessionDetails(), dqc, ph);  //looking for doubt in child based on qc failure in parent.
                                    if(dst.isEmpty()){ //no entry ..no doubt for child
                                        
                                    }else{              //doubt exists in child. now determine if the status is  overridden  or yes
                                        for (Iterator<DoubtStatus> iterator2 = dst.iterator(); iterator2.hasNext();) {
                                            DoubtStatus cdbt = iterator2.next();
                                            currentDoubtStatus=cdbt.getStatus();
                                            currentError=cdbt.getErrorMessage();
                                            exists=true;
                                            once++;
                                        }
                                      if(once>1){
                                          System.out.println("mid.doubt.qc.Q21.<init>() sub: "+subObj.getSubsurface()+" has multiple doubts of the same type: "+Doubt.doubtQc+" between "+ this.parent.getJobStepText()+" and "+this.child.getJobStepText());
                                      return;
                                      };
                                       
                                    }
                                    
                                    
                    //currentDoubtStatus is set at this point
                    
                    //now determine if the subObj has failed qc in the parent.
                    System.out.println("mid.doubt.qc.Q21.<init>(): Checking for sub: "+chsub.getSubsurface()+ "in parent job: "+this.parent.getJobStepText());
                    QcTableSubsurfaces qcsubInParent=null;
                    once=0;
                    List<QcTableSequences> parentQcSeqs=this.parent.getQcTableModel().getQcTableSequences();
                    for (Iterator<QcTableSequences> iterator1 = parentQcSeqs.iterator(); iterator1.hasNext();) {
                        QcTableSequences pqcseq = iterator1.next();
                        List<QcTableSubsurfaces> pcsubs=pqcseq.getQcSubs();
                        for (Iterator<QcTableSubsurfaces> iterator2 = pcsubs.iterator(); iterator2.hasNext();) {
                            QcTableSubsurfaces pqcsub = iterator2.next();
                            System.out.println("mid.doubt.qc.Q21.<init>(): Checking for sub: "+chsub.getSubsurface()+" comparing with parentsub : "+pqcsub.getSub().getSubsurface()+" which has the qcsub: "+pqcsub.getSubsurface());
                            SubSurfaceHeaders psubh=pqcsub.getSub();
                            if(psubh.getSubsurface().equals(chsub.getSubsurface())){
                                once++;
                                qcsubInParent=pqcsub;
                                
                            }
                            
                        }
                     }
                    if(once>1){
                            System.out.println("mid.doubt.qc.Q21.<init>() sub: "+subObj.getSubsurface()+" in child job:  "+this.child.getJobStepText()+"has more than one parent sub in parent job: "+this.parent.getJobStepText());
                            return;
                        }
                    
                    if(once==0){
                        System.out.println("mid.doubt.qc.Q21.<init>() not parent sub found for sub: "+subObj.getSubsurface()+" in child job: "+this.child.getJobStepText());
                    //    qcsubInParent=new QcTableSubsurfaces();
                        return;
                    }
                    
                    List<QcTypeModel> qctypes=qcsubInParent.getQctypes();                                               //get the qctypes in the parent.
                    for (Iterator<QcTypeModel> iterator1 = qctypes.iterator(); iterator1.hasNext();) {
                        QcTypeModel qct = iterator1.next();
                       // hasPassed = hasPassed && qct.isPassQc();
                       hasPassed = hasPassed && (qct.isPassQc().equals(QcTypeModel.isInDeterminate) || qct.isPassQc().equals(Boolean.FALSE.toString()))?false:true;

                    }
                    
                    Boolean finalDoubt=currentlyDoubtful || !hasPassed;   //final doubt of the child sub will be true if its currentlydoubtful=true (because of dependencyChecks) or if the parent hasn't passed QC;
                  //  chsub.getDoubt().setDoubt(finalDoubt);
                    System.out.println("mid.doubt.qc.Q21.<init>(): (parent,child) : ("+this.parent.getJobStepText()+","+this.child.getJobStepText()+"):  sub:"+ subObj.getSubsurface()+" currentlyDoubtful: "+currentlyDoubtful+" hassNOTPassed: "+(!hasPassed)+" finalDoubt: "+finalDoubt+" "+" existsInDB: "+exists);
                    //if(finalDoubt){  //if finaldoubt is true
                    System.out.println("mid.doubt.qc.Q21.<init>(): H(parent,child) : ("+this.parent.getJobStepText()+","+this.child.getJobStepText()+"):  sub:"+ subObj.getSubsurface()+" hassPassed: "+hasPassed+" existsInDB: "+exists);
                    
                    if(!hasPassed){  //if parent has failed 
                       if(exists){  //entry exists in db
                           System.out.println("mid.doubt.qc.Q21.<init>(): for sub: "+ chsub.getSubsurface()+ "in child :"+this.child.getJobStepText()+" , the currentStatus is "+currentDoubtStatus);
                           if(currentDoubtStatus.equals("Y")){
                               //dont do anything. it stays doubtful with status=Y for Doubt.qc type
                               chsub.getDoubt().setStatus("Y");
                               chsub.getSequenceHeader().getDoubt().setStatus("Y");
                               chsub.getDoubt().addToDoubtMap(parent, child, Doubt.doubtQc, currentError);
                               chsub.getDoubt().setDoubt(true);
                               doubtMessage.add(currentError);
                               
                             ///  chsub.setErrorMessageList(doubtMessage);
                           }
                           if(currentDoubtStatus.equals("O")){
                               //dont do anything. it stays doubtful with status=O for Doubt.qc type
                               chsub.getDoubt().setStatus("O");
                                chsub.getSequenceHeader().getDoubt().setStatus("O");
                               chsub.getDoubt().removeFromDoubtMap(parent, child, Doubt.doubtQc);
                                setSeqDoubtStatus(chsub);
                               chsub.getDoubt().setDoubt(true);
                               doubtMessage.add(currentError);
                              // chsub.setErrorMessageList(doubtMessage);
                               
                           }
                       }
                       
                       if(!exists){ //no entry in db
                           String err=new String("subsurface "+chsub.getSubsurface()+" in parent job: "+this.parent.getJobStepText()+" has failed at one or more qc types");
                           chsub.getDoubt().addToDoubtMap(parent, child, Doubt.doubtQc, err);    //add to the childs doubt map
                           chsub.getDoubt().setStatus("Y");
                            chsub.getSequenceHeader().getDoubt().setStatus("Y");
                           doubtMessage.add(currentError);
                          // chsub.setErrorMessageList(doubtMessage);
                           
                           doubtMessage.add(err);
                         //  chsub.setErrorMessageList(doubtMessage);
                           DoubtStatus ds=new DoubtStatus();
                           ds.setDoubtType(dqc);
                           ds.setChildSessionDetailsId(childSsd.getIdSessionDetails());
                           ds.setParentSessionDetails(parentSsd);
                           ds.setHeaders(ch);
                           //ds.setHeaders(ph);
                           ds.setUser(null);
                           ds.setStatus("Y");
                           ds.setErrorMessage(err);
                           
                           dsServ.createDoubtStatus(ds);
                           
                           
                       }
                    }
                    
                    //if(!finalDoubt){   //final doubt is false. i.e both the currentlyDoubtful = false and the parent has passed all QC
                     if(hasPassed){   //parent has passed all QC
                        if(exists){  //if an entry exists for child under the type Doubt.qc..remove it
                            DoubtStatus d=dst.get(0);
                            dsServ.deleteDoubtStatus(d.getIdDoubtStatus());
                            chsub.getDoubt().removeFromDoubtMap(parent, child, Doubt.doubtQc);
                            chsub.getDoubt().setStatus("N");
                             setSeqDoubtStatus(chsub);
                        }
                        if(!exists){
                                        //do nothing
                            //chsub.getDoubt().setStatus("N");
                            //setSeqDoubtStatus(chsub);
                        }
                    }
                    
                    
                    updateSummaryTimes(hdrsToBeUpdated);
            
        }
}
    
private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        System.out.println("fend.session.node.jobs.dependencies.Q21.calculateSubsInJob(): entered");
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
        
        System.out.println("fend.session.node.jobs.dependencies.Q21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
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
        
        System.out.println("fend.session.node.jobs.dependencies.Q21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
        return subsInJob;
        }
        
        
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
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
        
    }

    private void updateSummaryTimes(List<Headers> hdrsToBeUpdated) {
        for (Iterator<Headers> iterator = hdrsToBeUpdated.iterator(); iterator.hasNext();) {
            Headers next = iterator.next();
            List<QcTable> qctabListForHeaderSelected=qctserv.getQcTableFor(next);
            
            for (Iterator<QcTable> iterator1 = qctabListForHeaderSelected.iterator(); iterator1.hasNext();) {
                QcTable qctab = iterator1.next();
                qctab.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                qctserv.updateQcTable(qctab.getIdQcTable(), qctab);
                
            }
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
