package Models;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Gavin
 */
public class KnowledgeMap implements Serializable, Cloneable {
    private String name;
    private HashMap<String, ArrayList<KnowledgeNode>> map;
    
    public KnowledgeMap(String name) {
        this.name = name;
        this.map = new HashMap<>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public HashMap<String, ArrayList<KnowledgeNode>> getMap() {
        return this.map;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeMap cloned = (KnowledgeMap) super.clone();
            cloned.map = (HashMap<String, ArrayList<KnowledgeNode>>) map.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    public int size() {
        return this.map.size();
    }
    
    public boolean containsCatagory(String catagoryName) {
        return this.map.containsKey(catagoryName);
    }
    
    public ArrayList<KnowledgeNode> getCatagory(String catagoryName) {
        return this.map.get(catagoryName);
    }
    
    public void checkCatagory(String catagoryName) {
        if (!this.containsCatagory(catagoryName))
            throw new InvalidInputException("The map does not have this catagory.");
    }
    
    public void addCatagory(String catagoryName) {
        this.map.put(catagoryName, new ArrayList<>());
    }
    
    public KnowledgeNode getKnowldegeNodeFrom(String catagoryName, String knowledgeNodeName)
            throws InvalidInputException {
        checkCatagory(catagoryName);
        for (KnowledgeNode k : this.map.get(catagoryName)) {
            if (knowledgeNodeName.equals(k.getName()))
                return k;
        }
        return null;   
    }
    
    public void addKnowledgeNodeTo(String catagoryName, KnowledgeNode k)
            throws InvalidInputException {
        checkCatagory(catagoryName);
        getCatagory(catagoryName).add(k);
    }
    
    public void deleteCatagory(String catagoryName) {
        if (!this.map.containsKey(catagoryName))
            return;
        this.map.remove(catagoryName);
    }
    
    public void deleteKnowledgeNodeFrom(String catagoryName, KnowledgeNode k) {
        if (!this.map.containsKey(catagoryName))
            return;
        getCatagory(catagoryName).remove(k);
    }
    
    public void deleteKnowledgeNodeFrom(String catagoryName, String knowledgeNodeName) {
        if (!this.map.containsKey(catagoryName))
            return;
        getCatagory(catagoryName).remove(knowledgeNodeName);
    }
    
    public ArrayList<KnowledgeNode> getAllKnowledgeNodes() {
        ArrayList<KnowledgeNode> a = new ArrayList<>();
        for (String key : map.keySet()) 
            a.addAll(new HashSet<>(map.get(key)));
        Collections.sort(a, KnowledgeNodeList.comparatorBySignificance());
        return a;
    }
    
    // unit test
    public static void main(String[] args) {
        KnowledgeMap map = new KnowledgeMap("K");
        map.addCatagory("Test");
        System.out.println(map.containsCatagory("Test"));
        map.addKnowledgeNodeTo("Test", new KnowledgeNodeImpl("a", "Solution", "", "", "", "", ""));
        System.out.println(map.getKnowldegeNodeFrom("Test", "a").chineseFormattedInformation());
        map.deleteKnowledgeNodeFrom("Test", "a");
    }
}
