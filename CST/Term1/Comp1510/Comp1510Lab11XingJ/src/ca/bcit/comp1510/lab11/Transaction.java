package ca.bcit.comp1510.lab11;

/**
 * Manages transactions for a shopping cart.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Transaction {
    /**
     * This is your shopping cart.
     */
    private Item[] cart;

    /**
     *  The total price of all items in the cart.
     */
    private double totalPrice;

    /**
     * The count of all items in the cart.
     */
    private int itemCount;

    /**
     * Constructs with capacity.
     *
     * @param capacity the initial capacity of the cart
     */
    public Transaction(int capacity) {
        cart = new Item[capacity];
        totalPrice = 0.0;
        itemCount = 0;
    }

    /**
     * Adds an item to the cart.
     *
     * @param name     the name of the item.
     * @param price    the price of the item.
     * @param quantity the quantity of the item.
     */
    public void addToCart(String name, double price, int quantity) {
        if (itemCount >= cart.length) {
            increaseSize();
        }
        cart[itemCount] = new Item(name, price, quantity);
        totalPrice += price * quantity;
        itemCount++;
    }

    /**
     * Adds an existing item to the cart.
     *
     * @param item the item to be added
     */
    public void addToCart(Item item) {
        if (itemCount >= cart.length) {
            increaseSize();
        }
        cart[itemCount] = item;
        totalPrice += item.getPrice() * item.getQuantity();
        itemCount++;
    }

    /**
     * Increases the size of the cart by three slots.
     */
    public void increaseSize() {
        Item[] newCart = new Item[cart.length + 3];
        System.arraycopy(cart, 0, newCart, 0, cart.length);
        cart = newCart;
    }

    /**
     * Getter for total price.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Getter for total item count.
     *
     * @return the total item count
     */
    public int getCount() {
        return itemCount;
    }

    /**
     * ToString method.
     *
     * @return string representation of the transaction
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Item item : cart) {
            if (item != null) {
                result.append(item).append("\n");
            }
        }
        result.append("Total Price: $").append(String.format("%.2f", totalPrice));
        return result.toString();
    }
}
