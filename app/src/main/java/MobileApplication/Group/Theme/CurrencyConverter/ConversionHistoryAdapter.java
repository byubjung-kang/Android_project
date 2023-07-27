package MobileApplication.Group.Theme.CurrencyConverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import MobileApplication.Group.R;
import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.Theme.Data.ConversionHistoryDAO;
import MobileApplication.Group.databinding.ItemConversionHistoryBinding;


public class ConversionHistoryAdapter extends RecyclerView.Adapter<ConversionHistoryAdapter.MyRowHolder> {

    private ArrayList<ConversionHistory> conversionHistoryList;
    private ConversionHistoryDAO myDAO;

    public ConversionHistoryAdapter(ArrayList<ConversionHistory> conversionHistoryList, ConversionHistoryDAO myDAO) {
        this.conversionHistoryList = conversionHistoryList;
    }

    @NonNull
    @Override
    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemConversionHistoryBinding itemBinding = ItemConversionHistoryBinding.inflate(inflater, parent, false);
        return new MyRowHolder(itemBinding.getRoot()); // Pass the ConversionHistoryAdapter instance
    }


    @Override
    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
        ConversionHistory conversion = conversionHistoryList.get(position);

        // Bind the data to the views in the view holder
        holder.currencyTextView.setText(conversion.getCurrency());
        holder.currency2TextView.setText(conversion.getCurrency2());
        holder.amountTextView.setText(String.valueOf(conversion.getAmount()));
        holder.amount2TextView.setText(String.valueOf(conversion.getAmount2()));
    }

    @Override
    public int getItemCount() {
        return conversionHistoryList.size();
    }

    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView currencyTextView;
        TextView currency2TextView;
        TextView amountTextView;
        TextView amount2TextView;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            currencyTextView = itemView.findViewById(R.id.currency);
            currency2TextView = itemView.findViewById(R.id.currency2);
            amountTextView = itemView.findViewById(R.id.amount);
            amount2TextView = itemView.findViewById(R.id.amount2);

            itemView.setOnClickListener(click -> {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION && position < conversionHistoryList.size()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage(R.string.delete_confirmation_message)
                            .setTitle(R.string.delete_confirmation_title)
                            .setNegativeButton(R.string.no, (dialog, which) -> {
                                // Do nothing if the user chooses not to delete
                            })
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                ConversionHistory conversion = conversionHistoryList.remove(position);
                                notifyItemRemoved(position);

                                Snackbar.make(itemView, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.undo, view -> {
                                            conversionHistoryList.add(position, conversion);
                                            notifyItemInserted(position);

                                            // Insert the deleted item back to the database
                                            myDAO.insertConversion(conversion);
                                        })
                                        .show();
                            })
                            .create()
                            .show();
                }
            });
        }
    }}