/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Sequence;
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
public class SequenceDAOImpl implements SequenceDAO {

    @Override
    public void createSequence(Sequence seq) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(seq);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Sequence getSequence(Long sid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Sequence l= (Sequence) session.get(Sequence.class, sid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteSequence(Long sid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Sequence l= (Sequence) session.get(Sequence.class, sid);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateSequence(Long sid, Sequence newseq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Sequence l= (Sequence) session.get(Sequence.class, sid);
            l.setSequenceno(sid);
            
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public Sequence getSequenceObjByseqno(Long seqno) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Sequence> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Sequence.class);
            criteria.add(Restrictions.eq("sequenceno", seqno));
            
            
            result=criteria.list();
            if(result.size()>1){
                System.out.println("db.dao.SequenceDAOImpl.getSequenceObjByseqno(): More than one entry for sequence no: "+seqno);
            }
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()!=0){
             return result.get(0);
        }else
            return null;
       
    }

    @Override
    public List<Sequence> getSequenceList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Sequence> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Sequence.class);
            
            
            
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
