import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] data;
    private int eT; //Experiment Times
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) { 
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        
        eT = T;
        data = new double[eT];
        
        int i, j;
        double f; //fraction
        double t = (double) N * N; //total sites
        int count = 0;
        while (count < T) {
            Percolation P = new Percolation(N);
            
            int openSite = 0;
            while (!P.percolates()) {
                do {
                    i = StdRandom.uniform(1, N+1);
                    j = StdRandom.uniform(1, N+1);
                }
                while (P.isOpen(i, j));
                P.open(i, j);
                openSite++;
            }
            
            
            f = openSite / t;
            data[count] = f;
            count++;
        }
    }  
    
    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double val: data) {
            sum += val;
        }
        return sum / eT;
    }     
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        double m = mean();
        double sum = 0;
        for (double val: data) {
            sum += Math.pow((val - m), 2);
        }
        double var = sum / (eT - 1);
        return Math.sqrt(var);
    }     
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double sqrtT = Math.sqrt(eT);
        double d = stddev();
        double m = mean();
        
        return m - (1.96 * d) / sqrtT;
    }  
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double sqrtT = Math.sqrt(eT);
        double d = stddev();
        double m = mean();
        
        return m + (1.96 * d) / sqrtT;
    }          

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats PS = new PercolationStats(N, T);
        double m, d, loC, hiC;
        m = PS.mean();
        d = PS.stddev();
        loC = PS.confidenceLo();
        hiC = PS.confidenceHi();
        
        System.out.println("mean                    = " + m);
        System.out.println("stddv                   = " + d);
        System.out.println("95% confidence interval = " + loC + ", " + hiC);
    }  
    
}
