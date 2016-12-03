import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BaseballElimination {
    private int N; 
    private ArrayList<String> t;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private int combi;
    private int v;
    
    // create a baseball division from given filename in format specified
    public BaseballElimination(String filename) {
        In in = new In(filename);
        this.N = in.readInt();
        this.t = new ArrayList<String>();
        this.w = new int[N];
        this.l = new int[N];
        this.r = new int[N];
        this.g = new int[N][N];
        this.combi = choice2(N-1);
        this.v = N + combi + 1;
        
        int i = 0;
        while (!in.isEmpty()) {
            t.add(in.readString());
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < N; j++)
                g[i][j] = in.readInt();
            i++;
        }
    } 
    
    private FlowNetwork createFlowNetwork(String team) {
        int x = t.indexOf(team);
        int s = 0;
        FlowNetwork flowNet = new FlowNetwork(v+1);
        int count = 0;
        for (int i = 0; i < g.length; i++) {
            if (i == x) continue;
            for (int j = i+1; j < g[i].length; j++) {
                if (j == x) continue;
                count++;
                flowNet.addEdge(new FlowEdge(s, count, g[i][j]));
                flowNet.addEdge(new FlowEdge(count, 
                                             combi + i + 1, 
                                             Double.POSITIVE_INFINITY));
                flowNet.addEdge(new FlowEdge(count, 
                                             combi + j + 1, 
                                             Double.POSITIVE_INFINITY));
            }
            int cap = w[x] + r[x] - w[i];
            flowNet.addEdge(new FlowEdge(combi + i + 1,
                                             v,
                                         cap >= 0 ? cap : 0));
        }
        return flowNet;
    }
    
    private int choice2(int a) { return a * (a - 1) / 2; }
    
    private void validate(String team) {
        if (t.indexOf(team) < 0)
            throw new IllegalArgumentException();
    }
    
    // number of teams
    public int numberOfTeams() { return this.N; }
    
    // all teams
    public Iterable<String> teams() { return this.t; } 
    
    // number of wins for given team
    public int wins(String team) { 
        validate(team);
        return this.w[t.indexOf(team)]; 
    } 
    
    // number of losses for given team
    public int losses(String team) { 
        validate(team);
        return this.l[t.indexOf(team)]; 
    } 
    
    // number of remaining games for given team
    public int remaining(String team) { 
        validate(team);
        return this.r[t.indexOf(team)]; 
    } 
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        return this.g[t.indexOf(team1)][t.indexOf(team2)];
    }       
    
    // is given team eliminated?
    public boolean isEliminated(String team) { 
        validate(team);
        
        int totalPoss = wins(team) + remaining(team);
        // trivial
        for (String s : teams()) {
            if (s != team && totalPoss < wins(s))
                return true;
        }
        
        // notrivial
        int index = t.indexOf(team);
        FordFulkerson algs = new FordFulkerson(createFlowNetwork(team), 0, v);
        for (int i = combi+1; i < v; i++) {
            if (i == combi + index + 1) continue;
            if (algs.inCut(i)) return true;
        }
        return false;
    } 
    
    // subset R of teams that eliminates given team; null if not elimin
    public Iterable<String> certificateOfElimination(String team) { 
        ArrayList<String> res = new ArrayList<String>();
        
        int totalPoss = wins(team) + remaining(team);
        for (String s : teams()) {
            if (s != team && totalPoss < wins(s)) {
                res.add(s);
                return res;
            }
        }
        
        int index = t.indexOf(team);
        boolean flag = false;
        FordFulkerson algs = new FordFulkerson(createFlowNetwork(team), 0, v);
        for (int i = combi+1; i < v; i++) {
            if (i == combi + index + 1) continue;
            if (algs.inCut(i)) { res.add(t.get(i-combi-1)); flag = true; }
        }
        
        if (!flag) return null;
        return res;
    } 
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        //BaseballElimination division = new BaseballElimination("teams4.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}