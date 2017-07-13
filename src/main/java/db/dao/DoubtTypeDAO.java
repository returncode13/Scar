/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.DoubtType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DoubtTypeDAO {
    public void createDoubtType(DoubtType dt);
    public void updateDoubtType(Long dtid,DoubtType newdt);
    public DoubtType getDoubtType(Long dtid);
    public void deleteDoubtType(Long dtid);

    public DoubtType getDoubtTypeByName(String doubtName);
    
}
