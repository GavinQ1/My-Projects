import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class KdTree {
    private Node root;     // root of the BST
    private int size;
    
    
    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // BST helper node data type
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left, right;  // links to left and right subtrees
        
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    private Node put(Node x, Point2D p, Node parent, int count, int flag) {   
        if (x == null) {
            size++;
            if (parent == null) 
                return new Node(p, new RectHV(0, 0, 1, 1));
             
            double minX = parent.rect.xmin(),
                   minY = parent.rect.ymin(),
                   maxX = parent.rect.xmax(),
                   maxY = parent.rect.ymax();
            if (flag == 0) 
                return new Node(p, new RectHV(minX, parent.p.y(), maxX, maxY));
            else if (flag == 1) 
                return new Node(p, new RectHV(parent.p.x(), minY, maxX, maxY));
            else if (flag == 2) 
                return new Node(p, new RectHV(minX, minY, maxX, parent.p.y()));
            else if (flag == 3)
                return new Node(p, new RectHV(minX, minY, parent.p.x(), maxY));
            else 
                return null;
        }
  
        double cmp;
        if (count % 2 == 0) {
            cmp = p.x() - x.p.x();
            if (cmp < 0)
                flag = 3;
            else
                flag = 1;
            if (cmp == 0 && p.y() - x.p.y() == 0)
                flag = 4;
        } else {
            cmp = p.y() - x.p.y();
            if (cmp < 0)
                flag = 2;
            else
                flag = 0;
            if (cmp == 0 && p.x() - x.p.x() == 0)
                flag = 4;
        }
        
        if (cmp < 0) x.left = put(x.left, p, x, count+1, flag);
        else if (cmp >= 0 && flag < 4) x.right = put(x.right, p, x, count+1, flag);
        else x.p = p;

        return x;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        root = put(root, p, null, 0, -1);
    }
    
    private boolean contains(Node x, Point2D p, int count) {
        if (x == null) return false;
        
        if (x.p.compareTo(p) == 0) return true;
        
        double cmp;
        if (count % 2 == 0) 
            cmp = p.x() - x.p.x();
        else 
            cmp = p.y() - x.p.y();
        
        boolean left = false, right = false;
        if (cmp < 0) left = contains(x.left, p, count+1);
        else right = contains(x.right, p, count+1);
        
        return left || right;
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        return contains(root, p, 0);
    }
    
    private void drawer(Node pointer, int count) {
        if (pointer == null)
            return;
        
        RectHV rect = pointer.rect;
        double minX = rect.xmin(),
               minY = rect.ymin(),
               maxX = rect.xmax(),
               maxY = rect.ymax();
        if (count % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(pointer.p.x(), minY, pointer.p.x(), maxY);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, pointer.p.y(), maxX, pointer.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        pointer.p.draw();
        StdDraw.setPenRadius(.001);
        if (pointer.left != null)
            drawer(pointer.left, count+1);
        if (pointer.right != null)
            drawer(pointer.right, count+1);
    }
            
    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(.001);
        drawer(root, 0);
    }
    
    private void search(RectHV that, Node pointer, 
                                      ArrayList<Point2D> a) {
        if (pointer == null) 
            return;
        
        if (that.contains(pointer.p)) 
            a.add(pointer.p);
      
        if (pointer.left != null && pointer.left.rect.intersects(that)) 
            search(that, pointer.left, a);
        if (pointer.right != null && pointer.right.rect.intersects(that)) 
            search(that, pointer.right, a);
    }
        
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> a = new ArrayList<Point2D>();
        search(rect, root, a);
        return a;
    }
    
    private Point2D searchN(Point2D p, Node x, 
                            Point2D nearestP, int level) {
        if (x == null)
            return nearestP;
        
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearestP))
            nearestP = x.p;
        
        double cmp;
        boolean flag = false;
        if (level % 2 == 0) {
            cmp = p.x() - x.p.x();
            if (cmp == 0 && p.y() - x.p.y() == 0)
                flag = true;
        }
        else { 
            cmp = p.y() - x.p.y();
            if (cmp == 0 && p.x() - x.p.x() == 0)
                flag = true;
        }
        
        if (cmp < 0) {
            nearestP = searchN(p, x.left, nearestP, level+1); 
            if (x.right != null && p.distanceSquaredTo(nearestP) 
                              > x.right.rect.distanceSquaredTo(p))
                nearestP = searchN(p, x.right, nearestP, level+1);                         
        }
        else if (cmp >= 0 && !flag) {
            nearestP = searchN(p, x.right, nearestP, level+1);
            if (x.left != null && p.distanceSquaredTo(nearestP) 
                              > x.left.rect.distanceSquaredTo(p))
                nearestP = searchN(p, x.left, nearestP, level+1);
        }
        return nearestP;
        
    }

               
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        
        return searchN(p, root, root.p, 0);
    }
    
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = "circle10.txt";
        In in = new In(filename);

        StdDraw.show(0);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            System.out.println(p);
            kdtree.insert(p);
            kdtree.draw();
            StdDraw.show(50);
        }
    }
}
   