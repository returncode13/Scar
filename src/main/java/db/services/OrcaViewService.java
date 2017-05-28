/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.OrcaView;
import db.model.Sequence;
import java.util.List;

/**
 *
 * @author naila0152
 */
public interface OrcaViewService {
     public List<OrcaView> getOrcaView();      //return the list of all lines shot till date (SELECT * from table)

    public List<OrcaView> getOrcaViewsForSeq(Sequence seq);

    public List<Long> getSeqOrcaView();
}
