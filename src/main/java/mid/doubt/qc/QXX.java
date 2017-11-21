/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.qc;

import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.QcTable;
import db.model.Subsurface;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import landing.AppProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QXX {
    
    
    
    private JobStepType0Model root;
    SubsurfaceService subserv=new SubsurfaceServiceImpl();
    JobStepService jserv=new JobStepServiceImpl();
    JobVolumeDetailsService jvserv=new JobVolumeDetailsServiceImpl();
    HeadersService hserv=new HeadersServiceImpl();
    QcTableService qctserv=new QcTableServiceImpl();
    
    
    public QXX(JobStepType0Model root,List<SubSurfaceHeaders> subsToSummarize) {
        this.root = root;
        
         JobStep rootjs=jserv.getJobStep(this.root.getId());
        List<JobVolumeDetails> chjvList=jvserv.getJobVolumeDetails(rootjs);
        List<Headers> hdrsToBeUpdated=new ArrayList<>();
        
        Set<SubSurfaceHeaders> chsubs;
        if(subsToSummarize==null){
         calculateSubsInJob(this.root);
         chsubs=this.root.getSubsurfacesInJob();
        }else{
             //chsubs=new HashSet<>(subsToSummarize);
             chsubs=lookupSubsFromMap(this.root,subsToSummarize);
        }
         String time=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
         
         
        for (Iterator<SubSurfaceHeaders> iterator = chsubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders next = iterator.next();
            Subsurface subObj=subserv.getSubsurfaceObjBysubsurfacename(next.getSubsurface());
            
            Volume chVol=null;
            Headers ch=null;
            
            
                    for (Iterator<JobVolumeDetails> chjviterator = chjvList.iterator(); chjviterator.hasNext();) {
                                            JobVolumeDetails jv = chjviterator.next();
                                            chVol=jv.getVolume();
                                            List<Headers> hdrlist=hserv.getHeadersFor(chVol, subObj);
                                            if(hdrlist.isEmpty()){
                                                
                                            }else if(hdrlist.size()==1){
                                                ch=hdrlist.get(0);
                                               
                                                hdrsToBeUpdated.add(ch);
                                                
                                            }
                                        

                                        }
            
            
            next.setSummaryTime(time);
           updateSummaryTimes(hdrsToBeUpdated);
            
        }
    }
    
    
    
    private void updateSummaryTimes(List<Headers> hdrsToBeUpdated) {
        for (Iterator<Headers> iterator = hdrsToBeUpdated.iterator(); iterator.hasNext();) {
            Headers next = iterator.next();
            List<QcTable> qctabListForHeaderSelected=qctserv.getQcTableFor(next);
            System.out.println("mid.doubt.qc.QXX.updateSummaryTimes():  for header ID: "+next.getIdHeaders()+" size of qctable list returned "+qctabListForHeaderSelected.size());
            for (Iterator<QcTable> iterator1 = qctabListForHeaderSelected.iterator(); iterator1.hasNext();) {
                QcTable qctab = iterator1.next();
                qctab.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                qctserv.updateQcTable(qctab.getIdQcTable(), qctab);
                
            }
        }
    }
    
    
    
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        System.out.println("fend.session.node.jobs.dependencies.InheritXX.calculateSubsInJob(): entered");
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
        
      System.out.println("fend.session.node.jobs.dependencies.InheritXX.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
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
        
        System.out.println("fend.session.node.jobs.dependencies.InheritXX.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
        return subsInJob;
        }
        
        
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
    }
    
    
     private Set<SubSurfaceHeaders> lookupSubsFromMap(JobStepType0Model job, List<SubSurfaceHeaders> subsToSummarize) {
        
        if(job instanceof JobStepType1Model){                   //for 2D case
            List<VolumeSelectionModelType1> volList=job.getVolList();
        Set<SubSurfaceHeaders> correspondingSubsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModelType1> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 vol = iterator.next();
                
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
        }else if(job instanceof JobStepType2Model){                   //for 2D case
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
