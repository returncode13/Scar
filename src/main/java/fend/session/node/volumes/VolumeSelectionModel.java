/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes;


import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author naila0152
 */
public class VolumeSelectionModel {
    private static Long i=0L;
    private final StringProperty volumeSelectionLabel;
    
    private File volumeChosen;
    private boolean Inflated=false;
    private boolean headerButtonIsDisabled=true;
    private final BooleanProperty headerButtonDisabledStatusProperty=new SimpleBooleanProperty(headerButtonIsDisabled);
    private boolean alert=false;
    private BooleanProperty qcFlagProperty=new SimpleBooleanProperty(Boolean.FALSE);
    
    private HeadersModel headersModel=new HeadersModel();                                    // the headers corresponding to this particular volume.
    private Set<SubSurface> subsurfaces;                                       //the subsurfaces in the volume.
    private MultiMap<Long,SubSurface> seqSubsMap=new MultiValueMap<>();
    
    
    //for Debug
    private Long id;

    public HeadersModel getHeadersModel() {
        return headersModel;
    }

    public void setHeadersModel(HeadersModel headersModel) {
        this.headersModel = headersModel;
    }

    

    public Set<SubSurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(Set<SubSurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }

        
    
    
    public VolumeSelectionModel(String volumeSelectionLabel,Boolean toBeInflated) {
        ++i;
        this.volumeSelectionLabel = new SimpleStringProperty(i+" "+volumeSelectionLabel);
        
        
        this.Inflated=toBeInflated;
     
    }
    
     public VolumeSelectionModel(Boolean toBeinflated) {
        this(new String ("choose vol: "),toBeinflated);
    }
    
    public VolumeSelectionModel(){
        this(new String ("choose vol: "),false);
        
        
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public Long getId() {
        return id;
    }

    
    public boolean isHeaderButtonIsDisabled() {
        return headerButtonIsDisabled;
    }

    public void setHeaderButtonIsDisabled(boolean headerButtonIsDisabled) {
        this.headerButtonIsDisabled = headerButtonIsDisabled;
    }


    public BooleanProperty getHeaderButtonDisabledStatusProperty() {
        return headerButtonDisabledStatusProperty;
    }
    
    
    public void setHeaderButtonStatus(Boolean b){
        headerButtonDisabledStatusProperty.setValue(b);
    }
    
    public Boolean getHeaderButtonStatus(){
        return headerButtonDisabledStatusProperty.get();
    }
    
    public StringProperty getVolumeSelectionLabel() {
        //System.out.println("VSModel:  returning "+volumeSelectionLabel.get());
        return volumeSelectionLabel;
    }
    
    public String getLabel(){
        return volumeSelectionLabel.get();
    }
    public void setLabel(String volumeSelected){
       // System.out.println("VSModel: setting label to : "+volumeSelected);
        volumeSelectionLabel.set(volumeSelected);
    }

    public boolean isInflated() {
        return Inflated;
    }

    public void setInflated(boolean Inflated) {
        this.Inflated = Inflated;
    }

    public File getVolumeChosen() {
        return volumeChosen;
    }

    public void setVolumeChosen(File volumeChosen) {
        this.volumeChosen = volumeChosen;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public BooleanProperty getQcFlagProperty() {
        return qcFlagProperty;
    }

    public void setQcFlagProperty(Boolean b) {
        this.qcFlagProperty.set(b);
    }

    public MultiMap<Long, SubSurface> getSeqSubsMap() {
        return seqSubsMap;
    }

    
    
    public void setSeqSubsMap(MultiMap<Long, SubSurface> seqSubsMap) {
        this.seqSubsMap = seqSubsMap;
    }

    
    
    
    
   
    
}
