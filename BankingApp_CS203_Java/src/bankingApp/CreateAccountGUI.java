package bankingApp;

import account.*;
import javax.swing.*;

/**
 * CreateAccount application View
 * @author Gavin
 */
public class CreateAccountGUI extends JDialog {
    private ListOfAccounts accounts;
    private CreateAccountGUIControl controller;
    
    private JComboBox<String> accountType;
    private JLabel accountTypeLabel;
    private JButton cancel;
    private JButton createAccount;
    private JLabel depositLabel;
    private JTextField depositTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    
    
    public CreateAccountGUI(ListOfAccounts accounts,
            CreateAccountGUIControl controller) {
        setModal(true);
        this.accounts = accounts;
        this.controller = controller;
        this.controller.register(this);
        initComponents();
    }
                      
    private void initComponents() {

        nameLabel = new JLabel();
        depositLabel = new JLabel();
        nameTextField = new JTextField();
        depositTextField = new JTextField();
        accountTypeLabel = new JLabel();
        accountType = new JComboBox<>();
        createAccount = new JButton();
        cancel = new JButton();

        setTitle("New Account");

        nameLabel.setText("Name:");

        depositLabel.setText("First Deposite: $");

        depositTextField.setText("0.00");

        accountTypeLabel.setText("Account Type: ");

        accountType.setModel(new DefaultComboBoxModel<>(new String[] { "Checking Account", "Saving Account" }));

        createAccount.setText("Create Account");
        createAccount.addActionListener(this.controller.addCreateAccountActionListener());

        cancel.setText("Cancel");
        cancel.addActionListener(this.controller.addCancelActionListener());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(accountTypeLabel)
                        .addGap(31, 31, 31)
                        .addComponent(accountType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(depositLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(depositTextField, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createAccount)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(depositLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                    .addComponent(depositTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(accountType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountTypeLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createAccount)
                    .addComponent(cancel))
                .addGap(36, 36, 36))
        );

        pack();
    }                     

    public JComboBox<String> getAccountTypeBox() {
        return this.accountType;
    }
    
    public JTextField getDepositTextField() {
        return this.depositTextField;
    }
    
    public JTextField getNameTextField() {
        return this.nameTextField;
    }
    
    public JButton getCancelButton() {
        return this.cancel;
    }
    
    public JButton getCreateAccountButton() {
        return this.createAccount;
    }
    
    // unit test
    public static void main(String args[]) {
        ListOfAccounts accounts = new ListOfAccounts();
        CreateAccountGUIController controller = new CreateAccountGUIController(accounts);
        new CreateAccountGUI(accounts, controller);
    }        
}

