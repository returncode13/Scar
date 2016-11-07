/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SessionsDAO;
import db.dao.SessionsDAOImpl;
import java.util.List;
import db.model.Sessions;

/**
 *
 * @author sharath nair
 */
public class SessionsServiceImpl implements SessionsService {

    SessionsDAO sessDAO=new SessionsDAOImpl();
    @Override
    public void createSessions(Sessions s) {
        sessDAO.createSessions(s);
    }

    @Override
    public Sessions getSessions(Long sessionId) {
        return sessDAO.getSessions(sessionId);
    }

    @Override
    public void updateSessions(Long sessionId, Sessions newSession) {
        sessDAO.updateSessions(sessionId, newSession);
    }

    @Override
    public void deleteSessions(Long sessionId) {
        sessDAO.deleteSessions(sessionId);
    }

    @Override
    public List<Sessions> listSessions() {
        return sessDAO.listSessions();
    }
    
}
