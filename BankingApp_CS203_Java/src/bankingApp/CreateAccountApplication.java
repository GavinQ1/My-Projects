package bankingApp;

import account.ListOfAccounts;


/**
 *
 * @author Gavin
 */
public class CreateAccountApplication {
    
    public final static void run(ListOfAccounts accounts) {
        EnhanceAppView.enhanceVision();
        CreateAccountGUIController controller = new CreateAccountGUIController(accounts);
        new CreateAccountGUI(accounts, controller).setVisible(true);
    } 
    
}
