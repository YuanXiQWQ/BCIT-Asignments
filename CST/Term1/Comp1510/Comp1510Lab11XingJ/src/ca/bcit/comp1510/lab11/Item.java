package ca.bcit.comp1510.lab11;

/**
 * Represents an item in a shopping cart.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Item {
    /**
     * The name of the item.
     */
    private final String name;

    /**
     * The price of the item.
     */
    private final double price;

    /**
     * The quantity of the item.
     */
    private final int quantity;

    /**
     * Constructor with name, price, and quantity.
     *
     * @param name     the name.
     * @param price    the price.
     * @param quantity the quantity.
     */
    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Constructs with only name and price.
     *
     * @param name  the name.
     * @param price the price.
     */
    public Item(String name, double price) {
        this(name, price, 1);
    }

    /**
     * Getter of name.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of price.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter of quantity.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * ToString method.
     *
     * @return string representing the item
     */
    @Override
    public String toString() {
        return String.format("%s: $%.2f x%d", name, price, quantity);
    }
}
