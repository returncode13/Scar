/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import collector.Collector;
import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.jobs.JobStepNode;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import fend.session.node.jobs.JobStepModel;
import fend.session.node.jobs.JobStepNodeController;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.scene.Scene;

/**
 *
 * @author naila0152
 */
public class SessionController implements Initializable {
    
    
    private ArrayList<JobStepModel> jobStepModelList=new ArrayList<>();
    private ObservableList<JobStepModel> obsModelList=FXCollections.observableList(jobStepModelList);
    
    private List<VolumeSelectionModel> dummyList = new ArrayList<>();
    private JobStepNode jsn;
    
    private ArrayList<LinksModel> linksModelList=new ArrayList<>();
    private ObservableList<LinksModel> obsLinksModelList=FXCollections.observableList(linksModelList);
    
    private SessionModel model=new SessionModel();
    private SessionNode snn;
    
    private int rowNo,ColNo;
    private int numCols=1;
    private int numRows=0;
   
    private Collector collector=new Collector();
    private Long id;

    @FXML
    private VBox buttonHolderVBox;

     @FXML
    private ScrollPane rightPane;
    
    @FXML
    private AnchorPane rightInteractivePane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private SplitPane basePane;



     @FXML
    private Button addJobStepButton;
     
    
    
     int i=0;
    @FXML
    void handleAddJobStepButton(ActionEvent event) {
        //dummyList.add(new VolumeSelectionModel("v1", Boolean.TRUE));
       // dummyList.add(new VolumeSelectionModel("v2", Boolean.TRUE));
       // obsModelList.add(new JobStepModel("SRME", dummyList));
       
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        
        for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepModel next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            
        }
        obsModelList.add(new JobStepModel());
        jsn=new JobStepNode(obsModelList.get(obsModelList.size()-1));
        System.out.println("Value of numCols: "+numCols+" numRows: "+numRows);
        
       rightInteractivePane.getChildren().add(jsn);
        
        //gridPane.getChildren().add(jsn.getJobStepNode()); above method of setting constraints and adding children
        
