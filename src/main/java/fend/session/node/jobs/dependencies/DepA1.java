/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.dependencies;

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
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.map.MultiValueMap;

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

    public DepA1(JobStepType0Model parent, JobStepType0Model child) {
        this.parent = (AcquisitionJobStepModel) parent;
        this.child = (JobStepType1Model) child;
        
        if(parent.getType().equals(3L) && child.getType().equals(1L)){
           
           Boolean passTraceCounts=true;            //defualt QC status is false
           Boolean passTimeStamps=true;
        //Boolean insightFail=false;
           //insight version check
           calculateSubsInJob(this.child);
            Set<SubSurfaceHeaders> csubq=this.child.getSubsurfacesInJob();
            
            List<VolumeSelectionModelType1> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.TRUE);                        //first set all the volumes to false. then check each one below
            //next.setDoubt("N");
        }
            
             this.child.setDependency(Boolean.TRUE);
             this.child.getDoubt().setDoubt(false);
           
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders refSubQ = iterator.next();
                        refSubQ.setDependency(Boolean.TRUE);
                        refSubQ.getDoubt().setDoubt(false);
                       
           
       }
        
        }
        
        this.passDependency=Boolean.TRUE;
        this.errorMessage=new String();
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
    
    
    
    
    
    
}
