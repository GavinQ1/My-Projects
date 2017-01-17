package KnowledgeMapEditor;

import KnowledgeNodeEditor.*;
import Models.*;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Gavin
 */
public class KnowledgeMapEditorView extends javax.swing.JFrame {
    
    static class MyTreeNode extends DefaultMutableTreeNode {

        public MyTreeNode(Object k) {
            super(k);
        }

        public String toString() {
            Object o = getUserObject();
            if (o instanceof KnowledgeNode) {
                return ((KnowledgeNode) o).getName();
            }
            if (o instanceof KnowledgeNodeList) {
                return ((KnowledgeNodeList) o).getName();
            }
            if (o instanceof KnowledgeMap) {
                return ((KnowledgeMap) o).getName();
            }
            return o.toString();
        }
    }
    
    private KnowledgeMap edittingMap;
    private KnowledgeMapEditorControl controller;
    
    /**
     * Creates new form KnowledgeMapEditorView
     * @param controller
     */
    
    public KnowledgeMapEditorView(KnowledgeMapEditorControl controller) {
        this.controller = controller;
        this.edittingMap = controller.getMap();
        initComponents();
        setTitle("Knowledge Map");
        
        getRegistered();
        controller.initialize();
        setVisible(true);
    }
    
    private void getRegistered() {
        controller.register(this);
    }
    
