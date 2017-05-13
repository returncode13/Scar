/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import fend.session.edges.LinksModel;
import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.jobs.type1.JobStepType1Model;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author naila0152
 */
public class SessionModel implements Serializable{
    ObservableList<JobStepType0Model> listOfJobs=FXCollections.observableArrayList();
    //ArrayList<VolumeSelectionModel> listOfVolumes=new ArrayList<>();   // all volume information is held by the JobStepModel.
    ArrayList<LinksModel> listOfLinks=new ArrayList<>();
    ArrayList<JobStepType0Model> jobsToBeDeleted = new ArrayList<>();
    Long Id;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public ObservableList<JobStepType0Model> getListOfJobs() {
        System.out.println("fend.session.SessionModel.getListOfJobs(): size of list: "+listOfJobs.size());
        return listOfJobs;
    }
/*
    public void setListOfJobs(ArrayList<JobStepModel> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }
*/
    public ArrayList<LinksModel> getListOfLinks() {
        return listOfLinks;
    }

    public void setListOfLinks(ArrayList<LinksModel> listOfLinks) {
        this.listOfLinks = listOfLinks;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setListOfJobs(ObservableList<JobStepType0Model> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }
    
    public void removeJobfromSession(JobStepType0Model jobToBeDeleted){
        
        listOfJobs.remove(jobToBeDeleted);
        
        for (Iterator<JobStepType0Model> iterator = listOfJobs.iterator(); iterator.hasNext();) {
            JobStepType0Model job = iterator.next();
            
            job.getJsChildren().remove(jobToBeDeleted);
            job.getJsParents().remove(jobToBeDeleted);
            if(job.getJsParents().size()==0){
                job.addSelfToParent();
            }
            
            if(job.getJsChildren().size()==0){
                job.addSelfToChild();
            }
        }
        List<LinksModel> tempList=new ArrayList<>();
        for (Iterator<LinksModel> iterator = listOfLinks.iterator(); iterator.hasNext();) {
            LinksModel next = iterator.next();
            
            if(next.getChild().getId().equals(jobToBeDeleted.getId()) || next.getParent().getId().equals(jobToBeDeleted.getId())){
                tempList.add(next);
            }
        }
        
        for (Iterator<LinksModel> iterator = tempList.iterator(); iterator.hasNext();) {
            LinksModel next = iterator.next();
            listOfLinks.remove(next);
        }
        
        
         System.out.println("fend.session.SessionModel:  job "+jobToBeDeleted.getJobStepText()+" removed from session model . size of list: "+listOfJobs.size());
    }
    public void addJobToSession(JobStepType0Model jobToBeAdded){
        listOfJobs.add(jobToBeAdded);
        List<LinksModel> llmodel=jobToBeAdded.getListOfLinkModels();
        
        for (Iterator<LinksModel> iterator = llmodel.iterator(); iterator.hasNext();) {
            LinksModel lmod = iterator.next();
            listOfLinks.add(lmod);
            
        }
        System.out.println("fend.session.SessionModel:  job "+jobToBeAdded.getJobStepText()+" added to session model");
    }

    List<JobStepType0Model> getJobsToBeDeleted() {
        return jobsToBeDeleted;
    }

    public void addToDeleteList(JobStepType0Model jmodel) {
        jobsToBeDeleted.add(jmodel);
    }

    

    
    
    
}
