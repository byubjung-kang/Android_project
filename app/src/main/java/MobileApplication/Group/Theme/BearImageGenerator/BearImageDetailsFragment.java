package MobileApplication.Group.Theme.BearImageGenerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import Data.BearImageGeneratorData.BearImage;
import MobileApplication.Group.databinding.DetailsBearImageLayoutBinding;

public class BearImageDetailsFragment extends Fragment {

    BearImage selected;

    public BearImageDetailsFragment(BearImage m) {
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsBearImageLayoutBinding binding = DetailsBearImageLayoutBinding.inflate(inflater);

        binding.databaseText.setText( "Width = " + selected.getWidth() + " Height = " + selected.getHeight());

        String url = selected.getUrl();
        Picasso.get().load(url).into(binding.imageView2);

        return binding.getRoot();
    }
}

