/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcMatrixDAO;
import db.dao.QcMatrixDAOImpl;
import db.model.QcMatrix;
import db.model.QcType;
import db.model.SessionDetails;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixServiceImpl implements QcMatrixService{
    QcMatrixDAO qcmDao=new QcMatrixDAOImpl();
    
    @Override
    public void createQcMatrix(QcMatrix qcmatrix) {
        qcmDao.createQcMatrix(qcmatrix);
    }

    @Override
    public void updateQcMatrix(Long qid, QcMatrix newq) {
        qcmDao.updateQcMatrix(qid, newq);
    }

    @Override
    public QcMatrix getQcMatrix(Long qid) {
        return qcmDao.getQcMatrix(qid);
    }

    @Override
    public void deleteQcMatrix(Long qid) {
        qcmDao.deleteQcMatrix(qid);
    }

    /*@Override
    public List<QcMatrix> getQcMatrixForVolume(Volume v) {
    return qcmDao.getQcMatrixForVolume(v);
    }*/

    @Override
    public List<QcMatrix> getQcMatrixForSessionDetails(SessionDetails sd) {
        return qcmDao.getQcMatrixForSessionDetails(sd);
    }

    @Override
    public QcMatrix getQcMatrixFor(SessionDetails sd, QcType qctype) throws Exception {
        return qcmDao.getQcMatrixFor(sd, qctype);
    }

    @Override
    public List<QcMatrix> getQcMatrixForSessionDetails(SessionDetails sessDetails, boolean b) {
        return qcmDao.getQcMatrixForSessionDetails(sessDetails,b);
    }
    
}
