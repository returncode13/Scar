/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type4;

import collector.HeaderCollector;
import db.services.QcMatrixService;
import db.services.QcMatrixServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.HeadersNode;
import fend.session.node.headers.HeadersViewController;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.qcTable.QcMatrixModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListModel;
import fend.session.node.volumes.type0.VolumeSelectionControllerType0;
//import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeSelectionControllerType4 implements VolumeSelectionControllerType0{
     
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

    
    private VolumeSelectionModelType4 model;
    private  TableView<SubSurfaceHeaders> table;
     
     
    private List<TimerTask> volumeTimerTask=new ArrayList<>();
    private QcMatrixModel qcMatrixModel;
    private qcCheckListModel qcCModel;
    
   
     
     
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
        List<SequenceHeaders> seqs=hcollector.getHeaderListForVolume(model);
        
        /*At the end of this call the model's headersmodel contains all the sequences and the models variable "subsurfaces" contains the subsurfaces in the volume.
        */
        
        
        //ObservableList<Sequences> obSeqs=FXCollections.observableList(seqs);
       // hmodel.setObsHList(obSeqs);
       // model.setHeadersModel(hmodel);
                                    
        HeadersNode hnode=new HeadersNode(model.getHeadersModel(),0); //displays the table. see method setView();
        HeadersViewController hvc=hnode.getHeadersViewController();   
        

           
           
    }

    public VolumeSelectionModelType4 getModel() {
        return model;
    }

    public void setModel(VolumeSelectionModelType4 model) {
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
        System.out.println("VolumeSelectioncontroller.  binding qcCheckBox: with Property Value: "+model.getDependency());
        qcCheckBox.selectedProperty().bind(model.getDependency());
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
