import java.util.Comparator;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private SearchNode pointer;
    private byte solvable;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        
        solvable = 0;
        SearchNode init = new SearchNode(initial, 0, null);
        SearchNode init_twin = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(init.compMan);
        MinPQ<SearchNode> pqt = new MinPQ<SearchNode>(init_twin.compMan);
        
        pq.insert(init);
        SearchNode popper = pq.delMin();
        
        pqt.insert(init_twin);
        SearchNode poppy = pqt.delMin();
        
        while (!popper.board.isGoal() && !poppy.board.isGoal()) {
            for (Board b : popper.board.neighbors()) {
                if (popper.prev != null && b.equals(popper.prev.board)) 
                    continue;
                SearchNode n = new SearchNode(b, popper.move + 1, popper);
                pq.insert(n);
            }
            popper = pq.delMin();

            for (Board b : poppy.board.neighbors()) {
                if (poppy.prev != null && b.equals(poppy.prev.board)) 
                    continue;
                SearchNode n = new SearchNode(b, poppy.move + 1, poppy);
                pqt.insert(n);
            }
            poppy = pqt.delMin();
            
        }
        
        if (popper.board.isGoal()) {
            solvable = 1;
            pointer = popper;
        }     
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable > 0;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return pointer.move;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        
        LinkedList<Board> l = new LinkedList<Board>();
        SearchNode temp = pointer;
        while (temp != null) {
            l.addFirst(temp.board);
            temp = temp.prev;
        }
        return l;
    }
    
    
    private class SearchNode {
        private Board board;
        private int move;
        private SearchNode prev;
        //private Comparator<SearchNode> compHam = nodeOrderHam();
        private Comparator<SearchNode> compMan = nodeOrderMan();
        
        private SearchNode(Board board, int move, SearchNode prev) {
            this.board = board;
            this.move = move;
            this.prev = prev;
        }
        
        /*
        private Comparator<SearchNode> nodeOrderHam() {
            return new NodeOrderHam();
        }
        
        private class NodeOrderHam implements Comparator<SearchNode> {
            public int compare(SearchNode n1, SearchNode n2) {
                int p1 = n1.move + n1.board.hamming();
                int p2 = n2.move + n2.board.hamming();
                if (p1 > p2)
                    return +1;
                else if (p1 < p2)
                    return -1;
                else
                    return 0;
            }
        }
        */
        
        private Comparator<SearchNode> nodeOrderMan() {
            return new NodeOrderMan();
        }
        
        private class NodeOrderMan implements Comparator<SearchNode> {
            public int compare(SearchNode n1, SearchNode n2) {
                int p1 = n1.move + n1.board.manhattan();
                int p2 = n2.move + n2.board.manhattan();
                if (p1 > p2)
                    return +1;
                else if (p1 < p2)
                    return -1;
                else
                    return 0;
            }
        }
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        In in = new In("puzzle14.txt");
        // create initial board from file
        //In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}