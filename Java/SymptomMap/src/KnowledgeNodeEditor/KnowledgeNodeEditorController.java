package KnowledgeNodeEditor;

import Models.*;
import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private ArrayList<KnowledgeNode> sourceRemoveList, destinationRemoveList, neighborRemoveList, 
            sourceAddList, destinationAddList, neighborAddList;
    private KnowledgeNodeEditorView view;
    
    public KnowledgeNodeEditorController(KnowledgeMap map, KnowledgeNode node) throws CloneNotSupportedException {
        this.map = map;
        this.tempMap = (KnowledgeMap) map.clone();
        this.node = node;
        this.tempNode = (KnowledgeNode) node.clone();
        this.sourceRemoveList = new ArrayList<>();
        this.destinationRemoveList = new ArrayList<>();
        this.neighborRemoveList = new ArrayList<>();
        this.sourceAddList = new ArrayList<>();
        this.destinationAddList = new ArrayList<>();
        this.neighborAddList = new ArrayList<>();
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
                sourceAddList.add(realNode);
                ((DefaultTreeModel) view.getKnowledgeTree().getModel()).insertNodeInto(
                        leaf,  view.getSourcesTreeNode(), 
                        view.getSourcesTreeNode().getChildCount()
                );
                break;
            case 2:
                destinationAddList.add(realNode);
                ((DefaultTreeModel) view.getKnowledgeTree().getModel()).insertNodeInto(
                        leaf, view.getDestinationsTreeNode(), 
                        view.getDestinationsTreeNode().getChildCount()
                );
                break;
            default:
                neighborAddList.add(realNode);
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
                    sourceRemoveList.add(selectedNode);
                    tempNode.removeSource(tempSelectedNode);
                } else if (type.equals(KnowledgeNodeList.TYPE.DESTINATIONS.getValue())) {
                    destinationRemoveList.add(selectedNode);
                    tempNode.removeDestination(tempSelectedNode);
                } else {
                    neighborRemoveList.add(selectedNode);
                    tempNode.removeNeighbor(tempSelectedNode);
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
    
    private void updateInfoPane() {
        view.getKnowledgeNodeInfoPane().setText(tempNode.chineseFormattedInformation());
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
            
            for (KnowledgeNode k : sourceRemoveList)
                node.removeSource(k);
            for (KnowledgeNode k : destinationRemoveList)
                node.removeDestination(k);
            for (KnowledgeNode k : neighborRemoveList)
                node.removeNeighbor(k);
            
            for (KnowledgeNode k : sourceAddList)
                node.addSource(k);
            for (KnowledgeNode k : destinationAddList)
                node.addDestination(k);
            for (KnowledgeNode k : neighborAddList)
                node.addNeighbor(k);
            
            view.dispose();
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
