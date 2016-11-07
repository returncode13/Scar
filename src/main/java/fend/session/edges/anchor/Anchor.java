/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.session.edges.anchor;

import java.util.UUID;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import fend.session.node.jobs.JobStepNode;
import fend.session.edges.Links;



/**
 *
 * @author naila0152
 */
public class Anchor extends Circle{
    
        
      private  Links link;
      private class Delta{double x,y;}
      private AnchorModel model=new AnchorModel();
      private static int aid=100;    
      private Integer id;
        public  Anchor(Color color,AnchorModel model){
              super(model.getCenterX().doubleValue(),model.getCenterY().doubleValue(),2);
              this.model=model;
              aid++;
              setFill(color.deriveColor(1, 1, 1, 0.5));
              setStroke(color);
              setStrokeWidth(1);
              setStrokeType(StrokeType.OUTSIDE);
              
              this.model.getCenterX().bindBidirectional(centerXProperty());
              this.model.getCenterY().bindBidirectional(centerYProperty());
              
              //model.bindBidirectional(centerXProperty());
              //y.bindBidirectional(centerYProperty());
              enableDrag();
              setId(UUID.randomUUID().getMostSignificantBits()+"");
              model.setId(Long.valueOf(getId()));
          }

    public AnchorModel getModel() {
        return model;
    }

    public Links getLink() {
        return link;
    }

    public void setLink(Links link) {
        this.link = link;
    }

