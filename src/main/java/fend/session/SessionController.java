/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import collector.Collector;
import db.handler.ObpManagerLogDatabaseHandler;
import db.model.Acquisition;
import db.model.Ancestors;
import db.model.Child;
import db.model.Descendants;
import db.model.Headers;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.NodeProperty;
import db.model.NodeType;
import db.model.OrcaView;
import db.model.Parent;
import db.model.PropertyType;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Subsurface;
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
import db.services.NodePropertyService;
import db.services.NodePropertyServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.OrcaViewService;
import db.services.OrcaViewServiceImpl;
import db.services.ParentService;
import db.services.ParentServiceImpl;
import db.services.PropertyTypeService;
import db.services.PropertyTypeServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;

import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import mid.doubt.dependencies.Dep11;
import mid.doubt.dependencies.DepA1;
import mid.doubt.inheritance.Inherit11;
import mid.doubt.inheritance.InheritA1;
import fend.session.node.jobs.types.acquisitionType.AcquisitionJobStepModel;
import fend.session.node.jobs.types.acquisitionType.AcquisitionNode;
import fend.session.node.jobs.types.type1.JobStepType1Node;
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
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type1.JobStepType1NodeController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.nodeproperty.JobModelProperty;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type0.JobStepType0Node;
import fend.session.node.jobs.types.type0.JobStepType0NodeController;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.jobs.types.type2.JobStepType2Node;
import fend.session.node.jobs.types.type4.JobStepType4Model;
import fend.session.node.jobs.types.type4.JobStepType4Node;
import fend.session.node.jobs.types.type4.properties.JobStepType4ModelProperties;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
//import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.summary.SummaryModel;
import fend.summary.SummaryNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import mid.doubt.dependencies.Dep21;
import mid.doubt.dependencies.DepA2;
import mid.doubt.dependencies.DepXX;
import mid.doubt.inheritance.Inherit21;
import mid.doubt.inheritance.InheritA2;
import mid.doubt.inheritance.InheritXX;
import mid.doubt.qc.Q11;
import mid.doubt.qc.Q21;
import mid.doubt.qc.QA1;
import mid.doubt.qc.QA2;
import mid.doubt.qc.QXX;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.controlsfx.control.GridView;

/**
 *
 * @author naila0152
 */
public class SessionController implements Initializable {
    
    ObpManagerLogDatabaseHandler obpManagerLogDatabaseHandler=new ObpManagerLogDatabaseHandler();
    Logger logger=Logger.getLogger(SessionController.class.getName());
    
    private ArrayList<JobStepType0Model> jobStepModelList=new ArrayList<>();
    //private ObservableList<JobStepModel> obsModelList=FXCollections.observableList(jobStepModelList);
    private SessionModel model=new SessionModel();
    private ObservableList<JobStepType0Model> obsModelList=FXCollections.observableList(model.listOfJobs);
   // private List<VolumeSelectionModelType1> dummyList = new ArrayList<>();
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
    private List<JobStepType0Model> jobsThatHaveChanged=new ArrayList<>();
    Map<JobStepType0Model,List<SubSurfaceHeaders>> mapOfChangesSinceLastSummary=new HashMap<>();
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
    private NodeTypeService nodeserv=new NodeTypeServiceImpl();
    private NodePropertyService npropserv=new NodePropertyServiceImpl();
    private PropertyTypeService propServ=new PropertyTypeServiceImpl();
    private SessionsService sessionServ=new SessionsServiceImpl();
     
    private SummaryNode summaryNode=null;
    
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
    private Button addJobStepButton3;

    
    @FXML
    private Button addAcquistionJobNode;

     
     @FXML
    private CheckBox tracker;
    
    
     int i=0;
     @FXML
    private Button overviewButton;
     

     private MultiMap<JobStepType0Model,MultiMap<Integer,JobStepType0Model>> mapOfDepthMaps=new MultiValueMap<>();    //for multiple roots. The map will store the root job and a map of jobs keyed off their depths
    // private MultiValueMap<JobStepType0Model,List<JobStepType0Model>> graphMap=new MultiValueMap<>();                           //for multiple roots. The map will store the root job and its corresponding adjacency list of children. this list will be the graph traversed
    private boolean initSummary=true;

