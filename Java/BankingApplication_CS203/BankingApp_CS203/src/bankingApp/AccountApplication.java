package bankingApp;

import account.Account;

/**
 *
 * @author Gavin
 */
public class AccountApplication {
    
    public final static void run(Account selectedAccount) {
        EnhanceAppView.enhanceVision();
        AccountGUIControl controller = new AccountGUIController(selectedAccount);
        new AccountGUI(selectedAccount, controller).setVisible(true);
    }
    
}
