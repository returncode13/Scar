/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Child;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface ChildService {
    public void addChild(Child c);
    public Child getChild(Long cid);
    public void updateChild(Long id,Child newC);
    public void deleteChild(Long cid);
    
    public Child getChildFor(SessionDetails fkid,Long cid);
    public List<Child> getChildrenFor(SessionDetails s);
}
