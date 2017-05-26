package fend.session.node.headers.workflows;

import fend.session.node.volumes.VolumeSelectionModel;
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
    private VolumeSelectionModel volumeSelectionModel;
    
    public WorkflowVersionModel(VolumeSelectionModel volmodel) {
        this.volumeSelectionModel=volmodel;
    }
    
    public List<WorkflowVersionTabModel> getWfmodel() {
        return wfmodel;
    }

    public void setWfmodel(List<WorkflowVersionTabModel> wfmodel) {
        this.wfmodel = wfmodel;
    }

    public VolumeSelectionModel getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    public void setVolumeSelectionModel(VolumeSelectionModel volumeSelectionModel) {
        this.volumeSelectionModel = volumeSelectionModel;
    }
    
    
    
}
