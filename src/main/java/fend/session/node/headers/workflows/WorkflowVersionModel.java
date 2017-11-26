package fend.session.node.headers.workflows;

import fend.session.node.volumes.type0.VolumeSelectionModelType0;
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
    private VolumeSelectionModelType0 volumeSelectionModel;
    
    public WorkflowVersionModel(VolumeSelectionModelType0 volmodel) {
        this.volumeSelectionModel=volmodel;
    }
    
    public List<WorkflowVersionTabModel> getWfmodel() {
        return wfmodel;
    }

    public void setWfmodel(List<WorkflowVersionTabModel> wfmodel) {
        this.wfmodel = wfmodel;
    }

    public VolumeSelectionModelType0 getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    public void setVolumeSelectionModel(VolumeSelectionModelType0 volumeSelectionModel) {
        this.volumeSelectionModel = volumeSelectionModel;
    }
    
    
    
}
