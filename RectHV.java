/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Webber
 */
public class RectHV {
    
   // construct the rectangle [xmin, xmax] x [ymin, ymax]
   // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
   public RectHV(double xmin, double ymin,double xmax, double ymax) {
       
   }      
   
   // minimum x-coordinate of rectangle
   public double xmin() {
       
   }       
   
   // minimum y-coordinate of rectangle
   public double ymin() {
       
   }   
   
   // maximum x-coordinate of rectangle
   public double xmax() {
       
   }  
   
   // maximum y-coordinate of rectangle
   public double ymax() {
       
   }  
   
   // does this rectangle contain the point p (either inside or on boundary)?
   public boolean contains(Point2D p) {
       
   }      
   
   // does this rectangle intersect that rectangle (at one or more points)?
   public boolean intersects(RectHV that) {
       
   }  
   
   // Euclidean distance from point p to the closest point in rectangle
   public double distanceTo(Point2D p) {
       
   }  
   
   // square of Euclidean distance from point p to closest point in rectangle
   public double distanceSquaredTo(Point2D p) {
       
   } 
   
   // does this rectangle equal that?
   public boolean equals(Object that) {
       
   }     
   
   // draw to standard draw
   public void draw() {
       
   }   
   
   // string representation
   public String toString() {
       
   }                       
}