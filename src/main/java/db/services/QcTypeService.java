/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.QcType;
import db.model.Sessions;
import java.util.List;

/**
 *
 * @author adira0150
 */
public interface QcTypeService {
    public void createQcType(QcType qctype);
    public void updateQcType(Long qid,QcType newQcType);
    public QcType getQcType(Long qid);
    public void deleteQcType(Long qid);
    
  // public List<QcType> getQcTypesForSession(Sessions sessions);  //get qctypes for current session 
    public List<QcType> getAllQcTypes();
}
