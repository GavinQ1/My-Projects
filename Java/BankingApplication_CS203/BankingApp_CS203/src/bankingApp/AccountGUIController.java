package bankingApp;

import account.Account;
import account.InValidInputException;
import account.InsufficientFundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implemented controller for AccountGUI
 * @author Gavin
 */
public class AccountGUIController extends GeneralController implements AccountGUIControl {

    private Account selectedAccount;
    private AccountGUI view;

    public AccountGUIController(Account selectedAccount) {
        this(selectedAccount, null);
    }

    public AccountGUIController(Account selectedAccount, AccountGUI view) {
        super();
        this.selectedAccount = selectedAccount;
        this.view = view;
    }
    
    public void register(AccountGUI view) {
        this.view = view;
    }

    public ActionListener addSubmitActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (view.getDepositRadio().isSelected()) {
                        depositAction();
                    } else {
                        withdrawAction();
                    }
                    view.getAmountTextField().setText("0.00");
                } catch (NumberFormatException | InValidInputException e) {
                    AccountGUIController.errorMessageBox(e.getMessage());
                } catch (InsufficientFundException e) {
                    AccountGUIController.errorMessageBox(e.getMessage()
                            + "\nMind that the transaction fee also counts.");
                }
            }
        };
    }

    private void depositAction() throws
            NumberFormatException, InValidInputException {
        double amount = this.getAmount();
        view.getSelectedAccount().deposit(amount);
        this.updateDetailArea();
    }

    private void withdrawAction() throws
            NumberFormatException, InValidInputException, InsufficientFundException {
        double amount = this.getAmount();
        this.selectedAccount.withdraw(amount);
        this.updateDetailArea();

    }

    private double getAmount() throws NumberFormatException {
        String content = view.getAmountTextField().getText().trim();
        return Double.parseDouble(content);
    }

    private void updateDetailArea() {
        view.getDetailArea().setText(this.selectedAccount.accountDetail());
    }
}
