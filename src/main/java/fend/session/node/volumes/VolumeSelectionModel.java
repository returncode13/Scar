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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import watcher.LogWatcher;
import watcher.VolumeWatcher;

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
    
    private HeadersModel headersModel=new HeadersModel(this);                                    // the headers corresponding to this particular volume.
    private Set<SubSurface> subsurfaces;                                       //the subsurfaces in the volume.
    private MultiMap<Long,SubSurface> seqSubsMap=new MultiValueMap<>();
    private String insightVersionUsed;
    private VolumeWatcher volTimerTask=null;                                   //watch for any new subs in the volume
    private LogWatcher logTimerTask=null;                                   //watch for any new logs in ../000scratch/logs
    //for Debug

    private Long id;
    private Long volumeType;
    
    public HeadersModel getHeadersModel() {
        
        return headersModel;
    }

    public void setHeadersModel(HeadersModel headersModel) {
        this.headersModel = headersModel;
        //this.headersModel.setVolmodel(this);
    }

    

    public Set<SubSurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(Set<SubSurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }

        
    
    
    public VolumeSelectionModel(String volumeSelectionLabel,Boolean toBeInflated,Long volumeType) {
        ++i;
        this.volumeSelectionLabel = new SimpleStringProperty(i+" "+volumeSelectionLabel);
        
         
        this.Inflated=toBeInflated;
        this.volumeType=volumeType;
     
    }
    
     public VolumeSelectionModel(Boolean toBeinflated,long volumeType) {
        
        this(new String ("choose vol: "),toBeinflated,volumeType);
       //  this.headersModel.setVolmodel(this);
    }
    
    public VolumeSelectionModel(Long volumeType){
        this(new String ("choose vol: "),false,volumeType);
         //this.headersModel.setVolmodel(this);
        
        
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
       // this.qcFlagProperty=new SimpleBooleanProperty(b);
        
    }

    public MultiMap<Long, SubSurface> getSeqSubsMap() {
        return seqSubsMap;
    }

    
    
    public void setSeqSubsMap(MultiMap<Long, SubSurface> seqSubsMap) {
        this.seqSubsMap = seqSubsMap;
    }

    
    public void printQC(){
        System.out.println("volumeSelectionModel: QcFlag status "+qcFlagProperty.get());
    }
    
    
   private void startVolumeWatching(){
       System.out.println("fend.session.node.volumes.VolumeSelectionModel.startVolumeWatching():  starting to watch the Volume");
       if(volTimerTask==null){
           volTimerTask=new VolumeWatcher(volumeChosen.getAbsolutePath(),"idx");
       }
       
   }

    private void startLogWatching() {
        System.out.println("fend.session.node.volumes.VolumeSelectionModel.startLogWatching():  starting to watch logs for:  "+volumeChosen.getName());
        String logPath=volumeChosen.getAbsolutePath();
        logPath=logPath+"/../../000scratch/logs";
        if(logTimerTask==null) {
            System.out.println("fend.session.node.volumes.VolumeSelectionModel.startLogWatching():  initiating logwatcher for :  "+volumeChosen.getName());
            logTimerTask=new LogWatcher(logPath,"",this);
        }
    }

    public void startWatching() {
        
         ExecutorService executorService = Executors.newFixedThreadPool(10);
         executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //startVolumeWatching();
                
                startLogWatching();
            return null;
            }
            
        });
        
        
        
       
    }

    public Long getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(Long volumeType) {
        this.volumeType = volumeType;
    }

    
    
}
