package ca.bcit.comp2522.bank;

/**
 * The main class, creates and prints several person and their bank accounts.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class Main {
    private static final String DIVIDER =
            "------------------------------------------------------------------";

    /**
     * Runs the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Albert Einstein
        createAndPrintAccount(
                new Name("alBeRt", "EINsTein"),
                new Date(1879, 3, 14),
                new Date(1955, 4, 18),
                "abc123",
                new Date(1900, 1, 1),
                new Date(1950, 10, 14),
                3141,
                1000,
                100
        );

        // Nelson Mandela
        createAndPrintAccount(
                new Name("Nelson", "Mandela"),
                new Date(1918, 7, 18),
                new Date(2013, 12, 5),
                "654321",
                new Date(1994, 5, 10),
                null,
                4664,
                2000,
                200
        );

        // Frida Kahlo
        createAndPrintAccount(
                new Name("Frida", "Kahlo"),
                new Date(1907, 7, 6),
                new Date(1954, 7, 13),
                "frd123",
                new Date(1940, 1, 1),
                new Date(1954, 7, 13),
                1907,
                500,
                50
        );

        // Jackie Chan
        createAndPrintAccount(
                new Name("Jackie", "Chan"),
                new Date(1954, 4, 7),
                null,
                "chan789",
                new Date(1980, 10, 1),
                null,
                1954,
                3000,
                500
        );
    }

    /**
     * Helper method to create a person, bank client, and bank account and print their
     * details.
     *
     * @param name      the person's name
     * @param birthDate the person's birthdate
     * @param deathDate the person's death date (can be null if alive)
     * @param accountId the bank account ID
     * @param openDate  the date the account was opened
     * @param closeDate the date the account was closed (can be null if still open)
     * @param pin       the account's PIN
     * @param deposit   the initial deposit amount
     * @param withdraw  the amount to withdraw
     */
    private static void createAndPrintAccount(final Name name,
                                              final Date birthDate,
                                              final Date deathDate,
                                              final String accountId,
                                              final Date openDate,
                                              final Date closeDate,
                                              final int pin,
                                              final double deposit,
                                              final double withdraw)
    {
        final Person personDetails = new Person(name, birthDate, deathDate);
        final BankClient client = new BankClient(name, birthDate, deathDate, accountId,
                openDate);
        final BankAccount account = new BankAccount(client, accountId, openDate,
                closeDate,
                pin);

        account.deposit(deposit);

        if (closeDate != null) {
            account.closeAccount(closeDate);
        }

        System.out.println(name.getInitials());
        System.out.println(name.getFullName());
        System.out.println(name.getReverseName());
        System.out.println(personDetails.getDetails());
        System.out.println(account.getDetails());
        account.withdraw(withdraw, pin);
        System.out.println(account.getDetails());
        System.out.println(DIVIDER);
    }
}
