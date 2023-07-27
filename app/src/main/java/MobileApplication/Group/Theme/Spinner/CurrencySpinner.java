package MobileApplication.Group.Theme.Spinner;

public class CurrencySpinner {

    String countryName;
    int countryFlag;

    public CurrencySpinner(String countryName, int countryFlag){
        this.countryFlag = countryFlag;
        this.countryName = countryName;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
