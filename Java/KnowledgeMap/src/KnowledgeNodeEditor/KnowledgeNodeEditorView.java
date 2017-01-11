package KnowledgeNodeEditor;

import Models.*;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
        this.node = controller.getNode();
        initComponents();
        controller.initialize();
        setModal(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                controller.exitActionPerformed();
            }
        });
        getContentPane().setBackground(Color.lightGray);
        knowledgeNodeInfoPane.setBackground(Color.WHITE);
    }
    
    void setSelectedTreeNode(MyTreeNode k) { 
        this.selectedTreeNode = k; 
    }
    
    DefaultTreeModel createTreeModel(KnowledgeNode node) {
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
        return new DefaultTreeModel(root);
    }
    
    private void addLeavesFromKnowledgeList(MyTreeNode root, KnowledgeNodeList l) {
        for (KnowledgeNode k : l) {
            MyTreeNode n = new MyTreeNode(k);
            n.setAllowsChildren(false);
            root.add(n);
        }
    }
    
    
    public DefaultTreeModel getTreeModel() { return (DefaultTreeModel) knowledgeTree.getModel(); }
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
    
    
     // unit test
    public static void main(String args[]) throws CloneNotSupportedException {
        EnhanceAppView.enhanceVision();
        
        /* Create and display the form */
        KnowledgeNode a = new KnowledgeNode("A", "Character", "A", "First", "Source", "Destination", "Neighbor");
        KnowledgeNode b = new KnowledgeNode("B", "Character", "B", "Second", "Source", "Destination", "Neighbor");
        KnowledgeNode c = new KnowledgeNode("C", "Character", "C", "Third", "Source", "Destination", "Neighbor");
        KnowledgeNode d = new KnowledgeNode("D", "Character", "D", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode e = new KnowledgeNode("E", "Character", "E", "Fourth", "Source", "Destination", "Neighbor");
        KnowledgeNode f = new KnowledgeNode("F", "Character", "F", "Fourth", "Source", "Destination", "Neighbor");
        a.addDestination(d);
        e.addDestination(d);
        c.addDestination(a);
        
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
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        knowledgeNodeInfoPane = new javax.swing.JTextPane();
        nameLabel = new javax.swing.JLabel();
        catagoryLabel = new javax.swing.JLabel();
        definitionLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        catagoryTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        definitionTextArea = new javax.swing.JTextArea();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        relatedNodeLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        knowledgeTree = new javax.swing.JTree(createTreeModel(node));
        addNodeButton = new javax.swing.JButton();
        removeNodeButton = new javax.swing.JButton();
        renameRootButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        editKnowledgeNodeButton = new javax.swing.JButton();
        newNodeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Knowledge Node Editor");
        setBackground(new java.awt.Color(255, 204, 0));
        setIconImage(null);
        setModal(true);

        knowledgeNodeInfoPane.setEditable(false);
        knowledgeNodeInfoPane.setBackground(new java.awt.Color(255, 255, 255));
        knowledgeNodeInfoPane.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));
        knowledgeNodeInfoPane.setFont(new java.awt.Font("华文细黑", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(knowledgeNodeInfoPane);

        nameLabel.setBackground(new java.awt.Color(102, 102, 102));
        nameLabel.setFont(new java.awt.Font("华文细黑", 1, 14)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(102, 102, 102));
        nameLabel.setText("Name:");

        catagoryLabel.setFont(new java.awt.Font("华文细黑", 1, 14)); // NOI18N
        catagoryLabel.setForeground(new java.awt.Color(102, 102, 102));
        catagoryLabel.setText("Catagory:");

        definitionLabel.setFont(new java.awt.Font("华文细黑", 1, 14)); // NOI18N
        definitionLabel.setForeground(new java.awt.Color(102, 102, 102));
        definitionLabel.setText("Definition:");

        descriptionLabel.setFont(new java.awt.Font("华文细黑", 1, 14)); // NOI18N
        descriptionLabel.setForeground(new java.awt.Color(102, 102, 102));
        descriptionLabel.setText("Description:");

        nameTextField.setFont(new java.awt.Font("华文细黑", 0, 14)); // NOI18N
        nameTextField.setText("jTextField1");
        nameTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));

        catagoryTextField.setFont(nameTextField.getFont());
        catagoryTextField.setText("jTextField2");
        catagoryTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setFont(new java.awt.Font("华文细黑", 0, 14)); // NOI18N
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));
        jScrollPane2.setViewportView(descriptionTextArea);

        definitionTextArea.setColumns(20);
        definitionTextArea.setFont(new java.awt.Font("华文细黑", 0, 14)); // NOI18N
        definitionTextArea.setLineWrap(true);
        definitionTextArea.setRows(5);
        definitionTextArea.setWrapStyleWord(true);
        definitionTextArea.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));
        jScrollPane3.setViewportView(definitionTextArea);

        cancelButton.setBackground(new java.awt.Color(102, 102, 102));
        cancelButton.setFont(new java.awt.Font("华文琥珀", 0, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(204, 204, 204));
        cancelButton.setText("Exit");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(102, 102, 102));
        saveButton.setFont(new java.awt.Font("华文琥珀", 0, 14)); // NOI18N
        saveButton.setForeground(new java.awt.Color(204, 204, 204));
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        relatedNodeLabel.setFont(new java.awt.Font("华文细黑", 1, 14)); // NOI18N
        relatedNodeLabel.setForeground(new java.awt.Color(102, 102, 102));
        relatedNodeLabel.setText("Related Nodes:");

        knowledgeTree.setRootVisible(false);
        knowledgeTree.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));
        knowledgeTree.setFont(new java.awt.Font("华文细黑", 0, 14)); // NOI18N
        knowledgeTree.setForeground(new java.awt.Color(102, 102, 102));
        knowledgeTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        knowledgeTree.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
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

        addNodeButton.setBackground(new java.awt.Color(102, 102, 102));
        addNodeButton.setFont(new java.awt.Font("华文琥珀", 0, 15)); // NOI18N
        addNodeButton.setForeground(new java.awt.Color(204, 204, 204));
        addNodeButton.setText("Add");
        addNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNodeButtonActionPerformed(evt);
            }
        });

        removeNodeButton.setBackground(new java.awt.Color(102, 102, 102));
        removeNodeButton.setFont(new java.awt.Font("华文琥珀", 0, 14)); // NOI18N
        removeNodeButton.setForeground(new java.awt.Color(204, 204, 204));
        removeNodeButton.setText("Remove");
        removeNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeNodeButtonActionPerformed(evt);
            }
        });

        renameRootButton.setBackground(new java.awt.Color(102, 102, 102));
        renameRootButton.setFont(new java.awt.Font("华文琥珀", 0, 15)); // NOI18N
        renameRootButton.setForeground(new java.awt.Color(204, 204, 204));
        renameRootButton.setText("Rename Root");
        renameRootButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameRootButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("华文琥珀", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("知识点编辑器");

        editKnowledgeNodeButton.setBackground(new java.awt.Color(102, 102, 102));
        editKnowledgeNodeButton.setFont(new java.awt.Font("华文琥珀", 0, 14)); // NOI18N
        editKnowledgeNodeButton.setForeground(new java.awt.Color(204, 204, 204));
        editKnowledgeNodeButton.setText("Edit");
        editKnowledgeNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editKnowledgeNodeButtonActionPerformed(evt);
            }
        });

        newNodeButton.setBackground(new java.awt.Color(102, 102, 102));
        newNodeButton.setFont(new java.awt.Font("华文琥珀", 0, 14)); // NOI18N
        newNodeButton.setForeground(new java.awt.Color(204, 204, 204));
        newNodeButton.setText("New");
        newNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newNodeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(catagoryLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                        .addComponent(definitionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(catagoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(relatedNodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addNodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(removeNodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(editKnowledgeNodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(renameRootButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(newNodeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(358, 358, 358)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
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
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(catagoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(catagoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(definitionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(relatedNodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(renameRootButton)
                                .addGap(18, 18, 18)
                                .addComponent(addNodeButton)
                                .addGap(18, 18, 18)
                                .addComponent(removeNodeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editKnowledgeNodeButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(saveButton)
                            .addComponent(newNodeButton)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
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
        this.controller.exitActionPerformed();
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

    private void editKnowledgeNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editKnowledgeNodeButtonActionPerformed
        this.controller.editKnowledgeNodeActionPerformed();
    }//GEN-LAST:event_editKnowledgeNodeButtonActionPerformed

    private void newNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newNodeButtonActionPerformed
        this.controller.newNodeActionPerformed();
    }//GEN-LAST:event_newNodeButtonActionPerformed

    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNodeButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel catagoryLabel;
    private javax.swing.JTextField catagoryTextField;
    private javax.swing.JLabel definitionLabel;
    private javax.swing.JTextArea definitionTextArea;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton editKnowledgeNodeButton;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane knowledgeNodeInfoPane;
    private javax.swing.JTree knowledgeTree;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton newNodeButton;
    private javax.swing.JLabel relatedNodeLabel;
    private javax.swing.JButton removeNodeButton;
    private javax.swing.JButton renameRootButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
