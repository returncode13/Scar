/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Sequence;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SequenceService {
    public void createSequence(Sequence seq);
    public Sequence getSequence(Long sid);
    public void deleteSequence(Long sid);
    public void updateSequence(Long sid,Sequence newseq);
    public Sequence getSequenceObjByseqno(Long seqno);

    public List<Sequence> getSequenceList();
}
