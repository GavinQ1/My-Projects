package Models;

import java.util.HashMap;

/**
 *
 * @author Gavin
 */
public class KnowledgeMap {
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
    
    public boolean containsCatagory(String name) {
        return this.map.containsKey(name);
    }
    
    public KnowledgeNodeList getCatagory(String name) {
        return this.map.get(name);
    }
    
    public void checkCatagory(String name) {
        if (!this.containsCatagory(name))
            throw new InvalidInputException("The map already has this catagory.");
    }
    
    public void addCatagory(String name) {
        this.map.put(name, new KnowledgeNodeList(name));
    }
    
    public KnowledgeNode getKnowldegeNodeFrom(String catagoryName, String name)
            throws InvalidInputException {
        checkCatagory(catagoryName);
        return this.map.get(catagoryName).getKnowledge(name);
    }
    
    public void addKnowledgeNodeTo(String catagoryName, KnowledgeNode k)
            throws InvalidInputException {
        checkCatagory(catagoryName);
        getCatagory(catagoryName).add(k);
    }
    
    public void deleteCatagory(String name) {
        if (!this.map.containsKey(name))
            return;
        this.map.remove(name);
    }
    
    public void deleteKnowledgeNodeFrom(String catagoryName, KnowledgeNode k) {
        if (!this.map.containsKey(catagoryName))
            return;
        getCatagory(catagoryName).remove(k);
    }
    
    public void deleteKnowledgeNodeFrom(String catagoryName, String name) {
        if (!this.map.containsKey(catagoryName))
            return;
        getCatagory(catagoryName).remove(name);
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
