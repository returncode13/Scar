/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.UserDAO;
import db.dao.UserDAOImpl;
import db.model.Sessions;
import db.model.User;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public class UserServiceImpl implements UserService {
    
    UserDAO uDao= new UserDAOImpl();
    
    @Override
    public void createUser(User user) {
        uDao.createUser(user);
    }

    @Override
    public void updateUser(Long uid, User newUser) {
        uDao.updateUser(uid, newUser);
    }

    @Override
    public User getUser(Long uid) {
        return uDao.getUser(uid);
    }

    @Override
    public void deleteUser(Long uid) {
        uDao.deleteUser(uid);
    }

    @Override
    public List<User> getUsersForSession(Sessions sessions) {
        return uDao.getUsersForSession(sessions);
    }
    
}
