/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.dependencies;

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
 * Dependency class between parent=Acquisition and child=JobStepType2 nodes
 */
public class DepA2 {
        AcquisitionJobStepModel parent;
    JobStepType2Model child;
    Boolean passDependency=true;
    String errorMessage=new String();

    public DepA2(JobStepType0Model parent, JobStepType0Model child) {
        this.parent = (AcquisitionJobStepModel) parent;
        this.child = (JobStepType2Model) child;
        System.out.println("mid.doubt.dependencies.DepA2.<init>() entered");
        if(parent.getType().equals(3L) && child.getType().equals(2L)){
           System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")");
           Boolean passTraceCounts=true;            //defualt QC status is false
           Boolean passTimeStamps=true;
        //Boolean insightFail=false;
           //insight version check
           calculateSubsInJob(this.child);
            Set<SubSurfaceHeaders> csubq=this.child.getSubsurfacesInJob();
            System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug2");
            List<VolumeSelectionModelType2> cVolList=this.child.getVolList();
         for (Iterator<VolumeSelectionModelType2> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType2 next = iterator.next();
            next.setDependency(Boolean.TRUE);  
            System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug2.1");//first set all the volumes to false. then check each one below
            //next.setDoubt("N");
        }
            System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug2.2");
             this.child.setDependency(Boolean.TRUE);
             this.child.getDoubt().setDoubt(false);
           System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug3");
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders refSubQ = iterator.next();
                        refSubQ.setDependency(Boolean.TRUE);
                        refSubQ.getDoubt().setDoubt(false);
                        refSubQ.getSequenceHeader().getDoubt().setDoubt(false);
                        refSubQ.getSequenceHeader().setDependency(Boolean.TRUE);
                        System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug4");
                        
           
       }
        
        }
        System.out.println("mid.doubt.dependencies.DepA2.<init>() entered with ( parent:child ) = ("+parent.getJobStepText()+" , "+child.getJobStepText()+")  : debug5");
        this.passDependency=Boolean.TRUE;
        this.errorMessage=new String();
    }
    
    
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        
        if(job instanceof JobStepType2Model){                   //for 2D case
            List<VolumeSelectionModelType2> volList=job.getVolList();
        Set<SubSurfaceHeaders> subsInJob=new HashSet<>();
            System.out.println("mid.doubt.dependencies.DepA2.calculateSubsInJob() debug1");
        for (Iterator<VolumeSelectionModelType2> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType2 vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurfaceHeaders> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            System.out.println("mid.doubt.dependencies.DepA2.calculateSubsInJob() debug2");
            
        }
        job.setSubsurfacesInJob(subsInJob);
        /*for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
        SubSurfaceHeaders subinJob = iterator.next();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        System.out.println("mid.doubt.dependencies.DepA2.calculateSubsInJob() debug3");
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
