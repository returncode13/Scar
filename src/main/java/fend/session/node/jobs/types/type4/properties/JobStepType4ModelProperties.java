/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.jobs.types.type4.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobStepType4ModelProperties {
    List<String> properties=new ArrayList<>();

    public JobStepType4ModelProperties() {
        this.properties.add("from");
        this.properties.add("to");
    }

    public List<String> getProperties() {
        return properties;
    }
    
    
    
}
