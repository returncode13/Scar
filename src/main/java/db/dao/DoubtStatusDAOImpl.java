/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Headers;
import db.model.SessionDetails;
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
public class DoubtStatusDAOImpl implements DoubtStatusDAO{

    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(ds);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDoubtStatus(Long dsid, DoubtStatus newds) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtStatus ll=(DoubtStatus) session.get(DoubtStatus.class,dsid);
            ll.setDoubtType(newds.getDoubtType());
            ll.setErrorMessage(newds.getErrorMessage());
            ll.setHeaders(newds.getHeaders());
            ll.setSessionDetails(newds.getSessionDetails());
            ll.setStatus(newds.getStatus());
            ll.setUser(newds.getUser());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public DoubtStatus getDoubtStatus(Long dsid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            DoubtStatus ll=(DoubtStatus) session.get(DoubtStatus.class,dsid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubtStatus(Long dsid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtStatus ll=(DoubtStatus) session.get(DoubtStatus.class,dsid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DoubtStatus> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(DoubtStatus.class);
            criteria.add(Restrictions.eq("sessionDetails", sd));
            
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd, DoubtType dt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DoubtStatus> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(DoubtStatus.class);
            criteria.add(Restrictions.eq("sessionDetails", sd));
            criteria.add(Restrictions.eq("doubtType", dt));
            
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<DoubtStatus> getDoubtStatusListForJobInSession(SessionDetails sd, DoubtType dt, Headers hd) {                   
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DoubtStatus> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(DoubtStatus.class);
            criteria.add(Restrictions.eq("sessionDetails", sd));
            criteria.add(Restrictions.eq("doubtType", dt));
            criteria.add(Restrictions.eq("headers", hd));
            
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;                      //the list size should be one
    }
    
    
}
