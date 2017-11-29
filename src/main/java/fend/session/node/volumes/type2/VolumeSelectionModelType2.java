

package fend.session.node.volumes.type2;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import watcher.LogWatcher;
import watcher.VolumeWatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeSelectionModelType2 implements VolumeSelectionModelType0{
     private final Long type=2L;
    private static Long i=0L;
    private final StringProperty volumeSelectionLabel;
    
    private File volumeChosen;
    private boolean Inflated=false;
    private boolean headerButtonIsDisabled=true;
    private final BooleanProperty headerButtonDisabledStatusProperty=new SimpleBooleanProperty(headerButtonIsDisabled);
    private boolean alert=false;
    private BooleanProperty dependency=new SimpleBooleanProperty(Boolean.TRUE);
    
    private HeadersModel headersModel=new HeadersModel(this);                                    // the headers corresponding to this particular volume.
    private Set<SubSurfaceHeaders> subsurfaces;                                       //the subsurfaces in the volume.
    private MultiMap<Long,SubSurfaceHeaders> seqSubsMap=new MultiValueMap<>();
    private String insightVersionUsed;
    private VolumeWatcher volTimerTask=null;                                   //watch for any new subs in the volume
    private LogWatcher logTimerTask=null;                                   // 
    //private Map<Long,StringProperty> logstatusMapForSeq=new HashMap<>();       //this is the map to hold the run status from the latest log for each seq. Remember that the seq key is not the sequence object but just a number of type Long
    //for Debug                                                             //to get the seq object use the getSequenceObjBySequenceNumber() call inside HeadersModel class
    
  //  private Set<Long> wfVersionsUsedinVolume=new HashSet<>();
    
    private Long id;
    private Long volumeType;
    private final MapProperty<Long, StringProperty> logstatusMapForSeq = new SimpleMapProperty<>();
    private JobStepType0Model parentjob;
    
    private Map<String,SubSurfaceHeaders> subsurfaceNameSubSurfaceHeaderMap=new HashMap<>();
    
    
    
    @Override
    public Long getType() {
        return type;
    }
    
    
    
    
    public ObservableMap getLogstatusMapForSeq() {
        return logstatusMapForSeq.get();
    }

    public void setLogstatusMapForSeq(ObservableMap value) {
        logstatusMapForSeq.set(value);
    }

    public MapProperty logstatusMapForSeqProperty() {
        return logstatusMapForSeq;
    }

   
    public void addToLogstatusMapForSeq(Long seq,StringProperty s){
        logstatusMapForSeq.put(seq,s);
    }
    
    
    
    public HeadersModel getHeadersModel() {
        
        return headersModel;
    }

    @Override
    public void setHeadersModel(HeadersModel headersModel) {
        this.headersModel = headersModel;
        //this.headersModel.setVolmodel(this);
    }

    

    public Set<SubSurfaceHeaders> getSubsurfaces() {
        return subsurfaces;
    }

    @Override
    public void setSubsurfaces(Set<SubSurfaceHeaders> subsurfaces) {
        this.subsurfaces = subsurfaces;
        subsurfaceNameSubSurfaceHeaderMap.clear();
        for (Iterator<SubSurfaceHeaders> iterator = subsurfaces.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders next = iterator.next();
            System.out.println("fend.session.node.volumes.type1.VolumeSelectionModelType2.setSubsurfaces(): "+next.getSubsurface()+" UTime: "+next.getUpdateTime()+" STime: "+next.getSummaryTime());
            subsurfaceNameSubSurfaceHeaderMap.put(next.getSubsurface(), next);
        }
    }

        
    
    
    public VolumeSelectionModelType2(String volumeSelectionLabel,Boolean toBeInflated,Long volumeType,JobStepType0Model pjob) {
        ++i;
        this.volumeSelectionLabel = new SimpleStringProperty(i+" "+volumeSelectionLabel);
        this.parentjob=pjob;
         
        this.Inflated=toBeInflated;
        this.volumeType=volumeType;
     
    }
    
     public VolumeSelectionModelType2(Boolean toBeinflated,long volumeType,JobStepType0Model pjob) {
        
        this(new String ("choose vol: "),toBeinflated,volumeType,pjob);
       //  this.headersModel.setVolmodel(this);
    }
    
    public VolumeSelectionModelType2(Long volumeType,JobStepType0Model pjob){
        this(new String ("choose vol: "),false,volumeType, pjob);
         //this.headersModel.setVolmodel(this);
        
        
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
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
    
    @Override
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
    
    @Override
    public void setLabel(String volumeSelected){
       // System.out.println("VSModel: setting label to : "+volumeSelected);
        volumeSelectionLabel.set(volumeSelected);
    }

    public boolean isInflated() {
        return Inflated;
    }
    
    @Override
    public void setInflated(Boolean Inflated) {
        this.Inflated = Inflated;
    }
    
    @Override
    public File getVolumeChosen() {
        return volumeChosen;
    }
    
    @Override
    public void setVolumeChosen(File volumeChosen) {
        this.volumeChosen = volumeChosen;
        
    }

     public boolean isAlert() {
    return alert;
    }
    
    public void setAlert(Boolean alert) {
    this.alert = alert;
    }

    public BooleanProperty getDependency() {
        return dependency;
    }

    public void setDependency(Boolean b) {
        this.dependency.set(b);
       // this.dependency=new SimpleBooleanProperty(b);
        
    }

    public MultiMap<Long, SubSurfaceHeaders> getSeqSubsMap() {
        return seqSubsMap;
    }

    
    
    public void setSeqSubsMap(MultiMap<Long, SubSurfaceHeaders> seqSubsMap) {
        this.seqSubsMap = seqSubsMap;
    }

    
    public void printQC(){
        System.out.println("volumeSelectionModel: QcFlag status "+dependency.get());
    }
    
    
   private void startVolumeWatching(){
       System.out.println("fend.session.node.volumes.VolumeSelectionModelType2.startVolumeWatching():  starting to watch the Volume");
       if(volTimerTask==null){
           volTimerTask=new VolumeWatcher(volumeChosen.getAbsolutePath(),"idx");
       }
       
   }
  
   private void startLogWatching() {
   System.out.println("fend.session.node.volumes.VolumeSelectionModelType2.startLogWatching():  starting to watch logs for:  "+volumeChosen.getName());
   String logPath=volumeChosen.getAbsolutePath();
   logPath=logPath+"/logs/";
   if(logTimerTask==null) {
   System.out.println("fend.session.node.volumes.VolumeSelectionModelType2.startLogWatching():  initiating logwatcher for :  "+volumeChosen.getName());
   logTimerTask=new LogWatcher(logPath,"",this);
   }
   }
    
    @Override
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
    
    @Override
    public Long getVolumeType() {
        return volumeType;
    }
    
    @Override
    public void setVolumeType(Long volumeType) {
        this.volumeType = volumeType;
    }

    @Override
    public List<SubSurfaceHeaders> getSubSurfaceHeadersToBeSummarized() {
        List<SubSurfaceHeaders> subsToBeSummarized=new ArrayList<>();
        for (Iterator<SubSurfaceHeaders> iterator = subsurfaces.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders sub = iterator.next();
            if(sub.getUpdateTime().compareTo(sub.getSummaryTime())>0){       //if updateTime > summaryTime
                subsToBeSummarized.add(sub);
            }
            
        }
        return subsToBeSummarized;
    }

    @Override
    public Map<String, SubSurfaceHeaders> getSubsurfaceNameSubSurfaceHeaderMap() {
        return subsurfaceNameSubSurfaceHeaderMap;
    }

    @Override
    public JobStepType0Model getParentJob() {
        return parentjob;
    }

}
