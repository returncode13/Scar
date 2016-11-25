/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes;


import collector.HeaderCollector;
import db.model.Headers;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.HeadersNode;
import fend.session.node.headers.HeadersViewController;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author naila0152
 */
public class VolumeSelectionController  {
    
    final private DirectoryChooser dirChooser = new DirectoryChooser();
    private HeaderCollector hcollector=new HeaderCollector();
    private Long id;
    
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
    
    private VolumeSelectionModel model;
     private  TableView<SubSurface> table;
     
    @FXML
    void handleSelectVolumeButton(ActionEvent event) {
        final File f=dirChooser.showDialog(selectVolumeButton.getScene().getWindow());
       
            if(f!=null) {
             model.setVolumeChosen(f);
             model.setLabel(f.getName());
             model.setHeaderButtonStatus(Boolean.FALSE);                    //Counter intuitive !:(
             
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
                                    
        HeadersNode hnode=new HeadersNode(model.getHeadersModel());
        HeadersViewController hvc=hnode.getHeadersViewController();
        

           
           
    }

    public VolumeSelectionModel getModel() {
        return model;
    }

    public void setModel(VolumeSelectionModel model) {
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
    }
    
    private void setupModelListeners(){
       // System.out.println("VSController: addMListener");
        model.getVolumeSelectionLabel().addListener(VOLUME_LABEL_CHANGE_LISTENER);
        model.getHeaderButtonDisabledStatusProperty().addListener(HEADER_BUTTON_CHANGE_LISTENER);
       // System.out.println("VSController: binding");
        volumePathLabel.accessibleTextProperty().bindBidirectional(model.getVolumeSelectionLabel());
        headerTableDisplayButton.disableProperty().bindBidirectional(model.getHeaderButtonDisabledStatusProperty());
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
