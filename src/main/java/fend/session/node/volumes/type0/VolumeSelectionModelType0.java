/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type0;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurfaceHeaders;
import fend.session.node.jobs.types.type0.JobStepType0Model;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface VolumeSelectionModelType0 {
    public Long getType();

    public Long getId();

    public String getLabel();

    public Long getVolumeType();

    public Boolean getHeaderButtonStatus();

    public File getVolumeChosen();

    public void startWatching();

    public void setVolumeChosen(File file);

    public void setHeaderButtonStatus(Boolean b);

    public void setAlert(Boolean alert);

    public void setLabel(String nameVolume);

    public void setId(Long idVolume);

    public void setVolumeType(Long volumeType);

    public void setInflated(Boolean b);

    public void setSubsurfaces(Set<SubSurfaceHeaders> sl);
    public Set<SubSurfaceHeaders> getSubsurfaces();
    
    public void setHeadersModel(HeadersModel hmod);

    public HeadersModel getHeadersModel();
    
    public List<SubSurfaceHeaders> getSubSurfaceHeadersToBeSummarized();
    public Map<String, SubSurfaceHeaders> getSubsurfaceNameSubSurfaceHeaderMap();
    public JobStepType0Model getParentJob();
   
}
