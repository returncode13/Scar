/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.volumes.VolumeSelectionModel;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author adira0150
 */
public class SummaryVolumeNodeModel {
   
    private VolumeSelectionModel volumeSelectionModel;
    private final ObjectProperty<QCModel> qcmodel = new SimpleObjectProperty<>(this,"qcmodel");
    
    public QCModel getQcmodel() {
    return qcmodel.get();
    }
    
    public void setQcmodel(QCModel value) {
    qcmodel.set(value);
    }
    
    public ObjectProperty qcmodelProperty() {
    return qcmodel;
    }
    
    /*private List<QCModel> listOfQcModels=new ArrayList<>();
    
    public List<QCModel> getListOfQcModels() {
    return listOfQcModels;
    }
    
    public void setListOfQcModels(List<QCModel> listOfQcModels) {
    this.listOfQcModels = listOfQcModels;
    }
    */

    public VolumeSelectionModel getVolumeSelectionModel() {
        return volumeSelectionModel;
    }

    public void setVolumeSelectionModel(VolumeSelectionModel volumeSelectionModel) {
        this.volumeSelectionModel = volumeSelectionModel;
    }
    
    
    
    public SummaryVolumeNodeModel() {
    }
    
    
}
