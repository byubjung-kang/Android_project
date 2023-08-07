package MobileApplication.Group.Theme.CurrencyConverter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import MobileApplication.Group.Theme.Data.ConversionHistory;


/**
 * ViewModel class for managing currency conversions and selected conversion history entry.
 * This class is responsible for holding and updating data related to currency conversions
 * and the currently selected conversion history entry.
 */
public class CurrencyViewModel extends ViewModel {

    /**
     * MutableLiveData holding the list of conversion history entries.
     * Other classes can observe this LiveData to get notified of changes to the list of conversions.
     */
    public MutableLiveData<ArrayList<ConversionHistory>> conversions = new MutableLiveData< >();
    /**
     * MutableLiveData holding the currently selected conversion history entry.
     * Other classes can observe this LiveData to get notified of changes to the selected conversion entry.
     */
    public MutableLiveData<ConversionHistory> selectedConversion = new MutableLiveData< >();
}

