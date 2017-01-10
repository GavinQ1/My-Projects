package Models;

import java.util.Comparator;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class KnowledgeNode implements Comparator<KnowledgeNode>, 
        Comparable<KnowledgeNode>, Serializable, Cloneable {
    protected Integer significance;
    protected String name, catagory, definition, description;
    protected KnowledgeNodeList sources, destinations, neighbors;
    
    public KnowledgeNode(String name, String catagory, 
            String definition, String description) {
        this(name, catagory, definition, description, 
                new KnowledgeNodeList(), 
                new KnowledgeNodeList(), 
                new KnowledgeNodeList());
    }
    
    public KnowledgeNode(String name, String catagory, 
            String definition, String description,
            String sourcesName, String destinationsName, String neighborsName) {
        this(name, catagory, definition, description, 
                new KnowledgeNodeList(sourcesName), 
                new KnowledgeNodeList(destinationsName), 
                new KnowledgeNodeList(neighborsName));
    }
    
    public KnowledgeNode(String name, String catagory, 
            String definition, String description, 
            KnowledgeNodeList sources, KnowledgeNodeList destinations, KnowledgeNodeList neighbors) {
        this.significance = 0;
        this.name = name;
        this.catagory = catagory;
        this.definition = definition;
        this.description = description;
        this.sources = sources;
        this.sources.setType(KnowledgeNodeList.TYPE.SOURCES.getValue());
        this.destinations = destinations;
        this.destinations.setType(KnowledgeNodeList.TYPE.DESTINATIONS.getValue());
        this.neighbors = neighbors;
        this.neighbors.setType(KnowledgeNodeList.TYPE.NEIGHBORS.getValue());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeNode cloned = (KnowledgeNode) super.clone();
            cloned.sources = (KnowledgeNodeList) sources.clone();
            cloned.destinations = (KnowledgeNodeList) destinations.clone();
            cloned.neighbors = (KnowledgeNodeList) neighbors.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    @Override
    public int compareTo(KnowledgeNode other) {
        return this.significance.compareTo(other.significance);
    }
    
    @Override
    public int compare(KnowledgeNode t, KnowledgeNode t1) {
        return t.significance - t1.significance;
    }
    
    public static final Comparator<KnowledgeNode> comparatorByCatagory() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k1.catagory.compareTo(k2.catagory);
    }
    
    public static final Comparator<KnowledgeNode> comparatorBySignificance() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k2.significance.compareTo(k1.significance);
    }
    
    public static final Comparator<KnowledgeNode> comparatorByName() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k1.name.compareTo(k2.name);
    }
    
    public int getSignificance() { return this.significance; }
    public String getName() { return this.name; }
    public String getCatagory() { return this.catagory; }
    public String getDefinition() { return this.definition; }
    public String getDescription() { return this.description; }
    public KnowledgeNodeList getSources() { return this.sources; }
    public KnowledgeNodeList getDestinations() { return this.destinations; }
    public KnowledgeNodeList getNeighbors() { return this.neighbors; }
    public boolean hasSource(String name) { return this.sources.contains(name); }
    public boolean hasDestination(String name) { return this.destinations.contains(name); }
    public boolean hasNeighbor(String name) { return this.neighbors.contains(name); }
    public boolean hasSource(KnowledgeNode k) { return this.sources.contains(k); }
    public boolean hasDestination(KnowledgeNode k) { return this.destinations.contains(k); }
    public boolean hasNeighbor(KnowledgeNode k) { return this.neighbors.contains(k); }
    
    public void setName(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.name = newName;
    }
    
    public void setCatagory(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.catagory = newName;
    }
    
    public void setDefinition(String newDefinition) {
        if ("".equals(newDefinition.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.definition = newDefinition;
    }
    
    public void setDescription(String newDescription) {
        if ("".equals(newDescription.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.description = newDescription;
    }
    
    public void addSource(KnowledgeNode k) {
        if (this == k || this.name.equals(k.name)) // must be a different node
            throw new InvalidInputException("Can't add self as a source.");
        
        this.sources.add(k);
        k.destinations.add(this);
        significance++;
        for (KnowledgeNode s : this.sources) 
            k.addNeighbor(s);
    }
    
    public void addDestination(KnowledgeNode k) {
        if (this == k || this.name.equals(k.name)) // must be a different node
            throw new InvalidInputException("Can't add self as a destination.");
        
        this.destinations.add(k);
        k.sources.add(this);
        k.significance++;
        for (KnowledgeNode s : k.sources) 
            this.addNeighbor(s);
    }
    
    public void addNeighbor(KnowledgeNode k) {
        if (k == this || // k is iteself
            k.name.equals(this.name) || // k has same name
            !this.catagory.equals(k.catagory)) // k is from a different catagory
            return;  // do not add k as a neighbor
        this.neighbors.add(k);
        k.neighbors.add(this);
    }
    
    public void removeSource(KnowledgeNode k) {
        if (this == k || this.name.equals(k.name))
            throw new InvalidInputException("Can't remove self as a source.");
        this.sources.remove(k);
        k.destinations.remove(this);
        significance--;
        for (KnowledgeNode s : this.sources)
            this.removeNeighbor(s);
    }
    
    public void removeDestination(KnowledgeNode k) {
        if (this == k || this.name.equals(k.name))
            throw new InvalidInputException("Can't remove self as a destination.");
        this.destinations.remove(k);
        k.sources.remove(this);
        k.significance--;
        for (KnowledgeNode s : k.sources)
            s.removeNeighbor(this);
    }
    
    public void removeNeighbor(KnowledgeNode k) {
        if (k == this || // k is iteself
            k.name.equals(this.name) || // k has same name
            !this.catagory.equals(k.catagory)) // k is from a different catagory
            return;  // do not remove k as a neighbor
        this.neighbors.remove(k);
        k.neighbors.remove(this);
    }
    
    
    public String chineseFormattedInformation() {
        return "名称: " + name + "\n\n类别: " + catagory + "\n\n定义: " + definition +
                "\n\n描述: " + description + "\n\n引用次数: " + significance +
                "\n\n" + sources.getName() + ":\n" +
                sources.membersInString() + "\n\n" + destinations.getName() + ":\n" +
                destinations.membersInString() + "\n\n" + neighbors.getName() + ":\n" +
                neighbors.membersInString();
    }
    
    public String toString() {
        return name + " (" + catagory + ", referred: " + significance + ")";
    }
    
    // unit test
    public static void main(String[] args) {
        KnowledgeNode a = new KnowledgeNode("A", "Character", "A", "First", "Source", "Destination", "Neighbor");
        KnowledgeNode b = new KnowledgeNode("B", "Character", "B", "Second", "Source", "Destination", "Neighbor");
        KnowledgeNode c = new KnowledgeNode("C", "Character", "C", "Third", "Source", "Destination", "Neighbor");
        KnowledgeNode d = new KnowledgeNode("D", "Character", "D", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode e = new KnowledgeNode("E", "Character", "E", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode f = new KnowledgeNode("F", "Character", "F", "Fourth", "Source", "Destination", "Neighbor");
        a.addDestination(d);
        a.addDestination(d);
        e.addDestination(d);
        System.out.println(a.destinations.membersInString());
        System.out.println(d.chineseFormattedInformation());
        a.removeDestination(d);
        System.out.println(a.destinations.membersInString());
        a.addDestination(d);
        System.out.println(a.destinations.membersInString());
        
    }
}
