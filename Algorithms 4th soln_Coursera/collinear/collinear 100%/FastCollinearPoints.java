import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        
        int N = points.length;
        Point[] copy = new Point[N];
        for (int i = 0; i < N; i++) {
            copy[i] = points[i];
        }
        
        for (int i = 0; i < N; i++) {
            Point p = points[i];
            Arrays.sort(copy, p.slopeOrder());
            double oldSlope = Double.NEGATIVE_INFINITY;
            
            int count = 0;
            int j = 1;
            int start = N;
            while (j < N) {
              double newSlope = p.slopeTo(copy[j]);
              if (oldSlope == newSlope && j != N-1) {
                count++;
                if (start == N)
                  start = j-1;
              } else {
                if (j == N-1 && newSlope == oldSlope)
                  count++;
                
                if (count > 1) {
                  int end = j-1;
                  if (j == N-1 && newSlope == oldSlope)
                    end = j;
                  Arrays.sort(copy, start, end+1);
                  if (p.compareTo(copy[start]) < 0) 
                    this.lines.add(new LineSegment(p, copy[end]));
                }
                  start = N;
                  count = 0;
              }
              oldSlope = newSlope;
              j++;
            }
        }
    }    
    
    // the number of line segments
    public int numberOfSegments() {
        return this.lines.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[numberOfSegments()];
        for (int i = 0; i < numberOfSegments(); i++) {
            res[i] = this.lines.get(i);
        }

        return res;
    }
  
    public static void main(String[] args) {
        In in = new In("rs1423.txt");
        // read the N points from a file
        
        //In in = new In(args[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }       
}
