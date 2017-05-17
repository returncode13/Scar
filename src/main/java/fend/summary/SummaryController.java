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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    
    
    @FXML
            TableView tableView;
    
    void setModel(SummaryModel mod){
        this.model=mod;
        MultiMap<Integer,JobStepType0Model> map=model.getDepthNodeMap();
        Set<Integer> keys=map.keySet();
         List<TableColumn> depthsForGraph=new ArrayList<>();
        for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
            Integer depth = iterator.next();
            TableColumn dtc=new TableColumn(" "+depth+" ");
            List<JobStepType0Model> jobList=(List<JobStepType0Model>) map.get(depth);
            List<TableColumn> jobsForDepth=new ArrayList<>();
            for (Iterator<JobStepType0Model> iterator1 = jobList.iterator(); iterator1.hasNext();) {
                JobStepType0Model job = iterator1.next();
                TableColumn jtc=new TableColumn(" "+job.getJobStepText()+" ");
                List<VolumeSelectionModel> vomodList=job.getVolList();
                List<TableColumn> volsForJob=new ArrayList<>();
                for (Iterator<VolumeSelectionModel> iterator2 = vomodList.iterator(); iterator2.hasNext();) {
                    VolumeSelectionModel vol = iterator2.next();
                    TableColumn vtc=new TableColumn(vol.getLabel().substring(0, 10));
                    HeadersModel h=vol.getHeadersModel();
                    
                    ObservableList<Sequences> seqs=h.getSequenceListInHeaders();
                    TableColumn<Sequences,String> run=new TableColumn<>("Run");
                    TableColumn<Sequences,String> dep=new TableColumn<>("Dependency");
                    TableColumn<Sequences,Boolean> ins=new TableColumn<>("InsightVersion");
                    TableColumn<Sequences,Long> wf=new TableColumn<>("Workflow");
                    TableColumn<Sequences,String > qc=new TableColumn<>("QC");
                    
                    run.setCellValueFactory(new PropertyValueFactory<>("runStatus"));
                    dep.setCellValueFactory(new PropertyValueFactory<>("dependencyStatus"));
                    ins.setCellValueFactory(new PropertyValueFactory<>("insightFlag"));
                    wf.setCellValueFactory(new PropertyValueFactory<>("workflowVersion"));
                    qc.setCellValueFactory(new PropertyValueFactory<>("qcStatus"));
                    /*
                    TableView<Sequences> tempTable=new TableView();
                    tempTable.getColumns().addAll(run,dep,wf,ins,qc);
                    tempTable.setItems(seqs);
                    */
                    //vtc.getColumns().add(tempTable);
                    vtc.getColumns().addAll(run,dep,wf,ins,qc);
                    volsForJob.add(vtc);
                    
                }
                jtc.getColumns().addAll(volsForJob);
                jobsForDepth.add(jtc);
            }
           dtc.getColumns().addAll(jobsForDepth);
           depthsForGraph.add(dtc);
            
        }
        
        tableView.getColumns().addAll(depthsForGraph);
    }
    
    
    
    void setView(SummaryNode sn){
        this.node=sn;
        this.setTitle("Summary");
        this.setScene(new Scene(node));
        this.showAndWait();
    }
}
