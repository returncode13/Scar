/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;


import db.dao.NodePropertyValueDAO;
import db.dao.NodePropertyValueDAOImpl;
import db.model.JobStep;
import db.model.NodeProperty;

import db.model.NodePropertyValue;

import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class NodePropertyValueServiceImpl implements NodePropertyValueService{

    NodePropertyValueDAO npvdao=new NodePropertyValueDAOImpl();
    
    
    @Override
    public void createNodePropertyValue(NodePropertyValue npv) {
        npvdao.createNodePropertyValue(npv);
    }

    @Override
    public NodePropertyValue getNodePropertyValue(Long npvid) {
        return npvdao.getNodePropertyValue(npvid);
    }

    @Override
    public void updateNodePropertyValue(Long npvid, NodePropertyValue newNpv) {
        npvdao.updateNodePropertyValue(npvid, newNpv);
    }

    @Override
    public void deleteNodePropertyValue(Long npvid) {
        npvdao.deleteNodePropertyValue(npvid);
    }

    @Override
    public List<NodePropertyValue> getNodePropertyValuesFor(JobStep job) {
        return npvdao.getNodePropertyValuesFor(job);
    }

    @Override
    public NodePropertyValue getNodePropertyValueFor(JobStep jobStep, NodeProperty nodeProperty) {
        return npvdao.getNodePropertyValuesFor(jobStep,nodeProperty);
    }
    
    
   
    
  
    
}
