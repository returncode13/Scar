/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import hibUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import db.model.Headers;
import db.model.Volume;
import fend.session.node.headers.SubSurface;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class HeadersDAOImpl implements HeadersDAO{

    @Override
    public void createHeaders(Headers h) {
               
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Headers getHeaders(Long hid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Headers h= (Headers) session.get(Headers.class, hid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateHeaders(Long hid, Headers newH) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Headers h= (Headers) session.get(Headers.class, hid);
            
            if(h.getSequenceNumber()==newH.getSequenceNumber() && h.getSubsurface().equals(newH.getSubsurface())){
            h.setTimeStamp(newH.getTimeStamp());
            h.setCmpInc(newH.getCmpInc());
            h.setCmpMax(newH.getCmpMax());
            h.setCmpMin(newH.getCmpMin());
            
            h.setDugChannelInc(newH.getDugChannelInc());
            h.setDugChannelMax(newH.getDugChannelMax());
            h.setDugChannelMin(newH.getDugChannelMin());
            
            h.setDugShotInc(newH.getDugShotInc());
            h.setDugShotMax(newH.getDugShotMax());
            h.setDugShotMin(newH.getDugShotMin());
            
            h.setInlineInc(newH.getInlineInc());
            h.setInlineMax(newH.getInlineMax());
            h.setInlineMin(newH.getInlineMin());
            
            h.setOffsetInc(newH.getOffsetInc());
            h.setOffsetMax(newH.getOffsetMax());
            h.setOffsetMin(newH.getOffsetMin());
            
            h.setTraceCount(newH.getTraceCount());
            h.setVersion(newH.getVersion());
            h.setModified(newH.getModified());
            /*if(newH.getModified()){
            h.setModified(Boolean.FALSE);
            }*/
            session.update(h);
                System.out.println("db.dao.HeadersDAOImpl: updating entry for subsurface : "+newH.getSubsurface()+" with id: "+newH.getIdHeaders() );
            }
            else{
                throw new Exception("The id belongs to a different seq/subsurface compared to the ones that the new header value refers to!!. ");
            }
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void deleteHeaders(Long hid) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Headers h= (Headers) session.get(Headers.class, hid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public List<Headers> getHeadersFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Headers> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Headers.class);
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
    public void setHeadersFor(Volume v, List<Headers> headers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateHeaders(Volume v, List<Headers> headers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Headers> result=null;
        List<Headers> toBedeleted=new ArrayList<>();
        List<Headers> toBeUpdated=new ArrayList<>();
        List<Headers> toBeAdded=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Headers.class);
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            
            //the volume at this point has either more number of subsurfaces for update or less
            
            if(result.size()>headers.size())                                            //more subsurface lines in the extracted entries as compared to the the latest header extract
            {
              for (Iterator<Headers> iterator = result.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                for (Iterator<Headers> iterator1 = headers.iterator(); iterator1.hasNext();) {
                    Headers n = iterator1.next();
                    if(n.getSubsurface().equals(next.getSubsurface()))                  //since subsurface is what interests us.
                    {
                        n.setIdHeaders(next.getIdHeaders());
                        toBeUpdated.add(n);
                        if(toBedeleted.contains(n))toBedeleted.remove(n);
                        session.update(n);
                    }else
                    {
                       toBedeleted.add(n);
                        
                    }
                    
                         
                    
                }
                
                  
                  
                
              }
              
              
              for (Iterator<Headers> iterator1 = toBedeleted.iterator(); iterator1.hasNext();) {
                      Headers nn = iterator1.next();
                        for (Iterator<Headers> r = result.iterator(); r.hasNext();) {
                      Headers next = r.next();
                      if(next.getSubsurface().equals(nn.getSubsurface()))
                      {
                          nn.setIdHeaders(next.getIdHeaders());
                      }
                      
                  }
                        
                  for (Iterator<Headers> iterator = toBedeleted.iterator(); iterator.hasNext();) {
                      Headers next = iterator.next();
                      this.deleteHeaders(next.getIdHeaders());
                      
                  }
           
                        
                      
                  }
              
            }
            else{
                if(result.size()<=headers.size()){
                    for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
                         Headers next = iterator.next();
                    for (Iterator<Headers> iterator1 = result.iterator(); iterator1.hasNext();) {
                         Headers n = iterator1.next();
                         if(n.getSubsurface().equals(next.getSubsurface()))                  //since subsurface is what interests us.
                         {
                          next.setIdHeaders(n.getIdHeaders());
                          toBeUpdated.add(next);
                          if(toBeAdded.contains(next)){toBeAdded.remove(next);}
                           session.update(n);
                          }else
                         {
                          toBeAdded.add(next);
                        
                          }
                    
                         
                    
                }
                
                  
                  
                
              }
                    
                    for(Iterator<Headers> iterator1=toBeAdded.iterator();iterator1.hasNext();){
                        Headers nn=iterator1.next();
                        
                        this.createHeaders(nn);
                    }
                }
            }
            
            
            
            
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Volume> getVolumesContaining(String subsurface) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Headers> result=null;
        Set<Volume> volumeSet=new HashSet<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Headers.class);
            criteria.add(Restrictions.eq("subsurface", subsurface));
            
            result=criteria.list();
            if(result.size()>0){
                System.out.println("res >0 :"+result.size());
            }else
            {System.out.println("res=0");}
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        if(result.size()==0){
            System.out.println("result is zero");
        }
           
            
            for (Iterator<Headers> iterator = result.iterator(); iterator.hasNext();) {
                Headers next = iterator.next();
                System.out.println("Adding: "+next.getVolume());
                volumeSet.add(next.getVolume());
                
            }
        
        return volumeSet;
        
    }

    @Override
    public List<Headers> getHeadersFor(Volume v, String s) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Headers> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Headers.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurface", s));
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
