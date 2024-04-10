package q1;

/**
 * Represents a street address.
 * 
 * @author Lewis & Loftus 9e
 * @author BCIT
 * @version 2017
 */
public class Address {
    /** Street address. */
    private String streetAddress;
    
    /** city. */
    private String city;
    
    /** State. */
    private String state;
    
    /** Postal code, any country. */
    private String postalCode;

    /**
     * Constructs an Address object with the specified data.
     *
     * @param street Holds new streetAddress
     * @param town Holds new city
     * @param st Holds new state
     * @param code Holds new postalCode
     */
    public Address(String street, String town, String st, String code) {
        streetAddress = street;
        city = town;
        state = st;
        postalCode = code;
    }

    /**
     * Returns a description of this Address object.
     *
     * @return formatted value of streetAddress, city, state, zipCode
     */
    public String toString() {
        String result;

        result = streetAddress + "\n";
        result += city + ", " + state + "  " + postalCode;

        return result;
    }
}

