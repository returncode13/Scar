/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fend.session.node.volumes.VolumeSelectionModel;

/**
 *
 * @author naila0152
 */
public class JobStepModel {
    private final StringProperty jobStepTextProperty;
    private final ListProperty<VolumeSelectionModel>volListProperty;

    
    private ArrayList<JobStepModel> jsParents=new ArrayList<>();
    private ArrayList<JobStepModel> jsChildren=new ArrayList<>();
    
    private Boolean leaf=true;
    private Boolean root=true;
    
    //For debug
    private Long id;

   
    

    public JobStepModel(String jobStepText) {
   
        this.jobStepTextProperty = new SimpleStringProperty(jobStepText);
       ObservableList<VolumeSelectionModel> obs=FXCollections.observableArrayList();
        this.volListProperty = new SimpleListProperty<>(obs);
        
        jsParents.add(this);                                                        //provision for root
        jsChildren.add(this);                                                       //provision for leaf
        
        
       
        
        
    }

    public JobStepModel() {
        this("Enter a name");
    }

    public ListProperty<VolumeSelectionModel> getVolListProperty() {
        return volListProperty;
    }

    public StringProperty getJobStepTextProperty() {
        return jobStepTextProperty;
    }
    
    
    public String getJobStepText(){
        return jobStepTextProperty.get();
    }
    
    public void setJobStepText(String text){
        jobStepTextProperty.set(text);
    }
    
    public ObservableList<VolumeSelectionModel> getVolList(){
        return volListProperty.get();
    }
    
    public void setVolList(ObservableList<VolumeSelectionModel> obsList){
        volListProperty.set(obsList);
    }
 
    
    public void addToParent(JobStepModel parent){
        if(jsParents.contains(this)) {
            System.out.println("JSM: contains "+this.getJobStepText()+" ..removing from parent");
            jsParents.remove(this);
        }
        if(parent!=this){
            System.out.println("JSM: in "+this.getJobStepText()+" setting parent: "+parent.getJobStepText());
        jsParents.add(parent);
        }
        
    }
    
    
    public void addToChildren(JobStepModel child){
        if(jsChildren.contains(this)) {
        System.out.println("JSM: contains "+this.getJobStepText()+" ..removing from Children");
            jsChildren.remove(this);
        }
        if(child!=this){
            System.out.println("JSM: in "+this.getJobStepText()+" setting child: "+child.getJobStepText());
        jsChildren.add(child);
        }
        
    }

    public ArrayList<JobStepModel> getJsParents() {
        return jsParents;
    }

    public ArrayList<JobStepModel> getJsChildren() {
        return jsChildren;
    }

    public void setJsParents(ArrayList<JobStepModel> jsParents) {
        this.jsParents = jsParents;
    }

    public void setJsChildren(ArrayList<JobStepModel> jsChildren) {
        this.jsChildren = jsChildren;
    }

    public void setId(Long id) {
        this.id=id;
    }
    
     public Long getId() {
        return id;
    }

    public Boolean isLeaf() {
        return (jsChildren.isEmpty()? true:false);
        
    }

   

    public Boolean isRoot() {
        return (jsParents.isEmpty()?true:false);
    }

    

     
     
}
