package KnowledgeMapEditor;

import KnowledgeNodeModels.KnowledgeNodeList;
import KnowledgeNodeModels.KnowledgeMapImpl;
import KnowledgeNodeModels.KnowledgeMap;
import KnowledgeNodeModels.KnowledgeNodeImpl;
import KnowledgeNodeModels.KnowledgeNode;
import javax.swing.*;
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
        this.exportPathButton.setVisible(false);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        nodeTreeSplitPane = new javax.swing.JSplitPane();
        nodeToolBar = new javax.swing.JToolBar();
        selectButton = new javax.swing.JButton();
        viewNodeButton = new javax.swing.JButton();
        editNodeButton = new javax.swing.JButton();
        createNodeButton = new javax.swing.JButton();
        deleteNodeButton = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        nodesTree = new javax.swing.JTree();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        resultCatagoryComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        destinationNodesList = new javax.swing.JList<>();
        forwardButton = new javax.swing.JButton();
        backwardButton = new javax.swing.JButton();
        resultLabel = new javax.swing.JLabel();
        viewPathButton = new javax.swing.JButton();
        exportPathButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        relatedNodesLabel = new javax.swing.JLabel();
        selectedNodesLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        selectedNodesList = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        relatedNodesList = new javax.swing.JList<>();
        jSeparator5 = new javax.swing.JSeparator();
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
        nodeToolBar.add(jSeparator7);

        addButton.setText("Add");
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(addButton);

        removeButton.setText("Remove");
        removeButton.setFocusable(false);
        removeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        nodeToolBar.add(removeButton);

        nodeTreeSplitPane.setLeftComponent(nodeToolBar);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        nodesTree.setModel(createTreeModel(edittingMap)
        );
        nodesTree.setMaximumSize(new java.awt.Dimension(130, 84));
        nodesTree.setMinimumSize(new java.awt.Dimension(130, 84));
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
        jScrollPane5.setViewportView(nodesTree);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 400;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        jPanel1.add(jScrollPane5, gridBagConstraints);

        nodeTreeSplitPane.setRightComponent(jPanel1);

        jSplitPane1.setLeftComponent(nodeTreeSplitPane);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        resultCatagoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultCatagoryComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 27, 0, 0);
        jPanel4.add(resultCatagoryComboBox, gridBagConstraints);

        destinationNodesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                destinationNodesListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                destinationNodesListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                destinationNodesListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(destinationNodesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 423;
        gridBagConstraints.ipady = 372;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 156);
        jPanel4.add(jScrollPane1, gridBagConstraints);

        forwardButton.setText("Forward");
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(44, 250, 48, 157);
        jPanel4.add(forwardButton, gridBagConstraints);

        backwardButton.setText("Backward");
        backwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backwardButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(44, 18, 48, 0);
        jPanel4.add(backwardButton, gridBagConstraints);

        resultLabel.setText("可能的结果");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 18, 0, 0);
        jPanel4.add(resultLabel, gridBagConstraints);

        viewPathButton.setText("View Path");
        viewPathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPathButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 27, 0, 0);
        jPanel4.add(viewPathButton, gridBagConstraints);

        exportPathButton.setText("Export Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 27, 0, 0);
        jPanel4.add(exportPathButton, gridBagConstraints);

        jSplitPane2.setRightComponent(jPanel4);

        jPanel5.setMinimumSize(new java.awt.Dimension(213, 380));
        jPanel5.setPreferredSize(new java.awt.Dimension(213, 380));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        relatedNodesLabel.setText("相关的搜索项");
        relatedNodesLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 125;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel5.add(relatedNodesLabel, gridBagConstraints);

        selectedNodesLabel.setLabelFor(selectedNodesList);
        selectedNodesLabel.setText("搜索项");
        selectedNodesLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 125;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        jPanel5.add(selectedNodesLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 213;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel5.add(jSeparator4, gridBagConstraints);

        selectedNodesList.setToolTipText("Double Click To Remove");
        selectedNodesList.setPreferredSize(new java.awt.Dimension(55, 110));
        selectedNodesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectedNodesListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                selectedNodesListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                selectedNodesListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(selectedNodesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 170;
        gridBagConstraints.ipady = 105;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 9, 0, 0);
        jPanel5.add(jScrollPane3, gridBagConstraints);

        relatedNodesList.setPreferredSize(new java.awt.Dimension(55, 110));
        relatedNodesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatedNodesListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                relatedNodesListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                relatedNodesListMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(relatedNodesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 170;
        gridBagConstraints.ipady = 169;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 9, 16, 0);
        jPanel5.add(jScrollPane4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 213;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(jSeparator5, gridBagConstraints);

        jSplitPane2.setLeftComponent(jPanel5);

        jSplitPane1.setRightComponent(jSplitPane2);

        getContentPane().add(jSplitPane1, "card2");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        fileNewMapItem.setMnemonic('n');
        fileNewMapItem.setText("New Map");
        fileNewMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNewMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileNewMapItem);
        fileMenu.add(jSeparator1);

        fileOpenMapItem.setMnemonic('o');
        fileOpenMapItem.setText("Open Map");
        fileOpenMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileOpenMapItem);

        fileCloseMapItem.setMnemonic('c');
        fileCloseMapItem.setText("Close Map");
        fileCloseMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileCloseMapItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileCloseMapItem);
        fileMenu.add(jSeparator2);

        fileSaveItem.setMnemonic('s');
        fileSaveItem.setText("Save");
        fileSaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSaveItem);

        fileSaveAsItem.setMnemonic('a');
        fileSaveAsItem.setText("Save As...");
        fileSaveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveAsItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileSaveAsItem);
        fileMenu.add(jSeparator3);

        fileExitItem.setMnemonic('e');
        fileExitItem.setText("Exit");
        fileExitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExitItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileExitItem);

        jMenuBar1.add(fileMenu);

        jMenu1.setMnemonic('a');
        jMenu1.setText("About");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileExitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExitItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileExitItemActionPerformed

    private void fileSaveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveAsItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveAsItemActionPerformed

    private void fileSaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveItemActionPerformed

    private void fileCloseMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileCloseMapItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileCloseMapItemActionPerformed

    private void fileOpenMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenMapItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileOpenMapItemActionPerformed

    private void fileNewMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNewMapItemActionPerformed

    }//GEN-LAST:event_fileNewMapItemActionPerformed

    private void resultCatagoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultCatagoryComboBoxActionPerformed
        controller.resultCatagoryComboBoxActionPerformed();
    }//GEN-LAST:event_resultCatagoryComboBoxActionPerformed

    private void nodesTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMouseClicked
        controller.nodesTreeMouseClicked(evt);
    }//GEN-LAST:event_nodesTreeMouseClicked

    private void nodesTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMousePressed
        controller.nodesTreeMousePressed(evt);
    }//GEN-LAST:event_nodesTreeMousePressed

    private void nodesTreeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodesTreeMouseReleased
        controller.nodesTreeMouseReleased(evt);
    }//GEN-LAST:event_nodesTreeMouseReleased

    private void selectedNodesListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedNodesListMousePressed
        controller.selectedNodesListMousePressed(evt);
    }//GEN-LAST:event_selectedNodesListMousePressed

    private void selectedNodesListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedNodesListMouseReleased
        controller.selectedNodesListMouseReleased(evt);
    }//GEN-LAST:event_selectedNodesListMouseReleased

    private void selectedNodesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedNodesListMouseClicked
        controller.selectedNodesListMouseClicked(evt);
    }//GEN-LAST:event_selectedNodesListMouseClicked

    private void relatedNodesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedNodesListMouseClicked
        controller.relatedNodesListMouseClicked(evt);
    }//GEN-LAST:event_relatedNodesListMouseClicked

    private void relatedNodesListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedNodesListMousePressed
        controller.relatedNodesListMousePressed(evt);
    }//GEN-LAST:event_relatedNodesListMousePressed

    private void relatedNodesListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedNodesListMouseReleased
        controller.relatedNodesListMouseReleased(evt);
    }//GEN-LAST:event_relatedNodesListMouseReleased

    private void deleteNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteNodeButtonActionPerformed
        controller.deleteNodeButtonActionPerformed();
    }//GEN-LAST:event_deleteNodeButtonActionPerformed

    private void createNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNodeButtonActionPerformed
        controller.createNodeButtonActionPerformed();
    }//GEN-LAST:event_createNodeButtonActionPerformed

    private void editNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNodeButtonActionPerformed
        controller.editNodeButtonActionPerformed();
    }//GEN-LAST:event_editNodeButtonActionPerformed

    private void viewNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNodeButtonActionPerformed
        controller.viewNodeButtonActionPerformed();
    }//GEN-LAST:event_viewNodeButtonActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        controller.selectNodeButtonActionPerformed();
    }//GEN-LAST:event_selectButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        controller.removeButtonActionPerformed();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        controller.addButtonActionPerformed();
    }//GEN-LAST:event_addButtonActionPerformed

    private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
        controller.forwardButtonActionPerformed();
    }//GEN-LAST:event_forwardButtonActionPerformed

    private void backwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backwardButtonActionPerformed
        controller.backwardButtonActionPerformed();
    }//GEN-LAST:event_backwardButtonActionPerformed

    private void destinationNodesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_destinationNodesListMouseClicked
        controller.destinationNodesListMouseClicked(evt);
    }//GEN-LAST:event_destinationNodesListMouseClicked

    private void destinationNodesListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_destinationNodesListMousePressed
        controller.destinationNodesListMousePressed(evt);
    }//GEN-LAST:event_destinationNodesListMousePressed

    private void destinationNodesListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_destinationNodesListMouseReleased
        controller.destinationNodesListMouseReleased(evt);
    }//GEN-LAST:event_destinationNodesListMouseReleased

    private void viewPathButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPathButtonActionPerformed
        controller.viewPathButtonActionPerformed();
    }//GEN-LAST:event_viewPathButtonActionPerformed

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
        a.addDestination(e);

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
    
    JLabel getResultLabel() {
        return resultLabel;
    }
    
    JLabel getRelatedNodesLabel() {
        return relatedNodesLabel;
    }
    
    JLabel getSelectedNodesLabel() {
        return selectedNodesLabel;
    }
    
    JComboBox getResultCatagoryComboBox() {
        return resultCatagoryComboBox;
    }
    
    JList getDestinationNodesList() {
        return destinationNodesList;
    }
    
    JList getRelatedNodesList() {
        return relatedNodesList;
    }
    
    JList getSelectedNodesList() {
        return selectedNodesList;
    }
    
    JTree getNodesTree() {
        return nodesTree;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backwardButton;
    private javax.swing.JButton createNodeButton;
    private javax.swing.JButton deleteNodeButton;
    private javax.swing.JList<String> destinationNodesList;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar nodeToolBar;
    private javax.swing.JSplitPane nodeTreeSplitPane;
    private javax.swing.JTree nodesTree;
    private javax.swing.JLabel relatedNodesLabel;
    private javax.swing.JList<String> relatedNodesList;
    private javax.swing.JButton removeButton;
    private javax.swing.JComboBox<String> resultCatagoryComboBox;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JButton selectButton;
    private javax.swing.JLabel selectedNodesLabel;
    private javax.swing.JList<String> selectedNodesList;
    private javax.swing.JButton viewNodeButton;
    private javax.swing.JButton viewPathButton;
    // End of variables declaration//GEN-END:variables
}
