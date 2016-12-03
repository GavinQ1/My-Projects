package bankingApp;


import java.awt.event.*;

/**
 * Interface for CreateAccount application View Controller
 * @author Gavin
 */
public interface CreateAccountGUIControl {
    
    public void register(CreateAccountGUI view);
    public ActionListener addCreateAccountActionListener();
    public ActionListener addCancelActionListener();
    
}
