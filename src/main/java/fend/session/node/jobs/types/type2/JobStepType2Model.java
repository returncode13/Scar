/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type2;

import fend.session.SessionModel;
import fend.session.edges.LinksModel;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type1.JobStepType1Model;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import fend.session.node.volumes.type1.qcTable.QcMatrixModel;
import fend.session.node.volumes.type1.qcTable.QcTableModel;
import fend.session.node.volumes.type1.qcTable.qcCheckBox.qcCheckListModel;
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

/**
 *
 * @author adira0150
 */
public class JobStepType2Model implements JobStepType0Model{
    
    private final Long type=2L;
    private final StringProperty jobStepTextProperty;
    private final ListProperty<VolumeSelectionModelType1>volListProperty;
    private InsightVersionsModel insightVersionsModel;
    
    private BooleanProperty pendingFlagProperty=new SimpleBooleanProperty(Boolean.FALSE);
    private BooleanProperty qcFlagProperty=new SimpleBooleanProperty(Boolean.FALSE);
    
    private ArrayList<JobStepType0Model> jsParents=new ArrayList<>();
    private ArrayList<JobStepType0Model> jsChildren=new ArrayList<>();
    
    private Boolean leaf=true;
    private Boolean root=true;
    
    //For debug
    private Long id;
    
    private Set<SubSurfaceHeaders> subsurfacesInJob=new HashSet<>();
    private SessionModel sessionModel;
    

    private List<LinksModel> listOfLinkModels=new ArrayList<>();
    
    /* public JobStepType1Model(String jobStepText, SessionModel smodel) {
    super(jobStepText, smodel);
    }*/
    

   
    

    public JobStepType2Model(String jobStepText,SessionModel smodel) {
       
        this.jobStepTextProperty = new SimpleStringProperty(jobStepText);
       ObservableList<VolumeSelectionModelType1> obs=FXCollections.observableArrayList();
        this.volListProperty = new SimpleListProperty<>(obs);
        this.sessionModel=smodel;
        
        jsParents.add(this);                                                        //provision for root
        jsChildren.add(this);                                                       //provision for leaf
        
        
           
        
    }

    public InsightVersionsModel getInsightVersionsModel() {
        return insightVersionsModel;
    }

    public void setInsightVersionsModel(InsightVersionsModel insightVersionsModel) {
        this.insightVersionsModel = insightVersionsModel;
    }

    
    

    public JobStepType2Model(SessionModel smodel) {
        this("Enter a name",smodel);
    }

    public ListProperty<VolumeSelectionModelType1> getVolListProperty() {
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
    
    public ObservableList<VolumeSelectionModelType1> getVolList(){
        return volListProperty.get();
    }
    
    public void setVolList(ObservableList<VolumeSelectionModelType1> obsList){
        volListProperty.set(obsList);
    }
 
    
    public void addSelfToParent(){
        jsParents.add(this);
    }
    
    public void addSelfToChild(){
        jsChildren.add(this);
    }
    
    
    
    public void addToParent(JobStepType0Model parent){
       /* if(jsParents.contains(this)) {
            System.out.println("JSM: contains "+this.getJobStepText()+" ..removing from parent");
            jsParents.remove(this);
        }*/
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
    
    
    public void addToChildren(JobStepType0Model child){
       /* if(jsChildren.contains(this)) {
        System.out.println("JSM: contains "+this.getJobStepText()+" ..removing from Children");
            jsChildren.remove(this);
        }*/
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

    public ArrayList<JobStepType0Model> getJsParents() {
        return jsParents;
    }

    public ArrayList<JobStepType0Model> getJsChildren() {
        return jsChildren;
    }

    public void setJsParents(ArrayList<JobStepType0Model> jsParents) {
        this.jsParents = jsParents;
    }

    public void setJsChildren(ArrayList<JobStepType0Model> jsChildren) {
        this.jsChildren = jsChildren;
    }

    public void setId(Long id) {
        this.id=id;
    }
    
    @Override
     public Long getId() {
        return id;
    }

    public Boolean isLeaf() {
        return (jsChildren.isEmpty()? true:false);
        
    }

   

    public Boolean isRoot() {
        return (jsParents.isEmpty()?true:false);
    }

    public BooleanProperty getPendingFlagProperty() {
        return pendingFlagProperty;
    }
    
    public Boolean isPending(){
        return pendingFlagProperty.get();
    }

    public void setPendingFlagProperty(Boolean b) {
        this.pendingFlagProperty.set(b);
    }

    public BooleanProperty getQcFlagProperty() {
        return qcFlagProperty;
    }

    public void setQcFlagProperty(Boolean b) {
        this.qcFlagProperty.set(b);
    }

    public Set<SubSurfaceHeaders> getSubsurfacesInJob() {
        return subsurfacesInJob;
    }

    public void setSubsurfacesInJob(Set<SubSurfaceHeaders> subsurfacesInJob) {
        this.subsurfacesInJob = subsurfacesInJob;
    }

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setSessionModel(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public List<LinksModel> getListOfLinkModels() {
        return listOfLinkModels;
    }

    public void addToListOfLinksModel(LinksModel lm){
        listOfLinkModels.add(lm);
    }
    
    public void removeFromListOfLinksModel(LinksModel lm){
        listOfLinkModels.remove(lm);
    }

   
    public Long getType() {
        return this.type;
    }
    
      
  private  QcTableModel qcTableModel;
    private qcCheckListModel qcCheckListModel;
    
    private QcMatrixModel qcMatrixModel;
    

    public QcMatrixModel getQcMatrixModel() {
        if(qcMatrixModel==null) {
            qcMatrixModel=new QcMatrixModel();
            //qcMatrixModel.setVmodel(this);
            qcMatrixModel.setJobmodel(this);
        }
        return qcMatrixModel;
    }

    public void setQcMatrixModel(QcMatrixModel qcMatrixModel) {
        this.qcMatrixModel = qcMatrixModel;
    }
    
    public QcTableModel getQcTableModel() {
        if(qcTableModel==null){
            qcTableModel=new QcTableModel();
        }
        return qcTableModel;
    }

    public void setQcTableModel(QcTableModel qcMatrixModel) {
        this.qcTableModel = qcMatrixModel;
    }
    
     public qcCheckListModel getQcCheckListModel() {
        if(qcCheckListModel==null){
            qcCheckListModel=new qcCheckListModel();
        }
        return qcCheckListModel;
    }

    public void setQcCheckListModel(qcCheckListModel qcCheckListModel) {
        this.qcCheckListModel = qcCheckListModel;
    }
    
   
}