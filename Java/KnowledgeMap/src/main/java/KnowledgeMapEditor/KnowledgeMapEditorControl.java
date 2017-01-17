/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapEditor;

import Models.*;
import java.awt.event.MouseEvent;

/**
 *
 * @author Gavin
 */
public interface KnowledgeMapEditorControl {
    
    KnowledgeMap getMap();
    
    void initialize();
    
    void register(KnowledgeMapEditorView view);
    
    void nodesTreeMouseClicked(MouseEvent evt);
    
    void nodesTreeMousePressed(MouseEvent evt);
    
    void nodesTreeMouseReleased(MouseEvent evt);
    
    void editNodeButtonActionPerformed();
    
    void createNodeButtonActionPerformed();
    
    void deleteNodeButtonActionPerformed();
    
    void viewNodeButtonActionPerformed();
}
