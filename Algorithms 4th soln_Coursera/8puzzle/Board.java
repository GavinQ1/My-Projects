import java.util.Stack;

public class Board {
    private int N;
    private int[][] board;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                board[i][j] = blocks[i][j];
        }
    }

    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int _hamming = 0;
        int target = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != target) 
                    if (board[i][j] != 0)  _hamming++;
                target++;
            }
        }
        return _hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int _manhattan = 0, target = 1;
        int curr, row, col, rowDiff, colDiff;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != target) {
                    if (board[i][j] != 0) {
                        curr = board[i][j];
                        row = (curr - 1) / N;
                        col = curr - row * N;
                        rowDiff = Math.abs(row - i);
                        colDiff = Math.abs(col - j - 1);
                        _manhattan += rowDiff + colDiff;
                    }
                }
                target++;
            }
        }
        return _manhattan;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                copy[i][j] = board[i][j];
        }
        int i = 0, j = 0, i2 = 1, j2 = 1;
        if (copy[i][j] == 0) j++;
        if (copy[i2][j2] == 0) j2--;
        int temp = copy[i][j];
        copy[i][j] = copy[i2][j2];
        copy[i2][j2] = temp;
        return new Board(copy);
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (!y.getClass().isInstance(this))
            return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension())
            return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                if (this.board[i][j] != that.board[i][j]) return false;
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> _neighbors = new Stack<Board>();
        
        int[][] copy = new int[N][N];
        int oldI = 0, oldJ = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = board[i][j];
                if (copy[i][j] == 0) {
                    oldI = i;
                    oldJ = j;
                }
            }
        }
        
        int temp = copy[oldI][oldJ];
        if (oldI + 1 < N) {
            copy[oldI][oldJ] = copy[oldI+1][oldJ];
            copy[oldI+1][oldJ] = temp;
            _neighbors.push(new Board(copy));
        }
        
        copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                copy[i][j] = board[i][j];
        }
        if (oldI - 1 > -1) {
            copy[oldI][oldJ] = copy[oldI-1][oldJ];
            copy[oldI-1][oldJ] = temp;
            _neighbors.push(new Board(copy));
        }
        
        copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                copy[i][j] = board[i][j];
        }
        if (oldJ + 1 < N) {
            copy[oldI][oldJ] = copy[oldI][oldJ+1];
            copy[oldI][oldJ+1] = temp;
            _neighbors.push(new Board(copy));
        }
        
        copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                copy[i][j] = board[i][j];
        }
        if (oldJ - 1 > -1) {
            copy[oldI][oldJ] = copy[oldI][oldJ-1];
            copy[oldI][oldJ-1] = temp;
            _neighbors.push(new Board(copy));
        }
        
        return _neighbors;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        String res = N + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
                res += " " + board[i][j] + " ";
            res += "\n";
        }
        return res;
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] a1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] a2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] a3 = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board test1 = new Board(a1);
        Board test2 = new Board(a2);
        Board test3 = new Board(a3);
        System.out.println("TEST1 : \n" + test1);
        System.out.println("TEST1.isGoal() : " + test1.isGoal());
        System.out.println("TEST1.hamming() : " + test1.hamming());
        System.out.println("TEST1.manhattan() : " + test1.manhattan());
        System.out.println("TEST1.neighbors() : \n" + test1.neighbors());
        System.out.println("TEST1.equals(test2) : \n" + test1.equals(test2));
        
        System.out.println("TEST3 : \n" + test3);
        System.out.println("TEST3.isGoal() : " + test3.isGoal());
        System.out.println("TEST3.hamming() : " + test3.hamming());
        System.out.println("TEST3.manhattan() : " + test3.manhattan());
        System.out.println("TEST3.neighbors() : \n" + test3.neighbors());
        System.out.println("TEST3.equals(test2) : \n" + test3.equals(test2)); 
        System.out.println("TEST3.twin() : \n" + test3.twin()); 
    }
}