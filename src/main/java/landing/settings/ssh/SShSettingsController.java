package landing.settings.ssh;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sharath
 */
public class SShSettingsController extends Stage {

   
    
    
    
     private String NsshUser;
    private String NsshPass;
    private String NsshHost;
    private String NdbU;
    private String NdbPass;
    
    private SShSettingsNode snode;
    private SShSettings smodel;
    
     @FXML
    private TextField sshUserName;

    @FXML
    private TextField dbUser;

    @FXML
    private Button apply;

    @FXML
    private Button cancel;

    @FXML
    private PasswordField dbPassword;

    @FXML
    private PasswordField sshPassword;

    @FXML
    private TextField hostTextField;

    
    @FXML
    void applySettings(ActionEvent event) {
        smodel.setDbPassword(NdbPass);
        smodel.setDbUser(NdbU);
        smodel.setSshHost(NsshHost);
        smodel.setSshUser(NsshUser);
        smodel.setSshPassword(NsshPass);
        
        System.out.println("landing.settings.SettingsController.applySettings():" );
        System.out.println("host   : "+NsshHost);
        System.out.println("sshuser: "+NsshUser);
        System.out.println("sshPass: "+NsshPass);
        System.out.println("dbUser : "+NdbU);
        System.out.println("dbPass : "+NdbPass);
        
        close();
    }

    @FXML
    void cancel(ActionEvent event) {
        System.out.println("landing.settings.ssh.SShSettingsController.cancel(): Use Existing settings");
        close();    
    }

    @FXML
    void dbPasswordEntered(ActionEvent event) {
        NdbPass=dbPassword.getText();
        
    }
    
    @FXML
    void dbPassMouseExited(MouseEvent event) {
        NdbPass=dbPassword.getText();
    }
    
    
    @FXML
    void dbPasswordPressed(KeyEvent event) {
        NdbPass=dbPassword.getText();
    }

    @FXML
    void dbPasswordReleased(KeyEvent event) {
        NdbPass=dbPassword.getText();
    }

    @FXML
    void dbPasswordTyped(KeyEvent event) {
        NdbPass=dbPassword.getText();
    }
    

    @FXML
    void dbUserEntered(ActionEvent event) {
        NdbU=dbUser.getText();
        //System.out.println("landing.settings.SettingsController.dbUserEntered(): "+NdbU);
    }
    
     @FXML
    void dbUserMouseExited(MouseEvent event) {
        NdbU=dbUser.getText();
        //System.out.println("landing.settings.SettingsController.dbUserMouseExited(): "+NdbU);
    }

     @FXML
    void dbUserKeyPressed(KeyEvent event) {
         NdbU=dbUser.getText();
    }

    @FXML
    void dbUserKeyReleased(KeyEvent event) {
         NdbU=dbUser.getText();
    }

    @FXML
    void dbUserKeyTyped(KeyEvent event) {
         NdbU=dbUser.getText();
    }
    
    
   
    
    @FXML
    void sshPasswordEntered(ActionEvent event) {
        NsshPass=sshPassword.getText();
    }
    
     @FXML
    void sshPassMouseExited(MouseEvent event) {
        NsshPass=sshPassword.getText();
    }

    
    @FXML
    void sshPassKeyPressed(KeyEvent event) {
         NsshPass=sshPassword.getText();
    }

    @FXML
    void sshPassKeyReleased(KeyEvent event) {
         NsshPass=sshPassword.getText();
    }

    @FXML
    void sshPassKeyTyped(KeyEvent event) {
         NsshPass=sshPassword.getText();
    }

    @FXML
    void sshUserEntered(ActionEvent event) {
        NsshUser=sshUserName.getText();
    }
    
    
    @FXML
    void sshUserMouseExited(MouseEvent event) {
        NsshUser=sshUserName.getText();
    }
    
     @FXML
    void sshUserKeyPressed(KeyEvent event) {
         NsshUser=sshUserName.getText();
    }

    @FXML
    void sshUserKeyReleased(KeyEvent event) {
         NsshUser=sshUserName.getText();
    }

    @FXML
    void sshUserKeyTyped(KeyEvent event) {
         NsshUser=sshUserName.getText();
    }

    
    @FXML
    void hostTextAction(ActionEvent event) {
        NsshHost=hostTextField.getText();
    }

    @FXML
    void hostTextFieldMouseExited(MouseEvent event) {
        NsshHost=hostTextField.getText();
    }
    
    
    @FXML
    void hostKeyPressed(KeyEvent event) {
        NsshHost=hostTextField.getText();
    }

    @FXML
    void hostKeyReleased(KeyEvent event) {
        NsshHost=hostTextField.getText();
    }

    @FXML
    void hostKeyTyped(KeyEvent event) {
        NsshHost=hostTextField.getText();
    }

    
    
 



   






   
    
   

    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setModel(SShSettings lsm) {
        this.smodel=lsm;
    }

    void setView(SShSettingsNode aThis) {
        this.snode=aThis;
        if(smodel.isPopulated()){
            sshUserName.setText(smodel.getSshUser());
            dbUser.setText(smodel.getDbUser());
            dbPassword.setText(smodel.getDbPassword());
            sshPassword.setText(smodel.getSshPassword());
            hostTextField.setText(smodel.getSshHost());
        }
        this.setScene(new Scene(snode));
        this.showAndWait();
    }
    
}

 