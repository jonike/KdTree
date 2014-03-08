/**
 *
 * @author Webber Huang
 */


public class KdTree {
    // static variate
    private static final int K = 2;    
    
    private Node root;    
    private int count;
    
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle 
                                // corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        
        public Node(Point2D p, RectHV rect) {
            this.p = p; 
            this.rect = rect; 
        }
    }
    // construct an empty set of points
    public KdTree() {
       root = null;
       count = 0;
    }      
    
    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }     

    // number of points in the set
    public int size() {
        return count;
    } 

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, null, p, 0);  
    }     

    private Node insert(Node x, Node parent, Point2D p, int i) {
        if (x == null) {
            count++;
            if (parent == null) 
                return new Node(p, new RectHV(0, 0, 1, 1));
                        
            return new Node(p, getRect(p, parent, i-1));
        }
        int cmp = compare(p, x.p, i);
        
        if (cmp < 0)      x.lb = insert(x.lb, x, p, i+1);
        else if (cmp > 0) x.rt = insert(x.rt, x, p, i+1);
        else              x.p = p;       
        return x;
    }

    // helper method for compare L/R and B/T
    private int compare(Point2D p, Point2D q, int deep) {
        int level = (int) deep % K;
        if (level == 0) {
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
        }
        else  if (level == 1) {
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
        }
        return 0;
    }

    // get axis-aligned rectangle 
    private RectHV getRect(Point2D p, Node parent, int i) {
        int level = i % K;
        int cmp = compare(p, parent.p, i);
        RectHV r = parent.rect;
        
        if (level == 0) {
            if (cmp < 0) 
                return new RectHV(r.xmin(), r.ymin(), parent.p.x(), r.ymax());
            else if (cmp > 0) 
                return new RectHV(parent.p.x(), r.ymin(), r.xmax(), r.ymax());
        }
        else if (level == 1) {
            if (cmp < 0) 
                return new RectHV(r.xmin(), r.ymin(), r.xmax(), parent.p.y());
            else if (cmp > 0) 
                return new RectHV(r.xmin(), parent.p.y(), r.xmax(), r.ymax());
        }
        return null;
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, 0);
    }   

    private boolean contains(Node x, Point2D p, int i) {
        if (x == null) return false;
        if (x.p.equals(p))  return true;
        int cmp = compare(p, x.p, i);
        if (cmp < 0)      return contains(x.lb, p, i+1);
        else if (cmp > 0) return contains(x.rt, p, i+1); 
        else              return true;
    }
   
   // draw all of the points to standard draw
    public void draw() {
        draw(root, 0);
    }
   
    private void draw(Node x, int i) {
        int level = i % K;
        // draw line first
        StdDraw.setPenRadius(.001);
        if (level == 0) {
            StdDraw.setPenColor(StdDraw.RED);            
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else if (level == 1) {
            StdDraw.setPenColor(StdDraw.BLUE);            
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        // draw dot
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.005);
        x.p.draw();
        // draw subTree
        if (x.lb != null) draw(x.lb, i+1);
        if (x.rt != null) draw(x.rt, i+1);
    }
   
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> s = new Stack<>();
        range(root, rect, s);
        return s;
    }
    
    private void range(Node x, RectHV rect, Stack<Point2D> s) {
        if (x == null) return;
        if (rect.contains(x.p)) s.push(x.p);
        if (x.lb != null && x.lb.rect.intersects(rect)) range(x.lb, rect, s);
        if (x.rt != null && x.rt.rect.intersects(rect)) range(x.rt, rect, s);
    }
   
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, p, root.p, 0);
    } 
    
    private Point2D nearest(Node x, Point2D p, Point2D m, int i) {        
        Point2D champion = m;
        if (x == null) return champion;
        
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(champion))
            champion = x.p;
                
        int cmp = compare(p, x.p, i);

        if (cmp < 0) {
            champion = nearest(x.lb, p, champion, i+1); 
            if (x.rt != null && p.distanceSquaredTo(champion) 
                              > x.rt.rect.distanceSquaredTo(p))
                champion = nearest(x.rt, p, champion, i+1);                         
        }
        else if (cmp > 0) {
            champion = nearest(x.rt, p, champion, i+1);
            if (x.lb != null && p.distanceSquaredTo(champion) 
                              > x.lb.rect.distanceSquaredTo(p))
                champion = nearest(x.lb, p, champion, i+1);
        }
        return champion;
    } 
}