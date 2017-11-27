/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type0;

import fend.session.SessionModel;
import fend.session.edges.LinksModel;
import fend.session.node.headers.SequenceHeaders;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.insightVersions.InsightVersionsModel;
import fend.session.node.jobs.nodeproperty.JobModelProperty;
import fend.session.node.jobs.types.type2.JobStepType2Model;
import fend.session.node.qcTable.QcTableModel;
import fend.session.node.qcTable.qcCheckBox.qcCheckListModel;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import mid.doubt.Doubt;

/**
 *
 * @author sharath nair
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
   // public void setVolList(ObservableList<VolumeSelectionModelType1> obv);
    //public void setVolList(ObservableList<VolumeSelectionModelType0> obv);
    public void addToParent(JobStepType0Model next2);
    public void addToListOfLinksModel(LinksModel lm);
    public void setSessionModel(SessionModel smodel);
    public SessionModel getSessionModel();
    public BooleanProperty getPendingFlagProperty();
    public BooleanProperty getDependency();
    public InsightVersionsModel getInsightVersionsModel();
    public Long getType();
    public ObservableList getVolList();
    public void setSubsurfacesInJob(Set<SubSurfaceHeaders> subsInJob);
    public void setPendingFlagProperty(Boolean TRUE);
    public Set<SubSurfaceHeaders> getSubsurfacesInJob();
    public Set<SequenceHeaders> getSequencesInJob();
    public void setDependency(Boolean FALSE);

    public Doubt getDoubt();
    public List<JobModelProperty> getJobProperties();
    public void setJobProperties(List<JobModelProperty> jobModelProperties);
    
    public QcTableModel getQcTableModel();
   
    
}
