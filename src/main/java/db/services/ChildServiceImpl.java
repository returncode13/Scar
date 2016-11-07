/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.ChildDAO;
import db.dao.ChildDAOImpl;
import db.model.Child;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public class ChildServiceImpl implements ChildService{

    ChildDAO cDAO=new ChildDAOImpl();
           
    
    @Override
    public void addChild(Child c) {
        cDAO.addChild(c);
    }

    @Override
    public Child getChild(Long cid) {
        return cDAO.getChild(cid);
    }

    @Override
    public void updateChild(Long id, Child newC) {
        cDAO.updateChild(id, newC);
    }

    @Override
    public void deleteChild(Long cid) {
        cDAO.deleteChild(cid);
    }

    @Override
    public Child getChildFor(SessionDetails fkid, Long cid) {
        return cDAO.getChildFor(fkid, cid);
    }

    @Override
    public List<Child> getChildrenFor(SessionDetails s) {
        return cDAO.getChildrenFor(s);
    }
    
}
