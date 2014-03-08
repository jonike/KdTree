/**
 *
 * @author Webber Huang
 */

import java.util.Iterator;

public class PointSET {
    private static final double UNIT = 1.0;
    private final RedBlackBST points;      
    
    // construct an empty set of points
    public PointSET() {
       points = new RedBlackBST();
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
       points.put(p, (byte) 0);
   }     
   
   // does the set contain the point p?
   public boolean contains(Point2D p) {
       return points.contains(p);
   }           
   
   // draw all of the points to standard draw
   public void draw() {      
       Iterable<Point2D> queue = points.keys();
       Iterator<Point2D> iter = queue.iterator();
       while (iter.hasNext()) {
           iter.next().draw();
       }
   }   
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
       Stack<Point2D> s = new Stack<>();
       Point2D lo = new Point2D(rect.xmin(), rect.ymin());
       Point2D hi = new Point2D(rect.xmax(), rect.ymax());
       Iterable<Point2D> queue = points.keys(lo, hi);
       Iterator<Point2D> iter = queue.iterator();
       while (iter.hasNext()) {
           Point2D p = iter.next();
           if (p.x() >= rect.xmin() && p.x() <= rect.xmax())
               s.push(p);
       }
       return s;
   }    
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p) {
       if (isEmpty()) return null;
       
       double nearestDist = 2*UNIT*UNIT;
       Point2D nearestPoint = new Point2D(0.0, 0.0);
       Iterable<Point2D> queue = points.keys();
       Iterator<Point2D> iter = queue.iterator();
       while (iter.hasNext()) {
           Point2D curr = iter.next();
           double currDist = curr.distanceSquaredTo(p);
           if (currDist < nearestDist) {
               nearestDist = currDist;
               nearestPoint = curr;
           }
       }       
       return nearestPoint;
   }              
}