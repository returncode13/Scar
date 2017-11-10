/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type1;


import com.sun.javafx.scene.input.DragboardHelper;
import db.model.JobStep;
import db.model.QcMatrix;
import db.model.QcType;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Volume;
import db.services.JobStepService;
import db.services.JobStepServiceImpl;
import db.services.QcMatrixService;
import db.services.QcMatrixServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;
import db.services.SessionsService;
import db.services.SessionsServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;

import fend.session.SessionController;
import fend.session.SessionModel;
import fend.session.dialogs.DialogModel;
import fend.session.dialogs.DialogNode;

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
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.insightVersions.InsightVersionsController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.insightVersions.InsightVersionsNode;
import fend.session.node.jobs.types.type0.JobStepType0Model;

import fend.session.node.volumes.type1.VolumeSelectionCellType1;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.CheckBoxListCell;
import fend.session.node.jobs.types.type0.JobStepType0NodeController;
import fend.session.node.qcTable.QcMatrixModel;
import fend.session.node.qcTable.QcTableNode;
import fend.session.node.qcTable.QcTypeModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListNode;
import java.util.Map;


/**
 *
 * @author naila0152
 */
public class JobStepType1NodeController implements JobStepType0NodeController {
    
   // public static File insightLocation=new File("/home/sharath/programming/polarcus/insight");    
    public static File insightLocation=new File("/d/sw/Insight");    
    final private ChangeListener<String> JOBSTEP_TEXT_FIELD_CHANGE_LISTENER=new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("Name changed from "+oldValue+ " to "+newValue);
            updateJobStepTextFieldView(newValue);
        }
    };
    
    
    
    final private ChangeListener<ObservableList<VolumeSelectionModelType1>> JOBSTEP_VOLUME_LIST_CHANGE_LISTENER =new ChangeListener<ObservableList<VolumeSelectionModelType1>>() {

        @Override
        public void changed(ObservableValue<? extends ObservableList<VolumeSelectionModelType1>> observable, ObservableList<VolumeSelectionModelType1> oldValue, ObservableList<VolumeSelectionModelType1> newValue) {
           /* System.out.println("OldValue List Contents ");
            for (Iterator<VolumeSelectionModel> iterator = oldValue.iterator(); iterator.hasNext();) {
                VolumeSelectionModelType1 next = iterator.next();
                System.out.println("VSModel #  "+next.getVsId());
                
            }
            
             System.out.println("NewValue List Contents ");
            for (Iterator<VolumeSelectionModel> iterator = newValue.iterator(); iterator.hasNext();) {
                VolumeSelectionModelType1 next = iterator.next();
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
            
            final private ChangeListener<Boolean> CHECK_BOX_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println(".changed() from "+oldValue+" to "+newValue);
        }
    };
    
    private List<VolumeSelectionModelType1> volSelectionList=new ArrayList<>();
    private ObservableList<VolumeSelectionModelType1> obsList=FXCollections.observableList(volSelectionList);
    private int show=0;
    private JobStepType1Model model;
    private JobStepType1Node jsn;                                //the real node is jsn.getJobStepNode()
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
    private ListView<VolumeSelectionModelType1> volumeSelView;
    
    @FXML
    private Button insightVerButton;
    
     @FXML
    private ListView<String> insightListView;
    
    @FXML
    private CheckBox pendingCheckBox;
    
     @FXML
    private CheckBox qcCheckBox;
 
      @FXML
    private Button qmatBtn1;
     
     
    private QcMatrixModel qcMatrixModel;
    private qcCheckListModel qcCModel;  
    
    private QcTypeService qserv=new QcTypeServiceImpl();
    private SessionsService sserv=new SessionsServiceImpl();
    private VolumeService vserv=new VolumeServiceImpl();
    private QcMatrixService qcmserv=new QcMatrixServiceImpl();
    private SessionDetailsService ssdserv=new SessionDetailsServiceImpl();
    private JobStepService jserv=new JobStepServiceImpl();
    
    @FXML
    void handleAddNewVolumeSelectionButton(ActionEvent event) {
            
        if(model.getVolList()!=null){
            obsList=model.getVolList();
        }
             
        
                obsList.add(new VolumeSelectionModelType1(true,1L,model));
                 volumeSelView.setItems(obsList);
               
               
                model.setVolList(obsList);
                
                System.out.println("JSNC: Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("JSNC: At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModelType1> iterator = model.getVolList().iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
                    System.out.println("         id# "+next.getId()+" label: "+next.getLabel()+" headerButtonIsDisabled :"+next.isHeaderButtonIsDisabled());
            
        }
                
               show++;
              
    
    }
    
    
     @FXML
    void openQMatrix(ActionEvent event) {
        
        qcMatrixModel=model.getQcTableModel().getQcMatrixModel();
        
        if(qcMatrixModel.getQcTypePresMap().isEmpty()){
            
      
            SessionModel smodel=model.getSessionModel();
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): session is currently: "+smodel.getName());
        Sessions currentsession= sserv.getSessions(smodel.getId());
            if(currentsession!=null){
                System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): session from database is currently: "+smodel.getName());
            }else{
                System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): NO ENTRY FOR SESSION found in database");
            }
        JobStep js= jserv.getJobStep(model.getId());
        if(js==null){
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Null value found for jobstep from database: Please save the session before proceeding");
                DialogModel dm=new DialogModel();
                 String message="Please save the session before attempting to assign a QC matrix ";
                 dm.setMessage(message);
                 DialogNode dn=new DialogNode(dm);
                 return;
        }else{
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Jobstep: "+js.getNameJobStep()+" found");
        }
        SessionDetails sessiondetails=ssdserv.getSessionDetails(js, currentsession);
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): sessiondetails: "+((sessiondetails==null)?"is Null":"session: "+sessiondetails.getSessions().getNameSessions()));
     
        List<QcMatrix> qcmatdef=qcmserv.getQcMatrixForSessionDetails(sessiondetails);
       
        List<QcType> allQcTypes=qserv.getAllQcTypes();

             
         
        List<String> qcTypesNames=new ArrayList<>();
        //qcCModel=vmodel.getQcCheckListModel(); 
        qcCModel=model.getQcCheckListModel();
        for (Iterator<QcType> iterator = allQcTypes.iterator(); iterator.hasNext();) {
            QcType next = iterator.next();
            String name=next.getName();
            qcTypesNames.add(name);
        }
        qcCModel.setQcTypes(qcTypesNames);
      
         System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Checking if the QCMatrix is defined for this node");
        if(qcmatdef.isEmpty()){                     //no matrix defined for this job. ask for a definition           
           
            
            
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): QcMatrix not defined for current node");
            
            qcCheckListNode qcCNode=new qcCheckListNode(qcCModel);
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): selected: "+qcCModel.getCheckedTypes());
           
           
            
            
            List<String> types=qcCModel.getCheckedTypes();
            List<QcTypeModel> qctypeModels=new ArrayList<>();
            List<Long> ticked=new ArrayList<>();
            
            
             
             if(allQcTypes.isEmpty()){          //no qctypes declared for the entire session
                for (Iterator<String> iterator = types.iterator(); iterator.hasNext();) {
                String type = iterator.next();
                QcType q=new QcType();
                q.setName(type.toLowerCase());              //2Dstacks is the same as 2dstACks
               // q.setSessions(currentsession);
                qserv.createQcType(q);
                
                
                }
               
              //  allQcTypes=qserv.getQcTypesForSession(currentsession);
              allQcTypes=qserv.getAllQcTypes();
                
             }
             else{                                  //if there are existing entries
              
                 List<String> existingnames=new ArrayList<>();
                 for (Iterator<QcType> iterator = allQcTypes.iterator(); iterator.hasNext();) {
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
                    // qct.setSessions(currentsession);
                     qserv.createQcType(qct);               //create new entries
                     ticked.add(qct.getIdQcType());
                 }
                 
                 
               //  allQcTypes=qserv.getQcTypesForSession(currentsession);    //get the list again..this time with new entries
                allQcTypes=qserv.getAllQcTypes();
             }
             
             
            //at this point the list qcTypesforSession is now a list with all the entries (new and old) for qctypes
            //and           the list "ticked" contains the ids of the qctype records that have been selected for the current definition
             
                
             
             
             
            List<QcTypeModel> sessionQcTypeModels=new ArrayList<>();
            for (Iterator<QcType> iterator = allQcTypes.iterator(); iterator.hasNext();) {
                QcType next = iterator.next();
                System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): retrieved from db: "+next.getName());
                //names.(next.getName());
                QcTypeModel qcTypeModel=new QcTypeModel();
                qcTypeModel.setId(next.getIdQcType());
                qcTypeModel.setName(next.getName());
                sessionQcTypeModels.add(qcTypeModel);
                qcMatrixModel.addToQcTypePresMap(qcTypeModel, Boolean.FALSE);               //initially set all qctypes to false;
            }
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Setting the ticked ones to true");
             for (Iterator<Long> iterator = ticked.iterator(); iterator.hasNext();) {
                Long tickedid = iterator.next();
                QcType selectedType=qserv.getQcType(tickedid);
                QcTypeModel qctymod=new QcTypeModel();
                qctymod.setId(selectedType.getIdQcType());
                qctymod.setName(selectedType.getName());
                System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): ticked: id: "+selectedType.getIdQcType()+" :name: "+selectedType.getName());
                
                qctypeModels.add(qctymod);
            }
             
            
             
            
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): qctypeModels.size(): "+qctypeModels.size());
            for (Iterator<QcTypeModel> iterator = qctypeModels.iterator(); iterator.hasNext();) {
                QcTypeModel def = iterator.next();
                qcMatrixModel.addToQcTypePresMap(def, Boolean.TRUE);                    //set the ones checked to true;
                
            }
            
            Map<QcTypeModel,Boolean> qcmmap=qcMatrixModel.getQcTypePresMap();
            
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Creating the Qc matrix for Job: "+model.getJobStepText());
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): qcMatrixModel.size(): "+qcMatrixModel.getQcTypePresMap().size());
            for (Map.Entry<QcTypeModel, Boolean> entry : qcmmap.entrySet()) {
                QcTypeModel qctype = entry.getKey();
                Boolean ispres = entry.getValue();
                QcMatrix qcmatrix=new QcMatrix();
               // qcmatrix.setVolume(v);
                qcmatrix.setSessionDetails(sessiondetails);
                QcType qselect=qserv.getQcType(qctype.getId());
                qcmatrix.setQctype(qselect);
                qcmatrix.setPresent(ispres);
                qcmserv.createQcMatrix(qcmatrix);                   //create the qc matrix
                
                
            }
           
            
         //qcmatdef=qcmserv.getQcMatrixForVolume(v);   
         qcmatdef=qcmserv.getQcMatrixForSessionDetails(sessiondetails);             //only get the qctypes for which present=true
        }
        
        
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): QCMatrix is defined for this node");
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): size of qcmatdef= "+qcmatdef.size());
       
           
         showPopList(qcmatdef);    //qcmatdef holds the definitions
       // }
        
       }else{
            System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): Size of the qctypePresMap: "+qcMatrixModel.getQcTypePresMap().size());
            List<SequenceHeaders> seqsinJob=new ArrayList<>();
           for(VolumeSelectionModelType1 vmod:obsList){
               HeadersModel hmod=vmod.getHeadersModel();
            List<SequenceHeaders> seqsinVol=hmod.getSequenceListInHeaders();
            seqsinJob.addAll(seqsinVol);
           }
           
          // vmodel.getQcTableModel().setSequences(seqsinVol);
          model.getQcTableModel().setSequences(seqsinJob);
          model.getQcTableModel().loadQcTypes();
           
          // QcTableNode qcMatrixNode=new QcTableNode(vmodel.getQcTableModel());
          QcTableNode qcMatrixNode=new QcTableNode(model.getQcTableModel());
        }   
        
        
    }
    
    void showPopList(List<QcMatrix> qcmatrices){
       //if(model.getQcTableModel().getQcMatrixModel()==null){     //the resultant qcmatrix from this call is not the same as the models qcmatrix. aka, the qcMatrixModel variable
       
        System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.showPopList(): qcMatrixModel.getQcTypePresMap().isEmpty()?: "+qcMatrixModel.getQcTypePresMap().isEmpty()+" size: "+qcMatrixModel.getQcTypePresMap().size());
       
       
       if(qcMatrixModel.getQcTypePresMap().isEmpty()){                             //there is no definition of the qcmatrix in this job..so define it
        //qcMatrixModel.clear();
           System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.showPopList(): map is Empty.");
        
           for (Iterator<QcMatrix> iterator = qcmatrices.iterator(); iterator.hasNext();) {
            QcMatrix rec = iterator.next();
            QcType qctype=rec.getQctype();
            //Volume v=rec.getVolume();
            SessionDetails ssd=rec.getSessionDetails();
            Boolean pres=rec.getPresent();
            
            QcTypeModel qctm=new QcTypeModel();
            qctm.setId(qctype.getIdQcType());
            qctm.setName(qctype.getName());
            qcMatrixModel.addToQcTypePresMap(qctm, pres);
            
            }
        
        //   vmodel.getQcTableModel().setQcMatrixModel(qcMatrixModel);
        
           model.getQcTableModel().setQcMatrixModel(qcMatrixModel); 
        }
       else
       {
       qcMatrixModel=model.getQcTableModel().getQcMatrixModel();            //qcmatrix has been defined. get it.
        
        System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.showPopList(): map is NOT Empty.");
        
    }
        
           //model.getQcTableModel().setQctypes(qctypeModels);
           List<SequenceHeaders> seqsinJob=new ArrayList<>();
           for(VolumeSelectionModelType1 vmod:obsList){
               HeadersModel hmod=vmod.getHeadersModel();
            List<SequenceHeaders> seqsinVol=hmod.getSequenceListInHeaders();
            seqsinJob.addAll(seqsinVol);
           }
           
          // vmodel.getQcTableModel().setSequences(seqsinVol);
          model.getQcTableModel().setSequences(seqsinJob);
          model.getQcTableModel().loadQcTypes();
           
          // QcTableNode qcMatrixNode=new QcTableNode(vmodel.getQcTableModel());
          QcTableNode qcMatrixNode=new QcTableNode(model.getQcTableModel());
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
       jsn=(JobStepType1Node) event.getGestureTarget();
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
    
   
    public ObservableList<VolumeSelectionModelType1> getObsList() {
        return obsList;
    }

    /* public void setObsList(ObservableList<VolumeSelectionModel> obsList) {
    this.obsList = obsList;
    }*/
    
    
    public void startNewLink(JobStepType1Model jmod){
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
       
                
    };
    
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
    
    
    

    public JobStepType1Model getModel() {
        return model;
    }

    public void setModel(JobStepType1Model model) {
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
         pendingCheckBox.selectedProperty().unbind();
         qcCheckBox.selectedProperty().unbind();
        
         
     }
    
     private void setupModelListeners(){
         model.getJobStepTextProperty().addListener(JOBSTEP_TEXT_FIELD_CHANGE_LISTENER);
         jobStepTextField.accessibleTextProperty().bindBidirectional(model.getJobStepTextProperty());
         
         
        
         
         
         
      volumeSelView.setCellFactory(new Callback<ListView<VolumeSelectionModelType1>, ListCell<VolumeSelectionModelType1>>() {

                        @Override
                        public ListCell<VolumeSelectionModelType1> call(ListView<VolumeSelectionModelType1> param) {
                            //System.out.println("JSNController: calling setCellFactory on  "+param.getItems().get(show).getLabel());
                            
                           
                            
                             return new VolumeSelectionCellType1();
                          
                        }
                    });
         
        // model.getPendingFlagProperty().addListener(CHECK_BOX_CHANGE_LISTENER);
         pendingCheckBox.selectedProperty().bind(model.getPendingFlagProperty());
         qcCheckBox.selectedProperty().bind(model.getDependency());
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
    
    private void updateJobStepVolumeListView(ObservableList<VolumeSelectionModelType1> newValue){
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
                             return new VolumeSelectionCellType1();
                          
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
    public JobStepType1NodeController() {
        // System.out.println("Constructor called");
        
         
           
    }
    
    

    
    void setView(JobStepType1Node aThis) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        jsn=aThis;
        
        jsn.parentProperty().addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                basePane=(AnchorPane) jsn.getParent();
                
            }
            
        });
    }

    void addToLineage(JobStepType0Model parent) {
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
        
        
        for (Iterator<VolumeSelectionModelType1> iterator = obsList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType1 next = iterator.next();
            volumeSelView.setItems(obsList);
               
                
                model.setVolList(obsList);                               //Redundant step???
                
                
                System.out.println("JSNC: Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("JSNC: At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModelType1> iterator1 = model.getVolList().iterator(); iterator1.hasNext();) {
            VolumeSelectionModelType1 next1 = iterator1.next();
                    System.out.println("         id# "+next1.getId()+" label: "+next1.getLabel()+" headerButtonIsDisabled :"+next1.isHeaderButtonIsDisabled());
                    
                    HeadersModel hmod=next1.getHeadersModel();
                    List<SequenceHeaders> seqL=hmod.getSequenceListInHeaders();
                    for (Iterator<SequenceHeaders> iterator2 = seqL.iterator(); iterator2.hasNext();) {
                        SequenceHeaders next2 = iterator2.next();
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

    @Override
    public void setObsList(ObservableList obsList) {
        this.obsList = obsList;
    }

   
    
}

