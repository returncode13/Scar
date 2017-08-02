/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.inheritance;

import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
//import fend.session.node.jobs.types.type1.JobStepType1Model;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 *  class responsible for the inheritance of doubt between parent=JobStepModelType2 node and child=JobStepModelType1 
 */
public class Inherit21 {
    JobStepType2Model parent;
    JobStepType1Model child;

    public Inherit21(JobStepType0Model  parent, JobStepType0Model  child) {
        this.parent = (JobStepType2Model) parent;
        this.child = (JobStepType1Model)child;
        
        
         
        System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>() parent: "+this.parent.getJobStepText()+" : "+this.child.getJobStepText());
        
        
         System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>() Calculating subs in parent and child");
            calculateSubsInJob(this.child);
            calculateSubsInJob(this.parent);
       
         Set<SubSurfaceHeaders> psubs=this.parent.getSubsurfacesInJob();
         Set<SubSurfaceHeaders> csubs=this.child.getSubsurfacesInJob();
            System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): size of child and parent subs: "+csubs.size()+" : "+psubs.size());
            
         List<VolumeSelectionModelType1> cVolList=this.child.getVolList();
         List<VolumeSelectionModelType2> pVolList=this.parent.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
           // next.setDependency(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
        
         
         
         for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders c = iterator.next();
            VolumeSelectionModelType1 targetVol=new VolumeSelectionModelType1(1L,this.child);
            
            VolumeSelectionModelType2 refVol=new VolumeSelectionModelType2(2L,this.parent);
            
