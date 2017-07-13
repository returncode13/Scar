/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DoubtStatusDAO {
    public void createDoubtStatus(DoubtStatus ds);
    public void updateDoubtStatus(Long dsid,DoubtStatus newds);
    public DoubtStatus getDoubtStatus(Long dsid);
    public void deleteDoubtStatus(Long dsid);
    
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd);

    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd, DoubtType dt);

    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd, DoubtType dt, Headers hd);
    
    
}
