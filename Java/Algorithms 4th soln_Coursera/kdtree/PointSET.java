import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import java.util.ArrayList;

public class PointSET {
    private TreeSet<Point2D> pointSet;
    
    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return pointSet.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (!pointSet.contains(p))
            pointSet.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return pointSet.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p : pointSet)
            p.draw();
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        
        ArrayList<Point2D> a = new ArrayList<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p))
                    a.add(p);
        }
        return a;
    }
    
     // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (pointSet.isEmpty())
            return null;
        
        double min = Double.POSITIVE_INFINITY;
        Point2D res = null;
        for (Point2D p2 : pointSet) {
            if (p != p2 && p.distanceSquaredTo(p2) < min) {
                min = p.distanceSquaredTo(p2);
                res = p2;
            }
        }
        
        return res;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}