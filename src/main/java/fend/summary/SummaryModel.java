/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.session.node.jobs.type0.JobStepType0Model;
import java.util.Map;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author adira0150
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

    
    
    
    
    
}
