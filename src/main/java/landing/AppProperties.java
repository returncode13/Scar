/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing;

/**
 *
 * @author sharath
 */
public class AppProperties {
    public static final String VERSION="0.0.4";
    private String project=new String("no project selected");
    private String sessionName=new String("unknown session");
    private String irdbHost=new String("no host assigned");

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getIrdbHost() {
        return irdbHost;
    }

    public void setIrdbHost(String irdbHost) {
        this.irdbHost = irdbHost;
    }

    
    
    
}
