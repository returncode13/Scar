/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.PropertyTypeDAO;
import db.dao.PropertyTypeDAOImpl;
import db.model.PropertyType;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PropertyTypeServiceImpl implements PropertyTypeService{
    
    PropertyTypeDAO pdao=new PropertyTypeDAOImpl();
    
    @Override
    public void createPropertyType(PropertyType p) {
        pdao.createPropertyType(p);
    }

    @Override
    public PropertyType getPropertyType(Long pid) {
        return pdao.getPropertyType(pid);
    }

    @Override
    public void updatePropertyType(Long pid, PropertyType np) {
        pdao.updatePropertyType(pid, np);
    }

    @Override
    public void deletePropertyType(Long pid) {
        pdao.deletePropertyType(pid);
    }

    @Override
    public PropertyType getPropertyTypeObjForName(String propname) {
        return pdao.getPropertyTypeObjForName(propname);
    }
    
}