    public SessionController() {
        //LogManager.getLogManager().reset();
        logger.addHandler(obpManagerLogDatabaseHandler);
        logger.setLevel(Level.SEVERE);
    }
             
     
     
     
    @FXML
    void handleAddAcqJobNode(ActionEvent event) {
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        logger.info("jobStepContents below");
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            logger.info(next.getJobStepText());
            
        }
        List<JobModelProperty> job3Props=new ArrayList<>();
        model.addJobToSession(new AcquisitionJobStepModel(model,job3Props));
       // obsModelList.add(model.getListOfJobs().get(model.getListOfJobs().size()-1));
        obsModelList=model.getListOfJobs();
        jsn=new AcquisitionNode((AcquisitionJobStepModel) obsModelList.get(obsModelList.size()-1));
      
        
       rightInteractivePane.getChildren().add((AcquisitionNode)jsn);
    }
 
    @FXML
    void handleAddJobStepType3Button(ActionEvent event) {
            System.out.println("fend.session.SessionController.handleAddJobStepType3Button(): jobStepContents below");
        
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepType3Button(): "+next.getJobStepText());
            logger.info(next.getJobStepText());
            
        }
        //check if a type 4L exists in the nodeType table 
        //if not then create it
        if(nodeserv.getNodeTypeObjForType(4L)==null){
            NodeType n=new NodeType();
            n.setActualnodeid(4L);
            n.setName("Text");
            nodeserv.createNodeType(n);
        }
        
        //check if property type exists for 4. defined in class fend.session.node.jobs.types.type4.properties.JobStepType4ModelProperties
        JobStepType4ModelProperties jstmp=new JobStepType4ModelProperties();
        List<String> j4props=jstmp.getProperties();
        for (Iterator<String> iterator = j4props.iterator(); iterator.hasNext();) {
            String jprop = iterator.next();
            if(propServ.getPropertyTypeObjForName(jprop)==null){
                PropertyType prop=new PropertyType();
                prop.setName(jprop);
                propServ.createPropertyType(prop);
            }
        }
        
        
        //check if the nodepropertydefinitions table entries exist. i.e. check if entries like 
        /// type 4 job has the property "to"
        //type 4 job has the property "from" in the database
        //if not create
        //
        NodeType nodeType=nodeserv.getNodeTypeObjForType(4L);
        if(npropserv.getPropertyTypesFor(nodeType).isEmpty()){
           
            for (Iterator<String> iterator = j4props.iterator(); iterator.hasNext();) {
                 NodeProperty nodeProperty=new NodeProperty();
                nodeProperty.setNodeType(nodeType);
                String j4prop = iterator.next();
                PropertyType prop=propServ.getPropertyTypeObjForName(j4prop);
                nodeProperty.setPropertyType(prop);
                
                npropserv.createNodeProperty(nodeProperty);
            }
        }
        
        
       // NodeType nodetype=nodeserv.getNodeTypeObjForType(4L);
        List<NodeProperty> node4Props=npropserv.getPropertyTypesFor(nodeType );
        
        List<JobModelProperty> job4Props=new ArrayList<>();
        for (Iterator<NodeProperty> iterator = node4Props.iterator(); iterator.hasNext();) {
            NodeProperty next = iterator.next();
            JobModelProperty jp=new JobModelProperty();
            jp.setPropertyName(next.getPropertyType().getName());
                    
            job4Props.add(jp);
        }
        model.addJobToSession(new JobStepType4Model(model,job4Props));
       // obsModelList.add(model.getListOfJobs().get(model.getListOfJobs().size()-1));
        obsModelList=model.getListOfJobs();
        
        jsn=new JobStepType4Node((JobStepType4Model) obsModelList.get(obsModelList.size()-1));
      
        
       rightInteractivePane.getChildren().add((JobStepType4Node)jsn);
    }
    
     
     @FXML
    void overviewButtonClicked(ActionEvent event) {
         System.out.println("fend.session.SessionController.overviewButtonClicked(): Click");
         mapOfDepthMaps.clear();
         if(initSummary){
             firstSummary();
             initSummary=false;
         }else{
             laterSummaries();
         }
                 
         
         /*List<OverviewItem> overviewItems=new ArrayList<>();
         OverviewModel ovModel=new OverviewModel();
         
         List<JobStepType0Model> jobs=obsModelList;
         PendingJobsModel pmodel=new PendingJobsModel();
         pmodel.setPendingjobs(pendingarray);
         PendingJobsNode pnode=new PendingJobsNode(pmodel);
         PendingJobsController pcontr=new PendingJobsController();
         
         for (Iterator<JobStepType0Model> iterator = jobs.iterator(); iterator.hasNext();) {
         JobStepType0Model job = iterator.jprop();
         OverviewItem jobOverview=new OverviewItem();
         jobOverview.setName(job.getJobStepText());
         jobOverview.setpFlag(job.getPendingFlagProperty().get());
         jobOverview.setqFlag(job.getQcFlagProperty().get());
         
         overviewItems.add(jobOverview);
         
         }
         
         ovModel.setOverviewItemList(overviewItems);
         OverviewNode ovNode= new OverviewNode(ovModel);
         
         OverviewController ovContr=ovNode.getOverviewController();
         */
        
         System.out.println("fend.session.SessionController.overviewButtonClicked(): starting to map");
         logger.info("starting to map");
         mapping();
         Set<JobStepType0Model> rootJobSteps=mapOfDepthMaps.keySet();
         System.out.println("fend.session.SessionController.overviewButtonClicked(): size of rootJobSteps: "+rootJobSteps.size());
         logger.info("size of rootJobSteps: "+rootJobSteps.size());
         for (Iterator<JobStepType0Model> iterator = rootJobSteps.iterator(); iterator.hasNext();) {
             JobStepType0Model rootjob = iterator.next();
             //MultiValueMap<Integer,JobStepType0Model> depthnodemap=(MultiValueMap<Integer,JobStepType0Model>) mapOfDepthMaps.get(rootjob);              //get the map associated with this root
             List<MultiMap<Integer,JobStepType0Model>> mapi= (List<MultiMap<Integer,JobStepType0Model>>) mapOfDepthMaps.get(rootjob);
             for (Iterator<MultiMap<Integer, JobStepType0Model>> iterator1 = mapi.iterator(); iterator1.hasNext();) {
                 MultiMap<Integer, JobStepType0Model> depthnodeMap = iterator1.next();
                 SummaryModel sumModel=new SummaryModel();
                sumModel.setDepthNodeMap(depthnodeMap);
                if(summaryNode==null)
                {
                    summaryNode=new SummaryNode(sumModel);
                }else{
                    summaryNode.updateModel(sumModel);
                }
             }
             
                     
         }
         
         
    } 
     
     
     
     @FXML
    void onTrackCheck(ActionEvent event) {
         System.out.println("fend.session.SessionController.onTrackCheck() Checked "+tracker.isSelected());
         if(tracker.isSelected()){
             firstSummary();
         }
    }
    
     @FXML
    void handleAddJobStepType2Button(ActionEvent event) {
        
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        logger.info("jobStepContents below");
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            logger.info(next.getJobStepText());
        }
        List<JobModelProperty> job2Props=new ArrayList<>();
        model.addJobToSession(new JobStepType2Model(model,job2Props));
       // obsModelList.add(model.getListOfJobs().get(model.getListOfJobs().size()-1));
        obsModelList=model.getListOfJobs();
        jsn=new JobStepType2Node((JobStepType2Model) obsModelList.get(obsModelList.size()-1));
      
        
       rightInteractivePane.getChildren().add((JobStepType2Node)jsn);
        
      
    }
     
    @FXML
    void handleAddJobStepType1Button(ActionEvent event) {
        //dummyList.add(new VolumeSelectionModelType1("v1", Boolean.TRUE));
       // dummyList.add(new VolumeSelectionModelType1("v2", Boolean.TRUE));
       // obsModelList.add(new JobStepModel("SRME", dummyList));
       
        System.out.println("fend.session.SessionController.handleAddJobStepButton(): jobStepContents below");
        logger.info("jobStepContents below");
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.handleAddJobStepButton(): "+next.getJobStepText());
            logger.info(next.getJobStepText());
        }
        List<JobModelProperty> job1Props=new ArrayList<>();
        model.addJobToSession(new JobStepType1Model(model,job1Props));
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
            jsn=(JobStepType0Node) rightInteractivePane.lookup("#"+nodeId);
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
            JobStepModel jprop = iterator.jprop();
            List<JobStepModel> children=jprop.getJsChildren();
                for (Iterator<JobStepModel> iterator1 = children.iterator(); iterator1.hasNext();) {
                JobStepModel next1 = iterator1.jprop();
                List<JobStepModel> gchildren=next1.getJsChildren();
                
                
                    System.out.println("job: "+jprop.getJobStepText()+" : has child: "+next1.getJobStepText());
                    
                        for (Iterator<JobStepModel> iterator2 = gchildren.iterator(); iterator2.hasNext();) {
                        JobStepModel next2 = iterator2.jprop();
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
            logger.info(root.getJobStepText()+"--|");
        //    System.out.print(printSpace(root.getJobStepText().length()+2)+"|--");
            for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
                JobStepType0Model next = iterator1.next();
                //System.out.print(jprop.getJobStepText());
               
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
            logger.info(printSpace(ii)+"|--"+child.getJobStepText());
            ii+=child.getJobStepText().length();
            List<JobStepType0Model> children=child.getJsChildren();
            for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                JobStepType0Model next = iterator.next();
                if(!next.getId().equals(child.getId())){
                    //System.out.println(printSpace(ii)+"|--"+jprop.getJobStepText());
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
       System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit() entered with SessionModel Id: "+model.getId()+" : "+model.getName());
       logger.info("entered with SessionModel Id: "+model.getId()+" : "+model.getName());
       for (Iterator<Node> iterator = rightInteractivePane.getChildren().iterator(); iterator.hasNext();) {
            Node next = iterator.next();
            if(next instanceof  Links)
            {
                Links ln=(Links) next;
                
                //obsLinksModelList.add(ln.getLmodel());
                obsLinksModelList=FXCollections.observableArrayList(model.getListOfLinks());
            }
            
            
            
            
        }
       System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): Debug for session: ");
       SessionsService sstemp=new SessionsServiceImpl();
       Sessions cbefore= sstemp.getSessions(model.getId());
       if(cbefore==null){
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): Before deleting... couldn't find an entry for id: "+model.getId());
           logger.info("Before deleting... couldn't find an entry for id: "+model.getId());
       }else{
            System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): Before deleting... Found an entry for id: "+cbefore.getIdSessions()+" : "+cbefore.getNameSessions());
            logger.info("Before deleting... Found an entry for id: "+cbefore.getIdSessions()+" : "+cbefore.getNameSessions());
       }
       List<JobStepType0Model> jobsToBeDeleted=model.getJobsToBeDeleted();
       
       for (Iterator<JobStepType0Model> iterator = jobsToBeDeleted.iterator(); iterator.hasNext();) {
           JobStepType0Model jobTobeDeleted = iterator.next();
           JobStep jsd=jServ.getJobStep(jobTobeDeleted.getId());
           if(jsd!=null){
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: job found with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
               logger.info("DeleteMode: job found with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
           List<SessionDetails> sessionDetailsList=ssdServ.getSessionDetails(jsd);        // all the sessions to which this job belongs to.
           List<JobVolumeDetails> jobvolumeDetailsList=jvdServ.getJobVolumeDetails(jsd);  //list of volumes that belong to this job
         
           
           
           
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: DELETING job with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
           logger.info("DeleteMode: DELETING job with id: "+jsd.getIdJobStep()+" : name: "+jsd.getNameJobStep());
           jServ.deleteJobStep(jsd.getIdJobStep());
           
           for (Iterator<JobVolumeDetails> iterator1 = jobvolumeDetailsList.iterator(); iterator1.hasNext();) {
               JobVolumeDetails jvd = iterator1.next();
               Volume v=jvd.getVolume();
               List<Headers> hdrList=hdrServ.getHeadersFor(v);
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode: found volume with id: "+v.getIdVolume()+ " :name: "+v.getNameVolume());
               logger.info("DeleteMode: found volume with id: "+v.getIdVolume()+ " :name: "+v.getNameVolume());
                for (Iterator<Headers> iterator2 = hdrList.iterator(); iterator2.hasNext();) {
                   Headers hdr = iterator2.next();
                    System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING header wtih id: "+hdr.getIdHeaders()+" : subsurface:  "+hdr.getSubsurface() );
                    logger.info("DeleteMode:  DELETING header wtih id: "+hdr.getIdHeaders()+" : subsurface:  "+hdr.getSubsurface());
                   hdrServ.deleteHeaders(hdr.getIdHeaders());
               }
               
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING Jobvolumedetails with id: "+jvd.getIdJobVolumeDetails()+" : job:  "+jvd.getJobStep().getNameJobStep()+" :volume: "+jvd.getVolume().getNameVolume() );
               logger.info("DELETING Jobvolumedetails with id: "+jvd.getIdJobVolumeDetails()+" : job:  "+jvd.getJobStep().getNameJobStep()+" :volume: "+jvd.getVolume().getNameVolume());
               //jvdServ.deleteJobVolumeDetails(jvd.getIdJobVolumeDetails());
               System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): DeleteMode:  DELETING volume with id: "+v.getIdVolume()+" : name:  "+v.getNameVolume() ); 
               logger.info("DELETING volume with id: "+v.getIdVolume()+" : name:  "+v.getNameVolume());
               volServ.deleteVolume(v.getIdVolume());
           }
           
            
          
            
           
         
           }
           
           
           
       }
       
       
       
       
        if(obsModelList.size()!=model.getListOfJobs().size()){
            System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): Found obslist with: "+obsModelList.size()+" and model with: "+model.getListOfJobs().size());
            logger.info("Found obslist with: "+obsModelList.size()+" and model with: "+model.getListOfJobs().size());
        }
            obsModelList=model.getListOfJobs();
            
       for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
           JobStepType0Model next = iterator.next();
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit():  jobStepModeList : "+next.getJobStepText());
           logger.info("jobStepModeList : "+next.getJobStepText());
           
       }
       
           //model.setListOfJobs(obsModelList);
            
            for (Iterator<JobStepType0Model> iterator = jobStepModelList.iterator(); iterator.hasNext();) {
           JobStepType0Model next = iterator.next();
           
              //  System.out.println("SessContr: Checking on the Kids");
                ArrayList<JobStepType0Model> children=(ArrayList<JobStepType0Model>) next.getJsChildren();
                
                for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
                    JobStepType0Model child1 = iterator1.next();
                //    System.out.println("SessContr Parent: "+jprop.getJobStepText()+"   Child: "+child1.getJobStepText());
                    
                }
           
       }
            
            //model.setListOfLinks(linksModelList);
            
            System.out.println("SC: model has ID: "+model.getId());
           Sessions cafter= sstemp.getSessions(model.getId());
       if(cafter==null){
           System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): After deleting... couldn't find an entry for id: "+model.getId());
           logger.info("After deleting... couldn't find an entry for id: "+model.getId());
       }else{
            System.out.println("fend.session.SessionController.setAllLinksAndJobsForCommit(): After deleting... Found an entry for id: "+cafter.getIdSessions()+" : "+cafter.getNameSessions());
            logger.info("After deleting... Found an entry for id: "+cafter.getIdSessions()+" : "+cafter.getNameSessions());
       } 
            
            
            
   }
   
   
   public void setAllModelsForFrontEndDisplay(){
       
       
      
       
       
       for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
           
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): display contents");
           
           JobStepType0Model next = iterator.next();
           /*List<VolumeSelectionModel> testvm=jprop.getVolList();*/
           
           InsightVersionsModel insVerModel=next.getInsightVersionsModel();
           
           /*List<String>vv = insVerModel.getCheckedVersions();
           
           for (Iterator<String> iterator1 = vv.iterator(); iterator1.hasNext();) {
           String next1 = iterator1.jprop();
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay() VERSIONS FOUND: "+next1);
           }*/
           
           
           /*for (Iterator<VolumeSelectionModel> iterator1 = testvm.iterator(); iterator1.hasNext();) {
           VolumeSelectionModelType1 next1 = iterator1.jprop();
           System.out.println("fend.session.SessionController.setAllModelsForFrontEndDisplay(): "+next1.getLabel());
           }*/
           Long type=next.getType();
           if(type.equals(1L)){
               jsn=new JobStepType1Node((JobStepType1Model) next);
           }
           if(type.equals(2L)){
               jsn=new JobStepType2Node((JobStepType2Model) next);
           }
           if(type.equals(3L)){
               jsn=new AcquisitionNode((AcquisitionJobStepModel)next);
           }
           if(type.equals(4L)){
               jsn=new JobStepType4Node((JobStepType4Model) next);
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
                    }if(jsn instanceof AcquisitionNode){
                        roots.add((AcquisitionNode) jsn);
                    }
                    if(jsn instanceof JobStepType4Node){
                        roots.add((JobStepType4Node) jsn);
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
            if(jsn instanceof AcquisitionNode){
                        rightInteractivePane.getChildren().add((AcquisitionNode)jsn);
                        centerX=((AcquisitionNode)jsn).boundsInLocalProperty().getValue().getMinX();
                        centerY=((AcquisitionNode)jsn).boundsInLocalProperty().getValue().getMinY()+((AcquisitionNode)jsn).boundsInLocalProperty().get().getHeight()/2;
                        mstart.setJob(next);
                        mstart.setCenterX(centerX);
                        mstart.setCenterY(centerY);
            
           
            
                        jsnAnchorMap.put((AcquisitionNode)jsn, mstart);
                    }
            if(jsn instanceof JobStepType4Node){
                        rightInteractivePane.getChildren().add((JobStepType4Node)jsn);
                        centerX=((JobStepType4Node)jsn).boundsInLocalProperty().getValue().getMinX();
                        centerY=((JobStepType4Node)jsn).boundsInLocalProperty().getValue().getMinY()+((JobStepType4Node)jsn).boundsInLocalProperty().get().getHeight()/2;
                        mstart.setJob(next);
                        mstart.setCenterX(centerX);
                        mstart.setCenterY(centerY);
            
           
            
                        jsnAnchorMap.put((JobStepType4Node)jsn, mstart);
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
        /*Double centerX=jprop.boundsInLocalProperty().getValue().getMinX()+310;
        Double centerY=jprop.boundsInLocalProperty().getValue().getMinY()+72;*/
          Double centerX=((AnchorPane)next).boundsInLocalProperty().getValue().getMaxX();
            Double centerY=((AnchorPane)next).boundsInLocalProperty().getValue().getMaxY()/2;
         // Double centerX=jprop.layoutXProperty().doubleValue();
         // Double centerY=jprop.layoutYProperty().doubleValue();//next.getHeight();
            
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
              //  System.out.println("fend.session.SessionController.drawCurve(): "+jsmod.getJobStepText()+ " :is a leaf: "+next1.getJobStepText());
                return;
            }
                for (Map.Entry<JobStepType0Node, AnchorModel> entry : jsnAnchorMap.entrySet()) {
                JobStepType0Node key = entry.getKey();
                AnchorModel mEnd = entry.getValue();
               // AnchorModel mEnd= new AnchorModel();
                Long keyId=Long.parseLong(((AnchorPane)key).getId());         //since jobstepnodes id is a string
                   // System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " id of node: "+keyId);
                
                if(next1.getId().equals(keyId)){
                  //  System.out.println("fend.session.SessionController.drawCurve() id of model: "+next1.getId() + " EQUALS id of node: "+keyId+ " : starting to draw cubic curves here: ");
                   // System.out.println("fend.session.SessionController.drawCurve()    found   : "+next1.getJobStepText()+" === "+key.getJsnc().getModel().getJobStepText());
                  /// double sx=mEnd.getCenterX().doubleValue();
                 //  double sy=mEnd.getCenterY().doubleValue();
                 //   System.out.println("fend.session.SessionController.drawCurve() MAYlayoutY: "+((AnchorPane)key).getParent().boundsInLocalProperty().getValue().getMaxY());
                 //   System.out.println("fend.session.SessionController.drawCurve() "+((AnchorPane)key).getProperties().toString());
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
                    
                   
                    // DoubleProperty d= new SimpleDoubleProperty(jprop.layoutXProperty().get()+310);
                   // curve.startXProperty().bindBidirectional(d);
                    
                    //
                    //curve.startYProperty().bindBidirectional(new SimpleDoubleProperty(jprop.layoutYProperty().get()+72));
                    //Bindings.add(jprop.layoutXProperty(), jprop.boundsInLocalProperty().get().getMinX()+310);
                   // Bindings.add(jprop.layoutYProperty(),jprop.boundsInLocalProperty().get().getMinY()+72);
                   
                  //  curve.startXProperty().bindBidirectional(jprop.layoutXProperty());
                    //curve.startYProperty().bindBidirectional(jprop.layoutYProperty());
                    
                 //   System.out.println("fend.session.SessionController.drawCurve():  for: "+jprop.getJsnc().getModel().getJobStepText()+" : child: "+key.getJsnc().getModel().getJobStepText());
                    if(!next.getJsnc().getModel().getType().equals(3L)){
                        curve.startXProperty().bind(Bindings.add(((AnchorPane)next).layoutXProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinX()+515));         //next is the parent node
                        curve.startYProperty().bind(Bindings.add(((AnchorPane)next).layoutYProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinY()+74));
                    }else{
                        curve.startXProperty().bind(Bindings.add(((AnchorPane)next).layoutXProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinX()+172));         //next is the parent node
                        curve.startYProperty().bind(Bindings.add(((AnchorPane)next).layoutYProperty(),((AnchorPane)next).boundsInLocalProperty().get().getMinY()+61));
                    }
                            
                    
                    /*curve.startXProperty().bind(Bindings.add(jprop.layoutXProperty(),jprop.boundsInLocalProperty().get().getMaxX()));         //next is the parent node
                    curve.startYProperty().bind(Bindings.add(jprop.layoutYProperty(),jprop.boundsInLocalProperty().get().getMaxY()/2));*/
                    curve.endXProperty().bind(Bindings.add(((AnchorPane)key).layoutXProperty(),((AnchorPane)key).boundsInLocalProperty().get().getMinX()));           //key in the child node
                    curve.endYProperty().bind(Bindings.add(((AnchorPane)key).layoutYProperty(),((AnchorPane)key).boundsInLocalProperty().get().getMinY()+74));       // this is HARDCODED!!! find a way to get the height of the node!
                   
                    /*curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getMinY()));
                    System.out.println("Next MaxX(): "+jprop.boundsInLocalProperty().get().getMaxX());
                    System.out.println("Next MinX(): "+jprop.boundsInLocalProperty().get().getMinX());
                    System.out.println("Next MaxY(): "+jprop.boundsInLocalProperty().get().getMaxY());
                    System.out.println("Next MinY(): "+jprop.boundsInLocalProperty().get().getMinY());
                    System.out.println("Next PHeight(): "+jprop.boundsInLocalProperty().get().getHeight());
                    System.out.println("Next PWidth(): "+jprop.boundsInParentProperty().get().getWidth());
                    System.out.println("Next PMaxX(): "+jprop.boundsInParentProperty().get().getMaxX());
                    System.out.println("Next PMinX(): "+jprop.boundsInParentProperty().get().getMinX());
                    System.out.println("Next PMaxY(): "+jprop.boundsInParentProperty().get().getMaxY());
                    System.out.println("Next PMinY(): "+jprop.boundsInParentProperty().get().getMinY());
                    
                    System.out.println("Key MaxX(): "+key.boundsInLocalProperty().get().getMaxX());
                    System.out.println("Key MinX(): "+key.boundsInLocalProperty().get().getMinX());
                    System.out.println("Key MaxY(): "+key.boundsInLocalProperty().get().getMaxY());
                    System.out.println("Key MinY(): "+key.boundsInLocalProperty().get().getMinY());
                    System.out.println("Key Height(): "+key.boundsInLocalProperty().get().getHeight());
                    System.out.println("Key Width(): "+key.boundsInLocalProperty().get().getWidth());*/
                    
                    // curve.endYProperty().bind(Bindings.add(key.layoutYProperty(),key.boundsInLocalProperty().get().getHeight()/2));
                  //  System.out.println("fend.session.SessionController.drawCurve(): for parent "+jprop.getJsnc().getModel().getJobStepText()+"   :Setting startX : :jprop.boundsInLocalProperty().get().getMaxX():  "+jprop.boundsInLocalProperty().get().getMaxX());
                  //  System.out.println("fend.session.SessionController.drawCurve(): for child  "+key.getJsnc().getModel().getJobStepText() +"   :Setting  endX  : :jprop.boundsInLocalProperty().get().getMaxX():  "+key.boundsInLocalProperty().get().getMaxX());
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
                     logger.info(job.getJobStepText()+" is a root..adding to list of roots");
                     /*System.out.println("fend.session.SessionController.setRoots() :  id matched for model and the single content in the list of Parents");*/
                    modelRoots.add(job);
                    MultiValueMap<Integer,JobStepType0Model> depthZeroRoot=new MultiValueMap<>();
                    depthZeroRoot.put(0,job);
                    mapOfDepthMaps.put(job,depthZeroRoot);
                }
            }
            
            
            
        }
        
        
         for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ObsModelList: "+next.getJobStepText());
            logger.info("jobs in ObsModelList: "+next.getJobStepText());
            List<JobStepType0Model> chldn=next.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                System.out.println("fend.session.SessionController.setRoots(): ObsModeList job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                logger.info("ObsModeList job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                List<JobStepType0Model> gchild=next1.getJsChildren();
                    for (Iterator<JobStepType0Model> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ObsModeList child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                    logger.info("ObsModeList child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
                
                
            }
            
            
        }
         
         
         for (Iterator<JobStepType0Model> iterator = modelRoots.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println("fend.session.SessionController.setRoots(): jobs in ModelRoots: "+next.getJobStepText());
            logger.info("jobs in ModelRoots: "+next.getJobStepText());
            List<JobStepType0Model> chldn=next.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = chldn.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                
                System.out.println("fend.session.SessionController.setRoots(): ModelRoots job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                logger.info("ModelRoots job: "+next.getJobStepText()+" :has child: "+next1.getJobStepText());
                List<JobStepType0Model> gchild=next1.getJsChildren();
                    for (Iterator<JobStepType0Model> iterator2 = gchild.iterator(); iterator2.hasNext();) {
                    JobStepType0Model next2 = iterator2.next();
                    System.out.println("fend.session.SessionController.setRoots(): ModelRoots child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                    logger.info("ModelRoots child: "+next1.getJobStepText()+" :has child: "+next2.getJobStepText());
                }
            }
            
        }
        
        
    }
    
    private Set<SubSurfaceHeaders> calculateSubsInJob(JobStepType0Model job){
        
        if(job instanceof JobStepType1Model){                   //for 2D case
            List<VolumeSelectionModelType1> volList=job.getVolList();
        Set<SubSurfaceHeaders> subsInJob=new HashSet<>();
        
        for (Iterator<VolumeSelectionModelType1> iterator = volList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 vol = iterator.next();
                
                if(!vol.getHeaderButtonStatus()){
                Set<SubSurfaceHeaders> subsInVol=vol.getSubsurfaces();
                subsInJob.addAll(subsInVol);
                }
            
            
            
        }
        job.setSubsurfacesInJob(subsInJob);
        /*for (Iterator<SubSurface> iterator = subsInJob.iterator(); iterator.hasNext();) {
        SubSurfaceHeaders subinJob = iterator.jprop();
        System.out.println("fend.session.SessionController.calculateSubsInJob(): "+job.getJobStepText()+"  :contains: "+subinJob.getSubsurface());
        }*/
        
        return subsInJob;
        }
        else{
            logger.warning("calculateSubsinJob for job type. "+job.getType()+" not defined");
            throw new UnsupportedOperationException("calculateSubsinJob for job type. "+job.getType()+" not defined");
        }
        
    }
    
    
    private void laterSummaries(){
        fillMapOfJobsAndSubsurfacesToBeSummarized();                   //update the map that contains jobs and subsurfaces that need to be summarized
        System.out.println("fend.session.SessionController.laterSummaries(): size of MapOFChanges: "+mapOfChangesSinceLastSummary.size());
        for (Map.Entry<JobStepType0Model, List<SubSurfaceHeaders>> entry : mapOfChangesSinceLastSummary.entrySet()) {
            JobStepType0Model job = entry.getKey();
            List<SubSurfaceHeaders> subsToSummarize = entry.getValue();
            
            System.out.println("fend.session.SessionController.laterSummaries(): Listing subsurfaces that need to be summarized for job: "+job.getJobStepText()+" ");
            for (Iterator<SubSurfaceHeaders> iterator = subsToSummarize.iterator(); iterator.hasNext();) {
                SubSurfaceHeaders next = iterator.next();
                System.err.println("job: "+job.getJobStepText()+" : "+next.getSubsurface());
            }
            /*
            Check dependency,qc and inheritance with parent.
            */
            List<JobStepType0Model> parents=job.getJsParents();
            for (Iterator<JobStepType0Model> iterator = parents.iterator(); iterator.hasNext();) {
                JobStepType0Model parent = iterator.next();
                lsDependency(parent,job,subsToSummarize,false);
                lsQc(parent,job,subsToSummarize,false);
                lsInherit(parent,job,subsToSummarize,false);
                
            }
            
            
            /*
                Downwards walk for descendants from current node.
            */
            List<JobStepType0Model> children=job.getJsChildren();
            for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                JobStepType0Model child = iterator.next();
                lsDependency(job, child, subsToSummarize,true);
                lsQc(job, child, subsToSummarize, true);
                lsInherit(job, child, subsToSummarize, true);
            }
            
            
        }
    
    }
    
    
     private void firstSummary(){
         System.out.println("fend.session.SessionController.tracking():  STARTED");
         logger.info("started");
         setRoots();
       
         List<OrcaView> acquiredSubs=orcaServ.getOrcaView();
         List<String> acqString=new ArrayList<>();                      // hold the names of the acquired subsurfaces
         
         for (Iterator<OrcaView> iterator = acquiredSubs.iterator(); iterator.hasNext();) {
         OrcaView acq = iterator.next();
         acqString.add(acq.getSubsurfaceLineNames());
         System.out.println("fend.session.SessionController.tracking(): in AcqString: added: "+acqString.get(acqString.size()-1));
         logger.info("in AcqString: added: "+acqString.get(acqString.size()-1));
         
         }
         
        
         
         
         
         //List<String> jobSubString=new ArrayList<>();            // holds the names of the subsurfaces in the job
         
         for(JobStepType0Model root : modelRoots){
        
        
             
                List<JobStepType0Model> children = root.getJsChildren();
                for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): DependencyChecks: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    logger.info("DependencyChecks: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepType0Model> gChild=child.getJsChildren();
                        for (Iterator<JobStepType0Model> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepType0Model next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                            logger.info("child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                    
                    dependencyChecks(root, child);
                    
                    
             }
                
                for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): QcChecks: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    logger.info("QcChecks: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepType0Model> gChild=child.getJsChildren();
                        for (Iterator<JobStepType0Model> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepType0Model next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                            logger.info("child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                        pendingarray=new ArrayList<>();
               //  setPendingJobsFlag(root,child);
               //     setQCFlag(root, child);
                    qcChecks(root, child);
                    
                    
             }
                
                
                for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): Inheritance: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    logger.info("Inheritance: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepType0Model> gChild=child.getJsChildren();
                        for (Iterator<JobStepType0Model> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepType0Model next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                            logger.info("child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                        pendingarray=new ArrayList<>();
               //  setPendingJobsFlag(root,child);
               //     setQCFlag(root, child);
                    inheritanceOfDoubt(root, child);
                    
                    
             }
                
                
                for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                 JobStepType0Model child = iterator.next();
                    System.out.println("fend.session.SessionController.tracking(): InsightCheck: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    logger.info("InsightCheck: to be called for Parent: "+root.getJobStepText()+" : child: "+child.getJobStepText());
                    List<JobStepType0Model> gChild=child.getJsChildren();
                        for (Iterator<JobStepType0Model> iterator1 = gChild.iterator(); iterator1.hasNext();) {
                        JobStepType0Model next = iterator1.next();
                            System.out.println("fend.session.SessionController.tracking(): child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                            logger.info("child: "+child.getJobStepText()+" : has child: "+next.getJobStepText());
                        
                    }
                        pendingarray=new ArrayList<>();
               //  setPendingJobsFlag(root,child);
               //     setQCFlag(root, child);
                    insightCheck(root,child);
                    
                    
             }
                
             
             
         }
         
         
         
     }
     
     private void mapping(){
         
         Set<JobStepType0Model> keys=mapOfDepthMaps.keySet();
         System.out.println("fend.session.SessionController.mapping(): Inside the function: keys.size()==roots== : "+keys.size());
         for (Iterator<JobStepType0Model> iterator = keys.iterator(); iterator.hasNext();) {
             JobStepType0Model root = iterator.next();
              List<MultiMap<Integer,JobStepType0Model>> mapi= (List<MultiMap<Integer,JobStepType0Model>>) mapOfDepthMaps.get(root);
              System.out.println("fend.session.SessionController.mapping(): size of list: "+mapi.size());
              
              
              for (Iterator<MultiMap<Integer, JobStepType0Model>> iterator1 = mapi.iterator(); iterator1.hasNext();) {
                 MultiMap<Integer, JobStepType0Model> depthnodemap = iterator1.next();
                 
                 List<JobStepType0Model> children=root.getJsChildren();
                  for (Iterator<JobStepType0Model> iterator2 = children.iterator(); iterator2.hasNext();) {
                      JobStepType0Model child = iterator2.next();
                       System.out.println("fend.session.SessionController.mapping(): adding child: "+child.getJobStepText());
                       logger.info("adding child: "+child.getJobStepText());
                      //depthnodemap.put(1, child);
                      fillmap(root,child,0,depthnodemap);
                  }
                 
                 
             }
            // mapi.put(0,root);
             
             
            /* List<JobStepType0Model> children = root.getJsChildren();
            for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
            JobStepType0Model child = iterator1.jprop();
            System.out.println("fend.session.SessionController.mapping(): adding child: "+child.getJobStepText());
            mapi.put(1,child);
            
            fillmap(root,child,1,mapi);
            }*/
             
         }
         
         /*for (Iterator<JobStepType0Model> iterator = modelRoots.iterator(); iterator.hasNext();) {
         JobStepType0Model root = iterator.jprop();
         System.out.println("fend.session.SessionController.mapping(): Inside the function: inside for loop with root: " +root.getJobStepText());
         /* }
         for(JobStepType0Model root : modelRoots){*/
        /* 
         MultiValueMap<Integer,JobStepType0Model> mapi=(MultiValueMap<Integer,JobStepType0Model>) mapOfDepthMaps.get(root);
         System.out.println("fend.session.SessionController.mapping(): adding root job: "+root.getJobStepText());
         mapi.put(0,root);
         
         
         List<JobStepType0Model> children = root.getJsChildren();
         for (Iterator<JobStepType0Model> iterator1 = children.iterator(); iterator1.hasNext();) {
             JobStepType0Model child = iterator1.jprop();
             System.out.println("fend.session.SessionController.mapping(): adding child: "+child.getJobStepText());
             mapi.put(1,child);
             
             fillmap(root,child,1,mapi);
         }
         
     }*/
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
         Set<SubSurfaceHeaders> pSubs=parent.getSubsurfacesInJob();
         Set<SubSurfaceHeaders> cSubs=calculateSubsInJob(child);
      
         
         List<String> pSubsStrings=new ArrayList<>();
         List<String> cSubsStrings=new ArrayList<>();
         
         for (Iterator<SubSurfaceHeaders> iterator = pSubs.iterator(); iterator.hasNext();) {
         SubSurfaceHeaders subInParent = iterator.next();
         pSubsStrings.add(subInParent.getSubsurface());
        // System.out.println("fend.session.SessionController.setPendingJobsFlag():  pSubsStrings found : "+pSubsStrings.get(pSubsStrings.size()-1));
         
         }
         
         for (Iterator<SubSurfaceHeaders> iterator = cSubs.iterator(); iterator.hasNext();) {
         SubSurfaceHeaders subInChild = iterator.next();
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
       //  child.setPendingFlagProperty(Boolean.TRUE);
         
         System.out.println("fend.session.SessionController.setPendingJobsFlag():  child :"+child.getJobStepText()+" has pending subs "+remaining);
         pendingarray.addAll(remaining);
         System.out.println("fend.session.SessionController.setPendingJobsFlag(): Pending flag set in model");
         }else     //child has no pending subs
         {
       //  child.setPendingFlagProperty(Boolean.FALSE);
          System.out.println("fend.session.SessionController.setPendingJobsFlag():  child :"+child.getJobStepText()+" has NO pending subs "+remaining);
         }
         }
         
         List<JobStepType0Model> grandChildren=child.getJsChildren();
         for(JobStepType0Model gchild:grandChildren){
             System.out.println("fend.session.SessionController.setPendingJobsFlag(): job: "+child.getJobStepText()+" :has child: "+gchild.getJobStepText());
         }
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
             JobStepType0Model grandchild = iterator.next();
            // setPendingJobsFlag(child, grandchild);
         }
         
         
         
         
     }

    private void setQCFlag(JobStepType0Model parent,JobStepType0Model child){
       if(parent.getType().equals(3L) && child.getType().equals(1L)){
           
           Boolean traceFail=false;            //defualt QC status is false
        Boolean insightFail=false;
           //insight version check
           calculateSubsInJob(child);
            Set<SubSurfaceHeaders> csubq=child.getSubsurfacesInJob();
            
            List<VolumeSelectionModelType1> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
            
             child.setDependency(Boolean.FALSE);
            
            List<String> versionsSelectedInChildQ=child.getInsightVersionsModel().getCheckedVersions();
                        MultiValueMap<String,String> baseRevisionFromJobMapQ=new MultiValueMap<>();
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders refSubQ = iterator.next();
                        /*String baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                        String revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];*/
                        String baseVersionFromSubQ=new String();
                        String revisionOfVersionFromSubQ=new String();
                        if(refSubQ.getInsightVersion()!=null){
                            baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                            revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];
                        }
                           
                       
            VolumeSelectionModelType1 targetVolQ=new VolumeSelectionModelType1(1L,child);
            SequenceHeaders targetSeqQ=new SequenceHeaders();
            SubSurfaceHeaders targetSubQ=refSubQ;
                        
                           for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
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
                            List<SequenceHeaders> seqList=hmod.getSequenceListInHeaders();
                            
                for (SequenceHeaders seq : seqList) {
                    if(targetSubQ.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeqQ=seq;
                        //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
                    }
                    
                  
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
                             
                            
                        }
                        else 
                        {
                            List<String> revList=(List<String>) baseRevisionFromJobMapQ.get(baseVersionFromSubQ);
                            System.out.println("fend.session.SessionController.setQCFlag(): found base: "+baseVersionFromSubQ);
                            
                            if(!revList.contains(revisionOfVersionFromSubQ)){
                                insightFail=true;
                            
                            }
                            else{
                                insightFail=false;
                             
                            }
                        }
                        
                            Boolean QCFailure=traceFail || insightFail;
                            System.out.println("fend.session.SessionController.setQCFlag(): QCFailure Flag: "+QCFailure);
                            child.setDependency(QCFailure);
                            targetVolQ.setDependency(QCFailure);
                          //  targetSeqQ.setQcAlert(QCFailure);
                          targetSeqQ.setDependency(QCFailure);
                            targetSeqQ.setInsightFlag(insightFail);
                            targetSubQ.setDependency(QCFailure);
                            //targetSubQ.setQcAlert(QCFailure);
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
           //end of insight version check
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.setQCFlag():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            setQCFlag(child, gchild);
        }
           
       }
        
        
        //2d-2d edge
        if(parent.getType().equals(1L) && child.getType().equals(1L)){
            
        
        //If Child has been traversed then return.   Create a "traversed" flag in JobStepModel and set in each time the node is returning "upwards". i.e it and all of its descendants have been traversed, set its "traversed" flag to True
        Boolean traceFail=false;            //defualt QC status is false
        Boolean insightFail=false;
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
            
            //check for Insight Version mismatch
            Set<SubSurfaceHeaders> csubq=child.getSubsurfacesInJob();
            
            List<VolumeSelectionModelType1> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
            
             child.setDependency(Boolean.FALSE);
            
            List<String> versionsSelectedInChildQ=child.getInsightVersionsModel().getCheckedVersions();
                        MultiValueMap<String,String> baseRevisionFromJobMapQ=new MultiValueMap<>();
                        
                      for (Iterator<SubSurfaceHeaders> iterator = csubq.iterator(); iterator.hasNext();) {
                        SubSurfaceHeaders refSubQ = iterator.next();
                        /*String baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                        String revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];*/
                        String baseVersionFromSubQ=new String();
                        String revisionOfVersionFromSubQ=new String();
                        if(refSubQ.getInsightVersion()!=null){
                            baseVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[0];
                            revisionOfVersionFromSubQ=refSubQ.getInsightVersion().split("\\(")[1].split("\\)")[0];
                        }
                           
                       
            VolumeSelectionModelType1 targetVolQ=new VolumeSelectionModelType1(1L,child);
            SequenceHeaders targetSeqQ=new SequenceHeaders();
            SubSurfaceHeaders targetSubQ=refSubQ;
                        
                           for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
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
                            List<SequenceHeaders> seqList=hmod.getSequenceListInHeaders();
                            
                for (SequenceHeaders seq : seqList) {
                    if(targetSubQ.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeqQ=seq;
                        //  System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
                    }
                    
                  
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
                             
                            
                        }
                        else 
                        {
                            List<String> revList=(List<String>) baseRevisionFromJobMapQ.get(baseVersionFromSubQ);
                            System.out.println("fend.session.SessionController.setQCFlag(): found base: "+baseVersionFromSubQ);
                            
                            if(!revList.contains(revisionOfVersionFromSubQ)){
                                insightFail=true;
                            
                            }
                            else{
                                insightFail=false;
                             
                            }
                        }
                        
                            Boolean QCFailure=traceFail || insightFail;
                            System.out.println("fend.session.SessionController.setQCFlag(): QCFailure Flag: "+QCFailure);
                            child.setDependency(QCFailure);
                            targetVolQ.setDependency(QCFailure);
                           // targetSeqQ.setQcAlert(QCFailure);
                           targetSeqQ.setDependency(QCFailure);
                            targetSeqQ.setInsightFlag(insightFail);
                            //targetSubQ.setQcAlert(QCFailure);
                            targetSubQ.setDependency(QCFailure);
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
          child.setDependency(Boolean.FALSE);
         
            calculateSubsInJob(child);
            calculateSubsInJob(parent);
         //Set<SubSurface> csubs= calculateSubsInJob(child);
         //Set<SubSurface> psubs=calculateSubsInJob(parent);
         Set<SubSurfaceHeaders> psubs=parent.getSubsurfacesInJob();
         Set<SubSurfaceHeaders> csubs=child.getSubsurfacesInJob();
            System.out.println("fend.session.SessionController.setQCFlag(): size of child and parent subs: "+csubs.size()+" : "+psubs.size());
            
         List<VolumeSelectionModelType1> cVolList=child.getVolList();
         for (Iterator<VolumeSelectionModelType1> iterator = cVolList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            next.setDependency(Boolean.FALSE);                        //first set all the volumes to false. then check each one below
            
        }
        
         
         
         for (Iterator<SubSurfaceHeaders> iterator = csubs.iterator(); iterator.hasNext();) {
            SubSurfaceHeaders c = iterator.next();
            VolumeSelectionModelType1 targetVol=new VolumeSelectionModelType1(1L,child);
            SequenceHeaders targetSeq=new SequenceHeaders();
            SubSurfaceHeaders targetSub=c;
            SubSurfaceHeaders refSub=new SubSurfaceHeaders();
            
                        for (Iterator<VolumeSelectionModelType1> iterator1 = cVolList.iterator(); iterator1.hasNext();) {
                            VolumeSelectionModelType1 vc = iterator1.next();
                            Set<SubSurfaceHeaders> vcSub=vc.getSubsurfaces();
                           System.out.println("fend.session.SessionController.setQCFlag(): Checking if Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                            if(vcSub.contains(targetSub)){
                              System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Volume: "+vc.getLabel()+" :contains: "+targetSub.getSubsurface());
                                targetVol=vc;
                                targetVol.setVolumeType(vc.getVolumeType());
                                break;
                            }
                 
                        }
                        
                        if(targetVol!=null){
                            HeadersModel hmod=targetVol.getHeadersModel();
                            List<SequenceHeaders> seqList=hmod.getSequenceListInHeaders();
                            
                for (SequenceHeaders seq : seqList) {
                    if(targetSub.getSequenceNumber().equals(seq.getSequenceNumber())){
                        targetSeq=seq;
                          System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!Job: "+child.getJobStepText()+" :Seq: "+seq.getSequenceNumber()+" :contains: "+targetSub.getSubsurface());
                        break;
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
                      
                        
                        for (Iterator<SubSurfaceHeaders> iterator1 = psubs.iterator(); iterator1.hasNext();) {
                          SubSurfaceHeaders p = iterator1.next();
                          if(csubs.contains(p))
                          {
                              if(p.equals(c)){
                              System.out.println("fend.session.SessionController.setQCFlag(): SUCCESS!!found : "+p.getSubsurface()+" : in Parent job : "+parent.getJobStepText()+" : list");
                              refSub=p;
                              
                              
                              
                              break;
                              }
                             
                          }
                        
                        }
                        
                         String errorMessage=new String();
                        if(!refSub.getTraceCount().equals(targetSub.getTraceCount())){                                //Change this to a computed hash.
                                traceFail=true;
                               // errorMessage="tracecount mismatch";
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Comparing traceCounts! : "+refSub.getTraceCount()+" "+ refSub.getTraceCount().equals(targetSub.getTraceCount())+" "+targetSub.getTraceCount());
                                System.out.println("fend.session.SessionController.setQCFlag(): TRUE:: Setting QC flags to True : on volume : "+targetVol.getLabel()+" : Seq: "+targetSeq.getSequenceNumber()+" : "+targetSub.getSubsurface());
                               
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
                       
                        for (String s:versionsSelectedInChild){
                           
                            String[] parts=s.split("-");
                            String base=parts[0];
                            String rev=parts[1];
                            
                             
                            baseRevisionFromJobMap.put(base, rev);
                            
                            System.out.println("fend.session.SessionController.setQCFlag(): Sub: base"+baseVersionFromSub+" rev:"+revisionOfVersionFromSub);
                           
                            
                        }
                        
                       
                        
                        Set<String> baseKeys=baseRevisionFromJobMap.keySet();
                        
                        if(!baseKeys.contains(baseVersionFromSub)){
                            //Turn the sub and job QC flag =true;
                                insightFail=true;
                             
                            
                        }
                        else 
                        {
                            List<String> revList=(List<String>) baseRevisionFromJobMap.get(baseVersionFromSub);
                            System.out.println("fend.session.SessionController.setQCFlag(): found base: "+baseVersionFromSub);
                            
                            if(!revList.contains(revisionOfVersionFromSub)){
                                insightFail=true;
                               
                            }
                            // TO BE ENABLE AFTER CHECKING
                            else{
                                insightFail=false;
                               
                            }
                            
                        }
                        
                        
                       
                        
                               Boolean QCFailure=insightFail || traceFail;
                               
                               System.out.println("fend.session.SessionController.setQCFlag(): trace Flag: "+traceFail);
                               System.out.println("fend.session.SessionController.setQCFlag(): insight Flag: "+insightFail);
                               System.out.println("fend.session.SessionController.setQCFlag(): QCFailure Flag: "+QCFailure);
                               child.setDependency(QCFailure);
                               targetVol.setDependency(QCFailure);
                               /* targetSeq.setQcAlert(QCFailure);
                               targetSub.setQcAlert(QCFailure);*/
                               targetSeq.setDependency(QCFailure);
                               targetSub.setDependency(QCFailure);
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
         
    }
    
    
    
    
    
    public void dependencyChecks(JobStepType0Model parent,JobStepType0Model child){
        
           if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      logger.info("ROOT/LEAF found: "+parent.getJobStepText());
            System.out.println("collector.Collector.dependencychecks():  ROOT/LEAF found: "+parent.getJobStepText());
                new DepXX(parent,null);
             return;
         }
        
           if(parent.getType().equals(3L) && child.getType().equals(2L)){                       //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.dependencyChecks(): calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            DepA2 depA2=new DepA2(parent,child);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        } 
           
           if(parent.getType().equals(2L) && child.getType().equals(1L)){                       //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.dependencyChecks(): calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Dep21 dep21=new Dep21(parent,child,null,model);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        } 
           
        if(parent.getType().equals(3L) && child.getType().equals(1L)){                          //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.dependencyChecks(): calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            DepA1 depA1=new DepA1(parent,child);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        }   
           
           
        if(parent.getType().equals(1L) && child.getType().equals(1L)){                         //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.dependencyChecks(): calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling dependencyChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Dep11 dep11=new Dep11(parent, child,null,model);               //set doubt flags here
            
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
         }
        
        
        List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.dependencyChecks():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
             logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            dependencyChecks(child, gchild);
        }
    }
    
    public void qcChecks(JobStepType0Model parent,JobStepType0Model child){
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      
            System.out.println("collector.Collector.qcChecks():  ROOT/LEAF found: "+parent.getJobStepText());
             logger.info("ROOT/LEAF found: "+parent.getJobStepText());
             new QXX(parent,null);
             return;
         }
        
        if(parent.getType().equals(3L) && child.getType().equals(2L)){           //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.qcChecks(): calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            QA2 qa2=new QA2(parent, child);
        } 
        
        if(parent.getType().equals(2L) && child.getType().equals(1L)){            //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.qcChecks(): calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Q21 q21=new Q21(parent, child,null);
        } 
        
        
        
         if(parent.getType().equals(3L) && child.getType().equals(1L)){             //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.qcChecks(): calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            QA1 qa1=new QA1(parent, child);
        } 
         
         if(parent.getType().equals(1L) && child.getType().equals(1L)){             //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.qcChecks(): calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling qcChecks("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Q11 qa1=new Q11(parent, child,null);
        } 
        
        List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.qcChecks():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
             logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            qcChecks(child, gchild);
        }
         
    }
    
    
    
    public void inheritanceOfDoubt(JobStepType0Model parent,JobStepType0Model child){
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      
            System.out.println("collector.Collector.inheritanceOfDoubt():  ROOT/LEAF found: "+parent.getJobStepText());
            logger.info("ROOT/LEAF found: "+parent.getJobStepText());
            new InheritXX(parent,null);
             return;
         }
        
        if(parent.getType().equals(3L) && child.getType().equals(2L)){              //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            InheritA2 inhA2=new InheritA2(parent, child);
        } 
        
        if(parent.getType().equals(2L) && child.getType().equals(1L)){              //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Inherit21 inh21=new Inherit21(parent, child,null);
        } 
        
        if(parent.getType().equals(3L) && child.getType().equals(1L)){              //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            InheritA1 inhA1=new InheritA1(parent, child);
        } 
        
        if(parent.getType().equals(1L) && child.getType().equals(1L)){              //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling inheritanceOfDoubt("+parent.getJobStepText()+","+child.getJobStepText()+")");
           Inherit11 inh11=new Inherit11(parent, child,null);                     
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): moving on..");
         }
        
   
        List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.inheritanceOfDoubt():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
             logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            inheritanceOfDoubt(child, gchild);
        }
    }

    public void startWatching() {
        
        for (Iterator<JobStepType0Model> iterator = obsModelList.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            if(next instanceof JobStepType1Model){
                List<VolumeSelectionModelType1> volList=next.getVolList();
                
                for (Iterator<VolumeSelectionModelType1> iterator1 = volList.iterator(); iterator1.hasNext();) {
                VolumeSelectionModelType1 vol = iterator1.next();
                vol.startWatching();
                
                }
            }else{
                logger.info("not implemented for jobtype: "+next.getType());
                System.out.println("fend.session.SessionController.startWatching(): not implemented for jobtype: "+next.getType());
            }
            
        }
    }

    private void fillmap(JobStepType0Model parent, JobStepType0Model child, int dist, MultiMap<Integer, JobStepType0Model> mapi) {
        System.out.println("fend.session.SessionController.fillmap(): Inside the function with parent: "+parent.getJobStepText()+" to child: "+child.getJobStepText());
        logger.info("Inside the function with parent: "+parent.getJobStepText()+" to child: "+child.getJobStepText());
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){
             System.out.println("collector.Collector.fillmap():  ROOT/LEAF found: "+parent.getJobStepText());
             logger.info("ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        
        List<JobStepType0Model> children = child.getJsChildren();
              for (Iterator<JobStepType0Model> iterator = children.iterator(); iterator.hasNext();) {
                     JobStepType0Model gch = iterator.next();
                     System.out.println("fend.session.SessionController.fillmap(): from parent: "+child.getJobStepText()+" to child: "+gch.getJobStepText());
                     logger.info("from parent: "+child.getJobStepText()+" to child: "+gch.getJobStepText());
                     mapi.put(dist+1,child);
                    fillmap(child,gch,dist+1,mapi);
                }
        
    }

    private void insightCheck(JobStepType0Model parent, JobStepType0Model child) {
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      
            System.out.println("collector.Collector.insightCheck:  ROOT/LEAF found: "+parent.getJobStepText());
            logger.info("ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        
        
        if(child.getType().equals(3L)  ){              // parent=Acq (type3)
            System.out.println("fend.session.SessionController.insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
            logger.info("calling insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
            
        } 
        
        if(child.getType().equals(2L) ){              // parent=SEGDLoad(type2)
            System.out.println("fend.session.SessionController.insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
            logger.info("calling insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
           
        } 
        
        
        
        if(child.getType().equals(1L) ){              // parent=Denoise (type1)
            System.out.println("fend.session.SessionController.insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+child.getJobStepText());
            logger.info("calling insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
            checkInsightVersion(child);                 
            
         }
        
   
        List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             //System.out.println("fend.session.SessionController.insightCheck():  Calling the jprop child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
              System.out.println("fend.session.SessionController.insightCheck("+child.getJobStepText() +","+gchild.getJobStepText()+")");
              logger.info("calling insightCheck("+parent.getJobStepText()+","+child.getJobStepText()+") on node: "+parent.getJobStepText());
            insightCheck(child, gchild);
        }
        
        
    }

    private void checkInsightVersion(JobStepType0Model job) {
        List<String> claims=job.getInsightVersionsModel().getCheckedVersions();
        System.out.println("fend.session.SessionController.checkInsightVersion(): in job: "+job.getJobStepText());
        logger.info("in job: "+job.getJobStepText()+"  versionClaims: "+claims);
        System.out.println("fend.session.SessionController.checkInsightVersion(): versionClaims: "+claims);
        Set<SequenceHeaders> seq=job.getSequencesInJob();
        for (Iterator<SequenceHeaders> iterator = seq.iterator(); iterator.hasNext();) {
            SequenceHeaders sq = iterator.next();
            System.out.println("fend.session.SessionController.checkInsightVersion(): seq:  "+sq.getSequenceNumber()+" insightBefore: "+sq.isInsightFlag());
            logger.info("seq:  "+sq.getSequenceNumber()+" insightBefore: "+sq.isInsightFlag());
            boolean present=false;
            for(String claim:claims){
                
                
                System.out.println("fend.session.SessionController.checkInsightVersion(): seq:  "+sq.getSequenceNumber()+" insightValue: "+sq.getInsightVersion()+" claim: "+claim);
                logger.info("seq:  "+sq.getSequenceNumber()+" insightValue: "+sq.getInsightVersion()+" claim: "+claim);
                
                if(sq.getInsightVersion().equals(">1")){
                    sq.setInsightFlag(false);
                }
                else
                    if(custInsVerCompare(sq.getInsightVersion(),claim)){
                //if(sq.getInsightVersion().equals(claim)){
                logger.info("setting insightflag for seq: "+sq.getSubsurface()+" = TRUE");
                    sq.setInsightFlag(true);
                    present=true;
                }else{
                    if(!present){
                        sq.setInsightFlag(false);
                    }
                }
            }
            System.out.println("fend.session.SessionController.checkInsightVersion(): seq:  "+sq.getSequenceNumber()+" insightFlagAfter: "+sq.isInsightFlag());
            logger.info("seq:  "+sq.getSequenceNumber()+" insightFlagAfter: "+sq.isInsightFlag());
            
        }
        
    }

    private boolean custInsVerCompare(String insightVersion, String claim) {
       
        String claimf=claim.replaceAll("([^\\w])","");
        String versf=insightVersion.replaceAll("([^\\w])","");
        
        return claimf.equals(versf);
        
    }
    
    
    /*
    Retrive list of headers to be summarized based on updateTime and summarytime in database tables .
    */
    private void fillMapOfJobsAndSubsurfacesToBeSummarized() {
        mapOfChangesSinceLastSummary.clear();   //clear existing changes
        
        Sessions currentSession= sessionServ.getSessions(model.getId());
        List<SessionDetails> ssdOfCurrentSession=ssdServ.getSessionDetails(currentSession);
        /*
        List<JobStep> jobsInCurrentSession=new ArrayList<>();
        
        List<Volume> volumesInCurrentSession=new ArrayList<>();*/
        for (Iterator<SessionDetails> iterator = ssdOfCurrentSession.iterator(); iterator.hasNext();) {    // for each job in session
            SessionDetails next = iterator.next();
            JobStep j=next.getJobStep();                            //getdb job
           // jobsInCurrentSession.add(j);
            List<Volume> volsInJ=jvdServ.getVolumesFor(j);          //get  db vols in the db job
           // List<Subsurface> subsurfacesThatHaveChangedInBackEndNode=new ArrayList<>();
          //  volumesInCurrentSession.addAll(volsInJ);
            
            /* for (Iterator<Volume> iterator1 = volsInJ.iterator(); iterator1.hasNext();) {
            Volume vol = iterator1.next();
            subsurfacesThatHaveChangedInBackEndNode.addAll(hdrServ.getSubsurfacesToBeSummarized(vol));      //subsurfaces in db vol that have changed
            
            
            }*/
            
            JobStepType0Model feJob=model.getJobStepWithId(j.getIdJobStep());  //get frontend corresponding to the backend job
            if(feJob==null){
                throw new NullPointerException("No front end job corresponding to backend: "+j.getNameJobStep()+ " with ID: "+j.getIdJobStep()+" found");
            }else{
                List<SubSurfaceHeaders> subsToSummarizeForFrontEndNode=new ArrayList<>();   //list of front end subsurfaces that need to be summarized
               
               
                /*List<VolumeSelectionModelType0> volsinFeJob=feJob.getVolList();              //all front end vols in the current front end job
                for (Iterator<VolumeSelectionModelType0> iterator2 = volsinFeJob.iterator(); iterator2.hasNext();) {
                VolumeSelectionModelType0 fevol = iterator2.next();
                List<SequenceHeaders> seqsInFevol=fevol.getHeadersModel().getSequenceListInHeaders();           //sequences inside current fe vol
                for (Iterator<SequenceHeaders> iterator3 = seqsInFevol.iterator(); iterator3.hasNext();) {
                SequenceHeaders seqH = iterator3.next();
                List<SubSurfaceHeaders> subsInseqH=seqH.getSubsurfaces();                                   //subs inside the current seq
                for (Iterator<SubSurfaceHeaders> iterator4 = subsInseqH.iterator(); iterator4.hasNext();) {
                SubSurfaceHeaders subInFrontEndVol = iterator4.next();
                for (Iterator<Subsurface> iterator5 = subsurfacesThatHaveChangedInBackEndNode.iterator(); iterator5.hasNext();) {
                Subsurface dbSub = iterator5.next();
                if(subInFrontEndVol.getSubsurface().equalsIgnoreCase(dbSub.getSubsurface())){
                //mapOfChangesSinceSummary.put(frontEndJob, subsInseqH)
                subsToSummarizeForFrontEndNode.add(subInFrontEndVol);
                }
                
                }
                
                }
                
                }
                
                
                }*/
                
                List<VolumeSelectionModelType0> volsinFeJob=feJob.getVolList();              //all front end vols in the current front end job
                for (Iterator<VolumeSelectionModelType0> iterator1 = volsinFeJob.iterator(); iterator1.hasNext();) {
                    VolumeSelectionModelType0 vol = iterator1.next();
                   subsToSummarizeForFrontEndNode.addAll(vol.getSubSurfaceHeadersToBeSummarized());
                }
                
               
           
              mapOfChangesSinceLastSummary.put(feJob, subsToSummarizeForFrontEndNode);          //map of frontend jobs --- subsurfaces that have changed
             
            }
            
            
            
        }
             
        
       
        
    }

    private void lsDependency(JobStepType0Model parent, JobStepType0Model child, List<SubSurfaceHeaders> subsToSummarize,boolean recurseDownwards) {
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      logger.info("ROOT/LEAF found: "+parent.getJobStepText());
                      new DepXX(parent,subsToSummarize);
            System.out.println("fend.session.SessionController.lsDependency():  ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        
       
           if(parent.getType().equals(3L) && child.getType().equals(2L)){                       //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.lsDependency(): calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            DepA2 depA2=new DepA2(parent,child);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        } 
           
           if(parent.getType().equals(2L) && child.getType().equals(1L)){                       //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.lsDependency(): calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Dep21 dep21=new Dep21(parent,child,subsToSummarize,model);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        } 
           
        if(parent.getType().equals(3L) && child.getType().equals(1L)){                          //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsDependency(): calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            DepA1 depA1=new DepA1(parent,child);
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
        }   
           
           
        if(parent.getType().equals(1L) && child.getType().equals(1L)){                         //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsDependency(): calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsDependency("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Dep11 dep11=new Dep11(parent, child,subsToSummarize,model);               //set doubt flags here
            
            System.out.println("fend.session.SessionController.dependencyChecks(): moving on..");
         }
        
        if(recurseDownwards){
             List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.lsDependency():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
             logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            lsDependency(child, gchild,subsToSummarize,recurseDownwards);
        }
        }
        
    }

    private void lsQc(JobStepType0Model parent, JobStepType0Model child, List<SubSurfaceHeaders> subsToSummarize,boolean recurseDownwards) {
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      
            System.out.println("fend.session.SessionController.lsQc():  ROOT/LEAF found: "+parent.getJobStepText());
            new QXX(parent,subsToSummarize);
             logger.info("ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        
        if(parent.getType().equals(3L) && child.getType().equals(2L)){           //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.lsQc(): calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            QA2 qa2=new QA2(parent, child);
        } 
        
        if(parent.getType().equals(2L) && child.getType().equals(1L)){            //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.lsQc(): calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Q21 q21=new Q21(parent, child,subsToSummarize);
        } 
        
        
        
         if(parent.getType().equals(3L) && child.getType().equals(1L)){             //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsQc(): calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            QA1 qa1=new QA1(parent, child);
        } 
         
         if(parent.getType().equals(1L) && child.getType().equals(1L)){             //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsQc(): calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsQc("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Q11 qa1=new Q11(parent, child,subsToSummarize);
        }
         
         if(recurseDownwards){
              List<JobStepType0Model> grandChildren=child.getJsChildren();
                for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
                   JobStepType0Model gchild = iterator.next();
                    System.out.println("fend.session.SessionController.lsQc():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
                    logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
                   lsQc(child, gchild,subsToSummarize,recurseDownwards);
               }
         }
    }

    private void lsInherit(JobStepType0Model parent, JobStepType0Model child, List<SubSurfaceHeaders> subsToSummarize,boolean recurseDownwards) {
        if(parent.getJsChildren().size()==1 && parent.getJsChildren().get(parent.getJsChildren().size()-1).getId().equals(parent.getId())){   //if child=parent. leaf/root reached
                      
            System.out.println("fend.session.SessionController.lsInherit():  ROOT/LEAF found: "+parent.getJobStepText());
            new InheritXX(parent,subsToSummarize);
            logger.info("ROOT/LEAF found: "+parent.getJobStepText());
             return;
         }
        System.out.println("fend.session.SessionController.lsInherit()");
        if(parent.getType().equals(3L) && child.getType().equals(2L)){              //between parent=Acq and child=SEGDLoad (type2)
            System.out.println("fend.session.SessionController.lsInherit(): calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            InheritA2 inhA2=new InheritA2(parent, child);
        } 
        
        if(parent.getType().equals(2L) && child.getType().equals(1L)){              //between parent=SEGDLoad(type2) and child=Denoise(type1)
            System.out.println("fend.session.SessionController.lsInherit(): calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            Inherit21 inh21=new Inherit21(parent, child,subsToSummarize);
        } 
        
        if(parent.getType().equals(3L) && child.getType().equals(1L)){              //between parent=Acq and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsInherit(): calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            InheritA1 inhA1=new InheritA1(parent, child);
        } 
        
        if(parent.getType().equals(1L) && child.getType().equals(1L)){              //between parent=Denoise and child=Denoise (type1)
            System.out.println("fend.session.SessionController.lsInherit(): calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
            logger.info("calling lsInherit("+parent.getJobStepText()+","+child.getJobStepText()+")");
           Inherit11 inh11=new Inherit11(parent, child,subsToSummarize);                     
            System.out.println("fend.session.SessionController.inheritanceOfDoubt(): moving on..");
         }
        
        if(recurseDownwards){
             List<JobStepType0Model> grandChildren=child.getJsChildren();
         for (Iterator<JobStepType0Model> iterator = grandChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model gchild = iterator.next();
             System.out.println("fend.session.SessionController.lsInherit():  Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
             logger.info("Calling the next child : "+gchild.getJobStepText() +" :Parent: "+child.getJobStepText());
            lsInherit(child, gchild,subsToSummarize,recurseDownwards);
        }
        }
    }

   
   
    
   
}
