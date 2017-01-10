package KnowledgeNodeEditor;

import Models.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorApp {
    public static final void run(KnowledgeMap map, KnowledgeNode node) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnowledgeNodeWikiApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        KnowledgeNodeEditorController controller;
        try {
            controller = new KnowledgeNodeEditorController(map, node);
            new KnowledgeNodeEditorView(controller).setVisible(true);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(KnowledgeNodeEditorApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
