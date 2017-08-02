/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mid.doubt.qc;

import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;

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
    
}
