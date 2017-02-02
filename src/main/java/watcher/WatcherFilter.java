/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watcher;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author naila0152
 */
class WatcherFilter implements FileFilter{

    public String filter;
    
    public WatcherFilter(){
        this.filter="";
    }
    
    public WatcherFilter(String filter) {
        this.filter=filter;
    }

    @Override
    public boolean accept(File pathname) {
        if("".equals(filter)){
            return true;
        }
        return (pathname.getName().endsWith(filter));
    }
    
}
