package com.example.pengolahandatakaryawan.MainMenuFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.example.pengolahandatakaryawan.MenambahData;
import com.example.pengolahandatakaryawan.R;

import java.util.ArrayList;

public class DataFragment extends Fragment {

    Button nambah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        nambah = view.findViewById(R.id.nambahKaryawan);
        nambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MenambahData.class));
            }
        });
        return view;
    }
}