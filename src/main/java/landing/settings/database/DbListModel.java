/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.settings.database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adira0150
 */
public class DbListModel {
    private List<String> availableDatabases=new ArrayList<>();
    private String selectedDatabase=new String();

    public String getSelectedDatabase() {
        return selectedDatabase;
    }

    public void setSelectedDatabase(String selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
    }
    
    

    public List<String> getAvailableDatabases() {
        return availableDatabases;
    }

    public void setAvailableDatabases(List<String> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }
 
    

   
}
