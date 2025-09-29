import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Bank} and {@link BankAccount} classes.
 *
 * <p>This test class verifies the functionalities of the {@code Bank} and
 * {@code BankAccount} classes, ensuring that account operations such as deposit,
 * withdrawal, transfer, and account management within the bank are functioning
 * correctly.</p>
 *
 * <p>The tests cover both standard operations and edge cases, including exception
 * handling for invalid operations.</p>
 *
 * <p>Test Scenarios Include:</p>
 * <ul>
 *     <li>Depositing and withdrawing funds</li>
 *     <li>Handling insufficient funds during withdrawal</li>
 *     <li>Adding and retrieving accounts from the bank</li>
 *     <li>Transferring funds between accounts</li>
 *     <li>Calculating total balance across all accounts</li>
 *     <li>Handling invalid account retrieval and duplicate account additions</li>
 *     <li>Validating initial account balances and negative transaction amounts</li>
 * </ul>
 *
 * <p>Example Usage:</p>
 * <pre>{@code
 * BankApplicationTests tests = new BankApplicationTests();
 * tests.setUp();
 * tests.depositIncreasesBalanceAndVerify();
 * // Additional test methods can be invoked similarly.
 * }</pre>
 *
 * <p>Note: This test class assumes that the {@code Bank} and {@code BankAccount}
 * classes are
 * correctly implemented according to the specified requirements.</p>
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.2
 */
public class BankApplicationTests {
    private Bank bank1;
    private Bank bank2;
    private BankAccount account1;
    private BankAccount account2;

    /**
     * Sets up the test environment by initializing two banks and two bank accounts.
     *
     * <p>Each bank is initialized with a unique account:
     * <ul>
     *     <li>{@code bank1} contains {@code account1} with ID "12345" and balance 1000
     *     USD.</li>
     *     <li>{@code bank2} contains {@code account2} with ID "67890" and balance 500
     *     USD.</li>
     * </ul>
     * </p>
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
     * Tests that depositing funds correctly increases the account balance.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>Depositing 200 USD into {@code account1} results in a balance of 1200
     *     USD.</li>
     *     <li>Depositing 300 USD into {@code account2} results in a balance of 800 USD
     *     .</li>
     * </ul>
     * </p>
     */
    @Test
    void depositIncreasesBalanceAndVerify()
    {
        account1.deposit(200);
        assertEquals(1200, account1.getBalanceUsd(),
                "Account1 balance should be 1200 after deposit.");

        account2.deposit(300);
        assertEquals(800, account2.getBalanceUsd(),
                "Account2 balance should be 800 after deposit.");
    }

    /**
     * Tests that withdrawing funds correctly decreases the account balance.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>Withdrawing 200 USD from {@code account1} results in a balance of 800
     *     USD.</li>
     *     <li>Withdrawing 100 USD from {@code account2} results in a balance of 400
     *     USD.</li>
     * </ul>
     * </p>
     */
    @Test
    void withdrawDecreasesBalanceAndVerify()
    {
        account1.withdraw(200);
        assertEquals(800, account1.getBalanceUsd(),
                "Account1 balance should be 800 after withdrawal.");

        account2.withdraw(100);
        assertEquals(400, account2.getBalanceUsd(),
                "Account2 balance should be 400 after withdrawal.");
    }

    /**
     * Tests that withdrawing more than the available balance throws an
     * {@code IllegalArgumentException}.
     *
     * <p>This test verifies that attempting to withdraw:
     * <ul>
     *     <li>1200 USD from {@code account1} (which has 1000 USD) throws an exception
     *     with the message "Insufficient funds".</li>
     *     <li>600 USD from {@code account2} (which has 500 USD) throws an exception
     *     with the message "Insufficient funds".</li>
     * </ul>
     * </p>
     */
    @Test
    void cannotWithdrawMoreThanBalanceAndHandleException()
    {
        final IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.withdraw(1200),
                        "Withdrawing more than balance should throw " +
                                "IllegalArgumentException.");
        assertEquals("Insufficient funds", exception1.getMessage(),
                "Exception message should be 'Insufficient funds'.");

