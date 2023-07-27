package MobileApplication.Group.Theme.Data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public ConversionHistory() {
        // Empty constructor required by Room.
    }

    public ConversionHistory(String c, String c2, double a, double a2) {
        currency = c;
        currency2 = c2;
        amount = a;
        amount2 = a2;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrency2() {
        return currency2;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getAmount2() {
        return amount2;
    }
}
