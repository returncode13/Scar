/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs;


import com.sun.javafx.scene.input.DragboardHelper;

import fend.session.SessionController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Callback;
import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.Anchor;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.jobs.insightVersions.InsightVersionsController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.insightVersions.InsightVersionsNode;

import fend.session.node.volumes.VolumeSelectionCell;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.cell.CheckBoxListCell;


/**
 *
 * @author naila0152
 */
public class JobStepNodeController {
    
    public static File insightLocation=new File("/home/sharath/programming/polarcus/insight");    
    
    final private ChangeListener<String> JOBSTEP_TEXT_FIELD_CHANGE_LISTENER=new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("Name changed from "+oldValue+ " to "+newValue);
            updateJobStepTextFieldView(newValue);
        }
    };
    
    
    
    final private ChangeListener<ObservableList<VolumeSelectionModel>> JOBSTEP_VOLUME_LIST_CHANGE_LISTENER =new ChangeListener<ObservableList<VolumeSelectionModel>>() {

        @Override
        public void changed(ObservableValue<? extends ObservableList<VolumeSelectionModel>> observable, ObservableList<VolumeSelectionModel> oldValue, ObservableList<VolumeSelectionModel> newValue) {
           /* System.out.println("OldValue List Contents ");
            for (Iterator<VolumeSelectionModel> iterator = oldValue.iterator(); iterator.hasNext();) {
                VolumeSelectionModel next = iterator.next();
                System.out.println("VSModel #  "+next.getVsId());
                
            }
            
             System.out.println("NewValue List Contents ");
            for (Iterator<VolumeSelectionModel> iterator = newValue.iterator(); iterator.hasNext();) {
                VolumeSelectionModel next = iterator.next();
                System.out.println("VSModel #  "+next.getVsId());
                
            }
            */
            updateJobStepVolumeListView(newValue);
        }
    };
            
            final private ListChangeListener<LinksModel> LINKS_CHANGE_LISTENER=new ListChangeListener<LinksModel>() {

        

       

        @Override
        public void onChanged(ListChangeListener.Change<? extends LinksModel> c) {
            System.out.println(" Added: "+c.next());
        }
    };
    
    private List<VolumeSelectionModel> volSelectionList=new ArrayList<>();
    private ObservableList<VolumeSelectionModel> obsList=FXCollections.observableList(volSelectionList);
    private int show=0;
    private JobStepModel model;
    private JobStepNode jsn;                                //the real node is jsn.getJobStepNode()
    private ArrayList<Links> links=new ArrayList<Links>();
    
    
    
    private AnchorPane basePane=null;                     //the pane on which all of this is added
    private List<LinksModel> linksModelList=new ArrayList<>();
    private ObservableList<LinksModel> obsLinkList=FXCollections.observableList(linksModelList);
    
    
    
    private List<String> versionNames=new ArrayList<>();
    private ObservableList<String> allAvailableVersionsObsList=FXCollections.observableArrayList(versionNames);
    
    
    private List<String> selectedVersionNames=new ArrayList<>();
    private ObservableList<String> selectedVersions=FXCollections.observableArrayList(selectedVersionNames);
    private Long id;
    
    InsightVersionsModel ismod=new InsightVersionsModel(allAvailableVersionsObsList);
    
