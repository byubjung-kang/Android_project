package MobileApplication.Group.Theme.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import MobileApplication.Group.R;

public class CurrencyAdapter  extends ArrayAdapter<CurrencySpinner> {

    public CurrencyAdapter(Context context, ArrayList<CurrencySpinner> countryList) {
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_spinner, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.imageFlag);
        TextView textViewName = convertView.findViewById(R.id.textViewCountryName);

        CurrencySpinner currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getCountryFlag());
            textViewName.setText(currentItem.getCountryName());
        }

        return convertView;
    }

}