import java.awt.Color;
import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
    private Picture picture;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new NullPointerException();
        
        this.picture = new Picture(picture);
    } 
    
    private class SearchNode {
        private int index;
        private double weight;
        private SearchNode prev;
        
        public SearchNode(int index, double weight, SearchNode prev) {
            this.index = index;
            this.weight = weight;
            this.prev = prev;
        }
    }
    
    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }
    
    // width of current picture
    public int width() {
        return this.picture.width();
    }
    
    // height of current picture
    public int height() {
        return this.picture.height();
    }

    private int sqr(int x) { return x * x; }
    private int rgbXSqr(int x, int y) {
        Color c1 = this.picture.get(x-1, y);
        Color c2 = this.picture.get(x+1, y);
            
        int r1 = c1.getRed(),
            g1 = c1.getGreen(),
            b1 = c1.getBlue(),
            r2 = c2.getRed(),
            g2 = c2.getGreen(),
            b2 = c2.getBlue(),
            R = sqr(r2-r1),
            G = sqr(g2-g1),
            B = sqr(b2-b1);
        return R + G + B;
    }

    private int rgbYSqr(int x, int y) {
        Color c1 = this.picture.get(x, y-1);
        Color c2 = this.picture.get(x, y+1);
            
        int r1 = c1.getRed(),
            g1 = c1.getGreen(),
            b1 = c1.getBlue(),
            r2 = c2.getRed(),
            g2 = c2.getGreen(),
            b2 = c2.getBlue(),
            R = sqr(r2-r1),
            G = sqr(g2-g1),
            B = sqr(b2-b1);
        return R + G + B;
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= this.width() || y >= this.height())
            throw new IndexOutOfBoundsException();
        
        if (x == 0 || y == 0 || x == this.width()-1 || y == this.height()-1)
            return 1000;
        
        int rgbXsqr = rgbXSqr(x, y);
        int rgbYsqr = rgbYSqr(x, y);
        
        return Math.sqrt(rgbXsqr + rgbYsqr);
    }
    
    private int getX(int index) { return index / this.width(); }   
    private int getY(int index) { return index % this.width(); }
    
    private int[] adjHorizontal(int index) {
        if (getX(index) == 0) {
            int[] res = new int[2];
            res[0] = index + 1;
            res[1] = index + 1 + this.width();
            return res;
        } else if (getX(index) == this.height() - 1) {
            int[] res = new int[2];
            res[0] = index + 1;
            res[1] = index + 1 - this.width();
            return res;
        } else {
            int[] res = new int[3];
            res[0] = index + 1;
            res[1] = index + 1 - this.width();
            res[2] = index + 1 + this.width();
            return res;
        }
    }
 
    private int[] adjVertical(int index) {   
        int M  = this.width();
        
        if (index % M == 0) {
            int[] res = new int[2];
            res[0] = index + M;
            res[1] = index + M + 1;
            return res;
        } else if ((index+1) % M == 0) {
            int[] res = new int[2];
            res[0] = index + M - 1;
            res[1] = index + M;
            return res;
        } else {
            int[] res = new int[3];
            res[1] = index + M;
            res[0] = index + M - 1;
            res[2] = index + M + 1;
            return res;
        }  
    }
    
    private void relax(int from, int to, SearchNode[] distTo) { 
        int toX = getX(to),
            toY = getY(to);
        
        double weight = energy(toY, toX);  
        if (distTo[to].weight > distTo[from].weight + weight) {
            distTo[to].weight = distTo[from].weight + weight;
            distTo[to].prev = distTo[from];
        }
    }
      
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() { 
        int w = this.width(),
            h = this.height();
        
        int[] horizontalSeam = new int[this.width()];
        
        if (h == 1) 
            return horizontalSeam;      
        
        int entries = h * w;
        
        SearchNode[] distTo = new SearchNode[entries];
        
        for (int e = 0; e < entries; e++) 
            distTo[e] = new SearchNode(e, Double.POSITIVE_INFINITY, null);
        
        for (int i = 0, e = 0; i < h && e < entries; i++, e += w) 
            distTo[e].weight = energy(0, i);
                    
        for (int c = 0; c < w-1; c++) {
            for (int from = c; from < entries; from += w) { 
                for (int to : adjHorizontal(from)) 
                    relax(from, to, distTo);
            }
        }
        
        double lowest = Double.POSITIVE_INFINITY;
        SearchNode path = null;
        for (int i = w-1; i < entries; i += w) {
            if (lowest > distTo[i].weight) {
                lowest = distTo[i].weight;
                path = distTo[i];
            }
        }
        
        for (int i = w-1; i > -1; i--) {
            horizontalSeam[i] = getX(path.index);
            path = path.prev;
        }
 
        return horizontalSeam;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {       
        int w = this.width(),
            h = this.height();
                        
        int[] verticalSeam = new int[this.height()];
        
        if (w == 1) 
            return verticalSeam;
        
        int entries = h * w;
        
        SearchNode[] distTo = new SearchNode[entries];
        
        for (int e = 0; e < entries; e++) 
            distTo[e] = new SearchNode(e, Double.POSITIVE_INFINITY, null);
        
        for (int j = 0; j < w; j++) 
            distTo[j].weight = energy(j, 0);
                    
        for (int from = 0; from < entries-w; from++) {
            for (int to : adjVertical(from)) 
                    relax(from, to, distTo);
        }
        
        double lowest = Double.POSITIVE_INFINITY;
        SearchNode path = null;
        for (int i = entries-w; i < entries-1; i++) {
            if (lowest > distTo[i].weight) {
                lowest = distTo[i].weight;
                path = distTo[i];
            }
        }
        
        for (int i = h-1; i > -1; i--) {
            verticalSeam[i] = getY(path.index);
            path = path.prev;
        }
        
        return verticalSeam; 
    }
        
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new NullPointerException();
        int n = seam.length;
        if (n < 1 || n != width()) 
            throw new IllegalArgumentException();      
        for (int i = 0; i < n; i++) {
            if (seam[i] < 0 || seam[i] >= height()) 
                throw new IllegalArgumentException();            
        }
        for (int i = 1; i < n; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) 
                throw new IllegalArgumentException();            
        }
                
        Picture p = this.picture;
        Picture newPic = new Picture(p.width(), p.height()-1);
        for (int r = 0; r < p.width(); r++) {
            for (int c = 0; c < p.height(); c++) {
                if (c == seam[r]) continue;
                int newC = c;
                if (newC > seam[r]) newC--;
                newPic.set(r, newC, p.get(r, c));
            }
        }
        
        this.picture = newPic;
    }
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) { 
        if (seam == null)
            throw new NullPointerException();
        int m = seam.length;
        if (m < 1 || m != height()) 
            throw new IllegalArgumentException();
        for (int i = 0; i < m; i++) {
            if (seam[i] < 0 || seam[i] >= width()) 
                throw new IllegalArgumentException();
        }
        for (int i = 1; i < m; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) 
                throw new IllegalArgumentException();
        }
        
        Picture p = this.picture;
        Picture newPic = new Picture(p.width()-1, p.height());
        for (int r = 0; r < p.height(); r++) {
            for (int c = 0; c < p.width(); c++) {
                if (c == seam[r]) continue;
                int newC = c;
                if (newC > seam[r]) newC--;
                newPic.set(newC, r, p.get(c, r));
            }
        }
        
        this.picture = newPic;
    }
    
}