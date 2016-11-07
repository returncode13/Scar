/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.JobStep;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobStepService {
    public void createJobStep(JobStep js);
    public JobStep getJobStep(Long jobId);
    public void updateJobStep(Long jobId,JobStep newJs);
    public void deleteJobStep(Long jobId);
    
    
    public void startAlert(JobStep js);
    public void stopAlert(JobStep js);
    public List<JobStep> listJobSteps();
    
   
}
