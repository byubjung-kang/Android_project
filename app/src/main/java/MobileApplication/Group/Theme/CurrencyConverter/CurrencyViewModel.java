package MobileApplication.Group.Theme.CurrencyConverter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.Theme.Spinner.CurrencySpinner;


public class CurrencyViewModel extends ViewModel {

    public MutableLiveData<ArrayList<CurrencySpinner>> countrySpinnerList = new MutableLiveData< >();
    public MutableLiveData<ArrayList<ConversionHistory>> conversionHistoryList = new MutableLiveData< >();

}
