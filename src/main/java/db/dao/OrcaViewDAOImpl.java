/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.OrcaView;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author naila0152
 */
public class OrcaViewDAOImpl implements OrcaViewDAO{

    @Override
    public List<OrcaView> getOrcaView() {
        Session session=HibernateUtil.getSessionFactory().openSession();
        List<OrcaView> result=null;
        Transaction transaction=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(OrcaView.class);
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
}
