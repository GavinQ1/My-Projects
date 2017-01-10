package KnowledgeNodeEditor;

import Models.*;
import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorController extends GeneralController implements KnowledgeNodeEditorControl {
    private KnowledgeMap map;
    private KnowledgeNode node;
    private KnowledgeNodeEditorView view;
    
    public KnowledgeNodeEditorController(KnowledgeMap map, KnowledgeNode node) throws CloneNotSupportedException {
        this.map = map;
        this.node = node;
    }
    
    public void register(KnowledgeNodeEditorView view) { this.view = view; }
    public void cancelActionPerformed() { view.dispose(); }
    public KnowledgeNode getNode() { return this.node; }
    public void knowledgeTreeValueChangedAction(MyTreeNode selected) {
        view.setSelectedTreeNode((MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent());
    }
    
    public void knowledgeTreeAddActionPerformed() {
        KnowledgeNodeLinkerApp dialog = new KnowledgeNodeLinkerApp(map, node);
        dialog.run();
        KnowledgeNode added = dialog.getSelected();
        if (added == null) return;
        int signal = dialog.getExpandedGroup();
        MyTreeNode leaf = new MyTreeNode(added);
        
        switch (signal) {
            case 1:
                if (node.getSources().contains(added)) {
                    GeneralController.errorMessageBox("Node is already added");
                    return;
                }
                
                node.addSource(added);
                view.getTreeModel().insertNodeInto(
                        leaf,  view.getSourcesTreeNode(), 
                        view.getSourcesTreeNode().getChildCount()
                );
                break;
            case 2:
                if (node.getDestinations().contains(added)) {
                    GeneralController.errorMessageBox("Node is already added");
                    return;
                }
                
                node.addDestination(added);
                view.getTreeModel().insertNodeInto(
                        leaf, view.getDestinationsTreeNode(), 
                        view.getDestinationsTreeNode().getChildCount()
                );
                
                MyTreeNode neighborsTreeNode = view.getNeighborsTreeNode();
                ArrayList<KnowledgeNode> nodeOnTree = new ArrayList<>();
                for (Enumeration<MyTreeNode> e = view.getNeighborsTreeNode().children(); 
                        e.hasMoreElements();) {
                    MyTreeNode neighborNode =  e.nextElement();
                    KnowledgeNode neighbor = (KnowledgeNode) neighborNode.getUserObject();
                    if (node.getCatagory().equals(neighbor.getCatagory()))
                        nodeOnTree.add(neighbor);
                }
                for (KnowledgeNode k : node.getNeighbors()) {
                    if (!nodeOnTree.contains(k))
                        view.getTreeModel().insertNodeInto(new MyTreeNode(k), neighborsTreeNode, neighborsTreeNode.getChildCount());
                }
                
                break;
            default:
                node.addNeighbor(added);
                view.getTreeModel().insertNodeInto(
                        leaf, view.getNeighborsTreeNode(),  
                        view.getNeighborsTreeNode().getChildCount()
                );
                break;
        }
        updateInfoPane();
    }
    
    public void knowledgeTreeRemoveActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeList parentList = (KnowledgeNodeList) ((MyTreeNode) n.getParent()).getUserObject();
            KnowledgeNode selectedNode = (KnowledgeNode) n.getUserObject();
            String type = parentList.getListType();
            if (type.equals(KnowledgeNodeList.TYPE.SOURCES.getValue())) {
                node.removeSource(selectedNode);
            } else if (type.equals(KnowledgeNodeList.TYPE.DESTINATIONS.getValue())) {
                node.removeDestination(selectedNode);
                
                for (Enumeration<MyTreeNode> e = view.getNeighborsTreeNode().children(); 
                        e.hasMoreElements();) {
                    MyTreeNode neighborNode =  e.nextElement();
                    KnowledgeNode neighbor = (KnowledgeNode) neighborNode.getUserObject();
                    if (!node.getNeighbors().contains(neighbor))
                        removeNodeFromTree(neighborNode);
                }
                
            } else {
                node.removeNeighbor(selectedNode);
            }
            removeNodeFromTree(n);
            updateInfoPane();
        } 
    }
    
    public void editKnowledgeNodeActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeEditorApp.run(map, (KnowledgeNode) n.getUserObject());
            
            updateInfoPane();
            refreshTree();
        }
    }
    
    private void removeNodeFromTree(MyTreeNode node) { view.getTreeModel().removeNodeFromParent(node); }
    
    private void updateInfoPane(KnowledgeNode k) { view.getKnowledgeNodeInfoPane().setText(k.chineseFormattedInformation()); }
    
    private void updateInfoPane() { updateInfoPane(node); }
    
    private void refreshTree() {
        DefaultTreeModel m = view.createTreeModel(node);
        view.getKnowledgeTree().setModel(m);
        m.reload((MyTreeNode) m.getRoot());
    }
    
    public void initialize() {
        view.getNameTextField().setText(node.getName());
        view.getCatagoryTextField().setText(node.getCatagory());
        view.getDefinitionTextArea().setText(node.getDefinition());
        view.getDescriptionTextArea().setText(node.getDescription());
        updateInfoPane();
    }
    
    public void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt) {
        if(evt.getClickCount() == 2) {
            MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode)
                KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
        }
    }
    
    public void knowledgeTreeRenameRootActionPerformed(){
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n.getUserObject() instanceof KnowledgeNodeList) {
            String newRootName = JOptionPane.showInputDialog("New Root Name");
            if ("".equals(newRootName.trim())) return;
            ((KnowledgeNodeList) n.getUserObject()).setName(newRootName);
            view.getTreeModel().nodeChanged(n);
            updateInfoPane();
        }
    }
    
    private void checkNameAvaliability(String name) {
        for (KnowledgeNode k : map.getAllKnowledgeNodes())
            if (k != node && name.equals(k.getName())) throw new InvalidInputException("Name is taken.");
    }
    
    public void saveActionPerformed() {
        try {
            String temp = view.getNameTextField().getText().trim();
            checkNameAvaliability(temp);
            node.setName(temp);
            temp = view.getCatagoryTextField().getText().trim();
            node.setCatagory(temp);
            temp = view.getDefinitionTextArea().getText().trim();
            node.setDefinition(temp);
            temp = view.getDescriptionTextArea().getText().trim();
            node.setDescription(temp);
            
            updateInfoPane(node);
        } catch (InvalidInputException e) {
            KnowledgeNodeEditorController.errorMessageBox(e.toString());
        }
    }
    
    public static void main(String args[]) throws CloneNotSupportedException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnowledgeNodeEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        KnowledgeNode a = new KnowledgeNode("A", "Character", "A", "First", "Source", "Destination", "Neighbor");
        KnowledgeNode b = new KnowledgeNode("B", "Character", "B", "Second", "Source", "Destination", "Neighbor");
        KnowledgeNode c = new KnowledgeNode("C", "Character", "C", "Third", "Source", "Destination", "Neighbor");
        KnowledgeNode d = new KnowledgeNode("D", "Character", "D", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode e = new KnowledgeNode("E", "Character", "E", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode f = new KnowledgeNode("F", "Character", "F", "Fourth", "Source", "Destination", "Neighbor");
        a.addDestination(d);
        e.addDestination(d);
        
        KnowledgeMap map = new KnowledgeMap("K");
        map.addCatagory("Character");
        map.addCatagory("String");
        map.addKnowledgeNodeTo("Character", a);
        map.addKnowledgeNodeTo("Character", b);
        map.addKnowledgeNodeTo(c.getCatagory(), c);
        map.addKnowledgeNodeTo("Character", d);
        map.addKnowledgeNodeTo("Character", e);
        
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, a);
        new KnowledgeNodeEditorView(controller).setVisible(true);
       
    }
}
