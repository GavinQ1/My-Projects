package KnowledgeNodeEditor;

import Models.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorApp {
    
    public static KnowledgeNode edit(KnowledgeMap map, KnowledgeNode node) {
        return KnowledgeNodeEditorApp.edit(map, node, true);
    }
    
    public static KnowledgeNode create(KnowledgeMap map) {
        return KnowledgeNodeEditorApp.create(map, false);
    }
    
    public static KnowledgeNode edit(KnowledgeMap map, KnowledgeNode node, boolean center) {
        EnhanceAppView.enhanceVision();
        
        if (node == null)
            node = new KnowledgeNodeImpl();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        view.setVisible(true);
        
        if (!view.isSaved()) return null;
        return view.getNode();
    }
    
    public static KnowledgeNode create(KnowledgeMap map, boolean center) {
        EnhanceAppView.enhanceVision();
        
        KnowledgeNode node = new KnowledgeNodeImpl();
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, node);
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller); 
        if (center)
            view.setLocationRelativeTo(null);
        view.setVisible(true);
        
        if (!view.isSaved()) return null;
        return view.getNode();
    }
    
    
    public static void main(String[] args) {
        EnhanceAppView.enhanceVision();
        
        KnowledgeNode a = new KnowledgeNodeImpl("症状1", "症状", "A", "First", "Source", "相关的结果", "相关的症状");
        KnowledgeNode b = new KnowledgeNodeImpl("体质1", "体质", "B", "Second", "Source", "相关的结果", "相关的体质");
        KnowledgeNode c = new KnowledgeNodeImpl("药方1", "药方", "C", "Third", "Source", "相关的结果", "相关的药方");
        KnowledgeNode d = new KnowledgeNodeImpl("药方2", "药方", "D", "Fourth", "Source", "相关的结果", "相关的药方");
        KnowledgeNode e = new KnowledgeNodeImpl("体质2", "体质", "E", "Fourth", "Source", "相关的结果", "相关的体质");
        KnowledgeNode f = new KnowledgeNodeImpl("症状2", "症状", "F", "Fourth", "Source", "相关的结果", "相关的症状");

        a.addDestination(b);
        b.addDestination(c);
        a.addDestination(c);
        a.addDestination(e);
        f.addDestination(e);

        KnowledgeMap map = new KnowledgeMapImpl("K");
        map.addCatagory("症状");
        map.addCatagory("体质");
        map.addCatagory("药方");
        map.addKnowledgeNodeTo(a.getCatagory(), a);
        map.addKnowledgeNodeTo(b.getCatagory(), b);
        map.addKnowledgeNodeTo(c.getCatagory(), c);
        map.addKnowledgeNodeTo(d.getCatagory(), d);
        map.addKnowledgeNodeTo(e.getCatagory(), e);
        map.addKnowledgeNodeTo(f.getCatagory(), f);
        
        KnowledgeNode k = create(map);
        System.out.println(k.chineseFormattedInformation());
    }
}
