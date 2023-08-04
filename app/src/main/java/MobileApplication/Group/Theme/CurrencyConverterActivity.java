package MobileApplication.Group.Theme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import MobileApplication.Group.R;
import MobileApplication.Group.Theme.CurrencyConverter.ConversionDetailsFragment;
import MobileApplication.Group.Theme.CurrencyConverter.CurrencyViewModel;
import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.Theme.Data.ConversionHistoryDAO;
import MobileApplication.Group.Theme.Data.ConversionHistoryDatabase;
import MobileApplication.Group.Theme.Spinner.CurrencyAdapter;
import MobileApplication.Group.Theme.Spinner.CurrencySpinner;
import MobileApplication.Group.databinding.ActivityCurrencyConverterBinding;
import MobileApplication.Group.databinding.ItemConversionHistoryBinding;

public class CurrencyConverterActivity extends AppCompatActivity {

    private ActivityCurrencyConverterBinding binding;
    private EditText inputAmount;
    private TextView convertedAmount;
    private CurrencySpinner spinnerTo;
    private String clickedCountryTo;
    private CurrencySpinner spinnerFrom;
    private String clickedCountryFrom;
    private ArrayList<CurrencySpinner> countrySpinnerList;
    private RequestQueue queue;
    private ArrayList<ConversionHistory> conversions;
    private CurrencyViewModel currencyModel;
    private RecyclerView.Adapter myAdapter;
    ConversionHistoryDAO myDAO;
    private final ArrayList<ConversionHistory> savedConversions = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currencyModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyModel.selectedConversion.observe(this, (newConversionValue) -> {
            if(newConversionValue != null) {
                ConversionDetailsFragment convFragment = new ConversionDetailsFragment(newConversionValue);

                FragmentManager fMgr = getSupportFragmentManager();

                FragmentTransaction tx = fMgr.beginTransaction();
                tx.replace(R.id.fragmentLocation, convFragment);
                tx.addToBackStack(null);
                tx.commit();
            }});
        currencyModel.conversions.observe(this, updatedConversions -> myAdapter.notifyDataSetChanged());

        currencyModel.conversions.observe(this, updatedConversions -> {
            conversions = updatedConversions;
            myAdapter.notifyDataSetChanged();
        });

        if (currencyModel.conversions.getValue() == null) {
            conversions = new ArrayList<>();
            currencyModel.conversions.setValue(conversions);
        }

        Toolbar theToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(theToolbar);

        ConversionHistoryDatabase db = Room.databaseBuilder(getApplicationContext(), ConversionHistoryDatabase.class, "database-name").build();
        myDAO = db.chDAO();

        listCountry();

