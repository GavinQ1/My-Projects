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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private ArrayList<KnowledgeNode> selectedKnowledgeNodesList,
            relatedKnowledgeNodesList, destinationKnowledgeNodesList;

    public KnowledgeMapEditorController(KnowledgeMap map) {
        this.map = map;
        this.view = null;
        this.selectedKnowledgeNodesList = new ArrayList<>();
        this.relatedKnowledgeNodesList = new ArrayList<>();
        this.destinationKnowledgeNodesList = new ArrayList<>();
    }

    public KnowledgeMap getMap() {
        return map;
    }

    public void initialize() {
        view.getNodesTree();
        this.updateResultCatagory();
    }

    public void register(KnowledgeMapEditorView view) {
        this.view = view;
    }

    public void nodesTreeMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.selectNodeButtonActionPerformed();
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
        treeRightClick(evt);
    }

    public void nodesTreeMouseReleased(MouseEvent evt) {
        treeRightClick(evt);
    }

    public void selectedNodesListMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
//            KnowledgeNodeWikiApp.run((KnowledgeNode) view.getSelectedNodesList().getSelectedValue());
            removeButtonActionPerformed();
        }
    }

    public void selectedNodesListMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            JList list = (JList) evt.getSource();
            int row = list.locationToIndex(evt.getPoint());
            list.setSelectedIndex(row);
        }
        selectedListRightClick(evt);
    }

    public void selectedNodesListMouseReleased(MouseEvent evt) {
        selectedListRightClick(evt);
    }
    
    public void relatedNodesListMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
