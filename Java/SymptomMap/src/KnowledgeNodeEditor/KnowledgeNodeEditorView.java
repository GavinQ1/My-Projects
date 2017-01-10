package KnowledgeNodeEditor;

import Models.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Gavin
 */
public class KnowledgeNodeEditorView extends javax.swing.JDialog {
    static class MyTreeNode extends DefaultMutableTreeNode {
        
        public MyTreeNode(Object k) {
            super(k);
        }
        
        public String toString() {
            if (getUserObject() instanceof KnowledgeNode)
                return ((KnowledgeNode) getUserObject()).getName();
            if (getUserObject() instanceof KnowledgeNodeList)
                return ((KnowledgeNodeList) getUserObject()).getName();
            return super.toString();
        }
    }
    
    private KnowledgeNodeEditorControl controller;
    private KnowledgeNode node;
    private MyTreeNode selectedTreeNode, sources, destinations, neighbors;
    
    /**
     * Creates new form KnowledgeNodeEditorView
     */
    public KnowledgeNodeEditorView() {
        this(null);
    }
    
    public KnowledgeNodeEditorView(KnowledgeNodeEditorControl controller) {
        this.controller = controller;
        this.selectedTreeNode = null;
        
        controller.register(this);
        this.node = controller.getTempNode();
        initComponents();
        controller.initialize();
        setModal(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        knowledgeNodeInfoPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        catagoryTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        definitionTextArea = new javax.swing.JTextArea();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        MyTreeNode root = new MyTreeNode(node);
        sources = new MyTreeNode(node.getSources());
        addLeavesFromKnowledgeList(sources, node.getSources());
        destinations = new MyTreeNode(node.getDestinations());
        addLeavesFromKnowledgeList(destinations, node.getDestinations());
        neighbors = new MyTreeNode(node.getNeighbors());
        addLeavesFromKnowledgeList(neighbors, node.getNeighbors());
        root.add(sources);
        root.add(destinations);
        root.add(neighbors);
        knowledgeTree = new javax.swing.JTree(root);
        addNodeButton = new javax.swing.JButton();
        removeNodeButton = new javax.swing.JButton();
        renameRootButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Knowledge Node Editor");

        knowledgeNodeInfoPane.setEditable(false);
        knowledgeNodeInfoPane.setFont(new java.awt.Font("华文细黑", 0, 15)); // NOI18N
        knowledgeNodeInfoPane.setEnabled(false);
        jScrollPane1.setViewportView(knowledgeNodeInfoPane);

        jLabel1.setText("Name:");

        jLabel2.setText("Catagory");

        jLabel3.setText("Definition");

        jLabel4.setText("Description");

        nameTextField.setText("jTextField1");

        catagoryTextField.setText("jTextField2");

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(descriptionTextArea);

        definitionTextArea.setColumns(20);
        definitionTextArea.setLineWrap(true);
        definitionTextArea.setRows(5);
        definitionTextArea.setWrapStyleWord(true);
        jScrollPane3.setViewportView(definitionTextArea);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Related Nodes");

        knowledgeTree.setRootVisible(false);
        knowledgeTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        knowledgeTree.setRootVisible(false);
        knowledgeTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                knowledgeTreeMouseClicked(evt);
            }
        });
        knowledgeTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                knowledgeTreeValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(knowledgeTree);

        addNodeButton.setText("Add");
        addNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNodeButtonActionPerformed(evt);
            }
        });

        removeNodeButton.setText("Remove");
        removeNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeNodeButtonActionPerformed(evt);
            }
        });

        renameRootButton.setText("Rename Root");
        renameRootButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameRootButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("华文细黑", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("知识点编辑器");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(catagoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(renameRootButton, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                            .addComponent(addNodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(removeNodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(358, 358, 358)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(catagoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(renameRootButton))
                                .addGap(18, 18, 18)
                                .addComponent(addNodeButton)
                                .addGap(18, 18, 18)
                                .addComponent(removeNodeButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(saveButton)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void knowledgeTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_knowledgeTreeValueChanged
        this.controller.knowledgeTreeValueChangedAction((MyTreeNode) knowledgeTree.getLastSelectedPathComponent());
    }//GEN-LAST:event_knowledgeTreeValueChanged

    private void addNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNodeButtonActionPerformed
        this.controller.knowledgeTreeAddActionPerformed();
    }//GEN-LAST:event_addNodeButtonActionPerformed

    private void removeNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeNodeButtonActionPerformed
        this.controller.knowledgeTreeRemoveActionPerformed();
    }//GEN-LAST:event_removeNodeButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.controller.cancelActionPerformed();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        this.controller.saveActionPerformed();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void renameRootButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameRootButtonActionPerformed
        this.controller.knowledgeTreeRenameRootActionPerformed();
    }//GEN-LAST:event_renameRootButtonActionPerformed

    private void knowledgeTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_knowledgeTreeMouseClicked
        this.controller.knowledgeTreeNodeMouseDoubleClicked(evt);
    }//GEN-LAST:event_knowledgeTreeMouseClicked

    private void addLeavesFromKnowledgeList(MyTreeNode root, KnowledgeNodeList l) {
        for (KnowledgeNode k : l) {
            MyTreeNode n = new MyTreeNode(k);
            n.setAllowsChildren(false);
            root.add(n);
        }
    }
    
    public void setSelectedTreeNode(MyTreeNode k) { 
        this.selectedTreeNode = k; 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws CloneNotSupportedException {
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
            java.util.logging.Logger.getLogger(KnowledgeNodeEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KnowledgeNodeEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KnowledgeNodeEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnowledgeNodeEditorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        KnowledgeNode a = new KnowledgeNode("A", "Character", "A", "First", "Source", "Destination", "Neighbor");
        KnowledgeNode b = new KnowledgeNode("B", "Character", "B", "Second", "Source", "Destination", "Neighbor");
        KnowledgeNode c = new KnowledgeNode("C", "Character", "C", "Third", "Source", "Destination", "Neighbor");
        KnowledgeNode d = new KnowledgeNode("D", "Character", "D", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode e = new KnowledgeNode("E", "Character", "E", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode f = new KnowledgeNode("F", "Character", "F", "Fourth", "Source", "Destination", "Neighbor");
        
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
    
    public MyTreeNode getSourcesTreeNode() { return sources; }
    public MyTreeNode getDestinationsTreeNode() { return destinations; }
    public MyTreeNode getNeighborsTreeNode() { return neighbors; }
    public MyTreeNode getSelectedTreeNode() { return this.selectedTreeNode; }
    public javax.swing.JButton getAddNodeButton() { return addNodeButton;}
    public javax.swing.JButton getCancelButton() { return cancelButton;}
    public javax.swing.JTextField getCatagoryTextField() { return catagoryTextField;}
    public javax.swing.JTextArea getDefinitionTextArea() { return definitionTextArea;}
    public javax.swing.JTextArea getDescriptionTextArea() { return descriptionTextArea;}
    public javax.swing.JTextPane getKnowledgeNodeInfoPane() { return knowledgeNodeInfoPane;}
    public javax.swing.JTree getKnowledgeTree() { return knowledgeTree;}
    public javax.swing.JTextField getNameTextField() { return nameTextField;}
    public javax.swing.JButton getRemoveNodeButton() { return removeNodeButton;}
    public javax.swing.JButton getRenameRootButton() { return renameRootButton;}
    public javax.swing.JButton getSubmitButton() { return saveButton;}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNodeButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField catagoryTextField;
    private javax.swing.JTextArea definitionTextArea;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane knowledgeNodeInfoPane;
    private javax.swing.JTree knowledgeTree;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton removeNodeButton;
    private javax.swing.JButton renameRootButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}