/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcTable;
import db.model.QcType;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface QcTableDAO {
    public void createQcTable(QcTable qcm);
    public QcTable getQcTable(Long qid);
    public void updateQcTable(Long qid,QcTable newQ);
    public void deleteQcTable(Long qid);
    
    public List<QcTable> getQcTableFor(Volume v);
    public List<QcTable> getQcTableFor(Volume v,QcType qctype);               //for column wise retrieval
    
}
