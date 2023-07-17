package MobileApplication.Group.Theme;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import Data.BearImage;

public class BearImageViewModel extends ViewModel {
    public MutableLiveData<ArrayList<BearImage>> messages = new MutableLiveData< >();
    public MutableLiveData<BearImage> selectedMessage = new MutableLiveData<Data.BearImage>();
}