        numRows++;
        numCols++;
       i++;
    }
    
    @FXML
    void handleOnDragDetected(MouseEvent event) {

    }

    @FXML
    void handleOnDragDone(DragEvent event) {
     /*   System.out.println("Drag done on gridpane");
         if(event.getDragboard().hasString()){
            int valueToMove=Integer.parseInt(event.getDragboard().getString());
           
                event.acceptTransferModes(TransferMode.MOVE);
           
        }
         //event.setDropCompleted(true);
        event.consume();
             */
        
        System.out.println("JGVC: Drag done: "+event.getDragboard().getContent(DataFormat.PLAIN_TEXT));
    }
   
     @FXML
    void handleOnDragDropped(DragEvent event) {
           Dragboard dragboard=event.getDragboard();
        boolean success=false;
        if(dragboard.hasString()){
            String nodeId=dragboard.getString();
            jsn=(JobStepNode) rightInteractivePane.lookup("#"+nodeId);
             System.out.println("JGVC: Drag dropped on basePane mouse entered in row# "+rowNo+" colume#"+ ColNo);
         //interactiveAnchorPane.getChildren().add(node);
         
          jsn.relocateToPoint(new Point2D(event.getSceneX(),event.getSceneY()));
            success=true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    void handleOnDragOver(DragEvent event) {
      
        if(event.getGestureSource()!=rightInteractivePane && event.getDragboard().hasString()) {
          //    System.out.println("JGVC: Drag over ..accepting transfer.MOVE..class instanceOf "+event.getGestureSource().getClass().toString());
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
    
    
    @FXML
    void commit(ActionEvent event) {
           // ArrayList<Node> a=(ArrayList<Node>) rightInteractivePane.getChildren().;
        
        System.out.println("JGVC EventType: "+event.getEventType());
            for (Iterator<Node> iterator = rightInteractivePane.getChildren().iterator(); iterator.hasNext();) {
            Node next = iterator.next();
            if(next instanceof  Links)
            {
                Links ln=(Links) next;
                
                obsLinksModelList.add(ln.getLmodel());
            }
            
            
            
            
        }
            
            model.setListOfJobs(obsModelList);
            model.setListOfLinks(linksModelList);
            
                System.out.println("JGVC: Set the last model");

             //   collector.setCurrentSession(model.getId());
            //collector.setFeJobGraphModel(model);
    }

    public SessionModel getModel() {
        return model;
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println("initialize");
       
        // TODO
    }    
    
    public void addToLinksModelList(LinksModel l)
    {
        obsLinksModelList.add(l);
    }

    public void setObsModelList(ObservableList<JobStepModel> obsModelList) {
        this.obsModelList = obsModelList;
    }

    

    
    
    public ObservableList<JobStepModel> getObsModelList() {
        return obsModelList;
    }

    public ObservableList<LinksModel> getObsLinksModelList() {
        return obsLinksModelList;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     void setModel(SessionModel item) {
        this.model=item;
        this.model.setId(id);
    }

    void setView(SessionNode aThis) {
        this.snn=aThis;
    }
    
    
   public void setAllLinksAndJobsForCommit(){
       System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit()");
       for (Iterator<Node> iterator = rightInteractivePane.getChildren().iterator(); iterator.hasNext();) {
            Node next = iterator.next();
            if(next instanceof  Links)
            {
                Links ln=(Links) next;
                
                obsLinksModelList.add(ln.getLmodel());
            }
            
            
            
            
        }
            
       for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
           JobStepModel next = iterator.next();
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit():  jobStepModeList : "+next.getJobStepText());
           
       }
       
            model.setListOfJobs(obsModelList);
            
            for (Iterator<JobStepModel> iterator = jobStepModelList.iterator(); iterator.hasNext();) {
           JobStepModel next = iterator.next();
           
              //  System.out.println("SessContr: Checking on the Kids");
                ArrayList<JobStepModel> children=next.getJsChildren();
                
                for (Iterator<JobStepModel> iterator1 = children.iterator(); iterator1.hasNext();) {
                    JobStepModel child1 = iterator1.next();
                //    System.out.println("SessContr Parent: "+next.getJobStepText()+"   Child: "+child1.getJobStepText());
                    
                }
           
       }
            
            model.setListOfLinks(linksModelList);
            
            System.out.println("SC: model has ID: "+model.getId());
   }
   
   
   public void setAllModelsForFrontEndDisplay(){
       
       
       Map<JobStepNode,AnchorModel> jsnAnchorMap=new HashMap<>();
       
       List<JobStepNode> root=new ArrayList<>();                             // A list of possible root nodes. i.e step1->step2 and step1->step3  implies step1 is the root of the structure. However we can also have several independent graphs
                                                           // e.g. one graph is step1-> step2 and step1-> step3  
                                                           //the other graph is step6-> step7 and step6-> step8.
                                                           // here there are two roots namely step1 and step6
       
       
       for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
           
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): display contents");
           
           JobStepModel next = iterator.next();
           List<VolumeSelectionModel> testvm=next.getVolList();
           
           for (Iterator<VolumeSelectionModel> iterator1 = testvm.iterator(); iterator1.hasNext();) {
               VolumeSelectionModel next1 = iterator1.next();
               System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): "+next1.getLabel());
           }
           jsn=new JobStepNode(next);
            JobStepNodeController jsc=jsn.getJsnc();
            
            JobStepModel jsmod=jsc.getModel();
            
            ArrayList<JobStepModel> jsmodParents=jsmod.getJsParents();
            if (jsmodParents.size()==1){
               
                if(jsmod.getId().equals(jsmodParents.get(0).getId())){
                     System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  "+jsmodParents.get(0).getJobStepText()+" is a root..adding to list of roots");
                    System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  id matched for model and the single content in the list of Parents");
                    root.add(jsn);
                }
            }
            
            
            AnchorModel mstart= new AnchorModel();
            
            Scene sc=jsn.getScene();
            //System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay() SceneProperties: "+sc.getProperties().toString());
            
            Bounds pane=rightInteractivePane.getBoundsInLocal();
            
            
            
            
            
           
            
            ObservableList obvolist=next.getVolList();
            jsc.setObsList(obvolist);
           jsc.setVolumeModelsForFrontEndDisplay();
            rightInteractivePane.getChildren().add(jsn);
            
          /*  
           try {
               Thread.sleep(100000000L);
           } catch (InterruptedException ex) {
               Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
           }
            */
            
            Double centerX=jsn.boundsInLocalProperty().getValue().getMinX();
            Double centerY=jsn.boundsInLocalProperty().getValue().getMinY();
            
            mstart.setCenterX(centerX);
            mstart.setCenterY(centerY);
            mstart.setJob(next);
           
            
            jsnAnchorMap.put(jsn, mstart);
         //  System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  jsnSceneX() "+jsn.localToScene(rightInteractivePane.getBoundsInLocal()).toString());
            numRows++;
        numCols++;
       i++;
           
       }
    /*   jsn=new JobStepNode(obsModelList.get(obsModelList.size()-1));*/
        System.out.println("Value of numCols: "+numCols+" numRows: "+numRows);
       
       
        
        //gridPane.getChildren().add(jsn.getJobStepNode()); above method of setting constraints and adding children
        
        
        //Iterate through the map of jsnode and anchormodel. for a given jsn find its child. set one anchor to jsn and the other to its child. if jsn = child. i.e. a leaf then dont add!
        
        for (Iterator<JobStepNode> iterator = root.iterator(); iterator.hasNext();) {
           JobStepNode next = iterator.next();
           drawCurve(next,jsnAnchorMap);
           
           
       }
        
        
       
   }

    private void drawCurve(JobStepNode next, Map<JobStepNode, AnchorModel> jsnAnchorMap) {
        
        JobStepModel jsmod=next.getJsnc().getModel();
        AnchorModel mstart=new AnchorModel();
        // Double centerX=next.boundsInLocalProperty().getValue().getMinX();
          //  Double centerY=next.boundsInLocalProperty().getValue().getMinY();
          
          Double centerX=next.layoutXProperty().doubleValue();
          Double centerY=next.layoutYProperty().doubleValue();//next.getHeight();
            
          
            mstart.setCenterX(centerX);
            mstart.setCenterY(centerY);
            mstart.setJob(next.getJsnc().getModel());
        /*Bounds pane=rightInteractivePane.getBoundsInLocal();
            
            
            
            Double centerX=pane.getMaxY();
            Double centerY=pane.getMaxY();
        
        double startX=centerX;
        double startY=centerY;
        mstart.setCenterX(startX);
        mstart.setCenterY(startY);
        mstart.setJob(jsmod);
        */        
                
        ArrayList<JobStepModel> children=jsmod.getJsChildren();
        
        
        for (Iterator<JobStepModel> iterator = children.iterator(); iterator.hasNext();) {
            JobStepModel next1 = iterator.next();
            
            if(next1.getId().equals(jsmod.getId())){
                System.out.println("fend.session.SessionController.drawCurve(): "+jsmod.getJobStepText()+ " :is a leaf: "+next1.getJobStepText());
                return;
            }
                for (Map.Entry<JobStepNode, AnchorModel> entry : jsnAnchorMap.entrySet()) {
                JobStepNode key = entry.getKey();
                AnchorModel mEnd = entry.getValue();
                
                Long keyId=Long.parseLong(key.getId());         //since jobstepnodes id is a string
                   // System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " id of node: "+keyId);
                
                if(next1.getId().equals(keyId)){
                    System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " EQUALS id of node: "+keyId+ " : starting to draw cubic curves here: ");
                   double sx=mEnd.getCenterX().doubleValue();
                   double sy=mEnd.getCenterY().doubleValue();
                   
                   mEnd.setCenterX(sx+next.getWidth());
                   mEnd.setCenterY(sy+next.getHeight()/2);                    //redundant but for the sake of uniformity..   :(
                   mEnd.setJob(next1);
                   
                    CubCurveModel cmod=new CubCurveModel();
                    
                    LinksModel lm=new LinksModel(mstart, mEnd, cmod);
                    Links ln=new Links(lm);
                    
                    CubCurve curve=ln.getCurve();
                    
                    curve.startXProperty().bind(Bindings.add(next.layoutXProperty(),next.boundsInLocalProperty().get().getMaxX()));         //next is the parent node
                    curve.startYProperty().bind(Bindings.add(next.layoutYProperty(),next.boundsInLocalProperty().get().getMaxY()/2));
                    
                    curve.endXProperty().bind(Bindings.add(key.layoutXProperty(),key.boundsInLocalProperty().get().getMaxX()));           //key in the child node
                    curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getMaxY()/2));
                    rightInteractivePane.getChildren().add(0,ln);
                    
                    
                    drawCurve(key, jsnAnchorMap);
                }
                    
                
            }
            
        }
        
        
    }

   

   
}
