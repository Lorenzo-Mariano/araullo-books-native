package com.mariano.araullobooks.ui.scholarship;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mariano.araullobooks.databinding.FragmentScholarshipBinding;

public class ScholarshipFragment extends Fragment {

    private FragmentScholarshipBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScholarshipViewModel scholarshipViewModel =
                new ViewModelProvider(this).get(ScholarshipViewModel.class);

        binding = FragmentScholarshipBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}