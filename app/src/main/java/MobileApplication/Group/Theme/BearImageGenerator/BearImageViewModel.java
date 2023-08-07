package MobileApplication.Group.Theme.BearImageGenerator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import Data.BearImageGeneratorData.BearImage;
/**
 * @author Jeonghyeon Lee
 * @version 1.0
 */
/**
 * ViewModel class for the BearImageGenerator app and managing the data related to bear images.
 */
public class BearImageViewModel extends ViewModel {
    /**
     * MutableLiveData object holding an ArrayList of BearImage objects.
     */
    public MutableLiveData<ArrayList<BearImage>> images = new MutableLiveData< >();
    /**
     * MutableLiveData object holding a BearImage representing the currently selected bear image.
     */
    public MutableLiveData<BearImage> selectedImage = new MutableLiveData<BearImage>();
}
