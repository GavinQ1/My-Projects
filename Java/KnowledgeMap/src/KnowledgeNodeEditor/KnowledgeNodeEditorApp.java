package KnowledgeNodeEditor;

import Models.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorApp {
    public static final KnowledgeNode edit(KnowledgeMap map, KnowledgeNode node) {
        EnhanceAppView.enhanceVision();
        
        if (node == null)
            node = new KnowledgeNode();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        
        return node;
    }
    
    public static final KnowledgeNode create(KnowledgeMap map) {
        EnhanceAppView.enhanceVision();
        
        KnowledgeNode node = new KnowledgeNode();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        new KnowledgeNodeEditorView(controller).setVisible(true);
        
        return node;
    }
}
