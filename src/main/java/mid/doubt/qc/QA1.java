/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.qc;

import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Qc check between Acquisition and JobStepType1Model jobs. Nothing to do here here yet.
 */
public class QA1 {
     private AcquisitionJobStepModel parent;
    private JobStepType1Model child;

    public QA1(JobStepType0Model parent, JobStepType0Model child) {
        this.parent=(AcquisitionJobStepModel) parent;
        this.child=(JobStepType1Model) child;
    }
    
    
    
}
