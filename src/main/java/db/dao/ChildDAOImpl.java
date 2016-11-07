/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Child;
import db.model.SessionDetails;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class ChildDAOImpl implements ChildDAO{

    @Override
    public void addChild(Child c) {
       
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(c);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    @Override
    public Child getChild(Long cid) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Child anc= (Child) session.get(Child.class, cid);
            return anc;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateChild(Long id, Child newC) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Child anc= (Child) session.get(Child.class, id);
            anc.setChild(newC.getChild());
            anc.setSessionDetails(newC.getSessionDetails());
            session.update(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteChild(Long cid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Child anc= (Child) session.get(Child.class, cid);
            
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Child getChildFor(SessionDetails fkid, Long child) {
         Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Child> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Child.class);
            criteria.add(Restrictions.eq("sessionDetails", fkid));
            criteria.add(Restrictions.eq("child",child));
            result=criteria.list();
            transaction.commit();
            if(result.size()==1) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return null;
    }

    @Override
    public List<Child> getChildrenFor(SessionDetails s) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Child> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Child.class);
            criteria.add(Restrictions.eq("sessionDetails", s));
        
            result=criteria.list();
            transaction.commit();
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
}
