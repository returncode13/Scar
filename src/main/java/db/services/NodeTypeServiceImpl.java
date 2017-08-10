/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.NodeTypeDAO;
import db.dao.NodeTypeDAOImpl;
import db.model.NodeType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class NodeTypeServiceImpl implements NodeTypeService{
    
    NodeTypeDAO ndao=new NodeTypeDAOImpl();
    
    @Override
    public void createNodeType(NodeType n) {
        ndao.createNodeType(n);
    }

    @Override
    public NodeType getNodeType(Long nid) {
        return ndao.getNodeType(nid);
    }

    @Override
    public void updateNodeType(Long nid, NodeType newNodeType) {
        ndao.updateNodeType(nid, newNodeType);
    }

    @Override
    public void deleteNodeType(Long nid) {
        ndao.deleteNodeType(nid);
    }

    @Override
    public NodeType getNodeTypeObjForType(Long type) {
        return ndao.getNodeTypeObjForType(type);
    }
    
}
