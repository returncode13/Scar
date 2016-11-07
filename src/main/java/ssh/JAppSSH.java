/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
This class is a test class . USed to check ssh connection. Just run this file to verify ssh connections

*/


package ssh;

public class JAppSSH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // TODO code application logic here
        System.out.println("sendCommand");

     
    /*
     Kiba Params
     */
     String sshUserName = "irdb";
     String sshPassword = "rdV9BPk9WS";
     String sshRemoteHost = "192.168.56.101"; 
     int sshLocalPort=1111;   //Kiba
     int dbPort=5432;
     String strDbUSer="irdb";  //Kiba
     String DBPass="password";  //Kiba
     
     /*
     End of Kiba Params
     */
    
        
     /* Dubai Parameters    
   
     
     String sshUserName = "irdb";
     String sshPassword = "rdV9BPk9WS";
     String sshRemoteHost = "10.11.1.39";                                     //vm in Dubai
     int sshLocalPort=5432;
     int dbPort=5432;
     String strDbUSer="postgres";
     String DBPass="";
     
     
     */  // End of Dubai Params
     
     String command = "pwd";
     SSHManager instance = new SSHManager(sshUserName, sshPassword, sshRemoteHost, ""); 
     String errorMessage = instance.connect();
            
     
     if(errorMessage != null)
     {
        System.out.println(errorMessage);
        
       // fail();
     }
     else{
         String errorMessage1=instance.setPortForwarding(sshLocalPort, dbPort);
         System.out.println(errorMessage1);
         String ls=instance.sendCommand(command);
         System.out.println(ls);
     }

     
    }
    
}