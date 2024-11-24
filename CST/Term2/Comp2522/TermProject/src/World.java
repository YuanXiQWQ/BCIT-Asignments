import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Jiarui Xing
 */
public class World {
    private final HashMap<String, Country> countries;

    public World() {
        countries = new HashMap<>();
    }

    public void loadCountries(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String name = "";
            String capital = "";
            String[] facts = new String[3];
            int factIndex = 0;

            while ((line = br.readLine()) != null) {
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    name = parts[0].trim();
                    capital = parts[1].trim();
                    factIndex = 0;
                    facts = new String[3];
                } else if (!line.trim().isEmpty()) {
                    facts[factIndex++] = line.trim();
                    if (factIndex == 3) {
                        Country country = new Country(name, capital, facts);
                        countries.put(name, country);
                    }
                }
            }

            // In case the last country does not have exactly 3 facts
            if (factIndex > 0 && factIndex <= 3) {
                Country country = new Country(name, capital, facts);
                countries.put(name, country);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }
    }

    public HashMap<String, Country> getCountries() {
        return countries;
    }
}
