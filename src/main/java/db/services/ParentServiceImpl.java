/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.ParentDAO;
import db.dao.ParentDAOImpl;
import db.model.Parent;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public class ParentServiceImpl implements ParentService {

    ParentDAO pDAO=new ParentDAOImpl();
    
    
    @Override
    public void addParent(Parent p) {
        pDAO.addParent(p);
    }

    @Override
    public Parent getParent(Long pid) {
        return pDAO.getParent(pid);
    }

    @Override
    public void updateParent(Long id, Parent newP) {
        pDAO.updateParent(id, newP);
    }

    @Override
    public void deleteParent(Long id) {
      pDAO.deleteParent(id);
    }

    @Override
    public Parent getParentFor(SessionDetails fkid, Long parent) {
        return pDAO.getParentFor(fkid, parent);
    }

    @Override
    public List<Parent> getParentsFor(SessionDetails s) {
        return pDAO.getParentsFor(s);
    }
    
}
