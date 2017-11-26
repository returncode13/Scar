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
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface AcquisitionService {
    public void createAcquisition(Acquisition acq);
    public Acquisition getAcquisition(Long aid);
    public void deleteAcquisition(Long aid);
    public void updateAcquisition(Long aid, Acquisition newAcq);
    public List<Long> getCables();
    public List<Long> getGuns();
}
