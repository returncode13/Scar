/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtTypeDAO;
import db.dao.DoubtTypeDAOImpl;
import db.model.DoubtType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtTypeServiceImpl implements DoubtTypeService{
    
    DoubtTypeDAO dtDAO=new DoubtTypeDAOImpl();
    
    @Override
    public void createDoubtType(DoubtType dt) {
         dtDAO.createDoubtType(dt);
    }

    @Override
    public void updateDoubtType(Long dtid, DoubtType newdt) {
        dtDAO.updateDoubtType(dtid, newdt);
    }

    @Override
    public DoubtType getDoubtType(Long dtid) {
        return dtDAO.getDoubtType(dtid);
    }

    @Override
    public void deleteDoubtType(Long dtid) {
        dtDAO.deleteDoubtType(dtid);
    }

    @Override
    public DoubtType getDoubtTypeByName(String doubtName) {
        return dtDAO.getDoubtTypeByName(doubtName);
    }
    
}
