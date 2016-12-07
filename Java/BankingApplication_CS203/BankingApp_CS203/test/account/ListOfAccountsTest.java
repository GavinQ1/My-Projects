package account;

import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gavin
 */
public class ListOfAccountsTest {

    /**
     * Test of addAccount method, of class ListOfAccounts.
     */
    @Test
    public void testAddAccount() {
        System.out.println("addAccount");
        Account a = new Account("Test", 0);
        ListOfAccounts instance = new ListOfAccounts();
        instance.addAccount(a);
        assertTrue(instance.getAccountList().contains(a));
    }

    /**
     * Test of addCheckingAccount method, of class ListOfAccounts.
     */
    @Test
    public void testAddCheckingAccount_String_double() {
        System.out.println("addCheckingAccount");
        String name = "Test";
        double balance = 0.0;
        ListOfAccounts instance = new ListOfAccounts();
        instance.addCheckingAccount(name, balance);
        boolean flag = false;
        for (Account a : instance.getAccountList()) {
            if (a.getBalance() == balance && a.getName().equals(name) && a instanceof CheckingAccount)
                flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Test of addSavingAccount method, of class ListOfAccounts.
     */
    @Test
    public void testAddSavingAccount_String_double() {
        System.out.println("addSavingAccount");
        String name = "Test";
        double balance = 0.0;
        ListOfAccounts instance = new ListOfAccounts();
        instance.addSavingAccount(name, balance);
        boolean flag = false;
        for (Account a : instance.getAccountList()) {
            if (a.getBalance() == balance && a.getName().equals(name) && a instanceof SavingAccount)
                flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Test of addCheckingAccount method, of class ListOfAccounts.
     */
    @Test
    public void testAddCheckingAccount_3args() {
        System.out.println("addCheckingAccount");
        String name = "";
        double balance = 0.0;
        Date createdDate = new Date(0, 1, 1);
        ListOfAccounts instance = new ListOfAccounts();
        instance.addCheckingAccount(name, balance, createdDate);
        boolean flag = false;
        for (Account a : instance.getAccountList()) {
            if (a.getBalance() == balance 
                    && a.getName().equals(name) 
                    && a.getCreatedDate().equals(createdDate)
                    && a instanceof CheckingAccount)
                flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Test of addSavingAccount method, of class ListOfAccounts.
     */
    @Test
    public void testAddSavingAccount_3args() {
        System.out.println("addSavingAccount");
        String name = "";
        double balance = 0.0;
        Date createdDate = new Date(0, 1, 1);
        ListOfAccounts instance = new ListOfAccounts();
        instance.addSavingAccount(name, balance, createdDate);
        boolean flag = false;
        for (Account a : instance.getAccountList()) {
            if (a.getBalance() == balance 
                    && a.getName().equals(name) 
                    && a.getCreatedDate().equals(createdDate)
                    && a instanceof SavingAccount)
                flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Test of getAccountList method, of class ListOfAccounts.
     */
    @Test
    public void testGetAccountList_0args() {
        System.out.println("getAccountList");
        ListOfAccounts instance = new ListOfAccounts();
        ArrayList<Account> expResult = new ArrayList<Account>();
        ArrayList<Account> result = instance.getAccountList();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }
}
