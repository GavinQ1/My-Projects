package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import javax.persistence.*;

/**
 *
 * @author Gavin
 */
@MappedSuperclass
public interface KnowledgeNodeList extends Cloneable, Iterable<KnowledgeNode>, Serializable {
    public static enum TYPE { 
        SOURCES("Sources"), DESTINATIONS("Destinations"), NEIGHBORS("Neighbors");
        
        private String value;
        private TYPE(String value) {
            this.value = value;
        }
        public String getValue() {
            return this.value;
        }
    }

    void add(KnowledgeNode k);

    Object clone() throws CloneNotSupportedException;

    boolean contains(KnowledgeNode k);

    boolean contains(String name);

    HashMap<KnowledgeNode, Integer> getHist();

    KnowledgeNode getKnowledge(String name);

    ArrayList<KnowledgeNode> getList();

    String getListType();

    String getName();
    
    String getFullName();
    
    String getId();

    static Comparator<KnowledgeNode> comparatorByCatagory() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k1.getCatagory().compareTo(k2.getCatagory());
    }

    static Comparator<KnowledgeNode> comparatorBySignificance() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k2.getSignificance().compareTo(k1.getSignificance());
    }

    static Comparator<KnowledgeNode> comparatorByName() {
        return (Comparator<KnowledgeNode>) (KnowledgeNode k1, KnowledgeNode k2) -> k1.getName().compareTo(k2.getName());
    }

    static Comparator<KnowledgeNode> comparatorByRelativity(
            final HashMap<KnowledgeNode, Integer> hist) {
        return (KnowledgeNode k1, KnowledgeNode k2) -> hist.get(k1).compareTo(hist.get(k2));
    }

    Iterator<KnowledgeNode> iterator();

    String membersToString();

    void remove(KnowledgeNode k);

    void setName(String newName);

    void setType(String newName);
    
    void setId(String newId);

    int size();

    String toString();

}
