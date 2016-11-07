/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.JobVolumeDetailsDAO;
import db.dao.JobVolumeDetailsDAOImpl;
import java.util.List;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public class JobVolumeDetailsServiceImpl implements JobVolumeDetailsService{
    
    JobVolumeDetailsDAO jvdDao=new JobVolumeDetailsDAOImpl();
    
    @Override
    public void createJobVolumeDetails(JobVolumeDetails jvd) {
        jvdDao.createJobVolumeDetails(jvd);
    }

    @Override
    public JobVolumeDetails getJobVolumeDetails(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateJobVolumeDetails(Long jvdId, JobVolumeDetails newJvd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteJobVolumeDetails(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeDetails> getJobVolumeDetails(JobStep js) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeDetails> getJobVolumeDetails(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Volume> getVolumesFor(JobStep js) {
        return jvdDao.getVolumesFor(js);
    }

    @Override
    public void updateVolumesFor(JobStep js, List<Volume> volumes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteVolumesFor(JobStep js) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobStep> getJobStepContaining(Volume v) {
      return jvdDao.getJobStepContaining(v);
    }

    @Override
    public JobVolumeDetails getJobVolumeDetails(JobStep js, Volume v) {
        return jvdDao.getJobVolumeDetails(js, v);
    }
    
}
