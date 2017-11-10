/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.RawSeqInfo;
import db.model.Volume;
import hibUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class RawSeqInfoDAOImpl implements RawSeqInfoDAO{

    @Override
    public RawSeqInfo getRawSeqInfo(Long seq) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            RawSeqInfo ll=(RawSeqInfo) session.get(RawSeqInfo.class,seq);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public List<RawSeqInfo> getListOfRawSeqInfo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<RawSeqInfo> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(RawSeqInfo.class);
           
            
            result=criteria.list();
            transaction.commit();
            
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
    }
    
}
