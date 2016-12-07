package account;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gavin
 */
public class SavingAccountTest {
    
    public SavingAccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of dailySettle method, of class SavingAccount.
     */
    @Test
    public void testDailySettle() {
        System.out.println("dailySettle");
        double init = 100;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(SavingAccount.convertDate(LocalDateTime.now()));
        cal.add(Calendar.DATE, -1);
        
        Date yesterday = cal.getTime();
        
        SavingAccount instance = new SavingAccount(null, init, yesterday);
        instance.dailySettle();
        assertTrue(instance.getBalance() == init * (1 + SavingAccount.getDailyInterest()));
    }

    /**
     * Test of setDailyInterest method, of class SavingAccount.
     */
    @Test
    public void testSetDailyInterest() {
        System.out.println("setDailyInterest");
        double interest = 1.0;
        SavingAccount.setDailyInterest(interest);
        assertTrue(interest == SavingAccount.getDailyInterest());
    }

    /**
     * Test of getDailyInterest method, of class SavingAccount.
     */
    @Test
    public void testGetDailyInterest() {
        System.out.println("getDailyInterest");
        double interest = 1.0;
        SavingAccount.setDailyInterest(interest);
        double expResult = interest;
        double result = SavingAccount.getDailyInterest();
        assertTrue(expResult == result);
    }
}
