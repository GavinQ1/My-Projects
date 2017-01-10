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
    
    int size;
    private String catagoryName, listType;
    private HashMap<KnowledgeNode, Integer> hist;
    private HashMap<String, Integer> nameHist;
    
    public KnowledgeNodeList(String catagoryName) {
        this.size = 0;
        this.catagoryName = catagoryName;
        this.listType = null;
        this.hist = new HashMap<>();
        this.nameHist = new HashMap<>();
    }
    
    public KnowledgeNodeList() {
        this(null);
    }
    
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeNodeList cloned = (KnowledgeNodeList) super.clone();
            cloned.nameHist = (HashMap<String, Integer>) nameHist.clone();
            cloned.hist = (HashMap<KnowledgeNode, Integer>) hist.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    public static final Comparator<KnowledgeNode> comparatorByRelativity(
            final HashMap<KnowledgeNode, Integer> hist) {
        return new Comparator<KnowledgeNode>() {
          public int compare(KnowledgeNode k1, KnowledgeNode k2) {
              return hist.get(k1).compareTo(hist.get(k2));
          }  
        };
    }
    
    public int size() { return this.size; }
    public boolean contains(KnowledgeNode k) { return hist.containsKey(k) && hist.get(k) > 0; }
    public boolean contains(String name) { return nameHist.containsKey(name) && nameHist.get(name) > 0; }
    public String getListType() { return this.listType; }
    public HashMap<KnowledgeNode, Integer> getHist() { return this.hist; };
    public Iterator<KnowledgeNode> iterator() { return getList().iterator(); }
    
    public void add(KnowledgeNode k) {
        if (k == null)
            throw new InvalidInputException("Input cannot be null");
        
        if (!contains(k)) {
            hist.put(k, 1);
            nameHist.put(k.getName(), 1);
            size++;
        } else {
            hist.put(k, hist.get(k) + 1);
            nameHist.put(k.getName(), nameHist.get(k.getName()) + 1);
        }
    }
    
    public void remove(KnowledgeNode k) {
        if (!contains(k)) return;
        hist.put(k, hist.get(k) - 1);
        nameHist.put(k.getName(), nameHist.get(k.getName()) - 1);
        if (hist.get(k) == 0) size--;
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
    
    public void setType(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.listType = newName;
    }
    
    public ArrayList<KnowledgeNode> getList() {
        ArrayList<KnowledgeNode> a = new ArrayList<>();
        for (KnowledgeNode k : hist.keySet()) {
            if (hist.get(k) > 0)
                a.add(k);
        }
        return a;
    }
    
    public KnowledgeNode getKnowledge(String name) {
        for (KnowledgeNode k : getList()) {
            if (name.equals(k.getName()))
                return k;
        }
        return null;
    }
    
    public String toString() {
        String res = "";
        ArrayList<KnowledgeNode> a = getList();
        Collections.sort(a, KnowledgeNode.comparatorBySignificance());
        res = a.stream().map((s) -> s.getName() + " ").reduce(res, String::concat);
        return res;
    }
    
    public String membersInString() {
        String res = "";
        ArrayList<KnowledgeNode> uniqueList = getList();
        Collections.sort(uniqueList, KnowledgeNodeList.comparatorByRelativity(hist));
        for (int i = 0; i < uniqueList.size(); i++) {
            KnowledgeNode node = uniqueList.get(i);
            res += node.getName() + " (" + node.getSignificance() + ")";
            if (i < uniqueList.size()-1)
                res += "\n";
        }
        return res;
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
