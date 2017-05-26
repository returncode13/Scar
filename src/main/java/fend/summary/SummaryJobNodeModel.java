/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.jobs.type0.JobStepType0Model;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author adira0150
 */
public class SummaryJobNodeModel {

    /*private final ObjectProperty<VolumeNodeModel> volumenode = new SimpleObjectProperty<>(this,"volumenode");
    
    public SummaryVolumeNodeModel getVolumenode() {
    return volumenode.get();
    }
    
    public void setVolumenode(SummaryVolumeNodeModel value) {
    volumenode.set(value);
    }
    
    public ObjectProperty volumenodeProperty() {
    return volumenode;
    }
    
    public SummaryJobNodeModel() {
    }*/
    
    private List<SummaryVolumeNodeModel> listOfVolumes=new ArrayList<>();

    public List<SummaryVolumeNodeModel> getListOfVolumes() {
        return listOfVolumes;
    }

    public void setListOfVolumes(List<SummaryVolumeNodeModel> listOfVolumes) {
        this.listOfVolumes = listOfVolumes;
    }

    public SummaryJobNodeModel() {
    }
    
    private JobStepType0Model jobsteptype0model;

    public JobStepType0Model getJobsteptype0model() {
        return jobsteptype0model;
    }

    public void setJobsteptype0model(JobStepType0Model jobsteptype0model) {
        this.jobsteptype0model = jobsteptype0model;
    }
    
    
            
    
}
