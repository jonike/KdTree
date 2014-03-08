/**
 *
 * @author Webber Huang
 */

import java.util.Iterator;

public class PointSET {
    private static final double UNIT = 1.0;
    private final SET points;    
    
    // construct an empty set of points
    public PointSET() {
       points = new SET();
   }      
    
   // is the set empty?
   public boolean isEmpty() {
       return points.isEmpty();
   }     
   
   // number of points in the set
   public int size() {
       return points.size();
   } 
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p) {
       points.add(p);
   }     
   
   // does the set contain the point p?
   public boolean contains(Point2D p) {
       return points.contains(p);
   }           
   
   // draw all of the points to standard draw
   public void draw() {       
       for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
           iter.next().draw();
       }
   }   
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
       Stack<Point2D> s = new Stack<>();
       for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
           Point2D p = iter.next();
           if (rect.contains(p)) s.push(p);
       }
       return s;
   }    
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p) {
       if (isEmpty()) return null;
       
       double nearestDist = 2*UNIT*UNIT;
       Point2D nearestPoint = new Point2D(0.0, 0.0);
       for (Iterator<Point2D> iter = points.iterator(); iter.hasNext();) {
           Point2D currPoint = iter.next();
           double currDist = p.distanceSquaredTo(currPoint);
           if (currDist < nearestDist) {
               nearestDist = currDist;
               nearestPoint = currPoint;
           }
       }       
       return nearestPoint;
   }              
}