package account;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gavin
 */
public class AccountTest {

    /**
     * Test of withdraw method, of class Account.
     */
    @Test
    public void testWithdraw() {
        System.out.println("withdraw");
        double amount = 1.0, init = 10;
        Account instance = new Account(null, init);
        instance.withdraw(amount);
        assertTrue(instance.getBalance() == init-amount);
    }

    /**
     * Test of deposit method, of class Account.
     */
    @Test
    public void testDeposit() {
        System.out.println("deposit");
        double amount = 1.0, init = 10;
        Account instance = new Account(null, init);
        instance.deposit(amount);
       assertTrue(instance.getBalance() == init+amount);
    }

    /**
     * Test of changeNameTo method, of class Account.
     */
    @Test
    public void testChangeNameTo() {
        System.out.println("changeNameTo");
        String newName = "NewName", init = "Name";
        Account instance = new Account(init, 0);
        instance.changeNameTo(newName);
        assertEquals(instance.getName(), newName);
    }

    /**
     * Test of getName method, of class Account.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String init = "Name";
        Account instance = new Account(init, 0);
        String expResult = init;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBalance method, of class Account.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        double init = 10;
        Account instance = new Account(null, init);
        double expResult = init;
        double result = instance.getBalance();
        assertTrue(expResult == result);
    }

    /**
     * Test of balanceString method, of class Account.
     */
    @Test
    public void testBalanceString() {
        System.out.println("balanceString");
        double init = 10;
        Account instance = new Account(null, init);
        String expResult = String.format("$ %.2f", init);
        String result = instance.balanceString();
        assertEquals(expResult, result);
    }

    /**
     * Test of convertDate method, of class Account.
     */
    @Test
    public void testConvertDate() {
        System.out.println("convertDate");
        Date expResult = new Date(2016-1900, 10, 30);
        LocalDateTime time = LocalDateTime.ofInstant(expResult.toInstant(), ZoneId.systemDefault());
        Date result = Account.convertDate(time);
        assertTrue(expResult.getTime() == result.getTime());
    }

    /**
     * Test of getCreatedDate method, of class Account.
     */
    @Test
    public void testGetCreatedDate() {
        System.out.println("getCreatedDate");
        Date init = new Date(0, 1, 1);
        Account instance = new Account(null, 0, init);
        Date expResult = init;
        Date result = instance.getCreatedDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreatedTime method, of class Account.
     */
    @Test
    public void testGetCreatedTime() {
        System.out.println("getCreatedTime");
        LocalDateTime init = LocalDateTime.now();
        Account instance = new Account(null, 0, init);
        LocalDateTime expResult = init;
        LocalDateTime result = instance.getCreatedTime();
        assertEquals(expResult, result);
    }
}
