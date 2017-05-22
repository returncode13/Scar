/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.Sessions;
import db.model.User;
import hibUtil.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class UserDAOImpl implements UserDAO{

    @Override
    public void createUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateUser(Long uid, User newUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            User ll=(User) session.get(User.class,uid);
            ll.setUsername(newUser.getUsername());
            ll.setSessions(newUser.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public User getUser(Long uid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            User l= (User) session.get(User.class, uid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteUser(Long uid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            User h= (User) session.get(User.class, uid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<User> getUsersForSession(Sessions sessions) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<User> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(User.class);
            criteria.add(Restrictions.eq("sessions", sessions));
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
