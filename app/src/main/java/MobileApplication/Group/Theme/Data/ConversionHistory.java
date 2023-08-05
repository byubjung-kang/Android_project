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
    /**
     * The @PrimaryKey annotation specifies the primary key for the database table.
     * The @ColumnInfo annotation is used to specify additional details about a column in the database table.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    /**
     * The @ColumnInfo annotation for the `currency` field specifies the name of the column in the database table, which is "currency".
     */
    @ColumnInfo(name = "currency")
    public
    String currency;

    /**
     * The @ColumnInfo annotation for the `currency2` field specifies the name of the column in the database table, which is "currency2".
     */
    @ColumnInfo(name = "currency2")
    public
    String currency2;

    /**
     * The @ColumnInfo annotation for the `amount` field specifies the name of the column in the database table, which is "amount".
     */
    @ColumnInfo(name = "amount")
    public
    double amount;

    /**
     * The @ColumnInfo annotation for the `amount2` field specifies the name of the column in the database table, which is "amount2".
     */
    @ColumnInfo(name = "amount2")
    public
    double amount2;

    /**
     * Empty constructor required by Room for database operations.
     */
    public ConversionHistory() {
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