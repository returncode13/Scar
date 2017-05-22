/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcMatrix;
import db.model.QcType;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface QcMatrixDAO {
    public void createQcMatrix(QcMatrix qcm);
    public QcMatrix getQcMatrix(Long qid);
    public void updateQcMatrix(Long qid,QcMatrix newQ);
    public void deleteQcMatrix(Long qid);
    
    public List<QcMatrix> getQcMatrixFor(Volume v);
    public List<QcMatrix> getQcMatrixFor(Volume v,QcType qctype);               //for column wise retrieval
    
}