//    final Delta dragDelta = new Delta();
    
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
    private ListView<VolumeSelectionModel> volumeSelView;
    
    @FXML
    private Button insightVerButton;
    
     @FXML
    private ListView<String> insightListView;
    
 
     
     
     
    @FXML
    void handleAddNewVolumeSelectionButton(ActionEvent event) {
            
        if(model.getVolList()!=null){
            obsList=model.getVolList();
        }
             
        
                obsList.add(new VolumeSelectionModel(true));
                 volumeSelView.setItems(obsList);
               
               
                model.setVolList(obsList);
                
                System.out.println("JSNC: Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("JSNC: At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModel> iterator = model.getVolList().iterator(); iterator.hasNext();) {
            VolumeSelectionModel next = iterator.next();
                    System.out.println("         id# "+next.getId()+" label: "+next.getLabel()+" headerButtonIsDisabled :"+next.isHeaderButtonIsDisabled());
            
        }
                
               show++;
              
    
    }
    
    @FXML
    void handleJobStepLabelTextField(ActionEvent event) {
            String name=jobStepTextField.getText();
            if(name!=null)model.setJobStepText(name);
    }
  
   
   

    @FXML
    void handleJobStepTextFieldMouseExited(MouseEvent event) {
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

    
    
   
    //dragging
    
     @FXML
    void handleOnMouseDragEntered(MouseEvent event) {
            System.out.println("Drag Mouse Entered on "+model.getId());
            
            
            
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
       jsn=(JobStepNode) event.getGestureTarget();
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
    
    @FXML
    void handleInsightVerButtonClicked(ActionEvent event) {
        
       
        
        
        
        
        String  regex=".\\d*.\\d*-[a-zA-Z0-9_-]*";  
        Pattern pattern=Pattern.compile(regex);
        
       File[] versions= insightLocation.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pattern.matcher(pathname.getName()).matches();
            }
        });
        ArrayList<String> versionNames=new ArrayList<>();
        allAvailableVersionsObsList.clear();
        for (File version : versions) {
            
            String name=version.getName();
            this.allAvailableVersionsObsList.add(name);
            System.err.println("version: "+version);
        }
        
        
       
        if(model.getInsightVersionsModel()==null){
            model.setInsightVersionsModel(ismod);
        }
        else
            ismod=model.getInsightVersionsModel();
        
        InsightVersionsNode isnode=new InsightVersionsNode(ismod);
        InsightVersionsController iscontr=isnode.getInsightVersionsController();
        
        
        selectedVersions=FXCollections.observableArrayList(ismod.getCheckedVersions());
        insightListView.setItems(selectedVersions);
       
        
        
        
       
       
       
    }
    
   
    public ObservableList<VolumeSelectionModel> getObsList() {
        return obsList;
    }

    public void setObsList(ObservableList<VolumeSelectionModel> obsList) {
        this.obsList = obsList;
    }
    
    
    /*Dummy test
    */
 
    public void testIfButtonCanBeAdded(double startX,double startY){
      
      AnchorModel mStart=new AnchorModel();
       mStart.setCenterX(startX);
       mStart.setCenterY(startY);
       mStart.setJob(model);
       
       AnchorModel mEnd=new AnchorModel();
       mEnd.setCenterX(startX+10.0);
       mEnd.setCenterY(startY);
       
        CubCurveModel cmod=new CubCurveModel();
        
        LinksModel lm = new LinksModel(mStart, mEnd, cmod);
        Links ln=new Links(lm);
        obsLinkList.add(lm);
        
       
      CubCurve curve= ln.getCurve();
      
       curve.startXProperty().bind(Bindings.add(jsn.layoutXProperty(),jsn.boundsInLocalProperty().get().getMaxX()));
       curve.startYProperty().bind(Bindings.add(jsn.layoutYProperty(),jsn.boundsInLocalProperty().get().getMaxY()/2));
       
       
      // curve.endXProperty().bind(Bindings.add(jsn.layoutXProperty(),jsn.boundsInLocalProperty().get().getMaxX()+100.0));
       //curve.endYProperty().bind(Bindings.add(jsn.layoutYProperty(),jsn.boundsInLocalProperty().get().getMaxY()/2+100.0));
        System.out.println("fend.session.node.jobs.JobStepNodeController.testIfButtonCanBeAdded(): Curve added : maxyY: "+jsn.boundsInLocalProperty().get().getMaxY());
       
      curve.setEndX(basePane.getScene().getX()+100.0);
      curve.setEndY(basePane.getScene().getY()+100.0);
       basePane.getChildren().add(0, ln);
        
       
    }
    
    
    

    public JobStepModel getModel() {
        return model;
    }

    public void setModel(JobStepModel model) {
        if(this.model!=null) removeModelListeners();
        this.model=model;
        this.model.setId(id);
        setupModelListeners();
        updateView();
    }

    
     private void removeModelListeners(){
         model.getJobStepTextProperty().removeListener(JOBSTEP_TEXT_FIELD_CHANGE_LISTENER);
         jobStepTextField.accessibleTextProperty().unbindBidirectional(model.getJobStepTextProperty());
         
         model.getVolListProperty().removeListener(JOBSTEP_VOLUME_LIST_CHANGE_LISTENER);
         volumeSelView.itemsProperty().unbindBidirectional(model.getVolListProperty());
         
        
         
     }
    
     private void setupModelListeners(){
         model.getJobStepTextProperty().addListener(JOBSTEP_TEXT_FIELD_CHANGE_LISTENER);
         jobStepTextField.accessibleTextProperty().bindBidirectional(model.getJobStepTextProperty());
         
         
        
         
         
         
      volumeSelView.setCellFactory(new Callback<ListView<VolumeSelectionModel>, ListCell<VolumeSelectionModel>>() {

                        @Override
                        public ListCell<VolumeSelectionModel> call(ListView<VolumeSelectionModel> param) {
                            //System.out.println("JSNController: calling setCellFactory on  "+param.getItems().get(show).getLabel());
                            
                           
                            
                             return new VolumeSelectionCell();
                          
                        }
                    });
         
         model.getVolListProperty().addListener(JOBSTEP_VOLUME_LIST_CHANGE_LISTENER);
         volumeSelView.itemsProperty().bindBidirectional(model.getVolListProperty());
         obsLinkList.addListener(LINKS_CHANGE_LISTENER);
       
     }
    
    
    private void updateView(){
          
        
        updateJobStepTextFieldView();
       
        updateJobStepVolumeListView();
        
        
    }
    
    
    
   
    
    
    private void updateJobStepTextFieldView(){
        updateJobStepTextFieldView(model.getJobStepText());
    }
    
    
    private void updateJobStepTextFieldView(String newValue){
        jobStepTextField.setText(newValue);
    }
    
    private void updateJobStepVolumeListView(){
        updateJobStepVolumeListView(model.getVolList());
    }
    
    private void updateJobStepVolumeListView(ObservableList<VolumeSelectionModel> newValue){
        volumeSelView.setItems(newValue);
    }
 /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Initialized called");
    volumeSelView.setCellFactory(new Callback<ListView<VolumeSelectionModel>, ListCell<VolumeSelectionModel>>() {

                        @Override
                        public ListCell<VolumeSelectionModel> call(ListView<VolumeSelectionModel> param) {
                             return new VolumeSelectionCell();
                          
                        }
                    });
    
    
    
    
    
    
    }*/

   /* public ObservableList<LinksModel> getObsLinks() {
        return obsLinks;
    }

    public void setObsLinks(ObservableList<LinksModel> obsLinks) {
        this.obsLinks = obsLinks;
    }
*/
    public JobStepNodeController() {
        // System.out.println("Constructor called");
        
         
           
    }
    
    

    
    void setView(JobStepNode aThis) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        jsn=aThis;
        
        jsn.parentProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                basePane=(AnchorPane) jsn.getParent();
                
            }
            
        });
    }

    void addToLineage(JobStepModel parent) {
      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("JSNC: AddtoLineage ParentId: "+parent.getId());
        System.out.println("JSNC: AddtoLineage Child Id: "+model.getId());
        model.addToParent(parent);
        
       
        parent.addToChildren(model);
        
        //Add the code for ancestor shuffling here.
        
        
        
        
    }

    void setId(Long valueOf) {
        this.id=valueOf;
    }

    public Long getId() {
        return id;
    }

    public void setVolumeModelsForFrontEndDisplay() {
        
        
        for (Iterator<VolumeSelectionModel> iterator = obsList.iterator(); iterator.hasNext();) {
            VolumeSelectionModel next = iterator.next();
            volumeSelView.setItems(obsList);
               
                
                model.setVolList(obsList);                               //Redundant step???
                
                
                System.out.println("JSNC: Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("JSNC: At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModel> iterator1 = model.getVolList().iterator(); iterator1.hasNext();) {
            VolumeSelectionModel next1 = iterator1.next();
                    System.out.println("         id# "+next1.getId()+" label: "+next1.getLabel()+" headerButtonIsDisabled :"+next1.isHeaderButtonIsDisabled());
                    
                    HeadersModel hmod=next1.getHeadersModel();
                    List<Sequences> seqL=hmod.getObsHList();
                    for (Iterator<Sequences> iterator2 = seqL.iterator(); iterator2.hasNext();) {
                        Sequences next2 = iterator2.next();
                        System.out.println("fend.session.node.jobs.JobStepNodeController.setVolumeModelsForFrontEndDisplay() Sequence: "+next2.getSequenceNumber());
                    }
            
        }
            
            
        }
    }

    public void setInsightListForFrontEndDisplay() {
        /*   if(model.getInsightVersionsModel()==null){
        model.setInsightVersionsModel(ismod);
        }
        else
        ismod=model.getInsightVersionsModel();*/
         
        
         System.out.println("fend.session.node.jobs.JobStepNodeController.setInsightListForFrontEndDisplay():");
       // InsightVersionsNode isnode=new InsightVersionsNode(ismod);
       // InsightVersionsController iscontr=isnode.getInsightVersionsController();
        
        
        selectedVersions=FXCollections.observableArrayList(ismod.getCheckedVersions());
        for(Iterator<String> it=selectedVersions.iterator();it.hasNext();){
            String ver=it.next();
            System.out.println("fend.session.node.jobs.JobStepNodeController.setInsightListForFrontEndDisplay():  "+ver);
        }
        insightListView.setItems(selectedVersions);
    }

    public void setInsightVersionsModel(InsightVersionsModel insVerModel) {
        ismod=insVerModel;
    }

   
    
}

