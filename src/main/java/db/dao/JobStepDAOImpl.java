/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;
import db.model.Volume;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair
 */
public class JobStepDAOImpl implements JobStepDAO{

    public JobStepDAOImpl() {
    }

    
    
    @Override
    public void createJobStep(JobStep js) {
        
             
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(js);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public JobStep getJobStep(Long jobId) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        
        
        try{
            JobStep js = (JobStep) session.get(JobStep.class, jobId);
           // System.out.println("JobDAOIMPL: checking for id "+jobId+" and found "+(js==null?" NULL":js.getIdJobStep()));
            return js;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateJobStep(Long jobId,JobStep newJs) {
      Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            
            JobStep oldJs=(JobStep) session.get(JobStep.class, jobId);
            oldJs.setNameJobStep(newJs.getNameJobStep());
            oldJs.setAlert(newJs.isAlert());
            oldJs.setJobVolumeDetails(newJs.getJobVolumeDetails());
            oldJs.setInsightVersions(newJs.getInsightVersions());
            session.update(oldJs);
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteJobStep(Long jobId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =  null;
        
        try{
            transaction= session.beginTransaction();
            
            JobStep js = (JobStep) session.get(JobStep.class, jobId);  //try using criteria instead
            System.out.println((js==null)?"db.dao.JobStepDAOImpl.deleteJobStep: NULL found!":"Deleting job:  "+js.getNameJobStep()+" id: "+js.getIdJobStep());
            session.delete(js);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

   

    @Override
    public void startAlert(JobStep njs) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            
            JobStep oldJs=(JobStep) session.get(JobStep.class, njs.getIdJobStep());
            oldJs.setAlert(Boolean.TRUE);
            session.update(oldJs);
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void stopAlert(JobStep njs) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            
            JobStep oldJs=(JobStep) session.get(JobStep.class, njs.getIdJobStep());
            oldJs.setAlert(Boolean.FALSE);
            session.update(oldJs);
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    /*
    @Override
    public void setPending(JobStep njs) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    
    try{
    transaction=session.beginTransaction();
    
    JobStep oldJs=(JobStep) session.get(JobStep.class, njs.getIdJobStep());
    oldJs.setPending(Boolean.TRUE);
    session.update(oldJs);
    
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    }
    
    @Override
    public void resetPending(JobStep njs) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    
    try{
    transaction=session.beginTransaction();
    
    JobStep oldJs=(JobStep) session.get(JobStep.class, njs.getIdJobStep());
    oldJs.setPending(Boolean.FALSE);
    session.update(oldJs);
    
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    }*/

    
    
    
    
}
