/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.AncestorsDAO;
import db.dao.AncestorsDAOImpl;
import hibUtil.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Ancestors;
import db.model.JobStep;
import db.model.Parent;
import db.model.SessionDetails;
import java.util.ArrayList;
import java.util.Objects;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class AncestorsServiceImpl implements AncestorsService{

    AncestorsDAO ancDao = new AncestorsDAOImpl();
    SessionDetailsService ssdServ = new SessionDetailsServiceImpl();
    
    @Override
    public void addAncestor(Ancestors A) {
        ancDao.addAncestor(A);
    }

    @Override
    public Ancestors getAncestors(Long aid) {
        return ancDao.getAncestors(aid);
    }

    @Override
    public void updateAncestors(Long aid, Ancestors newAncestor) {
        ancDao.updateAncestors(aid, newAncestor);
    }

    @Override
    public void deleteAncestors(Long aid) {
        ancDao.deleteAncestors(aid);
    }

    @Override
    public void  getInitialAncestorsListFor(SessionDetails s,Set<Long> listOfAncestors){
            
        Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=sess.beginTransaction();
        try{
            //Criteria criteria=sess.createCriteria(Ancestors.class);
            Criteria criteria=sess.createCriteria(Parent.class);
            criteria.add(Restrictions.eq("sessionDetails", s));                    //in the Parent select all records where FK=s; i.e list of all jobs belonging to the same session
            List<Parent> result=criteria.list();
         
            System.out.println("AncServ: resultSize: "+result.size());
            
        
            
            for(Parent ancs:result){
               // System.out.println("AncServ: About to ask for the Sessiondetails for ancestor id "+ancs.getAncestor());
                   SessionDetails sdAnc=ssdServ.getSessionDetails(ancs.getParent());                           //remember that the "ancestor" column in the Ancestors Table is the Long id of a session detail as well. Both columns
                                                                                                                 //are really session details in this table. Just the sessiondetails column truly refers to a SessionDetails object (for foreign key)
                                                                                                                 //while the ancestor column holds the id of the immediate parent SessionDetails of the first columns sessionDetail object.
                   
                                                                                                                //so if 1<--2 then the table will contain an entry where the sessionDetails object =1 and its ancestor =2.id 
               
                 //  System.out.println("AncServ: inloop sdAnc:=Id: "+sdAnc.getIdSessionDetails());
                    if(Objects.equals(s.getIdSessionDetails(), sdAnc.getIdSessionDetails()))
                    {
                        System.out.println("root Or a standalone node...eitherways a root");
                    listOfAncestors.add(s.getIdSessionDetails());
                        
                    return;
                    }
                   listOfAncestors.add(s.getIdSessionDetails());
                   
                    getInitialAncestorsListFor(sdAnc,listOfAncestors);
                      
                       
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
      public void makeAncestorsTableFor(SessionDetails fkid,Set<Long> listOfAncestors)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Ancestors.class);
        criteria.add(Restrictions.eq("sessionDetails", fkid));
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Ancestors next = (Ancestors) iterator.next();
                    session.delete(next);
                    
                }
        transaction.commit();
        
        
    }
         
            Transaction transaction=session.beginTransaction();
         
         for (Iterator iterator = listOfAncestors.iterator(); iterator.hasNext();) {
            Long next = (Long) iterator.next();
            Ancestors anc=new Ancestors(fkid, next);
            session.save(anc);
            
        }
         transaction.commit();
         
         
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        }

    @Override
    public void getAncestorsFor(SessionDetails fkid, Set<Long> listOfAncestors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ancestors getAncestorsFor(SessionDetails fkid, Long ancestor) {
       return ancDao.getAncestorsFor(fkid, ancestor);
    }

   
    
}
