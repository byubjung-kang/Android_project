package MobileApplication.Group.Theme.CurrencyConverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.databinding.ConversionLayoutBinding;

public class ConversionDetailsFragment extends Fragment {

    ConversionLayoutBinding binding;
    ConversionHistory selected;

    public ConversionDetailsFragment(ConversionHistory c){ selected = c;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = ConversionLayoutBinding.inflate(inflater);

        binding.textCurrency.setText(selected.currency);
        binding.textCurrency1.setText(selected.currency2);
        binding.textAmount.setText(String.valueOf(selected.amount));
        binding.textAmount1.setText(String.valueOf(selected.amount2));
        binding.databaseText.setText(String.valueOf(selected.id));

        return binding.getRoot();
    }
}
