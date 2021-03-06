/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type4;

import db.model.JobStep;
import db.model.QcMatrix;
import db.model.QcType;
import db.model.SessionDetails;
import db.model.Sessions;
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
import fend.session.SessionModel;
import fend.session.dialogs.DialogModel;
import fend.session.dialogs.DialogNode;
import fend.session.edges.Links;
import fend.session.edges.LinksModel;
import fend.session.edges.anchor.AnchorModel;
import fend.session.edges.curves.CubCurve;
import fend.session.edges.curves.CubCurveModel;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.insightVersions.InsightVersionsController;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.insightVersions.InsightVersionsNode;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type0.JobStepType0NodeController;
//import fend.session.node.jobs.types.type2.JobStepType2Model;
//import fend.session.node.jobs.types.type2.JobStepType2Node;
import fend.session.node.qcTable.QcMatrixModel;
import fend.session.node.qcTable.QcTableNode;
import fend.session.node.qcTable.QcTypeModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListNode;
import fend.session.node.volumes.type4.VolumeSelectionCellType4;
import fend.session.node.volumes.type4.VolumeSelectionModelType4;
//import fend.session.node.volumes.type2.VolumeSelectionCellType2;
//import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobStepType4NodeController implements JobStepType0NodeController{
   // public static File insightLocation=new File("/d/sw/Insight");    
    final private ChangeListener<String> JOBSTEP_TEXT_FIELD_CHANGE_LISTENER=new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("Name changed from "+oldValue+ " to "+newValue);
            updateJobStepTextFieldView(newValue);
        }
    };
    
    
    
    final private ChangeListener<ObservableList<VolumeSelectionModelType4>> JOBSTEP_VOLUME_LIST_CHANGE_LISTENER =new ChangeListener<ObservableList<VolumeSelectionModelType4>>() {

        @Override
        public void changed(ObservableValue<? extends ObservableList<VolumeSelectionModelType4>> observable, ObservableList<VolumeSelectionModelType4> oldValue, ObservableList<VolumeSelectionModelType4> newValue) {
          
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
    
    private List<VolumeSelectionModelType4> volSelectionList=new ArrayList<>();
    private ObservableList<VolumeSelectionModelType4> obsList=FXCollections.observableList(volSelectionList);
    private int show=0;
    private JobStepType4Model model;
    private JobStepType4Node jsn;                                //the real node is jsn.getJobStepNode()
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
    private ListView<VolumeSelectionModelType4> volumeSelView;
    
   
    
    
 
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
    void openQMatrix(ActionEvent event) {
        
        qcMatrixModel=model.getQcTableModel().getQcMatrixModel();
        
        if(qcMatrixModel.getQcTypePresMap().isEmpty()){
            
      
            /*  SessionModel smodel=model.getSessionModel();
            Sessions currentsession= sserv.getSessions(smodel.getId());
            JobStep js= jserv.getJobStep(model.getId());
            SessionDetails sessiondetails=ssdserv.getSessionDetails(js, currentsession);*/
        
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix()");
         SessionModel smodel=model.getSessionModel();
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): session is currently: "+smodel.getName());
        Sessions currentsession= sserv.getSessions(smodel.getId());
            if(currentsession!=null){
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): session from database is currently: "+smodel.getName());
            }else{
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): NO ENTRY FOR SESSION found in database");
            }
        JobStep js= jserv.getJobStep(model.getId());
        if(js==null){
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): Null value found for jobstep from database: Please save the session before proceeding");
                DialogModel dm=new DialogModel();
                 String message="Please save the session before attempting to assign a QC matrix ";
                 dm.setMessage(message);
                 DialogNode dn=new DialogNode(dm);
                 return;
        }else{
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): Jobstep: "+js.getNameJobStep()+" found");
        }
        SessionDetails sessiondetails=ssdserv.getSessionDetails(js, currentsession);
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): sessiondetails: "+((sessiondetails==null)?"is Null":"session: "+sessiondetails.getSessions().getNameSessions()));
     
         
         
         
   //     for(VolumeSelectionModelType1 vmodel:obsList){
            //Check with definition of QcMatrix
        //Volume v=vserv.getVolume(vmodel.getId());
        
      //  List<QcMatrix> qcmatdef=qcmserv.getQcMatrixForVolume(v);
        List<QcMatrix> qcmatdef=qcmserv.getQcMatrixForSessionDetails(sessiondetails);
         
         //qcMatrixModel=model.getQcMatrixModel();
         
      //  List<QcType>
        
        //List<QcType> allQcTypes=qserv.getQcTypesForSession(currentsession);
        List<QcType> allQcTypes=qserv.getAllQcTypes();
        
        /*if(allQcTypes.isEmpty()){          //no qctypes declared for the entire session
        for (Iterator<String> iterator = types.iterator(); iterator.hasNext();) {
        String type = iterator.next();
        QcType q=new QcType();
        q.setName(type.toLowerCase());              //2Dstacks is the same as 2dstACks
        // q.setSessions(currentsession);
        qserv.createQcType(q);
        
        allQcTypes=qserv.getAllQcTypes();
        }
        */
              //  allQcTypes=qserv.getQcTypesForSession(currentsession);
             
        
        List<String> qcTypesNames=new ArrayList<>();
        //qcCModel=vmodel.getQcCheckListModel(); 
        qcCModel=model.getQcCheckListModel();
        for (Iterator<QcType> iterator = allQcTypes.iterator(); iterator.hasNext();) {
            QcType next = iterator.next();
            String name=next.getName();
            qcTypesNames.add(name);
        }
        qcCModel.setQcTypes(qcTypesNames);
      
        if(qcmatdef.isEmpty()){                     //no matrix defined for this job. ask for a definition           
           
            
            
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): QcMatrix not defined for current node");
            
            qcCheckListNode qcCNode=new qcCheckListNode(qcCModel);
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): selected: "+qcCModel.getCheckedTypes());
           
           
            
            
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
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): retrieved from db: "+next.getName());
                //names.(next.getName());
                QcTypeModel qcTypeModel=new QcTypeModel();
                qcTypeModel.setId(next.getIdQcType());
                qcTypeModel.setName(next.getName());
                sessionQcTypeModels.add(qcTypeModel);
                qcMatrixModel.addToQcTypePresMap(qcTypeModel, Boolean.FALSE);               //initially set all qctypes to false;
            }
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): Setting the ticked ones to true");
             for (Iterator<Long> iterator = ticked.iterator(); iterator.hasNext();) {
                Long tickedid = iterator.next();
                QcType selectedType=qserv.getQcType(tickedid);
                QcTypeModel qctymod=new QcTypeModel();
                qctymod.setId(selectedType.getIdQcType());
                qctymod.setName(selectedType.getName());
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): ticked: id: "+selectedType.getIdQcType()+" :name: "+selectedType.getName());
                
                qctypeModels.add(qctymod);
            }
             
            
             
            
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): qctypeModels.size(): "+qctypeModels.size());
            for (Iterator<QcTypeModel> iterator = qctypeModels.iterator(); iterator.hasNext();) {
                QcTypeModel def = iterator.next();
                QcTypeModel keyInTypePresMap=qcMatrixModel.getKeyFromTypePresMap(def);
                if(keyInTypePresMap==null){
                    System.out.println("fend.session.node.jobs.types.type1.JobStepType1NodeController.openQMatrix(): NULL value encountered");
                }
                qcMatrixModel.addToQcTypePresMap(keyInTypePresMap, Boolean.TRUE);                    //set the ones checked to true;
                
            }
            
            Map<QcTypeModel,Boolean> qcmmap=qcMatrixModel.getQcTypePresMap();
            
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): Creating the Qc matrix for Job: "+model.getJobStepText());
            System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.openQMatrix(): qcMatrixModel.size(): "+qcMatrixModel.getQcTypePresMap().size());
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
        
        
        
        
       
           
         showPopList(qcmatdef);    //qcmatdef holds the definitions
       // }
        
       }else{
            List<SequenceHeaders> seqsinJob=new ArrayList<>();
           for(VolumeSelectionModelType4 vmod:obsList){
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
       
        System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.showPopList(): qcMatrixModel.getQcTypePresMap().isEmpty()?: "+qcMatrixModel.getQcTypePresMap().isEmpty());
       
       
       if(qcMatrixModel.getQcTypePresMap().isEmpty()){                             //there is no definition of the qcmatrix in this job..so define it
        //qcMatrixModel.clear();
        
        
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
       qcMatrixModel=model.getQcTableModel().getQcMatrixModel();            //qcmatrix has been defined. get it.
        
        
        
           //model.getQcTableModel().setQctypes(qctypeModels);
           List<SequenceHeaders> seqsinJob=new ArrayList<>();
           for(VolumeSelectionModelType4 vmod:obsList){
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
    void handleAddNewVolumeSelectionButton(ActionEvent event) {
            
        if(model.getVolList()!=null){
            obsList=model.getVolList();
        }
             
        
                obsList.add(new VolumeSelectionModelType4(true,4L,model));
                 volumeSelView.setItems(obsList);
               
               
                model.setVolList(obsList);
                //System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.handleAddNewVolumeSelectionButton()");
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.handleAddNewVolumeSelectionButton() Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.handleAddNewVolumeSelectionButton() At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModelType4> iterator = model.getVolList().iterator(); iterator.hasNext();) {
            VolumeSelectionModelType4 next = iterator.next();
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
        //System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.onLinkDragDropped()");
        System.out.println("fend.session.node.jobs.types.type4.JobStepType4NodeController.onLinkDragDropped() LinkDragDropped On");
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
       jsn=(JobStepType4Node) event.getGestureTarget();
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
    
    
   
    public ObservableList<VolumeSelectionModelType4> getObsList() {
        return obsList;
    }

     @Override
    public void setObsList(ObservableList obsList) {
        this.obsList = obsList;
    }
    
    
    public void startNewLink(JobStepType0Model jmod){
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
    
    
    

    public JobStepType4Model getModel() {
        return model;
    }

    public void setModel(JobStepType4Model model) {
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
         
         
        
         
         
         
      volumeSelView.setCellFactory(new Callback<ListView<VolumeSelectionModelType4>, ListCell<VolumeSelectionModelType4>>() {

                        @Override
                        public ListCell<VolumeSelectionModelType4> call(ListView<VolumeSelectionModelType4> param) {
                            //System.out.println("JSNController: calling setCellFactory on  "+param.getItems().get(show).getLabel());
                            
                           
                            
                             return new VolumeSelectionCellType4();
                          
                        }
                    });
         
        // model.getPendingFlagProperty().addListener(CHECK_BOX_CHANGE_LISTENER);
       
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
    
    private void updateJobStepVolumeListView(ObservableList<VolumeSelectionModelType4> newValue){
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
    public JobStepType4NodeController() {
        // System.out.println("Constructor called");
        
         
           
    }
    
    

    
    void setView(JobStepType4Node aThis) {
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
        
        
        for (Iterator<VolumeSelectionModelType4> iterator = obsList.iterator(); iterator.hasNext();) {
            VolumeSelectionModelType4 next = iterator.next();
            volumeSelView.setItems(obsList);
               
                
                model.setVolList(obsList);                               //Redundant step???
                
                
                System.out.println("JSNC: Adding volumeModel "+obsList.get(obsList.size()-1).getId()+" to JobStepModel "+model.getId());
                System.out.println("JSNC: At this point the jobStep model# "+model.getId()+" has the following Volumes ");
                
                for (Iterator<VolumeSelectionModelType4> iterator1 = model.getVolList().iterator(); iterator1.hasNext();) {
            VolumeSelectionModelType4 next1 = iterator1.next();
                    System.out.println("         id# "+next1.getId()+" label: "+next1.getLabel()+" headerButtonIsDisabled :"+next1.isHeaderButtonIsDisabled());
                    
                    HeadersModel hmod=next1.getHeadersModel();
                    List<SequenceHeaders> seqL=hmod.getSequenceListInHeaders();
                    for (Iterator<SequenceHeaders> iterator2 = seqL.iterator(); iterator2.hasNext();) {
                        SequenceHeaders next2 = iterator2.next();
                        System.out.println("fend.session.node.jobs.JobStepType2NodeController.setVolumeModelsForFrontEndDisplay() Sequence: "+next2.getSequenceNumber());
                    }
            
        }
            
            
        }
    }

    @Override
    public void setInsightVersionsModel(InsightVersionsModel insVerModel) {
        this.model.setInsightVersionsModel(insVerModel);
    }

    @Override
    public void setInsightListForFrontEndDisplay() {
        
    }

   

}
