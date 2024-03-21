package com.mariano.araullobooks.ui.phinma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mariano.araullobooks.databinding.FragmentPhinmaBinding;

public class PhinmaFragment extends Fragment {

    private FragmentPhinmaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhinmaViewModel phinmaViewModel =
                new ViewModelProvider(this).get(PhinmaViewModel.class);

        binding = FragmentPhinmaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}