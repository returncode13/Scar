/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import collector.Collector;
import db.model.Acquisition;
import db.model.Ancestors;
import db.model.Child;
import db.model.Descendants;
import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.OrcaView;
import db.model.Parent;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Volume;
import db.services.AcquisitionService;
import db.services.AcquisitionServiceImpl;
import db.services.AncestorsService;
import db.services.AncestorsServiceImpl;
import db.services.ChildService;
import db.services.ChildServiceImpl;
import db.services.DescendantsService;
import db.services.DescendantsServiceImpl;
import db.services.HeadersService;
import db.services.HeadersServiceImpl;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.JobVolumeDetailsService;
import db.services.JobVolumeDetailsServiceImpl;
import db.services.OrcaViewService;
import db.services.OrcaViewServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.overview.OverviewController;
import fend.overview.OverviewItem;
import fend.overview.OverviewModel;
import fend.overview.OverviewNode;
import fend.overview.PendingJobsController;
import fend.overview.PendingJobsModel;
import fend.overview.PendingJobsNode;
import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.headers.SubSurface;
import fend.session.node.jobs.type1.JobStepType1Node;
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
import fend.session.node.jobs.type1.JobStepType1Model;
import fend.session.node.jobs.type1.JobStepType1NodeController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.jobs.type0.JobStepType0Node;
import fend.session.node.jobs.type0.JobStepType0NodeController;
import fend.session.node.jobs.type2.JobStepType2Model;
import fend.session.node.jobs.type2.JobStepType2Node;
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
import org.apache.commons.collections4.map.MultiValueMap;
import org.controlsfx.control.GridView;

/**
 *
 * @author naila0152
 */
public class SessionController implements Initializable {
    
    
    private ArrayList<JobStepType0Model> jobStepModelList=new ArrayList<>();
    //private ObservableList<JobStepModel> obsModelList=FXCollections.observableList(jobStepModelList);
    private SessionModel model=new SessionModel();
    private ObservableList<JobStepType0Model> obsModelList=FXCollections.observableList(model.listOfJobs);
    private List<VolumeSelectionModel> dummyList = new ArrayList<>();
    private JobStepType0Node jsn;
    
    private ArrayList<LinksModel> linksModelList=new ArrayList<>();
    private ObservableList<LinksModel> obsLinksModelList=FXCollections.observableList(linksModelList);
    
    private List<String> pendingarray;
    private SessionNode snn;
    
    
     private Map<JobStepType0Node,AnchorModel> jsnAnchorMap=new HashMap<>();
       
     private List<JobStepType0Node> roots=new ArrayList<>();                             // A list of possible root nodes. i.e step1->step2 and step1->step3  implies step1 is the root of the structure. However we can also have several independent graphs
                                                           // e.g. one graph is step1-> step2 and step1-> step3  
                                                           //the other graph is step6-> step7 and step6-> step8.
                                                           // here there are two roots namely step1 and step6
    
    private List<JobStepType0Model> modelRoots=new ArrayList<>();
    private int rowNo,ColNo;
    private int numCols=1;
    private int numRows=0;
   
    private Collector collector=new Collector();
    private Long id;
    
    
    
    //private AcquisitionService acqServ=new AcquisitionServiceImpl();
    private OrcaViewService orcaServ=new OrcaViewServiceImpl();
    private JobStepService jServ=new JobStepServiceImpl();
    private SessionDetailsService ssdServ=new SessionDetailsServiceImpl();
    private JobVolumeDetailsService jvdServ=new JobVolumeDetailsServiceImpl();
    private AncestorsService ancServ=new AncestorsServiceImpl();
    private DescendantsService descServ=new DescendantsServiceImpl();
    private VolumeService volServ=new VolumeServiceImpl();
    private HeadersService hdrServ=new HeadersServiceImpl();
    private ParentService pserv=new ParentServiceImpl();
    private ChildService cserv=new ChildServiceImpl();
    

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
    private Button addJobStepButton1;

    @FXML
    private Button addJobStepButton2;
     
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
                 
         List<JobStepType0Model> jobs=obsModelList;
         PendingJobsModel pmodel=new PendingJobsModel();
         pmodel.setPendingjobs(pendingarray);
         PendingJobsNode pnode=new PendingJobsNode(pmodel);
         PendingJobsController pcontr=new PendingJobsController();
         
         for (Iterator<JobStepType0Model> iterator = jobs.iterator(); iterator.hasNext();) {
             JobStepType0Model job = iterator.next();
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
    void handleAddJobStepType2Button(ActionEvent event) {
        
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            
        }
        model.addJobToSession(new JobStepType2Model(model));
       // obsModelList.add(model.getListOfJobs().get(model.getListOfJobs().size()-1));
        obsModelList=model.getListOfJobs();
        jsn=new JobStepType2Node((JobStepType2Model) obsModelList.get(obsModelList.size()-1));
      
        
       rightInteractivePane.getChildren().add((JobStepType2Node)jsn);
        
      
    }
     
    @FXML
    void handleAddJobStepType1Button(ActionEvent event) {
        //dummyList.add(new VolumeSelectionModel("v1", Boolean.TRUE));
       // dummyList.add(new VolumeSelectionModel("v2", Boolean.TRUE));
       // obsModelList.add(new JobStepModel("SRME", dummyList));
       
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            
        }
        model.addJobToSession(new JobStepType1Model(model));
       // obsModelList.add(model.getListOfJobs().get(model.getListOfJobs().size()-1));
        obsModelList=model.getListOfJobs();
        jsn=new JobStepType1Node((JobStepType1Model) obsModelList.get(obsModelList.size()-1));
      
        
       rightInteractivePane.getChildren().add((JobStepType1Node)jsn);
        
      
       
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
            jsn=(JobStepType1Node) rightInteractivePane.lookup("#"+nodeId);
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
                
