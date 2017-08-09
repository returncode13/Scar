/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.ObpManagerLog;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface ObpManagerLogService {
     public void createObpManagerLog(ObpManagerLog o);
    public ObpManagerLog getObpManagerLog(Long oid);
    public void updateObpManagerLog(Long oid,ObpManagerLog newO);
    public void deleteObpManagerLog(Long oid);
}
