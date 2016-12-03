package account;

import java.util.Date;
import java.util.Calendar;
import java.time.LocalDateTime;

/**
 * Create a Saving Account
 * @author Gavin
 */
public class SavingAccount extends Account {
    private static double dailyInterest = 0.0001;
    private Calendar settlementCalendar;
    
    /**
     * Create a saving account, default daily interest rate is %0.01
     * @param name
     * @param balance 
     */
    public SavingAccount(String name, double balance) {
        super(name, balance);
        this.settlementCalendar = Calendar.getInstance();
        this.settlementCalendar.setTime(this.createdDate);
    }
    
    /**
     * Create a saving account with created date assigned
     * @param name
     * @param balance
     * @param createdDate 
     */
    public SavingAccount(String name, double balance, Date createdDate) {
        super(name, balance, createdDate);
        this.settlementCalendar = Calendar.getInstance();
        this.settlementCalendar.setTime(this.createdDate);
    }

    private void dailySettle(Date today) {
        Date lastSettlementDate = this.settlementCalendar.getTime();
        while (lastSettlementDate.before(today)) {
            this.balance = this.balance * (1 + SavingAccount.dailyInterest);
            this.settlementCalendar.add(Calendar.DATE, 1);
            lastSettlementDate = this.settlementCalendar.getTime();
        }
    }
    
    /**
     * Settle an account with daily interest
     */
    public void dailySettle() {
        this.dailySettle(SavingAccount.convertDate(LocalDateTime.now()));
    }
    
    /**
     * Set Daily Interest Rate
     * @param interest 
     */
    public static void setDailyInterest(double interest) {
        SavingAccount.dailyInterest = interest;
    }
    
    /**
     * 
     * @return Daily Interest Rate
     */
    public static double getDailyInterest() {
        return SavingAccount.dailyInterest;
    }
    
    /**
     * Saving account details
     * @return 
     */
    @Override
    public String accountDetail() {
        return super.accountDetail() +
                "\n\nAccount Type: Saving" +
                String.format("\n\nDaily Interest Rate: %.2f%%", 
                        SavingAccount.dailyInterest * 100) +
                "\n\nTransaction Fee: Free";
    }
     
    /**
     * Brief of saving account  
     * @return 
     */
    @Override
     public String toString() {
        return "Saving         Account: ------ " + super.toString();
    }
     
    // unit test
    public static void main(String[] args) {
        SavingAccount a = new SavingAccount("Alice", 0);
        System.out.println(a);
        a.deposit(100); // test deposit()
        a.printBalance();
        a.withdraw(1); // test withdraw()
        a.printBalance();
        a.dailySettle(new Date(2017-1900, 1, 1)); // test settle()  date is 2017-1-1 
        a.printBalance();
    }
}
