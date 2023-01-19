package com.example.pengolahandatakaryawan.MainMenuFragment;

import static com.example.pengolahandatakaryawan.MainMenuFragment.ListFragment.karyawanList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.example.pengolahandatakaryawan.LaporanAkhir;
import com.example.pengolahandatakaryawan.MenambahData;
import com.example.pengolahandatakaryawan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DataFragment extends Fragment {

    Button nambah, report;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference karyawan;
    long total, pria, wanita;
    public static HashMap<String, Integer> departemenMap = new HashMap<String, Integer>();
    public static HashMap<String, Integer> divisiMap = new HashMap<String, Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        nambah = view.findViewById(R.id.nambahKaryawan);
        report = view.findViewById(R.id.membuatReport);
        karyawan = database.collection("Karyawan");
        sendReport();

        nambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MenambahData.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departemenMap.clear();
                divisiMap.clear();
                sendReport();
                startActivity(new Intent(getContext(), LaporanAkhir.class)
                        .putExtra("Total",total)
                        .putExtra("Pria",pria)
                        .putExtra("Wanita",wanita));
            }
        });
        return view;
    }

    void sendReport() {
        getAllTotal();
        getJenisKelaminTotal("Pria");
        getJenisKelaminTotal("Wanita");
        getDepartemen();
        getDivisi();
    }

    void getAllTotal() {
        AggregateQuery count = karyawan.count();
        count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                AggregateQuerySnapshot snapshot = task.getResult();
                total = snapshot.getCount();
            }
        });
    }

    void getJenisKelaminTotal(String s) {
        Query query = karyawan.whereEqualTo("jenisKelamin", s);
        AggregateQuery count = query.count();
        count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                AggregateQuerySnapshot snapshot = task.getResult();
                if (s.equals("Pria")) pria = snapshot.getCount();
                else if (s.equals("Wanita")) wanita = snapshot.getCount();
            }
        });
    }

    void getDepartemen() {
        for (int i = 0; i < karyawanList.size(); i++) {
            String key = karyawanList.get(i).getDepartemen();
            if (departemenMap.containsKey(key)) {
                int count = departemenMap.get(key) + 1;
                departemenMap.put(key, count);
            } else {
                departemenMap.put(key, 1);
            }
        }
    }

    void getDivisi(){
        for (int i = 0; i < karyawanList.size(); i++) {
            String key = karyawanList.get(i).getDivisi();
            if (divisiMap.containsKey(key)) {
                int count = divisiMap.get(key) + 1;
                divisiMap.put(key, count);
            } else {
                divisiMap.put(key, 1);
            }
        }
    }
}