            SubSurfaceHeaders targetSub=c;
            SequenceHeaders targetSeq=targetSub.getSequenceHeader();
             System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>() Checking dependency(): child: "+child.getJobStepText()+" sub: "+targetSub.getSubsurface()+" dependency: "+targetSub.dependencyProperty().get() +" doubt: "+targetSub.getDoubt().isDoubt()
                     + " :stat: "+targetSub.getDoubt().getStatus());
             
             System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>() Checking dependency(): child: "+child.getJobStepText()+" seq: "+targetSeq.getSequenceNumber()+" dependency: "+targetSeq.dependencyProperty().get() +" doubt: "+targetSeq.getDoubt().isDoubt()
                     + " :stat: "+targetSeq.getDoubt().getStatus());
             
            SubSurfaceHeaders refSub=new SubSurfaceHeaders();
            SequenceHeaders refSeq=new SequenceHeaders();
            
            
            //Finding the volume in the child which contains the current sub...i.e  targetSub=c
                        for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
                           System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): Checking if Job: "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                              System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): "+this.child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVol=vc;
                                targetVol.setVolumeType(vc.getVolumeType());
                                break;
                            }
                 
                        }
                        
                         //targetSeq=targetSub.getSequenceHeader();
                        
            //finding the sequence to which this sub belongs to.
                        
            /*if(targetVol!=null){
            HeadersModel hmod=targetVol.getHeadersModel();
            List<SequenceHeaders> seqList=hmod.getSequenceListInHeaders();
            
            for (SequenceHeaders seq : seqList) {
            if(targetSub.getSequenceNumber().equals(seq.getSequenceNumber())){
            targetSeq=seq;
            System.out.println("fend.session.node.jobs.dependencies.Inherit11.<init>(): "+this.child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
            break;
            }
            
            
            }
            
            
            }else
            {
            try {
            throw new Exception("Subline: "+targetSub.getSubsurface()+" :not found in any of the child job: "+child.getJobStepText()+" : volumes!!");
            } catch (Exception ex) {
            Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }*/
                      
                 
                        
                        //Find the refsub in the parent that corresponds to the targetSub..basically the same subline in the parent. 
                        //Also find the seq to which it corresponds to ..refseq contains refsub
                        for (Iterator<SubSurfaceHeaders> iterator1 = psubs.iterator(); iterator1.hasNext();) {
                          SubSurfaceHeaders p = iterator1.next();
                          if(csubs.contains(p))
                          {
                              if(p.equals(c)){
                              System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): "+p.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
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
                           System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                            if(vpSub.contains(refSub)){
                              System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): "+this.parent.getJobStepText()+" :Volume: "+vp.getLabel()+" :contains: "+refSub.getSubsurface());
                                refVol=vp;
                                refVol.setVolumeType(vp.getVolumeType());
                                break;
                            }
                 
                        }
                         
                         
                         
                         
                         
                         String parentSeqDoubtStatus=refSeq.getDoubt().getStatus();
                         String childSeqDoubtStatus=targetSeq.getDoubt().getStatus();
                         
                         
                         
                         
                         System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): for seq: "+refSeq.getSequenceNumber()+" pdoubt: "+parentSeqDoubtStatus+" cdoubt: "+childSeqDoubtStatus  +" child string equals Y? : "+ childSeqDoubtStatus.equals("Y") + "parent string equals N? :"+parentSeqDoubtStatus.equals("N"));
                            if(parentSeqDoubtStatus.equals("O") &&  childSeqDoubtStatus.equals("O") ) {targetSeq.getDoubt().setStatus("O");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("O") &&  childSeqDoubtStatus.equals("Y") ) {targetSeq.getDoubt().setStatus("Y");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("O") &&  childSeqDoubtStatus.equals("N") ) {targetSeq.getDoubt().setStatus("O");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("Y") &&  childSeqDoubtStatus.equals("O") ) {targetSeq.getDoubt().setStatus("Y");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("Y") &&  childSeqDoubtStatus.equals("Y") ) {targetSeq.getDoubt().setStatus("Y");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("Y") &&  childSeqDoubtStatus.equals("N") ) {targetSeq.getDoubt().setStatus("Y");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("N") &&  childSeqDoubtStatus.equals("O") ) {targetSeq.getDoubt().setStatus("O");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("N") &&  childSeqDoubtStatus.equals("Y") ) {targetSeq.getDoubt().setStatus("Y");targetSeq.getDoubt().setDoubt(true);};
                            if(parentSeqDoubtStatus.equals("N") &&  childSeqDoubtStatus.equals("N") ) {targetSeq.getDoubt().setStatus("V");targetSeq.getDoubt().setDoubt(false);};
                       // Boolean refSeqDoubt=refSeq.getDoubt().isDoubt();
                       // targetSeq.getDoubt().setDoubt(refSeqDoubt);
                        System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): for seq: "+targetSeq.getSequenceNumber()+" setting status: "+targetSeq.getDoubt().getStatus()+"  doubt: "+targetSeq.getDoubt().isDoubt());
                        
                            
                            
                            
                         String parentSubDoubtStatus=refSub.getDoubt().getStatus();
                         String childSubDoubtStatus=targetSub.getDoubt().getStatus();
                         System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): for parent: "+parent.getJobStepText()+" child: "+child.getJobStepText()+" sub: "+refSub.getSubsurface()+" pdoubt: "+parentSubDoubtStatus+" cdoubt: "+childSubDoubtStatus);

                            if(parentSubDoubtStatus.equals("O") &&  childSubDoubtStatus.equals("O") ) {targetSub.getDoubt().setStatus("O");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("O") &&  childSubDoubtStatus.equals("Y") ) {targetSub.getDoubt().setStatus("Y");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("O") &&  childSubDoubtStatus.equals("N") ) {targetSub.getDoubt().setStatus("O");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("Y") &&  childSubDoubtStatus.equals("O") ) {targetSub.getDoubt().setStatus("Y");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("Y") &&  childSubDoubtStatus.equals("Y") ) {targetSub.getDoubt().setStatus("Y");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("Y") &&  childSubDoubtStatus.equals("N") ) {targetSub.getDoubt().setStatus("Y");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("N") &&  childSubDoubtStatus.equals("O") ) {targetSub.getDoubt().setStatus("O");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("N") &&  childSubDoubtStatus.equals("Y") ) {targetSub.getDoubt().setStatus("Y");targetSub.getDoubt().setDoubt(true);}
                            if(parentSubDoubtStatus.equals("N") &&  childSubDoubtStatus.equals("N") ) {targetSub.getDoubt().setStatus("N");targetSub.getDoubt().setDoubt(false);}
                            
                        //Boolean refSubDoubt=refSub.getDoubt().isDoubt();
                        //targetSub.getDoubt().setDoubt(refSubDoubt);
                        System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): for parent: "+parent.getJobStepText()+" child: "+child.getJobStepText()+"  sub: "+targetSub.getSubsurface()+" setting status: "+targetSub.getDoubt().getStatus()+"  doubt: "+targetSub.getDoubt().isDoubt()+ "   After Setting");
                         
                         
                         
         }
         
         //inherit Doubts with priorities on own doubts. ( Y > O > N)
         
        String childDoubtStatus= this.child.getDoubt().getStatus();
        String parentDoubtStatus=this.parent.getDoubt().getStatus();
        
         System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>() parentDoubtStatus: "+parentDoubtStatus+" childDoubtStatus: "+childDoubtStatus);
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("O") ) {this.child.getDoubt().setStatus("O");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("Y") ) {this.child.getDoubt().setStatus("Y");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("O") &&  childDoubtStatus.equals("N") ) {this.child.getDoubt().setStatus("O");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("O") ) {this.child.getDoubt().setStatus("Y");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("Y") ) {this.child.getDoubt().setStatus("Y");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("Y") &&  childDoubtStatus.equals("N") ) {this.child.getDoubt().setStatus("Y");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("O") ) {this.child.getDoubt().setStatus("O");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("Y") ) {this.child.getDoubt().setStatus("Y");this.child.getDoubt().setDoubt(true);}
        if(parentDoubtStatus.equals("N") &&  childDoubtStatus.equals("N") ) {this.child.getDoubt().setStatus("N");this.child.getDoubt().setDoubt(true);}
        
        //this.child.getDoubt().setDoubt(this.parent.getDoubt().isDoubt());
        
        
        
        
        
        
        //At the end:
         System.out.println("fend.session.node.jobs.dependencies.Inherit21.<init>(): at the end of the iteration for "+parent.getJobStepText()+" : "+child.getJobStepText());
            for (Iterator<SubSurfaceHeaders> iterator = psubs.iterator(); iterator.hasNext();) {
                 SubSurfaceHeaders next = iterator.next();
                 System.out.println("Inh: "+parent.getJobStepText()+" :    "+next.getSubsurface()+" :dep: "+next.isDependency()+" :doubt: "+next.getDoubt().isDoubt()+ " :stat: "+next.getDoubt().getStatus());
                 
             }for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
                 SubSurfaceHeaders next = iterator.next();
                 System.out.println("Inh: "+child.getJobStepText()+" :    "+next.getSubsurface()+" :dep: "+next.isDependency()+" :doubt: "+next.getDoubt().isDoubt()+ " :stat: "+next.getDoubt().getStatus());
                 
             }
    }
    
    
    
     private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
         System.out.println("fend.session.node.jobs.dependencies.Inherit21.calculateSubsInJob(): entered");
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
        
        System.out.println("fend.session.node.jobs.dependencies.Inherit21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
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
        
        System.out.println("fend.session.node.jobs.dependencies.Inherit21.calculateSubsInJob(): returning sublist of size: "+subsInJob.size());
        return subsInJob;
        }
        
        
        else{
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
     
        
        
        
        
        
        
    }
}
