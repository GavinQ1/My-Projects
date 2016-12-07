import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) 
            throw new NullPointerException("can not compare to a null value");
    
        double rise = that.y - this.y;
        double run = that.x - this.x;
        double slope;
        if (run == 0 && rise == 0) {
            slope = Double.NEGATIVE_INFINITY;
        } else if (run == 0) {
            slope = Double.POSITIVE_INFINITY;
        } else if (rise == 0) {
            slope = 0;
        } else {
            slope = rise / run;
        }
        return slope;
  }


    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y || ((this.y == that.y) && (this.x < that.x))) {
            return -1;
        } else if (this.y == that.y && this.x == that.x) {
            return 0;
        } else {
            return 1;
        }   
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 < slope2) {
                return -1;
            } else if (slope1 > slope2) {
                return +1;
            }
            return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(2, 3);
        Point d = new Point(1, 1);
        
        System.out.println("a < c : -1 ===> " + a.compareTo(c));
        System.out.println("a == d : 0 ===> " + a.compareTo(d));
        System.out.println("c > d : 1 ===> " + c.compareTo(d));
        
        System.out.println("slope a -> b ===> " + a.slopeTo(b));
        System.out.println("slope a -> c ===> " + a.slopeTo(c));
        
        Comparator<Point> slopeOrder = a.slopeOrder();
        System.out.println("(slope a -> b) < (slope a -> c) : -1 ===> " +
                             slopeOrder.compare(b, c));
    }
}
