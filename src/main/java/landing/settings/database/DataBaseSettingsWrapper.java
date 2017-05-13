/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adira0150
 */
@XmlRootElement(name="databaseSettings")
public class DataBaseSettingsWrapper {
    private DataBaseSettings settings;
    
    @XmlElement(name="databaseSetting")
    
    public DataBaseSettings getSettings(){
        return settings;
        
    }

    public void setSettings(DataBaseSettings settings) {
        this.settings = settings;
    }
    
}
