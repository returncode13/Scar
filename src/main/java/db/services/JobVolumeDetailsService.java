/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobVolumeDetailsService {
     public void createJobVolumeDetails(JobVolumeDetails jvd);
    public JobVolumeDetails getJobVolumeDetails(Long jvdId);
    public void updateJobVolumeDetails(Long jvdId,JobVolumeDetails newJvd);
    public void deleteJobVolumeDetails(Long jvdId);
    
    public List<JobVolumeDetails> getJobVolumeDetails(JobStep js);
    public List<JobVolumeDetails> getJobVolumeDetails(Volume v);
    
    public List<Volume> getVolumesFor(JobStep js);              //convenience function to get volume records from the Volume table where foreign key =js
    public void updateVolumesFor(JobStep js,List<Volume> volumes);//update existing volume records in the Volume table where foreign key =js
    public void deleteVolumesFor(JobStep js);                      //delete all volume records in the volume table where foreign key=js
    public List<JobStep> getJobStepContaining(Volume v);                  //convenience function to return the foreign key for the pk=v.getPK();
    public JobVolumeDetails getJobVolumeDetails(JobStep js, Volume v);
}
