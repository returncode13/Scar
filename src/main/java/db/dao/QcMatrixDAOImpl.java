/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcMatrix;
import db.model.SessionDetails;
import db.model.Volume;
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
public class QcMatrixDAOImpl implements QcMatrixDAO{

    @Override
    public void createQcMatrix(QcMatrix qcmatrix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qcmatrix);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    @Override
    public void updateQcMatrix(Long qid, QcMatrix newq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
        //    h.setVolume(newq.getVolume());
            h.setSessionDetails(newq.getSessionDetails());
            h.setQctype(newq.getQctype());
            session.update(h);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public QcMatrix getQcMatrix(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null; 
    }

    @Override
    public void deleteQcMatrix(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    /*
    @Override
    public List<QcMatrix> getQcMatrixForVolume(Volume v) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcMatrix> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcMatrix.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("present", true));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    
    
    
    }*/

    @Override
    public List<QcMatrix> getQcMatrixForSessionDetails(SessionDetails sd) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcMatrix> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcMatrix.class);
    criteria.add(Restrictions.eq("sessionDetails", sd));
    criteria.add(Restrictions.eq("present", true));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;    }
    
}