        inputAmount = findViewById(R.id.editTextNumberDecimal);
        convertedAmount = findViewById(R.id.textViewTo);
        Button convertButton = findViewById(R.id.convertBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button viewAllBtn = findViewById(R.id.viewAll);

        saveBtn.setOnClickListener(v -> {
            double amount = Double.parseDouble(inputAmount.getText().toString());
            String fromCurrency = clickedCountryFrom;
            String toCurrency = clickedCountryTo;
            String convertedAmountStr = convertedAmount.getText().toString();
            double convertedAmount = Double.parseDouble(convertedAmountStr);

            // Check if the conversion is not already present in the list
            ConversionHistory existingConversion = findExistingConversion(fromCurrency, toCurrency, amount, convertedAmount);
            if (existingConversion == null) {
                // Create a new ConversionHistory object and add it to the list
                ConversionHistory conversion = new ConversionHistory(fromCurrency, toCurrency, amount, convertedAmount);

                // Set id to -1 to indicate it's not yet saved
                conversion.id = 0;

                conversions.add(conversion);

                // Notify the adapter of the data change
                myAdapter.notifyDataSetChanged();

                // Insert the conversion to the database using a background thread
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    // Update the id with the returned value from the database
                    conversion.id = myDAO.insertConversion(conversion);
                });
            } else {
                // Conversion already exists in the list
                Toast.makeText(CurrencyConverterActivity.this, "Conversion already saved.", Toast.LENGTH_SHORT).show();
            }
            saveConversionsToSharedPreferences(conversions);
        });


        viewAllBtn.setOnClickListener(v -> {
            // Show only the saved conversions (from the 'conversions' list)
            showSavedConversions();
        });

        ArrayList<ConversionHistory> savedConversions = loadConversionsFromSharedPreferences();
        if (savedConversions != null) {
            conversions.addAll(savedConversions);
        }

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        inputAmount.setText(prefs.getString("InputAmount", ""));

        Spinner spinnerFromCountry = findViewById(R.id.spinnerFrom);
        Spinner spinnerToCountry = findViewById(R.id.spinnerTo);

        CurrencyAdapter cAdapter = new CurrencyAdapter(this, countrySpinnerList);
        spinnerFromCountry.setAdapter(cAdapter);
        spinnerToCountry.setAdapter(cAdapter);

        spinnerFromCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerFrom = (CurrencySpinner) parent.getItemAtPosition(position);
                clickedCountryFrom = spinnerFrom.getCountryName();
                Toast.makeText(CurrencyConverterActivity.this, clickedCountryFrom, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerToCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTo = (CurrencySpinner) parent.getItemAtPosition(position);
                clickedCountryTo = spinnerTo.getCountryName();
                Toast.makeText(CurrencyConverterActivity.this, clickedCountryTo, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        queue = Volley.newRequestQueue(this);

        convertButton.setOnClickListener(click -> {
            String amountEditText = binding.editTextNumberDecimal.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("InputAmount", amountEditText);
            editor.apply();

            double amount = Double.parseDouble(inputAmount.getText().toString());

            // Construct the API URL
            String apiUrl = "https://api.getgeoapi.com/v2/currency/convert" +
                    "?api_key=bbfb5d67a0061d2b9ce4d7d81b229bd43b035bc6" +
                    "&from=" + clickedCountryFrom +
                    "&to=" + clickedCountryTo +
                    "&amount=" + amount +
                    "&format=json";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                    response -> {
                        try {
                            if (response.has("rates")) {
                                JSONObject rates = response.getJSONObject("rates");

                                if (clickedCountryTo.equals(clickedCountryFrom)) {
                                    // If the same country is selected for 'from' and 'to', just convert 1-to-1.
                                    double convertedValue = Double.parseDouble(inputAmount.getText().toString());
                                    String formattedAmount = String.format("%.2f", convertedValue);
                                    convertedAmount.setText(formattedAmount);
                                    Toast.makeText(CurrencyConverterActivity.this, "Conversion rate: 1.00 is to 1.00", Toast.LENGTH_SHORT).show();
                                } else if (clickedCountryTo.equals("CAD")) {
                                    JSONObject cadRates = rates.getJSONObject("CAD");
                                    double conversionRateCad = cadRates.getDouble("rate_for_amount");
                                    double convertedValueCad = amount * conversionRateCad;
                                    String formattedAmountCad = String.format("%.2f", convertedValueCad);
                                    convertedAmount.setText(formattedAmountCad);
                                    Toast.makeText(CurrencyConverterActivity.this, "CAD Rate: " + cadRates, Toast.LENGTH_SHORT).show();
                                } else if (clickedCountryTo.equals("AUD")) {
                                    JSONObject audRates = rates.getJSONObject("AUD");
                                    double conversionRateAud = audRates.getDouble("rate_for_amount");
                                    double convertedValueAud = amount * conversionRateAud;
                                    String formattedAmountAud = String.format("%.2f", convertedValueAud);
                                    convertedAmount.setText(formattedAmountAud);
                                    Toast.makeText(CurrencyConverterActivity.this, "AUD Rate: " + audRates, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    error -> {
                    });

            queue.add(request); // Add the request to the Volley RequestQueue
        });


        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View itemView;

                if (viewType == 0) {
                    ItemConversionHistoryBinding conversionBinding = ItemConversionHistoryBinding.inflate(inflater, parent, false);
                    itemView = conversionBinding.getRoot();
                } else {
                    // Throw an exception or return null to indicate that other view types are not implemented
                    throw new IllegalArgumentException("View type not implemented");
                }

                return new MyRowHolder(itemView);
            }
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ConversionHistory conversionHistory = conversions.get(position);
                holder.currencyTextView.setText(conversionHistory.getCurrency());
                holder.currency2TextView.setText(conversionHistory.getCurrency2());
                holder.amountTextView.setText(String.valueOf(conversionHistory.getAmount()));
                holder.amount2TextView.setText(String.valueOf(conversionHistory.getAmount2()));
            }

            @Override
            public int getItemCount() {
                return conversions.size();
            }
            @Override
            public int getItemViewType(int position) {
                return 0; // Return the same view type (0) for all positions
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    int num;
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

            itemView.setOnClickListener(click -> {   int position = getAbsoluteAdapterPosition();

                num = position; // Update num1 with the selected position

                ConversionHistory selected = conversions.get(position);
                currencyModel.selectedConversion.postValue(selected);
            });
        }

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.my_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.help) {
                showHelpDialog();
                return true;
            } else if (id == R.id.delete){
                int position = num;
                deleteSelectedConversion(position);
            } else if (id == R.id.bearImage) {
                openBearImage();
                return true;
            }else if (id == R.id.aviation) {
                openAviation();
                return true;
            }else if (id == R.id.trivia) {
                openTrivia();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        private void openBearImage() {
            Intent intent = new Intent(CurrencyConverterActivity.this, BearImageGenerator.class);
            startActivity(intent);
        }
        private void openAviation() {
            Intent intent = new Intent(CurrencyConverterActivity.this, AviationStackFlightTracker.class);
            startActivity(intent);
        }
        private void openTrivia() {
            Intent intent = new Intent(CurrencyConverterActivity.this, TriviaQuestionDatabase.class);
            startActivity(intent);
        }

        private void showHelpDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.help_dialog_title);
            builder.setMessage(R.string.help_dialog_message);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        }

        private void deleteSelectedConversion(int position) {
            if (position != RecyclerView.NO_POSITION) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyConverterActivity.this);
                builder.setMessage("Do you want to delete the conversion?")
                        .setTitle("Question:")
                        .setNegativeButton("No", ((dialog, cl) -> {
                        }))
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            ConversionHistory conv = conversions.get(position);

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                myDAO.deleteConversion(conv);
                                conversions.remove(position);
                                runOnUiThread(() -> myAdapter.notifyItemRemoved(position));
                            });

                            Snackbar.make(binding.recyclerView, "You deleted conversion #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", ck -> {
                                        conversions.add(position, conv);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create()
                        .show();
            }
    }

        private void listCountry() {
            countrySpinnerList = new ArrayList<>();
            countrySpinnerList.add(new CurrencySpinner("AUD", R.drawable.aud));
            countrySpinnerList.add(new CurrencySpinner("CAD", R.drawable.cad));
        }
    @SuppressLint("NotifyDataSetChanged")
    private void showSavedConversions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saved Conversions");

        // Clear the savedConversions list before populating it with saved conversions
        savedConversions.clear();

        // Loop through the conversions list and add only the saved conversions to the savedConversions list
        for (ConversionHistory conversion : conversions) {
            if (conversion.id > 0) { // Assuming positive id indicates the conversion is saved
                savedConversions.add(conversion);
            }
        }

        // Create a list of conversion strings to display in the dialog
        ArrayList<String> conversionStrings = new ArrayList<>();
        for (ConversionHistory conversion : savedConversions) {
            String conversionString = "From: " + conversion.getCurrency() + "\n" +
                    "To: " + conversion.getCurrency2() + "\n" +
                    "Amount: " + conversion.getAmount() + "\n" +
                    "Converted Amount: " + conversion.getAmount2();
            conversionStrings.add(conversionString);
        }

        // Convert the ArrayList to a String array for the dialog items
        String[] conversionArray = conversionStrings.toArray(new String[0]);

        builder.setItems(conversionArray, (dialog, which) -> {
            // Handle the item click here, which is the index 'which' of the selected conversion

            // Show a confirmation dialog to confirm the deletion
            AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
            confirmBuilder.setMessage("Do you want to delete this conversion?")
                    .setTitle("Delete Conversion:")
                    .setNegativeButton("No", ((dialog1, cl) -> {
                    }))
                    .setPositiveButton("Yes", (dialog1, cl) -> {
                        ConversionHistory conv = savedConversions.get(which);

                        // Delete the selected conversion from the database
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            myDAO.deleteConversion(conv);
                            conversions.remove(conv);
                            runOnUiThread(() -> myAdapter.notifyDataSetChanged());
                        });

                        Snackbar.make(binding.recyclerView, "You deleted conversion #" + which, Snackbar.LENGTH_LONG)
                                .setAction("Undo", ck -> {
                                    conversions.add(conv);
                                    runOnUiThread(() -> myAdapter.notifyDataSetChanged());
                                })
                                .show();
                    })
                    .create()
                    .show();
        });

        builder.create().show();
    }
    private void saveConversionsToSharedPreferences(ArrayList<ConversionHistory> conversions) {
        // Convert the list of conversions to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(conversions);

        // Save the JSON string to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SavedConversions", json);
        editor.apply();
    }

    private ArrayList<ConversionHistory> loadConversionsFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String json = prefs.getString("SavedConversions", null);

        if (json != null) {
            // Convert the JSON string back to a list of conversions
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ConversionHistory>>(){}.getType();
            return gson.fromJson(json, type);
        }

        return null; // If no saved conversions are found
    }

    private ConversionHistory findExistingConversion(String fromCurrency, String toCurrency, double amount, double convertedAmount) {
        for (ConversionHistory conversion : conversions) {
            if (conversion.getCurrency().equals(fromCurrency) &&
                    conversion.getCurrency2().equals(toCurrency) &&
                    conversion.getAmount() == amount &&
                    conversion.getAmount2() == convertedAmount) {
                return conversion;
            }
        }
        return null;
    }
}
