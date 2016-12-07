import account.*;
import bankingApp.*;
import java.util.Date;

/**
 * Create a bank application
 * @author Junyan Qi
 */
public class BankApplication {
    
    public static void run(ListOfAccounts accounts) {
        EnhanceAppView.enhanceVision();
        BankAppGUIControl controller = new BankAppGUIController(accounts);
        new BankAppGUI(accounts, controller).setVisible(true);
    }
    
    public static void main(String[] args) {
        
        ListOfAccounts accounts = new ListOfAccounts();
        
        Date yesterday = new Date(2016-1900, 10, 29);
        Date lastYear = new Date(2015-1900, 10, 30);
        Date reallyPast = new Date(0, 0, 1);
        
        accounts.addCheckingAccount("Alice", 100, lastYear);
        accounts.addSavingAccount("Bob", 100, lastYear);
        accounts.addCheckingAccount("Calvin", 0);
        accounts.addSavingAccount("Diana", 1000, reallyPast);
        accounts.addCheckingAccount("Frank", 0);
        accounts.addSavingAccount("Gina", 100, yesterday);
        
        BankApplication.run(accounts);
    }

}
