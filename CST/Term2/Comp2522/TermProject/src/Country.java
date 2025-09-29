/**
 * Represents a country with its name, capital city, and facts. This class is used in the
 * Word Game to store country information.
 *
 * @author Jiarui Xing
 */
public record Country(String name, String capitalCityName, String[] facts) {
    /**
     * Constructs a Country with the given name, capital city, and facts.
     *
     * @param name            the name of the country
     * @param capitalCityName the capital city of the country
     * @param facts           an array of facts about the country
     */
    public Country
    {
    }

    /**
     * Gets the name of the country.
     *
     * @return the country name
     */
    @Override
    public String name()
    {
        return name;
    }

    /**
     * Gets the capital city name of the country.
     *
     * @return the capital city name
     */
    @Override
    public String capitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Gets the array of facts about the country.
     *
     * @return the facts array
     */
    @Override
    public String[] facts()
    {
        return facts;
    }
}
