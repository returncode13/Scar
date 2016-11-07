/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Parent;
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
public class ParentDAOImpl implements ParentDAO{

    @Override
    public void addParent(Parent p) {
        
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(p);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    @Override
    public Parent getParent(Long pid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Parent anc= (Parent) session.get(Parent.class, pid);
            return anc;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateParent(Long id, Parent newP) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Parent anc= (Parent) session.get(Parent.class, id);
            anc.setParent(newP.getParent());
            anc.setSessionDetails(newP.getSessionDetails());
            session.update(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteParent(Long id) {
        
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Parent anc= (Parent) session.get(Parent.class, id);
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Parent getParentFor(SessionDetails fkid, Long parent) {
        
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Parent> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Parent.class);
            criteria.add(Restrictions.eq("sessionDetails", fkid));
            criteria.add(Restrictions.eq("parent",parent));
            result=criteria.list();
            transaction.commit();
            if(result.size()==1) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return null;
    }

    @Override
    public List<Parent> getParentsFor(SessionDetails s) {
       
         Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Parent> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Parent.class);
            criteria.add(Restrictions.eq("sessionDetails", s));
            
            result=criteria.list();
            transaction.commit();
          
        }catch(Exception e){
            e.printStackTrace();
        }
     return result;
    }
    
    
}
