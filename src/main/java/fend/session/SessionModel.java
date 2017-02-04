/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session;

import fend.session.edges.LinksModel;
import fend.session.node.jobs.JobStepModel;
import fend.session.node.volumes.VolumeSelectionModel;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author naila0152
 */
public class SessionModel implements Serializable{
    ObservableList<JobStepModel> listOfJobs=FXCollections.observableArrayList();
    //ArrayList<VolumeSelectionModel> listOfVolumes=new ArrayList<>();   // all volume information is held by the JobStepModel.
    ArrayList<LinksModel> listOfLinks=new ArrayList<>();
    Long Id;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public ObservableList<JobStepModel> getListOfJobs() {
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

    public void setListOfJobs(ObservableList<JobStepModel> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }
    
    public void removeJobfromSession(JobStepModel jobToBeDeleted){
        listOfJobs.remove(jobToBeDeleted);
    }

    

    
    
    
}
