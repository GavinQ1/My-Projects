/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KnowledgeMapEditor;

import Lib.GeneralController;
import Lib.Stack;
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

    // the map that is being editted
    private KnowledgeMap map;
    // registered view
    private KnowledgeMapEditorView view;
    // lists for the listBoxes in view
    private ArrayList<KnowledgeNode> selectedKnowledgeNodesList,
            relatedKnowledgeNodesList, destinationKnowledgeNodesList;
    // path history
    private Stack<ArrayList<KnowledgeNode>> path;

    /**
     *
     * @param map
     */
    public KnowledgeMapEditorController(KnowledgeMap map) {
        this.map = map;
        this.view = null;
        this.selectedKnowledgeNodesList = new ArrayList<>();
        this.relatedKnowledgeNodesList = new ArrayList<>();
        this.destinationKnowledgeNodesList = new ArrayList<>();
        this.path = new Stack<>();
    }

    /**
     *
     * @return
     */
    public KnowledgeMap getMap() {
        return map;
    }

    /**
     *
     */
    public void initialize() {
        view.getNodesTree();
        this.updateResultCatagory();
    }

    /**
     *
     * @param view
     */
    public void register(KnowledgeMapEditorView view) {
        this.view = view;
    }

    /**
     *
     * @param evt
     */
    public void nodesTreeMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            this.selectNodeButtonActionPerformed();
        }
    }

    /**
     *
     * @param evt
     */
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

    /**
     *
     * @param evt
     */
    public void nodesTreeMouseReleased(MouseEvent evt) {
        treeRightClick(evt);
    }

    /**
     *
     * @param evt
     */
    public void selectedNodesListMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
//            KnowledgeNodeViewerApp.run((KnowledgeNode) view.getSelectedNodesList().getSelectedValue());
            removeButtonActionPerformed();
        }
    }

    /**
     *
     * @param evt
     */
    public void selectedNodesListMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            JList list = (JList) evt.getSource();
            int row = list.locationToIndex(evt.getPoint());
            list.setSelectedIndex(row);
        }
        selectedListRightClick(evt);
    }

    /**
     *
     * @param evt
     */
    public void selectedNodesListMouseReleased(MouseEvent evt) {
        selectedListRightClick(evt);
    }

    /**
     *
     * @param evt
     */
    public void relatedNodesListMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
