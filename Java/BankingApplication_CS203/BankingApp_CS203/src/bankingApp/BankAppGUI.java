package bankingApp;

import javax.swing.*;
import account.*;
import java.awt.*;

/**
 * Bank application View
 *
 * @author Gavin
 */
public class BankAppGUI extends JFrame {
    static final String[] ACCOUNT_TYPES = {"Choose", "All accounts", "Checking accounts", "Saving accounts"};

    private ListOfAccounts accounts;
    private BankAppGUIControl controller;

    private JLabel Title;
    private JList<Account> accountList;
    private JLabel accountTitle;
    private JComboBox<String> accountType;
    private JLabel accountTypeTitle;
    private JMenuItem jMenuItem1;
    private JScrollPane jScrollPane2;
    private JButton logIn;
    private JButton newAccount;
    private JButton search;
    private JTextField searchCommand;
    private JLabel timeLabel;

    public BankAppGUI(BankAppGUIControl controller) {
        this(new ListOfAccounts(), controller);
    }

    public BankAppGUI(ListOfAccounts accounts, BankAppGUIControl controller) {

        this.accounts = accounts;
        this.controller = controller;
        this.controller.register(this);

        initComponents();

        this.controller.runTime(); //set off time
    }

    private void initComponents() {

        jMenuItem1 = new JMenuItem();
        searchCommand = new JTextField();
        search = new JButton();
        logIn = new JButton();
        accountType = new JComboBox<>();
        jScrollPane2 = new JScrollPane();
        accountList = new JList<>();
        accountTypeTitle = new JLabel();
        Title = new JLabel();
        accountTitle = new JLabel();
        timeLabel = new JLabel();
        newAccount = new JButton();

        jMenuItem1.setText("jMenuItem1");

        setTitle("Bank Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        search.setText("Search");
        search.addActionListener(this.controller.addSearchActionListener());

        logIn.setText("Log in");
        logIn.addActionListener(this.controller.addLogInActionListener());

        accountType.setModel(new DefaultComboBoxModel<>(ACCOUNT_TYPES));
        accountType.addItemListener(this.controller.addAccountTypeItemListener());

        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountList.addMouseListener(this.controller.addAccountListMouseListener());
        jScrollPane2.setViewportView(accountList);

        accountTypeTitle.setText("Account Type: ");

        Title.setFont(new Font("sansserif", 1, 36));
        Title.setText("      Bank Application");

        accountTitle.setText("Accounts:");

        newAccount.setText("New Account");
        newAccount.addActionListener(this.controller.addNewAccountActionListener());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(36, 36, 36)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(accountType, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(accountTypeTitle))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(accountTitle, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(6, 6, 6)
                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(search, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(logIn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(searchCommand, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(newAccount, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(202, 202, 202)
                                                        .addComponent(Title, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 128, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Title, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(accountTypeTitle)
                                .addComponent(accountTitle))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(accountType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2)
                                        .addGap(16, 16, 16)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(search)
                                                .addComponent(searchCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addComponent(logIn))
                                .addComponent(newAccount))
                        .addGap(9, 9, 9)
                        .addComponent(timeLabel)
                        .addGap(19, 19, 19))
        );

        pack();
    }

    public JList<Account> getAccountListField() {
        return this.accountList;
    }

    public JComboBox<String> getAccountTypeBox() {
        return this.accountType;
    }

    public JButton getLogInButton() {
        return this.logIn;
    }

    public JButton getNewAccountButton() {
        return this.newAccount;
    }

    public JButton getSearchButton() {
        return this.search;
    }

    public JTextField getSearchTextField() {
        return this.searchCommand;
    }

    public JLabel getTimeLabel() {
        return this.timeLabel;
    }

    // unit test
    public static void main(String args[]) {

        ListOfAccounts accounts = new ListOfAccounts();
        accounts.addCheckingAccount("Alice", 0);
        accounts.addSavingAccount("Bob", 0);
        BankAppGUIControl controller = new BankAppGUIController(accounts);
        new BankAppGUI(accounts, controller).setVisible(true);

    }
}
