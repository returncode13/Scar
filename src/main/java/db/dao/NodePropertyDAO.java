/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.NodeProperty;
import db.model.NodeType;
import db.model.PropertyType;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface NodePropertyDAO {
    public void createNodeProperty(NodeProperty np);
    public NodeProperty getNodeProperty(Long npid);
    public void updateNodeProperty(Long npid,NodeProperty newNp);
    public void deleteNodeProperty(Long npid);
    
    public List<NodeProperty> getPropertyTypesFor(NodeType nodeType);

    public NodeProperty getNodeProperty(NodeType ntype, PropertyType protype);
    
}
