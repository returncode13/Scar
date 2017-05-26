/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.volumes.qcTable;

import fend.session.node.jobs.type0.JobStepType0Model;
import fend.session.node.volumes.VolumeSelectionModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixModel {
   // VolumeSelectionModel vmodel;
    Map<QcTypeModel,Boolean> qcTypePresMap=new HashMap<>();
    JobStepType0Model jobmodel;
   // List<QcMatrixRecord> qcmatrixRecords;

    public JobStepType0Model getJobmodel() {
        return jobmodel;
    }

    public void setJobmodel(JobStepType0Model jobmodel) {
        this.jobmodel = jobmodel;
    }
    
    
    
    /*
    public VolumeSelectionModel getVmodel() {
    return vmodel;
    }
    
    public void setVmodel(VolumeSelectionModel vmodel) {
    this.vmodel = vmodel;
    }*/
    public Map<QcTypeModel, Boolean> getQcTypePresMap() {
        return qcTypePresMap;
    }

    public void setQcTypePresMap(Map<QcTypeModel, Boolean> qcTypePresMap) {
        this.qcTypePresMap = qcTypePresMap;
    }
    
    public void addToQcTypePresMap(QcTypeModel qcmodel,Boolean present) {
        qcTypePresMap.put(qcmodel, present);
        
    }
    
    
    public void clear(){
        qcTypePresMap.clear();
    }
    
    public List<QcTypeModel> getQcTypeModels(){
        Set<QcTypeModel> keys=qcTypePresMap.keySet();
        List<QcTypeModel> types=new ArrayList<>(keys);
        
        
        
        return types;
    }
    
}
