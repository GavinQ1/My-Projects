package bankingApp;

import java.awt.event.*;

/**
 * Interface for Account application View Controller
 * @author Gavin
 */
public interface AccountGUIControl {
    
    public void register(AccountGUI view);
    public ActionListener addSubmitActionListener();
    
}
