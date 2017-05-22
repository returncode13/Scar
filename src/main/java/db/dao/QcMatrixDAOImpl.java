/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcMatrix;
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
public class QcMatrixDAOImpl implements QcMatrixDAO{

    @Override
    public void createQcMatrix(QcMatrix qcm) {
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
    public void updateQcMatrix(Long qid, QcMatrix newQ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
            h.setVolume(newQ.getVolume());
            h.setTime(newQ.getTime());
            h.setSequenceNumber(newQ.getSequenceNumber());
            h.setSubsurface(newQ.getSubsurface());
            h.setResult(newQ.getResult());
            h.setQctype(newQ.getQctype());
            h.setComment(newQ.getComment());
            session.update(h);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
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

    @Override
    public List<QcMatrix> getQcMatrixFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrix> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(QcMatrix.class);
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
    public List<QcMatrix> getQcMatrixFor(Volume v, QcType qctype) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrix> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(QcMatrix.class);
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
    }
    
}
