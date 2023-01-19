package com.example.pengolahandatakaryawan;

import static com.example.pengolahandatakaryawan.MainMenuFragment.DataFragment.departemenMap;
import static com.example.pengolahandatakaryawan.MainMenuFragment.DataFragment.divisiMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LaporanAkhir extends AppCompatActivity {

    String totalSemua, totalPria, totalWanita;
    TextView iTotal, iPria, iWanita;
    ListView departemenList, divisiList;
    LaporanAdapter adapter, adapter2;
    public static ArrayList ArrayListDepartemen, ArrayListDivisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_akhir);

        iTotal = findViewById(R.id.jumlahTotal);
        iPria = findViewById(R.id.jumlahPria);
        iWanita = findViewById(R.id.jumlahWanita);
        departemenList = findViewById(R.id.lvDepartemen);
        divisiList = findViewById(R.id.lvDivisi);

        ArrayListDepartemen = new ArrayList<>(departemenMap.entrySet());
        ArrayListDivisi = new ArrayList<>(divisiMap.entrySet());

        adapter = new LaporanAdapter(this, ArrayListDepartemen, "Departemen");
        adapter2 = new LaporanAdapter(this, ArrayListDivisi, "Divisi");

        departemenList.setAdapter(adapter);
        divisiList.setAdapter(adapter2);

        Intent intent = getIntent();

        long total = intent.getLongExtra("Total",0);
        long pria = intent.getLongExtra("Pria",0);
        long wanita = intent.getLongExtra("Wanita",0);

        totalSemua = "Total semua karyawan ada: " + total;
        totalPria = "Total semua karyawan pria ada: " + pria;
        totalWanita = "Total semua karyawan wanita ada: " + wanita;

        iTotal.setText(totalSemua);
        iPria.setText(totalPria);
        iWanita.setText(totalWanita);
    }

}