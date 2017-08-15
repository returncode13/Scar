/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.NodeType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface NodeTypeDAO {
    public void createNodeType(NodeType n);
    public NodeType getNodeType(Long nid);
    public void updateNodeType(Long nid,NodeType newNodeType);
    public void deleteNodeType(Long nid);
    
    
    public NodeType getNodeTypeObjForType(Long type);
}
