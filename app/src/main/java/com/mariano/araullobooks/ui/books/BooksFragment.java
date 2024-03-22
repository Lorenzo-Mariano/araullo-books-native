package com.mariano.araullobooks.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mariano.araullobooks.Globals;
import com.mariano.araullobooks.databinding.FragmentBooksBinding;

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

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Globals globals = Globals.getInstance();

                            LinearLayout linearLayout = binding.availableBooksList; // Assuming you have a LinearLayout with id "linearLayout"
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bookObject = response.getJSONObject(i);

                                // Iterate over each attribute in the JSON object
                                Iterator<String> keys = bookObject.keys();

                                // if the user is not logged in, I want the last attribute
                                // to have margin bottom
                                int attributeCount = 0;
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    if (bookObject.has(key)) {
                                        String value = bookObject.getString(key);

                                        // Create a new TextView
                                        TextView textView = new TextView(getContext());
                                        textView.setText(value);

                                        // Add the TextView to the LinearLayout
                                        linearLayout.addView(textView);

                                        attributeCount++;

                                        if (!globals.isLoggedIn() && !keys.hasNext()) {
                                            // Add margin bottom to the last TextView
                                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                                            layoutParams.setMargins(0, 0, 0, 50); // Add bottom margin
                                            textView.setLayoutParams(layoutParams);
                                        }
                                    }
                                }

                                if (globals.isLoggedIn()) {
                                    Button borrowButton = new Button(getContext());
                                    borrowButton.setText("Borrow");

                                    borrowButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                                            String url = "https://book-borrowing-system.000webhostapp.com/borrowBook.php";

                                            // Create a request to send form data to the server
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            // Handle the response from the server
                                                            try {
                                                                JSONObject jsonResponse = new JSONObject(response);
                                                                boolean success = jsonResponse.getBoolean("success");
                                                                String message = jsonResponse.getString("message");
                                                                if (success) {
                                                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                                    // Perform any additional actions after successful borrowing
                                                                } else {
                                                                    Toast.makeText(getContext(), "Failed to borrow book: " + message, Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                Toast.makeText(getContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    // Define your form parameters here
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("userId", String.valueOf(globals.getUserId()));
                                                    params.put("bookId", String.valueOf(bookObject.optInt("book_id")));
                                                    return params;
                                                }
                                                @Override
                                                public Map<String, String> getHeaders() {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                                    return params;
                                                }
                                            };

                                            // Add the request to the request queue
                                            requestQueue.add(stringRequest);
                                        }
                                    });


                                    // Add margin to the borrow button to create space between text views and button
                                    LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    buttonLayoutParams.setMargins(0, 0, 0, 50); // Add bottom margin
                                    borrowButton.setLayoutParams(buttonLayoutParams);

                                    // Add the Button to the LinearLayout
                                    linearLayout.addView(borrowButton);
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
