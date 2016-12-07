import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//credit to http://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html

public class Percolation {
    private int size;
    private boolean percolate;
    private byte[] grid;
    private WeightedQuickUnionUF uf;
    
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        
        size = N;
        percolate = false;
        grid = new byte[N * N];
        
        uf = new WeightedQuickUnionUF(N * N);
    }
    
    public void open(int i, int j) {
        if (i < 1 || j < 1 || i > size || j > size) 
            throw new IndexOutOfBoundsException();
        if (isOpen(i, j)) 
            return;
        
        int currIndex = getUFIndex(i, j);
        grid[currIndex] += 1;
        
        if (i == 1) 
            grid[currIndex] += 4;
        
        if (i == size)
            grid[currIndex] += 2;
        
        byte currGrid = grid[currIndex];
        byte lefty = 0, righty = 0, up = 0, down = 0;
        if (j > 1 && isOpen(i, j-1)) {
            lefty = grid[uf.find(getUFIndex(i, j-1))]; 
            uf.union(currIndex, getUFIndex(i, j-1));
        }
        
        if (j < size && isOpen(i, j + 1)) {
            righty = grid[uf.find(getUFIndex(i, j+1))];
            uf.union(currIndex, getUFIndex(i, j+1));
        }

        if (i < size && isOpen(i+1, j)) {
            down = grid[uf.find(getUFIndex(i+1, j))];
            uf.union(currIndex, getUFIndex(i+1, j)); 
        }
        
        if (i > 1 && isOpen(i-1, j)) {
            up = grid[uf.find(getUFIndex(i-1, j))];
            uf.union(currIndex, getUFIndex(i-1, j));
        }
        
        int newRoot = uf.find(currIndex); 
        grid[newRoot] |= lefty | righty | up | down | currGrid;
      
        if (!percolates() && (grid[newRoot] & 7) == 7)
            percolate = true;
        
    }
    
    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > size || j > size) 
            throw new IndexOutOfBoundsException();
        
        return grid[getUFIndex(i, j)] >= 1; 
    }
    
    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > size || j > size) 
            throw new IndexOutOfBoundsException();
        
        return grid[uf.find(getUFIndex(i, j))] >= 5;
    }

    public boolean percolates() {
        return percolate;
    }
    
    private int getUFIndex(int i, int j) {
        return size * (i - 1) + j - 1;
    }
}