package Models;

/**
 *
 * @author Gavin
 */
public class Solution extends KnowledgeNode {
    private static final String SOURCE_NAME = "症状";
    private static final String DESTIN_NAME = "untitled";
    private static final String NEIGHBOR_NAME = "相关的药方";
    
    public Solution(String name, String definition, String description, 
            String sourcesName, String destinationsName, String neighborsName) {
        super(name, definition, description, 
                sourcesName, destinationsName, neighborsName);
        
    }
    
    public Solution(String name, String definition, String description) {
        this(name, definition, description, 
                SOURCE_NAME, DESTIN_NAME,NEIGHBOR_NAME);
    }
}
