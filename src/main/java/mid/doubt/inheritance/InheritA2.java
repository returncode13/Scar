/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.inheritance;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * class responsible for the inheritance of doubt between Acquisition node and JobStepModelType2 
 */
public class InheritA2 {
    AcquisitionJobStepModel parent;
    JobStepType2Model child;

    public InheritA2(JobStepType0Model  parent, JobStepType0Model  child) {
        this.parent = (AcquisitionJobStepModel) parent;
        this.child = (JobStepType2Model) child;
        
        if(parent.getType().equals(3L) && child.getType().equals(1L)){
           
          
           calculateSubsInJob(this.child);
            Set<SubSurfaceHeaders> csubq=this.child.getSubsurfacesInJob();
            
            List<VolumeSelectionModelType2> cVolList=child.getVolList();
         
            
           
             this.child.getDoubt().setDoubt(false);
           
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders targetSub = iterator.next();
                       
                        targetSub.getDoubt().setDoubt(false);
                          SequenceHeaders targetSeq=targetSub.getSequenceHeader();
                          targetSeq.getDoubt().setDoubt(false);
           
                        }
        
        }
        
        
    }
 
      private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        
        if(job instanceof JobStepType2Model){                   //for 2D case
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
        
        return subsInJob;
        }
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
    }
}
