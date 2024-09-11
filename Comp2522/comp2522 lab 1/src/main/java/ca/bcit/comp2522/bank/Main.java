package ca.bcit.comp2522.bank;

/**
 * The main class.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class Main {

    /**
     * Runs the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Albert Einstein
        Name einsteinName = new Name("Albert", "Einstein");
        Date einsteinBirth = new Date(1879, 3, 14);
        Date einsteinDeath = new Date(1955, 4, 18);
        Date einsteinOpen = new Date(1900, 1, 1);
        Date einsteinClose = new Date(1950, 10, 14);

        Person einsteinDetails = new Person(einsteinName, einsteinBirth, einsteinDeath);
        BankClient einstein =
                new BankClient(einsteinName, einsteinBirth, einsteinDeath, "123456",
                        einsteinOpen);
        BankAccount einsteinAccount =
                new BankAccount(einstein, "abc123", einsteinOpen, einsteinClose, 3141);

        einsteinAccount.deposit(1000);
        einsteinAccount.withdraw(100, 3141);
        einsteinAccount.closeAccount(new Date(1950, 10, 14));

        System.out.println(einstein.getName().getInitials());
        System.out.println(einstein.getName().getFullName());
        System.out.println(einstein.getName().getReverseName());
        System.out.println(einsteinDetails.getDetails());
        System.out.println(einsteinAccount.getDetails());

        // Nelson Mandela
        Name mandelaName = new Name("Nelson", "Mandela");
        Date mandelaBirth = new Date(1918, 7, 18);
        Date mandelaDeath = new Date(2013, 12, 5);
        Date mandelaOpen = new Date(1994, 5, 10);
        Date mandelaClose = null;

        Person mandelaDetails = new Person(mandelaName, mandelaBirth, mandelaDeath);
        BankClient mandela =
                new BankClient(mandelaName, mandelaBirth, mandelaDeath, "654321",
                        mandelaOpen);
        BankAccount mandelaAccount =
                new BankAccount(mandela, "654321", mandelaOpen, mandelaClose, 4664);

        mandelaAccount.deposit(2000);
        mandelaAccount.withdraw(200, 4664);

        System.out.println(mandela.getName().getInitials());
        System.out.println(mandela.getName().getFullName());
        System.out.println(mandela.getName().getReverseName());
        System.out.println(mandelaDetails.getDetails());
        System.out.println(mandelaAccount.getDetails());

        // Frida Kahlo
        Name kahloName = new Name("Frida", "Kahlo");
        Date kahloBirth = new Date(1907, 7, 6);
        Date kahloDeath = new Date(1954, 7, 13);
        Date kahloOpen = new Date(1940, 1, 1);
        Date kahloClose = new Date(1954, 7, 13);

        Person kahloDetails = new Person(kahloName, kahloBirth, kahloDeath);
        BankClient kahlo =
                new BankClient(kahloName, kahloBirth, kahloDeath, "frd123", kahloOpen);
        BankAccount kahloAccount =
                new BankAccount(kahlo, "frd123", kahloOpen, kahloClose, 1907);

        kahloAccount.deposit(500);
        kahloAccount.withdraw(50, 1907);
        kahloAccount.closeAccount(new Date(1954, 7, 13));

        System.out.println(kahlo.getName().getInitials());
        System.out.println(kahlo.getName().getFullName());
        System.out.println(kahlo.getName().getReverseName());
        System.out.println(kahloDetails.getDetails());
        System.out.println(kahloAccount.getDetails());

        // Jackie Chan
        Name chanName = new Name("Jackie", "Chan");
        Date chanBirth = new Date(1954, 4, 7);
        Date chanDeath = null;
        Date chanOpen = new Date(1980, 10, 1);
        Date chanClose = null;

        Person chanDetails = new Person(chanName, chanBirth, chanDeath);
        BankClient chan =
                new BankClient(chanName, chanBirth, chanDeath, "chan789", chanOpen);
        BankAccount chanAccount =
                new BankAccount(chan, "chan789", chanOpen, chanClose, 1954);

        chanAccount.deposit(3000);
        chanAccount.withdraw(500, 1954);

        System.out.println(chan.getName().getInitials());
        System.out.println(chan.getName().getFullName());
        System.out.println(chan.getName().getReverseName());
        System.out.println(chanDetails.getDetails());
        System.out.println(chanAccount.getDetails());
    }
}
