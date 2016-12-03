package bankingApp;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;

/**
 * Interface for BankApp application View Controller
 * @author Gavin
 */
public interface BankAppGUIControl {
    
    public void register(BankAppGUI view);
    public ActionListener addLogInActionListener();
    public ActionListener addSearchActionListener();
    public ActionListener addNewAccountActionListener();
    public ItemListener addAccountTypeItemListener();
    public MouseAdapter addAccountListMouseListener();
    public void runTime();
    
}
