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
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author adira0150
 */
public interface JobStepType0Model {

    public String getJobStepText(); /*{
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }*/

    public Long getId() ;/*{
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public List<JobStepType0Model> getJsChildren(); /*{
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }*/

    public void addToChildren(JobStepType0Model model);/* {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }*/

    public List<LinksModel> getListOfLinkModels(); /*{
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }*/

    public List<JobStepType0Model> getJsParents(); /*{
                                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                    }*/

    public void addSelfToParent();; /*{
                                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                            }*/

    public void addSelfToChild(); /*{
                                                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                                    }*/

    public void setJobStepText(String nameJobStep);

    public void setId(Long idJobStep);

    public void setInsightVersionsModel(InsightVersionsModel ivm);

    public void setVolList(ObservableList<VolumeSelectionModel> obv);

    public void addToParent(JobStepType0Model next2);

    public void addToListOfLinksModel(LinksModel lm);

    public void setSessionModel(SessionModel smodel);

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
