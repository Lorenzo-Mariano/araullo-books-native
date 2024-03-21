package com.mariano.araullobooks.ui.developers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mariano.araullobooks.databinding.FragmentDevelopersBinding;

public class DevelopersFragment extends Fragment {

    private FragmentDevelopersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DevelopersViewModel aboutViewModel =
                new ViewModelProvider(this).get(DevelopersViewModel.class);

        binding = FragmentDevelopersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}