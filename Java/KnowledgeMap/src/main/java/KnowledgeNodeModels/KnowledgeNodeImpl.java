package KnowledgeNodeModels;

import Lib.InvalidInputException;
import javax.persistence.*;


/**
 *
 * @author Gavin
 */
@Entity(name = "KnowledgeNode")
public class KnowledgeNodeImpl implements 
        KnowledgeNode {
    @Id
    @Basic
    private Integer id;
    @Basic
    @Column(insertable=false, updatable=false)
    private Integer significance;
    @Basic
    @Column(insertable=false, updatable=false)
    private String name, catagory, definition, description;
    @ManyToOne
    private KnowledgeNodeListImpl sources, destinations, neighbors;
    
    public KnowledgeNodeImpl() {
        this(0, "", "", "", "", "Sources", "Destinations", "Neighbors");
    }
    
    public KnowledgeNodeImpl(String name, String catagory, 
            String definition, String description, 
            String sourcesName, String destinationsName, String neighborsName) {
        this(0, name, catagory, definition, description, 
                sourcesName,  destinationsName, neighborsName);
    }
    
    public KnowledgeNodeImpl(Integer id, String name, String catagory, 
            String definition, String description,
            String sourcesName, String destinationsName, String neighborsName) {
        this(id, name, catagory, definition, description, 
                new KnowledgeNodeListImpl(
                        "KnowledgeNodeList-" + name + "-" + id.toString() + "-Sources",
                        sourcesName), 
                new KnowledgeNodeListImpl(
                        "KnowledgeNodeList-" + name + "-" + id.toString() + "-Sources",
                        destinationsName), 
                new KnowledgeNodeListImpl(
                        "KnowledgeNodeList-" + name + "-" + id.toString() + "-Sources",
                        neighborsName));
    }
    
    public KnowledgeNodeImpl(Integer id, String name, String catagory, 
            String definition, String description, 
            KnowledgeNodeListImpl sources, KnowledgeNodeListImpl destinations, KnowledgeNodeListImpl neighbors) {
        this.id = id;
        this.significance = 0;
        this.name = name;
        this.catagory = catagory;
        this.definition = definition;
        this.description = description;
        this.sources = sources;
        this.sources.setType(KnowledgeNodeListImpl.TYPE.SOURCES.getValue());
        this.destinations = destinations;
        this.destinations.setType(KnowledgeNodeListImpl.TYPE.DESTINATIONS.getValue());
        this.neighbors = neighbors;
        this.neighbors.setType(KnowledgeNodeListImpl.TYPE.NEIGHBORS.getValue());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeNodeImpl cloned = (KnowledgeNodeImpl) super.clone();
            cloned.sources = (KnowledgeNodeListImpl) sources.clone();
            cloned.destinations = (KnowledgeNodeListImpl) destinations.clone();
            cloned.neighbors = (KnowledgeNodeListImpl) neighbors.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    /*
    public KnowledgeNodeImpl cleanClone() {
        return new KnowledgeNodeImpl(name, catagory, definition, description, 
                sources.getName(), destinations.getName(), neighbors.getName());
    }
    
    public void relationClone(KnowledgeNodeImpl o) {
        for (KnowledgeNodeImpl k : sources)
            o.addSource(k);
        for (KnowledgeNodeImpl k : destinations)
            o.addDestination(k);
        for (KnowledgeNodeImpl k : neighbors)
            o.addNeighbor(k);
    }
*/
    
    @Override
    public int compareTo(KnowledgeNode other) {
        return this.significance.compareTo(other.getSignificance());
    }
    
    @Override
    public int compare(KnowledgeNode t, KnowledgeNode t1) {
        return t.getSignificance() - t1.getSignificance();
    }
    
    @Override
    public Integer getId() { return this.id; }
    @Override
    public Integer getSignificance() { return this.significance; }
    @Override
    public String getName() { return this.name; }
    @Override
    public String getCatagory() { return this.catagory; }
    @Override
    public String getDefinition() { return this.definition; }
    @Override
    public String getDescription() { return this.description; }
    @Override
    public KnowledgeNodeListImpl getSources() { return this.sources; }
    @Override
    public KnowledgeNodeListImpl getDestinations() { return this.destinations; }
    @Override
    public KnowledgeNodeListImpl getNeighbors() { return this.neighbors; }
    @Override
    public boolean hasSource(String name) { return this.sources.contains(name); }
    @Override
    public boolean hasDestination(String name) { return this.destinations.contains(name); }
    @Override
    public boolean hasNeighbor(String name) { return this.neighbors.contains(name); }
    @Override
    public boolean hasSource(KnowledgeNode k) { return this.sources.contains(k); }
    @Override
    public boolean hasDestination(KnowledgeNode k) { return this.destinations.contains(k); }
    @Override
    public boolean hasNeighbor(KnowledgeNode k) { return this.neighbors.contains(k); }
    @Override
    public void setSignificance(Integer val) { this.significance = val; }
    @Override
    public void setId(Integer id) { this.id = id; }
    
    @Override
    public void setName(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("名称栏不能为空");
        this.name = newName;
    }
    
    @Override
    public void setCatagory(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("类别栏不能为空");
        this.catagory = newName;
    }
    
    @Override
    public void setDefinition(String newDefinition) {
        if ("".equals(newDefinition.trim()))
            throw new InvalidInputException("定义栏不能为空");
        this.definition = newDefinition;
    }
    
    @Override
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    
    @Override
    public void addSource(KnowledgeNode k) {
        if (this == k || this.name.equals(k.getName())) // must be a different node
            throw new InvalidInputException("Can't add self as a source.");
        
        this.sources.add(k);
        k.getDestinations().add(this);
        significance++;
        
        for (KnowledgeNode s : this.sources) 
            k.addNeighbor(s);      
        // addRelatedAsDestination(k)
    }
    
    @Override
    public void addDestination(KnowledgeNode k) {
        if (this == k || this.name.equals(k.getName())) // must be a different node
            throw new InvalidInputException("Can't add self as a destination.");
        
        this.destinations.add(k);
        k.getSources().add(this);
        k.setSignificance(k.getSignificance()+1);
        for (KnowledgeNode s : k.getSources()) 
            this.addNeighbor(s);
        // addRelatedAsSource(k);
    }
    
    @Override
    public void addNeighbor(KnowledgeNode k) {
        if (k == this || // k is iteself
            k.getName().equals(this.name) || // k has same name
//            || !this.catagory.equals(k.getCatagory()) // k is from a different catagory
                this.destinations.contains(k))  // k is a destination of this node
            return;  // do not add k as a neighbor
        this.neighbors.add(k);
        k.getNeighbors().add(this);
    }
    
    @Override
    public void removeSource(KnowledgeNode k) {
        if (this == k || this.name.equals(k.getName()))
            throw new InvalidInputException("Can't remove self as a source.");
        this.sources.remove(k);
        k.getDestinations().remove(this);
        significance--;
        for (KnowledgeNode s : this.sources)
            k.removeNeighbor(s);
    }
    
    @Override
    public void removeDestination(KnowledgeNode k) {
        if (this == k || this.name.equals(k.getName()))
            throw new InvalidInputException("Can't remove self as a destination.");
        this.destinations.remove(k);
        k.getSources().remove(this);
        k.setSignificance(k.getSignificance()-1);
        for (KnowledgeNode s : k.getSources())
            s.removeNeighbor(this);
    }
    
    @Override
    public void removeNeighbor(KnowledgeNode k) {
        if (k == this || // k is iteself
            k.getName().equals(this.name) || // k has same name
            !this.catagory.equals(k.getCatagory())) // k is from a different catagory
            return;  // do not remove k as a neighbor
        this.neighbors.remove(k);
        k.getNeighbors().remove(this);
    }
    
    @Override
    public void removeAllRelated() {
        removeAllSources();
        removeAllDestinations();
        removeAllNeighbors();
    }
    
    @Override
    public void removeAllSources() {
        for (KnowledgeNode k : sources)
            removeSource(k);
    }
    
    @Override
    public void removeAllDestinations() {
        for (KnowledgeNode k: destinations)
            removeDestination(k);
    }
    
    @Override
    public void removeAllNeighbors() {
        for (KnowledgeNode k : neighbors)
            removeNeighbor(k);
    }
    
    @Override
    public String chineseFormattedInformation() {
        return "名称: " + name + "\n\n类别: " + catagory + "\n\n定义: " + definition +
                "\n\n描述: " + description + "\n\n引用次数: " + significance +
                "\n\n" + sources.getName() + ":\n" +
                sources.membersToString() + "\n\n" + destinations.getName() + ":\n" +
                destinations.membersToString() + "\n\n" + neighbors.getName() + ":\n" +
                neighbors.membersToString();
    }
    
    @Override
    public String toString() {
        return name + " (" + catagory + ", referred: " + significance + ")";
    }
    
    // unit test
    public static void main(String[] args) {
        KnowledgeNodeImpl a = new KnowledgeNodeImpl("A", "Character", "A", "First", "Source", "Destination", "Neighbor");
        KnowledgeNodeImpl b = new KnowledgeNodeImpl("B", "Character", "B", "Second", "Source", "Destination", "Neighbor");
        KnowledgeNodeImpl c = new KnowledgeNodeImpl("C", "Character", "C", "Third", "Source", "Destination", "Neighbor");
        KnowledgeNodeImpl d = new KnowledgeNodeImpl("D", "Character", "D", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNodeImpl e = new KnowledgeNodeImpl("E", "Character", "E", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNodeImpl f = new KnowledgeNodeImpl("F", "Character", "F", "Fourth", "Source", "Destination", "Neighbor");
        a.addDestination(d);
        a.addDestination(d);
        e.addDestination(d);
        System.out.println(e.chineseFormattedInformation());
        
    }
}
