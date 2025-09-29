package ca.bcit.comp1510.lab09;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is a hotel for cats.喵呜~
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class CatHotel {
    /**
     * The name of the hotel.
     */
    private final String hotelName;
    /**
     * The list of guests.
     */
    private final ArrayList<Cat> guests;

    /**
     * The constructor for the CatHotel class.
     *
     * @param hotelName name of the hotel
     */
    public CatHotel(String hotelName) {
        this.hotelName = hotelName;
        this.guests = new ArrayList<>();
    }

    /**
     * Add a guest to the list.
     *
     * @param cat the ketty guest
     */
    public void addCat(Cat cat) {
        guests.add(cat);
    }

    /**
     * Remove all guests from the list.
     */
    public void removeAllGuests() {
        guests.clear();
    }

    /**
     * Get the number of guests in the list.
     *
     * @return the number of guests
     */
    public int guestCount() {
        return guests.size();
    }

    /**
     * Remove old guests from the list.
     *
     * @param ageLimit the age limit
     *
     * @return the number of removed guests
     */
    public int removeOldGuests(int ageLimit) {
        Iterator<Cat> iterator = guests.iterator();
        int removedCount = 0;
        while (iterator.hasNext()) {
            Cat cat = iterator.next();
            if (cat.getAge() > ageLimit) {
                iterator.remove();
                removedCount++;
            }
        }
        return removedCount;
    }

    /**
     * Print the guest list.
     */
    public void printGuestList() {
        System.out.println("Guest list for " + hotelName + ":");
        for (Cat cat : guests) {
            System.out.println(cat);
        }
    }
}