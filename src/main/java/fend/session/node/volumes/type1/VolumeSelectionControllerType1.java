/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type1;


import collector.HeaderCollector;
import db.model.Headers;
import db.model.QcMatrix;
import db.model.QcType;
import db.model.Sessions;
import db.model.Volume;
import db.services.QcMatrixService;
import db.services.QcMatrixServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.SessionModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.HeadersNode;
import fend.session.node.headers.HeadersViewController;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type0.VolumeSelectionControllerType0;
import fend.session.node.volumes.type1.qcTable.QcMatrixModel;
import fend.session.node.volumes.type1.qcTable.QcTableNode;
import fend.session.node.volumes.type1.qcTable.QcTypeModel;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListModel;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListNode;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.controlsfx.control.CheckModel;
import watcher.VolumeWatcher;

/**
 *
 * @author naila0152
 */
public class VolumeSelectionControllerType1 implements VolumeSelectionControllerType0  {
    
    final private DirectoryChooser dirChooser = new DirectoryChooser();
    private HeaderCollector hcollector=new HeaderCollector();
    private Long id;
    private QcTypeService qserv=new QcTypeServiceImpl();
    private JobStepType0Model parentjob;
    private SessionsService sserv=new SessionsServiceImpl();
    private VolumeService vserv=new VolumeServiceImpl();
    private QcMatrixService qcmserv=new QcMatrixServiceImpl();
    
    final private ChangeListener<String> VOLUME_LABEL_CHANGE_LISTENER=new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
           // System.out.println("VSController: Changed from "+oldValue+ " to "+newValue);
            updateVolumeSelectionLabelView(newValue);
            
