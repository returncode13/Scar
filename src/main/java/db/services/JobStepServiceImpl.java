/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.JobStepDAO;
import db.dao.JobStepDAOImpl;
import java.util.List;
import java.util.Set;
import db.model.JobStep;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public class JobStepServiceImpl implements JobStepService {
    
    JobStepDAO jobStepDAO= new JobStepDAOImpl();

    public JobStepServiceImpl() {
    }

        
    @Override
    public void createJobStep(JobStep js) {
        jobStepDAO.createJobStep(js);
    }

    @Override
    public JobStep getJobStep(Long jobId) {
        return jobStepDAO.getJobStep(jobId);
    }

    @Override
    public void updateJobStep(Long jobId, JobStep newJs) {
        jobStepDAO.updateJobStep(jobId, newJs);
    }

    @Override
    public void deleteJobStep(Long jobId) {
        jobStepDAO.deleteJobStep(jobId);
    }

    @Override
    public List<JobStep> listJobSteps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startAlert(JobStep js) {
   jobStepDAO.startAlert(js);   }

    @Override
    public void stopAlert(JobStep js) {
    jobStepDAO.stopAlert(js);    }

    /*@Override
    public void setPending(JobStep js) {
    jobStepDAO.setPending(js);
    }
    
    @Override
    public void resetPending(JobStep js) {
    jobStepDAO.resetPending(js);
    }*/

    
    
}
