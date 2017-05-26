/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.acquisitionType;

import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.curves.CubCurve;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.jobs.type0.JobStepType0NodeController;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionController implements JobStepType0NodeController{
    final private ChangeListener<String> JOBSTEP_TEXT_FIELD_CHANGE_LISTENER=new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("Name changed from "+oldValue+ " to "+newValue);
            updateJobStepTextFieldView(newValue);
        }
    };
    
     final private ListChangeListener<LinksModel> LINKS_CHANGE_LISTENER=new ListChangeListener<LinksModel>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends LinksModel> c) {
            System.out.println(" Added: "+c.next());
        }
    };
     
     
      private AcquisitionJobStepModel model;
    private AcquisitionNode jsn;                                //the real node is jsn.getJobStepNode()
    private ArrayList<Links> links=new ArrayList<Links>();
    
    
    
    private AnchorPane basePane=null;                     //the pane on which all of this is added
    private List<LinksModel> linksModelList=new ArrayList<>();
    private ObservableList<LinksModel> obsLinkList=FXCollections.observableList(linksModelList);
    private Long id;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField jobStepTextField;

    @FXML
    private AnchorPane leftLinkHandle;
    
    @FXML
    private AnchorPane rightLinkHandle;

    @FXML
    private Button addNewVolumeSelectionView;

    @FXML
    private GridPane gridPaneJobStepNode;
    
    @FXML
    private Button acquisitionBtn;
    
    @FXML
    void showAcqTable(ActionEvent event) {
        
    }

     @FXML
    void handleJobStepLabelTextField(ActionEvent event) {
            String name=jobStepTextField.getText();
            if(name!=null)model.setJobStepText(name);
    }
    
    
      @FXML
    void onLinkDragDetected(MouseEvent event) {
     
    }

    @FXML
    void onLinkDragDropped(DragEvent event) {
        //jsn.getParent().setOnDragOver(null);
      //  jsn.getParent().setOnDragDropped(null);
        System.out.println("JSNC: LinkDragDropped On");
        event.setDropCompleted(true);
        event.consume();
        

    }
    
       @FXML
    void handleOnMouseDragEntered(MouseEvent event) {
            System.out.println("Drag Mouse Entered on "+model.getId());
            
            
            
    }
    
    @FXML
    void handleJobStepTextFieldMouseExited(MouseEvent event) {
            String name=jobStepTextField.getText();
            if(name!=null)model.setJobStepText(name);
    }

    
    @FXML
    void handleOnMouseDragged(MouseEvent event) {
        System.out.println("Drag Mouse Dragged on "+model.getId());
    }
    
    
    
    
    @FXML
    void handleOnDragDetected(MouseEvent event) {                                               // number 1
     
    }

    @FXML
    void handleOnDragDone(DragEvent event) {
       
    }

    @FXML                                                                                                           //this is a repetition
    void handleOnDragDropped(DragEvent event) {
        System.out.println("Drag  Dragged Dropped on "+model.getId()+" : "+model.getJobStepText()+ " :GTarget :"+event.getTarget().getClass().getCanonicalName());
      
        
        
        //event.acceptTransferModes(TransferMode.LINK);
       jsn=(AcquisitionNode) event.getGestureTarget();
        System.out.println("Linked to "+jsn.getJsnc().getModel().getJobStepText());
        event.setDropCompleted(true);
        event.consume();
      
    }

    @FXML
    void handleOnDragOver(DragEvent event) {
        System.out.println("Drag over on "+model.getId()+" : "+model.getJobStepText()+ ":GSource : "+event.getGestureSource().getClass().getCanonicalName()+" :GTarget :"+event.getTarget().getClass().getCanonicalName());
     
        event.acceptTransferModes(TransferMode.LINK);
        event.consume();
        
       
        
    }
    
    
    

    @Override
    public JobStepType0Model getModel() {
         return model;
    }

    @Override
    public void setObsList(ObservableList obvolist) {
       
    }

    @Override
    public void setInsightVersionsModel(InsightVersionsModel insVerModel) {
        this.model.setInsightVersionsModel(insVerModel);
    }

    @Override
    public void setVolumeModelsForFrontEndDisplay() {
        
    }

    @Override
    public void setInsightListForFrontEndDisplay() {
        
    }

    void setId(Long valueOf) {
        this.id=valueOf;
    }

    void setModel(AcquisitionJobStepModel item) {
        if(this.model!=null) removeModelListeners();
        this.model=item;
        this.model.setId(id);
        setupModelListeners();
        updateView();
    }

    void setView(AcquisitionNode aThis) {
          jsn=aThis;
          jsn.parentProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                basePane=(AnchorPane) jsn.getParent();
                
            }
            
        });
    }

    void addToLineage(JobStepType0Model parent) {
        System.out.println("JSNC: AddtoLineage Child Id: "+model.getId());
        model.addToParent(parent);
        
       
        parent.addToChildren(model);
    }

    void startNewLink(AcquisitionJobStepModel jmod) {
         List<LinksModel> lmList = jmod.getListOfLinkModels();
        LinksModel lm=lmList.get(lmList.size()-1);
        obsLinkList.add(lm);
        Links link=new Links(lm);
        CubCurve curve=link.getCurve();
        curve.startXProperty().bind(Bindings.add(jsn.layoutXProperty(),jsn.boundsInLocalProperty().get().getMaxX()));
        curve.startYProperty().bind(Bindings.add(jsn.layoutYProperty(),jsn.boundsInLocalProperty().get().getMaxY()/2));
        curve.setEndX(basePane.getScene().getX()+100.0);
        curve.setEndY(basePane.getScene().getY()+100.0);
        
        basePane.getChildren().add(0,link);
       
    }
    
    
     private void removeModelListeners(){
         model.getJobStepTextProperty().removeListener(JOBSTEP_TEXT_FIELD_CHANGE_LISTENER);
         jobStepTextField.accessibleTextProperty().unbindBidirectional(model.getJobStepTextProperty());
         
        // model.getVolListProperty().removeListener(JOBSTEP_VOLUME_LIST_CHANGE_LISTENER);
        // volumeSelView.itemsProperty().unbindBidirectional(model.getVolListProperty());
       //  pendingCheckBox.selectedProperty().unbind();
        // qcCheckBox.selectedProperty().unbind();
        
         
     }
     
      private void setupModelListeners(){
         model.getJobStepTextProperty().addListener(JOBSTEP_TEXT_FIELD_CHANGE_LISTENER);
         jobStepTextField.accessibleTextProperty().bindBidirectional(model.getJobStepTextProperty());
         
         
        
         
         
         
         /* volumeSelView.setCellFactory(new Callback<ListView<VolumeSelectionModel>, ListCell<VolumeSelectionModel>>() {
         
         @Override
         public ListCell<VolumeSelectionModel> call(ListView<VolumeSelectionModel> param) {
         //System.out.println("JSNController: calling setCellFactory on  "+param.getItems().get(show).getLabel());
         
         
         
         return new VolumeSelectionCell();
         
         }
         });
         
         // model.getPendingFlagProperty().addListener(CHECK_BOX_CHANGE_LISTENER);
         pendingCheckBox.selectedProperty().bind(model.getPendingFlagProperty());
         qcCheckBox.selectedProperty().bind(model.getQcFlagProperty());
         model.getVolListProperty().addListener(JOBSTEP_VOLUME_LIST_CHANGE_LISTENER);
         volumeSelView.itemsProperty().bindBidirectional(model.getVolListProperty());
         */
         obsLinkList.addListener(LINKS_CHANGE_LISTENER);
       
     }
      
       private void updateView(){
          
        
        updateJobStepTextFieldView();
       
       // updateJobStepVolumeListView();
        
        
    }
    
       
       private void updateJobStepTextFieldView(){
        updateJobStepTextFieldView(model.getJobStepText());
    }
    
    
    private void updateJobStepTextFieldView(String newValue){
        jobStepTextField.setText(newValue);
    }
}
