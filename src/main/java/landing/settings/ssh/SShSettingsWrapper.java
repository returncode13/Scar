/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.ssh;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sharath
 */
@XmlRootElement(name="sshSettings")
public class SShSettingsWrapper {
    private SShSettings settings;
    
    @XmlElement(name="sshSetting")
    
    public SShSettings getSettings(){
        return settings;
    }
    
    public void setSettings(SShSettings settings){
        this.settings=settings;
    }
}
