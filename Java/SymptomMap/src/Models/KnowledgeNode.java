package Models;

import java.util.Comparator;

/**
 *
 * @author Gavin
 */
public class KnowledgeNode implements Comparator<KnowledgeNode>, 
        Comparable<KnowledgeNode>{
    protected Integer significance;
    protected String name, definition, description;
    protected KnowledgeNodeList sources, destinations, neighbors;
    
    public KnowledgeNode(String name, String definition, String description) {
        this(name, definition, description, 
                new KnowledgeNodeList(), 
                new KnowledgeNodeList(), 
                new KnowledgeNodeList());
    }
    
    public KnowledgeNode(String name, String definition, String description,
            String sourcesName, String destinationsName, String neighborsName) {
        this(name, definition, description, 
                new KnowledgeNodeList(sourcesName), 
                new KnowledgeNodeList(destinationsName), 
                new KnowledgeNodeList(neighborsName));
    }
    
    public KnowledgeNode(String name, String definition, String description,
            KnowledgeNodeList sources, KnowledgeNodeList destinations, KnowledgeNodeList neighbors) {
        this.significance = 0;
        this.name = name;
        this.definition = definition;
        this.description = description;
        this.sources = sources;
        this.destinations = destinations;
        this.neighbors = neighbors;
    }
    
    @Override
    public int compareTo(KnowledgeNode other) {
        return this.significance.compareTo(other.significance);
    }
    
    @Override
    public int compare(KnowledgeNode t, KnowledgeNode t1) {
        return t.significance - t1.significance;
    }
    
    public int getSignificance() {
        return this.significance;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public String getDefinition() {
        return this.definition;
    }
    
    public void setDefinition(String newDefinition) {
        this.name = newDefinition;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String newDescription) {
        this.name = newDescription;
    }
    
    public KnowledgeNodeList getSources() {
        return this.sources;
    }
    
    public KnowledgeNodeList getDestinations() {
        return this.destinations;
    }
    
    public KnowledgeNodeList getNeighbors() {
        return this.neighbors;
    }
    
    public boolean hasSource(String name) {
        return this.sources.contains(name);
    }
    
    public boolean hasDestination(String name) {
        return this.destinations.contains(name);
    }
    
    public boolean hasNeighbor(String name) {
        return this.neighbors.contains(name);
    }
    
    public void addSource(KnowledgeNode k) {
        if (hasSource(k.getName()) || this == k) return;
        this.sources.add(k);
        k.addDestination(this);
        for (KnowledgeNode s : this.sources)
            k.addNeighbor(s);
    }
    
    public void removeSource(String name) {
        if (!hasSource(name)) return;
        KnowledgeNode s = this.sources.getKnowledge(name);
        this.sources.remove(name);
        s.destinations.remove(name);
        this.significance--;
        for (KnowledgeNode left : this.sources)
            left.removeNeighbor(name);
    }
    
    public void addDestination(KnowledgeNode k) {
        if (hasDestination(k.getName()) || this == k) return;
        this.destinations.add(k);
        k.significance++;
        k.addSource(this);
    }
    
    public void removeDestination(String name) {
        if (!hasDestination(name)) return;
        KnowledgeNode d = this.destinations.getKnowledge(name);
        this.destinations.remove(name);
        d.removeSource(this.name);
    }
    
    public void addNeighbor(KnowledgeNode k) {
        if (hasNeighbor(k.getName()) || this == k) return;
        this.neighbors.add(k);
        k.addNeighbor(this);
    }
    
    public void removeNeighbor(String name) {
        if (!hasNeighbor(name)) return;
        KnowledgeNode n = this.neighbors.getKnowledge(name);
        this.neighbors.remove(name);
        n.neighbors.remove(this.name);
    }
    
    // unit test
    public static void main(String[] args) {
        KnowledgeNode a = new KnowledgeNode("a", "", "", "", "", "");
        KnowledgeNode b = new KnowledgeNode("b", "", "", "", "", "");
        KnowledgeNode c = new KnowledgeNode("c", "", "", "", "", "");
        KnowledgeNode d = new KnowledgeNode("d", "", "", "", "", "");
        KnowledgeNode e = new KnowledgeNode("e", "", "", "", "", "");
        a.addSource(e);
        a.addDestination(d);
        c.addDestination(d);
        c.addDestination(a);
        b.addDestination(d);
        System.out.println(a.sources);
        System.out.println(d.sources);
        System.out.println(b.destinations);
        System.out.println(c.destinations);
        System.out.println(a.neighbors);
        System.out.println(b.neighbors);
        System.out.println(c.neighbors);
        System.out.println(e.neighbors);
        System.out.println(d.significance);
        a.removeDestination(d.getName());
        System.out.println(d.significance);
        System.out.println(a.neighbors);
        System.out.println(b.neighbors);
        System.out.println(c.neighbors);
    }
}
