/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.Sequences;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author adira0150
 */
public class SummaryController extends Stage{
    private SummaryModel model;
    private SummaryNode node;
    private VolumeService vserv=new VolumeServiceImpl();
    private ObservableList<SummarySequenceModel> sumSeqModelList=FXCollections.observableArrayList();
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
            dtc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, DepthModel>, ObservableValue<DepthModel>>() {
                @Override
                public ObservableValue<DepthModel> call(TableColumn.CellDataFeatures<SummarySequenceModel, DepthModel> param) {
                   // return param.getValue().getDepthlist().depthsProperty().valueAt(depindex);
                    return (ObservableValue<DepthModel>) param.getValue().getDepthlist().getListOfDepthModel().get(depindex);
                   
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
                    }
                });
                
                
                
                List<VolumeSelectionModel> vomodList=job.getVolList();
                List<TableColumn<SummarySequenceModel,SummaryVolumeNodeModel>> volsForJob=new ArrayList<>();               //a list for vols for each job
                List<SummaryVolumeNodeModel> summaryVolumeNodeModelList=summaryJobNodeModel.getListOfVolumes();
                
                for(int vindex=0;vindex<vomodList.size();vindex++){
                    
                    /*}
                    for (Iterator<VolumeSelectionModel> iterator2 = vomodList.iterator(); iterator2.hasNext();) {*/
                    //VolumeSelectionModel vol = iterator2.next();
                    VolumeSelectionModel vol = vomodList.get(vindex);
                    SummaryVolumeNodeModel summaryVolumeNodeModel=new SummaryVolumeNodeModel();
                    summaryVolumeNodeModel.setVolumeSelectionModel(vol);
                    
                    
                            
                    final int volindex=vindex;
                    TableColumn<SummarySequenceModel,SummaryVolumeNodeModel> vtc=new TableColumn(vol.getLabel().substring(0, 10));     //for each volume
                    vtc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, SummaryVolumeNodeModel>, ObservableValue<SummaryVolumeNodeModel>>() {
                        @Override
                        public ObservableValue<SummaryVolumeNodeModel> call(TableColumn.CellDataFeatures<SummarySequenceModel, SummaryVolumeNodeModel> param) {
                            return (ObservableValue<SummaryVolumeNodeModel>) param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex);
                        }
                    });
                    
                    HeadersModel h=vol.getHeadersModel();
                    
                    ObservableList<Sequences> seqs=h.getSequenceListInHeaders();
                    
                    TableColumn<SummarySequenceModel,String> run=new TableColumn<>("Run");
                    TableColumn<SummarySequenceModel,String> dep=new TableColumn<>("Dependency");
                    TableColumn<SummarySequenceModel,Boolean> ins=new TableColumn<>("InsightVersion");
                    TableColumn<SummarySequenceModel,Long> wf=new TableColumn<>("Workflow");
                    TableColumn<SummarySequenceModel,String > qc=new TableColumn<>("QC");
                    
                    run.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                          //  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().runProperty();
                          return  param.getValue().runProperty();
                        }
                    });
                    dep.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().depProperty();
                             return  param.getValue().depProperty();
                        }
                    });
                    ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Boolean>, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SummarySequenceModel, Boolean> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().insProperty();
                             return  param.getValue().insProperty();
                        }
                    });
                    wf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Long>, ObservableValue<Long>>() {
                        @Override
                        public ObservableValue<Long> call(TableColumn.CellDataFeatures<SummarySequenceModel, Long> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().wfversionProperty().asObject();
                            return  param.getValue().wfversionProperty().asObject();
                        }
                    });
                    qc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().qcflagProperty();
                             return  param.getValue().qcflagProperty();
                        }
                    });
                    
                    
                    /*for (Iterator<Sequences> iterator1 = seqs.iterator(); iterator1.hasNext();) {
                    Sequences seq = iterator1.next();
                    
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
                    vtc.getColumns().addAll(run,dep,wf,ins,qc);
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
        
        tableView.setItems(sumSeqModelList);
    }
    
    void createData(){
        MultiMap<Integer,JobStepType0Model> map=model.getDepthNodeMap();
        Set<Integer> keys=map.keySet();
        
         
         List<DepthModel> dmodelList=depthListmodel.getListOfDepthModel();
         
        for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
            Integer depth = iterator.next();                                                            //for each depth
            List<JobStepType0Model> jobList=(List<JobStepType0Model>) map.get(depth);                 //list of jobs at this depth
            
            for(int jindex=0;jindex<jobList.size();jindex++){
               
               JobStepType0Model job=jobList.get(jindex);
               List<VolumeSelectionModel> vomodList=job.getVolList();                              //list of volumes for this job
               
                    for (Iterator<VolumeSelectionModel> iterator2 = vomodList.iterator(); iterator2.hasNext();) {
                    VolumeSelectionModel vol = iterator2.next();
                   
                    HeadersModel h=vol.getHeadersModel();
                    
                    ObservableList<Sequences> seqs=h.getSequenceListInHeaders();
                    
                    
                    
                    for (Iterator<Sequences> iterator1 = seqs.iterator(); iterator1.hasNext();) {
                        Sequences seq = iterator1.next();
                        
                        SummarySequenceModel summarySequenceModel=new SummarySequenceModel();
                        summarySequenceModel.setDepthlist(depthListmodel);
                        summarySequenceModel.setSequence(seq);
                        sumSeqModelList.add(summarySequenceModel);
                    }
                   
                   
                    
                }
                
            }
           
        }
    }
    
    void setView(SummaryNode sn){
        this.node=sn;
        this.setTitle("Summary");
        this.setScene(new Scene(node));
        this.showAndWait();
    }
}
