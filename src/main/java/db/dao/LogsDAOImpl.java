/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Headers;
import db.model.Logs;
import db.model.Volume;
import db.model.Workflow;
import hibUtil.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author sharath nair  
 * sharath.nair@polarcus.com
 */
public class LogsDAOImpl implements LogsDAO{

    @Override
    public void createLogs(Logs l) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Logs getLogs(Long lid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Logs l= (Logs) session.get(Logs.class, lid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateLogs(Long lid, Logs newL) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Logs ll=(Logs) session.get(Logs.class,lid);
            ll.setHeaders(newL.getHeaders());
            ll.setSubsurfaces(newL.getSubsurfaces());
            ll.setInsightVersion(newL.getInsightVersion());
            ll.setLogpath(newL.getLogpath());
            ll.setTimestamp(newL.getTimestamp());
            ll.setVolume(newL.getVolume());
            ll.setCompletedsuccessfully(newL.getCompletedsuccessfully());
            ll.setErrored(newL.getErrored());
            ll.setRunning(newL.getRunning());
            ll.setCancelled(newL.getCancelled());
            ll.setWorkflow(newL.getWorkflow());
            ll.setUpdateTime(newL.getUpdateTime());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteLogs(Long lid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Logs h= (Logs) session.get(Logs.class, lid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Logs> getLogsFor(Headers h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("headers", h));
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
    public List<Logs> getLogsFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
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
    public List<Logs> getLogsFor(Volume v, String subline) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurfaces", subline));
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
    public Logs getLatestLogFor(Volume v, String subline) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurfaces", subline));
            criteria.addOrder(Order.desc("timestamp"));
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }

    @Override
    public List<Logs> getLogsFor(Volume v, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("completedsuccessfully", completed));
            criteria.add(Restrictions.eq("running", running));
            criteria.add(Restrictions.eq("errored", errored));
            criteria.add(Restrictions.eq("cancelled", cancelled));
            
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
    public List<Logs> getLogsFor(Volume v, String subline, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurfaces", subline));
            criteria.add(Restrictions.eq("completedsuccessfully", completed));
            criteria.add(Restrictions.eq("running", running));
            criteria.add(Restrictions.eq("errored", errored));
            criteria.add(Restrictions.eq("cancelled", cancelled));
            
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
    public List<Logs> getLogsFor(Volume v, Workflow workflow) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        List<Workflow> reswf=null;
        try{
            transaction=session.beginTransaction();
           
                Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            //criteria.add(Restrictions.eq("workflow", workflow));
            criteria.add(Restrictions.or(Restrictions.isNull("workflow"),Restrictions.eq("workflow", workflow)));
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
    public List<Logs> getLogsFor(Volume v, Long seq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("sequence", seq));
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
    public List<Logs> getSequencesFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sequence"),"sequence")));
            criteria.setResultTransformer(Transformers.aliasToBean(Logs.class));
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
    public List<Logs> getSubsurfacesFor(Volume v, Long seq) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Logs> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("sequence", seq));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("subsurfaces"),"subsurfaces")));
            criteria.setResultTransformer(Transformers.aliasToBean(Logs.class));
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
