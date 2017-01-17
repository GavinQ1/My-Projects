/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapEditor;

import KnowledgeMapEditor.KnowledgeMapEditorView.MyTreeNode;
import KnowledgeNodeEditor.*;
import Models.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Gavin
 */
public class KnowledgeMapEditorController extends GeneralController implements KnowledgeMapEditorControl {

    private KnowledgeMap map;
    private KnowledgeMapEditorView view;

    public KnowledgeMapEditorController(KnowledgeMap map) {
        this.map = map;
        this.view = null;
    }

    public KnowledgeMap getMap() {
        return map;
    }

    public void initialize() {
        view.getNodesTree();
    }

    public void register(KnowledgeMapEditorView view) {
        this.view = view;
    }

    public void nodesTreeMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode) {
                KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
            }
        }
    }

    public void nodesTreeMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            int selRow = view.getNodesTree().getRowForLocation(evt.getX(), evt.getY());
            TreePath selPath = view.getNodesTree().getPathForLocation(evt.getX(), evt.getY());
            view.getNodesTree().setSelectionPath(selPath);
            if (selRow > -1) {
                view.getNodesTree().setSelectionRow(selRow);
            }
        }
        righClick(evt);
    }

    public void nodesTreeMouseReleased(MouseEvent evt) {
        righClick(evt);
    }

    public void viewNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
        }
    }
    
    public void editNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeEditorApp.edit(map, (KnowledgeNode) n.getUserObject());
            refreshTree();
        }
    }
    
    public void createNodeButtonActionPerformed() {
        KnowledgeNode newNode = KnowledgeNodeEditorApp.create(map);
        if (newNode == null) 
            return;
        
        map.addKnowledgeNodeTo(newNode.getCatagory(), newNode);
        refreshTree();
    }
    
    public void deleteNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNode k = (KnowledgeNode) n.getUserObject();
            map.deleteKnowledgeNodeFrom(k.getCatagory(), k);
            ((DefaultTreeModel) view.getNodesTree().getModel()).removeNodeFromParent(n);
        }
    }

    private void righClick(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode) {
                (new NodeTreePopUp((KnowledgeNode) n.getUserObject()))
                        .show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }
    
    private void refreshTree() {
        DefaultTreeModel m = view.createTreeModel(map);
        view.getNodesTree().setModel(m);
        m.reload((MyTreeNode) m.getRoot());
    }

    private final class NodeTreePopUp extends JPopupMenu {

        KnowledgeNode selectedNode;
        MyTreeNode selectedTreeNode;
        JMenuItem select, viewB, edit, delete;

        public NodeTreePopUp(KnowledgeNode selectedNode) {
            this.selectedNode = selectedNode;
            this.selectedTreeNode = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
            this.select = new JMenuItem("Select");
            add(this.select);
            add(new JSeparator());

            this.viewB = new JMenuItem("View");
            this.edit = new JMenuItem("Edit");
            add(this.viewB);
            add(this.edit);
            add(new JSeparator());

            this.delete = new JMenuItem("delete");
            add(this.delete);

            this.addSelectMouseListener();
            
            this.viewB.addActionListener((ActionEvent evt) -> {
                KnowledgeNodeWikiApp.run(selectedNode);
            });
            
            this.edit.addActionListener((ActionEvent evt) -> {
                KnowledgeNodeEditorApp.edit(map, selectedNode);
                refreshTree();
            });
            
            this.delete.addActionListener((ActionEvent evt) -> {
                map.deleteKnowledgeNodeFrom(selectedNode.getCatagory(), selectedNode);
                ((DefaultTreeModel) view.getNodesTree().getModel()).removeNodeFromParent(selectedTreeNode);
            });
            
            setVisible(true);
        }

        public void addSelectMouseListener() {
            select.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {

                }
            });
        }

    }

}
