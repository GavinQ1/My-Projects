package account;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Create an Account
 * @author Gavin
 */
public class Account {
    protected String name;
    protected double balance;
    protected LocalDateTime createdTime; // record system time when the account is created
    protected Date createdDate; // record the date the account is created
    protected static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /*
     * Create an Account with Name, and Balance, 
     * created date and time are System Time
     * @param name
     * @param balance
     */
    public Account(String name, double balance) {
        this(name, balance, LocalDateTime.now());
    }
    
    /**
     * Create an Account with Name, and Balance, created time is assigned
     * @param name
     * @param balance
     * @param createdTime
     */
    public Account(String name, double balance, LocalDateTime createdTime) {
        this.name = name;
        this.balance = balance;
        this.createdTime = createdTime;
        this.createdDate = Account.convertDate(this.createdTime);
    }
    
    /**
     * Create an Account with Name, and Balance, created date is assigned
     * @param name
     * @param balance
     * @param createdDate
     */
    public Account(String name, double balance, Date createdDate) {
        this.name = name;
        this.balance = balance;
        this.createdDate = createdDate;
        this.createdTime = LocalDateTime.ofInstant(this.createdDate.toInstant(),
                                                    ZoneId.systemDefault());
    }
    
    /**
     * Operation on balance according to type of operation
     * @param amount
     * @param increase
     * @return 
     */
    private double newBalanceAmount(double amount, boolean increase) {
        if (amount < 0)
            throw new InValidInputException("The input should be non-negative.");
        if (increase) 
            return this.balance + amount;
        else {
            double newAmount = this.balance - amount;
            if (newAmount < 0)
                throw new InsufficientFundException("Not enough fund to be withdrawn.");
            return newAmount;
        }
    }
    
    /**
     * Withdraw an amount of money
     * @param amount
     * @throws InValidInputException
     * @throws InsufficientFundException
     */
    public void withdraw(double amount) 
            throws InValidInputException, InsufficientFundException {
        double newAmount = newBalanceAmount(amount, false);
        this.balance = newAmount;
    }
    
    /**
     * Deposit an amount of money
     * @param amount
     * @throws InValidInputException 
     */
    public void deposit(double amount) throws InValidInputException {
        double newAmount = newBalanceAmount(amount, true);
        this.balance = newAmount;
    }
    
    /**
     * Change name
     * @param newName
     */
    public void changeNameTo(String newName) {
        this.name = newName;
    }
    
    /**
     * Get name
     * @return String name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get current balance
     * @return double balance
     */
    public double getBalance() {
        return this.balance;
    }
    
    /**
     * Get created date
     * @return 
     */
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    /**
     * Get created time
     * @return 
     */
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }
    
    /**
     * Get balance in $0.00 format
     * @return String balance in dollar format
     */
    public String balanceString() {
        return String.format("$ %.2f", this.balance);
    }
    
    /**
     * Print balance in dollar format
     */
    public void printBalance() {
        System.out.println(this.balanceString());
    }
    
    /**
     * Get the account details
     * @return String account details
     */
    public String accountDetail() {
        return "This account was created at: \n      " + 
                this.createdTime.format(DATETIME_FORMATTER) +
                "\n\nName: " + this.name + 
                "\n\nBalance: " + this.balanceString();
    }
    
    /**
     * Convert LocalDateTime instance to Date instance, neglect hour, minute, second
     * @param time
     * @return Date 
     */
    protected static Date convertDate(LocalDateTime time) {
        return new Date(time.getYear()-1900, time.getMonthValue()-1, time.getDayOfMonth());
    }
    
    /**
     * Brief of account
     * @return String brief of account
     */
    public String toString() {
        return "Name: " + this.name + ", Balance: " + this.balanceString() +
                ", Created Date: " + this.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    // unit test
    public static void main(String[] args) {
        Account a = new Account("Alice", 0);
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
    }
    
}
