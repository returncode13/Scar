/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sharath
 */
@XmlRootElement(name="settings")
public class SettingsWrapper {
    private Settings settings;
    
    @XmlElement(name="setting")
    
    public Settings getSettings(){
        return settings;
    }
    
    public void setSettings(Settings settings){
        this.settings=settings;
    }
}
