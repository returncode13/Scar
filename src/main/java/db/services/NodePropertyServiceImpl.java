/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.NodePropertyDAO;
import db.dao.NodePropertyDAOImpl;
import db.model.NodeProperty;
import db.model.NodeType;
import db.model.PropertyType;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class NodePropertyServiceImpl implements NodePropertyService{

    NodePropertyDAO npdao=new NodePropertyDAOImpl();
    
    @Override
    public void createNodeProperty(NodeProperty np) {
        npdao.createNodeProperty(np);
    }

    @Override
    public NodeProperty getNodeProperty(Long npid) {
        return npdao.getNodeProperty(npid);
    }

    @Override
    public void updateNodeProperty(Long npid, NodeProperty newNp) {
        npdao.updateNodeProperty(npid, newNp);
    }

    @Override
    public void deleteNodeProperty(Long npid) {
        npdao.deleteNodeProperty(npid);
    }

    @Override
    public List<NodeProperty> getPropertyTypesFor(NodeType nodeType) {
        return npdao.getPropertyTypesFor(nodeType);
    }

    @Override
    public NodeProperty getNodeProperty(NodeType ntype, PropertyType protype) {
        return npdao.getNodeProperty(ntype,protype);
    }
    
}
