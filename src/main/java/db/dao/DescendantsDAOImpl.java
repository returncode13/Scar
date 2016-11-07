/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import db.model.Descendants;
import db.model.SessionDetails;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class DescendantsDAOImpl implements DescendantsDAO {

    @Override
    public void addDescendants(Descendants d) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(d);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Descendants getDescendants(Long did) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        Descendants anc=null;
        try{
            transaction=session.beginTransaction();
           anc = (Descendants) session.get(Descendants.class, did);
            System.out.println("retrieved "+anc.getIdDescendants());
           transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return anc;
    }

    @Override
    public void updateDescendants(Long did, Descendants newD) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Descendants anc= (Descendants) session.get(Descendants.class, did);
            anc.setDescendant(newD.getDescendant());
            anc.setSessionDetails(newD.getSessionDetails());
            session.update(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteDescendants(Long did) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Descendants anc= (Descendants) session.get(Descendants.class, did);
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Descendants getDescendantsFor(SessionDetails fkid, Long descendant) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        List<Descendants> results=null;
        try{
            Transaction transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(Descendants.class);
        criteria.add(Restrictions.eq("sessionDetails", fkid));
        criteria.add(Restrictions.eq("descendant", descendant));
        results=criteria.list();
     
        if(results.size()>0)return results.get(0);
         
            
         transaction.commit();
         
         
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }
    
}
