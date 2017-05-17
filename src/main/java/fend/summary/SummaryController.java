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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
    private ObservableSet<SummarySequenceModel> sumSeqModelSet=FXCollections.observableSet();
    private MultiMap<Sequences,DepthModel> mapseqDepthModel=new MultiValueMap<>();
    private MultiMap<DepthModel,Sequences> mapDepthModelSeq=new MultiValueMap<>();                  //inverse map of mapseqDepthModel
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
                    summaryVolumeNodeModel.setVolumeSelectionModel(vol,depindex,jindex,vindex);
                    
                    
                            
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
                     //mapDepthModelSeq.put(dm, seqs);
                  //  List<DepthModel>
                  for (Iterator<Sequences> iterator1 = seqs.iterator(); iterator1.hasNext();) {
                  Sequences seq = iterator1.next();
                  mapDepthModelSeq.put(dm, seq);
                  
                  
                  }
                    
                    
                    TableColumn<SummarySequenceModel,String> run=new TableColumn<>("Run");
                    TableColumn<SummarySequenceModel,String> dep=new TableColumn<>("Dependency");
                    TableColumn<SummarySequenceModel,Boolean> ins=new TableColumn<>("InsightVersion");
                    TableColumn<SummarySequenceModel,Long> wf=new TableColumn<>("Workflow");
                    TableColumn<SummarySequenceModel,String > qc=new TableColumn<>("QC");
                    
                    run.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().runProperty();
                           return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).runProperty();
                         // return  param.getValue().runProperty();
                        }
                    });
                    dep.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().depProperty();
                            //return  param.getValue().depProperty();
                             return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).depProperty();
                        }
                    });
                    ins.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Boolean>, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SummarySequenceModel, Boolean> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().insProperty();
                            // return  param.getValue().insProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).insightFlagProperty();
                             return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).insProperty();
                        }
                    });
                    wf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, Long>, ObservableValue<Long>>() {
                        @Override
                        public ObservableValue<Long> call(TableColumn.CellDataFeatures<SummarySequenceModel, Long> param) {
                           // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().wfversionProperty().asObject();
                           // return  param.getValue().wfversionProperty().asObject();
                          //  return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).workflowVersionProperty().asObject();
                           return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).wfversionProperty().asObject();
                        }
                    });
                    qc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SummarySequenceModel, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<SummarySequenceModel, String> param) {
                            //return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getQcmodel().qcflagProperty();
                             //return  param.getValue().qcflagProperty();
                            // return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).getVolumeSelectionModel().getHeadersModel().getSequenceListInHeaders().get(0).qcStatusProperty();
                             return  param.getValue().getDepthlist().getListOfDepthModel().get(depindex).getListOfJobs().get(jobindex).getListOfVolumes().get(volindex).qcflagProperty();
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
        ObservableList<SummarySequenceModel> tableList=FXCollections.observableArrayList(sumSeqModelSet);
        tableView.setItems(tableList);
    }
    
    void createData(){
        MultiMap<Integer,JobStepType0Model> map=model.getDepthNodeMap();
        Set<Integer> keys=map.keySet();
        
        Set<DepthModel> depthKeys=mapDepthModelSeq.keySet();
        for (Iterator<DepthModel> iterator = depthKeys.iterator(); iterator.hasNext();) {
            DepthModel dd = iterator.next();
            List<Sequences> seqinDepth=(List<Sequences>) mapDepthModelSeq.get(dd);
            for (Iterator<Sequences> iterator1 = seqinDepth.iterator(); iterator1.hasNext();) {
                Sequences seq = iterator1.next();
                mapseqDepthModel.put(seq, dd);
                
            }
        }
        
        
       // Map<Long,
        
        List<DepthModel> dmodelList=depthListmodel.getListOfDepthModel();
        Set<Sequences> keyseq=mapseqDepthModel.keySet();
        for (Iterator<Sequences> iterator = keyseq.iterator(); iterator.hasNext();) {
            Sequences seq = iterator.next();
            SummarySequenceModel seqm=new SummarySequenceModel();
            seqm.setSequence(seq);
            List<DepthModel> deplist=(List<DepthModel>) mapseqDepthModel.get(seq);
            DepthListModel seqdplistmodel=new DepthListModel();
            seqdplistmodel.setListOfDepthModel(dmodelList);
            seqm.setDepthlist(seqdplistmodel);
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
         VolumeSelectionModel vol = iterator2.next();
         
         HeadersModel h=vol.getHeadersModel();
         
         ObservableList<Sequences> seqs=h.getSequenceListInHeaders();
         
         
         
         for (Iterator<Sequences> iterator3 = seqs.iterator(); iterator3.hasNext();) {
         Sequences seq = iterator3.next();
         
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
                     VolumeSelectionModel vs=svnm.getVolumeSelectionModel();
                     HeadersModel hsm=vs.getHeadersModel();
                     List<Sequences> seqs=hsm.getSequenceListInHeaders();
                    // ListSHolder listsh=new ListSHolder();
                     for (Iterator<Sequences> iterator3 = seqs.iterator(); iterator3.hasNext();) {
                         Sequences seq = iterator3.next();
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
            Sequences seq = iterator.next();
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
        this.setScene(new Scene(node));
        this.showAndWait();
    }
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