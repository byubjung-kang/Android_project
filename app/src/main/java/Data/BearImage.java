package Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BearImage {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;

    @ColumnInfo(name="width")
    String width;

    @ColumnInfo(name="height")
    String height;

    @ColumnInfo(name="url")
    String url;

    @ColumnInfo(name="SendOrReceive")
    boolean isSentButton;

    public BearImage(String w, String h, boolean sent)
    {
        width = w;
        height = h;
        url = "https://placebear.com/"+ w +"/"+ h;
        isSentButton = sent;
    }

    public BearImage()
    {}

    public String getWidth() {
        return width;
    }
    public String getHeight() {
        return height;
    }
    public String getUrl() {return url;}
    public boolean getIsSentButton() {
        return isSentButton;
    }

}
