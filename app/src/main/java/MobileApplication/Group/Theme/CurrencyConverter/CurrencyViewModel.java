package MobileApplication.Group.Theme.CurrencyConverter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.Theme.Spinner.CurrencySpinner;


public class CurrencyViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ConversionHistory>> conversions = new MutableLiveData< >();
    public MutableLiveData<ConversionHistory> selectedConversion = new MutableLiveData< >();
}
