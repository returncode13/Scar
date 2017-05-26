/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.workflows;

import de.cismet.custom.visualdiff.WorkflowDifferenceModel;

/**
 *
 * @author adira0150
 */
public class WorkflowDifferenceFrameModel {
    WorkflowDifferenceModel workflowDifferenceModel;

    public void setDifferenceModel(WorkflowDifferenceModel workflowDifferenceModel) {
        this.workflowDifferenceModel = workflowDifferenceModel;
    }
    
    
    
    public WorkflowDifferenceModel getDifferenceModel() {
        return this.workflowDifferenceModel;
    }
    
}
