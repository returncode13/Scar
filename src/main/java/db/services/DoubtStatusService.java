/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.SessionDetails;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DoubtStatusService {
    public void createDoubtStatus(DoubtStatus ds);
    public void updateDoubtStatus(Long dsid,DoubtStatus newds);
    public DoubtStatus getDoubtStatus(Long dsid);
    public void deleteDoubtStatus(Long dsid);
    
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd);
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd,DoubtType dt);

    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails parentsd,Long childsdId, DoubtType dt, Headers hd);
}
