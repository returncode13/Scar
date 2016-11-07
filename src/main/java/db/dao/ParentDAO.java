/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Parent;
import db.model.SessionDetails;
import java.util.List;



/**
 *
 * @author sharath nair
 */
public interface ParentDAO {
    public void addParent(Parent p);
    public Parent getParent(Long pid);
    public void updateParent(Long id,Parent newP);
    public void deleteParent(Long id);
    
    public Parent getParentFor(SessionDetails fkid,Long parent);
    public List<Parent> getParentsFor(SessionDetails s);
}
