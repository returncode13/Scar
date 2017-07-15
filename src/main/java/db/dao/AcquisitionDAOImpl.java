/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Acquisition;
import hibUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    
   
    
}
