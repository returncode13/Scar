/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcTypeDAO;
import db.dao.QcTypeDAOImpl;
import db.model.QcType;
import db.model.Sessions;
import java.util.List;

/**
 *
 * @author adira0150
 */
public class QcTypeServiceImpl implements QcTypeService{
    
    QcTypeDAO qcDao=new QcTypeDAOImpl();
    
    
    @Override
    public void createQcType(QcType qctype) {
        qcDao.createQcType(qctype);
    }

    @Override
    public void updateQcType(Long qid, QcType newQcType) {
        qcDao.updateQcType(qid, newQcType);
    }

    @Override
    public QcType getQcType(Long qid) {
        return qcDao.getQcType(qid);
    }

    @Override
    public void deleteQcType(Long qid) {
        qcDao.deleteQcType(qid);
    }

    /*@Override
    public List<QcType> getQcTypesForSession(Sessions sessions) {
    return qcDao.getQcTypesForSession(sessions);
    }*/

    @Override
    public List<QcType> getAllQcTypes() {
        return qcDao.getAllQcTypes();
    }
    
}
