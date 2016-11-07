/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import db.model.Ancestors;
import db.model.JobStep;
import db.model.SessionDetails;
import db.model.Sessions;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import db.services.AncestorsService;
import db.services.AncestorsServiceImpl;
import db.services.SessionDetailsService;
import db.services.SessionDetailsServiceImpl;

/**
 *
 * @author sharath nair
 */
public class AppInterceptor extends EmptyInterceptor{
    
    @Override
    public void preFlush(Iterator entities) {
        super.preFlush(entities); //To change body of generated methods, choose Tools | Templates.
      //  System.out.println("Before committing");
        
     
        
       
    }

    @Override
    public void postFlush(Iterator entities) {
        super.postFlush(entities); //To change body of generated methods, choose Tools | Templates.
     //   System.out.println("After committing");
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
       if(entity instanceof JobStep){
       //     System.out.println("JobStep save operation");
        }
        if(entity instanceof Sessions)
        {
       //     System.out.println("Sessions save operation");
        }
        
        if(entity instanceof SessionDetails)
        {
        //    System.out.println("SessionDetails save operation");
        }
        return false;
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if(entity instanceof JobStep){
         //   System.out.println("JobStep load operation");
        }
        if(entity instanceof Sessions)
        {
          //  System.out.println("Sessions load operation");
        }
        
        if(entity instanceof SessionDetails)
        {
          //  System.out.println("SessionDetails load operation");
        }
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        
        if(entity instanceof JobStep){
          //  System.out.println("JobStep update operation");
        }
        if(entity instanceof Sessions)
        {
          //  System.out.println("Sessions update operation");
        }
        
        if(entity instanceof SessionDetails)
        {
          //  System.out.println("SessionDetails update operation");
        }
        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        super.onDelete(entity, id, state, propertyNames, types); //To change body of generated methods, choose Tools | Templates.
      //  System.out.println("Delete event");
    }
    
    
    
}
