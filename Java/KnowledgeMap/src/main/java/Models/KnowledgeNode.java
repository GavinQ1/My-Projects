package Models;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.*;

/**
 *
 * @author Gavin
 */
@MappedSuperclass
public interface KnowledgeNode extends Cloneable, Comparable<KnowledgeNode>, Comparator<KnowledgeNode>, Serializable {

    void addDestination(KnowledgeNode k);

    void addNeighbor(KnowledgeNode k);

    void addRelatedAsDestination(KnowledgeNode k);

    void addRelatedAsSource(KnowledgeNode k);

    void addSource(KnowledgeNode k);

    String chineseFormattedInformation();

    Object clone() throws CloneNotSupportedException;

    int compare(KnowledgeNode t, KnowledgeNode t1);

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
    int compareTo(KnowledgeNode other);

    String getCatagory();

    String getDefinition();

    String getDescription();

    KnowledgeNodeList getDestinations();

    String getName();

    KnowledgeNodeList getNeighbors();

    Integer getSignificance();
    
    Integer getId();

    KnowledgeNodeListImpl getSources();

    boolean hasDestination(String name);

    boolean hasDestination(KnowledgeNode k);

    boolean hasNeighbor(String name);

    boolean hasNeighbor(KnowledgeNode k);

    boolean hasSource(String name);

    boolean hasSource(KnowledgeNode k);

    void removeDestination(KnowledgeNode k);

    void removeNeighbor(KnowledgeNode k);

    void removeSource(KnowledgeNode k);

    void setCatagory(String newName);

    void setDefinition(String newDefinition);

    void setDescription(String newDescription);

    void setName(String newName);
    
    void setSignificance(Integer val);
    
    void setId(Integer id);

    String toString();
    
}
