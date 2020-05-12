package sample;

import java.util.TreeMap;

/**
 * World
 *
 * Class used to represent the world, the countries in the world, and all of the
 * world wide statistics of the coronavirus.
 */
public class World {
    TreeMap<String, Country> countries; //TreeMap used to order countries alphabetically
    private int totalActive = 0;
    private int totalConfirmed = 0;
    private int newConfirmed = 0;
    private int totalRecovered = 0;
    private int newRecovered = 0;
    private int totalDeaths = 0;
    private int newDeaths = 0;

    /**
     * Default constructor for world class
     */
    public World() {
        this.countries = new TreeMap<String, Country>();
    }

    /**
     * Initiate a world using a pre-existing TreeMap of Countries
     *
     * @param countries TreeMap
     */
    public World(TreeMap<String, Country> countries) {
        this.countries = countries;
    }

    /**
     * Returns a TreeMap containing all of the countries
     *
     * @return TreeMap countries
     */
    public TreeMap<String, Country> getCountries() {
        return countries;
    }

    /**
     * Add country c to the world
     *
     * @param c Country
     */
    public void add(Country c) {
        countries.put(c.getName(), c);
        this.totalActive += c.getTotalActive();
        this.totalConfirmed += c.getTotalConfirmed();
        this.newConfirmed += c.getNewConfirmed();
        this.totalRecovered += c.getTotalRecovered();
        this.newRecovered += c.getNewRecovered();
        this.totalDeaths += c.getTotalDeaths();
        this.newDeaths += c.getNewDeaths();
    }

    /**
     * Returns the total number of active cases in the world
     *
     * @return int totalActive
     */
    public int getTotalActive() {
        return totalActive;
    }

    /**
     * Returns the total number of confirmed cases in the world
     *
     * @return int totalConfirmed
     */
    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    /**
     * Returns the number of new cases in the world
     *
     * @return int newConfirmed
     */
    public int getNewConfirmed() {
        return newConfirmed;
    }

    /**
     * Returns the total number of recoveries in the world
     *
     * @return int totalRecovered
     */
    public int getTotalRecovered() {
        return totalRecovered;
    }

    /**
     * Returns the total number of new recoveries in the world
     *
     * @return int newRecovered
     */
    public int getNewRecovered() {
        return newRecovered;
    }

    /**
     * Returns the total number of deaths in the world
     *
     * @return int totalDeaths
     */
    public int getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * Returns the total number of new deaths in the world
     *
     * @return int newDeaths
     */
    public int getNewDeaths() {
        return newDeaths;
    }

    /**
     * Searches for the country in the TreeMap with the String key s, and returns the corresponding country
     *
     * @param s String of the Country's name
     * @return Country
     */
    public Country getCountry(String s) {
        return countries.get(s);
    }
}
