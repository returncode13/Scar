/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import db.model.JobStep;
import db.model.JobVolumeDetails;
import db.model.Volume;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class JobVolumeDetailsDAOImpl implements JobVolumeDetailsDAO{

    @Override
    public void createJobVolumeDetails(JobVolumeDetails jvd) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(jvd);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public JobVolumeDetails getJobVolumeDetails(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateJobVolumeDetails(Long jvdId, JobVolumeDetails newJvd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteJobVolumeDetails(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeDetails> getJobVolumeDetails(JobStep js) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeDetails> getJobVolumeDetails(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobStep> getJobStepContaining(Volume v) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeDetails> result=null;
        List<JobStep> jobStepList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(JobVolumeDetails.class);
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        for (Iterator<JobVolumeDetails> iterator = result.iterator(); iterator.hasNext();) {
            JobVolumeDetails jvd = iterator.next();
            jobStepList.add(jvd.getJobStep());
        }
        
        return jobStepList;
    }

    @Override
    public List<Volume> getVolumesFor(JobStep js) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeDetails> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(JobVolumeDetails.class);
            criteria.add(Restrictions.eq("jobStep", js));
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        for (Iterator<JobVolumeDetails> iterator = result.iterator(); iterator.hasNext();) {
            JobVolumeDetails jvd = iterator.next();
            volumeList.add(jvd.getVolume());
        }
        
        return volumeList;
    }

    @Override
    public JobVolumeDetails getJobVolumeDetails(JobStep js, Volume v) {
        
        
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeDetails> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(JobVolumeDetails.class);
            criteria.add(Restrictions.eq("jobStep", js));
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            transaction.commit();
            
            if(result.size()>0) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
        
    }
    
    
}
