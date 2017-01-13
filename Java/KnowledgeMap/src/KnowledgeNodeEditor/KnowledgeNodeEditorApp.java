package KnowledgeNodeEditor;

import Models.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorApp {
    
    public static KnowledgeNodeEditorView edit(KnowledgeMap map, KnowledgeNode node) {
        return KnowledgeNodeEditorApp.edit(map, node, true);
    }
    
    public static KnowledgeNodeEditorView create(KnowledgeMap map) {
        return KnowledgeNodeEditorApp.create(map, false);
    }
    
    public static KnowledgeNodeEditorView edit(KnowledgeMap map, KnowledgeNode node, boolean center) {
        EnhanceAppView.enhanceVision();
        
        if (node == null)
            node = new KnowledgeNodeImpl();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        
        return view;
    }
    
    public static KnowledgeNodeEditorView create(KnowledgeMap map, boolean center) {
        EnhanceAppView.enhanceVision();
        
        KnowledgeNode node = new KnowledgeNodeImpl();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        
        return view;
    }
}
