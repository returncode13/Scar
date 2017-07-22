/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.qcTable;

import fend.session.node.jobs.types.type0.JobStepType0Model;
import fend.session.node.volumes.type1.VolumeSelectionModelType1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.comparators.ComparableComparator;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixModel {
   // VolumeSelectionModelType1 vmodel;
    Map<QcTypeModel,Boolean> qcTypePresMap=new HashMap<>();
    JobStepType0Model jobmodel;
   // List<QcMatrixRecord> qcmatrixRecords;
    Map<QcTypeModel,Boolean> checkMap=new HashMap<>();      //use this map for keeping track of what's been checked/unchecked from the presMap <--not used anywhere
    
    
    public JobStepType0Model getJobmodel() {
        return jobmodel;
    }

    public void setJobmodel(JobStepType0Model jobmodel) {
        this.jobmodel = jobmodel;
    }
    
    
    
    /*
    public VolumeSelectionModelType1 getVmodel() {
    return vmodel;
    }
    
    public void setVmodel(VolumeSelectionModelType1 vmodel) {
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

    public Map<QcTypeModel, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<QcTypeModel, Boolean> checkMap) {
        this.checkMap = checkMap;
    }
    
    public void changeCheckMap(QcTypeModel q,Boolean newVal){
        checkMap.put(q, newVal);
    }
    
    
    
    
    
    
    public void clear(){
        qcTypePresMap.clear();
    }
    
    public List<QcTypeModel> getQcTypeModels(){   //the qctypes chosen for this job step
        Set<QcTypeModel> keys=qcTypePresMap.keySet();
        List<QcTypeModel> types=new ArrayList<>(keys);
        Collections.sort(types);
        
        
        
        return types;
    }
    
}
