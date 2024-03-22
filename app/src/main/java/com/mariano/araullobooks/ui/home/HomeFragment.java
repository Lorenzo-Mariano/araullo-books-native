package com.mariano.araullobooks.ui.home;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mariano.araullobooks.Globals;
import com.mariano.araullobooks.R;
import com.mariano.araullobooks.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private Globals globals;
    private boolean isLoggedIn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        globals = Globals.getInstance();
        isLoggedIn = globals.isLoggedIn();

        if (isLoggedIn) {
            View loggedInView = inflater.inflate(R.layout.fragment_home_logged_in, container, false);
            binding.getRoot().addView(loggedInView);

            // Construct the URL with the userId parameter
            String userId = String.valueOf(globals.getUserId());
            String url = "https://book-borrowing-system.000webhostapp.com/getBorrowedBooks.php?userId=" + userId;

            LinearLayout borrowedBooksList = root.findViewById(R.id.borrowedBooksList);
            // Create a GET request using Volley
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the JSON response here and update your UI
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                if (jsonArray.length() == 0) {
                                    TextView loggedInHomeTextView = requireView().findViewById(R.id.loggedInHomeTextView);
                                    loggedInHomeTextView.setText("You have no books. Head over to the books tab to borrow some!");
                                } else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        int bookId = jsonObject.getInt("book_id");
                                        String title = jsonObject.getString("title");
                                        String author = jsonObject.getString("author");
                                        String description = jsonObject.getString("description");

                                        TextView bookIdTextView = new TextView(getContext());
                                        TextView titleTextView = new TextView(getContext());
                                        TextView authorTextView = new TextView(getContext());
                                        TextView descriptionTextView = new TextView(getContext());

                                        bookIdTextView.setText("Book ID: " + bookId);
                                        titleTextView.setText("Title: " + title);
                                        authorTextView.setText("Author: " + author);
                                        descriptionTextView.setText("Description: " + description);

                                        borrowedBooksList.addView(bookIdTextView);
                                        borrowedBooksList.addView(titleTextView);
                                        borrowedBooksList.addView(authorTextView);
                                        borrowedBooksList.addView(descriptionTextView);

                                        Button returnButton = new Button(getContext());
                                        returnButton.setText("Return book");

                                        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        buttonLayoutParams.setMargins(0, 0, 0, 20); // Add bottom margin
                                        returnButton.setLayoutParams(buttonLayoutParams);

                                        borrowedBooksList.addView(returnButton);

                                        returnButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Globals globals = Globals.getInstance();
                                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                                String url = "https://book-borrowing-system.000webhostapp.com/returnBook.php";
                                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonResponse = new JSONObject(response);
                                                                    boolean success = jsonResponse.getBoolean("success");
                                                                    String message = jsonResponse.getString("message");

                                                                    if (success) {
                                                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    Toast.makeText(getContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // Handle Volley error
                                                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<>();
                                                        params.put("userId", String.valueOf(globals.getUserId()));
                                                        params.put("bookId", String.valueOf(bookId)); // Assuming bookId is accessible here
                                                        return params;
                                                    }
                                                };

                                                // Add the request to the request queue
                                                requestQueue.add(stringRequest);
                                            }
                                        });

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle errors in making the request
                            Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // Add the request to the Volley request queue
            Volley.newRequestQueue(requireContext()).add(stringRequest);
        } else {
            View loggedOutView = inflater.inflate(R.layout.fragment_home_logged_out, container, false);
            binding.getRoot().addView(loggedOutView);
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



