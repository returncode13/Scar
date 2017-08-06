/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.reporter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ReporterController extends Stage{
    private final String htmlLocation="/d/home/adira0150/programming/php/submit.html";
    final File file=new File(htmlLocation);
    private URL url;
    
    @FXML
    private WebView webView;

    ReporterModel model;
    ReporterNode node;
    
    void setModel(ReporterModel lsm) {
       this.model=lsm;
        try {
            url=file.toURI().toURL();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
       
    }

    void setView(ReporterNode aThis) {
        node=aThis;
        final WebEngine webengine=webView.getEngine();
        webengine.load(url.toString());
        this.setTitle("Reporter");
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
}
