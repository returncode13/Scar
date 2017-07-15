/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.OrcaKey;
import db.model.OrcaView;
import db.model.Sequence;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

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

    @Override
    public List<OrcaView> getOrcaViewsForSeq(Sequence seq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<OrcaView> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(OrcaView.class);
            //criteria.createAlias("orcaid", "i");
           // criteria.createAlias("orcaid.sequence", "seq");
           // criteria.add(Restrictions.eq("id", "i"));
           /*
            Criteria criteria=session.createCriteria(Logs.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sequence"),"sequence")));
            criteria.setResultTransformer(Transformers.aliasToBean(Logs.class));
           */
            criteria.add(Restrictions.eq("orcaid.sequence", seq.getSequenceno()));
            
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
    public List<Long> getSeqOrcaView() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Long> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(OrcaView.class);
            //criteria.createAlias("orcaid", "i");
           // criteria.createAlias("orcaid.sequence", "seq");
           // criteria.add(Restrictions.eq("id", "i"));
           
           // Criteria criteria=session.createCriteria(Logs.class);
            //criteria.add(Restrictions.eq("volume", v));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("orcaid.sequence"),"orcaid.sequence")));
            //criteria.setResultTransformer(Transformers.aliasToBean(OrcaKey.class));
          // criteria.setResultTransformer(Transformers.aliasToBean(OrcaView.class));
            //criteria.add(Restrictions.eq("orcaid.sequence", seq.getSequenceno()));
           
            result=criteria.list();
             System.out.println("db.dao.OrcaViewDAOImpl.getSeqOrcaView() result.size(): "+result.size());
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }
    
}