//            KnowledgeNodeWikiApp.run((KnowledgeNode) view.getRelatedNodesList().getSelectedValue());
            addButtonActionPerformed();
        }
    }
    
    public void relatedNodesListMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            JList list = (JList) evt.getSource();
            int row = list.locationToIndex(evt.getPoint());
            list.setSelectedIndex(row);
        }
        relatedListRightClick(evt);
    }
    
    public void relatedNodesListMouseReleased(MouseEvent evt) {
        relatedListRightClick(evt);
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
            editAction((KnowledgeNode) n.getUserObject());
        }
    }

    public void createNodeButtonActionPerformed() {
        KnowledgeNode newNode = KnowledgeNodeEditorApp.create(map);
        if (newNode == null) {
            return;
        }

        map.addKnowledgeNodeTo(newNode.getCatagory(), newNode);
        refreshTree();
        clearLists();
    }

    public void deleteNodeButtonActionPerformed() {
        deleteNodeFromTreeAction();
    }

    public void selectNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNode k = (KnowledgeNode) n.getUserObject();
            addKnowledgeNodeToSelectedNodes(k);
        }
    }

    public void removeButtonActionPerformed() {
        Object selected = view.getSelectedNodesList().getSelectedValue();
        if (selected != null) {
            removeKnowledgeNodeFromSelectedNodes((KnowledgeNode) selected);
        }
    }

    public void addButtonActionPerformed() {
        Object selected = view.getRelatedNodesList().getSelectedValue();
        if (selected != null) {
            addKnowledgeNodeToSelectedNodes((KnowledgeNode) selected);
        }
    }

    public void resultCatagoryComboBoxActionPerformed() {
        String input = (String) view.getResultCatagoryComboBox().getSelectedItem();
        view.getResultCatagoryComboBox().setSelectedItem(input);
        if (input.equals(KnowledgeMapEditorControl.resultCatagoryComboBoxDefaultString)) {
            this.updateDestinationNodesList();
        } else {
            ArrayList<KnowledgeNode> a = new ArrayList<>();
            this.destinationKnowledgeNodesList.stream().filter((k) -> (input.equals(k.getCatagory()))).forEach((k) -> {
                a.add(k);
            });
            this.updateDestinationNodesList(a);
        }
    }
   
    private void deleteNodeFromTreeAction() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNode k = (KnowledgeNode) n.getUserObject();
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure that you want to delete Node " + k.getName()
                    + "? This action can't be restored."
            );
            if (dialogResult == JOptionPane.YES_OPTION) {
                map.deleteKnowledgeNodeFrom(k.getCatagory(), k);
                ((DefaultTreeModel) view.getNodesTree().getModel()).removeNodeFromParent(n);
            }
            clearLists();
        }
    }

    private void treeRightClick(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode) {
                (new NodeTreePopUp((KnowledgeNode) n.getUserObject()))
                        .show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private void selectedListRightClick(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            KnowledgeNode selected = (KnowledgeNode) view.getSelectedNodesList().getSelectedValue();
            SelectedListPopup popUp = new SelectedListPopup(selected);
            if (selected != null && selected instanceof KnowledgeNode) {
                popUp.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }
    
    private void relatedListRightClick(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            KnowledgeNode selected = (KnowledgeNode) view.getRelatedNodesList().getSelectedValue();
            RelatedListPopup popUp = new RelatedListPopup(selected);
            if (selected != null && selected instanceof KnowledgeNode) {
                popUp.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    private void refreshTree() {
        DefaultTreeModel m = view.createTreeModel(map);
        view.getNodesTree().setModel(m);
        m.reload((MyTreeNode) m.getRoot());
    }
    
    private void editAction(KnowledgeNode k) {
        KnowledgeNodeEditorApp.edit(map, k);
        refreshTree();
        clearLists();
    }

    private void addKnowledgeNodeToSelectedNodes(KnowledgeNode selected) {
        boolean successOp = this.selectedKnowledgeNodesList.add(selected);
        if (successOp) {
            updateAllLists();
        }
    }

    private void removeKnowledgeNodeFromSelectedNodes(KnowledgeNode selected) {
        boolean successOp = this.selectedKnowledgeNodesList.remove(selected);
        if (successOp) {
            if (selectedKnowledgeNodesList.isEmpty()) {
                clearLists();
                return;
            }
            updateAllLists();
        }
    }

    private void updateAllLists() {
        updateSelectedNodesList();
        updateNeighbors();
        updateDestinations();
        updateLabels();
    }

    private void updateLabels() {
        KnowledgeNode first = selectedKnowledgeNodesList.get(0);
        view.getSelectedNodesLabel().setText("Selected - " + first.getCatagory());
        view.getRelatedNodesLabel().setText("Related - " + first.getNeighbors().getName());
        view.getResultLabel().setText("Possible results - " + first.getDestinations().getName());
    }

    private void updateNeighbors() {
        HashMap<KnowledgeNode, Integer> hist = new HashMap<>();
        selectedKnowledgeNodesList.stream().forEach((k) -> {
            for (KnowledgeNode n : k.getNeighbors()) {
                hist.putIfAbsent(n, 0);
                hist.put(n, hist.get(n) + 1);
            }
        });
        relatedKnowledgeNodesList.clear();
        hist.keySet().stream().filter((k) -> (!selectedKnowledgeNodesList.contains(k))).forEach((k) -> {
            relatedKnowledgeNodesList.add(k);
        });
        Collections.sort(relatedKnowledgeNodesList, (KnowledgeNode k1, KnowledgeNode k2) -> hist.get(k1).compareTo(hist.get(k2)));
        updateRelatedNodesList();
    }

    // update destination list after select a search option
    private void updateDestinations() {
        if (!selectedKnowledgeNodesList.isEmpty()) {
            destinationKnowledgeNodesList = selectedKnowledgeNodesList.get(0).getDestinations().getList();
            for (int i = 1; i < selectedKnowledgeNodesList.size(); i++) {
                KnowledgeNode k = selectedKnowledgeNodesList.get(i);
                destinationKnowledgeNodesList.retainAll(k.getDestinations().getList());
            }

            Collections.sort(destinationKnowledgeNodesList, KnowledgeNodeList.comparatorBySignificance());

            updateDestinationNodesList();
        }
    }

    private void updateRelatedNodesList(ArrayList<KnowledgeNode> l) {
        view.getRelatedNodesList().setModel(listModelFactory(l));
    }

    private void updateSelectedNodesList(ArrayList<KnowledgeNode> l) {
        view.getSelectedNodesList().setModel(listModelFactory(l));
    }

    private void updateDestinationNodesList(ArrayList<KnowledgeNode> l) {
        view.getDestinationNodesList().setModel(listModelFactory(l));
        updateResultCatagory();
    }

    private void updateRelatedNodesList() {
        updateRelatedNodesList(relatedKnowledgeNodesList);
    }

    private void updateSelectedNodesList() {
        updateSelectedNodesList(selectedKnowledgeNodesList);
    }

    private void updateDestinationNodesList() {
        updateDestinationNodesList(destinationKnowledgeNodesList);
    }

    private void updateResultCatagory() {
        HashMap<String, Integer> hist = new HashMap<>();
        destinationKnowledgeNodesList.stream().forEach((k) -> {
            String s = k.getCatagory();
            hist.putIfAbsent(s, 0);
            hist.put(s, hist.get(s) + 1);
        });
        ArrayList<String> cataNames = new ArrayList<>(hist.keySet());
        view.getResultCatagoryComboBox().setModel(catagoryModelFactory(cataNames));
    }

    private DefaultComboBoxModel<String> catagoryModelFactory(ArrayList<String> cataNames) {
        Collections.sort(cataNames);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement(KnowledgeMapEditorControl.resultCatagoryComboBoxDefaultString);
        cataNames.stream().forEach((s) -> {
            model.addElement(s);
        });

        return model;
    }

    private void clearLists() {
        this.selectedKnowledgeNodesList.clear();
        this.relatedKnowledgeNodesList.clear();
        this.destinationKnowledgeNodesList.clear();

        updateRelatedNodesList();
        updateSelectedNodesList();
        updateDestinationNodesList();
    }

    private AbstractListModel<KnowledgeNode> listModelFactory(final ArrayList<KnowledgeNode> wantedList) {
        return new AbstractListModel<KnowledgeNode>() {
            ArrayList<KnowledgeNode> displayed = wantedList;

            public int getSize() {
                return displayed.size();
            }

            public KnowledgeNode getElementAt(int i) {
                return displayed.get(i);
            }
        };
    }

    private class RightClickPopUp extends JPopupMenu {

        KnowledgeNode selectedNode;
        JMenuItem viewB, edit;

        public RightClickPopUp(KnowledgeNode selectedNode) {
            this.selectedNode = selectedNode;

            this.viewB = new JMenuItem("View");

            this.viewB.addActionListener((ActionEvent evt) -> {
                KnowledgeNodeWikiApp.run(selectedNode);
            });
            
            this.edit = new JMenuItem("Edit");
            
            this.edit.addActionListener((ActionEvent evt) -> {
                editAction(selectedNode);
            });
        }
    }
    
    private class RelatedListPopup extends RightClickPopUp {

        JMenuItem add;

        public RelatedListPopup(KnowledgeNode selectedNode) {
            super(selectedNode);
            this.add = new JMenuItem("Add");
            add(add);

            add.addActionListener((ActionEvent evt) -> {
                addKnowledgeNodeToSelectedNodes(selectedNode);
            });
            
            add(new JSeparator());
            add(viewB);
//            add(edit);

            setVisible(true);
        }
    }

    private class SelectedListPopup extends RightClickPopUp {

        JMenuItem remove;

        public SelectedListPopup(KnowledgeNode selectedNode) {
            super(selectedNode);
            this.remove = new JMenuItem("Remove");
            add(remove);

            remove.addActionListener((ActionEvent evt) -> {
                removeKnowledgeNodeFromSelectedNodes(selectedNode);
            });
            
            add(new JSeparator());
            add(viewB);
//            add(edit);

            setVisible(true);
        }
    }

    private class NodeTreePopUp extends RightClickPopUp {

        MyTreeNode selectedTreeNode;
        JMenuItem select, delete;

        public NodeTreePopUp(KnowledgeNode selectedNode) {
            super(selectedNode);
            this.selectedTreeNode = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();

            this.select = new JMenuItem("Select");
            add(this.select);
            add(new JSeparator());

            add(this.viewB);
            add(this.edit);
            add(new JSeparator());

            this.delete = new JMenuItem("delete");
            add(this.delete);
            

            this.delete.addActionListener((ActionEvent evt) -> {
                deleteNodeFromTreeAction();
            });

            this.select.addActionListener((ActionEvent evt) -> {
                addKnowledgeNodeToSelectedNodes(selectedNode);
            });

            setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        KnowledgeMapEditorView.main(args);
    }
}
