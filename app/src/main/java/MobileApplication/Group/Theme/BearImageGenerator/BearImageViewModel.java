package MobileApplication.Group.Theme.BearImageGenerator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import Data.BearImageGeneratorData.BearImage;

public class BearImageViewModel extends ViewModel {
    public MutableLiveData<ArrayList<BearImage>> images = new MutableLiveData< >();
    public MutableLiveData<BearImage> selectedImage = new MutableLiveData<BearImage>();
}