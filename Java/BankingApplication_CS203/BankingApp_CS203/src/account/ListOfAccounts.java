package account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Create a list holding accounts
 * @author Gavin
 */
public class ListOfAccounts implements Iterable<Account> {
    private ArrayList<Account> accounts;
    private HashSet<String> checkingAccounts, savingAccounts; // avoid name collision
    
    
    /**
     * Initialize with an empty list of accounts
     */
    public ListOfAccounts() {
        this.accounts = new ArrayList<Account>();
        this.checkingAccounts = new HashSet<String>();
        this.savingAccounts = new HashSet<String>();
    }
    
    /**
     * Initialize with a list of accounts
     * @param accounts 
     */
    public ListOfAccounts(ArrayList<Account> accounts) {
        this();
        
        for (Account a : accounts) {
            try {
                this.addAccount(a);
            } catch(InValidInputException e) {
                System.out.println(e.getMessage()); // ignore but log the name collisions
            }
        }
    }
    
    /**
     * Add an account
     * @param a
     * @throws InValidInputException 
     */
    public void addAccount(Account a) throws InValidInputException {
        if (!this.contains(a)) 
            this.accounts.add(a);
    }
    
    /**
     * Test if the name is taken by someone else in this type of account
     * @param a
     * @return boolean
     * @throws InValidInputException 
     */
    public boolean contains(Account a) throws InValidInputException { // tested in the main but not in JUnit for this method throws exception
        if (a instanceof CheckingAccount) 
            return contains(a.getName(), this.checkingAccounts);
        else
            return contains(a.getName(), this.savingAccounts);
    }
    
    private boolean contains(String name, HashSet<String> scope) {
        if (scope.contains(name)) 
            throw new InValidInputException("The name \"" + name + "\" is already taken!");
        scope.add(name);
        return false;
    } 
    
    /**
     * Add a new checking account
     * @param name
     * @param balance 
     */
    public void addCheckingAccount(String name, double balance) throws InValidInputException {
        this.addAccount(new CheckingAccount(name, balance));
    }
    
    /**
     * Add a new saving account
     * @param name
     * @param balance 
     */
    public void addSavingAccount(String name, double balance) throws InValidInputException {
        this.addAccount(new SavingAccount(name, balance));
    }
    
    /**
     * Add a new checking account with date assigned
     * @param name
     * @param balance 
     * @param createdDate 
     */
    public void addCheckingAccount(String name, double balance, Date createdDate) throws InValidInputException {
        this.addAccount(new CheckingAccount(name, balance, createdDate));
    }
    
    /**
     * Add a new saving account with date assigned
     * @param name
     * @param balance 
     * @param createdDate 
     */
    public void addSavingAccount(String name, double balance, Date createdDate) throws InValidInputException {
        this.addAccount(new SavingAccount(name, balance, createdDate));
    }
    
    public Iterator<Account> iterator() {
        return this.accounts.iterator();
    }
    
    /**
     * Return account list
     * @return 
     */
    public ArrayList<Account> getAccountList() {
        return this.accounts;
    }
    
    /**
     * Return account list of assigned account type
     * @param accountType
     * @return 
     */
    public ArrayList<Account> getAccountList(Class accountType) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        for (int i = 0; i < this.accounts.size(); i++) {
            Account a = this.accounts.get(i);
            if (accountType.isInstance(a))
                accounts.add(a);
        }
        return accounts;
    }
    
    /**
     * Return checking account list
     * @return 
     */
    public ArrayList<CheckingAccount> getCheckingAccountList() {
        return this.getTypeAccountList(CheckingAccount.class);
    }
    
    /**
     * Return saving account list
     * @return 
     */
    public ArrayList<SavingAccount> getSavingAccountList() {
        return this.getTypeAccountList(SavingAccount.class);
    }
    
    /**
     * Settle all saving account
     */
    public void dailySettleSavingAccounts() {
        for (SavingAccount a : getSavingAccountList())
            a.dailySettle();
    }
    
    private <T> ArrayList<T> getTypeAccountList(Class<T> accountType) {
        ArrayList<T> accounts = new ArrayList<T>();
        for (int i = 0; i < this.accounts.size(); i++) {
            T a = (T) this.accounts.get(i);
            if (accountType.isInstance(a))
                accounts.add(a);
        }
        return accounts;
    }
    
    // unit test
    public static void main(String[] args) {
        ListOfAccounts accounts = new ListOfAccounts();
        accounts.addCheckingAccount("Alice", 0);
        accounts.addSavingAccount("Alice", 0);
        try { // test contains()
            accounts.addSavingAccount("Alice", 0);
        } catch(InValidInputException e) {
            System.out.println(e.getMessage());
        }
        for (Account a: accounts)
            System.out.println(a);
    }
}
