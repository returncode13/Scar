/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author adira0150
 */
public class DataBaseSettingsNode extends AnchorPane{
    
    private FXMLLoader fXMLLoader;
    private final URL location;
    private DataBaseSettingsController dsc;
    
    public DataBaseSettingsNode(DataBaseSettings dbm){
        this.location=getClass().getClassLoader().getResource("landingResources/settings/database/databaseSettings.fxml");
        System.out.println("landing.settings.database.DataBaseSettingsNode.<init>(): "+location.toString());
        fXMLLoader=new FXMLLoader();
        fXMLLoader.setLocation(location);
        fXMLLoader.setRoot(this);
        fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        try{
            fXMLLoader.load(location.openStream());
            dsc=(DataBaseSettingsController)fXMLLoader.getController();
            dsc.setModel(dbm);
            dsc.setView(this);
        } catch (IOException ex) {
            Logger.getLogger(DataBaseSettingsNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataBaseSettingsController getDataBaseSettingsController() {
        return dsc;
    }
    
    
    
    
}
