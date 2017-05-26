/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Volume;
import db.model.Workflow;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author adira0150
 */
public class WorkflowDAOImpl implements WorkflowDAO {

    @Override
    public void createWorkFlow(Workflow W) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(W);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Workflow getWorkflow(Long wid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Workflow l= (Workflow) session.get(Workflow.class, wid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateWorkFlow(Long wid, Workflow newW) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteWorkFlow(Long wid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Workflow> getWorkFlowsFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            transaction.commit();
            if(result==null || result.size()==0)
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Workflow> getWorkFlowWith(String md5,Volume vol) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", vol));
            criteria.add(Restrictions.eq("md5sum", md5));
            result=criteria.list();
            if(result==null || result.size()==0)
                return null;
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public Workflow getWorkFlowVersionFor(Volume v) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.addOrder(Order.desc("wfversion"));
            result=criteria.list();
            transaction.commit();
            if(result==null || result.size()==0)
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }
    
}
