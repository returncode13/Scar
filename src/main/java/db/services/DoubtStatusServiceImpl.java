/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtStatusDAO;
import db.dao.DoubtStatusDAOImpl;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusServiceImpl implements DoubtStatusService{

    DoubtStatusDAO dsDAO=new DoubtStatusDAOImpl();
            
    
    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        dsDAO.createDoubtStatus(ds);
    }

    @Override
    public void updateDoubtStatus(Long dsid, DoubtStatus newds) {
        dsDAO.updateDoubtStatus(dsid, newds);
    }

    @Override
    public DoubtStatus getDoubtStatus(Long dsid) {
        return dsDAO.getDoubtStatus(dsid);
    }

    @Override
    public void deleteDoubtStatus(Long dsid) {
        dsDAO.deleteDoubtStatus(dsid);
    }

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd) {
        return dsDAO.getDoubtStatusListForJobInSession(sd);
    }

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd, DoubtType dt) {
        return dsDAO.getDoubtStatusListForJobInSession(sd,dt);
    }

    

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails parentsd, Long childsdId, DoubtType dt, Headers hd) {
        return dsDAO.getDoubtStatusListForJobInSession(parentsd, childsdId, dt, hd);
    }
    
}
