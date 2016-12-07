package bankingApp;

import account.*;
import javax.swing.*;

/**
 * Account application View
 * @author Gavin
 */
public class AccountGUI extends JDialog {
    private Account selectedAccount;
    private AccountGUIControl controller;
    
    private JLabel amountLabel;
    private JTextField amountTextField;
    private ButtonGroup buttonGroup1;
    private JRadioButton deposit;
    private JTextArea detailArea;
    private JLabel detailLabel;
    private JScrollPane jScrollPane1;
    private JLabel operationTypeLabel;
    private JButton submit;
    private JRadioButton withraw;
    
    public AccountGUI(Account selectedAccount, AccountGUIControl controller) {
        this.setModal(true);
        this.selectedAccount = selectedAccount;
        this.controller = controller;
        this.controller.register(this);
        initComponents();
    }
    
    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jScrollPane1 = new JScrollPane();
        detailArea = new JTextArea();
        detailLabel = new JLabel();
        amountLabel = new JLabel();
        amountTextField = new JTextField();
        operationTypeLabel = new JLabel();
        submit = new JButton();
        deposit = new JRadioButton();
        withraw = new JRadioButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Account Details");

        detailArea.setColumns(20);
        detailArea.setRows(5);
        detailArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        detailArea.setEnabled(false);
        jScrollPane1.setViewportView(detailArea);
        detailArea.setText(this.selectedAccount.accountDetail());

        detailLabel.setText("Account Details: ");

        amountLabel.setText("Amount:  $");

        amountTextField.setText("0.00");

        operationTypeLabel.setText("Operation Type: ");

        submit.setText("Submit");
        submit.addActionListener(this.controller.addSubmitActionListener());

        buttonGroup1.add(deposit);
        deposit.setSelected(true);
        deposit.setText("Deposite");

        buttonGroup1.add(withraw);
        withraw.setText("Withdraw");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(detailLabel)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(amountLabel)
                        .addGap(5, 5, 5)
                        .addComponent(amountTextField, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 91, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(submit)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(operationTypeLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(withraw)
                                    .addComponent(deposit))))
                        .addGap(0, 84, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(detailLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(amountLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                            .addComponent(amountTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(operationTypeLabel)
                            .addComponent(deposit))
                        .addGap(18, 18, 18)
                        .addComponent(withraw)
                        .addGap(62, 62, 62)
                        .addComponent(submit)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        
        pack();
    }                      
    
    public Account getSelectedAccount() {
        return this.selectedAccount;
    }
    
    public JTextField getAmountTextField() {
        return this.amountTextField;
    }
    
    public JTextArea getDetailArea() {
        return this.detailArea;
    }
    
    public JRadioButton getDepositRadio() {
        return this.deposit;
    }
    
    public JRadioButton getWithrawRadio() {
        return this.withraw;
    }
    
    // unit test
    public static void main(String args[]) {
        Account a = new CheckingAccount("Alice", 0);
        AccountGUIControl controller = new AccountGUIController(a);
         new AccountGUI(a, controller).setVisible(true);
    }          
}