            if(newValue!=null){
              //updateHeaderButton(Boolean.FALSE);
              updateHeaderButton();
            }
                
        }
    };
    
    
    final private ChangeListener<Boolean> HEADER_BUTTON_CHANGE_LISTENER=new ChangeListener<Boolean>(){

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          //  System.out.println("VSC: HButton Status changed from "+oldValue+" to "+ newValue);
            model.setHeaderButtonStatus(newValue);
        }
            
            };
     @FXML
    private Button showTable;
            
    @FXML
    private HBox HBox;

    @FXML
    private Button selectVolumeButton;

     @FXML
    private Button headerTableDisplayButton;

    
    @FXML
    private Label volumePathLabel;
    
    @FXML
    private CheckBox qcCheckBox;
    
    @FXML
    private Button qMatrixBtn;

    
    private VolumeSelectionModelType1 model;
    private  TableView<SubSurface> table;
     
     
    private List<TimerTask> volumeTimerTask=new ArrayList<>();
    private QcMatrixModel qcMatrixModel;
    private qcCheckListModel qcCModel;
    
    /*  @FXML
    void openQMatrix(ActionEvent event) {
    SessionModel smodel=model.getParentjob().getSessionModel();
    Sessions currentsession= sserv.getSessions(smodel.getId());
    //Check with definition of QcMatrix
    Volume v=vserv.getVolume(model.getId());
    List<QcMatrix> qcmatdef=qcmserv.getQcMatrixForVolume(v);
    qcMatrixModel=model.getQcMatrixModel();
    //  List<QcType>
    qcCheckListModel qcckmod=new qcCheckListModel();
    List<QcType> qcTypesForSession=qserv.getQcTypesForSession(currentsession);
    List<String> qcTypesNames=new ArrayList<>();
    qcCModel=model.getQcCheckListModel();
    for (Iterator<QcType> iterator = qcTypesForSession.iterator(); iterator.hasNext();) {
    QcType next = iterator.next();
    String name=next.getName();
    qcTypesNames.add(name);
    }
    qcCModel.setQcTypes(qcTypesNames);
    
    if(qcmatdef.isEmpty()){
    
    
    
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): QcMatrix not defined for current node");
    
    qcCheckListNode qcCNode=new qcCheckListNode(qcCModel);
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): selected: "+qcCModel.getCheckedTypes());
    
    
    
    
    List<String> types=qcCModel.getCheckedTypes();
    List<QcTypeModel> qctypeModels=new ArrayList<>();
    List<Long> ticked=new ArrayList<>();
    
    
    
    if(qcTypesForSession.isEmpty()){          //no qctypes declared for the entire session
    for (Iterator<String> iterator = types.iterator(); iterator.hasNext();) {
    String type = iterator.next();
    QcType q=new QcType();
    q.setName(type.toLowerCase());              //2Dstacks is the same as 2dstACks
    q.setSessions(currentsession);
    qserv.createQcType(q);
    
    
    }
    
    qcTypesForSession=qserv.getQcTypesForSession(currentsession);
    
    }
    else{                                  //if there are existing entries
    
    List<String> existingnames=new ArrayList<>();
    for (Iterator<QcType> iterator = qcTypesForSession.iterator(); iterator.hasNext();) {
    QcType qctmy = iterator.next();
    existingnames.add(qctmy.getName());
    if(types.contains(qctmy.getName())){
    ticked.add(qctmy.getIdQcType());
    }
    
    }
    List<String> remaining=new ArrayList<>(types);
    remaining.removeAll(existingnames);
    
    for (Iterator<String> iterator = remaining.iterator(); iterator.hasNext();) {
    String next = iterator.next();
    QcType qct=new QcType();
    qct.setName(next);
    qct.setSessions(currentsession);
    qserv.createQcType(qct);               //create new entries
    ticked.add(qct.getIdQcType());
    }
    
    
    qcTypesForSession=qserv.getQcTypesForSession(currentsession);    //get the list again..this time with new entries
    }
    
    
    //at this point the list qcTypesforSession is now a list with all the entries (new and old) for qctypes
    //and           the list "ticked" contains the ids of the qctype records that have been selected for the current definition
    
    
    
    
    
    List<QcTypeModel> sessionQcTypeModels=new ArrayList<>();
    for (Iterator<QcType> iterator = qcTypesForSession.iterator(); iterator.hasNext();) {
    QcType next = iterator.next();
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): retrieved from db: "+next.getName());
    //names.(next.getName());
    QcTypeModel qcTypeModel=new QcTypeModel();
    qcTypeModel.setId(next.getIdQcType());
    qcTypeModel.setName(next.getName());
    sessionQcTypeModels.add(qcTypeModel);
    qcMatrixModel.addToQcTypePresMap(qcTypeModel, Boolean.FALSE);               //initially set all qctypes to false;
    }
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): Setting the ticked ones to true");
    for (Iterator<Long> iterator = ticked.iterator(); iterator.hasNext();) {
    Long tickedid = iterator.next();
    QcType selectedType=qserv.getQcType(tickedid);
    QcTypeModel qctymod=new QcTypeModel();
    qctymod.setId(selectedType.getIdQcType());
    qctymod.setName(selectedType.getName());
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): ticked: id: "+selectedType.getIdQcType()+" :name: "+selectedType.getName());
    
    qctypeModels.add(qctymod);
    }
    
    
    
    
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): qctypeModels.size(): "+qctypeModels.size());
    for (Iterator<QcTypeModel> iterator = qctypeModels.iterator(); iterator.hasNext();) {
    QcTypeModel def = iterator.next();
    qcMatrixModel.addToQcTypePresMap(def, Boolean.TRUE);                    //set the ones checked to true;
    
    }
    
    Map<QcTypeModel,Boolean> qcmmap=qcMatrixModel.getQcTypePresMap();
    
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): Creating the Qc matrix for Volume: "+model.getLabel());
    System.out.println("fend.session.node.volumes.VolumeSelectionControllerType1.openQMatrix(): qcMatrixModel.size(): "+qcMatrixModel.getQcTypePresMap().size());
    for (Map.Entry<QcTypeModel, Boolean> entry : qcmmap.entrySet()) {
    QcTypeModel qctype = entry.getKey();
    Boolean ispres = entry.getValue();
    QcMatrix qcmatrix=new QcMatrix();
    qcmatrix.setVolume(v);
    QcType qselect=qserv.getQcType(qctype.getId());
    qcmatrix.setQctype(qselect);
    qcmatrix.setPresent(ispres);
    qcmserv.createQcMatrix(qcmatrix);                   //create the qc matrix
    
    
    }
    
    
    qcmatdef=qcmserv.getQcMatrixForVolume(v);
    }
    
    
    
    
    
    
    showPopList(qcmatdef);
    
    }
    
    
    void showPopList(List<QcMatrix> qcmatrices){
    
    qcMatrixModel.clear();
    
    
    for (Iterator<QcMatrix> iterator = qcmatrices.iterator(); iterator.hasNext();) {
    QcMatrix rec = iterator.next();
    QcType qctype=rec.getQctype();
    Volume v=rec.getVolume();
    Boolean pres=rec.getPresent();
    
    QcTypeModel qctm=new QcTypeModel();
    qctm.setId(qctype.getIdQcType());
    qctm.setName(qctype.getName());
    qcMatrixModel.addToQcTypePresMap(qctm, pres);
    
    }
    
    model.getQcTableModel().setQcMatrixModel(qcMatrixModel);
    
    //model.getQcTableModel().setQctypes(qctypeModels);
    HeadersModel hmod=model.getHeadersModel();
    List<Sequences> seqsinVol=hmod.getSequenceListInHeaders();
    model.getQcTableModel().setSequences(seqsinVol);
    
    QcTableNode qcMatrixNode=new QcTableNode(model.getQcTableModel());
    }
    
    */
     
     
    @FXML
    void handleSelectVolumeButton(ActionEvent event) {
        final File f=dirChooser.showDialog(selectVolumeButton.getScene().getWindow());
       
            if(f!=null) {
             model.setVolumeChosen(f);                    //start watching here
             model.setLabel(f.getName());
             model.setHeaderButtonStatus(Boolean.FALSE);                    //Counter intuitive !:(
                System.out.println("fend.session.node.volumes.VolumeSelectionController: starting the volume watch");
          //   model.startVolumeWatching();
            }
           // System.out.println("VSC: "+model.getId()+" label is "+model.getLabel());
    }
    
    
    @FXML
    void handleHeaderDisplayButton(ActionEvent event) {
           
           hcollector.setFeVolumeSelModel(model);    //first Click        < -- calculate and commit headers into db
           showTable.setDisable(model.getHeaderButtonStatus());
           
           //hcollector.setHeaderTableModel  on second Click
    }

    
    @FXML
    void showTable(ActionEvent event) {
        /*Stage stage=new Stage();*/
        /*HeaderTableModelBack htm=new HeaderTableModelBack();
        htm.setHeaderList(hcollector.getHeaderListForVolume());*/
        //HeaderGroup hg=new HeaderGroup(htm);
       // HeadersModel hmodel=new HeadersModel();
       // List<SubSurface> subs=hcollector.getHeaderListForVolume();
        List<Sequences> seqs=hcollector.getHeaderListForVolume(model);
        
        /*At the end of this call the model's headersmodel contains all the sequences and the models variable "subsurfaces" contains the subsurfaces in the volume.
        */
        
        
        //ObservableList<Sequences> obSeqs=FXCollections.observableList(seqs);
       // hmodel.setObsHList(obSeqs);
       // model.setHeadersModel(hmodel);
                                    
        HeadersNode hnode=new HeadersNode(model.getHeadersModel(),0); //displays the table. see method setView();
        HeadersViewController hvc=hnode.getHeadersViewController();   
        

           
           
    }

    public VolumeSelectionModelType1 getModel() {
        return model;
    }

    public void setModel(VolumeSelectionModelType1 model) {
        if(this.model!=null)removeModelListeners();
        
        this.model = model;
        this.model.setId(id);
        //this.model.setToBeInflated(false);
        //System.out.println("VSController: setModel()");
        setupModelListeners();
        updateView();
    }
    
    private void removeModelListeners(){
      //  System.out.println("VSController: removeMListener");
        model.getVolumeSelectionLabel().removeListener(VOLUME_LABEL_CHANGE_LISTENER);
        model.getHeaderButtonDisabledStatusProperty().removeListener(HEADER_BUTTON_CHANGE_LISTENER);
        
       volumePathLabel.accessibleTextProperty().unbindBidirectional(model.getVolumeSelectionLabel());
        headerTableDisplayButton.disableProperty().unbindBidirectional(model.getHeaderButtonDisabledStatusProperty());
        qcCheckBox.selectedProperty().unbind();
    }
    
    private void setupModelListeners(){
       // System.out.println("VSController: addMListener");
        model.getVolumeSelectionLabel().addListener(VOLUME_LABEL_CHANGE_LISTENER);
        model.getHeaderButtonDisabledStatusProperty().addListener(HEADER_BUTTON_CHANGE_LISTENER);
       // System.out.println("VSController: binding");
        volumePathLabel.accessibleTextProperty().bindBidirectional(model.getVolumeSelectionLabel());
        headerTableDisplayButton.disableProperty().bindBidirectional(model.getHeaderButtonDisabledStatusProperty());
        System.out.println("VolumeSelectioncontroller.  binding qcCheckBox: with Property Value: "+model.getQcFlagProperty());
        qcCheckBox.selectedProperty().bind(model.getQcFlagProperty());
        //headerTableDisplayButton.defaultButtonProperty().bindBidirectional(model.getHeaderButtonDisabledStatusProperty());
    }
    
    private void updateView(){
        //System.out.println("updating view: 1");
        updateVolumeSelectionLabelView();
        updateHeaderButton();
    }
    
    private void updateVolumeSelectionLabelView(){
        updateVolumeSelectionLabelView(model.getLabel());
    }
    private void updateVolumeSelectionLabelView(String newValue){
        volumePathLabel.setText(newValue);
    }
    
    
     private void updateHeaderButton(){
      //   System.out.println("VSC: HButton is now "+(model.isHeaderButtonIsDisabled()?"Disabled":"Enabled"));
         headerTableDisplayButton.setDisable(model.getHeaderButtonStatus());
        showTable.setDisable(model.getHeaderButtonStatus());
         
     }
    
    
    private void updateHeaderButton(Boolean isDisabled){
        headerTableDisplayButton.setDisable(isDisabled);
    }

    public void setId(Long valueOf) {
        this.id=valueOf;
    }

    public Long getId() {
        return id;
    }
    
    
    
}
