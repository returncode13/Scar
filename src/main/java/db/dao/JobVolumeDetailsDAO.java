/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobVolumeDetailsDAO {
    public void createJobVolumeDetails(JobVolumeDetails jvd);
    public JobVolumeDetails getJobVolumeDetails(Long jvdId);
    public void updateJobVolumeDetails(Long jvdId,JobVolumeDetails newJvd);
    public void deleteJobVolumeDetails(Long jvdId);
    
    public List<JobVolumeDetails> getJobVolumeDetails(JobStep js);
    public List<JobVolumeDetails> getJobVolumeDetails(Volume v);
    public JobVolumeDetails getJobVolumeDetails(JobStep js, Volume v);

    public List<JobStep> getJobStepContaining(Volume v);        //convenience function to return the foreign key for the pk=v.getPK();
    public List<Volume> getVolumesFor(JobStep js);              
    
}
