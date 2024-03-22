package com.mariano.araullobooks.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mariano.araullobooks.Globals;
import com.mariano.araullobooks.R;
import com.mariano.araullobooks.databinding.FragmentHomeBinding;

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



