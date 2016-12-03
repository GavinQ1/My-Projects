package bankingApp;

import account.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implemented controller for CreateAccountDialogueGUI
 * @author Gavin
 */
public class CreateAccountGUIController extends GeneralController implements CreateAccountGUIControl {

    private ListOfAccounts accounts;
    private CreateAccountGUI view;

    public CreateAccountGUIController(ListOfAccounts accounts) {
        this(accounts, null);
    }

    public CreateAccountGUIController(ListOfAccounts accounts, CreateAccountGUI view) {
        super();
        this.accounts = accounts;
        this.view = view;
    }

    public void register(CreateAccountGUI view) {
        this.view = view;
    }

    public ActionListener addCreateAccountActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String typeOfAccount = (String) view.getAccountTypeBox().getSelectedItem();
                try {
                    String name = getNameInput();
                    double amount = getAmount();
                    Account a;
                    if ("Checking Account".equals(typeOfAccount)) {
                        a = new CheckingAccount(name, 0);
                    } else {
                        a = new SavingAccount(name, 0);
                    }
                    a.deposit(amount);
                    accounts.addAccount(a);
                    view.dispose();
                } catch (NumberFormatException | InValidInputException e) {
                    CreateAccountGUIController.errorMessageBox(e.getMessage());
                }
            }
        };
    }
    
    public ActionListener addCancelActionListener() {
         return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                view.dispose();
            }
         };
    }
    
    private String getNameInput() {
        String res = view.getNameTextField().getText().trim();
        if ("".equals(res)) {
            throw new InValidInputException("This entry can't be empty.");
        }
        return res;
    }
    
    private double getAmount() throws NumberFormatException {
        String content = view.getDepositTextField().getText().trim();
        return Double.parseDouble(content);
    }
}
