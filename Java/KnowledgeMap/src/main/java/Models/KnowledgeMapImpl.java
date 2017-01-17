package Models;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

/**
 *
 * @author Gavin
 */
@Entity(name = "KnowledgeMap")
public class KnowledgeMapImpl implements KnowledgeMap {
    @Id
    @Basic
    private String name;
    @Basic
    private Integer size;
    @ElementCollection
    @CollectionTable(name = "knowldegeNodeCollection")
    @MapKeyJoinColumn(name = "KnowledgeNodeCollection_id", referencedColumnName = "id")
    private Map<String, ArrayList<KnowledgeNode>> map;

    public KnowledgeMapImpl() {
        this("New Map");
    }

    public KnowledgeMapImpl(String name) {
        this.name = name;
        this.map = new HashMap<>();
        this.size = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public Map<String, ArrayList<KnowledgeNode>> getMap() {
        return this.map;
    }

    @Override
    public void setMap(Map<String, ArrayList<KnowledgeNode>> map) {
        this.map = map;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            KnowledgeMap cloned = (KnowledgeMap) super.clone();
            cloned.setMap((HashMap<String, ArrayList<KnowledgeNode>>) 
                    ((HashMap<String, ArrayList<KnowledgeNode>>) map).clone());
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public boolean containsCatagory(String catagoryName) {
        return this.map.containsKey(catagoryName);
    }

    @Override
    public List<KnowledgeNode> getCatagory(String catagoryName) {
        this.map.putIfAbsent(catagoryName, new ArrayList<>());
        return this.map.get(catagoryName);
    }

    @Override
    public void setCatagoryName(String oldName, String newName) {
        if (containsCatagory(newName)) 
            throw new InvalidInputException("The map does not have this catagory.");
        
        ArrayList<KnowledgeNode> a = new ArrayList<>();
        getCatagory(oldName).stream().map((k) -> {
            k.setCatagory(newName);
            return k;
        }).forEach((k) -> {
            a.add(k);
        });
        map.put(newName, a);
        map.remove(oldName);
    }

    @Override
    public void addCatagory(String catagoryName) {
        this.map.put(catagoryName, new ArrayList<>());
    }

    @Override
    public KnowledgeNode getKnowldegeNodeFrom(String catagoryName, String knowledgeNodeName)
            throws InvalidInputException {
        if (containsCatagory(catagoryName)) {
            for (KnowledgeNode k : this.map.get(catagoryName)) {
                if (knowledgeNodeName.equals(k.getName())) {
                    return k;
                }
            }
        }
        return null;
    }

    @Override
    public void addKnowledgeNodeTo(String catagoryName, KnowledgeNode k) {
        if (k == null) 
            return;
        
        getCatagory(catagoryName).add(k);
        k.setId(++size);
    }

    @Override
    public void deleteCatagory(String catagoryName) {
        if (!containsCatagory(catagoryName)) 
            return;
        
        getCatagory(catagoryName).stream().forEach((k) -> {
            k.removeAllRelated();
        });
        this.map.remove(catagoryName);
    }

    @Override
    public void deleteKnowledgeNodeFrom(String catagoryName, KnowledgeNode k) {
        if (!this.map.containsKey(catagoryName)) 
            return;
        
        boolean exist = getCatagory(catagoryName).remove(k);
        if (exist) 
            k.removeAllRelated();
    }

    @Override
    public void moveKnowledgeNodeFrom(String oldCatagory, String newCatagory, KnowledgeNode k) {
        if (!this.map.containsKey(oldCatagory)) 
            return;
        
        getCatagory(oldCatagory).remove(k);
        getCatagory(newCatagory).add(k);
    }

    @Override
    public List<KnowledgeNode> getAllKnowledgeNodes() {
        ArrayList<KnowledgeNode> a = new ArrayList<>();
        map.keySet().stream().forEach((key) -> {
            a.addAll(new HashSet<>(map.get(key)));
        });
        Collections.sort(a, KnowledgeNodeList.comparatorBySignificance());
        return (ArrayList<KnowledgeNode>) a;
    }
    
    @Override
    public String[] getAllCatagoryNames() {
        return map.keySet().toArray(new String[map.size()]);
    }

    // unit test
    public static void main(String[] args) {
        KnowledgeMap map = new KnowledgeMapImpl("K");
        map.addCatagory("Test");
        System.out.println(map.containsCatagory("Test"));
        KnowledgeNode a = new KnowledgeNodeImpl("a", "Solution", "", "", "", "", "");
        map.addKnowledgeNodeTo("Test", a);
        System.out.println(map.getKnowldegeNodeFrom("Test", "a").chineseFormattedInformation());
        map.deleteKnowledgeNodeFrom("Test", a);
    }
}
