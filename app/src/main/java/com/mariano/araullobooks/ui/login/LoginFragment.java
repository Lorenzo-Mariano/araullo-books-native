package com.mariano.araullobooks.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mariano.araullobooks.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private RequestQueue requestQueue;
    private static final String LOGIN_URL = "https://book-borrowing-system.000webhostapp.com/register.php/login.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requestQueue = Volley.newRequestQueue(requireContext());
        binding.loginButton.setOnClickListener(v -> loginUser());

        return root;
    }

    private void loginUser() {
        String firstname = binding.firstNameEditText.getText().toString().trim();
        String lastname = binding.lastNameEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter username/email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        String message = jsonResponse.getString("message");
                        if (success) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error response (e.g., network error)
                    Toast.makeText(requireContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Add login parameters to the request
                Map<String, String> params = new HashMap<>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                // Add headers if needed (e.g., Content-Type)
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Add request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
