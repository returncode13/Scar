/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.acquisitionType;

import fend.session.SessionModel;
import fend.session.edges.LinksModel;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.acquisition.AcquisitionVolumeModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
//Acimport fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import midend.doubt.Doubt;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionJobStepModel implements JobStepType0Model{
    
    
    private final Long type=3L;
    private final StringProperty jobStepTextProperty;
    private final ListProperty<AcquisitionVolumeModel>volListProperty;
    
    private ArrayList<JobStepType0Model> jsParents=new ArrayList<>();
    private ArrayList<JobStepType0Model> jsChildren=new ArrayList<>();
    
    
     private Boolean leaf=true;
    private Boolean root=true;
    
    //For debug
    private Long id;
    private SessionModel sessionModel;
    

    private List<LinksModel> listOfLinkModels=new ArrayList<>();
     private InsightVersionsModel insightVersionsModel;
     private AcquisitionVolumeModel volmodel;
     private Doubt doubt;
     
    public AcquisitionJobStepModel(String jobStepText, SessionModel sessionModel) {
        this.jobStepTextProperty = new SimpleStringProperty(jobStepText);
        this.sessionModel = sessionModel;
        jsParents.add(this);
        jsChildren.add(this);
        this.doubt=new Doubt();
        volmodel=new AcquisitionVolumeModel();
        List<AcquisitionVolumeModel> templ=new ArrayList<>();
        templ.add(volmodel);
        ObservableList<AcquisitionVolumeModel> ob=FXCollections.observableArrayList(templ);
        volListProperty=new SimpleListProperty<>(ob);
        //volListProperty.addAll(ob);
                
    }

    public AcquisitionJobStepModel(SessionModel sessionModel) {
        this("Acquisition",sessionModel);
    }
    
    
     public Doubt getDoubt() {
        return doubt;
    }

    public void setDoubt(Doubt doubt) {
        this.doubt = doubt;
    }
    
    
    
    
    

    @Override
    public String getJobStepText() {
        return jobStepTextProperty.get();
    }
    @Override
     public void setJobStepText(String text){
        jobStepTextProperty.set(text);
    }
     
     
    public StringProperty getJobStepTextProperty() {
        return jobStepTextProperty;
    }
    
    
    @Override
    public Long getId() {
         return id;
        
    }
    
    
    @Override
    public void setId(Long idJobStep) {
         this.id=idJobStep;
    }
    
    @Override
    public List<JobStepType0Model> getJsChildren() {
        return jsChildren;
    }

    @Override
    public void addToChildren(JobStepType0Model child) {
         System.out.println("fend.session.node.jobs.JobStepModel.addToChildren()   "+child.getId()+"=="+this.id+ "     :  "+child.getId().equals(this.id));
        if(!child.getId().equals(this.id)){
            System.out.println("JSM: in "+this.getJobStepText()+" :ID: "+this.getId()+" setting child: "+child.getJobStepText()+" :ID: "+child.getId());
            jsChildren.remove(this);
        jsChildren.add(child);
        }
        
        System.out.println("fend.session.node.jobs.JobStepModel.addToChildren(): Children of "+this.getJobStepText()+"  :id: "+this.id);
        for (Iterator<JobStepType0Model> iterator = jsChildren.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println(next.getJobStepText() + " : id: "+next.getId());
            
            List<JobStepType0Model> gchildren=next.getJsChildren();
            System.out.println("children of "+next.getJobStepText());
                for (Iterator<JobStepType0Model> iterator1 = gchildren.iterator(); iterator1.hasNext();) {
                JobStepType0Model next1 = iterator1.next();
                    System.out.println(next1.getJobStepText()+" :id: "+next1.getId());
                
            }
                System.out.println("");
            
        }
    }

    @Override
    public List<LinksModel> getListOfLinkModels() {
         return listOfLinkModels;
    }

    @Override
    public List<JobStepType0Model> getJsParents() {
        return jsParents;
    }

    @Override
    public void addSelfToParent() {
        jsParents.add(this);
    }

    @Override
    public void addSelfToChild() {
        jsChildren.add(this);
    }

   


    @Override
    public void setInsightVersionsModel(InsightVersionsModel ivm) {
        this.insightVersionsModel=ivm;
    }
    /*
    @Override
    public void setVolList(ObservableList<AcquisitionVolumeModel> obv) {
    
    }*/

    @Override
    public void addToParent(JobStepType0Model parent) {
         if(!parent.getId().equals(this.id)){
            System.out.println("JSM: in "+this.getJobStepText()+" setting parent: "+parent.getJobStepText());
            jsParents.remove(this);
        jsParents.add(parent);
        }
        System.out.println("fend.session.node.jobs.JobStepModel.addToParent(): Parents of "+this.getJobStepText());
        for (Iterator<JobStepType0Model> iterator = jsParents.iterator(); iterator.hasNext();) {
            JobStepType0Model next = iterator.next();
            System.out.println(next.getJobStepText());
            
        }
    }

    @Override
    public void addToListOfLinksModel(LinksModel lm) {
         listOfLinkModels.add(lm);
    }

    @Override
    public void setSessionModel(SessionModel smodel) {
        this.sessionModel = smodel;
    }

    @Override
    public SessionModel getSessionModel() {
         return sessionModel;
    }

    @Override
    public BooleanProperty getPendingFlagProperty() {
        return new SimpleBooleanProperty(false);
    }

    @Override
    public BooleanProperty getDependency() {
        return new SimpleBooleanProperty(false);
    }

    @Override
    public InsightVersionsModel getInsightVersionsModel() {
        List<String> temp=new ArrayList<>();
        temp.add("acqVersion");
        InsightVersionsModel insM=new InsightVersionsModel(temp);
        insM.setCheckedVersions(temp);
        return insM;
    }

    @Override
    public Long getType() {
        return this.type;
    }

    @Override
    public ObservableList getVolList() {
        return volListProperty;
    }
    
    @Override
    public void setSubsurfacesInJob(Set<SubSurfaceHeaders> subsInJob) {
        
    }

    @Override
    public void setPendingFlagProperty(Boolean TRUE) {
        
    }

    @Override
    public Set<SubSurfaceHeaders> getSubsurfacesInJob() {
        return new HashSet<>();
    }

    @Override
    public void setDependency(Boolean FALSE) {
        
    }

    public void setVolList(ObservableList<AcquisitionVolumeModel> obva) {
        volListProperty.set(obva);
    }

    
}
