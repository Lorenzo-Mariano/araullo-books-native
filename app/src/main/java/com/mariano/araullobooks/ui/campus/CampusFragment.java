package com.mariano.araullobooks.ui.campus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mariano.araullobooks.databinding.FragmentCampusBinding;

public class CampusFragment extends Fragment {

    private FragmentCampusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CampusViewModel campusViewModel =
                new ViewModelProvider(this).get(CampusViewModel.class);

        binding = FragmentCampusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}