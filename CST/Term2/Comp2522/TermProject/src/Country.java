/**
 * Represents a country with its name, capital city, and facts. This class is used in the
 * Word Game to store country information.
 *
 * @author Jiarui Xing
 */
public class Country {
    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    /**
     * Constructs a Country with the given name, capital city, and facts.
     *
     * @param name            the name of the country
     * @param capitalCityName the capital city of the country
     * @param facts           an array of facts about the country
     */
    public Country(String name, String capitalCityName, String[] facts)
    {
        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    /**
     * Gets the name of the country.
     *
     * @return the country name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the capital city name of the country.
     *
     * @return the capital city name
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Gets the array of facts about the country.
     *
     * @return the facts array
     */
    public String[] getFacts()
    {
        return facts;
    }
}
