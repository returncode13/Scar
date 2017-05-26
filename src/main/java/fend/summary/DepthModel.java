/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public class DepthModel {

    private final ListProperty<JobNodeListModel> joblist = new SimpleListProperty<>(this,"joblist");
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    
    public ObservableList getJoblist() {
    return joblist.get();
    }
    
    public void setJoblist(ObservableList value) {
    joblist.set(value);
    }
    
    public ListProperty joblistProperty() {
    return joblist;
    }
    /*
    private final ObjectProperty<JobNodeModel> jobnode = new SimpleObjectProperty<>(this,"jobnode");
    
    public SummaryJobNodeModel getJobnode() {
    return jobnode.get();
    }
    
    public void setJobnode(SummaryJobNodeModel value) {
    jobnode.set(value);
    }
    
    public ObjectProperty jobnodeProperty() {
    return jobnode;
    }*/
    
    
    private List<SummaryJobNodeModel> listOfJobs=new ArrayList();

    public List<SummaryJobNodeModel> getListOfJobs() {
        return listOfJobs;
    }

    public void setListOfJobs(List<SummaryJobNodeModel> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }

    public DepthModel() {
    }
    
    
    
}
