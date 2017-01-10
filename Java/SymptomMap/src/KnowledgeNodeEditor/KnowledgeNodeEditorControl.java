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
    public void knowledgeTreeAddActionPerformed();
    public void knowledgeTreeRemoveActionPerformed();
    public void knowledgeTreeRenameRootActionPerformed();
    public void editKnowledgeNodeActionPerformed();
    
    public void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt);
    
    public void saveActionPerformed();
    public void cancelActionPerformed();
    
    
}
