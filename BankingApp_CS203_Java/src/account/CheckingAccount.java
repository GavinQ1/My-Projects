package account;

import java.util.Date;

/**
 * Create a Checking Account
 * @author Gavin
 */
public class CheckingAccount extends Account {
    private static double transactionFee = 0.1;
    
    /**
     * Create a checking account, default transactionFee is $0.10
     * @param name
     * @param balance 
     */
    public CheckingAccount(String name, double balance) {
        super(name, balance);
    }
    
    /**
     * Create a checking account with created date assigned
     * @param name
     * @param balance
     * @param createdDate 
     */
    public CheckingAccount(String name, double balance, Date createdDate) {
        super(name, balance, createdDate);
    }
    
    /**
     * Withdraw with a transaction fee
     * @param amount
     * @throws InValidInputException
     * @throws InsufficientFundException 
     */
    @Override
    public void withdraw (double amount) 
            throws InValidInputException, InsufficientFundException {
        if (amount != 0)
            super.withdraw(CheckingAccount.transactionFee + amount);
    }
    
    /**
     * return transaction fee
     * @return 
     */
    public static double getTransactionFee() {
        return CheckingAccount.transactionFee;
    }
    
    /**
     * Checking account detail
     * @return 
     */
    @Override
    public String accountDetail() {
        return super.accountDetail() +
                "\n\nAccount Type: Checking" +
                String.format("\n\nTransaction Fee: $ %.2f", CheckingAccount.transactionFee);
    }
    
    /**
     * Brief of checking account
     * @return 
     */
    @Override
    public String toString() {
        return "Checking    Account: ------ " + super.toString();
    }
    
    public static void setTransactionFee(double fee) {
        if (fee < 0)
            throw new InValidInputException("The input should be non-negative.");
        CheckingAccount.transactionFee = fee;
    }
    
    // unit test
    public static void main(String[] args) {
        CheckingAccount a = new CheckingAccount("Alice", 0);
        System.out.println(a);
        a.deposit(100); // test deposit()
        a.printBalance(); 
        a.withdraw(1); // test withdraw()
        a.printBalance();
        try { // test InsufficientFundException
            a.withdraw(100);
        } catch(InsufficientFundException e) {
             System.out.println(e);
        } 
        try { // test InValidInputException
            a.withdraw(-100);
        } catch(InValidInputException e) {
             System.out.println(e);
        } 
        a.printBalance();
        a.withdraw(1); // test multiple withdraw() calls
        a.printBalance();
    }
}
