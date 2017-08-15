/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.ObpManagerLog;

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
public class ObpManagerLogDAOImpl  implements ObpManagerLogDAO {

    @Override
    public void createObpManagerLog(ObpManagerLog o) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(o);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    @Override
    public ObpManagerLog getObpManagerLog(Long oid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            ObpManagerLog l= (ObpManagerLog) session.get(ObpManagerLog.class, oid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateObpManagerLog(Long oid, ObpManagerLog newO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            ObpManagerLog ll= (ObpManagerLog) session.get(ObpManagerLog.class, oid);
            ll.setLevel(newO.getLevel());
            ll.setLogger(newO.getLogger());
            ll.setMessage(newO.getMessage());
            ll.setSourceClass(newO.getSourceClass());
            ll.setSourceMethod(newO.getSourceMethod());
            ll.setThreadId(newO.getThreadId());
            ll.setTimeEntered(newO.getTimeEntered());
//            ll.setSessions(newO.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteObpManagerLog(Long oid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            ObpManagerLog h= (ObpManagerLog) session.get(ObpManagerLog.class, oid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<ObpManagerLog> getObpManagerLogs() {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<ObpManagerLog> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(ObpManagerLog.class);
       
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result;
    }

   
    
}
