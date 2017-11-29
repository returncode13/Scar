/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.acquisition;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionVolumeModel implements VolumeSelectionModelType0{
    final private Long type=3L;
    private Long id=Long.valueOf(UUID.randomUUID().getMostSignificantBits()+"");
    private String label="AcqVol";
    private HeadersModel headersModel=new HeadersModel(this);
    private Set<SubSurfaceHeaders> subsurfaces;
    
    @Override
    public Long getType() {
        return type;
    }

    @Override
    public Long getId() {

            return id;
    }

    @Override
    public String getLabel() {
            return label;
    }

    @Override
    public Long getVolumeType() {
        return type;
    }

    @Override
    public Boolean getHeaderButtonStatus() {
        return true;
    }

    @Override
    public File getVolumeChosen() {
        return new File("");
    }

    @Override
    public void startWatching() {
        
    }

    @Override
    public void setVolumeChosen(File file) {
        
    }

    

    @Override
    public void setAlert(Boolean alert) {
        
    }

    @Override
    public void setLabel(String nameVolume) {
        
    }

    

    @Override
    public void setVolumeType(Long volumeType) {
        
    }

    

    @Override
    public void setSubsurfaces(Set<SubSurfaceHeaders> sl) {
        subsurfaces=sl;
    }

    @Override
    public void setHeadersModel(HeadersModel hmod) {
        this.headersModel = hmod;
    }

    @Override
    public void setHeaderButtonStatus(Boolean b) {
        
    }

    @Override
    public void setId(Long idVolume) {
        id=idVolume;
    }

    @Override
    public void setInflated(Boolean b) {
        
    }

    public ObservableValue<String> getRunStatus() {
        return new SimpleStringProperty("acquired");                    //this can be changed to NTBP/Acquired , but that needs to come from the view in the database
    }

    public ObservableValue<String> getDependency() {
        return new SimpleStringProperty("Ok");
    }

    public ObservableValue<Boolean> getInsight() {
        return new SimpleBooleanProperty(true);
    }

    public ObservableValue<String> getInsightString(){
        return new SimpleStringProperty("");
    }
    
    public ObservableValue<String> getWorkflowVersion() {
        return new SimpleStringProperty("v0");
    }

    public ObservableValue<String> getQcStatus() {
        return new SimpleStringProperty("v0");
    }

    @Override
    public HeadersModel getHeadersModel() {
        return headersModel;
    }

    @Override
    public Set<SubSurfaceHeaders> getSubsurfaces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubSurfaceHeaders> getSubSurfaceHeadersToBeSummarized() {
        return new ArrayList<>();       //return maybe just the latest set of subsurfaces
    }

    @Override
    public Map<String, SubSurfaceHeaders> getSubsurfaceNameSubSurfaceHeaderMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JobStepType0Model getParentJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
