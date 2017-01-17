package KnowledgeNodeEditor;

import Models.*;
import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorController extends GeneralController implements KnowledgeNodeEditorControl {

    private boolean needSave = false, saved = false, savePressed = false;
    private String oldNodeCatagoryName = null;
    private KnowledgeMap map;
    private KnowledgeNode node;
    private KnowledgeNodeEditorView view;
    private HashMap<KnowledgeNode, Integer> sourcesCache, destinationsCache, neighborsCache;

    public KnowledgeNodeEditorController(KnowledgeMap map, KnowledgeNode node) {
        this.map = map;
        this.node = node;
        this.sourcesCache = new HashMap<>();
        this.destinationsCache = new HashMap<>();
        this.neighborsCache = new HashMap<>();
    }

    // function register controller to view
    public void register(KnowledgeNodeEditorView view) {
        this.view = view;
    }

    // function for getting node
    public KnowledgeNode getNode() {
        return this.node;
    }

    public boolean isSaved() {
        return saved || savePressed;
    }

    // function for initialize the view
    public void initialize() {
        view.getNameTextField().setText(node.getName());
        view.getCatagoryTextField().setText(node.getCatagory());
        view.getDefinitionTextArea().setText(node.getDefinition());
        view.getDescriptionTextArea().setText(node.getDescription());
        ArrayList<String> cataNames = new ArrayList<>(map.getMap().keySet());
        Collections.sort(cataNames);
        cataNames.stream().forEach((s) -> {
            view.getCatagoryComboBox().addItem(s);
        });
        updateInfoPane();
    }

    // function for add a node
    public void knowledgeTreeAddActionPerformed() {
        // call Linker
        KnowledgeNodeLinkerApp dialog = new KnowledgeNodeLinkerApp(map, node);
        dialog.run(true);
        KnowledgeNode added = dialog.getSelected();
        if (added == null) {
            return;
        }

        int signal = dialog.getExpandedGroup();
        MyTreeNode leaf = new MyTreeNode(added);

        // update changes
        switch (signal) {
            case 1:
                if (node.getSources().contains(added)) {
                    GeneralController.errorMessageBox("Node is already added");
                    return;
                }

                node.addSource(added);

                // record
                sourcesCache.putIfAbsent(added, 0);
                sourcesCache.put(added, sourcesCache.get(added) + 1);

                view.getTreeModel().insertNodeInto(
                        leaf, view.getSourcesTreeNode(),
                        view.getSourcesTreeNode().getChildCount()
                );
                break;
            case 2:
                if (node.getDestinations().contains(added)) {
                    GeneralController.errorMessageBox("Node is already added");
                    return;
                }

                node.addDestination(added);

                // record
                destinationsCache.putIfAbsent(added, 0);
                destinationsCache.put(added, destinationsCache.get(added) + 1);

                view.getTreeModel().insertNodeInto(
                        leaf, view.getDestinationsTreeNode(),
                        view.getDestinationsTreeNode().getChildCount()
                );

                MyTreeNode neighborsTreeNode = view.getNeighborsTreeNode();
                ArrayList<KnowledgeNode> nodeOnTree = new ArrayList<>();
                for (Enumeration<MyTreeNode> e = view.getNeighborsTreeNode().children();
                        e.hasMoreElements();) {
                    MyTreeNode neighborNode = e.nextElement();
                    KnowledgeNode neighbor = (KnowledgeNode) neighborNode.getUserObject();
                    /*
                    if (node.getCatagory().equals(neighbor.getCatagory())) {
                        nodeOnTree.add(neighbor);
                    }
                     */
                    nodeOnTree.add(neighbor);
                }
                for (KnowledgeNode k : node.getNeighbors()) {
                    if (!nodeOnTree.contains(k)) {
                        view.getTreeModel().insertNodeInto(new MyTreeNode(k), neighborsTreeNode, neighborsTreeNode.getChildCount());
                    }
                }

                break;
            default:
                node.addNeighbor(added);

                // record
                neighborsCache.putIfAbsent(added, 0);
                neighborsCache.put(added, neighborsCache.get(added) + 1);

                view.getTreeModel().insertNodeInto(
                        leaf, view.getNeighborsTreeNode(),
                        view.getNeighborsTreeNode().getChildCount()
                );
                break;
        }
        updateInfoPane();
        needSave = true;
    }

    // function for removing a node
    public void knowledgeTreeRemoveActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNodeList parentList = (KnowledgeNodeList) ((MyTreeNode) n.getParent()).getUserObject();
            KnowledgeNode selectedNode = (KnowledgeNode) n.getUserObject();
            String type = parentList.getListType();
            if (type.equals(KnowledgeNodeList.TYPE.SOURCES.getValue())) {
                node.removeSource(selectedNode);
                // record
                sourcesCache.putIfAbsent(selectedNode, 0);
                sourcesCache.put(selectedNode, sourcesCache.get(selectedNode) - 1);

            } else if (type.equals(KnowledgeNodeList.TYPE.DESTINATIONS.getValue())) {
                node.removeDestination(selectedNode);

                // record
                destinationsCache.putIfAbsent(selectedNode, 0);
                destinationsCache.put(selectedNode, destinationsCache.get(selectedNode) - 1);

                for (Enumeration<MyTreeNode> e = view.getNeighborsTreeNode().children();
                        e.hasMoreElements();) {
                    MyTreeNode neighborNode = e.nextElement();
                    KnowledgeNode neighbor = (KnowledgeNode) neighborNode.getUserObject();
                    if (!node.getNeighbors().contains(neighbor)) {
                        removeNodeFromTree(neighborNode);
                    }
                }

            } else {
                node.removeNeighbor(selectedNode);

                // record
                neighborsCache.putIfAbsent(selectedNode, 0);
                neighborsCache.put(selectedNode, neighborsCache.get(selectedNode) - 1);
            }
            removeNodeFromTree(n);
            updateInfoPane();
            needSave = true;
        }
    }

    // function for edit a node
    public void editKnowledgeNodeActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNode) {
            KnowledgeNode target = (KnowledgeNode) n.getUserObject();
            target = KnowledgeNodeEditorApp.edit(map, target);

            updateInfoPane();
            refreshTree();
            needSave = true;
        }
    }

    // function for detaild node info
    public void knowledgeTreeNodeMouseDoubleClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode) {
                KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
            }
        }
    }

    // function for rename a root
    public void knowledgeTreeRenameRootActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n != null && n.getUserObject() instanceof KnowledgeNodeList) {
            String newRootName = JOptionPane.showInputDialog("New Root Name");
            if ("".equals(newRootName.trim())) {
                return;
            }
            ((KnowledgeNodeList) n.getUserObject()).setName(newRootName);
            view.getTreeModel().nodeChanged(n);
            updateInfoPane();
            needSave = true;
        }
    }

    // function for Catagory name hints
    public void catagoryComboBoxActionPerformed() {
        String input = (String) view.getCatagoryComboBox().getSelectedItem();
        if (input.equals(KnowledgeNodeEditorView.CATA_COMBO_BOX_DEFAULT_OPTION)) {
            input = view.getCatagoryTextField().getText().trim();
        }
        view.getCatagoryTextField().setText(input);
    }

    // function for "New" button, create a new node
    public void newNodeActionPerformed() {
        KnowledgeNode newNode = KnowledgeNodeEditorApp.create(map);
        if (newNode == null) {
            return;
        }
        map.addKnowledgeNodeTo(newNode.getCatagory(), newNode);
        updateInfoPane();
        refreshTree();
    }

    // function for "Save" button
    public void saveActionPerformed() {
        try {
            saveAction();
            savePressed = true;
        } catch (InvalidInputException e) {
            KnowledgeNodeEditorController.errorMessageBox(e.toString());
        }
    }

    // function for the "EXIT" button
    public void exitActionPerformed() {
        try {
            if (nothingFieldChanged() && !needSave) {
                saved = false;
                view.dispose();
            } else {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Would You Like to Save your changes before you leave?"
                );
                if (dialogResult == JOptionPane.YES_OPTION) {
                    saveAction();
                    saved = true;
                    view.dispose();
                }
                if (dialogResult == JOptionPane.NO_OPTION) {
                    restoreNodeChanges();
                    saved = false;
                    view.dispose();
                }
            }
        } catch (InvalidInputException e) {
            KnowledgeNodeEditorController.errorMessageBox(e.toString());
        }
    }

    // check if name is usable
    private void checkNameAvaliability(String name) {
        for (KnowledgeNode k : map.getAllKnowledgeNodes()) {
            if (k != node && name.equals(k.getName())) {
                throw new InvalidInputException("Name is taken.");
            }
        }
    }

    // clear cache
    private void clearCache() {
        sourcesCache = new HashMap<>();
        destinationsCache = new HashMap<>();
        neighborsCache = new HashMap<>();
    }

    // save text field info into a node
    private void saveInTo(KnowledgeNode node) throws InvalidInputException {
        String temp = view.getNameTextField().getText().trim();
        checkNameAvaliability(temp);
        node.setName(temp);
        temp = view.getDefinitionTextArea().getText().trim();
        node.setDefinition(temp);
        temp = view.getDescriptionTextArea().getText().trim();
        node.setDescription(temp);
        temp = view.getCatagoryTextField().getText().trim();
        oldNodeCatagoryName = node.getCatagory();
        node.setCatagory(temp);
        map.moveKnowledgeNodeFrom(oldNodeCatagoryName, node.getCatagory(), node);
    }

    // save action function
    private void saveAction() throws InvalidInputException {
        saveInTo(node);
        updateInfoPane();
        clearCache();
        needSave = false;
    }

    // return true if no textfield changes
    private boolean nothingFieldChanged() {
        int sum = 0;
        sum += sourcesCache.values().stream().map((i) -> i).reduce(sum, Integer::sum);
        sum += destinationsCache.values().stream().map((i) -> i).reduce(sum, Integer::sum);
        sum += neighborsCache.values().stream().map((i) -> i).reduce(sum, Integer::sum);

        return sum == 0
                && node.getName().equals(view.getNameTextField().getText().trim())
                && node.getCatagory().equals(view.getCatagoryTextField().getText().trim())
                && node.getDefinition().equals(view.getDefinitionTextArea().getText().trim())
                && node.getDescription().equals(view.getDescriptionTextArea().getText().trim());
    }

    // remove node from tree
    private void removeNodeFromTree(MyTreeNode node) {
        view.getTreeModel().removeNodeFromParent(node);
    }

    // update info pane
    private void updateInfoPane(KnowledgeNode k) {
        view.getKnowledgeNodeInfoPane().setText(k.chineseFormattedInformation());
    }

    // update info pane
    private void updateInfoPane() {
        updateInfoPane(node);
    }

    // refresh tree
    private void refreshTree() {
        DefaultTreeModel m = view.createTreeModel(node);
        view.getKnowledgeTree().setModel(m);
        m.reload((MyTreeNode) m.getRoot());
    }

    // restore tree changes and node changes
    private void restoreNodeChanges() {
        restore(sourcesCache, 1);
        restore(destinationsCache, 2);
        restore(neighborsCache, 3);
    }

    // 1 - sources, 2 - destinations, 3 - neighbors
    private void restore(HashMap<KnowledgeNode, Integer> cache, int signal) {
        for (KnowledgeNode k : cache.keySet()) {
            int i = cache.get(k);
            while (i != 0) {
                if (i > 0) {
                    switch (signal) {
                        case 1:
                            node.removeSource(k);
                            break;
                        case 2:
                            node.removeDestination(k);
                            break;
                        default:
                            node.removeNeighbor(k);
                            break;
                    }
                    i--;
                } else {
                    switch (signal) {
                        case 1:
                            node.addSource(k);
                            break;
                        case 2:
                            node.addDestination(k);
                            break;
                        default:
                            node.addNeighbor(k);
                            break;
                    }
                    i++;
                }
            }
        }
    }

    // unit test
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

        KnowledgeNode a = new KnowledgeNodeImpl("症状1", "症状", "A", "First", "Source", "相关的结果", "相关的症状");
        KnowledgeNode b = new KnowledgeNodeImpl("体质1", "体质", "B", "Second", "Source", "相关的结果", "相关的体质");
        KnowledgeNode c = new KnowledgeNodeImpl("药方1", "药方", "C", "Third", "Source", "相关的结果", "相关的药方");
        KnowledgeNode d = new KnowledgeNodeImpl("药方2", "药方", "D", "Fourth", "Source", "相关的结果", "相关的药方");
        KnowledgeNode e = new KnowledgeNodeImpl("体质2", "体质", "E", "Fourth", "Source", "相关的结果", "相关的体质");
        KnowledgeNode f = new KnowledgeNodeImpl("症状2", "症状", "F", "Fourth", "Source", "相关的结果", "相关的症状");

        a.addDestination(b);
        b.addDestination(c);
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

        System.out.println(a.getDestinations());
        KnowledgeNodeEditorController controller = new KnowledgeNodeEditorController(map, a);
        KnowledgeNodeEditorView view = new KnowledgeNodeEditorView(controller);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        System.out.println(a.getDestinations());
    }

}
