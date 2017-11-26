/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.dependencies;

import db.model.Headers;
import db.model.Volume;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionController;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.acquisitionType.AcquisitionNode;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type0.JobStepType0Node;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type1.JobStepType1Node;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import landing.AppProperties;
import org.apache.commons.collections4.map.MultiValueMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Dependency class between parent=Acquisition and child=JobStepType1 nodes
 */
 

public class DepA1 {
    AcquisitionJobStepModel parent;
    JobStepType1Model child;
    Boolean passDependency=true;
    String errorMessage=new String();
    HeadersService hserv=new HeadersServiceImpl();
    VolumeService volserv=new VolumeServiceImpl();
    
    
    
    public DepA1(JobStepType0Model parent, JobStepType0Model child) {
        this.parent = (AcquisitionJobStepModel) parent;
        this.child = (JobStepType1Model) child;
        List<Headers> headersToBeUpdated=new ArrayList<>();
        
        if(parent.getType().equals(3L) && child.getType().equals(1L)){
           
           Boolean passTraceCounts=true;            //defualt QC status is false
           Boolean passTimeStamps=true;
        //Boolean insightFail=false;
           //insight version check
           calculateSubsInJob(this.child);
            Set<SubSurfaceHeaders> csubq=this.child.getSubsurfacesInJob();
            
            List<AcquisitionVolumeModel> acqVolList=this.parent.getVolList();
            for (Iterator<AcquisitionVolumeModel> iterator = acqVolList.iterator(); iterator.hasNext();) {
                AcquisitionVolumeModel next = iterator.next();
                Volume vol=volserv.getVolume(next.getId());
            List<Headers> hdrs=hserv.getHeadersFor(vol);
            headersToBeUpdated.addAll(hdrs);
            }
            
            
            List<VolumeSelectionModelType1> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.TRUE);                        //first set all the volumes to false. then check each one below
            //next.setDoubt("N");
            Volume vol=volserv.getVolume(next.getId());
            List<Headers> hdrs=hserv.getHeadersFor(vol);
            headersToBeUpdated.addAll(hdrs);
            
        }
            
             this.child.setDependency(Boolean.TRUE);
             this.child.getDoubt().setDoubt(false);
           
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders refSubQ = iterator.next();
                        refSubQ.setDependency(Boolean.TRUE);
                        refSubQ.getDoubt().setDoubt(false);
                        refSubQ.getSequenceHeader().getDoubt().setDoubt(false);
                        refSubQ.getSequenceHeader().setDependency(Boolean.TRUE);
           
       }
        
        }
        
        this.passDependency=Boolean.TRUE;
        this.errorMessage=new String();
        updateSummaryTimes(headersToBeUpdated);
    }
    
    
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        
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
        
        return subsInJob;
        }
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
    }

    public Boolean getPassDependency() {
        return passDependency;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void updateSummaryTimes(List<Headers> headersToBeUpdated) {
        for (Iterator<Headers> iterator = headersToBeUpdated.iterator(); iterator.hasNext();) {
            
            Headers next = iterator.next();
            next.setSummaryTime(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
            hserv.updateHeaders(next.getIdHeaders(), next);
        }
    }
    
    
    
    
    
    
}
