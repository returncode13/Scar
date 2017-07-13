/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.DoubtStatus;
import db.model.DoubtType;
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
public class DoubtTypeDAOImpl implements DoubtTypeDAO {

    @Override
    public void createDoubtType(DoubtType dt) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(dt);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDoubtType(Long dtid, DoubtType newdt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            ll.setName(newdt.getName());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public DoubtType getDoubtType(Long dtid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubtType(Long dtid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public DoubtType getDoubtTypeByName(String doubtName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DoubtType> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(DoubtType.class);
            
            criteria.add(Restrictions.eq("name", doubtName));
            
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }
    
    
}
