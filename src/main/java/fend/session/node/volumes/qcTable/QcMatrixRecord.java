/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import fend.session.node.volumes.VolumeSelectionCell;
import fend.session.node.volumes.VolumeSelectionModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixRecord {
    VolumeSelectionModel vmodel;
    QcTypeModel qcTypeModel;
    Boolean present;

    public VolumeSelectionModel getVmodel() {
        return vmodel;
    }

    public void setVmodel(VolumeSelectionModel vmodel) {
        this.vmodel = vmodel;
    }

    public QcTypeModel getQcTypeModel() {
        return qcTypeModel;
    }

    public void setQcTypeModel(QcTypeModel qcTypeModel) {
        this.qcTypeModel = qcTypeModel;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
    
    
          
}
