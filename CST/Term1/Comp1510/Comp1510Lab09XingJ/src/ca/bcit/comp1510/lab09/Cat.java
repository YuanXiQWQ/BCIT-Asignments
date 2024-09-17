package ca.bcit.comp1510.lab09;

/**
 * This is the Cat class.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class Cat {
    /**
     * The name of the cat.
     */
    private final String name;
    /**
     * The age of the cat.
     */
    private int age;

    /**
     * The constructor for the Cat class.
     *
     * @param name name of the cat
     * @param age  age of the cat
     */
    public Cat(String name, int age) {
        this.name = name.isBlank() ? "Cleo" : name;
        this.age = Math.max(age, 0);
    }

    /**
     * Gets the name of the cat.
     *
     * @return the name of the cat
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of the cat.
     *
     * @return the age of the cat
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age of the cat.
     *
     * @param age the age of the cat
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the cat.
     *
     * @return a string representation of the cat
     */
    @Override
    public String toString() {
        return "Cat{" + "name='" + name + '\'' +
                ", age=" + age + '}';
    }
}
