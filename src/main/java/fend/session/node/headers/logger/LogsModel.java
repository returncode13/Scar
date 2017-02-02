/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author naila0152
 */
public class LogsModel {
    private List<VersionLogsModel> logsmodel=new ArrayList<>();

    

    public List<VersionLogsModel> getLogsmodel() {
        return logsmodel;
    }

    public void setLogsmodel(List<VersionLogsModel> logsmodel) {
        this.logsmodel = logsmodel;
    }
    
    
}
