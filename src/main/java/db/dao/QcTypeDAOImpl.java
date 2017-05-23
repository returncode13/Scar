/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.QcType;
import db.model.Sessions;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author adira0150
 */
public class QcTypeDAOImpl implements QcTypeDAO{

    @Override
    public void createQcType(QcType qctype) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qctype);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateQcType(Long qid, QcType newQcType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcType ll=(QcType) session.get(QcType.class,qid);
            ll.setName(newQcType.getName());
            ll.setSessions(newQcType.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public QcType getQcType(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcType l= (QcType) session.get(QcType.class, qid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteQcType(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcType h= (QcType) session.get(QcType.class, qid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<QcType> getQcTypesForSession(Sessions sessions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcType> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(QcType.class);
            criteria.add(Restrictions.eq("sessions", sessions));
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
