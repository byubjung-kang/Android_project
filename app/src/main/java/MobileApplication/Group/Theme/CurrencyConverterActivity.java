package MobileApplication.Group.Theme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import MobileApplication.Group.R;
import MobileApplication.Group.Theme.CurrencyConverter.ConversionHistoryAdapter;
import MobileApplication.Group.Theme.Data.ConversionHistory;
import MobileApplication.Group.Theme.Data.ConversionHistoryDAO;
import MobileApplication.Group.Theme.Data.ConversionHistoryDatabase;
import MobileApplication.Group.Theme.Spinner.CurrencyAdapter;
import MobileApplication.Group.Theme.Spinner.CurrencySpinner;
import MobileApplication.Group.databinding.ActivityMainBinding;

public class CurrencyConverterActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText inputAmount;
    private Button convertButton;
    private TextView convertedAmount;
    private CurrencySpinner spinnerTo;
    private String clickedCountryTo;
    private CurrencySpinner spinnerFrom;
    private String clickedCountryFrom;
    private ArrayList<CurrencySpinner> countrySpinnerList;
    private CurrencyAdapter cAdapter;
    private RequestQueue queue;
    private ConversionHistoryAdapter conversionHistoryAdapter;
    private ArrayList<ConversionHistory> conversionHistoryList;
   ConversionHistoryDAO myDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        Toolbar theToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(theToolbar);

        ConversionHistoryDatabase db = Room.databaseBuilder(getApplicationContext(), ConversionHistoryDatabase.class, "database-name").build();
        myDAO = db.chDAO();

        listCountry();

        inputAmount = findViewById(R.id.editTextNumberDecimal);
        convertedAmount = findViewById(R.id.textViewTo);
        convertButton = findViewById(R.id.convertBtn);

        // Retrieve the saved user input from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        inputAmount.setText(prefs.getString("InputAmount", ""));


        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        conversionHistoryList = new ArrayList<>();
        ConversionHistoryAdapter conversionHistoryAdapter = new ConversionHistoryAdapter(conversionHistoryList, myDAO);
        recyclerView.setAdapter(conversionHistoryAdapter);

        Spinner spinnerFromCountry = findViewById(R.id.spinnerFrom);
        Spinner spinnerToCountry = findViewById(R.id.spinnerTo);

        cAdapter = new CurrencyAdapter(this, countrySpinnerList);
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
            String amountEditText = inputAmount.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("InputAmount", amountEditText);

            editor.commit();


            double amount = Double.parseDouble(inputAmount.getText().toString());

            // Construct the API URL
            String apiUrl = "https://api.getgeoapi.com/v2/currency/convert?" +
                    "api_key=95e71849514e83fc857fb7364e3a26decace4de0" +
                    "&from=" + clickedCountryFrom +
                    "&to=" + clickedCountryTo +
                    "&amount=" + amount +
                    "&format=json";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                    response -> {
                        try {
                            if (response.has("rates")) {
                                JSONObject rates = response.getJSONObject("rates");

                                if (clickedCountryTo.equals("CAD")) {
                                    JSONObject cadRates = rates.getJSONObject("CAD");
                                    String cadRateForAmount = cadRates.getString("rate_for_amount");
                                    convertedAmount.setText(cadRateForAmount);
                                    Toast.makeText(CurrencyConverterActivity.this, "CAD Rate: " + cadRateForAmount, Toast.LENGTH_SHORT).show();

                                    // Create a new ConversionHistory object and add it to the list
                                    ConversionHistory conversion = new ConversionHistory(clickedCountryFrom, clickedCountryTo, amount, Double.parseDouble(cadRateForAmount));
                                    conversionHistoryList.add(conversion);

                                    // Notify the adapter that the data has changed
                                    conversionHistoryAdapter.notifyDataSetChanged();

                                    Executor thread = Executors.newSingleThreadExecutor();
                                    thread.execute(() ->{
                                        conversion.id = myDAO.insertConversion(conversion);//returns id
                                    });

                                } else if (clickedCountryTo.equals("AUD")) {
                                    JSONObject audRates = rates.getJSONObject("AUD");
                                    String audRateForAmount = audRates.getString("rate_for_amount");

                                    Toast.makeText(CurrencyConverterActivity.this, "AUD Rate: " + audRateForAmount, Toast.LENGTH_SHORT).show();

                                    convertedAmount.setText(audRateForAmount);

                                    // Create a new ConversionHistory object and add it to the list
                                    ConversionHistory conversion = new ConversionHistory(clickedCountryFrom, clickedCountryTo, amount, Double.parseDouble(audRateForAmount));
                                    conversionHistoryList.add(conversion);

                                    // Notify the adapter that the data has changed
                                    conversionHistoryAdapter.notifyDataSetChanged();

                                    //insert into database
                                    Executor thread = Executors.newSingleThreadExecutor();
                                    thread.execute(() ->{
                                        conversion.id = myDAO.insertConversion(conversion);//returns id
                                    });

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
        } else if (id == R.id.bearImage) {
            openBearImage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBearImage() {
            Intent intent = new Intent(CurrencyConverterActivity.this, BearImageGenerator.class);
            startActivity(intent);
    }


    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_dialog_title);
        builder.setMessage(R.string.help_dialog_message);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void listCountry() {
        countrySpinnerList = new ArrayList<>();
        countrySpinnerList.add(new CurrencySpinner("AUD", R.drawable.aud));
        countrySpinnerList.add(new CurrencySpinner("CAD", R.drawable.cad));
    }
}