        final IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                        () -> account2.withdraw(600),
                        "Withdrawing more than balance should throw " +
                                "IllegalArgumentException.");
        assertEquals("Insufficient funds", exception2.getMessage(),
                "Exception message should be 'Insufficient funds'.");
    }

    /**
     * Tests adding new accounts to the bank and retrieving them by their IDs.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>Adding {@code newAccount} with ID "54321" to {@code bank2} allows it to
     *     be retrieved correctly.</li>
     *     <li>Adding {@code newAccount2} with ID "11111" to {@code bank1} allows it to
     *     be retrieved correctly.</li>
     * </ul>
     * </p>
     */
    @Test
    void addingAndRetrievingAccountFromBank()
    {
        final BankAccount newAccount = new BankAccount("54321", 100);
        bank2.addAccount(newAccount);
        assertEquals(newAccount, bank2.retrieveAccount("54321"),
                "Retrieved account should match the added account.");

        final BankAccount newAccount2 = new BankAccount("11111", 300);
        bank1.addAccount(newAccount2);
        assertEquals(newAccount2, bank1.retrieveAccount("11111"),
                "Retrieved account should match the added account.");
    }

    /**
     * Tests transferring funds between bank accounts and verifies the resulting
     * balances.
     *
     * <p>This test performs the following transfers:
     * <ul>
     *     <li>Transfers 200 USD from {@code account1} to {@code account2}, resulting
     *     in {@code account1} having 800 USD and {@code account2} having 700 USD.</li>
     *     <li>Transfers 100 USD from {@code account2} to {@code account1}, resulting
     *     in {@code account1} having 900 USD and {@code account2} having 600 USD.</li>
     * </ul>
     * </p>
     */
    @Test
    void transferBetweenBankAccountsAndVerifyBalances()
    {
        account1.transferToBank(account2, "12345", 200);
        assertEquals(800, account1.getBalanceUsd(),
                "Account1 balance should be 800 after transferring 200 USD.");
        assertEquals(700, account2.getBalanceUsd(),
                "Account2 balance should be 700 after receiving 200 USD.");

        account2.transferToBank(account1, "67890", 100);
        assertEquals(900, account1.getBalanceUsd(),
                "Account1 balance should be 900 after receiving 100 USD.");
        assertEquals(600, account2.getBalanceUsd(),
                "Account2 balance should be 600 after transferring 100 USD.");
    }

    /**
     * Tests calculating the total balance of all accounts within each bank.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>The total balance of {@code bank1} is initially 1000 USD.</li>
     *     <li>The total balance of {@code bank2} is initially 500 USD.</li>
     *     <li>After adding a new account with 200 USD to {@code bank1}, the total
     *     balance becomes 1500 USD.</li>
     * </ul>
     * </p>
     */
    @Test
    void totalBalanceCalculationForBanks()
    {
        assertEquals(1000, bank1.totalBalanceUsd(),
                "Initial total balance of bank1 should be 1000 USD.");
        assertEquals(500, bank2.totalBalanceUsd(),
                "Initial total balance of bank2 should be 500 USD.");

        bank1.addAccount(new BankAccount("33333", 500));
        assertEquals(1500, bank1.totalBalanceUsd(),
                "Total balance of bank1 should be 1500 USD after adding a new account.");
    }

    /**
     * Tests handling of invalid account retrieval by verifying that retrieving
     * non-existent accounts throws exceptions.
     *
     * <p>This test verifies that attempting to retrieve:
     * <ul>
     *     <li>Account with ID "99999" from {@code bank1} throws an exception with the
     *     message "Account not found".</li>
     *     <li>Account with ID "00000" from {@code bank2} throws an exception with the
     *     message "Account not found".</li>
     * </ul>
     * </p>
     */
    @Test
    void handlingInvalidAccountRetrieval()
    {
        final IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class,
                        () -> bank1.retrieveAccount("99999"),
                        "Retrieving a non-existent account should throw " +
                                "IllegalArgumentException.");
        assertEquals("Account not found", exception1.getMessage(),
                "Exception message should be 'Account not found'.");

        final IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class,
                        () -> bank2.retrieveAccount("00000"),
                        "Retrieving a non-existent account should throw " +
                                "IllegalArgumentException.");
        assertEquals("Account not found", exception2.getMessage(),
                "Exception message should be 'Account not found'.");
    }

    /**
     * Tests that the initial balances of the accounts are set correctly upon creation.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>{@code account1} has an initial balance of 1000 USD.</li>
     *     <li>{@code account2} has an initial balance of 500 USD.</li>
     * </ul>
     * </p>
     */
    @Test
    void initialBalanceIsSetCorrectly()
    {
        assertEquals(1000, account1.getBalanceUsd(),
                "Initial balance of account1 should be 1000 USD.");
        assertEquals(500, account2.getBalanceUsd(),
                "Initial balance of account2 should be 500 USD.");
    }

    /**
     * Tests that depositing a negative amount throws an
     * {@code IllegalArgumentException}.
     *
     * <p>This test verifies that attempting to deposit:
     * <ul>
     *     <li>-100 USD into {@code account1} throws an exception with the message
     *     "Deposit amount must be positive".</li>
     * </ul>
     * </p>
     */
    @Test
    void depositNegativeAmountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.deposit(-100),
                        "Depositing a negative amount should throw " +
                                "IllegalArgumentException.");
        assertEquals("Deposit amount must be positive", exception.getMessage(),
                "Exception message should be 'Deposit amount must be positive'.");
    }

    /**
     * Tests that withdrawing a negative amount throws an
     * {@code IllegalArgumentException}.
     *
     * <p>This test verifies that attempting to withdraw:
     * <ul>
     *     <li>-100 USD from {@code account1} throws an exception with the message
     *     "Withdrawal amount must be positive".</li>
     * </ul>
     * </p>
     */
    @Test
    void withdrawNegativeAmountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.withdraw(-100),
                        "Withdrawing a negative amount should throw " +
                                "IllegalArgumentException.");
        assertEquals("Withdrawal amount must be positive", exception.getMessage(),
                "Exception message should be 'Withdrawal amount must be positive'.");
    }

    /**
     * Tests that adding an account with a duplicate ID throws an
     * {@code IllegalArgumentException}.
     *
     * <p>This test verifies that attempting to add:
     * <ul>
     *     <li>An account with ID "12345" to {@code bank1} when an account with the
     *     same ID already exists throws an exception with the message "Account with ID
     *     12345 already exists".</li>
     * </ul>
     * </p>
     */
    @Test
    void addingDuplicateAccountThrowsException()
    {
        final BankAccount duplicateAccount = new BankAccount("12345", 300);
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> bank1.addAccount(duplicateAccount),
                        "Adding a duplicate account should throw " +
                                "IllegalArgumentException.");
        assertEquals("Account with ID 12345 already exists", exception.getMessage(),
                "Exception message should indicate duplicate account ID.");
    }

    /**
     * Tests that transferring funds to a non-existent destination account throws an
     * {@code IllegalArgumentException}.
     *
     * <p>This test verifies that attempting to transfer:
     * <ul>
     *     <li>100 USD from {@code account1} to a {@code null} destination account
     *     throws an exception with the message "Destination account not found".</li>
     * </ul>
     * </p>
     */
    @Test
    void transferToNonExistentAccountThrowsException()
    {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> account1.transferToBank(null, "12345", 100),
                        "Transferring to a non-existent (null) account should throw " +
                                "IllegalArgumentException.");
        assertEquals("Destination account cannot be null", exception.getMessage(),
                "Exception message should be 'Destination account not found'.");
    }

    /**
     * Tests the total balance calculation after performing multiple deposit and
     * withdrawal operations.
     *
     * <p>This test performs the following operations:
     * <ul>
     *     <li>Deposits 500 USD into {@code account1}, resulting in a balance of 1500
     *     USD.</li>
     *     <li>Withdraws 200 USD from {@code account1}, resulting in a balance of 1300
     *     USD.</li>
     *     <li>Deposits 300 USD into {@code account2}, resulting in a balance of 800
     *     USD.</li>
     *     <li>Withdraws 100 USD from {@code account2}, resulting in a balance of 700
     *     USD.</li>
     *     <li>Adds a new account with ID "33333" and balance 200 USD to {@code bank1},
     *     resulting in a total balance of 1500 USD.</li>
     * </ul>
     * </p>
     *
     * <p>It verifies that:
     * <ul>
     *     <li>The total balance of {@code bank1} is 1500 USD.</li>
     *     <li>The total balance of {@code bank2} is 700 USD.</li>
     * </ul>
     * </p>
     */
    @Test
    void totalBalanceAfterMultipleOperations()
    {
        // account1: 1000 + 500 = 1500
        account1.deposit(500);
        // account1: 1500 - 200 = 1300
        account1.withdraw(200);
        // account2: 500 + 300 = 800
        account2.deposit(300);
        // account2: 800 - 100 = 700
        account2.withdraw(100);
        bank1.addAccount(new BankAccount("33333", 200));

        assertEquals(1500, bank1.totalBalanceUsd(),
                "Total balance of bank1 should be 1500 USD after multiple operations.");
        assertEquals(700, bank2.totalBalanceUsd(),
                "Total balance of bank2 should be 700 USD after multiple operations.");
    }

    /**
     * Tests that retrieving an account returns the correct instance.
     *
     * <p>This test verifies that:
     * <ul>
     *     <li>Retrieving account with ID "12345" from {@code bank1} returns the exact
     *     {@code account1} instance.</li>
     *     <li>Retrieving account with ID "67890" from {@code bank2} returns the exact
     *     {@code account2} instance.</li>
     * </ul>
     * </p>
     */
    @Test
    void retrieveAccountReturnsCorrectInstance()
    {
        final BankAccount retrievedAccount1 = bank1.retrieveAccount("12345");
        final BankAccount retrievedAccount2 = bank2.retrieveAccount("67890");

        assertSame(account1, retrievedAccount1,
                "Retrieved account1 instance should be the same as the original " +
                        "account1.");
        assertSame(account2, retrievedAccount2,
                "Retrieved account2 instance should be the same as the original " +
                        "account2.");
    }
}
