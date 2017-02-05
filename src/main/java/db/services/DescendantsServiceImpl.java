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
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void getInitialDescendantsListFor(SessionDetails fkid, Set<Long> listOfDescendants){
        List<Child> childrenList=getChildrenList();
        getInitialDescendantsListFor(fkid, listOfDescendants,childrenList);
    } 
    
    
    
    private List<Child> getChildrenList() {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Child> results=null;
        Transaction transaction=null;
        try{
        transaction=sess.beginTransaction();
        
        Criteria criteria=sess.createCriteria(Child.class);
        results=criteria.list();                           //get all the Parent entries;
        transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sess.close();
        }
        return results;
    }
    
    
    
    private void getInitialDescendantsListFor(SessionDetails s,Set<Long> listOfAncestors,List<Child> childrenList){
        List<Child> shortList=new ArrayList<>();
        
        for (Iterator<Child> iterator = childrenList.iterator(); iterator.hasNext();) {
            Child child = iterator.next();
            if(child.getSessionDetails().getIdSessionDetails().equals(s.getIdSessionDetails())){
                shortList.add(child);                         //shortlist will now contain the list of all children where FK=s .i.e list of all jobs belonging to the same session
                
            }
        }
        
        
        for (Child ancs:shortList) {
                SessionDetails sdAnc=ssdServ.getSessionDetails(ancs.getChild());
                if(Objects.equals(s.getIdSessionDetails(), sdAnc.getIdSessionDetails()))
                    {
                        System.out.println(s.getJobStep().getNameJobStep()+ ": root Or a standalone node...eitherways a root");
                    listOfAncestors.add(s.getIdSessionDetails());
                        
                    return;
                    }
                listOfAncestors.add(s.getIdSessionDetails());
                getInitialDescendantsListFor(sdAnc,listOfAncestors,childrenList);   
            }
                
        
    }
    
    

    
    private void getInitialDescendantsListFor(SessionDetails fkid, Set<Long> listOfDescendants,Session session) {
        
         if(session!=null && session.isOpen()){
            
            session.getTransaction().commit();
            session.close();
           
        
        }
        
        
          Session sess = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=sess.beginTransaction();
        try{
            //Criteria criteria=sess.createCriteria(Descendants.class);
            Criteria criteria=sess.createCriteria(Child.class);
            criteria.add(Restrictions.eq("sessionDetails", fkid));                    //in the Child table select all records where FK=fkid;
            List<Child> result=criteria.list();
          //  System.out.println("db.services.DescendantsServiceImpl.getInitialDescendantsListFor(): resultListvontents below ");
          /*  for(Child dd:result){
             //   System.out.println("Child: "+ssdServ.getSessionDetails(dd.getChild()).getJobStep().getNameJobStep()+"  for job: "+fkid.getJobStep().getNameJobStep());
             //   System.out.println("ChildSession: "+ssdServ.getSessionDetails(dd.getChild()).getSessions().getIdSessions() +" :Parent session: "+fkid.getSessions().getIdSessions());
            }*/
            
            for(Child desc:result){
                  // SessionDetails sdDesc=ssdServ.getSessionDetails(desc.getDescendant());// 
                   SessionDetails sdDesc=ssdServ.getSessionDetails(desc.getChild());
                   
                 //  System.out.println("db.services.DescendantsServiceImpl.getInitialDescendantsListFor():  "+ssdServ.getSessionDetails(desc.getChild()).getJobStep().getNameJobStep()+ " : for job: "+ssdServ.getSessionDetails(fkid.getIdSessionDetails()).getJobStep().getNameJobStep());
                   
                   
                    if(Objects.equals(fkid.getIdSessionDetails(), sdDesc.getIdSessionDetails())){
                        System.out.println(fkid.getJobStep().getNameJobStep()+" is a leaf Or a standalone node...");
                        listOfDescendants.add(fkid.getIdSessionDetails());
                        return;
                    }
                   listOfDescendants.add(fkid.getIdSessionDetails());
                    getInitialDescendantsListFor(sdDesc,listOfDescendants,sess);
                      
                       
            }
            
            if(sess.isOpen() && sess.isConnected())
          transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
         if(sess.isOpen())sess.close();
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
