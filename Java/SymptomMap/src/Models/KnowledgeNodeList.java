package Models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeList implements Iterable<KnowledgeNode> {
    private String catagoryName;
    private final ArrayList<KnowledgeNode> list;
    
    public KnowledgeNodeList(String catagoryName) {
        this.catagoryName = catagoryName;
        this.list = new ArrayList<>();
    }
    
    public KnowledgeNodeList() {
        this("Untitled");
    }
    
    public int size() {
        return this.list.size();
    }
    
    public void add(KnowledgeNode k) {
        this.list.add(k);
    }
    
    public void remove(String name) {
        this.list.remove(getKnowledge(name));
    }
    
    public void remove(KnowledgeNode k) {
        this.list.remove(k);
    }
    
    public String getName() {
        return this.catagoryName;
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
    
    public Iterator<KnowledgeNode> iterator() {
        return this.list.iterator();
    }
    
    public static void main(String[] args) {
        KnowledgeNodeList k = new KnowledgeNodeList();
        k.add(new Solution("a", "", "", "", "", ""));
        System.out.println(k.contains("a"));
        k.remove("a");
        System.out.println(k.contains("a"));
    }
}
