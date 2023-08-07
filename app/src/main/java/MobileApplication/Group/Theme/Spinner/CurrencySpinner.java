package MobileApplication.Group.Theme.Spinner;

/**
 * The CurrencySpinner class represents a currency item with its country name and flag icon.
 */
public class CurrencySpinner {

    /**
     * The country name of the currency.
     */
    String countryName;
    /**
     * The resource ID of the country's flag icon.
     */
    int countryFlag;
    /**
     * Constructor for the CurrencySpinner class.
     * @param countryName The name of the country associated with the currency.
     * @param countryFlag The resource ID of the country's flag icon representing the currency.
     */
    public CurrencySpinner(String countryName, int countryFlag){
        this.countryFlag = countryFlag;
        this.countryName = countryName;
    }

    /**
     * Get the resource ID of the country's flag icon.
     * @return The resource ID of the country's flag icon.
     */
    public int getCountryFlag() {
        return countryFlag;
    }

    /**
     * Set the resource ID of the country's flag icon.
     * @param countryFlag The resource ID of the country's flag icon.
     */
    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    /**
     * Get the name of the country associated with the currency.
     * @return The name of the country.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set the name of the country associated with the currency.
     * @param countryName The name of the country.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}