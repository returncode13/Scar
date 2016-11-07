/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DescendantsDAO;
import db.dao.DescendantsDAOImpl;
import db.model.Child;
import hibUtil.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Descendants;
import db.model.SessionDetails;
import java.util.Objects;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class DescendantsServiceImpl implements DescendantsService{
    DescendantsDAO descDao=new DescendantsDAOImpl();
    SessionDetailsService ssdServ=new SessionDetailsServiceImpl();
            

    @Override
    public void addDescendants(Descendants d) {
        descDao.addDescendants(d);
    }

    @Override
    public Descendants getDescendants(Long did) {
        return descDao.getDescendants(did);
    }

    @Override
    public void updateDescendants(Long did, Descendants newD) {
        descDao.updateDescendants(did, newD);
    }

    @Override
    public void deleteDescendants(Long did) {
        descDao.deleteDescendants(did);
    }

    @Override
    public void getInitialDescendantsListFor(SessionDetails fkid, Set<Long> listOfDescendants) {
          Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=sess.beginTransaction();
        try{
            //Criteria criteria=sess.createCriteria(Descendants.class);
            Criteria criteria=sess.createCriteria(Child.class);
            criteria.add(Restrictions.eq("sessionDetails", fkid));                    //in the descendants table select all records where FK=fkid;
            List<Child> result=criteria.list();
         
            for(Child desc:result){
                  // SessionDetails sdDesc=ssdServ.getSessionDetails(desc.getDescendant());// 
                   SessionDetails sdDesc=ssdServ.getSessionDetails(desc.getChild());
                   
                    if(Objects.equals(fkid.getIdSessionDetails(), sdDesc.getIdSessionDetails())){
                        System.out.println("leaf Or a standalone node...");
                        listOfDescendants.add(fkid.getIdSessionDetails());
                        return;
                    }
                   listOfDescendants.add(fkid.getIdSessionDetails());
                    getInitialDescendantsListFor(sdDesc,listOfDescendants);
                      
                       
            }
           
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sess.close();
        }
         //list.add(s);
    }

    @Override
    public void makeDescendantsTableFor(SessionDetails fkid, Set<Long> listOfDescendants) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Descendants.class);
        criteria.add(Restrictions.eq("sessionDetails", fkid));
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Descendants next = (Descendants) iterator.next();
                    session.delete(next);
                    
                }
        transaction.commit();
        
        
    }
         
            Transaction transaction=session.beginTransaction();
         
         for (Iterator iterator = listOfDescendants.iterator(); iterator.hasNext();) {
            Long next = (Long) iterator.next();
            Descendants desc=new Descendants(fkid, next);
            session.save(desc);
            
        }
         transaction.commit();
         
         
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void getDescendantsFor(SessionDetails fkid, Set<Long> listOfDescendants) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            Transaction transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(Descendants.class);
        criteria.add(Restrictions.eq("sessionDetails", fkid));
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Descendants next = (Descendants) iterator.next();
                    
                   // System.out.println("Got Desc: "+next.getIdDescendants()+" : "+next.getDescendant());
                   listOfDescendants.add(next.getDescendant());
                    
                    
                }
       
        
        
    }
         
            
         transaction.commit();
         
         
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Descendants getDescendantsFor(SessionDetails fkid, Long descendant) {
      return  descDao.getDescendantsFor(fkid,descendant);
    }
    
}
