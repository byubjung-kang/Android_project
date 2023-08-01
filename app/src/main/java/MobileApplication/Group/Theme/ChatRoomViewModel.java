package MobileApplication.Group.Theme;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Flight>> messages = new MutableLiveData< >();
   // public MutableLiveData<FlightMessage> selectedMessage = new MutableLiveData< >();
}
