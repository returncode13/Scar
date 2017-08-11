/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.JobStep;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface NodePropertyValueService {
    public void createNodePropertyValue(NodePropertyValue npv);
    public NodePropertyValue getNodePropertyValue(Long npvid);
    public void updateNodePropertyValue(Long npvid,NodePropertyValue newNpv);
    public void deleteNodePropertyValue(Long npvid);
    
    public List<NodePropertyValue> getNodePropertyValuesFor(JobStep job);

    public NodePropertyValue getNodePropertyValueFor(JobStep jobStep, NodeProperty nodeProperty);
}
