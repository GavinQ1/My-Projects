package Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeListImpl implements KnowledgeNodeList {
    int size;
    private String catagoryName, listType;
    private HashMap<KnowledgeNode, Integer> hist;
    private HashMap<String, Integer> nameHist;
    
    public KnowledgeNodeListImpl(String catagoryName) {
        this.size = 0;
        this.catagoryName = catagoryName;
        this.listType = null;
        this.hist = new HashMap<>();
        this.nameHist = new HashMap<>();
    }
    
    public KnowledgeNodeListImpl() {
        this(null);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeNodeListImpl cloned = (KnowledgeNodeListImpl) super.clone();
            cloned.nameHist = (HashMap<String, Integer>) nameHist.clone();
            cloned.hist = (HashMap<KnowledgeNode, Integer>) hist.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    @Override
    public int size() { return this.size; }
    @Override
    public boolean contains(KnowledgeNode k) { return hist.containsKey(k) && hist.get(k) > 0; }
    @Override
    public boolean contains(String name) { return nameHist.containsKey(name) && nameHist.get(name) > 0; }
    @Override
    public String getListType() { return this.listType; }
    @Override
    public HashMap<KnowledgeNode, Integer> getHist() { return this.hist; };
    @Override
    public Iterator<KnowledgeNode> iterator() { return getList().iterator(); }
    
    
    @Override
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
    
    @Override
    public void remove(KnowledgeNode k) {
        if (!contains(k)) return;
        hist.put(k, hist.get(k) - 1);
        nameHist.put(k.getName(), nameHist.get(k.getName()) - 1);
        if (hist.get(k) == 0) size--;
    }
    
    @Override
    public String getName() {
        if (this.listType != null)
            return this.listType + " - " + this.catagoryName;
        return this.catagoryName;
    }
    
    @Override
    public void setName(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.catagoryName = newName;
    }
    
    @Override
    public void setType(String newName) {
        if ("".equals(newName.trim()))
            throw new InvalidInputException("Input can't be empty.");
        this.listType = newName;
    }
    
    @Override
    public ArrayList<KnowledgeNode> getList() {
        ArrayList<KnowledgeNode> a = new ArrayList<>();
        for (KnowledgeNode k : hist.keySet()) {
            if (hist.get(k) > 0)
                a.add(k);
        }
        return a;
    }
    
    @Override
    public KnowledgeNode getKnowledge(String name) {
        for (KnowledgeNode k : getList()) {
            if (name.equals(k.getName()))
                return k;
        }
        return null;
    }
    
    @Override
    public String toString() {
        String res = "";
        ArrayList<KnowledgeNode> a = getList();
        Collections.sort(a, KnowledgeNodeList.comparatorBySignificance());
        res = a.stream().map((s) -> s.getName() + " ").reduce(res, String::concat);
        return res;
    }
    
    @Override
    public String membersToString() {
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
        KnowledgeNodeListImpl k = new KnowledgeNodeListImpl();
        KnowledgeNodeListImpl temp;
        try {
            k.add(new KnowledgeNodeImpl("a", "Solution", "", "", "", "", ""));
            temp = (KnowledgeNodeListImpl) k.clone();
            
            System.out.println(temp.membersToString());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(KnowledgeNodeListImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
