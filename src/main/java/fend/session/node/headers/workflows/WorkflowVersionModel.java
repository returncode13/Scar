package fend.session.node.headers.workflows;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adira0150
 */
public class WorkflowVersionModel {
    List<WorkflowVersionTabModel> wfmodel=new ArrayList<>();

    public List<WorkflowVersionTabModel> getWfmodel() {
        return wfmodel;
    }

    public void setWfmodel(List<WorkflowVersionTabModel> wfmodel) {
        this.wfmodel = wfmodel;
    }
    
    
}
