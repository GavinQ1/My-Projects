package account;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gavin
 */
public class CheckingAccountTest {
    
    /**
     * Test of withdraw method, of class CheckingAccount.
     */
    @Test
    public void testWithdraw() {
        System.out.println("withdraw");
        double amount = 1.0, init = 10;
        CheckingAccount instance = new CheckingAccount(null, init);
        instance.withdraw(amount);
        assertTrue(instance.getBalance() == init-amount-CheckingAccount.getTransactionFee());
    }

    /**
     * Test of getTransactionFee method, of class CheckingAccount.
     */
    @Test
    public void testGetTransactionFee() {
        System.out.println("getTransactionFee");
        double expResult = CheckingAccount.getTransactionFee();
        double result = CheckingAccount.getTransactionFee();
        assertTrue(expResult == result);
    }

    /**
     * Test of setTransactionFee method, of class CheckingAccount.
     */
    @Test
    public void testSetTransactionFee() {
        System.out.println("setTransactionFee");
        double fee = 0.0;
        CheckingAccount.setTransactionFee(fee);
        assertTrue(fee == CheckingAccount.getTransactionFee());
    }
}
