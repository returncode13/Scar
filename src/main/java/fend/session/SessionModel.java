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

/**
 *
 * @author naila0152
 */
public class SessionModel implements Serializable{
    ArrayList<JobStepModel> listOfJobs=new ArrayList<>();
    //ArrayList<VolumeSelectionModel> listOfVolumes=new ArrayList<>();   // all volume information is held by the JobStepModel.
    ArrayList<LinksModel> listOfLinks=new ArrayList<>();
    Long Id;
    
    
    
    public ArrayList<JobStepModel> getListOfJobs() {
        return listOfJobs;
    }

    public void setListOfJobs(ArrayList<JobStepModel> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }

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

    
    
    
}
