/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.QcMatrix;
import db.model.SessionDetails;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface QcMatrixService {
    public void createQcMatrix(QcMatrix qcmatrix);
    public void updateQcMatrix(Long qid,QcMatrix newq);
    public QcMatrix getQcMatrix(Long qid);
    public void deleteQcMatrix(Long qid);
    
    //public List<QcMatrix> getQcMatrixForVolume(Volume v);    //get all the fields for volume v where present="true"
    public List<QcMatrix> getQcMatrixForSessionDetails(SessionDetails sd);
}
