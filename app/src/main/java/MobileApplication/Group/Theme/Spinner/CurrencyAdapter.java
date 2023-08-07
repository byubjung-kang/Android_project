package MobileApplication.Group.Theme.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import MobileApplication.Group.R;

/**
 * The CurrencyAdapter class is an ArrayAdapter used for customizing the appearance of a Spinner widget
 * for currency selection. It extends ArrayAdapter to provide a custom view for each currency item.
 *
 */
public class CurrencyAdapter  extends ArrayAdapter<CurrencySpinner> {

    /**
     * Constructor for the CurrencyAdapter.
     * @param context    The context in which the adapter is being used.
     * @param countryList The list of CurrencySpinner objects representing currencies and their details.
     */
    public CurrencyAdapter(Context context, ArrayList<CurrencySpinner> countryList) {
        super(context, 0, countryList);
    }

    /**
     * Gets a View that displays the data at the specified position in the currency Spinner.
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    /**
     * Gets a View that displays the data in a drop-down list for the specified position in the currency Spinner.
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position in the drop-down list.
     */
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    /**
     * Initializes and returns a View to display the currency item at the specified position.
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The initialized View corresponding to the data at the specified position.
     */
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Inflate the custom layout for each currency item
            convertView = LayoutInflater.from(getContext()).inflate(
                    MobileApplication.Group.R.layout.activity_spinner, parent, false
            );
        }

        // Find the views within the custom layout
        ImageView imageViewFlag = convertView.findViewById(R.id.imageFlag);
        TextView textViewName = convertView.findViewById(R.id.textViewCountryName);

        // Get the current CurrencySpinner object at the specified position
        CurrencySpinner currentItem = getItem(position);

        // Populate the views with data from the current CurrencySpinner object
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getCountryFlag());
            textViewName.setText(currentItem.getCountryName());
        }

        return convertView;
    }
}