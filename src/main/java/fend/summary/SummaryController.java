/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.model.RawSeqInfo;
import db.model.Sequence;
import db.services.OrcaViewService;
import db.services.OrcaViewServiceImpl;
import db.services.RawSeqInfoService;
import db.services.RawSeqInfoServiceImpl;
import db.services.SequenceService;
import db.services.SequenceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.HeadersNode;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type2.VolumeSelectionModelType2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import mid.doubt.Doubt;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */






public class SummaryController extends Stage{
    private SummaryModel model;
    private SummaryNode node;
    private VolumeService vserv=new VolumeServiceImpl();
    private ObservableSet<SummarySequenceModel> sumSeqModelSet=FXCollections.observableSet();
    private MultiMap<Long,DepthModel> mapseqDepthModel=new MultiValueMap<>();
    private MultiMap<DepthModel,Long> mapDepthModelSeq=new MultiValueMap<>();                  //inverse map of mapseqDepthModel
    private DepthListModel depthListmodel;
    private SequenceService seqserv=new SequenceServiceImpl();
    private OrcaViewService ovserv=new OrcaViewServiceImpl();
    private RawSeqInfoService rawSeqServ=new RawSeqInfoServiceImpl();
    
    @FXML
            TableView tableView;
    
    void setModel(SummaryModel mod){
        this.model=mod;
        MultiMap<Integer,JobStepType0Model> map=model.getDepthNodeMap();
        TableColumn<SummarySequenceModel,Long> seqTableColumn=new TableColumn<>("seq");
        seqTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TableColumn.CellDataFeatures<SummarySequenceModel, Long> param) {
                return param.getValue().seqProperty().asObject();
            }
        });
        //System.out.println("fend.summary.SummaryController.setModel()");
       
        TableColumn<SummarySequenceModel,String> linenameTableColumn=new TableColumn<>("linename");
        linenameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                return param.getValue().linenameProperty();
            }
        });
        
        Set<Integer> keys=map.keySet();
         List<TableColumn<SummarySequenceModel,DepthModel>> depthsForGraph=new ArrayList<>();       //a list of depths for each seq
         depthListmodel=new DepthListModel();
         List<DepthModel> dmodelList=depthListmodel.getListOfDepthModel();
         
        for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
            Integer depth = iterator.next();
            TableColumn<SummarySequenceModel,DepthModel> dtc=new TableColumn<>(" "+depth+" ");          //for each depth
            DepthModel dm=new DepthModel();
            
            
            
            final int depindex=depth;
            dm.setDepth(depth);
            dtc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, DepthModel>, ObservableValue<DepthModel>>() {
                @Override
                public ObservableValue<DepthModel> call(TableColumn.CellDataFeatures<SummarySequenceModel, DepthModel> param) {
                   // return param.getValue().getDepthlist().depthsProperty().valueAt(depindex);
                    return (ObservableValue<DepthModel>) param.getValue().getDepthlist().getListOfDepthModel().get(depindex);
                   
                }
            });
            
            
            dtc.setCellFactory(new Callback<TableColumn<SummarySequenceModel, DepthModel>, TableCell<SummarySequenceModel, DepthModel>>() {
                @Override
                public TableCell<SummarySequenceModel, DepthModel> call(TableColumn<SummarySequenceModel, DepthModel> param) {
                    
                    TableCell<SummarySequenceModel, DepthModel>  cell=new TableCell<SummarySequenceModel, DepthModel>(){
                        
                        /*@Override
                        public void updateItem(DepthModel item,boolean empty){
                        super.updateItem(item, empty);
                        if(!isEmpty()){
                        String color=depthcolors.get(depindex%2);
                        this.setStyle("-fx-background-color:red;");
                        System.out.println(" STYLE "+getStyle() +" COLOR "+depthcolors.get(depindex%2));
                        setText(""+item.getDepth());
                        }
                        }*/
                        
                        @Override
                         protected void updateItem(DepthModel item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(empty||item==null){
                                     //  setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   
                                 
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:orange");
                                      System.out.println(" STYLE "+getStyle() +" COLOR "+depthcolors.get(depindex%2));
                                  
                                     
                                   }
                               }
                        
                        
                        
                        /* @Override
                        public void updateIndex(int i){
                        super.updateIndex(i);
                        if(i>=0){
                        String color=depthcolors.get(depindex%2);
                        this.setStyle("-fx-background-color: "+color+";");
                        System.out.println(getStyle());
                        
                        }
                        }*/
                    };
                 return cell;   
                }
            });
            
            List<JobStepType0Model> jobList=(List<JobStepType0Model>) map.get(depth);
            List<TableColumn<SummarySequenceModel,SummaryJobNodeModel>> jobsForDepth=new ArrayList<>();                  // a list of jobs for each depth
            List<SummaryJobNodeModel> summaryjobnodemodelList=dm.getListOfJobs();
            
            
            for(int jindex=0;jindex<jobList.size();jindex++){
                
                /* }
                
                for (Iterator<JobStepType0Model> iterator1 = jobList.iterator(); iterator1.hasNext();) {*/
               // JobStepType0Model job = iterator1.next();
               JobStepType0Model job=jobList.get(jindex);
               SummaryJobNodeModel summaryJobNodeModel=new SummaryJobNodeModel();
               summaryJobNodeModel.setJobsteptype0model(job);
               
               
               
               final int jobindex=jindex;
                TableColumn<SummarySequenceModel,SummaryJobNodeModel> jtc=new TableColumn(" "+job.getJobStepText()+" ");       //for each job 
                
                jtc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, SummaryJobNodeModel>, ObservableValue<SummaryJobNodeModel>>() {
                    @Override
                    public ObservableValue<SummaryJobNodeModel> call(TableColumn.CellDataFeatures<SummarySequenceModel, SummaryJobNodeModel> param) {
                       return (ObservableValue<SummaryJobNodeModel>) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex);
                       // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).joblistProperty().valueAt(jobindex);
                    }
                });
                
                Long jtype=job.getType();
                
                
                List<VolumeSelectionModelType0> vomodList=job.getVolList();
                List<TableColumn<SummarySequenceModel,SummaryVolumeNodeModel>> volsForJob=new ArrayList<>();               //a list for vols for each job
                List<SummaryVolumeNodeModel> summaryVolumeNodeModelList=summaryJobNodeModel.getListOfVolumes();
                
                for(int vindex=0;vindex<vomodList.size();vindex++){
                    
                    /*}
                    for (Iterator<VolumeSelectionModel> iterator2 = vomodList.iterator(); iterator2.hasNext();) {*/
                    //VolumeSelectionModel vol = iterator2.next();
                    VolumeSelectionModelType0 vol=vomodList.get(vindex);
                    
                   // VolumeSelectionModelType1 vol = vomodList.get(vindex);
                    SummaryVolumeNodeModel summaryVolumeNodeModel=new SummaryVolumeNodeModel();
                    summaryVolumeNodeModel.setVolumeSelectionModel(vol,depindex,jindex,vindex);
                    
                    
                            
                    final int volindex=vindex;
                    TableColumn<SummarySequenceModel,SummaryVolumeNodeModel> vtc=new TableColumn(vol.getLabel().length()<10?vol.getLabel():vol.getLabel().substring(0, 10));     //for each volume
                    vtc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, SummaryVolumeNodeModel>, ObservableValue<SummaryVolumeNodeModel>>() {
                        @Override
                        public ObservableValue<SummaryVolumeNodeModel> call(TableColumn.CellDataFeatures<SummarySequenceModel, SummaryVolumeNodeModel> param) {
                            return (ObservableValue<SummaryVolumeNodeModel>) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex);
                        }
                    });
                    
                    HeadersModel h=vol.getHeadersModel();
                    
                    ObservableList<SequenceHeaders> seqs=h.getSequenceListInHeaders();
                     //mapDepthModelSeq.put(dm, seqs);
                  //  List<DepthModel>
                  for (Iterator<SequenceHeaders> iterator1 = seqs.iterator(); iterator1.hasNext();) {
                  SequenceHeaders seq = iterator1.next();
                  mapDepthModelSeq.put(dm, seq.getSequenceNumber());
                  
                  
                  
                  }
                    
                    Label runl=new Label("Run");
                    Label depl=new Label("Dependency");
                    Label insl=new Label("Insight Version");
                    Label wfl=new Label("Worflow Version");
                    Label qcl=new Label("Qc");
                    Label dbtl=new Label("Doubt");
                    /* runl.setRotate(-90);
                    depl.setRotate(-90);
                    insl.setRotate(-90);
                    wfl.setRotate(-90);
                    qcl.setRotate(-90);*/
                    
                    VBox vboxd=new VBox(depl);
                    vboxd.setRotate(-90);
                    vboxd.setPadding(new Insets(5,5,5,5));
                    Group grpd=new Group(vboxd);
                    
                    VBox vboxr=new VBox(runl);
                    vboxr.setRotate(-90);
                    vboxr.setPadding(new Insets(5,5,5,5));
                    Group grpr=new Group(vboxr);
                    
                    VBox vboxw=new VBox(wfl);
                    vboxw.setRotate(-90);
                    vboxw.setPadding(new Insets(5,5,5,5));
                    Group grpw=new Group(vboxw);
                    
                    VBox vboxi=new VBox(insl);
                    vboxi.setRotate(-90);
                    vboxi.setPadding(new Insets(5,5,5,5));
                    Group grpi=new Group(vboxi);
                    
                    VBox vboxq=new VBox(qcl);
                    vboxq.setRotate(-90);
                    vboxq.setPadding(new Insets(5,5,5,5));
                    Group grpq=new Group(vboxq);
                    
                    VBox vboxdbt=new VBox(dbtl);
                    vboxdbt.setRotate(-90);
                    vboxdbt.setPadding(new Insets(5,5,5,5));
                    Group grpdbt=new Group(vboxdbt);
                    
                    /*TableColumn<SummarySequenceModel,String> run=new TableColumn<>("Run");
                    TableColumn<SummarySequenceModel,String> dep=new TableColumn<>("Dependency");
                    TableColumn<SummarySequenceModel,Boolean> ins=new TableColumn<>("InsightVersion");
                    TableColumn<SummarySequenceModel,String> wf=new TableColumn<>("Workflow");
                    TableColumn<SummarySequenceModel,String > qc=new TableColumn<>("QC");
                    */
                    
                    TableColumn<SummarySequenceModel,String> run=new TableColumn<>(""); run.setGraphic(grpr);
                    TableColumn<SummarySequenceModel,String> dep=new TableColumn<>(""); dep.setGraphic(grpd);
                    TableColumn<SummarySequenceModel,String> ins=new TableColumn<>(""); ins.setGraphic(grpi);
                    TableColumn<SummarySequenceModel,String> wf=new TableColumn<>(""); wf.setGraphic(grpw);
                    TableColumn<SummarySequenceModel,String > qc=new TableColumn<>(""); qc.setGraphic(grpq);
                    TableColumn<SummarySequenceModel,String > dbt=new TableColumn<>(""); dbt.setGraphic(grpdbt);
                    System.out.println("fend.summary.SummaryController.setModel()");
                    
                    dbt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().dependencyProperty();
                            //return  param.getValue().dependencyProperty();
                           
                             //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                             
                             
                             
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                              /*
                             Vol type 1L : 2dPRocess..etc
                             Start
                             */
                             if(type.equals(1L)){
                                  try{
                                     
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                      JobStepType1Model job1=(JobStepType1Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                             
                               if(ss==null){
                                  // return param.getValue().notApplicableDependencyProperty();
                                  return null;
                               }
                               else{
                                   String dep=new String();
                                   Boolean Pf=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model().getPendingFlagProperty().getValue();
                                   //Boolean Qf=ss.getQcAlert();
                                   Boolean Qf=ss.getDoubt().isDoubt();
                                   String dbtStat=ss.getDoubt().getStatus();
                                   Boolean isParent=ss.getDoubt().isParent(job1);
                                  Boolean isChild=ss.getDoubt().isChild(job1);
                                 //  System.out.println("fend.summary.SummaryController.setModel().dbt.setCellValueFactory().call() node: "+job1.getJobStepText()+" sub: "+ss.getSubsurface()+" Parent: "+isParent+" Child: "+isChild);
                                   System.out.println("fend.summary.SummaryController.setModel().dbt.setCellValueFactory().call(): "+job1.getJobStepText()+" sub: "+ss.getSubsurface()+"dStat: "+dbtStat +" isChild: "+isChild+" QF: "+Qf);
                                    List<String> dtypes=ss.getDoubt().getDoubtTypes();
                                    /*for(Iterator<String> iterator1 = dtypes.iterator(); iterator1.hasNext();) {
                                    String next = iterator1.next();
                                    System.out.println("fend.summary.SummaryController.setModel().dbt.setCellValueFactory().call(): "+job1.getJobStepText()+" sub: "+ss.getSubsurface()+"dttype: "+next);
                                    
                                    }*/
                                 
                                  
                                  if(Qf && isChild && dbtStat.equals("Y")){
                                  dep="Y";
                                  }else if(Qf && isChild && dbtStat.equals("O")){
                                   dep="O"   ;
                                  }
                                  else{
                                  dep="OK";
                                  }
                                  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty().set(dep);
                                  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                            
                            
                        }
                              /*
                             Vol type 1L : denoise..etc
                             End
                             */
                             
                             /*
                             Vol type 2L :segdLoad
                             Start
                             */
                             if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                   JobStepType2Model job2=(JobStepType2Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                               if(ss==null){
                                 //  return param.getValue().notApplicableDependencyProperty();
                                 return null;
                               }
                               else{
                                   String dep=new String();
                                   Boolean Pf=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model().getPendingFlagProperty().getValue();
                                   //Boolean Qf=ss.getQcAlert();
                                   Boolean Qf=ss.getDoubt().isDoubt();
                                   String dbtStat=ss.getDoubt().getStatus();
                                   Boolean isParent=ss.getDoubt().isParent(job2);
                                  Boolean isChild=ss.getDoubt().isChild(job2);
                                  System.out.println("fend.summary.SummaryController.setModel().dbt.setCellValueFactory().call(): "+job2.getJobStepText()+" sub: "+ss.getSubsurface()+"dStat: "+dbtStat +" isChild: "+isChild+" QF: "+Qf);
                                 
                                   if(Qf && isChild && dbtStat.equals("Y")){
                                  dep="Y";
                                  }else if(Qf && isChild && dbtStat.equals("O")){
                                   dep="O"   ;
                                  }
                                  else{
                                  dep="OK";
                                  }
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty().set(dep);
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                            
                            
                        }
                             /*
                             Vol type 2L : segdLoad
                             End
                             */
                             
                              /*
                             Vol type 3L : acq
                             Start
                             */
                             if(type.equals(3L)){
                             
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getDependency();
                             }
                            
                            
                            return null;
                        }
                    });
                    
                    
                    
                    
                  //The values that the columns read  
                    run.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().runProperty();
                           try{
                             
                               Long type= param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Vol type 1L : denoise..etc
                               Start
                             */
                               
                               if(type.equals(1L)){
                                   VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                   SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                   if(ss==null){
                                       return null;
                                   }else{
                                       return vol1.logstatusMapForSeqProperty().valueAt(param.getValue().getSeq());
                                   }
                                   
                               }
                               
                              /*
                             Vol type 1L : denoise..etc
                             End
                             */
                             
                             /*
                             Vol type 2L :segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                  SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                   if(ss==null){
                                       return null;
                                   }else{
                                       return vol2.logstatusMapForSeqProperty().valueAt(param.getValue().getSeq());
                                   }
                               }
                               /*
                             Vol type 2L : segdLoad
                             End
                             */
                             
                              /*
                             Vol type 3L : acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                   return acq.getRunStatus();
                               }
                               
                                 /*
                             Vol type 3L : acq
                             end
                             */
                               
                           }catch(ArrayIndexOutOfBoundsException ae){
                               return param.getValue().notApplicableProperty();
                           }catch(IndexOutOfBoundsException ie){
                               System.out.println("fend.summary.SummaryController.setModel().call(): looking for volindex: "+volindex+" in job: "+jobindex+" depth: "+depindex);
                                return param.getValue().notApplicableProperty();
                           }
                           
                         // return  param.getValue().runProperty();
                        return null;
                        }
                    });
 
                    dep.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().dependencyProperty();
                            //return  param.getValue().dependencyProperty();
                           
                             //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                             
                             
                             
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                              /*
                             Vol type 1L : 2dPRocess..etc
                             Start
                             */
                             if(type.equals(1L)){
                                  try{
                                     
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                      JobStepType1Model job1=(JobStepType1Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                             
                               if(ss==null){
                                 //  return param.getValue().notApplicableDependencyProperty();
                                 return null;
                               }
                               else{
                                   String dep=new String();
                                   Boolean Pf=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model().getPendingFlagProperty().getValue();
                                   //Boolean Qf=ss.getQcAlert();
                                   Boolean Qf=ss.getDoubt().isDoubt();
                                   Boolean isParent=ss.getDoubt().isParent(job1);
                                  Boolean isChild=ss.getDoubt().isChild(job1);
                                 //  System.out.println("ParentChild.call() node: "+job1.getJobStepText()+" sub: "+ss.getSubsurface()+" Parent: "+isParent+" Child: "+isChild);
                                  
                                  List<String> dtypes=ss.getDoubt().getDoubtTypes();
                                  for(Iterator<String> iterator1 = dtypes.iterator(); iterator1.hasNext();) {
                                       String next = iterator1.next();
                                   //     System.out.println(".call(): "+next);
                                       
                                   }
                                  if(isChild && dtypes.contains(Doubt.doubtTime)){
                                      dep="Time";
                                  }
                                  if(isChild && dtypes.contains(Doubt.doubtTraces)){
                                      dep+="Traces";
                                  }
                                  if(isChild && !dtypes.contains(Doubt.doubtTime) && !dtypes.contains(Doubt.doubtTraces)){
                                      dep="OK";
                                  }
                                  if(isParent){
                                      dep="OK";
                                  }
                                  // System.out.println("fend.summary.SummaryController.setModel().call(): "+ss.getSequenceNumber()+" doubt: "+Qf);
                                   
                                  /* if(Pf){
                                  dep="";
                                  }
                                  if(Qf){
                                  dep="Q";
                                  }
                                  /*if(Pf && Qf){
                                  dep="Q";
                                  }*/
                                  /*
                                  if(!Qf){
                                      dep="OK";
                                  }*/
                                  
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty().set(dep);
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                            
                            
                        }
                              /*
                             Vol type 1L : denoise..etc
                             End
                             */
                             
                             /*
                             Vol type 2L :segdLoad
                             Start
                             */
                             if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                                   JobStepType2Model job2=(JobStepType2Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                               if(ss==null){
                                  // return param.getValue().notApplicableDependencyProperty();
                                  return null;
                               }
                               else{
                                   String dep=new String();
                                   Boolean Pf=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model().getPendingFlagProperty().getValue();
                                   //Boolean Qf=ss.getQcAlert();
                                   Boolean Qf=ss.getDoubt().isDoubt();
                                   Boolean isParent=ss.getDoubt().isParent(job2);
                                  Boolean isChild=ss.getDoubt().isChild(job2);
                                  
                                  
                                    List<String> dtypes=ss.getDoubt().getDoubtTypes();
                                    /*for(Iterator<String> iterator1 = dtypes.iterator(); iterator1.hasNext();) {
                                    String next = iterator1.next();
                                    System.out.println(".call(): "+next);
                                    
                                    }*/
                                  if(isChild && dtypes.contains(Doubt.doubtTime)){
                                      dep="Time";
                                  }
                                  if(isChild && dtypes.contains(Doubt.doubtTraces)){
                                      dep+="Traces";
                                  }
                                  if(isChild && !dtypes.contains(Doubt.doubtTime) && !dtypes.contains(Doubt.doubtTraces)){
                                      dep="OK";
                                  }
                                  if(isParent){
                                      dep="OK";
                                  }
                                   //System.out.println("fend.summary.SummaryController.setModel().call(): "+ss.getSequenceNumber()+" doubt: "+Qf);
                                   /*
                                   if(Pf){
                                       dep="";
                                   }
                                   if(Qf){
                                       dep="Q";
                                   }
                                   /*if(Pf && Qf){
                                   dep="Q";
                                   }*/
                                   /*
                                   if(!Qf){
                                       dep="OK";
                                   }*/
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty().set(dep);
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                            
                            
                        }
                             /*
                             Vol type 2L : segdLoad
                             End
                             */
                             
                              /*
                             Vol type 3L : acq
                             Start
                             */
                             if(type.equals(3L)){
                             
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getDependency();
                             }
                            
                            
                            return null;
                        }
                    });

                    // working block for Boolean insight column
                    
                    
                    
                    
                  /*  <--UNCOMMENT THIS    ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Boolean>, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SummarySequenceModel, Boolean> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().insProperty();
                            // return  param.getValue().insProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).insightFlagProperty();
                            
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                              /*
                             Vol type 1L : denoise
                             Start
                             */
                             
                          /*  <--UNCOMMENT THIS     if(type.equals(1L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   return param.getValue().notApplicableBooleanProperty(); 
                               }
                               else{
                                   String inss=ss.getInsightVersion();
                                   /*Boolean insf=ss.insightFlagProperty().getValue();
                                   
                                   if(insf){
                                   inss="FAIL";
                                   }else{
                                   inss="PASS";
                                   }*/
                                   
                        /*  <--UNCOMMENT THIS          Boolean insightflag=false;
                                   if(inss.equals(">1")) insightflag=false;
                                   else insightflag=true;
                                   
                                   //param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insf);
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insightflag);
                                   System.out.println("fend.summary.SummaryController.setModel().call(): returning: "+param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty().get());
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                                   //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq()).insightFlagProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableBooleanProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableBooleanProperty();
                           }
                        }
                             
                               /*
                             Vol type 1L : denoise
                             End
                             */
                             
                             /*
                             Vol type 2L : segdLoad
                             Start
                             
                             */
                 /*        <--UNCOMMENT THIS       if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                /*      <--UNCOMMENT THIS         SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   return param.getValue().notApplicableBooleanProperty(); 
                               }
                               else{
                                   String inss=new String("");
                                   Boolean insf=ss.insightFlagProperty().getValue();
                                   if(insf){
                                       inss="FAIL";
                                   }else{
                                       inss="PASS";
                                   }
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insf);
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                                   //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq()).insightFlagProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableBooleanProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableBooleanProperty();
                           }
                        }
                             /*
                             Vol type 2L : segdLoad
                             End
                             */
                             
                              /*
                             Vol type 3L : acq
                             Start
                             */
                             
                 /*    <--UNCOMMENT THIS           if(type.equals(3L)){
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getInsight();
                             }
                              /*
                             Vol type 3L : acq
                             End
                             */
                             
                   /* <--UNCOMMENT THIS            return null;
                        }
                    });
                    */
                    
                    //End of working block for Boolean insight column
                    
                    
                       ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().insProperty();
                            // return  param.getValue().insProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).insightFlagProperty();
                            
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                              /*
                             Vol type 1L : denoise
                             Start
                             */
                             
                            if(type.equals(1L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   //return param.getValue().notApplicableProperty();
                                   return null;
                               }
                               else{
                                   String inss=ss.getInsightVersion();
                                   /*Boolean insf=ss.insightFlagProperty().getValue();
                                   
                                   if(insf){
                                   inss="FAIL";
                                   }else{
                                   inss="PASS";
                                   }*/
                                   
                               //Boolean insightflag=false;
                                 //  if(inss.equals(">1")) insightflag=false;
                                 //  else insightflag=true;
                                   
                                   //param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insf);
                                   //param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insightflag);
                                    param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setInsightString(inss);
                                   System.out.println("fend.summary.SummaryController.setModel().call(): returning: "+param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insightStringProperty().get());
                                  // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                                  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insightStringProperty();
                                   //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq()).insightFlagProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                             
                               /*
                             Vol type 1L : denoise
                             End
                             */
                             
                             /*
                             Vol type 2L : segdLoad
                             Start
                             
                             */
                      if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                       SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   //return param.getValue().notApplicableProperty(); 
                                   return null;
                               }
                               else{
                                  // String inss=new String("");
                                   /*Boolean insf=ss.insightFlagProperty().getValue();
                                   if(insf){
                                   inss="FAIL";
                                   }else{
                                   inss="PASS";
                                   }*/
                                   
                                   String inss=ss.getInsightVersion();
                                   //param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setIns(insf);
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).setInsightString(inss);
                                  // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                                   //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq()).insightFlagProperty();
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insightStringProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                             /*
                             Vol type 2L : segdLoad
                             End
                             */
                             
                              /*
                             Vol type 3L : acq
                             Start
                             */
                             
                           if(type.equals(3L)){
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getInsightString();
                             }
                              /*
                             Vol type 3L : acq
                             End
                             */
                             
                              return null;
                        }
                    });
                    
                   
                   
                   
                   
                   
                    wf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().wfversionProperty().asObject();
                           // return  param.getValue().wfversionProperty().asObject();
                          //  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).workflowVersionProperty().asObject();
                          Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             
                          /*
                             Vol type 1L: denoise
                             Start
                             */
                          
                          if(type.equals(1L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   //return param.getValue().notApplicableProperty(); 
                                   return null;
                               }
                               else{
                                   return  ss.workflowSeqPropertyProperty();
                               }
                           }catch(ArrayIndexOutOfBoundsException ae){
                               return param.getValue().notApplicableProperty();
                           }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                           
                        }
                          /*
                             Vol type 1L: denoise
                             End
                             */
                          
                             /*
                             Vol type 2L: segdload
                             Start
                             */
                             if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   //return param.getValue().notApplicableProperty(); 
                                   return null;
                               }
                               else{
                                   return  ss.workflowSeqPropertyProperty();
                               }
                           }catch(ArrayIndexOutOfBoundsException ae){
                               return param.getValue().notApplicableProperty();
                           }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                           
                        }
                             /*
                             Vol type 2L: segdload
                             End
                             */
                             
                             
                             /*
                             Vol type 3L: acq
                             Start
                             */
                             
                             
                             if(type.equals(3L)){
                                  AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getWorkflowVersion();
                             
                             }
                             
                              /*
                             Vol type 3L: acq
                            End
                             */
                             
                             
                        return null;
                        }
                    });
                    
                    qc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().qcflagProperty();
                             //return  param.getValue().qcflagProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).qcStatusProperty();
                            Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                            
                            if(type.equals(1L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            JobStepType1Model job1=(JobStepType1Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                            if(ss==null){
                                   //return param.getValue().notApplicableProperty(); 
                                   return null;
                               }
                               else{
                                /* String dep=new String();
                                List<String> dtypes=ss.getDoubt().getDoubtTypes();
                                
                                for (Iterator<String> iterator1 = dtypes.iterator(); iterator1.hasNext();) {
                                String next = iterator1.next();
                                System.out.println("QC.call(): "+next);
                                
                                }
                                if(dtypes.contains(Doubt.doubtQc)){
                                dep="QC";
                                }
                                
                                else{
                                dep="OK";
                                }
                                */
                                  
                                 // param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).qcflagProperty().set(dep);
                                  // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).qcflagProperty();
                                  System.out.println("ParentChild.call() node: "+job1.getJobStepText()+" sub: "+ss.getSubsurface()+" QC.call(): "+ss.qcStatusProperty().get());
                                   return  ss.qcStatusProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                            /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                             if(type.equals(2L)){
                                  try{
                                 VolumeSelectionModelType2 vol2=(VolumeSelectionModelType2) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            JobStepType2Model job2=(JobStepType2Model) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model();
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol2.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                            if(ss==null){
                                   //return param.getValue().notApplicableProperty(); 
                                   return null;
                               }
                               else{
                                /*String dep=new String();
                                List<String> dtypes=ss.getDoubt().getDoubtTypes();
                                if(dtypes.contains(Doubt.doubtQc)){
                                dep="QC";
                                }
                                
                                else{
                                dep="OK";
                                }
                                
                                
                                param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).qcflagProperty().set(dep);
                                return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).qcflagProperty();*/
                                System.out.println("ParentChild.call() node: "+job2.getJobStepText()+" sub: "+ss.getSubsurface()+" QC.call(): "+ss.qcStatusProperty().get());
                                   return  ss.qcStatusProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                             /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                             else{
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getQcStatus();
                             }
                              /*
                             Volume type 3L: acq
                             End
                             */
                     }
                    });
                    
                    
                    dbt.setCellFactory(ee->{
                        TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(empty||item==null){
                                     //  setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   if(item.equals("OK")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:green");
                                   }
                                    if(item.equals("Y")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:purple");
                                   }
                                    if(item.equals("O")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:pink");
                                   }
                                    
                                     
                                   }
                               }
                           };
                        
                        cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println("fend.summary.SummaryController.setModel().call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                               
                               if(type.equals(1L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   
                              VolumeSelectionModelType2 vmod2=(VolumeSelectionModelType2) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod2.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod2.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   System.out.println("fend.summary.SummaryController.setModel().call():  AcquisitionModel implementation pending");
                               }
                            /*
                             Volume type 3L: acq
                             End
                             */
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                           
                        
                        
                        
                        
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                        
                    });
                    
                    
                    
                    dep.setCellFactory(ee->{
                        TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(empty||item==null){
                                     //  setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   if(item.equals("OK")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:green");
                                   }
                                    if(item.equals("Time")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:orange");
                                   }
                                    if(item.equals("Traces")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:purple");
                                   }
                                    if(item.equals("TimeTraces")){
                                      // setText(item.toString());
                                       setStyle("-fx-background-color:blue");
                                   }
                                     
                                   }
                               }
                           };
                        
                        cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println("fend.summary.SummaryController.setModel().call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                               
                               if(type.equals(1L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   
                              VolumeSelectionModelType2 vmod2=(VolumeSelectionModelType2) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod2.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod2.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   System.out.println("fend.summary.SummaryController.setModel().call():  AcquisitionModel implementation pending");
                               }
                            /*
                             Volume type 3L: acq
                             End
                             */
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                           
                        
                        
                        
                        
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                        
                    });
                    
                    
                    
                    
                    
                    //The context menus for each column
                    run.setCellFactory(new Callback<TableColumn<SummarySequenceModel, String>, TableCell<SummarySequenceModel, String>>() {
                        @Override
                        public TableCell<SummarySequenceModel, String> call(TableColumn<SummarySequenceModel, String> col) {
                           TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(item==null||empty){
                                      // setText("");
                                       setStyle("");
                                   }
                                   else{
                                       
                                   
                                   if(item.equals("Success")){
                                     //  setText(item);
                                       setStyle("-fx-background-color:green");;
                                   }
                                    if(item.equals("Running")){
                                     //  setText(item);
                                       setStyle("-fx-background-color:orange");;
                                   }
                                     if(item.equals("Cancelled")){
                                      // setText(item);
                                       setStyle("-fx-background-color:yellow");;
                                   }
                                      if(item.equals("Errored")){
                                     //  setText(item);
                                       setStyle("-fx-background-color:red");;
                                   }
                                      if(item.equals("")){
                                          setStyle("");
                                      }
                                   }
                               }
                           };
                           cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println("fend.summary.SummaryController.setModel().call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                               
                               if(type.equals(1L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   
                              VolumeSelectionModelType2 vmod2=(VolumeSelectionModelType2) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod2.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod2.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   System.out.println("fend.summary.SummaryController.setModel().call():  AcquisitionModel implementation pending");
                               }
                            /*
                             Volume type 3L: acq
                             End
                             */
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                           
                           
                           
                           
                           
                                   
                           return cell;
                        }
                    });
                    
                    //Working block for insight Boolean
                    
                    
                /*    ins.setCellFactory(e->{
                        TableCell<SummarySequenceModel,Boolean> cell=new TableCell<SummarySequenceModel,Boolean>(){
                               @Override
                               protected void updateItem(Boolean item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(empty||item==null){
                                     //  setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   if(item){
                                       setText(item.toString());
                                       setStyle("-fx-background-color:green");;
                                   }
                                    if(!item){
                                       setText(item.toString());
                                       setStyle("-fx-background-color:red");;
                                   }
                                     
                                   }
                               }
                           };
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                    });
                    */
                
                
                ins.setCellFactory(e->{
                        TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(item==null||empty){
                                     //  setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                  // String insfromSeq=((SummarySequenceModel)getTableRow().getItem()).getSequence().getInsightVersion();
                                  List<String> insightVersionsDeclaredInJob=job.getInsightVersionsModel().getCheckedVersions();
                                  if(item.equals(">1")){
                                       setText(">1 Versions used");
                                       setStyle("-fx-background-color:red");;
                                   } 
                                  else if(insightVersionsDeclaredInJob.contains(item)){
                                       
                                       setText(item.toString());
                                       setStyle("-fx-background-color:green");;
                                   }
                                  else if(!insightVersionsDeclaredInJob.contains(item)){
                                       
                                       setText(item.toString());
                                       setStyle("-fx-background-color:orange");;
                                   }
                                    
                                     
                                   }
                               }
                           };
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                    });
                    
                    wf.setCellFactory(ee->{
                        TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(item==null||empty){
                                      // setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   if(item.equals(">1")){
                                     //  setText(item);
                                       setStyle("-fx-background-color:orange");
                                   }
                                    else{
                                      // setText(item);
                                       setStyle("-fx-background-color:green");
                                   }
                                     
                                   }
                               }
                           };
                        
                         cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println("fend.summary.SummaryController.setModel().call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                               
                               if(type.equals(1L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   
                              VolumeSelectionModelType2 vmod2=(VolumeSelectionModelType2) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod2.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod2.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   System.out.println("fend.summary.SummaryController.setModel().call():  AcquisitionModel implementation pending");
                               }
                            /*
                             Volume type 3L: acq
                             End
                             */
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                           
                        
                        
                        
                        
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                        
                    });
                    
                    qc.setCellFactory(ee->{
                        TableCell<SummarySequenceModel,String> cell=new TableCell<SummarySequenceModel,String>(){
                               @Override
                               protected void updateItem(String item,boolean empty){
                                   super.updateItem(item, empty);
                                   if(item==null||empty){
                                      // setText("Empty");
                                       setStyle("");
                                   }
                                   else{
                                   
                                   if(item.equals("OK")){
                                       //setText(item);
                                       setStyle("-fx-background-color:green");
                                   }
                                    if(item.equals("QC")){
                                       //setText(item);
                                       setStyle("-fx-background-color:orange");
                                   }
                                     
                                   }
                               }
                           };
                        
                         cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println("fend.summary.SummaryController.setModel().call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                                /*
                             Volume type 1L: denoise
                             Start
                             */ 
                            
                               
                               if(type.equals(1L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 1L: denoise
                             End
                             */ 
                             
                             /*
                             Volume type 2L: segdLoad
                             Start
                             */
                               if(type.equals(2L)){
                                   
                              VolumeSelectionModelType2 vmod2=(VolumeSelectionModelType2) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod2.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod2.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               /*
                             Volume type 2L: segdLoad
                             End
                             */
                             
                              /*
                             Volume type 3L: acq
                             Start
                             */
                               
                               if(type.equals(3L)){
                                   System.out.println("fend.summary.SummaryController.setModel().call():  AcquisitionModel implementation pending");
                               }
                            /*
                             Volume type 3L: acq
                             End
                             */
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                           
                        
                        
                        
                        
                        //cell.textProperty().bind(cell.itemProperty());
                        return cell;
                        
                    });
                    
                    vtc.getColumns().addAll(dep,run,wf,ins,qc,dbt);
                    volsForJob.add(vtc);
                    summaryVolumeNodeModelList.add(summaryVolumeNodeModel);
                    
                }
                
                jtc.getColumns().addAll(volsForJob);
                jobsForDepth.add(jtc);
                summaryJobNodeModel.setListOfVolumes(summaryVolumeNodeModelList);
                summaryjobnodemodelList.add(summaryJobNodeModel);
            }
           dtc.getColumns().addAll(jobsForDepth);
           depthsForGraph.add(dtc);
           dm.setListOfJobs(summaryjobnodemodelList);
           dmodelList.add(dm);
        }
        tableView.getColumns().add(seqTableColumn);
        tableView.getColumns().add(linenameTableColumn);
        tableView.getColumns().addAll(depthsForGraph);
        createData();
        ObservableList<SummarySequenceModel> tableList=FXCollections.observableArrayList(sumSeqModelSet);
        tableView.setItems(tableList);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        
        /*tableView.setRowFactory(tv->{
        ContextMenu contextMenu=new ContextMenu();
        MenuItem showSequence= new MenuItem("sequence information");
        contextMenu.getItems().add(showSequence);
        TableRow<SummarySequenceModel> row=new TableRow<SummarySequenceModel>(){
        
        @Override
        protected void updateItem(SummarySequenceModel item,boolean empty){
        super.updateItem(item, empty);
        setContextMenu(contextMenu);
        
        }
        };
        return null;
        });*/
    }
    
    void createData(){
        MultiMap<Integer,JobStepType0Model> map=model.getDepthNodeMap();
        Set<Integer> keys=map.keySet();
        
        Set<DepthModel> depthKeys=mapDepthModelSeq.keySet();
        for (Iterator<DepthModel> iterator = depthKeys.iterator(); iterator.hasNext();) {
            DepthModel dd = iterator.next();
            List<Long> seqinDepth=(List<Long>) mapDepthModelSeq.get(dd);
            Set<Long> seqinDepthSet=new HashSet<>(seqinDepth);
            for (Iterator<Long> iterator1 = seqinDepthSet.iterator(); iterator1.hasNext();) {
                Long seqno = iterator1.next();
                mapseqDepthModel.put(seqno, dd);
                
            }
        }
        
        
       // Map<Long,
        
        List<DepthModel> dmodelList=depthListmodel.getListOfDepthModel();
        Set<Long> keyseq=mapseqDepthModel.keySet();
        
        
        List<Sequence> seqFromDb=seqserv.getSequenceList();
        List<RawSeqInfo> rawSeqInfoList=rawSeqServ.getListOfRawSeqInfo();
        
        Map<Long,String> seqLineNameMap=new HashMap<>();
        for (Iterator<Sequence> iterator = seqFromDb.iterator(); iterator.hasNext();) {
            Sequence next = iterator.next();
            //String linename=ovserv.getOrcaViewsForSeq(next).get(0).getSubsurfaceLineNames();
            String linename=rawSeqServ.getRawSeqInfo(next.getSequenceno()).getRealLineName();
            seqLineNameMap.put(next.getSequenceno(), linename);
            
        }
        
        
        for (Iterator<Long> iterator = keyseq.iterator(); iterator.hasNext();) {
            
            Long seqno = iterator.next();
            System.out.println("fend.summary.SummaryController.createData(): got seq: "+seqno);
            SummarySequenceModel seqm=new SummarySequenceModel();
            seqm.setSeq(seqno);
            seqm.setLinename(seqLineNameMap.get(seqno));
            List<DepthModel> deplist=(List<DepthModel>) mapseqDepthModel.get(seqno);
            DepthListModel seqdplistmodel=new DepthListModel();
            seqdplistmodel.setListOfDepthModel(dmodelList);
            seqm.setDepthlist(seqdplistmodel);
           // System.out.println("fend.summary.SummaryController.createData(): Adding: seq: "+seqm.getSequence().getSequenceNumber());
           /*for (Iterator<DepthModel> iterator1 = dmodelList.iterator(); iterator1.hasNext();) {
           DepthModel ddd = iterator1.next();
           List<SummaryJobNodeModel> jlist=ddd.getListOfJobs();
           for (Iterator<SummaryJobNodeModel> iterator2 = jlist.iterator(); iterator2.hasNext();) {
           SummaryJobNodeModel jjj = iterator2.next();
           List<SummaryVolumeNodeModel> vlist=jjj.getListOfVolumes();
           for (Iterator<SummaryVolumeNodeModel> iterator3 = vlist.iterator(); iterator3.hasNext();) {
           SummaryVolumeNodeModel vvv = iterator3.next();
           // System.out.println("\t\t\t\t\t\tseq: "+seqm.getSequence().getSequenceNumber()+" depth: "+ddd.getDepth()+" job: "+jjj.getJobsteptype0model().getJobStepText()+" vol: "+vvv.getVolumeSelectionModel().getLabel());
           
           }
           }
           
           }*/
             sumSeqModelSet.add(seqm);
        }
        
        /*
         List<DepthModel> dmodelList=depthListmodel.getListOfDepthModel();
         
         for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
         Integer depth = iterator.next();                                                            //for each depth
         List<JobStepType0Model> jobList=(List<JobStepType0Model>) map.get(depth);                 //list of jobs at this depth
         for (Iterator<JobStepType0Model> iterator1 = jobList.iterator(); iterator1.hasNext();) {
         JobStepType0Model job = iterator1.next();
         
         List<VolumeSelectionModel> vomodList=job.getVolList();                              //list of volumes for this job
         
         for (Iterator<VolumeSelectionModel> iterator2 = vomodList.iterator(); iterator2.hasNext();) {
         VolumeSelectionModelType1 vol = iterator2.next();
         
         HeadersModel h=vol.getHeadersModel();
         
         ObservableList<Sequences> seqs=h.getSequenceListInHeaders();
         
         
         
         for (Iterator<Sequences> iterator3 = seqs.iterator(); iterator3.hasNext();) {
         SequenceHeaders seq = iterator3.next();
         
         SummarySequenceModel summarySequenceModel=new SummarySequenceModel();
         List<DepthModel> deplist=(List<DepthModel>) mapseqDepthModel.get(seq);
         DepthListModel seqdplistmodel=new DepthListModel();
         seqdplistmodel.setListOfDepthModel(dmodelList);
         summarySequenceModel.setDepthlist(seqdplistmodel);
         summarySequenceModel.setSequence(seq);
             for (Iterator<DepthModel> iterator4 = deplist.iterator(); iterator4.hasNext();) {
                 DepthModel dept = iterator4.next();
                 System.out.println("fend.summary.SummaryController.createData(): job: "+job.getJobStepText()+" vol: "+vol.getLabel()+" seq: "+seq.getSequenceNumber()+" :in depth: "+dept.getDepth());
             }
             for (Iterator<DepthModel> iterator4 = dmodelList.iterator(); iterator4.hasNext();) {
                 DepthModel dept = iterator4.next();
                 System.out.println("fend.summary.SummaryController.createData(): job: "+job.getJobStepText()+" vol: "+vol.getLabel()+" seq: "+seq.getSequenceNumber()+" :in dmodL: "+dept.getDepth());
             }
         sumSeqModelSet.add(summarySequenceModel);
         }
         
         
         
         }
         
         }
         
         } */
  /*
         MultiMap<Sequences,SHolder>  mmap=new MultiValueMap<>();
         
         for (Iterator<DepthModel> iterator = dmodelList.iterator(); iterator.hasNext();) {
            DepthModel depth = iterator.next();
            List<SummaryJobNodeModel> lsjnm=depth.getListOfJobs();
            
             for (Iterator<SummaryJobNodeModel> iterator1 = lsjnm.iterator(); iterator1.hasNext();) {
                 SummaryJobNodeModel sjnm = iterator1.next();
                 List<SummaryVolumeNodeModel> lvnm=sjnm.getListOfVolumes();
                 
                 for (Iterator<SummaryVolumeNodeModel> iterator2 = lvnm.iterator(); iterator2.hasNext();) {
                     SummaryVolumeNodeModel svnm = iterator2.next();
                     VolumeSelectionModelType1 vs=svnm.getVolumeSelectionModel();
                     HeadersModel hsm=vs.getHeadersModel();
                     List<Sequences> seqs=hsm.getSequenceListInHeaders();
                    // ListSHolder listsh=new ListSHolder();
                     for (Iterator<Sequences> iterator3 = seqs.iterator(); iterator3.hasNext();) {
                         SequenceHeaders seq = iterator3.next();
                         SHolder sh=new SHolder();
                         sh.d=depth;
                         sh.j=sjnm;
                         sh.v=svnm;
                         mmap.put(seq, sh);
                     }
                     
                 }
             }
            
        }
         
         
         
         Set<Sequences> keySeqs=mmap.keySet();
         for (Iterator<Sequences> iterator = keySeqs.iterator(); iterator.hasNext();) {
            SequenceHeaders seq = iterator.next();
            List<SHolder> shlist=(List<SHolder>) mmap.get(seq);
            DepthListModel dlist=new DepthListModel();
            List<DepthModel> dmodlist=dlist.getListOfDepthModel();
            
             for (Iterator<SHolder> iterator1 = shlist.iterator(); iterator1.hasNext();) {
                 SHolder shseq = iterator1.next();
                 SummarySequenceModel sm=new SummarySequenceModel();
                 
                 
             }
        }
*/
    }
    
    void setView(SummaryNode sn){
        this.node=sn;
        this.setTitle("Summary");
        this.setOnCloseRequest(event->{removeModelRunstatus();});
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    private void removeModelRunstatus() {
        model.destroyRunStatusThreads();
    }
    
    
    final List<String> depthcolors=Arrays.asList("BLUE","RED");
}

class SHolder{
    DepthModel d;
    SummaryJobNodeModel j;
    SummaryVolumeNodeModel v;
}

class ListSHolder{
    List<SHolder> listSholder=new ArrayList<>();

    public List<SHolder> getListSholder() {
        return listSholder;
    }

    public void setListSholder(List<SHolder> listSholder) {
        this.listSholder = listSholder;
    }
    public void addToListSholder(SHolder s){
        this.listSholder.add(s);
    }
    
}