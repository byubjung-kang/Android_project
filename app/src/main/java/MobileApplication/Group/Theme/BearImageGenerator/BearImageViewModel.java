package MobileApplication.Group.Theme.BearImageGenerator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import Data.BearImageGeneratorData.BearImage;

public class BearImageViewModel extends ViewModel {
    public MutableLiveData<ArrayList<BearImage>> messages = new MutableLiveData< >();
    public MutableLiveData<BearImage> selectedMessage = new MutableLiveData<BearImage>();
}
