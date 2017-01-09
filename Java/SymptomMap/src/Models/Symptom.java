package Models;

/**
 *
 * @author Gavin
 */
public class Symptom extends KnowledgeNode {
    private static final String SOURCE_NAME = "表现";
    private static final String DESTIN_NAME = "对应的药方";
    private static final String NEIGHBOR_NAME = "可用同种药方的症状";
    
    public Symptom(String name, String definition, String description, 
            String sourcesName, String destinationsName, String neighborsName) {
        super(name, definition, description, 
                sourcesName, destinationsName, neighborsName);
        
    }
    
    public Symptom(String name, String definition, String description) {
        this(name, definition, description, 
                SOURCE_NAME, DESTIN_NAME,NEIGHBOR_NAME);
    }
}
