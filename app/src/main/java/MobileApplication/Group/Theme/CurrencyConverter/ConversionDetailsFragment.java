package MobileApplication.Group.Theme.CurrencyConverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.databinding.ConversionLayoutBinding;


/**
 * A fragment that displays details of a conversion history entry.
 */
public class ConversionDetailsFragment extends Fragment {
    /**
     * ViewBinding object for layout. Allows access to the views in the layout file for this activity.
     */
    private ConversionLayoutBinding binding; // ViewBinding object for the layout
    /**
     * The selected ConversionHistory object. The `selected` variable holds the currently selected
     * `ConversionHistory` object in the RecyclerView. This object represents the currency conversion
     */
    private ConversionHistory selected; // The selected conversion history entry
    /**
     * Constructor to create a new instance of the fragment with the provided conversion history.
     * @param c The conversion history entry to display details of.
     */
    public ConversionDetailsFragment(ConversionHistory c){ selected = c;}

    /**
     * Called when the fragment's UI is created or inflated.
     * Inflates the layout and populates UI elements with data from the selected conversion history entry.
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout using ViewBinding
        binding = ConversionLayoutBinding.inflate(inflater);
        // Populate UI elements with data from the selected conversion history entry
        binding.textCurrency.setText(selected.currency);
        binding.textCurrency1.setText(selected.currency2);
        binding.textAmount.setText(String.valueOf(selected.amount));
        binding.textAmount1.setText(String.valueOf(selected.amount2));
        binding.databaseText.setText(String.valueOf(selected.id));

        return binding.getRoot();// Return the root view of the layout.
    }
}
