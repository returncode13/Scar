package fend.session.node.headers.workflows;

import fend.session.node.volumes.type1.VolumeSelectionModelType1;
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
    private VolumeSelectionModelType1 volumeSelectionModel;
    
    public WorkflowVersionModel(VolumeSelectionModelType1 volmodel) {
        this.volumeSelectionModel=volmodel;
    }
    
    public List<WorkflowVersionTabModel> getWfmodel() {
        return wfmodel;
    }

    public void setWfmodel(List<WorkflowVersionTabModel> wfmodel) {
        this.wfmodel = wfmodel;
    }

    public VolumeSelectionModelType1 getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    public void setVolumeSelectionModel(VolumeSelectionModelType1 volumeSelectionModel) {
        this.volumeSelectionModel = volumeSelectionModel;
    }
    
    
    
}
