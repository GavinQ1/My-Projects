package Models;

import java.util.HashMap;
import java.io.Serializable;

/**
 *
 * @author Gavin
 */
public class KnowledgeMap implements Serializable {
    private String name;
    private final HashMap<String, KnowledgeNodeList> map;
    
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
    
    public HashMap<String, KnowledgeNodeList> getMap() {
        return this.map;
    }
    
    public int size() {
        return this.map.size();
    }
    
    public boolean containsCatagory(String catagoryName) {
        return this.map.containsKey(catagoryName);
    }
    
    public KnowledgeNodeList getCatagory(String catagoryName) {
        return this.map.get(catagoryName);
    }
    
    public void checkCatagory(String catagoryName) {
        if (!this.containsCatagory(catagoryName))
            throw new InvalidInputException("The map already has this catagory.");
    }
    
    public void addCatagory(String catagoryName) {
        this.map.put(catagoryName, new KnowledgeNodeList(catagoryName));
    }
    
    public KnowledgeNode getKnowldegeNodeFrom(String catagoryName, String knowledgeNodeName)
            throws InvalidInputException {
        checkCatagory(catagoryName);
        return this.map.get(catagoryName).getKnowledge(knowledgeNodeName);
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
    
    // unit test
    public static void main(String[] args) {
        KnowledgeMap map = new KnowledgeMap("K");
        map.addCatagory("Test");
        System.out.println(map.containsCatagory("Test"));
        map.addKnowledgeNodeTo("Test", new Symptom("a", "", "", "", "", ""));
        System.out.println(map.getKnowldegeNodeFrom("Test", "a").getName());
        map.deleteKnowledgeNodeFrom("Test", "a");
        System.out.println(map.getCatagory("Test"));
    }
}
