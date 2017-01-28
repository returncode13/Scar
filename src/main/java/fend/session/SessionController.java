/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import collector.Collector;
import db.model.Acquisition;
import db.services.AcquisitionService;
import db.services.AcquisitionServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import fend.overview.OverviewController;
import fend.overview.OverviewItem;
import fend.overview.OverviewModel;
import fend.overview.OverviewNode;
import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
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
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import org.controlsfx.control.GridView;

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
    
    
     private Map<JobStepNode,AnchorModel> jsnAnchorMap=new HashMap<>();
       
     private List<JobStepNode> roots=new ArrayList<>();                             // A list of possible root nodes. i.e step1->step2 and step1->step3  implies step1 is the root of the structure. However we can also have several independent graphs
                                                           // e.g. one graph is step1-> step2 and step1-> step3  
                                                           //the other graph is step6-> step7 and step6-> step8.
                                                           // here there are two roots namely step1 and step6
    
    private List<JobStepModel> modelRoots=new ArrayList<>();
    private int rowNo,ColNo;
    private int numCols=1;
    private int numRows=0;
   
    private Collector collector=new Collector();
    private Long id;
    
    
    
    private AcquisitionService acqServ=new AcquisitionServiceImpl();
    private JobStepService jServ=new JobStepServiceImpl();

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
     
     @FXML
    private CheckBox tracker;
    
    
     int i=0;
     
     @FXML
    private Button overviewButton;

     
     @FXML
    void overviewButtonClicked(ActionEvent event) {
         System.out.println("fend.session.SessionController.overviewButtonClicked(): Click");
        
         tracking();
         List<OverviewItem> overviewItems=new ArrayList<>();
         OverviewModel ovModel=new OverviewModel();
                 
         List<JobStepModel> jobs=obsModelList;
         
         for (Iterator<JobStepModel> iterator = jobs.iterator(); iterator.hasNext();) {
             JobStepModel job = iterator.next();
             OverviewItem jobOverview=new OverviewItem();
             jobOverview.setName(job.getJobStepText());
             jobOverview.setpFlag(job.getPendingFlagProperty().get());
             jobOverview.setqFlag(job.getQcFlagProperty().get());
             
             overviewItems.add(jobOverview);
             
         }
         
         ovModel.setOverviewItemList(overviewItems);
         OverviewNode ovNode= new OverviewNode(ovModel);
         
         OverviewController ovContr=ovNode.getOverviewController();
         
         
    } 
     
     
     @FXML
    void onTrackCheck(ActionEvent event) {
         System.out.println("fend.session.SessionController.onTrackCheck() Checked "+tracker.isSelected());
         if(tracker.isSelected()){
             tracking();
         }
    }
     
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
        System.out.println("Inside fend.session.SessionController.setObsModelList()");
        for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepModel next = iterator.next();
            List<JobStepModel> children=next.getJsChildren();
                for (Iterator<JobStepModel> iterator1 = children.iterator(); iterator1.hasNext();) {
                JobStepModel next1 = iterator1.next();
                List<JobStepModel> gchildren=next1.getJsChildren();
                
                
                    System.out.println("job: "+next.getJobStepText()+" : has child: "+next1.getJobStepText());
                    
                        for (Iterator<JobStepModel> iterator2 = gchildren.iterator(); iterator2.hasNext();) {
                        JobStepModel next2 = iterator2.next();
                            System.out.println("child: "+next1.getJobStepText()+" : has gchild: "+next2.getJobStepText());
                    }
                
            }
            
        }
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
       
       
      
       
       
       for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
           
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): display contents");
           
           JobStepModel next = iterator.next();
           /*List<VolumeSelectionModel> testvm=next.getVolList();*/
           
           InsightVersionsModel insVerModel=next.getInsightVersionsModel();
           
           /*List<String>vv = insVerModel.getCheckedVersions();
           
           for (Iterator<String> iterator1 = vv.iterator(); iterator1.hasNext();) {
           String next1 = iterator1.next();
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay() VERSIONS FOUND: "+next1);
           }*/
           
           
           /*for (Iterator<VolumeSelectionModel> iterator1 = testvm.iterator(); iterator1.hasNext();) {
           VolumeSelectionModel next1 = iterator1.next();
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): "+next1.getLabel());
           }*/
           jsn=new JobStepNode(next);
            JobStepNodeController jsc=jsn.getJsnc();
            
            JobStepModel jsmod=jsc.getModel();
            
            ArrayList<JobStepModel> jsmodParents=jsmod.getJsParents();
            if (jsmodParents.size()==1){
               
                if(jsmod.getId().equals(jsmodParents.get(0).getId())){
                     System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  "+jsmodParents.get(0).getJobStepText()+" is a root..adding to list of roots");
                    System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  id matched for model and the single content in the list of Parents");
                    roots.add(jsn);
                }
            }
            
            
            AnchorModel mstart= new AnchorModel();
            
          
            
            
            
            
           
            
            ObservableList obvolist=next.getVolList();
           
            jsc.setObsList(obvolist);
            jsc.setInsightVersionsModel(insVerModel);
           jsc.setVolumeModelsForFrontEndDisplay();
           jsc.setInsightListForFrontEndDisplay();
           
            rightInteractivePane.getChildren().add(jsn);
            
          /*  
           try {
               Thread.sleep(100000000L);
           } catch (InterruptedException ex) {
               Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
           }
            */
            
            Double centerX=jsn.boundsInLocalProperty().getValue().getMinX();
            Double centerY=jsn.boundsInLocalProperty().getValue().getMinY()+jsn.boundsInLocalProperty().get().getHeight()/2;
            
            mstart.setJob(next);
            mstart.setCenterX(centerX);
            mstart.setCenterY(centerY);
            
           
            
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
        
       
        
        
       
   }

   
   public void setAllLinksForFrontEnd(){
        for (Iterator<JobStepNode> iterator = roots.iterator(); iterator.hasNext();) {
           JobStepNode next = iterator.next();
           drawCurve(next,jsnAnchorMap);
           
           
       }
   }
   
    private void drawCurve(JobStepNode next, Map<JobStepNode, AnchorModel> jsnAnchorMap) {
        
        JobStepModel jsmod=next.getJsnc().getModel();
        AnchorModel mstart=new AnchorModel();
         Double centerX=next.boundsInLocalProperty().getValue().getMinX()+310;
            Double centerY=next.boundsInLocalProperty().getValue().getMinY()+72;
          
         // Double centerX=next.layoutXProperty().doubleValue();
         // Double centerY=next.layoutYProperty().doubleValue();//next.getHeight();
            
            mstart.setJob(next.getJsnc().getModel());
            mstart.setCenterX(centerX);
            mstart.setCenterY(centerY);
            
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
                    System.out.println("fend.session.SessionController.drawCurve()    found   : "+next1.getJobStepText()+" === "+key.getJsnc().getModel().getJobStepText());
                  // double sx=mEnd.getCenterX().doubleValue();
                 //  double sy=mEnd.getCenterY().doubleValue();
                    System.out.println("fend.session.SessionController.drawCurve() MAYlayoutY: "+key.getParent().boundsInLocalProperty().getValue().getMaxY());
                    System.out.println("fend.session.SessionController.drawCurve() "+key.getProperties().toString());
                  double sx=key.boundsInLocalProperty().getValue().getMaxX();
                 double sy=key.boundsInLocalProperty().getValue().getMinY()+144;
                  
                  //double sx=key.getLayoutX();
                  //double sy=key.getLayoutY();
                  
                   mEnd.setJob(next1);
                   mEnd.setCenterX(sx);
                   
                   mEnd.setCenterY(sy);                    //redundant but for the sake of uniformity..   :(
                   
                   
                    CubCurveModel cmod=new CubCurveModel();
                    
                    LinksModel lm=new LinksModel(mstart, mEnd, cmod);
                    Links ln=new Links(lm);
                    
                    CubCurve curve=ln.getCurve();
                    
                    
                   
                    // DoubleProperty d= new SimpleDoubleProperty(next.layoutXProperty().get()+310);
                   // curve.startXProperty().bindBidirectional(d);
                    
                    //
                    //curve.startYProperty().bindBidirectional(new SimpleDoubleProperty(next.layoutYProperty().get()+72));
                    //Bindings.add(next.layoutXProperty(), next.boundsInLocalProperty().get().getMinX()+310);
                   // Bindings.add(next.layoutYProperty(),next.boundsInLocalProperty().get().getMinY()+72);
                   
                  //  curve.startXProperty().bindBidirectional(next.layoutXProperty());
                    //curve.startYProperty().bindBidirectional(next.layoutYProperty());
                    
                 //   System.out.println("fend.session.SessionController.drawCurve():  for: "+next.getJsnc().getModel().getJobStepText()+" : child: "+key.getJsnc().getModel().getJobStepText());
                    
                   curve.startXProperty().bind(Bindings.add(next.layoutXProperty(),next.boundsInLocalProperty().get().getMinX()+310));         //next is the parent node
                   curve.startYProperty().bind(Bindings.add(next.layoutYProperty(),next.boundsInLocalProperty().get().getMinY()+72));
                    
                    curve.endXProperty().bind(Bindings.add(key.layoutXProperty(),key.boundsInLocalProperty().get().getMinX()));           //key in the child node
                    curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getMinY()+72));       // this is HARDCODED!!! find a way to get the height of the node!
                   // curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getHeight()/2));
                  //  System.out.println("fend.session.SessionController.drawCurve(): for parent "+next.getJsnc().getModel().getJobStepText()+"   :Setting startX : :next.boundsInLocalProperty().get().getMaxX():  "+next.boundsInLocalProperty().get().getMaxX());
                  //  System.out.println("fend.session.SessionController.drawCurve(): for child  "+key.getJsnc().getModel().getJobStepText() +"   :Setting  endX  : :next.boundsInLocalProperty().get().getMaxX():  "+key.boundsInLocalProperty().get().getMaxX());
                    rightInteractivePane.getChildren().add(0,ln);
                    
                    
                    drawCurve(key, jsnAnchorMap);
                }
                    
                
            }
            
        }
        
        
    }

    
    private void setRoots(){
        
        
       
        
        modelRoots=new ArrayList<>();
        //modelRoots.clear();
        for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepModel job = iterator.next();
            ArrayList<JobStepModel> jobParents=job.getJsParents();
            if (jobParents.size()==1){
               
                if(job.getId().equals(jobParents.get(0).getId())){
                     System.out.println("fend.session.SessionController.setRoots():  "+job.getJobStepText()+" is a root..adding to list of roots");
                     /*System.out.println("fend.session.SessionController.setRoots() :  id matched for model and the single content in the list of Parents");*/
                    modelRoots.add(job);
                }
            }
            
            
            
        }
        
        
         for (Iterator<JobStepModel> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepModel next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ObsModelList: "+next.getJobStepText());
            List<JobStepModel> chldn=next.getJsChildren();
            for (Iterator<JobStepModel> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepModel next1 = iterator1.next();
                System.out.println("fend.session.SessionController.setRoots(): ObsModeList job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                
                List<JobStepModel> gchild=next1.getJsChildren();
                    for (Iterator<JobStepModel> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepModel next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ObsModeList child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
                
                
            }
            
            
        }
         
         
         for (Iterator<JobStepModel> iterator = modelRoots.iterator(); iterator.hasNext();) {
            JobStepModel next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ModelRoots: "+next.getJobStepText());
            List<JobStepModel> chldn=next.getJsChildren();
            for (Iterator<JobStepModel> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepModel next1 = iterator1.next();
                
                System.out.println("fend.session.SessionController.setRoots(): ModelRoots job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                
                List<JobStepModel> gchild=next1.getJsChildren();
                    for (Iterator<JobStepModel> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepModel next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ModelRoots child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
            }
            
        }
        
        
    }
    
    private Set<SubSurface> calculateSubsInJob(JobStepModel job){
        List<VolumeSelectionModel> volList=job.getVolList();
        Set<SubSurface> subsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModel> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModel vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurface> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            
            
        }
        
        for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
            SubSurface subinJob = iterator.next();
            System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }
        
        return subsInJob;
    }
    
    
    
     private void tracking(){
         System.out.println("fend.session.SessionController.tracking():  STARTED");
         setRoots();
         List<Acquisition> acuiredSubs=acqServ.getAcquisition();      // this will query the db. Maybe put a timer?
         List<String> acqString=new ArrayList<>();                      // hold the names of the acquired subsurfaces
         
         for (Iterator<Acquisition> iterator = acuiredSubs.iterator(); iterator.hasNext();) {
         Acquisition acq = iterator.next();
         acqString.add(acq.getSubsurfaceLines());
         System.out.println("fend.session.SessionController.tracking(): in AcqString: added: "+acqString.get(acqString.size()-1));
         
         }
         
         List<String> jobSubString=new ArrayList<>();            // holds the names of the subsurfaces in the job
         
         for(JobStepModel root : modelRoots){
        
        // for (Iterator<JobStepModel> iteratorr = modelRoots.iterator(); iteratorr.hasNext();) {
          //   JobStepModel root = iteratorr.next();
             
         
              Set<SubSurface> rootsSubSurfaces=calculateSubsInJob(root);
             
             for (Iterator<SubSurface> iterator = rootsSubSurfaces.iterator(); iterator.hasNext();) {
             SubSurface subinRoot = iterator.next();
             jobSubString.add(subinRoot.getSubsurface());
             System.out.println("fend.session.SessionController.tracking(): in jobSubstring: found: "+jobSubString.get(jobSubString.size()-1));
             
             }
             
             List<String> acqStringBackedUp=new ArrayList(acqString);    //because acqString is about to go ba-bye!
             
             acqString.removeAll(jobSubString);    //remove the subs common to both acq and jobs. Since acq leads job , I guess it is logical to assume that acq be the larger list
             List<String> remainingSubs=new ArrayList<>(acqString);
             
             System.out.println("fend.session.SessionController.tracking(): remaining subs: "+remainingSubs);
             
             if(remainingSubs.size()>0){  //means jobs has subs that were acquired but weren't processed
             root.setPendingFlagProperty(Boolean.TRUE);
             System.out.println("fend.session.SessionController.tracking() setting PendingFlagProperty to TRUE: ");
             
             }
             else{                       //all subs acquired are present in the job
             root.setPendingFlagProperty(Boolean.FALSE);
             System.out.println("fend.session.SessionController.tracking() setting PendingFlagProperty to FALSE: ");
             }
             
                List<JobStepModel> children = root.getJsChildren();
                for (Iterator<JobStepModel> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepModel child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): setPendingJobs: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepModel> gChild=child.getJsChildren();
                        for (Iterator<JobStepModel> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepModel next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                 setPendingJobsFlag(root,child);
                    setQCFlag(root, child);
             }
             
             
         }
         
         
         
     }
     
     /**
      * 
      * @param parent
      * @param child 
      * call to set the pending job flag in each jobStepModel
      */
     private void setPendingJobsFlag(JobStepModel parent,JobStepModel child){
         if(parent.getId().equals(child.getId())){
             System.out.println("collector.Collector.mismatch():  ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
         
         
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): called for Parent: "+parent.getJobStepText()+" :child: "+child.getJobStepText());
         
         //Calculate the subsurfaces present in the parent
         
         
         Set<SubSurface> pSubs=calculateSubsInJob(parent);
         Set<SubSurface> cSubs=calculateSubsInJob(child);
         
         List<String> pSubsStrings=new ArrayList<>();
         List<String> cSubsStrings=new ArrayList<>();
         
         for (Iterator<SubSurface> iterator = pSubs.iterator(); iterator.hasNext();) {
         SubSurface subInParent = iterator.next();
         pSubsStrings.add(subInParent.getSubsurface());
         System.out.println("fend.session.SessionController.setPendingJobsFlag():  pSubsStrings found : "+pSubsStrings.get(pSubsStrings.size()-1));
         
         }
         
         for (Iterator<SubSurface> iterator = cSubs.iterator(); iterator.hasNext();) {
         SubSurface subInChild = iterator.next();
         cSubsStrings.add(subInChild.getSubsurface());
         System.out.println("fend.session.SessionController.setPendingJobsFlag():  cSubsStrings found : "+cSubsStrings.get(cSubsStrings.size()-1));
         }
         
         List<String> remaining=new ArrayList<>();
         
         if(pSubsStrings.size()<cSubsStrings.size()){
         System.out.println("fend.session.SessionController.setPendingJobsFlag():  Child : "+child.getJobStepText()+"  has more subsurfaces than Parent: "+parent.getJobStepText());
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): Unimplemented Method. Contact dev");
         
         }
         
         if(pSubsStrings.size()>=cSubsStrings.size()){
         pSubsStrings.removeAll(cSubsStrings);
         remaining=pSubsStrings;
         if(remaining.size()>0){   //child has pending subs
         child.setPendingFlagProperty(Boolean.TRUE);
         
         System.out.println("fend.session.SessionController.setPendingJobsFlag():  child :"+child.getJobStepText()+" has pending subs "+remaining);
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): Pending flag set in model");
         }else     //child has no pending subs
         {
         child.setPendingFlagProperty(Boolean.FALSE);
         }
         }
         
         List<JobStepModel> grandChildren=child.getJsChildren();
         for(JobStepModel gchild:grandChildren){
             System.out.println("fend.session.SessionController.setPendingJobsFlag(): job: "+child.getJobStepText()+" :has child: "+gchild.getJobStepText());
         }
         for (Iterator<JobStepModel> iterator = grandChildren.iterator(); iterator.hasNext();) {
             JobStepModel grandchild = iterator.next();
             setPendingJobsFlag(child, grandchild);
         }
         
         
         
         
     }

    private void setQCFlag(JobStepModel parent,JobStepModel child){
         if(parent.getId().equals(child.getId())){
             System.out.println("collector.Collector.setQCFlag():  ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
         
         
         Set<SubSurface> csubs= calculateSubsInJob(child);
         Set<SubSurface> psubs=calculateSubsInJob(parent);
         
         List<VolumeSelectionModel> cVolList=child.getVolList();
         
        
         
         
         for (Iterator<SubSurface> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurface c = iterator.next();
            VolumeSelectionModel targetVol=new VolumeSelectionModel();
            Sequences targetSeq=new Sequences();
            SubSurface targetSub=c;
            SubSurface refSub=new SubSurface();
            
                        for (Iterator<VolumeSelectionModel> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModel vc = iterator1.next();
                            Set<SubSurface> vcSub=vc.getSubsurfaces();
                            System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                                System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVol=vc;
                            }
                 
                        }
                        
                        if(targetVol!=null){
                            HeadersModel hmod=targetVol.getHeadersModel();
                            List<Sequences> seqList=hmod.getObsHList();
                            
                            for (Iterator<Sequences> iterator1 = seqList.iterator(); iterator1.hasNext();) {
                                Sequences seq = iterator1.next();
                                List<SubSurface> seqSubs=seq.getSubsurfaces();
                                System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                                    if(seqSubs.contains(targetSub)){
                                        targetSeq=seq;
                                        System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                                    }
                            }
                            
                        }else
                        {
                                try {
                                    throw new Exception("Subline: "+targetSub.getSubsurface()+" :not found in any of the child job: "+child.getJobStepText()+" : volumes!!");
                                } catch (Exception ex) {
                                    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        
                        
                        for (Iterator<SubSurface> iterator1 = psubs.iterator(); iterator1.hasNext();) {
                          SubSurface p = iterator1.next();
                          
                            System.out.println("fend.session.SessionController.setQCFlag(): Trying to find : "+c.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                            if(p.getSubsurface().equals(c.getSubsurface())){
                                 System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!found : "+c.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                                refSub=p;
                            }
                        }
                        
                        if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                                //Change this to a computed hash.
                            
                            System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                            System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Setting QC flags to True");
                            child.setQcFlagProperty(Boolean.TRUE);
                            targetVol.setQcFlagProperty(Boolean.TRUE);
                            targetSeq.setQcFlagProperty(Boolean.TRUE);
                            targetSub.setQcFlagProperty(Boolean.TRUE);
                        }
                        else{
                            System.out.println("fend.session.SessionController.setQCFlag(): FALSE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                            System.out.println("fend.session.SessionController.setQCFlag(): FALSE:: Setting QC flags to FALSE");
                            child.setQcFlagProperty(Boolean.FALSE);
                            targetVol.setQcFlagProperty(Boolean.FALSE);
                            targetSeq.setQcFlagProperty(Boolean.FALSE);
                            targetSub.setQcFlagProperty(Boolean.FALSE);
                        }
            
        }
         
         List<JobStepModel> grandChildren=child.getJsChildren();
         for (Iterator<JobStepModel> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepModel gchild = iterator.next();
             System.out.println("fend.session.SessionController.setQCFlag():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            setQCFlag(child, gchild);
        }
         
         
        
         
         
         
         
         
    }
   
    
   
}
