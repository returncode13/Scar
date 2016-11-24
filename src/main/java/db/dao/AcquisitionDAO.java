/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Acquisition;
import java.util.List;

/**
 *
 * @author sharath
 */
public interface AcquisitionDAO {
    public List<Acquisition> getAcquisition(); //return a list of ALL subsurfaces shot till date (SELECT * FROM <table> )
    
}
