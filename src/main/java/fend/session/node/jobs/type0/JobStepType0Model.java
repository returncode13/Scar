/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.type0;

import fend.session.SessionModel;
import fend.session.edges.LinksModel;
import fend.session.node.headers.SubSurface;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.type2.JobStepType2Model;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public interface JobStepType0Model {

    public String getJobStepText();
    public Long getId() ;
    public List<JobStepType0Model> getJsChildren();
    public void addToChildren(JobStepType0Model model);
    public List<LinksModel> getListOfLinkModels();
    public List<JobStepType0Model> getJsParents(); 
    public void addSelfToParent();
    public void addSelfToChild(); 
    public void setJobStepText(String nameJobStep);
    public void setId(Long idJobStep);
    public void setInsightVersionsModel(InsightVersionsModel ivm);
    public void setVolList(ObservableList<VolumeSelectionModelType1> obv);
    //public void setVolList(ObservableList<VolumeSelectionModelType0> obv);
    public void addToParent(JobStepType0Model next2);
    public void addToListOfLinksModel(LinksModel lm);
    public void setSessionModel(SessionModel smodel);
    public SessionModel getSessionModel();
    public BooleanProperty getPendingFlagProperty();
    public BooleanProperty getQcFlagProperty();
    public InsightVersionsModel getInsightVersionsModel();
    public Long getType();
    public ObservableList getVolList();
    public void setSubsurfacesInJob(Set<SubSurface> subsInJob);
    public void setPendingFlagProperty(Boolean TRUE);
    public Set<SubSurface> getSubsurfacesInJob();
    public void setQcFlagProperty(Boolean FALSE);
    
    
    
}
