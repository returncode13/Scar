/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.NodeProperty;
import db.model.NodeType;
import db.model.PropertyType;
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
public class NodePropertyDAOImpl implements NodePropertyDAO{

    @Override
    public void createNodeProperty(NodeProperty np) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(np);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public NodeProperty getNodeProperty(Long npid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            NodeProperty ll=(NodeProperty) session.get(NodeProperty.class,npid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateNodeProperty(Long npid, NodeProperty newNp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodeProperty ll=(NodeProperty) session.get(NodeProperty.class,npid);
            ll.setNodeType(newNp.getNodeType());
            ll.setPropertyType(newNp.getPropertyType());
            
          //  ll.setSessions(newQcType.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteNodeProperty(Long npid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodeProperty ll=(NodeProperty) session.get(NodeProperty.class,npid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<NodeProperty> getPropertyTypesFor(NodeType nodeType) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodeProperty> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodeProperty.class);
            criteria.add(Restrictions.eq("nodeType", nodeType));
            
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

    @Override
    public NodeProperty getNodeProperty(NodeType ntype, PropertyType protype) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodeProperty> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodeProperty.class);
            criteria.add(Restrictions.eq("nodeType", ntype));
            criteria.add(Restrictions.eq("propertyType", protype));            
            
            result=criteria.list();
            transaction.commit();
            if(result.size()>1){
                
            }if(result.size()==1){
                return result.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
    }

   
}
