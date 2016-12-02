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
 * @author sharath
 */
public class AcquisitionServiceImpl implements AcquisitionService{

    AcquisitionDAO acqDao=new AcquisitionDAOImpl();

    @Override
    public List<Acquisition> getAcquisition() {
        return acqDao.getAcquisition();
    }

    @Override
    public void createAcquisition(Acquisition acq) {
        acqDao.createAcquisition(acq);
    }
    
    
    
}
