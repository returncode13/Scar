/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SessionDetailsDAO;
import db.dao.SessionDetailsDAOImpl;
import java.util.List;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;

/**
 *
 * @author sharath nair
 */
public class SessionDetailsServiceImpl implements SessionDetailsService{

    SessionDetailsDAO sdDao=new SessionDetailsDAOImpl();
    
    
    @Override
    public void createSessionDetails(SessionDetails sd) {
       sdDao.createSessionDetails(sd);
    }

    @Override
    public SessionDetails getSessionDetails(Long sdId) {
        return sdDao.getSessionDetails(sdId);
    }

    @Override
    public void updateSessionDetails(Long sdId, SessionDetails newSd) {
        sdDao.updateSessionDetails(sdId, newSd);
    }

    @Override
    public void deleteSessionDetails(Long sdId) {
        sdDao.deleteSessionDetails(sdId);
    }

    @Override
    public List<SessionDetails> getSessionDetails(Sessions s) {
        return sdDao.getSessionDetails(s);
    }

    @Override
    public List<SessionDetails> getSessionDetails(JobStep js) {
        return sdDao.getSessionDetails(js);
    }

    @Override
    public SessionDetails getSessionDetails(JobStep js, Sessions s) {
        return sdDao.getSessionDetails(js, s);
                
    }

    
    
}
