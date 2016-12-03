public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    public Edge(int v, int w) {
    	this.v = v;
    	this.w = w;
    	this.weight = 0;
    }

    public Edge(int v, int w, double weight) {
    	this.v = v;
    	this.w = w;
    	this.weight = weight;
    }

    public int v() {
    	return this.v;
    }

    public int w() {
    	return this.w;
    }

    public double weight() {
    	return this.weight;
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() {
    	return String.format("%d-%d %.3f", v, w, weight);
    }

    public static void main(String[] args) {
    	 Edge e = new Edge(0, 1, 3.5);
    	 System.out.println(e);
    }
}
