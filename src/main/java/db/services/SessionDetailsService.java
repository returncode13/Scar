/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;

/**
 *
 * @author sharath nair
 */
public interface SessionDetailsService {
    public void createSessionDetails(SessionDetails sd);
    public SessionDetails getSessionDetails(Long sdId);
    public void updateSessionDetails(Long sdId,SessionDetails newSd);
    public void deleteSessionDetails(Long sdId);
    
    public List<SessionDetails> getSessionDetails(Sessions s);              //returns all jobs-sessions belonging to a session
    public List<SessionDetails> getSessionDetails(JobStep js);              //returns all jobs-sessions belonging to a single value of jobstep
    public SessionDetails getSessionDetails(JobStep js, Sessions s);        //returns sessionDetails belonging to jobStep js and sessions s
}
