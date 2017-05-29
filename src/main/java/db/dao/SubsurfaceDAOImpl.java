/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Sequence;
import db.model.Subsurface;
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

    @Override
    public List<Subsurface> getSubsurfaceForSequence(Sequence seq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Subsurface> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Subsurface.class);
            criteria.add(Restrictions.eq("sequence", seq.getSequenceno()));
            
            
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
    public Subsurface getSubsurfaceObjBysubsurfacename(String dugSubsurface) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Subsurface> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Subsurface.class);
            criteria.add(Restrictions.eq("subsurface", dugSubsurface));
            
            
            result=criteria.list();
            if(result.size()>1){
                System.out.println("db.dao.SubsurfaceDAOImpl.getSubsurfaceObjBysubsurfacename(): more than one entry found in db table for "+dugSubsurface);
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
    public List<Subsurface> getSubsurfaceList() {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Subsurface> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Subsurface.class);
            
            
            
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
