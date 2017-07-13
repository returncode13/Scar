/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Parent;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface ParentService {
     public void addParent(Parent p);
    public Parent getParent(Long pid);
    public void updateParent(Long id,Parent newP);
    public void deleteParent(Long id);
    
    public Parent getParentRowFor(SessionDetails fkid,Long parent);//get the Parent row for these two columns!
    public List<Parent> getParentsFor(SessionDetails s);    //this returns the parent rows for this node s. the parents are found under parentrow.parent variable
}
