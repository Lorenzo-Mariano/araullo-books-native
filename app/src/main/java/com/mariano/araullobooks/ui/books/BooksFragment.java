package com.mariano.araullobooks.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mariano.araullobooks.databinding.FragmentBooksBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class BooksFragment extends Fragment {

    private FragmentBooksBinding binding;
    private RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BooksViewModel booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);

        binding = FragmentBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Instantiate the RequestQueue
        requestQueue = Volley.newRequestQueue(getContext());

        // Call method to fetch books data
        fetchBooksData();

        return root;
    }

    private void fetchBooksData() {
        String url = "https://book-borrowing-system.000webhostapp.com/getAllBooks.php";

        // Request a JSON response from the provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Create a LinearLayout to hold the TextViews
                            LinearLayout linearLayout = binding.linearLayout; // Assuming you have a LinearLayout with id "linearLayout"

                            // Iterate over each JSON object in the array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bookObject = response.getJSONObject(i);

                                // Iterate over each attribute in the JSON object
                                Iterator<String> keys = bookObject.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    String value = bookObject.getString(key);

                                    // Create a new TextView
                                    TextView textView = new TextView(getContext());
                                    textView.setText(key + ": " + value);

                                    // Add the TextView to the LinearLayout
                                    linearLayout.addView(textView);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(getContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
