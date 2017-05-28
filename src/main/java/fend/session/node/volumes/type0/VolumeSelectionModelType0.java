/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.type0;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurface;
import java.io.File;
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

    public void setSubsurfaces(Set<SubSurface> sl);

    public void setHeadersModel(HeadersModel hmod);

    public HeadersModel getHeadersModel();
   
}
