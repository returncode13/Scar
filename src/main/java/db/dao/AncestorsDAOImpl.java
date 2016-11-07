/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Ancestors;
import db.model.SessionDetails;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class AncestorsDAOImpl implements AncestorsDAO{

    @Override
    public void addAncestor(Ancestors anc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Ancestors getAncestors(Long aid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Ancestors anc= (Ancestors) session.get(Ancestors.class, aid);
            return anc;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateAncestors(Long aid, Ancestors newAncestor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Ancestors anc= (Ancestors) session.get(Ancestors.class, aid);
            anc.setAncestor(newAncestor.getAncestor());
            anc.setSessionDetails(newAncestor.getSessionDetails());
            session.update(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteAncestors(Long aid) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Ancestors anc= (Ancestors) session.get(Ancestors.class, aid);
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Ancestors getAncestorsFor(SessionDetails fkid, Long ancestor) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Ancestors> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Ancestors.class);
            criteria.add(Restrictions.eq("sessionDetails", fkid));
            criteria.add(Restrictions.eq("ancestor",ancestor));
            result=criteria.list();
            transaction.commit();
            if(result.size()==1) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return null;
    }
    
     
         

}