               // obsLinksModelList.add(ln.getLmodel());
            }
            
            
            obsLinksModelList=FXCollections.observableArrayList(model.getListOfLinks());
            
        }
            
            //model.setListOfJobs(obsModelList);
            //model.setListOfLinks(linksModelList);
            
                System.out.println("fend.sessions.SessionController.commit: Set the last model");

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

    public void setObsModelList(ObservableList<JobStepType0Model> obsModelList) {
        this.obsModelList = obsModelList;
        System.out.println("Inside fend.session.SessionController.setObsModelList()");
        printGraph(obsModelList);
        
        
        
     /*   System.out.println("Inside fend.session.SessionController.setObsModelList()");
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
            
        }*/
    }

    
    private void printGraph(List<JobStepType0Model> jlist){
        ArrayList<JobStepType0Model> rootList=new ArrayList<>();
        for (Iterator<JobStepType0Model> iterator = jlist.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            if(next.getJsParents().size()==1 && next.getJsParents().get(0).getId().equals(next.getId())){
                rootList.add(next);
            }
            
        }
        
        for (Iterator<JobStepType0Model> iterator = rootList.iterator(); iterator.hasNext();) {
            JobStepType0Model root = iterator.next();
            List<JobStepType0Model> children=root.getJsChildren();
            System.out.println(root.getJobStepText()+"--|");
        //    System.out.print(printSpace(root.getJobStepText().length()+2)+"|--");
            for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
                JobStepType0Model next = iterator1.next();
                //System.out.print(next.getJobStepText());
               
                walk(root,next,root.getJobStepText().length()+2);
            }
            
        }
                
    }

    private String printSpace(int n){
        String c=new String();
        for(int i=0;i<n;i++)c+=" ";
        return c;
    }
    
    
    private void walk(JobStepType0Model parent,JobStepType0Model child,int ii){
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(0).getId().equals(parent.getId())){return;}
        else{
            
            System.out.println(printSpace(ii)+"|--"+child.getJobStepText());
            ii+=child.getJobStepText().length();
            List<JobStepType0Model> children=child.getJsChildren();
            for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                JobStepType0Model next = iterator.next();
                if(!next.getId().equals(child.getId())){
                    //System.out.println(printSpace(ii)+"|--"+next.getJobStepText());
                    ii+=next.getJobStepText().length();
                }
                
                       
                walk(child,next,ii);
            }
        }
        
    }
    public ObservableList<JobStepType0Model> getObsModelList() {
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
                
                //obsLinksModelList.add(ln.getLmodel());
                obsLinksModelList=FXCollections.observableArrayList(model.getListOfLinks());
            }
            
            
            
            
        }
       List<JobStepType0Model> jobsToBeDeleted=model.getJobsToBeDeleted();
       for (Iterator<JobStepType0Model> iterator = jobsToBeDeleted.iterator(); iterator.hasNext();) {
           JobStepType0Model jobTobeDeleted = iterator.next();
           JobStep jsd=jServ.getJobStep(jobTobeDeleted.getId());
           if(jsd!=null){
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: job found with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
           List<SessionDetails> sessionDetailsList=ssdServ.getSessionDetails(jsd);        // all the sessions to which this job belongs to.
           List<JobVolumeDetails> jobvolumeDetailsList=jvdServ.getJobVolumeDetails(jsd);  //list of volumes that belong to this job
           /*
           for (Iterator<SessionDetails> iterator1 = sessionDetailsList.iterator(); iterator1.hasNext();) {
           SessionDetails sessionDetails = iterator1.next();
           Sessions session=sessionDetails.getSessions();                           //get one of the sessions
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING sessiondetails with id: "+sessionDetails.getIdSessionDetails()+" : session:  "+sessionDetails.getSessions().getNameSessions()+"  :job: "+sessionDetails.getJobStep().getNameJobStep() );
           List<Parent> pl=pserv.getParentsFor(sessionDetails);
           for (Iterator<Parent> pit = pl.iterator(); pit.hasNext();) {
           Parent pan = pit.next();
           Long pin=pan.getIdParent();
           pserv.deleteParent(pin);
           }
           
           List<Child> cl=cserv.getChildrenFor(sessionDetails);
           for (Iterator<Child> cin = cl.iterator(); cin.hasNext();) {
           Child chan = cin.next();
           Long cid=chan.getIdChild();
           cserv.deleteChild(cid);
           
           }
           
           
           //ssdServ.deleteSessionDetails(sessionDetails.getIdSessionDetails());
           }
           */
           
           
           
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: DELETING job with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
           jServ.deleteJobStep(jsd.getIdJobStep());
           
           for (Iterator<JobVolumeDetails> iterator1 = jobvolumeDetailsList.iterator(); iterator1.hasNext();) {
               JobVolumeDetails jvd = iterator1.next();
               Volume v=jvd.getVolume();
               List<Headers> hdrList=hdrServ.getHeadersFor(v);
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: found volume with id: "+v.getIdVolume()+ " :name: "+v.getNameVolume());
                for (Iterator<Headers> iterator2 = hdrList.iterator(); iterator2.hasNext();) {
                   Headers hdr = iterator2.next();
                    System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING header wtih id: "+hdr.getIdHeaders()+" : subsurface:  "+hdr.getSubsurface() );
                   hdrServ.deleteHeaders(hdr.getIdHeaders());
               }
               
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING Jobvolumedetails with id: "+jvd.getIdJobVolumeDetails()+" : job:  "+jvd.getJobStep().getNameJobStep()+" :volume: "+jvd.getVolume().getNameVolume() ); 
               //jvdServ.deleteJobVolumeDetails(jvd.getIdJobVolumeDetails());
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING volume with id: "+v.getIdVolume()+" : name:  "+v.getNameVolume() ); 
               volServ.deleteVolume(v.getIdVolume());
           }
           
            
          
            
           
         
           }
           
           
           
       }
       
       
       
       
        if(obsModelList.size()!=model.getListOfJobs().size()){
            System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): Found obslist with: "+obsModelList.size()+" and model with: "+model.getListOfJobs().size());
        }
            obsModelList=model.getListOfJobs();
            
       for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
           JobStepType0Model next = iterator.next();
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit():  jobStepModeList : "+next.getJobStepText());
           
       }
       
           model.setListOfJobs(obsModelList);
            
            for (Iterator<JobStepType0Model> iterator = jobStepModelList.iterator(); iterator.hasNext();) {
           JobStepType0Model next = iterator.next();
           
              //  System.out.println("SessContr: Checking on the Kids");
                ArrayList<JobStepType0Model> children=(ArrayList<JobStepType0Model>) next.getJsChildren();
                
                for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
                    JobStepType0Model child1 = iterator1.next();
                //    System.out.println("SessContr Parent: "+next.getJobStepText()+"   Child: "+child1.getJobStepText());
                    
                }
           
       }
            
            //model.setListOfLinks(linksModelList);
            
            System.out.println("SC: model has ID: "+model.getId());
   }
   
   
   public void setAllModelsForFrontEndDisplay(){
       
       
      
       
       
       for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
           
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): display contents");
           
           JobStepType0Model next = iterator.next();
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
           Long type=next.getType();
           if(type.equals(1L)){
               jsn=new JobStepType1Node((JobStepType1Model) next);
           }
           if(type.equals(2L)){
               jsn=new JobStepType2Node((JobStepType2Model) next);
           }
           
            JobStepType0NodeController jsc=jsn.getJsnc();
            
            JobStepType0Model jsmod=jsc.getModel();
            
            ArrayList<JobStepType0Model> jsmodParents=(ArrayList<JobStepType0Model>) jsmod.getJsParents();
            if (jsmodParents.size()==1){
               
                if(jsmod.getId().equals(jsmodParents.get(0).getId())){
                     System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  "+jsmodParents.get(0).getJobStepText()+" is a root..adding to list of roots");
                    System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay():  id matched for model and the single content in the list of Parents");
                    if(jsn instanceof JobStepType1Node){
                        roots.add((JobStepType1Node) jsn);
                    }if(jsn instanceof JobStepType2Node){
                        roots.add((JobStepType2Node) jsn);
                    }
                    
                }
            }
            
            
            AnchorModel mstart= new AnchorModel();
            
          
            
            
            
            
           
            
            ObservableList obvolist=next.getVolList();
           
            jsc.setObsList(obvolist);
            jsc.setInsightVersionsModel(insVerModel);
           jsc.setVolumeModelsForFrontEndDisplay();
           jsc.setInsightListForFrontEndDisplay();
           Double centerX=0.0;
           Double centerY=0.0;
           if(jsn instanceof JobStepType1Node){
                        
                        rightInteractivePane.getChildren().add((JobStepType1Node)jsn);
                        centerX=((JobStepType1Node)jsn).boundsInLocalProperty().getValue().getMinX();
                        centerY=((JobStepType1Node)jsn).boundsInLocalProperty().getValue().getMinY()+((JobStepType1Node)jsn).boundsInLocalProperty().get().getHeight()/2;
                        mstart.setJob(next);
                        mstart.setCenterX(centerX);
                        mstart.setCenterY(centerY);
            
           
            
                        jsnAnchorMap.put((JobStepType1Node)jsn, mstart);
                    }
           if(jsn instanceof JobStepType2Node){
                        rightInteractivePane.getChildren().add((JobStepType2Node)jsn);
                        centerX=((JobStepType2Node)jsn).boundsInLocalProperty().getValue().getMinX();
                        centerY=((JobStepType2Node)jsn).boundsInLocalProperty().getValue().getMinY()+((JobStepType2Node)jsn).boundsInLocalProperty().get().getHeight()/2;
                        mstart.setJob(next);
                        mstart.setCenterX(centerX);
                        mstart.setCenterY(centerY);
            
           
            
                        jsnAnchorMap.put((JobStepType2Node)jsn, mstart);
                    }
            
            
          /*  
           try {
               Thread.sleep(100000000L);
           } catch (InterruptedException ex) {
               Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
           }
            */
            
            
            
           
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
        for (Iterator<JobStepType0Node> iterator = roots.iterator(); iterator.hasNext();) {
           JobStepType0Node next = iterator.next();
           drawCurve(next,jsnAnchorMap);
           
           
       }
   }
   
    private void drawCurve(JobStepType0Node next, Map<JobStepType0Node, AnchorModel> jsnAnchorMap) {
        
        JobStepType0Model jsmod=next.getJsnc().getModel();
        AnchorModel mstart=new AnchorModel();
        /*Double centerX=next.boundsInLocalProperty().getValue().getMinX()+310;
        Double centerY=next.boundsInLocalProperty().getValue().getMinY()+72;*/
          Double centerX=((AnchorPane)next).boundsInLocalProperty().getValue().getMaxX();
            Double centerY=((AnchorPane)next).boundsInLocalProperty().getValue().getMaxY()/2;
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
                
        ArrayList<JobStepType0Model> children=(ArrayList<JobStepType0Model>) jsmod.getJsChildren();
        
        
        for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
            JobStepType0Model next1 = iterator.next();
            
            if(next1.getId().equals(jsmod.getId())){
                System.out.println("fend.session.SessionController.drawCurve(): "+jsmod.getJobStepText()+ " :is a leaf: "+next1.getJobStepText());
                return;
            }
                for (Map.Entry<JobStepType0Node, AnchorModel> entry : jsnAnchorMap.entrySet()) {
                JobStepType0Node key = entry.getKey();
                AnchorModel mEnd = entry.getValue();
               // AnchorModel mEnd= new AnchorModel();
                Long keyId=Long.parseLong(((AnchorPane)key).getId());         //since jobstepnodes id is a string
                   // System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " id of node: "+keyId);
                
                if(next1.getId().equals(keyId)){
                    System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " EQUALS id of node: "+keyId+ " : starting to draw cubic curves here: ");
                    System.out.println("fend.session.SessionController.drawCurve()    found   : "+next1.getJobStepText()+" === "+key.getJsnc().getModel().getJobStepText());
                  // double sx=mEnd.getCenterX().doubleValue();
                 //  double sy=mEnd.getCenterY().doubleValue();
                    System.out.println("fend.session.SessionController.drawCurve() MAYlayoutY: "+((AnchorPane)key).getParent().boundsInLocalProperty().getValue().getMaxY());
                    System.out.println("fend.session.SessionController.drawCurve() "+((AnchorPane)key).getProperties().toString());
                  double sx=((AnchorPane)key).boundsInLocalProperty().getValue().getMaxX();
                // double sy=key.boundsInLocalProperty().getValue().getMinY()+144;
                  double sy=((AnchorPane)key).boundsInLocalProperty().getValue().getMaxY()/2;
                  //double sx=key.getLayoutX();
                  //double sy=key.getLayoutY();
                  
                   mEnd.setJob(next1);
                  // mEnd.setCenterX(sx);
                   
                   //mEnd.setCenterY(sy);                    //redundant but for the sake of uniformity..   :(
                   
                   
                    CubCurveModel cmod=new CubCurveModel();
                    
                    LinksModel lm=new LinksModel(mstart, mEnd, cmod);
                    Links ln=new Links(lm);
                    
                    CubCurve curve=ln.getCurve();
                    next.getJsnc().getModel().addToListOfLinksModel(lm);
                    key.getJsnc().getModel().addToListOfLinksModel(lm);
                    
                   
                    // DoubleProperty d= new SimpleDoubleProperty(next.layoutXProperty().get()+310);
                   // curve.startXProperty().bindBidirectional(d);
                    
                    //
                    //curve.startYProperty().bindBidirectional(new SimpleDoubleProperty(next.layoutYProperty().get()+72));
                    //Bindings.add(next.layoutXProperty(), next.boundsInLocalProperty().get().getMinX()+310);
                   // Bindings.add(next.layoutYProperty(),next.boundsInLocalProperty().get().getMinY()+72);
                   
                  //  curve.startXProperty().bindBidirectional(next.layoutXProperty());
                    //curve.startYProperty().bindBidirectional(next.layoutYProperty());
                    
                 //   System.out.println("fend.session.SessionController.drawCurve():  for: "+next.getJsnc().getModel().getJobStepText()+" : child: "+key.getJsnc().getModel().getJobStepText());
                    
                    curve.startXProperty().bind(Bindings.add(((AnchorPane)next).layoutXProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinX()+515));         //next is the parent node
                    curve.startYProperty().bind(Bindings.add(((AnchorPane)next).layoutYProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinY()+74));
                    /*curve.startXProperty().bind(Bindings.add(next.layoutXProperty(),next.boundsInLocalProperty().get().getMaxX()));         //next is the parent node
                    curve.startYProperty().bind(Bindings.add(next.layoutYProperty(),next.boundsInLocalProperty().get().getMaxY()/2));*/
                    curve.endXProperty().bind(Bindings.add(((AnchorPane)key).layoutXProperty(),((AnchorPane)key).boundsInLocalProperty().get().getMinX()));           //key in the child node
                    curve.endYProperty().bind(Bindings.add(((AnchorPane)key).layoutYProperty(),((AnchorPane)key).boundsInLocalProperty().get().getMinY()+74));       // this is HARDCODED!!! find a way to get the height of the node!
                   
                    /*curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getMinY()));
                    System.out.println("Next MaxX(): "+next.boundsInLocalProperty().get().getMaxX());
                    System.out.println("Next MinX(): "+next.boundsInLocalProperty().get().getMinX());
                    System.out.println("Next MaxY(): "+next.boundsInLocalProperty().get().getMaxY());
                    System.out.println("Next MinY(): "+next.boundsInLocalProperty().get().getMinY());
                    System.out.println("Next PHeight(): "+next.boundsInLocalProperty().get().getHeight());
                    System.out.println("Next PWidth(): "+next.boundsInParentProperty().get().getWidth());
                    System.out.println("Next PMaxX(): "+next.boundsInParentProperty().get().getMaxX());
                    System.out.println("Next PMinX(): "+next.boundsInParentProperty().get().getMinX());
                    System.out.println("Next PMaxY(): "+next.boundsInParentProperty().get().getMaxY());
                    System.out.println("Next PMinY(): "+next.boundsInParentProperty().get().getMinY());
                    
                    System.out.println("Key MaxX(): "+key.boundsInLocalProperty().get().getMaxX());
                    System.out.println("Key MinX(): "+key.boundsInLocalProperty().get().getMinX());
                    System.out.println("Key MaxY(): "+key.boundsInLocalProperty().get().getMaxY());
                    System.out.println("Key MinY(): "+key.boundsInLocalProperty().get().getMinY());
                    System.out.println("Key Height(): "+key.boundsInLocalProperty().get().getHeight());
                    System.out.println("Key Width(): "+key.boundsInLocalProperty().get().getWidth());*/
                    
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
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model job = iterator.next();
            ArrayList<JobStepType0Model> jobParents=(ArrayList<JobStepType0Model>) job.getJsParents();
            if (jobParents.size()==1){
               
                if(job.getId().equals(jobParents.get(0).getId())){
                     System.out.println("fend.session.SessionController.setRoots():  "+job.getJobStepText()+" is a root..adding to list of roots");
                     /*System.out.println("fend.session.SessionController.setRoots() :  id matched for model and the single content in the list of Parents");*/
                    modelRoots.add(job);
                }
            }
            
            
            
        }
        
        
         for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ObsModelList: "+next.getJobStepText());
            List<JobStepType0Model> chldn=next.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                System.out.println("fend.session.SessionController.setRoots(): ObsModeList job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                
                List<JobStepType0Model> gchild=next1.getJsChildren();
                    for (Iterator<JobStepType0Model> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ObsModeList child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
                
                
            }
            
            
        }
         
         
         for (Iterator<JobStepType0Model> iterator = modelRoots.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ModelRoots: "+next.getJobStepText());
            List<JobStepType0Model> chldn=next.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                
                System.out.println("fend.session.SessionController.setRoots(): ModelRoots job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                
                List<JobStepType0Model> gchild=next1.getJsChildren();
                    for (Iterator<JobStepType0Model> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ModelRoots child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
            }
            
        }
        
        
    }
    
    private Set<SubSurface> calculateSubsInJob(JobStepType0Model job){
        List<VolumeSelectionModel> volList=job.getVolList();
        Set<SubSurface> subsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModel> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModel vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurface> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            
            
        }
        job.setSubsurfacesInJob(subsInJob);
        /*for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
        SubSurface subinJob = iterator.next();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        
        return subsInJob;
    }
    
    
    
     private void tracking(){
         System.out.println("fend.session.SessionController.tracking():  STARTED");
         setRoots();
        // List<Acquisition> acuiredSubs=acqServ.getAcquisition();      // this will query the db. Maybe put a timer?
         List<OrcaView> acquiredSubs=orcaServ.getOrcaView();
         List<String> acqString=new ArrayList<>();                      // hold the names of the acquired subsurfaces
         
         for (Iterator<OrcaView> iterator = acquiredSubs.iterator(); iterator.hasNext();) {
         OrcaView acq = iterator.next();
         acqString.add(acq.getSubsurfaceLines());
         System.out.println("fend.session.SessionController.tracking(): in AcqString: added: "+acqString.get(acqString.size()-1));
         
         }
         
         List<String> jobSubString=new ArrayList<>();            // holds the names of the subsurfaces in the job
         
         for(JobStepType0Model root : modelRoots){
        
        // for (Iterator<JobStepModel> iteratorr = modelRoots.iterator(); iteratorr.hasNext();) {
          //   JobStepModel root = iteratorr.next();
             
         
             Set<SubSurface> rootsSubSurfaces=calculateSubsInJob(root);
              //Set<SubSurface> rootsSubSurfaces=new HashSet(root.getSeqSubsInJob().values());
             
             for (Iterator<SubSurface> iterator = rootsSubSurfaces.iterator(); iterator.hasNext();) {
             SubSurface subinRoot = iterator.next();
             jobSubString.add(subinRoot.getSubsurface().split("_")[0]);
             //System.out.println("fend.session.SessionController.tracking(): in jobSubstring: found: "+jobSubString.get(jobSubString.size()-1));
             
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
             
                List<JobStepType0Model> children = root.getJsChildren();
                for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): setPendingJobs: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepType0Model> gChild=child.getJsChildren();
                        for (Iterator<JobStepType0Model> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepType0Model next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                        pendingarray=new ArrayList<>();
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
     private void setPendingJobsFlag(JobStepType0Model parent,JobStepType0Model child){
         
         if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){
             System.out.println("collector.Collector.mismatch():  ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
         /*if(parent.getId().equals(child.getId())){
         System.out.println("collector.Collector.mismatch():  ROOT/LEAF found: "+parent.getJobStepText());
         return;
         }*/
         child.setPendingFlagProperty(Boolean.FALSE);
         
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): called for Parent: "+parent.getJobStepText()+" :child: "+child.getJobStepText());
         
         //Calculate the subsurfaces present in the parent
         
         
       // Set<SubSurface> pSubs=calculateSubsInJob(parent);
         Set<SubSurface> pSubs=parent.getSubsurfacesInJob();
         Set<SubSurface> cSubs=calculateSubsInJob(child);
      
         
         List<String> pSubsStrings=new ArrayList<>();
         List<String> cSubsStrings=new ArrayList<>();
         
         for (Iterator<SubSurface> iterator = pSubs.iterator(); iterator.hasNext();) {
         SubSurface subInParent = iterator.next();
         pSubsStrings.add(subInParent.getSubsurface());
        // System.out.println("fend.session.SessionController.setPendingJobsFlag():  pSubsStrings found : "+pSubsStrings.get(pSubsStrings.size()-1));
         
         }
         
         for (Iterator<SubSurface> iterator = cSubs.iterator(); iterator.hasNext();) {
         SubSurface subInChild = iterator.next();
         cSubsStrings.add(subInChild.getSubsurface());
         //System.out.println("fend.session.SessionController.setPendingJobsFlag():  cSubsStrings found : "+cSubsStrings.get(cSubsStrings.size()-1));
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
         pendingarray.addAll(remaining);
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): Pending flag set in model");
         }else     //child has no pending subs
         {
         child.setPendingFlagProperty(Boolean.FALSE);
          System.out.println("fend.session.SessionController.setPendingJobsFlag():  child :"+child.getJobStepText()+" has NO pending subs "+remaining);
         }
         }
         
         List<JobStepType0Model> grandChildren=child.getJsChildren();
         for(JobStepType0Model gchild:grandChildren){
             System.out.println("fend.session.SessionController.setPendingJobsFlag(): job: "+child.getJobStepText()+" :has child: "+gchild.getJobStepText());
         }
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
             JobStepType0Model grandchild = iterator.next();
             setPendingJobsFlag(child, grandchild);
         }
         
         
         
         
     }

    private void setQCFlag(JobStepType0Model parent,JobStepType0Model child){
       
        //If Child has been traversed then return.   Create a "traversed" flag in JobStepModel and set in each time the node is returning "upwards". i.e it and all of its descendants have been traversed, set its "traversed" flag to True
        Boolean traceFail=false;            //defualt QC status is false
        Boolean insightFail=false;
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
            
            //check for Insight Version mismatch
            Set<SubSurface> csubq=child.getSubsurfacesInJob();
            
            List<VolumeSelectionModel> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModel> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModel next = iterator.next();
            next.setQcFlagProperty(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
            
             child.setQcFlagProperty(Boolean.FALSE);
            
            List<String> versionsSelectedInChildQ=child.getInsightVersionsModel().getCheckedVersions();
                        MultiValueMap<String,String> baseRevisionFromJobMapQ=new MultiValueMap<>();
                        
                      for (Iterator<SubSurface> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurface refSubQ = iterator.next();
                        /*String baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                        String revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];*/
                        String baseVersionFromSubQ=new String();
                        String revisionOfVersionFromSubQ=new String();
                        if(refSubQ.getInsightVersion()!=null){
                            baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                            revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];
                        }
                           
                       
            VolumeSelectionModel targetVolQ=new VolumeSelectionModel(1L);
            Sequences targetSeqQ=new Sequences();
            SubSurface targetSubQ=refSubQ;
                        
                           for (Iterator<VolumeSelectionModel> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModel vc = iterator1.next();
                            Set<SubSurface> vcSub=vc.getSubsurfaces();
                           // System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSubQ)){
                              //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVolQ=vc;
                                targetVolQ.setVolumeType(vc.getVolumeType());
                                break;
                            }
                 
                        }
                        
                        if(targetVolQ!=null){
                            HeadersModel hmod=targetVolQ.getHeadersModel();
                            List<Sequences> seqList=hmod.getSequenceListInHeaders();
                            
                for (Sequences seq : seqList) {
                    if(targetSubQ.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeqQ=seq;
                        //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
                    }
                    
                    
                    /* List<SubSurface> seqSubs=seq.getSubsurfaces();
                    System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                    if(seqSubs.contains(targetSub)){
                    targetSeq=seq;
                    System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                    targetSub.setSequenceNumber(seq.getSequenceNumber());
                    break;
                    }*/
                }
                            
                        }else
                        {
                                try {
                                    throw new Exception("Subline: "+targetSubQ.getSubsurface()+" :not found in any of the child job: "+child.getJobStepText()+" : volumes!!");
                                } catch (Exception ex) {
                                    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        
                        
                        for (String s:versionsSelectedInChildQ){
                           
                            String[] parts=s.split("-");
                            String base=parts[0];
                            String rev=parts[1];
                            
                             
                            baseRevisionFromJobMapQ.put(base, rev);
                            
                            System.out.println("fend.session.SessionController.setQCFlag(): Sub: base"+baseVersionFromSubQ+" rev:"+revisionOfVersionFromSubQ);
                            
                            
                        }
                         Set<String> baseKeysQ=baseRevisionFromJobMapQ.keySet();
                        /*for (Iterator<String> iterator1 = baseKeys.iterator(); iterator1.hasNext();) {
                        String next = iterator1.next();
                        List<String> revs=(List<String>) baseRevisionFromJobMap.get(next);
                        
                        
                        for (Iterator<String> iterator2 = revs.iterator(); iterator2.hasNext();) {
                        String next1 = iterator2.next();
                        System.out.println("fend.session.SessionController.setQCFlag() Job: base: "+next+" rev: "+next1);
                        }
                        }*/
                        
                        String errorMessage=targetSubQ.getErrorMessage();
                          System.out.println("fend.session.SessionController.setQCFlag() OldErrorMessage: "+errorMessage);
                          if(errorMessage==null){
                              errorMessage=new String();
                          }
                        if(errorMessage.contains("version")){
                            insightFail=true;
                        }else
                        {
                            insightFail=false;
                        }
                        if(errorMessage.contains("tracecount")){ 
                            traceFail=true;
                        }else
                        {
                            traceFail=false;
                        }
                        
                        if(!baseKeysQ.contains(baseVersionFromSubQ)){
                            //Turn the sub and job QC flag =true;
                                insightFail=true;
                                //errorMessage="version mismatch";
                                /*child.setQcFlagProperty(Boolean.TRUE);
                                targetVolQ.setQcFlagProperty(Boolean.TRUE);
                                System.out.println("After entering loop");
                                targetVolQ.printQC();
                                targetSeqQ.setAlert(Boolean.TRUE);
                                targetSeqQ.setErrorMessage("version mismatch");
                                targetSubQ.setAlert(Boolean.TRUE);
                                targetSubQ.setErrorMessage("version mismatch");*/
                            
                        }
                        else 
                        {
                            List<String> revList=(List<String>) baseRevisionFromJobMapQ.get(baseVersionFromSubQ);
                            System.out.println("fend.session.SessionController.setQCFlag(): found base: "+baseVersionFromSubQ);
                            
                            if(!revList.contains(revisionOfVersionFromSubQ)){
                                insightFail=true;
                                //errorMessage="version mismatch";
                                /*System.out.println("fend.session.SessionController.setQCFlag(): rev: "+revisionOfVersionFromSubQ+" missing from the list");
                                child.setQcFlagProperty(Boolean.TRUE);
                                targetVolQ.setQcFlagProperty(Boolean.TRUE);
                                System.out.println("After entering loop");
                                targetVolQ.printQC();
                                targetSeqQ.setAlert(Boolean.TRUE);
                                targetSeqQ.setErrorMessage("version mismatch");
                                targetSubQ.setAlert(Boolean.TRUE);
                                targetSubQ.setErrorMessage("version mismatch");*/
                            }
                            else{
                                insightFail=false;
                                /*System.out.println("fend.session.SessionController.setQCFlag(): rev: "+revisionOfVersionFromSubQ+" missing from the list");
                                child.setQcFlagProperty(Boolean.FALSE);
                                targetVolQ.setQcFlagProperty(Boolean.FALSE);
                                System.out.println("After entering loop");
                                targetVolQ.printQC();
                                targetSeqQ.setAlert(Boolean.FALSE);
                                targetSeqQ.setErrorMessage("");
                                targetSubQ.setAlert(Boolean.FALSE);
                                targetSubQ.setErrorMessage("");*/
                            }
                        }
                        
                            Boolean QCFailure=traceFail || insightFail;
                            System.out.println("fend.session.SessionController.setQCFlag(): QCFailure Flag: "+QCFailure);
                            child.setQcFlagProperty(QCFailure);
                            targetVolQ.setQcFlagProperty(QCFailure);
                            targetSeqQ.setAlert(QCFailure);
                            targetSubQ.setAlert(QCFailure);
                              if(insightFail && !traceFail){
                                   errorMessage="version mismatch";
                               }
                               if(traceFail && !insightFail){
                                   errorMessage="tracecount mismatch";
                               }
                               if(insightFail &&  traceFail){
                                   errorMessage="version and tracecount mismatch";
                               }
                               if(!traceFail && !insightFail)
                               {
                                   errorMessage="";
                               }
                            targetSeqQ.setErrorMessage(errorMessage);
                            targetSubQ.setErrorMessage(errorMessage);
                            
                        
                        }  
                        
                        
                        
                  //end checking for insight version       
                        
                        
            System.out.println("collector.Collector.setQCFlag():  ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        
        /*if(parent.getId().equals(child.getId())){
        System.out.println("collector.Collector.setQCFlag():  ROOT/LEAF found: "+parent.getJobStepText());
        return;
        }*/
          child.setQcFlagProperty(Boolean.FALSE);
         
         //Set<SubSurface> csubs= calculateSubsInJob(child);
         //Set<SubSurface> psubs=calculateSubsInJob(parent);
         Set<SubSurface> psubs=parent.getSubsurfacesInJob();
         Set<SubSurface> csubs=child.getSubsurfacesInJob();
         
         List<VolumeSelectionModel> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModel> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModel next = iterator.next();
            next.setQcFlagProperty(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
        
         
         
         for (Iterator<SubSurface> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurface c = iterator.next();
            VolumeSelectionModel targetVol=new VolumeSelectionModel(1L);
            Sequences targetSeq=new Sequences();
            SubSurface targetSub=c;
            SubSurface refSub=new SubSurface();
            
                        for (Iterator<VolumeSelectionModel> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModel vc = iterator1.next();
                            Set<SubSurface> vcSub=vc.getSubsurfaces();
                           // System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                              //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVol=vc;
                                targetVol.setVolumeType(vc.getVolumeType());
                                break;
                            }
                 
                        }
                        
                        if(targetVol!=null){
                            HeadersModel hmod=targetVol.getHeadersModel();
                            List<Sequences> seqList=hmod.getSequenceListInHeaders();
                            
                for (Sequences seq : seqList) {
                    if(targetSub.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeq=seq;
                        //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
                    }
                    
                    
                    /* List<SubSurface> seqSubs=seq.getSubsurfaces();
                    System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                    if(seqSubs.contains(targetSub)){
                    targetSeq=seq;
                    System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                    targetSub.setSequenceNumber(seq.getSequenceNumber());
                    break;
                    }*/
                }
                            
                        }else
                        {
                                try {
                                    throw new Exception("Subline: "+targetSub.getSubsurface()+" :not found in any of the child job: "+child.getJobStepText()+" : volumes!!");
                                } catch (Exception ex) {
                                    Logger.getLogger(SessionController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        /*
                        if(psubs.contains(c)){
                        System.out.println("SessionController.setQCFlag(): FOUND "+c.getSubsurface()+" : in Parent list!");
                        refSub=c;
                        }*/
                        
                        for (Iterator<SubSurface> iterator1 = psubs.iterator(); iterator1.hasNext();) {
                          SubSurface p = iterator1.next();
                          if(csubs.contains(p))
                          {
                              if(p.equals(c)){
                            //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!found : "+p.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                              refSub=p;
                              
                              /*if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                                //Change this to a computed hash.
                        
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Setting QC flags to True : on volume : "+targetVol.getLabel()+" : Seq: "+targetSeq.getSequenceNumber()+" : "+targetSub.getSubsurface());
                                child.setQcFlagProperty(Boolean.TRUE);
                                targetVol.setQcFlagProperty(Boolean.TRUE);
                                targetVol.printQC();
                                targetSeq.setAlert(Boolean.TRUE);
                                targetSub.setAlert(Boolean.TRUE);
                                }
                                else{
                                  /* System.out.println("fend.session.SessionController.setQCFlag(): FALSE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                                  System.out.println("fend.session.SessionController.setQCFlag(): FALSE:: Setting QC flags to FALSE");*/
                              /*  child.setQcFlagProperty(Boolean.FALSE);
                                targetVol.setQcFlagProperty(Boolean.FALSE);
                                targetSeq.setAlert(Boolean.FALSE);
                                targetSub.setAlert(Boolean.FALSE);
                                }
                              */
                              
                              break;
                              }
                             
                          }
                          /*System.out.println("fend.session.SessionController.setQCFlag(): Trying to find : "+c.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                          if(p.getSubsurface().equals(c.getSubsurface())){
                          System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!found : "+c.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                          refSub=p;
                          break;
                          }*/
                        }
                        
                         String errorMessage=new String();
                        if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                                //Change this to a computed hash.
                                traceFail=true;
                               // errorMessage="tracecount mismatch";
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Setting QC flags to True : on volume : "+targetVol.getLabel()+" : Seq: "+targetSeq.getSequenceNumber()+" : "+targetSub.getSubsurface());
                                /*child.setQcFlagProperty(Boolean.TRUE);
                                targetVol.setQcFlagProperty(Boolean.TRUE);
                                System.out.println("After entering loop");
                                targetVol.printQC();
                                targetSeq.setAlert(Boolean.TRUE);
                                targetSub.setAlert(Boolean.TRUE);
                                targetSeq.setErrorMessage("tracecount mismatch");
                                targetSub.setErrorMessage("tracecount mismatch");*/
                                }
                        else{
                            traceFail=false;
                            
                        }
                        
                        
                        List<String> versionsSelectedInChild=child.getInsightVersionsModel().getCheckedVersions();
                        MultiValueMap<String,String> baseRevisionFromJobMap=new MultiValueMap<>();
                        String baseVersionFromSub=new String();
                        String revisionOfVersionFromSub=new String();
                        if(refSub.getInsightVersion()!=null){
                            baseVersionFromSub=refSub.getInsightVersion().split("\\(")[0];
                            revisionOfVersionFromSub=refSub.getInsightVersion().split("\\(")[1].split("\\)")[0];
                        }
                        //System.out.println("fend.session.SessionController.setQCFlag(): InsightVersions for sub: "+refSub.getSubsurface()+" vers: "+refSub.getInsightVersion());
                        /* else
                        {
                        
                        }
                        */
                            
                        for (String s:versionsSelectedInChild){
                           
                            String[] parts=s.split("-");
                            String base=parts[0];
                            String rev=parts[1];
                            
                             
                            baseRevisionFromJobMap.put(base, rev);
                            
                            System.out.println("fend.session.SessionController.setQCFlag(): Sub: base"+baseVersionFromSub+" rev:"+revisionOfVersionFromSub);
                           
                            
                        }
                        
                       
                        
                        Set<String> baseKeys=baseRevisionFromJobMap.keySet();
                        /*for (Iterator<String> iterator1 = baseKeys.iterator(); iterator1.hasNext();) {
                        String next = iterator1.next();
                        List<String> revs=(List<String>) baseRevisionFromJobMap.get(next);
                        
                        
                        for (Iterator<String> iterator2 = revs.iterator(); iterator2.hasNext();) {
                        String next1 = iterator2.next();
                        System.out.println("fend.session.SessionController.setQCFlag() Job: base: "+next+" rev: "+next1);
                        }
                        }*/
                        
                        if(!baseKeys.contains(baseVersionFromSub)){
                            //Turn the sub and job QC flag =true;
                                insightFail=true;
                               // errorMessage+="version mismatch";
                                /*child.setQcFlagProperty(Boolean.TRUE);
                                targetVol.setQcFlagProperty(Boolean.TRUE);
                                System.out.println("After entering loop");
                                targetVol.printQC();
                                targetSeq.setAlert(Boolean.TRUE);
                                targetSeq.setErrorMessage("version mismatch");
                                targetSub.setAlert(Boolean.TRUE);
                                targetSub.setErrorMessage("version mismatch");*/
                            
                        }
                        else 
                        {
                            List<String> revList=(List<String>) baseRevisionFromJobMap.get(baseVersionFromSub);
                            System.out.println("fend.session.SessionController.setQCFlag(): found base: "+baseVersionFromSub);
                            
                            if(!revList.contains(revisionOfVersionFromSub)){
                                insightFail=true;
                                //errorMessage+="version mismatch";
                                /*System.out.println("fend.session.SessionController.setQCFlag(): rev: "+revisionOfVersionFromSub+" missing from the list");
                                child.setQcFlagProperty(Boolean.TRUE);
                                targetVol.setQcFlagProperty(Boolean.TRUE);
                                System.out.println("After entering loop");
                                targetVol.printQC();
                                targetSeq.setAlert(Boolean.TRUE);
                                targetSeq.setErrorMessage("version mismatch");
                                targetSub.setAlert(Boolean.TRUE);
                                targetSub.setErrorMessage("version mismatch");*/
                            }
                            // TO BE ENABLE AFTER CHECKING
                            else{
                                insightFail=false;
                               // errorMessage="version mismatch";
                                /*System.out.println("fend.session.SessionController.setQCFlag(): rev: "+revisionOfVersionFromSub+" missing from the list");
                                child.setQcFlagProperty(Boolean.FALSE);
                                targetVol.setQcFlagProperty(Boolean.FALSE);
                                System.out.println("After entering loop");
                                targetVol.printQC();
                                targetSeq.setAlert(Boolean.FALSE);
                                targetSeq.setErrorMessage("");
                                targetSub.setAlert(Boolean.FALSE);
                                targetSub.setErrorMessage("");*/
                            }
                            
                        }
                        
                        
                        /*if(child.getInsightVersionsModel().){                             //Change this to a computed hash.
                        
                        System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Comparing InisghtVersions! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                        System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Setting QC flags to True : on volume : "+targetVol.getLabel()+" : Seq: "+targetSeq.getSequenceNumber()+" : "+targetSub.getSubsurface());
                        child.setQcFlagProperty(Boolean.TRUE);
                        targetVol.setQcFlagProperty(Boolean.TRUE);
                        System.out.println("After entering loop");
                        targetVol.printQC();
                        targetSeq.setAlert(Boolean.TRUE);
                        targetSub.setAlert(Boolean.TRUE);
                        }*/
                        
                               Boolean QCFailure=insightFail || traceFail;
                               
                               System.out.println("fend.session.SessionController.setQCFlag(): trace Flag: "+traceFail);
                               System.out.println("fend.session.SessionController.setQCFlag(): insight Flag: "+insightFail);
                               System.out.println("fend.session.SessionController.setQCFlag(): QCFailure Flag: "+QCFailure);
                               child.setQcFlagProperty(QCFailure);
                               targetVol.setQcFlagProperty(QCFailure);
                               targetSeq.setAlert(QCFailure);
                               targetSub.setAlert(QCFailure);
                               if(insightFail && !traceFail){
                                   errorMessage="version mismatch";
                               }
                               if(traceFail && !insightFail){
                                   errorMessage="tracecount mismatch";
                               }
                               if(insightFail &&  traceFail){
                                   errorMessage="version and tracecount mismatch";
                               }
                               if(!traceFail && !insightFail)
                               {
                                   errorMessage="";
                               }
                               System.out.println("fend.session.SessionController.setQCFlag(): ErrorMessage "+errorMessage);
                               targetSeq.setErrorMessage(errorMessage);
                               targetSub.setErrorMessage(errorMessage);
                        
                        
                       
        }
         
         List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.setQCFlag():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            setQCFlag(child, gchild);
        }
         
         
        
         
         
         
         
         
    }

    public void startWatching() {
        
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            List<VolumeSelectionModel> volList=next.getVolList();
                
                for (Iterator<VolumeSelectionModel> iterator1 = volList.iterator(); iterator1.hasNext();) {
                VolumeSelectionModel vol = iterator1.next();
                vol.startWatching();
                
            }
        }
    }
   
    
   
}
