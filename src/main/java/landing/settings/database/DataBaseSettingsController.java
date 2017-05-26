/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import hibUtil.HibernateUtil;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import landing.LandingController;
import landing.settings.ssh.SShSettings;
import ssh.SSHManager;

/**
 *
 * @author adira0150
 */
public class DataBaseSettingsController extends Stage{
    
    private static SSHManager instance;
    final private String urltemplate="jdbc:postgresql://localhost:5432/template1";
    final private String url="jdbc:postgresql://localhost:5432/";
    private static int sshLocalPort=5432;   
    private static int dbPort=5432;
    
    private DataBaseSettings dbsmodel;
    private DataBaseSettingsNode dbnode;
    private DbListModel dbListModel=new DbListModel();
    DbListNode dbListnode;
    
    private String dbUser="fgeo";
    private String dbPassword="";
    private String dbase;
    private ArrayList<String> dbList=new ArrayList<>();
    
    private final String statement="SELECT datname FROM pg_database WHERE datistemplate=false;"; 
    
    private StringProperty dbnameproperty=new SimpleStringProperty();
    
    
    @FXML
    private TextField chosenDbTextField;

    @FXML
    private Button lookUpDbButton;
    @FXML
    private Button applyButton;

    @FXML
    private Button cancelButton;
    
      
    @FXML
    private TextField dbuserTextField;

    @FXML
    private TextField dbPasswordTextField;

    
    private String connectionIP;
    private String userName;
    private String password;
    
    
    @FXML
    void applySettings(ActionEvent event) {
        dbsmodel.setDbUser(dbUser);
        dbsmodel.setDbPassword(dbPassword);
        dbase=dbListModel.getSelectedDatabase();
        dbase=url+dbase;
        dbsmodel.setChosenDatabase(dbase);
        
        close();
    }

    @FXML
    void cancel(ActionEvent event) {
        System.out.println("landing.settings.database.DataBaseSettingsController.cancel(): Use Existingsettings");
        close();    
    }

  
    
    
    
    @FXML
    void lookUpDatabases(ActionEvent event) throws JAXBException {
        File sFile=new File(LandingController.getSshSettingXml());
         
         JAXBContext contextObj;
        try {
         contextObj = JAXBContext.newInstance(SShSettings.class);
         Unmarshaller unm=contextObj.createUnmarshaller();
         SShSettings sett=(SShSettings) unm.unmarshal(sFile);
         
         if(!sett.isPopulated()){
             System.err.println("Warning!: SSH Settings not Found! under "+sFile);
         }
         else{
             connectionIP=sett.getSshHost();
             userName=sett.getSshUser();
             password=sett.getSshPassword();
         }
        }catch (JAXBException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        instance = new SSHManager(userName, password, connectionIP, "");
    String errorMessage = instance.connect();
       
        
        try{
           
              if(errorMessage != null)
                {
                 System.out.println("Failed to connect via SSH instance :"+errorMessage);
       // fail();
                }else
                   {
         String errorMessage1=instance.setPortForwarding(sshLocalPort, dbPort);
         System.out.println(errorMessage1);
     }
    }
        catch(Throwable ex){
            System.err.println("Initial SessionFactory Creation failed");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
        
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            Connection connection=DriverManager.getConnection(urltemplate,dbUser,null);
           
            PreparedStatement ps=connection.prepareStatement(statement);
            ResultSet resultSet=ps.executeQuery();
            dbList.clear();
            while(resultSet.next()){
                dbList.add(resultSet.getString(1));
                System.out.println("landing.settings.database.DataBaseSettingsController.lookUpDatabases(): "+resultSet.getString(1));
                
            }
            
            resultSet.close();
            connection.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
       
        dbListModel.setAvailableDatabases(dbList);
        dbListnode=new DbListNode(dbListModel);
        dbsmodel.setChosenDatabase(dbListModel.getSelectedDatabase());
        chosenDbTextField.setText(dbsmodel.getChosenDatabase());
        instance.close();
        
}
    
    void setModel(DataBaseSettings dbm){
        this.dbsmodel=dbm;
       // dbnameproperty.bind();
      //  chosenDbTextField.textProperty().bind(dbnameproperty);
    }
    
    void setView(DataBaseSettingsNode adbnode){
        this.dbnode=adbnode;
        
        chosenDbTextField.setText(dbsmodel.getChosenDatabase());
        dbuserTextField.setText(dbsmodel.getDbUser());
        
        dbPasswordTextField.setText(dbsmodel.getDbPassword());
        this.setScene(new Scene(dbnode));
        this.showAndWait();
        
    }

}
