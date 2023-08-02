package Data.BearImageGeneratorData;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Jeonghyeon Lee
 * @version 1.0
 */

/**
 * This is a BearImage object that contains information about a bear image
 */
@Entity
public class BearImage {
    /**
     * The unique id for the BearImage, auto-generated.
     */
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;

    /**
     * The width of the bear image.
     */
    @ColumnInfo(name="width")
    String width;

    /**
     * The height of the bear image.
     */
    @ColumnInfo(name="height")
    String height;

    /**
     * The URL of the bear image.
     */
    @ColumnInfo(name="url")
    String url;

    /**
     * Indicates whether the bear image is associated with a sent button.
     */
    @ColumnInfo(name="SendOrReceive")
    boolean isSentButton;

    /**
     * Constructs a new BearImage object with the given width and height.
     *
     * @param w The width of the bear image.
     * @param h The height of the bear image.
     */
    public BearImage(String w, String h)
    {
        width = w;
        height = h;
        url = "https://placebear.com/"+ w +"/"+ h;
    }

    /**
     * Default constructor for the BearImage class.
     * Creates a new BearImage object with default values for the fields.
     */
    public BearImage()
    {}

    /**
     * Retrieves the width of the bear image.
     *
     * @return The width of the bear image as a string.
     */
    public String getWidth() {
        return width;
    }
    /**
     * Retrieves the height of the bear image.
     *
     * @return The height of the bear image as a string.
     */
    public String getHeight() {
        return height;
    }
    /**
     * Retrieves the URL of the bear image.
     *
     * @return The URL of the bear image.
     */
    public String getUrl() {return url;}

    /**
     * Checks whether the bear image is associated with a sent button.
     *
     * @return True if the bear image is associated with a sent button, false otherwise.
     */
    public boolean getIsSentButton() {
        return isSentButton;
    }

}
