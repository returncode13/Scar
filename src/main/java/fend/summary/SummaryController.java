/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.HeadersNode;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
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
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author sharath nair
 * 
 */






public class SummaryController extends Stage{
    private SummaryModel model;
    private SummaryNode node;
    private VolumeService vserv=new VolumeServiceImpl();
    private ObservableSet<SummarySequenceModel> sumSeqModelSet=FXCollections.observableSet();
    private MultiMap<Long,DepthModel> mapseqDepthModel=new MultiValueMap<>();
    private MultiMap<DepthModel,Long> mapDepthModelSeq=new MultiValueMap<>();                  //inverse map of mapseqDepthModel
    private DepthListModel depthListmodel;
    
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
                    
                    TableCell<SummarySequenceModel, DepthModel>  cell=new TableCell(){
                        
                        
                        @Override
                        public void updateIndex(int i){
                            super.updateIndex(i);
                            if(i>=0){
                                String color=depthcolors.get(depindex%2);
                                this.setStyle("-fx-background-color: "+color);
                                
                            }
                        }
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
                    
                    
                    TableColumn<SummarySequenceModel,String> run=new TableColumn<>("Run");
                    TableColumn<SummarySequenceModel,String> dep=new TableColumn<>("Dependency");
                    TableColumn<SummarySequenceModel,String> ins=new TableColumn<>("InsightVersion");
                    TableColumn<SummarySequenceModel,String> wf=new TableColumn<>("Workflow");
                    TableColumn<SummarySequenceModel,String > qc=new TableColumn<>("QC");
                  //The values that the columns read  
                    run.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().runProperty();
                           try{
                             
                               Long type= param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                               if(!type.equals(3L)){
                                   VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                   return vol1.logstatusMapForSeqProperty().valueAt(param.getValue().getSeq());
                               }else{
                                   AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                   return acq.getRunStatus();
                               }
                               
                                
                               
                           }catch(ArrayIndexOutOfBoundsException ae){
                               return param.getValue().notApplicableProperty();
                           }catch(IndexOutOfBoundsException ie){
                               System.out.println(".call(): looking for volindex: "+volindex+" in job: "+jobindex+" depth: "+depindex);
                                return param.getValue().notApplicableProperty();
                           }
                           
                         // return  param.getValue().runProperty();
                        }
                    });
                    
                    
                    
                    
                    dep.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().dependencyProperty();
                            //return  param.getValue().dependencyProperty();
                           
                             //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).dependencyProperty();
                             
                             
                             
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             if(!type.equals(3L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                             
                               if(ss==null){
                                   return param.getValue().notApplicableDependencyProperty();
                               }
                               else{
                                   String dep=new String();
                                   Boolean Pf=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getJobsteptype0model().getPendingFlagProperty().getValue();
                                   //Boolean Qf=ss.getQcAlert();
                                   Boolean Qf=ss.getDoubt().isDoubt();
                                   System.out.println(".call(): "+ss.getSequenceNumber()+" doubt: "+Qf);
                                   
                                   if(Pf){
                                       dep="";
                                   }
                                   if(Qf){
                                       dep="Q";
                                   }
                                   /*if(Pf && Qf){
                                   dep="Q";
                                   }*/
                                   if(!Qf){
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
                            
                            
                        }else{
                             
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getDependency();
                             }
                            
                            
                            
                        }
                    });
                    
                    
                    /*dep.setCellFactory(column->{
                    return new TableCell<SummarySequenceModel,String>(){
                    @Override
                    protected void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty || item==null){
                    setText(null);
                    }else{
                    if(item.equalsIgnoreCase("Q"))
                    setText()
                    }
                    
                    }
                    };
                    });*/
                    
                    
                    ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().insProperty();
                            // return  param.getValue().insProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).insightFlagProperty();
                            
                             Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             if(!type.equals(3L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   return param.getValue().notApplicableProperty(); 
                               }
                               else{
                                   String inss=new String("");
                                   Boolean insf=ss.insightFlagProperty().getValue();
                                   if(insf){
                                       inss="FAIL";
                                   }else{
                                       inss="PASS";
                                   }
                                   param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty().setValue(inss);
                                   return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                             else{
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getInsight();
                             }
                             
                        }
                    });
                    wf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().wfversionProperty().asObject();
                           // return  param.getValue().wfversionProperty().asObject();
                          //  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).workflowVersionProperty().asObject();
                          Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             if(!type.equals(3L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                               if(ss==null){
                                   return param.getValue().notApplicableProperty(); 
                               }
                               else{
                                   return  ss.workflowSeqPropertyProperty();
                               }
                           }catch(ArrayIndexOutOfBoundsException ae){
                               return param.getValue().notApplicableProperty();
                           }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                           
                        }else{
                                  AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getWorkflowVersion();
                             
                             }
                             
                        }
                    });
                    qc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().qcflagProperty();
                             //return  param.getValue().qcflagProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).qcStatusProperty();
                            Long type=param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                             if(!type.equals(3L)){
                                  try{
                                 VolumeSelectionModelType1 vol1=(VolumeSelectionModelType1) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                            
                            
                            
                            
                             
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                            SequenceHeaders ss=vol1.getHeadersModel().getSequenceObjBySequenceNumber(param.getValue().getSeq());
                            if(ss==null){
                                   return param.getValue().notApplicableProperty(); 
                               }
                               else{
                                   return  ss.qcStatusProperty();
                               }
                            }catch(ArrayIndexOutOfBoundsException ae){
                                return param.getValue().notApplicableProperty();
                            }catch(IndexOutOfBoundsException ie){
                                return param.getValue().notApplicableProperty();
                           }
                        }
                             else{
                                 AcquisitionVolumeModel acq=(AcquisitionVolumeModel) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                                 return acq.getQcStatus();
                             }
                     }
                    });
                    
                    
                    
                    //The context menus for each column
                    run.setCellFactory(new Callback<TableColumn<SummarySequenceModel, String>, TableCell<SummarySequenceModel, String>>() {
                        @Override
                        public TableCell<SummarySequenceModel, String> call(TableColumn<SummarySequenceModel, String> col) {
                           TableCell<SummarySequenceModel,String> cell=new TableCell<>();
                           cell.textProperty().bind(cell.itemProperty());
                           final ContextMenu contextMenu=new ContextMenu();
                           final MenuItem showSeq=new MenuItem("seq information");
                           showSeq.setOnAction(e->{
                               String cellString=cell.getItem();
                               SummarySequenceModel summarySequenceModel=(SummarySequenceModel) cell.getTableRow().getItem();
                               
                               System.out.println(".call(): seq "+summarySequenceModel.getSeq()+" status: "+cellString);
                               Long type=summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getType();
                               if(!type.equals(3L)){
                                   
                              VolumeSelectionModelType1 vmod1=(VolumeSelectionModelType1) summarySequenceModel.getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel();
                               SequenceHeaders ss=vmod1.getHeadersModel().getSequenceObjBySequenceNumber(summarySequenceModel.getSeq());
                               if(ss==null){
                                   
                               }else{
                                   HeadersModel hmod=vmod1.getHeadersModel();
                                   HeadersNode hnode=new HeadersNode(hmod, (int) summarySequenceModel.getSeq());
                               }
                               }
                               else{
                                   System.out.println(".call():  AcquisitionModel implementation pending");
                               }
                           
                           
                           });
                           contextMenu.getItems().add(showSeq);
                           cell.setContextMenu(contextMenu);
                                   
                           return cell;
                        }
                    });
                    
                    /*for (Iterator<Sequences> iterator1 = seqs.iterator(); iterator1.hasNext();) {
                    SequenceHeaders seq = iterator1.next();
                    
                    SummarySequenceModel summarySequenceModel=new SummarySequenceModel();
                    summarySequenceModel.setDepthlist(value);
                    
                    }*/
                    
                    /*TableColumn<Sequences,String> run=new TableColumn<>("Run");
                    TableColumn<Sequences,String> dep=new TableColumn<>("Dependency");
                    TableColumn<Sequences,Boolean> ins=new TableColumn<>("InsightVersion");
                    TableColumn<Sequences,Long> wf=new TableColumn<>("Workflow");
                    TableColumn<Sequences,String > qc=new TableColumn<>("QC");
                    
                    run.setCellValueFactory(new PropertyValueFactory<>("runStatus"));
                    dep.setCellValueFactory(new PropertyValueFactory<>("dependencyStatus"));
                    ins.setCellValueFactory(new PropertyValueFactory<>("insightFlag"));
                    wf.setCellValueFactory(new PropertyValueFactory<>("workflowVersion"));
                    qc.setCellValueFactory(new PropertyValueFactory<>("qcStatus"));*/
                    /*
                    TableView<Sequences> tempTable=new TableView();
                    tempTable.getColumns().addAll(run,dep,wf,ins,qc);
                    tempTable.setItems(seqs);
                    */
                    //vtc.getColumns().add(tempTable);
                    vtc.getColumns().addAll(dep,run,wf,ins,qc);
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
        
        
        
        
        
        for (Iterator<Long> iterator = keyseq.iterator(); iterator.hasNext();) {
            
            Long seqno = iterator.next();
            System.out.println("fend.summary.SummaryController.createData(): got seq: "+seqno);
            SummarySequenceModel seqm=new SummarySequenceModel();
            seqm.setSeq(seqno);
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
    
    
    final List<String> depthcolors=Arrays.asList("DimGray","DarkGray");
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