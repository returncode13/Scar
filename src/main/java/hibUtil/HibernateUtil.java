/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibUtil;




import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import landing.LandingController;
import landing.settings.database.DataBaseSettings;
import landing.settings.ssh.SShSettings;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.service.ServiceRegistry;
import ssh.SSHManager;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author naila0152
 */
public class HibernateUtil {

    private static  SessionFactory sessionFactory;
    private static  EntityManagerFactory emfactory;  /* Dubai Params
     private static String command = "ls";
     private static String userName = "irdb";
     private static String password = "rdV9BPk9WS";
     private static String connectionIP = "10.11.1.39";     //vm in Dubai
     
     private static SSHManager instance; 
     private static int sshLocalPort=5432;
     private static int dbPort=5432;
     String strDbUSer="postgres";  //Dubai   . CHANGE this in the file hibernate.cfg.xml
     String DBPass="";  //Dubai      CHANGE this in the file hibernate.cfg.xml
    
    ALSO change the localport:1111 to localport:5432 in the file hibernate.cfg.xml when using for Dubai
    
    
    */ //End of Dubai Params
     
     /*
     Kiba Params
     */
     private static String command = "ls";
     private static String userName = "irdb";
     private static String password = "rdV9BPk9WS";
     private static String connectionIP = "192.168.56.101";     
     private static SSHManager instance; 
     private static int sshLocalPort=5432;    //Cannot bind to 5432 on Kiba. Permission issues??
     private static int dbPort=5432;
     private static String strDbUSer="irdb";  //Kiba   . CHANGE this in the file hibernate.cfg.xml
     private static String DBPass="password";  //Kiba      CHANGE this in the file hibernate.cfg.xml
     
     private static String database="";
     
     
    /*
     End of Kiba Params
     */
    
     
     
     
     
     
     
     
    private static SessionFactory buildSessionFactory(){
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory() : Loading the connection configurations "+LandingController.getSshSettingXml());
        
         File sFile=new File(LandingController.getSshSettingXml());
         File dbFile=new File(LandingController.getDbSettingXml());
         
         JAXBContext contextObj;
         JAXBContext dbcontext;
        try {
         contextObj = JAXBContext.newInstance(SShSettings.class);
         Unmarshaller unm=contextObj.createUnmarshaller();
         SShSettings sett=(SShSettings) unm.unmarshal(sFile);
         
         if(!sett.isPopulated()){
             System.err.println("Settings not Found!");
         }
         else{
             connectionIP=sett.getSshHost();
             userName=sett.getSshUser();
             password=sett.getSshPassword();
             strDbUSer=sett.getDbUser();                // this doesnt matter. since its specified in the file persistence.xml
             DBPass=sett.getDbPassword();               //ditto for this as well.
             
             
             
         }
         
         
         dbcontext = JAXBContext.newInstance(DataBaseSettings.class);
         Unmarshaller dbunm=dbcontext.createUnmarshaller();
         DataBaseSettings dbsett=(DataBaseSettings)dbunm.unmarshal(dbFile);
         
         database=dbsett.getChosenDatabase();
         
            
         
        } catch (JAXBException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
        
        
        
        System.out.println("HibernateUtil called using connection settings:");
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  HOST : "+connectionIP);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  USER : "+userName);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  PASS : "+password);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  DBASE: "+database);
        
        
        
        
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

    
     // call sendCommand for each command and the output 
     //(without prompts) is returned
    
     // close only after all commands are sent
    // instance.close();
     //assertEquals(expResult, result);
    
            
            
            
           /*
            Configuration configuration=new Configuration().setInterceptor(new AppInterceptor());
            //Configuration configuration=new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Configuration file loaded :"+configuration.getProperties().toString());
            System.out.println("Configuration file loaded :"+configuration.getProperty("hibernate.connection.username"));
            
            ServiceRegistry serviceRegistry =new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Service Registry built: "+serviceRegistry.toString());
            
            SessionFactory sessionFactory = configuration.buildSessionFactory( serviceRegistry);
            System.out.println("returning sessionFactory from buildSessionFactory: "+sessionFactory.toString() );
            return sessionFactory;
*/  
            Map<String,String> persistenceMap=new HashMap<>();
            persistenceMap.put("javax.persistence.jdbc.url",database);
            
           
            emfactory=Persistence.createEntityManagerFactory("landing_OBPManagerMaven-2_jar_1.0-SNAPSHOTPU",persistenceMap);
            //emfactory=Persistence.createEntityManagerFactory("landing_OBPManagerMaven-2_jar_1.0-SNAPSHOTPU");
            //emfactory=provider.createEntityManagerFactory("landing_OBPManagerMaven-2_jar_1.0-SNAPSHOTPU",null);
            String result = instance.sendCommand(command);
            //sessionFactory=emfactory.unwrap(SessionFactory.class);
            
            //sessionFactory=emfactory.unwrap(Session.class).getSessionFactory();
            sessionFactory=emfactory.unwrap(SessionFactory.class);
            System.out.println(result);
           return sessionFactory;
           
        }
        catch(Throwable ex){
            System.err.println("Initial SessionFactory Creation failed");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    
  
    
    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null ){sessionFactory=buildSessionFactory();if(sessionFactory!=null) System.out.println("returning sessionFactory from getSessionFactory");}
        return sessionFactory;
    }
}
