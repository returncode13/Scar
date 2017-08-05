/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.List;
import db.model.Sessions;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair
 */
public class SessionsDAOImpl implements SessionsDAO {

    @Override
    public void createSessions(Sessions s) {
      Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          session.saveOrUpdate(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public Sessions getSessions(Long sessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
            
      try{
          
          Sessions s=(Sessions) session.get(Sessions.class,sessionId);
         // System.out.println("SessDAOIMPL: checking for id "+sessionId+" and found "+(s==null?" NULL":s.getIdSessions()));
          return s;
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
      return null;
    }

    @Override
    public void updateSessions(Long sessionId, Sessions newSession) {
        Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          Sessions s=(Sessions) session.get(Sessions.class, sessionId);
          s.setNameSessions(newSession.getNameSessions());
          session.update(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public void deleteSessions(Long sessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          Sessions s=(Sessions) session.get(Sessions.class, sessionId);
          session.delete(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public List<Sessions> listSessions() {
        Session hibsession = HibernateUtil.getSessionFactory().openSession();
        List<Sessions> sessList=new ArrayList<>();
      Transaction transaction=null;
      
      try{
          transaction=hibsession.beginTransaction();
          sessList=hibsession.createCriteria(Sessions.class).list();
          
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          hibsession.close();
      }
      return sessList;
    }
    
    
}