    public Integer  getAId() {
        return id=aid;
    }
          
    
          
          
          
          
          
          
          private void enableDrag(){
              final Delta dragDelta = new Delta();
               setOnMousePressed(new EventHandler<MouseEvent>(){

                  @Override
                  public void handle(MouseEvent event) {
                      
                      
                      System.out.println(" ArrowsAndCurves: MousePressed: "+event.getScreenX()+","+event.getScreenY());
                      dragDelta.x=getCenterX()-event.getX();
                      dragDelta.y=getCenterY()-event.getY();
                      getScene().setCursor(Cursor.MOVE);
                     
                      
                    /*  System.out.println("P: "+getParent().getClass().toString());
                     System.out.println("PP: "+getParent().getParent().getClass().toString());
                     System.out.println("PPP: "+getParent().getParent().getParent().getClass().toString());
                     System.out.println("PPPP: "+getParent().getParent().getParent().getParent().getClass().toString());
                      System.out.println("PPPPP: "+getParent().getParent().getParent().getParent().getParent().getClass().toString());
                      
                      
                      */
                      
                      
                     setMouseTransparent(true);
                      
                      
                      
                      
                     //ScrollPane parent= (ScrollPane) getParent().getParent().getParent().getParent();
                     //parent.setMouseTransparent(true);
                      System.out.println("ArrowsAndCurves: Setting mousetransparent =true");
                      event.consume();
                  }
                  
                  
              });
               
               setOnMouseReleased(new EventHandler<MouseEvent>(){

                  @Override
                  public void handle(MouseEvent event) {
                  //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      
                      getScene().setCursor(Cursor.HAND);
                      //JobStepNode parent= (JobStepNode) getParent().getParent().getParent().getParent();
                    // EventHandler<MouseEvent> morig=parent.getmOnDragDetected();
                     // parent.setOnDragDetected(morig);
                    setMouseTransparent(false);
                     //parent.setMouseTransparent(false);
                     
                      //System.out.println("ArrowsAndCurves: Setting mousetransparent =false");
                       System.out.println(" ArrowsAndCurves: MouseReleased: "+event.getScreenX()+","+event.getScreenY()+" from source "+event.getSource()+" on target "+event.getTarget().toString());
                  }
                 
                      
              });
              
               setOnMouseDragged(new EventHandler<MouseEvent>(){


                  @Override
                  public void handle(MouseEvent event) {

                     // curve.getParent().setOnMouseDragged(null);
                      
                    // JobStepNode parent= (JobStepNode) getParent().getParent().getParent().getParent();                                              //get the jobstep that is parent to this.
                                                                                         
                   // parent.setOnDragDetected(null);                                                                                                       //set the onDragMethod in parent to null.
                      
                      
                    //  System.out.println(" ArrowsAndCurves: MouseDragged: "+event.getScreenX()+","+event.getScreenY());
                      double newX= event.getX()+dragDelta.x;
                      if(newX >0 && newX < getScene().getWidth()){
                          setCenterX(newX);
                      }
                      
                      double newY=event.getY()+dragDelta.y;
                      if(newY >0 && newY<getScene().getHeight()){
                          setCenterY(newY);
                      }
                      
                      
                    
                      
                      event.consume();
                  }
                  
              });
               
              setOnMouseDragReleased(new EventHandler<MouseDragEvent>(){                                                          // <<DELETE this       

                  @Override
                  public void handle(MouseDragEvent event) {
                  //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      System.out.println(" ArrowsAndCurves: MouseDrag Released: "+event.getScreenX()+","+event.getScreenY());
                  }
                  
              }); 
               
              setOnDragEntered(new EventHandler<DragEvent>() {                                                              //DELETE this

                  @Override
                  public void handle(DragEvent event) {
               //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 System.out.println(" ArrowsAndCurves: DragEntered: "+event.getScreenX()+","+event.getScreenY());
                  }
              });
               
              
              setOnDragDetected(new EventHandler<MouseEvent>() {

                  @Override
                  public void handle(MouseEvent event) {
                     // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                       System.out.println(" ArrowsAndCurves: Drag Detected: "+event.getScreenX()+","+event.getScreenY()+ ": eventype: "+event.getEventType());
                       startFullDrag();
                       
                       
                       
                       
                  }
              });
              
              
              setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {

                  @Override
                  public void handle(MouseDragEvent event) {
                     // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      //startFullDrag();
                      getParent().setOnMouseDragged(null); 
                   //   JobStepNode parent= (JobStepNode) getParent().getParent().getParent().getParent();                                              //get the jobstep that is parent to this.
                    //  EventHandler<MouseEvent> morig=parent.getmOnDragDetected();                                                                           //get its onDrag handler   <--pointless here
                     // parent.setOnDragDetected(null);                                                                                                       //set the onDragMethod in parent to null.
                      
                      
                      System.out.println(" ArrowsAndCurves: MouseDraggEntered MouseDrag: "+event.getScreenX()+","+event.getScreenY());
                     /*
                      
                      double newX= event.getX()+dragDelta.x;
                      if(newX >0 && newX < getScene().getWidth()){
                          setCenterX(newX);
                      }
                      
                      double newY=event.getY()+dragDelta.y;
                      if(newY >0 && newY<getScene().getHeight()){
                          setCenterY(newY);
                      }
                      
                      */
                    
                      /*
                       double newX=dragDelta.x+event.getSceneX();
                      if(newX >0 && newX < getScene().getWidth()){
                          setCenterX(newX);
                      }
                      
                      double newY=dragDelta.y+event.getSceneY();
                      if(newY >0 && newY<getScene().getHeight()){
                          setCenterY(newY);
                      }
                      
                      */
                      
                      double newX=dragDelta.x+event.getScreenX();
                      if(newX >0 && newX < getScene().getWidth()){
                          setCenterX(newX);
                      }
                      
                      double newY=dragDelta.y+event.getScreenY();
                      if(newY >0 && newY<getScene().getHeight()){
                          setCenterY(newY);
                      }
                      
                      event.consume();
                      
                  }
              });
              
              setOnMouseEntered(new EventHandler<MouseEvent>(){                                     // << DELETE this

                  @Override
                  public void handle(MouseEvent event) {
                  //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      if(!event.isPrimaryButtonDown()){
                          getScene().setCursor(Cursor.HAND);
                           System.out.println(" ArrowsAndCurves: MouseEntered: "+event.getScreenX()+","+event.getScreenY()+"Event Type: "+event.getEventType().toString());
                      }
                  }
                  
              });
              
              
              addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>(){

                  @Override
                  public void handle(MouseEvent event) {
                      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      System.out.println(" ArrowsAndCurves: MouseEntered  Target: "+event.getScreenX()+","+event.getScreenY()+"Event Type: "+event.getEventType().toString() + " Target: "+event.getTarget().toString());
                  }
                  
              });
              setOnMouseExited(new EventHandler<MouseEvent>(){                                        // <<DELETE this

                  @Override
                  public void handle(MouseEvent event) {
                  //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      getScene().setCursor(Cursor.DEFAULT);
                      
                       System.out.println(" ArrowsAndCurves: MouseExited: "+event.getScreenX()+","+event.getScreenY());
                  }
                  
              });
              
              
          }
          
          
          
      }
      

