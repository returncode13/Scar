/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.Sessions;

/**
 *
 * @author sharath nair
 */
public interface SessionsDAO {
    public void createSessions(Sessions s);
    public Sessions getSessions(Long sessionId);
    public void updateSessions(Long sessionId,Sessions newSession);
    public void deleteSessions(Long sessionId);
    
    public List<Sessions> listSessions();
}
