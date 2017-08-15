/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.PropertyType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface PropertyTypeService {
     public void createPropertyType(PropertyType p);
    public PropertyType getPropertyType(Long pid);
    public void updatePropertyType(Long pid,PropertyType np);
    public void deletePropertyType(Long pid);
    
    public PropertyType getPropertyTypeObjForName(String propname);
}
