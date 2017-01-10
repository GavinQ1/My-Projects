package KnowledgeNodeEditor;

import Models.*;
import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorController extends GeneralController implements KnowledgeNodeEditorControl {
    private KnowledgeMap map, tempMap;
    private KnowledgeNode node, tempNode;
    private HashMap<KnowledgeNode, Integer> sourceCache, destinationCache, neighborCache;
    private KnowledgeNodeEditorView view;
    
    public KnowledgeNodeEditorController(KnowledgeMap map, KnowledgeNode node) throws CloneNotSupportedException {
        this.map = map;
        this.tempMap = (KnowledgeMap) map.clone();
        this.node = node;
        this.tempNode = (KnowledgeNode) node.clone();
        this.sourceCache = new HashMap<>();
        this.destinationCache = new HashMap<>();
        this.neighborCache = new HashMap<>();
    }
    
    public void register(KnowledgeNodeEditorView view) {
        this.view = view;
    }
    
    public void knowledgeTreeValueChangedAction(MyTreeNode selected) {
        view.setSelectedTreeNode((MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent());
    }
    
    public void knowledgeTreeAddActionPerformed() {
        KnowledgeNodeLinkerApp dialog = new KnowledgeNodeLinkerApp(tempMap, tempNode);
        dialog.run();
        KnowledgeNode added = dialog.getSelected();
        if (added == null) return;
        int signal = dialog.getExpandedGroup();
        MyTreeNode leaf = new MyTreeNode(added);
        KnowledgeNode realNode = null;
        for (KnowledgeNode k : map.getAllKnowledgeNodes()) {
            if (k.getName().equals(added.getName())) {
                realNode = k;
                break;
            }
        }
        
        switch (signal) {
            case 1:
                sourceCache.putIfAbsent(realNode, 0);
                sourceCache.put(realNode, sourceCache.get(realNode) + 1);
                
                ((DefaultTreeModel) view.getKnowledgeTree().getModel()).insertNodeInto(
                        leaf,  view.getSourcesTreeNode(), 
                        view.getSourcesTreeNode().getChildCount()
                );
                break;
            case 2:
                destinationCache.putIfAbsent(realNode, 0);
                destinationCache.put(realNode, destinationCache.get(realNode) + 1);
                
                ((DefaultTreeModel) view.getKnowledgeTree().getModel()).insertNodeInto(
                        leaf, view.getDestinationsTreeNode(), 
                        view.getDestinationsTreeNode().getChildCount()
                );
                break;
            default:
                neighborCache.putIfAbsent(realNode, 0);
                neighborCache.put(realNode, neighborCache.get(realNode) + 1);
                
                ((DefaultTreeModel) view.getKnowledgeTree().getModel()).insertNodeInto(
                        leaf, view.getNeighborsTreeNode(),  
                        view.getNeighborsTreeNode().getChildCount()
                );
                break;
        }
        
        updateInfoPane();
    }
    
    public void knowledgeTreeRemoveActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n.isLeaf()) {
            try {
                KnowledgeNodeList parentList = (KnowledgeNodeList) ((MyTreeNode) n.getParent()).getUserObject();
                KnowledgeNode selectedNode = (KnowledgeNode) n.getUserObject();
                KnowledgeNode tempSelectedNode = (KnowledgeNode) selectedNode.clone();
                String type = parentList.getListType();
                if (type.equals(KnowledgeNodeList.TYPE.SOURCES.getValue())) {
                    tempNode.removeSource(tempSelectedNode);
                    sourceCache.putIfAbsent(selectedNode, 0);
                    sourceCache.put(selectedNode, sourceCache.get(selectedNode) - 1);
                } else if (type.equals(KnowledgeNodeList.TYPE.DESTINATIONS.getValue())) {
                    tempNode.removeDestination(tempSelectedNode);
                    destinationCache.putIfAbsent(selectedNode, 0);
                    destinationCache.put(selectedNode, destinationCache.get(selectedNode) - 1);
                } else {
                    tempNode.removeNeighbor(tempSelectedNode);
                    neighborCache.putIfAbsent(selectedNode, 0);
                    neighborCache.put(selectedNode, neighborCache.get(selectedNode) - 1);
                }
                removeNodeFromTree(n);
                updateInfoPane();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(KnowledgeNodeEditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    private void removeNodeFromTree(MyTreeNode node) {
        ((DefaultTreeModel) view.getKnowledgeTree().getModel()).removeNodeFromParent(node);
    }
    
    private void updateInfoPane(KnowledgeNode k) {
        view.getKnowledgeNodeInfoPane().setText(k.chineseFormattedInformation());
    }
    
    private void updateInfoPane() {
        updateInfoPane(tempNode);
    }
    
    public void initialize() {
        view.getNameTextField().setText(tempNode.getName());
        view.getCatagoryTextField().setText(tempNode.getCatagory());
        view.getDefinitionTextArea().setText(tempNode.getDefinition());
        view.getDescriptionTextArea().setText(tempNode.getDescription());
        updateInfoPane();
    }
    
    public void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt) {
        if(evt.getClickCount() == 2) {
            MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
            if (n.isLeaf()) {
                KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
            }
        }
    }
    
    public void knowledgeTreeRenameRootActionPerformed(){
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n.getUserObject() instanceof KnowledgeNodeList) {
            String newRootName = JOptionPane.showInputDialog("New Root Name");
            if ("".equals(newRootName.trim())) return;
            ((KnowledgeNodeList) n.getUserObject()).setName(newRootName);
            ((DefaultTreeModel) view.getKnowledgeTree().getModel()).nodeChanged(n);
            updateInfoPane();
        }
    }
    
    public void saveActionPerformed() {
        try {
            
            node.setName(view.getNameTextField().getText());
            node.setCatagory(view.getCatagoryTextField().getText());
            node.setDefinition(view.getDefinitionTextArea().getText());
            node.setDescription(view.getDescriptionTextArea().getText());
            
            for (KnowledgeNode k : sourceCache.keySet()) {
                if (sourceCache.get(k) < 0) node.removeSource(k);
                if (sourceCache.get(k) > 0) node.addSource(k);
            }
            for (KnowledgeNode k : destinationCache.keySet()){
                if (destinationCache.get(k) < 0) node.removeDestination(k);
                if (destinationCache.get(k) > 0) node.addDestination(k);
            }
            for (KnowledgeNode k : neighborCache.keySet()) {
                if (neighborCache.get(k) < 0) node.removeNeighbor(k);
                if (neighborCache.get(k) > 0) node.addNeighbor(k);
            }
            
            updateInfoPane(node);
            sourceCache = new HashMap<>();
            destinationCache = new HashMap<>();
            neighborCache = new HashMap<>();
        } catch (InvalidInputException e) {
            KnowledgeNodeEditorController.errorMessageBox(e.toString());
        }
    }
    
    public void cancelActionPerformed() {
        view.dispose();
    }
    
    public KnowledgeNode getTempNode() {
        return this.tempNode;
    }
}
