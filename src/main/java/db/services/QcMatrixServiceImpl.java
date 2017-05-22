/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcMatrixDAO;
import db.dao.QcMatrixDAOImpl;
import db.dao.QcTypeDAO;
import db.model.QcMatrix;
import db.model.QcType;
import db.model.Sessions;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixServiceImpl implements QcMatrixService{
    
    QcMatrixDAO qcmDAO=new QcMatrixDAOImpl();
    
    @Override
    public void createQcMatrix(QcMatrix qcm) {
        qcmDAO.createQcMatrix(qcm);
    }

    @Override
    public QcMatrix getQcMatrix(Long qid) {
        return qcmDAO.getQcMatrix(qid);
    }

    @Override
    public void updateQcMatrix(Long qid, QcMatrix newQ) {
        qcmDAO.updateQcMatrix(qid, newQ);
    }

    @Override
    public void deleteQcMatrix(Long qid) {
        qcmDAO.deleteQcMatrix(qid);
    }

    @Override
    public List<QcMatrix> getQcMatrixFor(Volume v) {
        return qcmDAO.getQcMatrixFor(v);
    }

    @Override
    public List<QcMatrix> getQcMatrixFor(Volume v, QcType qctype) {
        return qcmDAO.getQcMatrixFor(v, qctype);
    }

   
    
}
