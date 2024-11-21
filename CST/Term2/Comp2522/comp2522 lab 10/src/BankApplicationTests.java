import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the BankApplication class.
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.0
 */
public class BankApplicationTests {
    private Bank bank1;
    private Bank bank2;
    private BankAccount account1;
    private BankAccount account2;

    /**
     * Sets up the banks and accounts for the tests.
     */
    @BeforeEach
    void setUp()
    {
        bank1 = new Bank();
        bank2 = new Bank();
        account1 = new BankAccount("12345", 1000);
        account2 = new BankAccount("67890", 500);
        bank1.addAccount(account1);
        bank2.addAccount(account2);
    }

    /**
     * Tests the deposit and withdraw methods.
     */
    @Test
    void depositIncreasesBalanceAndVerify()
    {
        account1.deposit(200);
        assertEquals(1200, account1.getBalanceUsd());
        account2.deposit(300);
        assertEquals(800, account2.getBalanceUsd());
    }

    /**
     * Tests the withdrawal method.
     */
    @Test
    void withdrawDecreasesBalanceAndVerify()
    {
        account1.withdraw(200);
        assertEquals(800, account1.getBalanceUsd());
        account2.withdraw(100);
        assertEquals(400, account2.getBalanceUsd());
    }

    /**
     * Tests the withdrawal method with insufficient funds.
     */
    @Test
    void cannotWithdrawMoreThanBalanceAndHandleException()
    {
        final IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.withdraw(1200));
        assertEquals("Insufficient funds", exception1.getMessage());

        final IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                        () -> account2.withdraw(600));
        assertEquals("Insufficient funds", exception2.getMessage());
    }

    /**
     * Tests the addAccount and retrieveAccount methods.
     */
    @Test
    void addingAndRetrievingAccountFromBank()
    {
        final BankAccount newAccount = new BankAccount("54321", 100);
        bank2.addAccount(newAccount);
        assertEquals(newAccount, bank2.retrieveAccount("54321"));

        final BankAccount newAccount2 = new BankAccount("11111", 300);
        bank1.addAccount(newAccount2);
        assertEquals(newAccount2, bank1.retrieveAccount("11111"));
    }

    /**
     * Tests the transferToBank method.
     */
    @Test
    void transferBetweenBankAccountsAndVerifyBalances()
    {
        account1.transferToBank(account2, "12345", 200);
        assertEquals(800, account1.getBalanceUsd());
        assertEquals(700, account2.getBalanceUsd());

        account2.transferToBank(account1, "67890", 100);
        assertEquals(900, account1.getBalanceUsd());
        assertEquals(600, account2.getBalanceUsd());
    }

    /**
     * Tests the totalBalanceUsd method.
     */
    @Test
    void totalBalanceCalculationForBanks()
    {
        assertEquals(1000, bank1.totalBalanceUsd());
        assertEquals(500, bank2.totalBalanceUsd());

        bank1.addAccount(new BankAccount("33333", 200));
        assertEquals(1200, bank1.totalBalanceUsd());
    }

    /**
     * Tests the handling of invalid account retrieval.
     */
    @Test
    void handlingInvalidAccountRetrieval()
    {
        final IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                        () -> bank1.retrieveAccount("99999"));
        assertEquals("Account not found", exception1.getMessage());

        final IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                        () -> bank2.retrieveAccount("00000"));
        assertEquals("Account not found", exception2.getMessage());
    }

    /**
     * Tests the initial balance of the accounts.
     */
    @Test
    void initialBalanceIsSetCorrectly()
    {
        assertEquals(1000, account1.getBalanceUsd());
        assertEquals(500, account2.getBalanceUsd());
    }

    /**
     * Tests the handling of negative deposit and withdrawal amounts.
     */
    @Test
    void depositNegativeAmountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.deposit(-100));
        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    /**
     * Tests the handling of negative withdrawal amounts.
     */
    @Test
    void withdrawNegativeAmountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.withdraw(-100));
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    /**
     * Tests the handling of duplicate account IDs.
     */
    @Test
    void addingDuplicateAccountThrowsException()
    {
        final BankAccount duplicateAccount = new BankAccount("12345", 300);
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> bank1.addAccount(duplicateAccount));
        assertEquals("Account with ID 12345 already exists", exception.getMessage());
    }

    /**
     * Tests the handling of non-existent destination accounts.
     */
    @Test
    void transferToNonExistentAccountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.transferToBank(null, "12345", 100));
        assertEquals("Destination account not found", exception.getMessage());
    }

    /**
     * Tests the totalBalanceUsd method after multiple operations.
     */
    @Test
    void totalBalanceAfterMultipleOperations()
    {
        account1.deposit(500);
        account1.withdraw(200);
        account2.deposit(300);
        account2.withdraw(100);
        bank1.addAccount(new BankAccount("33333", 200));

        assertEquals(1500, bank1.totalBalanceUsd());
        assertEquals(700, bank2.totalBalanceUsd());
    }

    /**
     * Tests the retrieveAccount method.
     */
    @Test
    void retrieveAccountReturnsCorrectInstance()
    {
        final BankAccount retrievedAccount1 = bank1.retrieveAccount("12345");
        final BankAccount retrievedAccount2 = bank2.retrieveAccount("67890");

        assertSame(account1, retrievedAccount1);
        assertSame(account2, retrievedAccount2);
    }
}
