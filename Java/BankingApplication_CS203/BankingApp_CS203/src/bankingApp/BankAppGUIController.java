package bankingApp;

import account.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.Timer;

/**
 * Implemented controller for BankAppGUI
 *
 * @author Gavin
 */
public class BankAppGUIController extends GeneralController implements BankAppGUIControl {

    private ListOfAccounts accounts;
    private ArrayList<Account> displayedAccounts;
    private BankAppGUI view;

    public BankAppGUIController(ListOfAccounts accounts) {
        this(accounts, null);
    }

    public BankAppGUIController(ListOfAccounts accounts, BankAppGUI view) {
        super();
        this.accounts = accounts;
        this.displayedAccounts = new ArrayList<Account>();
        this.view = view;
    }

    public void register(BankAppGUI view) {
        this.view = view;
    }

    public ActionListener addLogInActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginAction();
            }
        };
    }

    public ActionListener addSearchActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                searchByNameAction();
            }
        };
    }

    public ActionListener addNewAccountActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createAccountAction();
            }
        };
    }

    public ItemListener addAccountTypeItemListener() {
        return new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                updateAccountListAction();
            }
        };
    }

    public MouseAdapter addAccountListMouseListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    loginAction();
                }
            }
        };
    }

    public void runTime() {
        dailySettle(); // always settle once when started
        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                LocalDateTime now = LocalDateTime.now();
                if (now.getHour() == 0
                        && now.getMinute() == 0
                        && now.getSecond() == 0) {
                    dailySettle();
                }
                view.getTimeLabel().setText(
                        now.format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        }).start();
    }

    private void updateAccountListAction(String scope) {
        if (scope.equals(BankAppGUI.ACCOUNT_TYPES[1])) 
            this.displayedAccounts = this.accounts.getAccountList();
        else if (scope.equals(BankAppGUI.ACCOUNT_TYPES[2])) 
            this.displayedAccounts = this.accounts.getAccountList(CheckingAccount.class);
        else if (scope.equals(BankAppGUI.ACCOUNT_TYPES[3]))
            this.displayedAccounts = this.accounts.getAccountList(SavingAccount.class);
        else if (scope.equals(BankAppGUI.ACCOUNT_TYPES[0]))
            this.displayedAccounts = new ArrayList();
        
        view.getAccountListField().setModel(this.listModelFactory(this.displayedAccounts));
    }

    private void updateAccountListAction() {
        this.updateAccountListAction((String) view.getAccountTypeBox().getSelectedItem());
    }

    private AbstractListModel<Account> listModelFactory(final ArrayList<Account> accounts) {
        return new AbstractListModel<Account>() {
            ArrayList<Account> accountList = accounts;

            public int getSize() {
                return accountList.size();
            }

            public Account getElementAt(int i) {
                return accountList.get(i);
            }
        };
    }

    private void loginAction() {
        Account selectedAccount = view.getAccountListField().getSelectedValue();
        
        if (selectedAccount != null) 
            AccountApplication.run(selectedAccount);
        
        updateAccountListAction();
    }

    private void createAccountAction() {
        CreateAccountApplication.run(this.accounts);
        view.getAccountTypeBox().setSelectedItem(BankAppGUI.ACCOUNT_TYPES[1]);
        updateAccountListAction(BankAppGUI.ACCOUNT_TYPES[1]);
    }

    private void dailySettle() {
        this.accounts.dailySettleSavingAccounts();
        this.updateAccountListAction();
    }

    private void searchByNameAction() {
        String searchContent = view.getSearchTextField().getText().trim().toLowerCase();
        int i = 0;
        ArrayList<Account> newDisplayedAccounts = new ArrayList();
        ArrayList<Account> accounts = this.accounts.getAccountList();
        
        while (i < accounts.size()) {
            Account a = accounts.get(i++);
            if (a.getName().toLowerCase().startsWith(searchContent)) {
                newDisplayedAccounts.add(a);
            }
        
        }
        displayedAccounts = newDisplayedAccounts;
        view.getAccountListField().setModel(listModelFactory(displayedAccounts));
    }

}
