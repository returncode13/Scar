/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Acquisition;
import hibUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionDAOImpl implements AcquisitionDAO{

    @Override
    public void createAcquisition(Acquisition acq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(acq);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Acquisition getAcquisition(Long aid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Acquisition l= (Acquisition) session.get(Acquisition.class, aid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteAcquisition(Long aid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Acquisition l= (Acquisition) session.get(Acquisition.class, aid);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateAcquisition(Long aid, Acquisition newAcq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Acquisition l= (Acquisition) session.get(Acquisition.class, aid);
            l.setSequence(newAcq.getSequence());
            l.setCable(newAcq.getCable());
            l.setFgsp(newAcq.getFgsp());
            l.setFirstChannel(newAcq.getFirstChannel());
            l.setFirstFFID(newAcq.getFirstFFID());
            l.setFirstGoodFFID(newAcq.getFirstGoodFFID());
            l.setFirstShot(newAcq.getFirstShot());
            l.setGun(newAcq.getGun());
            l.setLastChannel(newAcq.getLastChannel());
            l.setLastFFID(newAcq.getLastFFID());
            l.setLastGoodFFID(newAcq.getLastGoodFFID());
            l.setLgsp(newAcq.getLgsp());
            l.setLastShot(newAcq.getLastShot());
            l.setSubsurface(newAcq.getSubsurface());
            
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public List<Long> getCables() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Acquisition> result=null;
        List<Long> cables=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Acquisition.class);
            ProjectionList pList=Projections.projectionList();
            pList.add(Projections.property("cable"));
            criteria.setProjection(Projections.distinct(pList));
            criteria.addOrder(Order.desc("cable"));
            cables=criteria.list();
            transaction.commit();
            System.out.println("db.dao.AcquisitionDAOImpl.getCables(): returning cables: size:  "+cables.size());
           // System.out.println("db.dao.AcquisitionDAOImpl.getCable(): result.size() "+result.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
        /*for (Iterator<Acquisition> iterator = result.iterator(); iterator.hasNext();) {
        Acquisition next = iterator.next();
        System.out.println("db.dao.AcquisitionDAOImpl.getCables(): adding: "+next.getCable());
        cables.add(next.getCable());
        }*/
        System.out.println("db.dao.AcquisitionDAOImpl.getCables(): returning cables: size:  "+cables.size());
        return cables;
    }

    @Override
    public List<Long> getGuns() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Acquisition> result=null;
        List<Long> guns=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Acquisition.class);
            ProjectionList pList=Projections.projectionList();
            pList.add(Projections.property("gun"));
            criteria.setProjection(Projections.distinct(pList));
            criteria.addOrder(Order.desc("gun"));
            //result=criteria.list();
            guns=criteria.list();
            transaction.commit();
             System.out.println("db.dao.AcquisitionDAOImpl.getGuns(): returning guns: size: "+guns.size());
          //  System.out.println("db.dao.AcquisitionDAOImpl.getGuns(): result.size() "+result.size());
            /*for (Iterator<Acquisition> iterator = result.iterator(); iterator.hasNext();) {
            Acquisition next = iterator.next();
            System.out.println("db.dao.AcquisitionDAOImpl.getGuns(): adding : "+next.getGun());
            guns.add(next.getGun());
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
        
        
        System.out.println("db.dao.AcquisitionDAOImpl.getGuns(): returning guns: size: "+guns.size());
        return guns;
    }
    
   
    
}
