/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.acquisition;

import fend.session.node.headers.HeadersModel;
import fend.session.node.headers.SubSurface;
import fend.session.node.volumes.type0.VolumeSelectionModelType0;
import java.io.File;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionVolumeModel implements VolumeSelectionModelType0{
    final private Long type=3L;
    private Long id;
    private String label="AcqVol";
    
    @Override
    public Long getType() {
        return type;
    }

    @Override
    public Long getId() {

            return id;
    }

    @Override
    public String getLabel() {
            return label;
    }

    @Override
    public Long getVolumeType() {
        return type;
    }

    @Override
    public Boolean getHeaderButtonStatus() {
        return true;
    }

    @Override
    public File getVolumeChosen() {
        return new File("");
    }

    @Override
    public void startWatching() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVolumeChosen(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void setAlert(Boolean alert) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLabel(String nameVolume) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void setVolumeType(Long volumeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void setSubsurfaces(Set<SubSurface> sl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHeadersModel(HeadersModel hmod) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHeaderButtonStatus(Boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(Long idVolume) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInflated(Boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
