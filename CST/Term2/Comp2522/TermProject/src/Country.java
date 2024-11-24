/**
 * @author Jiarui Xing
 */
public class Country {
    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    public Country(String name, String capitalCityName, String[] facts)
    {
        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    public String getName()
    {
        return name;
    }

    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    public String[] getFacts()
    {
        return facts;
    }
}