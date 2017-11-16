/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Headers;
import db.model.QcMatrix;
import db.model.QcTable;
import db.model.QcType;
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
public class QcTableDAOImpl implements QcTableDAO{

    @Override
    public void createQcTable(QcTable qcm) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qcm);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public QcTable getQcTable(Long qid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcTable h= (QcTable) session.get(QcTable.class, qid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateQcTable(Long qid, QcTable newQ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcTable h= (QcTable) session.get(QcTable.class, qid);
           // h.setVolume(newQ.getVolume());
            h.setTime(newQ.getTime());
            /*h.setSequenceNumber(newQ.getSequenceNumber());
            h.setSubsurface(newQ.getSubsurface());*/
            h.setHeaders(newQ.getHeaders());
            h.setResult(newQ.getResult());
          //  h.setQctype(newQ.getQctype());
          h.setQcmatrix(newQ.getQcmatrix());
            h.setComment(newQ.getComment());
            h.setUpdateTime(newQ.getUpdateTime());
            session.update(h);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteQcTable(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcTable h= (QcTable) session.get(QcTable.class, qid);
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
    public List<QcTable> getQcTableFor(Volume v) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("volume", v));
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
    public List<QcTable> getQcTableFor(Volume v, QcType qctype) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("qctype", qctype));
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
    public List<QcTable> getQcTableFor(QcMatrix qmx) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("qcmatrix", qmx));
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
    public List<QcTable> getQcTableFor(Headers h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("headers", h));
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
    public List<QcTable> getQcTableFor(QcMatrix qmx, Headers h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("qcmatrix", qmx));
        criteria.add(Restrictions.eq("headers", h));
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
