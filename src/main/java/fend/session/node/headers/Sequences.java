/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.node.headers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author naila0152
 */
public class Sequences implements Serializable{
    ArrayList<SubSurface> subs=new ArrayList<>();
    private Long sequenceNumber;   
    private String subsurface;
    private String timeStamp;
    private Long traceCount;
    private Long inlineMax;
    private Long inlineMin;
    private Long inlineInc;
    private Long xlineMax;
    private Long xlineMin;
    private Long xlineInc;
    private Long dugShotMax;
    private Long dugShotMin;
    private Long dugShotInc;
    private Long dugChannelMax;
    private Long dugChannelMin;
    private Long dugChannelInc;
    private Long offsetMax;
    private Long offsetMin;
    private Long offsetInc;
    private Long cmpMax;
    private Long cmpMin;
    private Long cmpInc;
    
    
    
    
}
