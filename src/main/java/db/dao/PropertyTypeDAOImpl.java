/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.PropertyType;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PropertyTypeDAOImpl implements PropertyTypeDAO {

    @Override
    public void createPropertyType(PropertyType p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(p);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public PropertyType getPropertyType(Long pid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            PropertyType ll=(PropertyType) session.get(PropertyType.class,pid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updatePropertyType(Long pid, PropertyType np) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            PropertyType ll=(PropertyType) session.get(PropertyType.class,pid);
            ll.setName(np.getName());
            
            
          //  ll.setSessions(newQcType.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deletePropertyType(Long pid) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            PropertyType ll=(PropertyType) session.get(PropertyType.class,pid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public PropertyType getPropertyTypeObjForName(String propname) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<PropertyType> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(PropertyType.class);
            criteria.add(Restrictions.eq("name", propname));
        
            result=criteria.list();
            transaction.commit();
           if(result.size()==1){
               return result.get(0);
           }else{
               return null;
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
}
