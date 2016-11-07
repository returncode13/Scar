/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes;

import fend.session.node.headers.HeaderTableModel;
import java.io.File;
import java.util.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    //for Debug
    private Long id;

        
    
    
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
        System.out.println("VSModel: setting label to : "+volumeSelected);
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

   
    
}
