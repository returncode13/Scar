/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Acquisition;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath
 */
public class AcquisitionDAOImpl implements AcquisitionDAO{

    @Override
    public List<Acquisition> getAcquisition() {
        Session session=HibernateUtil.getSessionFactory().openSession();
        List<Acquisition> result=null;
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Acquisition.class);
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            session.close();
        }
        return result;
    }

    @Override
    public void createAcquisition(Acquisition acq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(acq);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

   
    
}
