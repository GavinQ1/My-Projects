package KnowledgeNodeEditor;

import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import Models.KnowledgeNode;
import java.awt.event.MouseEvent;


/**
 *
 * @author Gavin
 */
public interface KnowledgeNodeEditorControl {
    
    public void register(KnowledgeNodeEditorView view);
    public void initialize();
    
    public KnowledgeNode getNode();
    public void knowledgeTreeValueChangedAction(MyTreeNode selected);
    
    public void knowledgeTreeAddActionPerformed();   // Add
    public void knowledgeTreeRemoveActionPerformed(); // Remove
    public void knowledgeTreeRenameRootActionPerformed(); // Rename
    public void editKnowledgeNodeActionPerformed(); // Edit
    public void newNodeActionPerformed(); // New
    public void saveActionPerformed(); // Save
    public void exitActionPerformed(); // Exit
    
    public void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt);
    
    
    
}
