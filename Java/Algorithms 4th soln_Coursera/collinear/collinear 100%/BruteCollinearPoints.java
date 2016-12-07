import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {     
            if (points[i] == null)
                throw new NullPointerException("argument is null");
            for (int j = i; j > 0; j--) {
                if (points[i].compareTo(points[j-1]) == 0)
                    throw new IllegalArgumentException("argument is illegal");
            }
        }
        
        
        
        this.lines = new ArrayList<LineSegment>();
        if (points.length < 4)
            return;
        Point p1, p2, p3, p4;
        int N = points.length;
        Point[] copy = new Point[N];
        for (int i = 0; i < N; i++) {
            copy[i] = points[i];
        }
        Arrays.sort(copy);
        ArrayList<Tuple> processed = new ArrayList<Tuple>();
        for (int i = 0; i < copy.length-3; i++) {
            p1 = copy[i];
            for (int j = i+1; j < copy.length-2; j++) {
                p2 = copy[j];
                double slope1 = p1.slopeTo(p2);
                for (int k = j+1; k < copy.length-1; k++) {
                    p3 = copy[k];
                    double slope2 = p2.slopeTo(p3);
                    if (slope1 != slope2)
                        continue;     
                    Point end = null;
                    double slope = 0.0;
                    for (int l = k+1; l < copy.length; l++) {
                        p4 = copy[l];
                        double slope3 = p3.slopeTo(p4);
                        if (slope2 != slope3)
                            continue;
                        end = p4;
                        slope = slope3;
                    }
                    if (end != null) {
                        Tuple g = new Tuple(slope, end);
                        if (noDuplicateTuple(processed, g)) {
                            processed.add(g);
                            this.lines.add(new LineSegment(p1, end));
                        }
                    }
                }
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return this.lines.size();
    }
     
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[this.lines.size()];
        for (int i = 0; i < this.lines.size(); i++) {
            res[i] = this.lines.get(i);
        }
        
        return res;
    }

    private class Tuple {
        private double slope;
        private Point end;
        private Tuple(double slope, Point end) {
            this.slope = slope;
            this.end = end;
        }
        
        private boolean compareTo(Tuple that) {
            return this.slope == that.slope &&
                   this.end.compareTo(that.end) == 0;
        }
    }
    
    private boolean noDuplicateTuple(ArrayList<Tuple> T, Tuple tuple) {
        for (Tuple t : T) {
            if (tuple.compareTo(t))
                return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        //In in = new In("rs1423.txt");
        // read the N points from a file
        
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
