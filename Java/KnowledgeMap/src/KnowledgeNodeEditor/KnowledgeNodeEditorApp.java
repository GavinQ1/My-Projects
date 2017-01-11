package KnowledgeNodeEditor;

import Models.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorApp {
    
    public static final KnowledgeNode edit(KnowledgeMap map, KnowledgeNode node) {
        return KnowledgeNodeEditorApp.edit(map, node, true);
    }
    
    public static final KnowledgeNode create(KnowledgeMap map) {
        return KnowledgeNodeEditorApp.create(map, false);
    }
    
    public static final KnowledgeNode edit(KnowledgeMap map, KnowledgeNode node, boolean center) {
        EnhanceAppView.enhanceVision();
        
        if (node == null)
            node = new KnowledgeNode();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        view.setVisible(true);
        
        return node;
    }
    
    public static final KnowledgeNode create(KnowledgeMap map, boolean center) {
        EnhanceAppView.enhanceVision();
        
        KnowledgeNode node = new KnowledgeNode();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        view.setVisible(true);
        
        return node;
    }
}
