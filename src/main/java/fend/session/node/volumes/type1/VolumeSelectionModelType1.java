/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1;


import db.model.QcMatrix;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.qcTable.QcMatrixModel;
import fend.session.node.volumes.type1.qcTable.QcTableModel;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListModel;
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
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import watcher.LogWatcher;
import watcher.VolumeWatcher;

/**
 *
 * @author naila0152
 */
public class VolumeSelectionModelType1 implements VolumeSelectionModelType0 {
    private final Long type=1L;
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
    //private Map<Long,StringProperty> logstatusMapForSeq=new HashMap<>();       //this is the map to hold the run status from the latest log for each seq. Remember that the seq key is not the sequence object but just a number of type Long
    //for Debug                                                             //to get the seq object use the getSequenceObjBySequenceNumber() call inside HeadersModel class
    
  //  private Set<Long> wfVersionsUsedinVolume=new HashSet<>();
    
    private Long id;
    private Long volumeType;
    private final MapProperty<Long, StringProperty> logstatusMapForSeq = new SimpleMapProperty<>();
    private JobStepType0Model parentjob;
    /*    private  QcTableModel qcTableModel;
    private qcCheckListModel qcCheckListModel;
    private JobStepType0Model parentjob;
    private QcMatrixModel qcMatrixModel;
    
    public QcMatrixModel getQcMatrixModel() {
    if(qcMatrixModel==null) {
    qcMatrixModel=new QcMatrixModel();
    qcMatrixModel.setVmodel(this);
    }
    return qcMatrixModel;
    }
    
    public void setQcMatrixModel(QcMatrixModel qcMatrixModel) {
    this.qcMatrixModel = qcMatrixModel;
    }
    
    
    
    public JobStepType0Model getParentjob() {
    return parentjob;
    }
    
    public void setParentjob(JobStepType0Model parentjob) {
    this.parentjob = parentjob;
    }
    
    
    
    
    
    public QcTableModel getQcTableModel() {
    if(qcTableModel==null){
    qcTableModel=new QcTableModel();
    }
    return qcTableModel;
    }
    
    public void setQcTableModel(QcTableModel qcMatrixModel) {
    this.qcTableModel = qcMatrixModel;
    }
    
    public qcCheckListModel getQcCheckListModel() {
    if(qcCheckListModel==null){
    qcCheckListModel=new qcCheckListModel();
    }
    return qcCheckListModel;
    }
    
    public void setQcCheckListModel(qcCheckListModel qcCheckListModel) {
    this.qcCheckListModel = qcCheckListModel;
    }
    */

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

    /*public Set<Long> getWfVersionsUsedinVolume() {
    return wfVersionsUsedinVolume;
    }
    
    public void setWfVersionsUsedinVolume(Set<Long> wfVersionsUsedinVolume) {
    this.wfVersionsUsedinVolume = wfVersionsUsedinVolume;
    }*/
    
    
    
    
    /*
    public Map<Long, StringProperty> getLogstatusMapForSeq() {
    return logstatusMapForSeq;
    }
    
    public void addToLogstatusMapForSeq(Map<Long, StringProperty> logstatusMapForSeq) {
    this.logstatusMapForSeq = logstatusMapForSeq;
    }
    */
    public void addToLogstatusMapForSeq(Long seq,StringProperty s){
        logstatusMapForSeq.put(seq,s);
    }
    
    
    
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

        
    
    
    public VolumeSelectionModelType1(String volumeSelectionLabel,Boolean toBeInflated,Long volumeType,JobStepType0Model pjob) {
        ++i;
        this.volumeSelectionLabel = new SimpleStringProperty(i+" "+volumeSelectionLabel);
        this.parentjob=pjob;
         
        this.Inflated=toBeInflated;
        this.volumeType=volumeType;
     
    }
    
     public VolumeSelectionModelType1(Boolean toBeinflated,long volumeType,JobStepType0Model pjob) {
        
        this(new String ("choose vol: "),toBeinflated,volumeType,pjob);
       //  this.headersModel.setVolmodel(this);
    }
    
    public VolumeSelectionModelType1(Long volumeType,JobStepType0Model pjob){
        this(new String ("choose vol: "),false,volumeType, pjob);
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
