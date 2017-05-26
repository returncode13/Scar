/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Sequence;
import db.model.Subsurface;
import hibUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceDAOImpl implements SubsurfaceDAO{
    
    
    @Override
    public void createSubsurface(Subsurface sub) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(sub);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }

    }

    @Override
    public Subsurface getSubsurface(Long sid) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Subsurface l= (Subsurface) session.get(Subsurface.class, sid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
        
    }

    @Override
    public void deleteSubsurface(Long sid) {
       
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
              Subsurface l= (Subsurface) session.get(Subsurface.class, sid);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateSubsurface(Long sid, Subsurface newsub) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Subsurface l= (Subsurface) session.get(Subsurface.class, sid);
           l.setSequence(newsub.getSequence());
           l.setSubsurface(newsub.getSubsurface());
            
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
        
    }
    
}
