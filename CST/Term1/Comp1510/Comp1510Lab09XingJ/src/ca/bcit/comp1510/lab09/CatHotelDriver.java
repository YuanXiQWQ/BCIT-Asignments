package ca.bcit.comp1510.lab09;

import java.util.Random;

/**
 * The driver for the CatHotel class.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class CatHotelDriver {
    /**
     * The main method.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        // Initialise
        CatHotel catHotel = new CatHotel("Jerry's Cat Hotel");
        Random random = new Random();

        // My cat's name
        Cat myCat = new Cat("大猫", random.nextInt(2));
        // My other cat's name
        Cat myOtherCat = new Cat("小猫", random.nextInt(2));
        // Vincent's cat's name
        Cat vincentsCat = new Cat("妹妹", random.nextInt(15));

        myCat.setAge(2);
        myOtherCat.setAge(2);

        // Add some guests
        catHotel.addCat(myCat);
        catHotel.addCat(myOtherCat);
        catHotel.addCat(vincentsCat);
        // TOM!!!
        catHotel.addCat(new Cat("Tom", random.nextInt(15)));
        catHotel.addCat(new Cat("黑猫警长", random.nextInt(15)));
        catHotel.addCat(new Cat("白猫警士", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraAmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraBmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraCmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraDmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraEmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraFmon", random.nextInt(15)));
        catHotel.addCat(new Cat("DoraGmon", random.nextInt(15)));
        // Test for an empty name
        catHotel.addCat(new Cat(" ", random.nextInt(15)));

        // Print the guest list
        System.out.println("All guests before removing old guests:");
        catHotel.printGuestList();

        // Move old guests over 7 years
        int removedCats = catHotel.removeOldGuests(7);
        System.out.println(removedCats + " old guests were removed.");

        // Print the guest list
        System.out.println("All guests after removing old guests:");
        catHotel.printGuestList();

        // Remove all guests
        catHotel.removeAllGuests();
        System.out.println("All guests have been removed.");
        System.out.println("Guest count: " + catHotel.guestCount());
    }
}
