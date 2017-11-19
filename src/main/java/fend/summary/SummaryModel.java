/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.jobs.types.type0.JobStepType0Model;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import watcher.SummaryStatusWatcher;

/**
 *
 * @author sharath nair
 */
public class SummaryModel {
    MultiMap<Integer,JobStepType0Model> depthNodeMap;
    
    public SummaryModel() {
        
    }

    public MultiMap<Integer, JobStepType0Model> getDepthNodeMap() {
        return depthNodeMap;
    }

    public void setDepthNodeMap(MultiMap<Integer, JobStepType0Model> depthNodeMap) {
        this.depthNodeMap = depthNodeMap;
    }

    void destroyRunStatusThreads() {
        System.out.println("fend.summary.SummaryModel.destroyRunStatusThreads(): destroying threads..need to implement");       //stop timer threads here. walk down depths into jobs into volumemodels and kill threads there
    }

   
    
    
    
    
    
}
