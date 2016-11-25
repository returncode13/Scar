/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Acquisition;
import java.util.List;

/**
 *
 * @author sharath
 */
public interface AcquisitionService {
    
    public List<Acquisition> getAcquisition();              //return the list of Acquisition parameters. Subsurfacelinename,starting shot pont, ending sp, start time,end time etc.
     public void createAcquisition(Acquisition acq);
    
}
