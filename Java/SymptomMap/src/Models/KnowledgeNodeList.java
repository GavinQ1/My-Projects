package Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeList implements Iterable<KnowledgeNode>, 
        Serializable, Cloneable {
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
    
    private String catagoryName, listType;
    private ArrayList<KnowledgeNode> list;
    private HashMap<KnowledgeNode, Integer> hist = new HashMap<>();
    
    public KnowledgeNodeList(String catagoryName) {
        this.catagoryName = catagoryName;
        this.listType = null;
        this.list = new ArrayList<>();
    }
    
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeNodeList cloned = (KnowledgeNodeList) super.clone();
            cloned.list = (ArrayList<KnowledgeNode>) list.clone();
            cloned.hist = (HashMap<KnowledgeNode, Integer>) hist.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    public KnowledgeNodeList() {
        this(null);
    }
    
    public int size() {
        return this.list.size();
    }
    
    public void add(KnowledgeNode k) {
        this.list.add(k);
        hist.putIfAbsent(k, 0);
        hist.put(k, hist.get(k) + 1);
    }
    
    public void remove(String name) {
        this.list.remove(getKnowledge(name));
    }
    
    public void remove(KnowledgeNode k) {
        this.list.remove(k);
        hist.put(k, hist.get(k) - 1);
    }
    
    public String getName() {
        if (this.listType != null)
            return this.listType + " - " + this.catagoryName;
        return this.catagoryName;
    }
    
    public void setName(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.catagoryName = newName;
    }
    
    public String getListType() {
        return this.listType;
    }
    
    public void setType(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.listType = newName;
    }
    
    public ArrayList<KnowledgeNode> getList() {
        return list;
    }
    
    public boolean contains(String name) {
        return this.list.stream().anyMatch((k) -> (k.getName().equals(name)));
    }
    
    public KnowledgeNode getKnowledge(String name) {
        for (KnowledgeNode k : this.list) {
            if (name.equals(k.getName()))
                return k;
        }
        return null;
    }
    
    public String toString() {
        String res = "";
        for (KnowledgeNode s : list)
            res += s.getName() + " ";
        return res;
    }
    
    public String membersInString() {
        String res = "";
        ArrayList<KnowledgeNode> uniqueList = new ArrayList<>(hist.keySet());
        Collections.sort(uniqueList, KnowledgeNodeList.comparatorByRelativity(hist));
        for (int i = 0; i < uniqueList.size(); i++) {
            KnowledgeNode node = uniqueList.get(i);
            if (hist.get(node) < 1) continue;
            res += node.getName() + " (" + hist.get(node) + ")";
            if (i < uniqueList.size()-1)
                res += "\n";
        }
        return res;
    }
    
    public Iterator<KnowledgeNode> iterator() {
        return this.list.iterator();
    }
    
    public static final Comparator<KnowledgeNode> comparatorByRelativity(
            final HashMap<KnowledgeNode, Integer> hist) {
        return new Comparator<KnowledgeNode>() {
          public int compare(KnowledgeNode k1, KnowledgeNode k2) {
              return hist.get(k1).compareTo(hist.get(k2));
          }  
        };
    }
    
    public static void main(String[] args) {
        KnowledgeNodeList k = new KnowledgeNodeList();
        KnowledgeNodeList temp;
        try {
            k.add(new KnowledgeNode("a", "Solution", "", "", "", "", ""));
            temp = (KnowledgeNodeList) k.clone();
            
            System.out.println(temp.membersInString());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(KnowledgeNodeList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
