public class DirectedEdge implements Comparable<DirectedEdge> {
    private final int from, to;
    private final double weight;

    public DirectedEdge(int from, int to) {
    	this.from = from;
    	this.to = to;
    	this.weight = 0;
    }

    public DirectedEdge(int from, int to, double weight) {
    	this.from = from;
    	this.to = to;
    	this.weight = weight;
    }

    public int from() {
    	return this.from;
    }

    public int to() {
    	return this.to;
    }

    public double weight() {
    	return this.weight;
    }

    public int compareTo(DirectedEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() {
    	return String.format("%d --> %d, %.3f", from, to, weight);
    }

    public static void main(String[] args) {
    	 DirectedEdge e = new DirectedEdge(0, 1, 3.5);
    	 System.out.println(e);
    }
}

