package KnowledgeNodeEditor;

import Models.*;
import KnowledgeNodeEditor.KnowledgeNodeEditorView.MyTreeNode;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
    private HashMap<KnowledgeNode, Integer> sourcesCache, destinationsCache, neighborsCache;

    public KnowledgeNodeEditorController(KnowledgeMap map, KnowledgeNode node) throws CloneNotSupportedException {
        this.map = map;
        this.node = node;
        this.sourcesCache = new HashMap<>();
        this.destinationsCache = new HashMap<>();
        this.neighborsCache = new HashMap<>();
    }

    public void register(KnowledgeNodeEditorView view) {
        this.view = view;
    }

    public KnowledgeNode getNode() {
        return this.node;
    }

    public void knowledgeTreeValueChangedAction(MyTreeNode selected) {
        view.setSelectedTreeNode((MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent());
    }

    public void knowledgeTreeAddActionPerformed() {
        KnowledgeNodeLinkerApp dialog = new KnowledgeNodeLinkerApp(map, node);
        dialog.run();
        KnowledgeNode added = dialog.getSelected();
        if (added == null) {
            return;
        }
        int signal = dialog.getExpandedGroup();
        MyTreeNode leaf = new MyTreeNode(added);

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
                    if (node.getCatagory().equals(neighbor.getCatagory())) {
                        nodeOnTree.add(neighbor);
                    }
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
    }

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

    private void removeNodeFromTree(MyTreeNode node) {
        view.getTreeModel().removeNodeFromParent(node);
    }

    private void updateInfoPane(KnowledgeNode k) {
        view.getKnowledgeNodeInfoPane().setText(k.chineseFormattedInformation());
    }

    private void updateInfoPane() {
        updateInfoPane(node);
    }

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
        if (evt.getClickCount() == 2) {
            MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
            if (n != null && n.getUserObject() instanceof KnowledgeNode) {
                KnowledgeNodeWikiApp.run((KnowledgeNode) n.getUserObject());
            }
        }
    }

    public void knowledgeTreeRenameRootActionPerformed() {
        MyTreeNode n = (MyTreeNode) view.getKnowledgeTree().getLastSelectedPathComponent();
        if (n.getUserObject() instanceof KnowledgeNodeList) {
            String newRootName = JOptionPane.showInputDialog("New Root Name");
            if ("".equals(newRootName.trim())) {
                return;
            }
            ((KnowledgeNodeList) n.getUserObject()).setName(newRootName);
            view.getTreeModel().nodeChanged(n);
            updateInfoPane();
        }
    }

    private void checkNameAvaliability(String name) {
        for (KnowledgeNode k : map.getAllKnowledgeNodes()) {
            if (k != node && name.equals(k.getName())) {
                throw new InvalidInputException("Name is taken.");
            }
        }
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
            sourcesCache = new HashMap<>();
            destinationsCache = new HashMap<>();
            neighborsCache = new HashMap<>();
        } catch (InvalidInputException e) {
            KnowledgeNodeEditorController.errorMessageBox(e.toString());
        }
    }

    public void cancelActionPerformed() {
        int dialogResult = JOptionPane.showConfirmDialog (null, 
                "Would You Like to Save your changes before you leave?"
        );
        if (dialogResult == JOptionPane.YES_OPTION)
            view.dispose();
        if (dialogResult == JOptionPane.NO_OPTION)
            restoreNodeChanges();
    }

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

        KnowledgeNode a = new KnowledgeNode("症状1", "症状", "A", "First", "Source", "相关的结果", "相关的症状");
        KnowledgeNode b = new KnowledgeNode("体质1", "体质", "B", "Second", "Source", "相关的结果", "相关的体质");
        KnowledgeNode c = new KnowledgeNode("药方1", "药方", "C", "Third", "Source", "相关的结果", "相关的药方");
        KnowledgeNode d = new KnowledgeNode("药方2", "药方", "D", "Fourth", "Source", "相关的结果", "相关的药方");
        KnowledgeNode e = new KnowledgeNode("体质2", "体质", "E", "Fourth", "Source", "相关的结果", "相关的体质");
        KnowledgeNode f = new KnowledgeNode("症状2", "症状", "F", "Fourth", "Source", "相关的结果", "相关的症状");

        a.addDestination(b);
        b.addDestination(c);
        a.addDestination(c);
        a.addDestination(e);
        f.addDestination(e);

        KnowledgeMap map = new KnowledgeMap("K");
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
        new KnowledgeNodeEditorView(controller).setVisible(true);
        System.out.println(a.getDestinations());
    }
}
