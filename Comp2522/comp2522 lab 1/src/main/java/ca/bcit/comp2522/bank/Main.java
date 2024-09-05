package ca.bcit.comp2522.bank;

/**
 * The main class.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        // Albert Einstein
        Name einsteinName = new Name("Albert", "Einstein");
        Date einsteinBirth = new Date(1879, 3, 14);
        Date einsteinDeath = new Date(1955, 4, 18);
        BankClient einstein =
                new BankClient(einsteinName, einsteinBirth, einsteinDeath, "123456",
                        new Date(1900, 1, 1));
        BankAccount einsteinAccount =
                new BankAccount(einstein, 1000.00, 3141, "abc123", new Date(1900, 1, 1));
        einsteinAccount.withdraw(100);
        einsteinAccount.closeAccount(new Date(1950, 10, 14));
        System.out.println(einstein.getName().getInitials());
        System.out.println(einstein.getName().getFullName());
        System.out.println(einstein.getName().getReverseName());
        System.out.println(einstein.getDetails());
        System.out.println(einsteinAccount.getDetails());

        // Nelson Mandela
        Name mandelaName = new Name("Nelson", "Mandela");
        Date mandelaBirth = new Date(1918, 7, 18);
        Date mandelaDeath = new Date(2013, 12, 5);
        BankClient mandela =
                new BankClient(mandelaName, mandelaBirth, mandelaDeath, "654321",
                        new Date(1994, 5, 10));
        BankAccount mandelaAccount =
                new BankAccount(mandela, 2000.00, 4664, "654321", new Date(1994, 5, 10));
        mandelaAccount.withdraw(200);
        System.out.println(mandela.getName().getInitials());
        System.out.println(mandela.getName().getFullName());
        System.out.println(mandela.getName().getReverseName());
        System.out.println(mandela.getDetails());
        System.out.println(mandelaAccount.getDetails());

        // Frida Kahlo
        Name kahloName = new Name("Frida", "Kahlo");
        Date kahloBirth = new Date(1907, 7, 6);
        Date kahloDeath = new Date(1954, 7, 13);
        BankClient kahlo = new BankClient(kahloName, kahloBirth, kahloDeath, "frd123",
                new Date(1940, 1, 1));
        BankAccount kahloAccount =
                new BankAccount(kahlo, 500.00, 1907, "frd123", new Date(1940, 1, 1));
        kahloAccount.withdraw(50);
        kahloAccount.closeAccount(new Date(1954, 7, 13));
        System.out.println(kahlo.getName().getInitials());
        System.out.println(kahlo.getName().getFullName());
        System.out.println(kahlo.getName().getReverseName());
        System.out.println(kahlo.getDetails());
        System.out.println(kahloAccount.getDetails());

        // Jackie Chan
        Name chanName = new Name("Jackie", "Chan");
        Date chanBirth = new Date(1954, 4, 7);
        BankClient chan = new BankClient(chanName, chanBirth, null, "chan789",
                new Date(1980, 10, 1));
        BankAccount chanAccount =
                new BankAccount(chan, 3000.00, 1954, "chan789", new Date(1980, 10, 1));
        chanAccount.withdraw(500);
        System.out.println(chan.getName().getInitials());
        System.out.println(chan.getName().getFullName());
        System.out.println(chan.getName().getReverseName());
        System.out.println(chan.getDetails());
        System.out.println(chanAccount.getDetails());
    }
}