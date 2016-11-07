/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.List;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair     
 */
public class SessionDetailsDAOImpl implements SessionDetailsDAO{

    @Override
    public void createSessionDetails(SessionDetails sd) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(sd);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public SessionDetails getSessionDetails(Long sdId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            SessionDetails sd= (SessionDetails) session.get(SessionDetails.class, sdId);
            return sd;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

                                
    
    @Override
    public void updateSessionDetails(Long sdId, SessionDetails newSd) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            SessionDetails sd= (SessionDetails) session.get(SessionDetails.class, sdId);
            session.update(sd);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void deleteSessionDetails(Long sdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SessionDetails> getSessionDetails(Sessions s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List results=null;
        
        try{
            transaction=session.beginTransaction();
             Criteria criteria=session.createCriteria(SessionDetails.class);
        criteria.add(Restrictions.eq("sessions", s));
        results=criteria.list();
        transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
       
        
                
        return results;
        
    }

    @Override
    public List<SessionDetails> getSessionDetails(JobStep js) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List results=null;
        
        try{
            transaction=session.beginTransaction();
             Criteria criteria=session.createCriteria(SessionDetails.class);
        criteria.add(Restrictions.eq("jobStep", js));
        results=criteria.list();
        transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
       
        
                
        return results;
        
    }

    @Override
    public SessionDetails getSessionDetails(JobStep js, Sessions s) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<SessionDetails> results=null;
        
        try{
            transaction=session.beginTransaction();
             Criteria criteria=session.createCriteria(SessionDetails.class);
        criteria.add(Restrictions.eq("jobStep", js));
        criteria.add(Restrictions.eq("sessions", s));
        results=criteria.list();
        transaction.commit();
        if(results.size()>0) return results.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
       
        System.out.println("SSDAOIMPL: Number of results "+results.size());
                
        return null;
    }

   
    
}
