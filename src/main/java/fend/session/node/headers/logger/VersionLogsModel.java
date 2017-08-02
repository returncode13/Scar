/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers.logger;

import fend.session.node.headers.SubSurfaceHeaders;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.max;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair
 */


/**
 * Class to hold the version of the logs
 * @author sharath nair
 */




 public class VersionLogsModel {
    
   Long version;
   String timestamp;
   File logfile;

    public VersionLogsModel(Long version, String timestamp, String logfile) {
        this.version = version;
        this.timestamp = timestamp;
        this.logfile = new File(logfile);
    }

    public Long getVersion() {
        return version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public File getLogfile() {
        return logfile;
    }
   
    
   
   
   
}
