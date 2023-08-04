package MobileApplication.Group.Theme.Data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a single conversion history entry.
 * This class is used to define the structure of the database table for storing conversion history entries.
 */
@Entity
public class ConversionHistory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "currency")
    public
    String currency;

    @ColumnInfo(name = "currency2")
    public
    String currency2;

    @ColumnInfo(name = "amount")
    public
    double amount;

    @ColumnInfo(name = "amount2")
    public
    double amount2;

    /**
     * Empty constructor required by Room for database operations.
     */public ConversionHistory() {
        // Empty constructor required by Room.
    }
    /**
     * Constructor to create a new instance of the ConversionHistory class with the provided data.
     *
     * @param c  The first currency code.
     * @param c2 The second currency code.
     * @param a  The amount in the first currency.
     * @param a2 The amount in the second currency.
     */
    public ConversionHistory(String c, String c2, double a, double a2) {
        currency = c;
        currency2 = c2;
        amount = a;
        amount2 = a2;
    }

    /**
     * Get the first currency code.
     * @return The first currency code as a String.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Get the first currency code.
     * @return The first currency code as a String.
     */
    public String getCurrency2() {
        return currency2;
    }
    /**
     * Get the amount in the first currency.
     * @return The amount in the first currency as a Double.
     */
    public Double getAmount() {
        return amount;
    }
    /**
     * Get the amount in the second currency.
     * @return The amount in the second currency as a Double.
     */
    public Double getAmount2() {
        return amount2;
    }
}