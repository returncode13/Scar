/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.qc;

import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 *  Qc check between Acquisition and JobStepType2Model jobs. Nothing to do here here yet.
 */
public class QA2 {
     private AcquisitionJobStepModel parent;
    private JobStepType2Model child;

    public QA2(JobStepType0Model parent, JobStepType0Model child) {
        this.parent=(AcquisitionJobStepModel) parent;
        this.child=(JobStepType2Model) child;
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


}
