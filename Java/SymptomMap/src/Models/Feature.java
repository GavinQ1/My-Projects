package Models;

/**
 *
 * @author Gavin
 */
public class Feature extends KnowledgeNode {
    private static final String SOURCE_NAME = "untitled";
    private static final String DESTIN_NAME = "对应的症状";
    private static final String NEIGHBOR_NAME = "相关的表现";
    
    public Feature(String name, String definition, String description, 
            String sourcesName, String destinationsName, String neighborsName) {
        super(name, definition, description, 
                sourcesName, destinationsName, neighborsName);
        
    }
    
    public Feature(String name, String definition, String description) {
        this(name, definition, description, 
                SOURCE_NAME, DESTIN_NAME,NEIGHBOR_NAME);
    }
}
