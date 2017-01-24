package KnowledgeNodeEditor;

import KnowledgeNodeModels.KnowledgeNode;
import java.awt.event.MouseEvent;


/**
 *
 * @author Gavin
 */
public interface KnowledgeNodeEditorControl {
    
    void register(KnowledgeNodeEditorView view);
    void initialize();
    
    KnowledgeNode getNode();
    void catagoryComboBoxActionPerformed();
    
    void knowledgeTreeAddActionPerformed();   // Add
    void knowledgeTreeRemoveActionPerformed(); // Remove
    void knowledgeTreeRenameRootActionPerformed(); // Rename
    void editKnowledgeNodeActionPerformed(); // Edit
    void newNodeActionPerformed(); // New
    void saveActionPerformed(); // Save
    void exitActionPerformed(); // Exit
    boolean isSaved();
    
    void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt);
    
    
    
}
