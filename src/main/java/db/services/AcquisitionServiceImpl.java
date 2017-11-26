/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.AcquisitionDAO;
import db.dao.AcquisitionDAOImpl;
import db.model.Acquisition;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionServiceImpl implements AcquisitionService{
    AcquisitionDAO aDao=new AcquisitionDAOImpl();
    
    @Override
    public void createAcquisition(Acquisition acq) {
        aDao.createAcquisition(acq);
    }

    @Override
    public Acquisition getAcquisition(Long aid) {
       return aDao.getAcquisition(aid);
    }

    @Override
    public void deleteAcquisition(Long aid) {
        aDao.deleteAcquisition(aid);
    }

    @Override
    public void updateAcquisition(Long aid, Acquisition newAcq) {
        aDao.updateAcquisition(aid, newAcq);
    }

    @Override
    public List<Long> getCables() {
        return aDao.getCables();
    }

    @Override
    public List<Long> getGuns() {
        return aDao.getGuns();
    }
    
}
