package sample;

/**
 * Country
 *
 * Class intended to represent a Country and its coronavirus statistics
 */
public class Country {
    String name;
    private int totalActive;
    private int totalConfirmed;
    private int newConfirmed;
    private int totalRecovered;
    private int newRecovered;
    private int totalDeaths;
    private int newDeaths;

    /**
     * Constructor for the Country class
     *
     * @param name String
     * @param totalConfirmed int
     * @param newConfirmed int
     * @param totalRecovered int
     * @param newRecovered int
     * @param totalDeaths int
     * @param newDeaths int
     */
    public Country(String name, int totalConfirmed, int newConfirmed, int totalRecovered, int newRecovered, int totalDeaths, int newDeaths) {
        this.name = name;
        this.totalConfirmed = totalConfirmed;
        this.newConfirmed = newConfirmed;
        this.totalRecovered = totalRecovered;
        this.newRecovered = newRecovered;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;

        // Calculate the total number of active cases
        this.totalActive = totalConfirmed - (totalRecovered + totalDeaths);
    }

    /**
     * Returns the name of the country
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total number of confirmed cases in the country
     *
     * @return int totalConfirmed
     */
    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    /**
     * Returns the total number of active cases in the country
     *
     * @return int totalActive
     */
    public int getTotalActive() {
        return totalActive;
    }

    /**
     * Returns the number of new cases in the country
     *
     * @return int newConfirmed
     */
    public int getNewConfirmed() {
        return newConfirmed;
    }

    /**
     * Returns the total number of total recovered cases in the country
     *
     * @return int totalRecovered
     */
    public int getTotalRecovered() {
        return totalRecovered;
    }

    /**
     * Returns the total number of new recovered cases in the country
     *
     * @return int newRecovered
     */
    public int getNewRecovered() {
        return newRecovered;
    }

    /**
     * Returns the total number of deaths in the country
     *
     * @return int totalDeaths
     */
    public int getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * Returns the number of new deaths in the country
     *
     * @return int newDeaths
     */
    public int getNewDeaths() {
        return newDeaths;
    }

    /**
     * Set the name of the country
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the total confirmed cases for the country
     *
     * @param totalConfirmed int
     */
    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    /**
     * Set the new confirmed cases for the country
     *
     * @param newConfirmed int
     */
    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed= newConfirmed;
    }

    /**
     * Set the total recovered cases for the country
     *
     * @param totalRecovered int
     */
    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    /**
     * Set the new recovered cases for the country
     *
     * @param newRecovered int
     */
    public void setNewRecovered(int newRecovered) {
        this.newRecovered = newRecovered;
    }

    /**
     * Set the total deaths for the country
     *
     * @param totalDeaths int
     */
    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    /**
     * Set the new deaths for the country
     *
     * @param newDeaths int
     */
    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }
}
