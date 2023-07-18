package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityTriviaQuestionDatabase2Binding;

public class TriviaQuestionDatabase2 extends AppCompatActivity {

    protected ActivityTriviaQuestionDatabase2Binding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTriviaQuestionDatabase2Binding binding = ActivityTriviaQuestionDatabase2Binding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );


        Intent fromPrevious = getIntent();
        String userName = fromPrevious.getStringExtra("UserName");
        binding.uNameText2.setText("Player : " + userName);


    };

//        String stringURL = "https://opentdb.com/api.php?amount=50";


//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL,
//                null, (response) -> { },
//                (error) -> { });
//        queue.add(request);



//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        String url = "https://opentdb.com/api.php?amount=10"; //
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("results"); // "category" 대신 "results" 사용
//                    ArrayList<String> categories = new ArrayList<>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject categoryObject = jsonArray.getJSONObject(i);
//                        String category = categoryObject.getString("category"); // "name" 대신 "category" 사용
//                        categories.add(category);
//                    }
//
//
//
//                    adapter = new CategoryAdapter(categories, position -> {
//
//                        String selectedCategory = categories.get(position);
//
//
//                    });
//                    recyclerView.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
}