    DefaultTreeModel createTreeModel(KnowledgeMap map) {
        MyTreeNode root = new MyTreeNode(map);
        String[] cataNames = map.getAllCatagoryNames();
        for (String cataName : cataNames) {
            MyTreeNode cata = new MyTreeNode(cataName);
            map.getCatagory(cataName).stream().forEach((k) -> {
                cata.add(new MyTreeNode(k));
            });
            root.add(cata);
        }
        return new DefaultTreeModel(root);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        nodeTreeSplitPane = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        nodesTree = new javax.swing.JTree();
        nodeToolBar = new javax.swing.JToolBar();
        selectButton = new javax.swing.JButton();
        viewNodeButton = new javax.swing.JButton();
        editNodeButton = new javax.swing.JButton();
        createNodeButton = new javax.swing.JButton();
        deleteNodeButton = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        selectedNodesLabel = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        selectedNodesList = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        relatedNodesList = new javax.swing.JList<>();
        relatedNodesLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        forwardButton = new javax.swing.JButton();
        backwardButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        viewPathButton = new javax.swing.JButton();
        exportPathButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileNewMapItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        fileOpenMapItem = new javax.swing.JMenuItem();
        fileCloseMapItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        fileSaveItem = new javax.swing.JMenuItem();
        fileSaveAsItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        fileExitItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        nodesTree.setModel(createTreeModel(edittingMap)
        );
        nodesTree.setPreferredSize(new java.awt.Dimension(130, 84));
        nodesTree.setModel(createTreeModel(edittingMap));
        nodesTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nodesTreeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nodesTreeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                nodesTreeMouseReleased(evt);
            }
        });
        nodesTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                nodesTreeValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(nodesTree);

        nodeTreeSplitPane.setRightComponent(jScrollPane2);

        nodeToolBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
        nodeToolBar.setRollover(true);
        nodeToolBar.setName(""); // NOI18N
        nodeToolBar.setPreferredSize(new java.awt.Dimension(70, 131));

        selectButton.setText("Select");
        selectButton.setFocusable(false);
        selectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(selectButton);

        viewNodeButton.setText("View");
        viewNodeButton.setFocusable(false);
        viewNodeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewNodeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewNodeButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(viewNodeButton);

        editNodeButton.setText("Edit");
        editNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNodeButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(editNodeButton);

        createNodeButton.setText("Create");
        createNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNodeButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(createNodeButton);

        deleteNodeButton.setText("Delete");
        deleteNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteNodeButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(deleteNodeButton);

        nodeTreeSplitPane.setLeftComponent(nodeToolBar);

        jSplitPane1.setLeftComponent(nodeTreeSplitPane);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jScrollPane3, java.awt.BorderLayout.PAGE_START);

        selectedNodesLabel.setText("Selected Nodes");
        jPanel1.add(selectedNodesLabel, java.awt.BorderLayout.CENTER);

        selectedNodesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(selectedNodesList);

        jPanel1.add(jScrollPane7, java.awt.BorderLayout.PAGE_END);

        jSplitPane3.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        relatedNodesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(relatedNodesList);

        jPanel3.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        relatedNodesLabel.setText("Related Nodes");
        jPanel3.add(relatedNodesLabel, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, "card2");

        jSplitPane3.setRightComponent(jPanel2);

        jSplitPane2.setLeftComponent(jSplitPane3);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        forwardButton.setText("Forward");

        backwardButton.setText("Backward");

        jLabel1.setText("Corresponding Results");

        viewPathButton.setText("View Path");

        exportPathButton.setText("Export Path");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewPathButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportPathButton, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(backwardButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(forwardButton))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(viewPathButton)
                        .addGap(26, 26, 26)
                        .addComponent(exportPathButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forwardButton)
                    .addComponent(backwardButton))
                .addGap(48, 48, 48))
        );

        jSplitPane2.setRightComponent(jPanel4);

        jSplitPane1.setRightComponent(jSplitPane2);

        getContentPane().add(jSplitPane1, "card2");

        fileMenu.setText("File");

        fileNewMapItem.setText("New Map");
        fileNewMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNewMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileNewMapItem);
        fileMenu.add(jSeparator1);

        fileOpenMapItem.setText("Open Map");
        fileOpenMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileOpenMapItem);

        fileCloseMapItem.setText("Close Map");
        fileCloseMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileCloseMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileCloseMapItem);
        fileMenu.add(jSeparator2);

        fileSaveItem.setText("Save");
        fileSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSaveItem);

        fileSaveAsItem.setText("Save As...");
        fileSaveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveAsItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSaveAsItem);
        fileMenu.add(jSeparator3);

        fileExitItem.setText("Exit");
        fileExitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExitItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileExitItem);

        jMenuBar1.add(fileMenu);

        jMenu1.setText("About");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nodesTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMouseClicked
        controller.nodesTreeMouseClicked(evt);
    }//GEN-LAST:event_nodesTreeMouseClicked

    private void nodesTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_nodesTreeValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_nodesTreeValueChanged

    private void viewNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNodeButtonActionPerformed
        controller.viewNodeButtonActionPerformed();
    }//GEN-LAST:event_viewNodeButtonActionPerformed

    private void editNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNodeButtonActionPerformed
        controller.editNodeButtonActionPerformed();
    }//GEN-LAST:event_editNodeButtonActionPerformed

    private void createNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNodeButtonActionPerformed
        controller.createNodeButtonActionPerformed();
    }//GEN-LAST:event_createNodeButtonActionPerformed

    private void deleteNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteNodeButtonActionPerformed
        controller.deleteNodeButtonActionPerformed();
    }//GEN-LAST:event_deleteNodeButtonActionPerformed

    private void fileNewMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNewMapItemActionPerformed
        
    }//GEN-LAST:event_fileNewMapItemActionPerformed

    private void fileOpenMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenMapItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileOpenMapItemActionPerformed

    private void fileCloseMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileCloseMapItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileCloseMapItemActionPerformed

    private void fileSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveItemActionPerformed

    private void fileSaveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveAsItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveAsItemActionPerformed

    private void fileExitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExitItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileExitItemActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectButtonActionPerformed

    private void nodesTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMousePressed
        controller.nodesTreeMousePressed(evt);
    }//GEN-LAST:event_nodesTreeMousePressed

    private void nodesTreeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMouseReleased
        controller.nodesTreeMouseReleased(evt);
    }//GEN-LAST:event_nodesTreeMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KnowledgeMapEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KnowledgeMapEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KnowledgeMapEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnowledgeMapEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        KnowledgeNode a = new KnowledgeNodeImpl("症状1", "症状", "A", "First", "Source", "相关的结果", "相关的症状");
        KnowledgeNode b = new KnowledgeNodeImpl("体质1", "体质", "B", "Second", "Source", "相关的结果", "相关的体质");
        KnowledgeNode c = new KnowledgeNodeImpl("药方1", "药方", "C", "Third", "Source", "相关的结果", "相关的药方");
        KnowledgeNode d = new KnowledgeNodeImpl("药方2", "药方", "D", "Fourth", "Source", "相关的结果", "相关的药方");
        KnowledgeNode e = new KnowledgeNodeImpl("体质2", "体质", "E", "Fourth", "Source", "相关的结果", "相关的体质");
        KnowledgeNode f = new KnowledgeNodeImpl("症状2", "症状", "F", "Fourth", "Source", "相关的结果", "相关的症状");

        a.addDestination(b);
        b.addDestination(c);
        a.addDestination(c);
        f.addDestination(e);

        KnowledgeMap map = new KnowledgeMapImpl("Test");
        map.addCatagory("症状");
        map.addCatagory("体质");
        map.addCatagory("药方");
        map.addKnowledgeNodeTo(a.getCatagory(), a);
        map.addKnowledgeNodeTo(b.getCatagory(), b);
        map.addKnowledgeNodeTo(c.getCatagory(), c);
        map.addKnowledgeNodeTo(d.getCatagory(), d);
        map.addKnowledgeNodeTo(e.getCatagory(), e);
        map.addKnowledgeNodeTo(f.getCatagory(), f);
        
        KnowledgeMapEditorControl controller = new KnowledgeMapEditorController(map);
        KnowledgeMapEditorView view = new KnowledgeMapEditorView(controller);
    }
    
    public JTree getNodesTree() {
        return nodesTree;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backwardButton;
    private javax.swing.JButton createNodeButton;
    private javax.swing.JButton deleteNodeButton;
    private javax.swing.JButton editNodeButton;
    private javax.swing.JButton exportPathButton;
    private javax.swing.JMenuItem fileCloseMapItem;
    private javax.swing.JMenuItem fileExitItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileNewMapItem;
    private javax.swing.JMenuItem fileOpenMapItem;
    private javax.swing.JMenuItem fileSaveAsItem;
    private javax.swing.JMenuItem fileSaveItem;
    private javax.swing.JButton forwardButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar nodeToolBar;
    private javax.swing.JSplitPane nodeTreeSplitPane;
    private javax.swing.JTree nodesTree;
    private javax.swing.JLabel relatedNodesLabel;
    private javax.swing.JList<String> relatedNodesList;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel selectedNodesLabel;
    private javax.swing.JList<String> selectedNodesList;
    private javax.swing.JButton viewNodeButton;
    private javax.swing.JButton viewPathButton;
    // End of variables declaration//GEN-END:variables
}