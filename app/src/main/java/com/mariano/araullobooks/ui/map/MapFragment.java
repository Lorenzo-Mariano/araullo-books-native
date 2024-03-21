package com.mariano.araullobooks.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mariano.araullobooks.databinding.FragmentMapBinding;
import com.mariano.araullobooks.R;

public class MapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        WebView webView = view.findViewById(R.id.web_map);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com/maps/place/PHINMA+Araullo+University+South/@15.4616012,120.9485521,17z/data=!3m1!4b1!4m6!3m5!1s0x3397288e7773ce57:0xdd78f815acb955f0!8m2!3d15.461596!4d120.951127!16s%2Fg%2F11cp7j4r6k?entry=ttu");
        return view;
    }
}