//            KnowledgeNodeViewerApp.run((KnowledgeNode) view.getRelatedNodesList().getSelectedValue());
            addButtonActionPerformed();
        }
    }

    /**
     *
     * @param evt
     */
    public void relatedNodesListMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            JList list = (JList) evt.getSource();
            int row = list.locationToIndex(evt.getPoint());
            list.setSelectedIndex(row);
        }
        relatedListRightClick(evt);
    }

    /**
     *
     * @param evt
     */
    public void relatedNodesListMouseReleased(MouseEvent evt) {
        relatedListRightClick(evt);
    }
    
    public void destinationNodesListMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            forwardButtonActionPerformed();
        }
    }
    
    public void destinationNodesListMousePressed(MouseEvent evt) {
        if (SwingUtilities.isRightMouseButton(evt)) {
            JList list = (JList) evt.getSource();
            int row = list.locationToIndex(evt.getPoint());
            list.setSelectedIndex(row);
        }
        destinationListRightClick(evt);
    }
    
    public void destinationNodesListMouseReleased(MouseEvent evt) {
        destinationListRightClick(evt);
    }

    /**
     *
     */
    public void viewNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeViewerApp.run((KnowledgeNode) n.getUserObject());
        }
    }

    /**
     *
     */
    public void editNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            editAction((KnowledgeNode) n.getUserObject());
        }
    }

    /**
     *
     */
    public void createNodeButtonActionPerformed() {
        KnowledgeNode newNode = KnowledgeNodeEditorApp.create(map);
        if (newNode == null) {
            return;
        }

        map.addKnowledgeNodeTo(newNode.getCatagory(), newNode);
        refreshTree();
        clearLists();
    }

    /**
     *
     */
    public void deleteNodeButtonActionPerformed() {
        deleteNodeFromTreeAction();
    }

    /**
     *
     */
    public void selectNodeButtonActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getNodesTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNode k = (KnowledgeNode) n.getUserObject();
            addKnowledgeNodeToSelectedNodes(k);
        }
    }

    /**
     *
     */
    public void removeButtonActionPerformed() {
        Object selected = view.getSelectedNodesList().getSelectedValue();
        if (selected != null) {
            removeKnowledgeNodeFromSelectedNodes((KnowledgeNode) selected);
        }
    }

    /**
     *
     */
    public void addButtonActionPerformed() {
        Object selected = view.getRelatedNodesList().getSelectedValue();
        if (selected != null) {
            addKnowledgeNodeToSelectedNodes((KnowledgeNode) selected);
        }
    }

    /**
     *
     */
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

    public void forwardButtonActionPerformed() {
        forwardAction((KnowledgeNode) 
                view.getDestinationNodesList().getSelectedValue()) ;
    }
    
    public void viewPathButtonActionPerformed() {
        if (path.isEmpty() && selectedKnowledgeNodesList.isEmpty() &&
                destinationKnowledgeNodesList.isEmpty()) {
            KnowledgeMapEditorController.errorMessageBox("No path record found!");
            return;
        }
        ShowPathApp.run(path, selectedKnowledgeNodesList, destinationKnowledgeNodesList);
    }

    private void forwardAction(KnowledgeNode selected) {
        if (selected == null) {
            return;
        }
        path.push((ArrayList<KnowledgeNode>) selectedKnowledgeNodesList.clone());
        clearLists();
        selectedKnowledgeNodesList.add(selected);
        updateAllLists();
    }

    public void backwardButtonActionPerformed() {
        backwardAction();
    }
    
    private void backwardAction() {
        if (path.isEmpty()) {
            KnowledgeMapEditorController.errorMessageBox("No more history records!");
            return;
        }
        clearLists();
        selectedKnowledgeNodesList = path.pop();
        updateAllLists();
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
    
    private void destinationListRightClick(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            KnowledgeNode selected = (KnowledgeNode) view.getDestinationNodesList().getSelectedValue();
            DestinationListPopup popUp = new DestinationListPopup(selected);
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
        if (KnowledgeNodeEditorApp.edit(map, k) != null) {
            refreshTree();
            clearLists();
        }
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

    private void clearLists() {
        this.selectedKnowledgeNodesList.clear();
        this.relatedKnowledgeNodesList.clear();
        this.destinationKnowledgeNodesList.clear();

        updateRelatedNodesList();
        updateSelectedNodesList();
        updateDestinationNodesList();
    }

    private void updateLabels() {
        KnowledgeNode first = selectedKnowledgeNodesList.get(0);
        view.getSelectedNodesLabel().setText("Selected - " + first.getCatagory());
        view.getRelatedNodesLabel().setText("Related - " + first.getNeighbors().getName());
        view.getResultLabel().setText("Possible results - " + first.getDestinations().getName());
    }

    private void updateNeighbors() {
        if (selectedKnowledgeNodesList.isEmpty()) {
            return;
        }
        relatedKnowledgeNodesList.clear();
        KnowledgeNode primary = selectedKnowledgeNodesList.get(0);
        primary.getNeighbors().getList().stream().filter((k) -> (!selectedKnowledgeNodesList.contains(k))).forEach((k) -> {
            relatedKnowledgeNodesList.add(k);
        });
        Collections.sort(relatedKnowledgeNodesList, KnowledgeNodeList.comparatorByRelativity(primary.getNeighbors().getHist()));
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

    // update destination list after select a search option
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
                KnowledgeNodeViewerApp.run(selectedNode);
            });

            this.edit = new JMenuItem("Edit");

            this.edit.addActionListener((ActionEvent evt) -> {
                editAction(selectedNode);
            });
        }
    }
    
    private class DestinationListPopup extends RightClickPopUp {
        JMenuItem forward, backward;
        
        public DestinationListPopup(KnowledgeNode selectedNode) {
            super(selectedNode);
            add(viewB);
            add(new JSeparator());
            
            forward = new JMenuItem("Forward");
            add(forward);
            backward = new JMenuItem("Backward");
            add(backward);
            
            forward.addActionListener((ActionEvent evt) -> {
               forwardAction(selectedNode); 
            });
            
            backward.addActionListener((ActionEvent evt) -> {
                backwardAction();
            });
            
            setVisible(true);
        } 
    }

    private class RelatedListPopup extends RightClickPopUp {

        JMenuItem addN;

        public RelatedListPopup(KnowledgeNode selectedNode) {
            super(selectedNode);
            this.addN = new JMenuItem("Add");
            add(this.addN);

            this.addN.addActionListener((ActionEvent evt) -> {
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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        KnowledgeMapEditorView.main(args);
    }
}
