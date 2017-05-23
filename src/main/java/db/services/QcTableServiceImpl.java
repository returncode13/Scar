/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcTableDAOImpl;
import db.dao.QcTypeDAO;
import db.model.QcTable;
import db.model.QcType;
import db.model.Sessions;
import db.model.Volume;
import java.util.List;
import db.dao.QcTableDAO;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableServiceImpl implements QcTableService{
    
    QcTableDAO qcmDAO=new QcTableDAOImpl();
    
    @Override
    public void createQcTable(QcTable qcm) {
        qcmDAO.createQcTable(qcm);
    }

    @Override
    public QcTable getQcTable(Long qid) {
        return qcmDAO.getQcTable(qid);
    }

    @Override
    public void updateQcTable(Long qid, QcTable newQ) {
        qcmDAO.updateQcTable(qid, newQ);
    }

    @Override
    public void deleteQcTable(Long qid) {
        qcmDAO.deleteQcTable(qid);
    }

    @Override
    public List<QcTable> getQcTableFor(Volume v) {
        return qcmDAO.getQcTableFor(v);
    }

    @Override
    public List<QcTable> getQcTableFor(Volume v, QcType qctype) {
        return qcmDAO.getQcTableFor(v, qctype);
    }

   
    
}
