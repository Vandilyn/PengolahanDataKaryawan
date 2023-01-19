package com.example.pengolahandatakaryawan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;


public class LaporanAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {

    String ingfo;

    public LaporanAdapter (Context context, List<Map.Entry<String, Integer>> entryList, String info) {
        super (context, R.layout.laporan, entryList);
        this.ingfo = info;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView != null ? convertView : LayoutInflater.from (getContext ()).inflate (R.layout.laporan, parent, false);
        Map.Entry<String, Integer> currentEntry = this.getItem (position);
        TextView departemen = currentItemView.findViewById (R.id.jumlahPerDepartemen);
        if(ingfo=="Departemen"){
            departemen.setText("Jumlah karyawan dari departemen " + currentEntry.getKey() + ": " + currentEntry.getValue() + " orang");
        }else if(ingfo=="Divisi"){
            departemen.setText("Jumlah karyawan dari divisi " + currentEntry.getKey() + ": " + currentEntry.getValue() + " orang");
        }

        return currentItemView;
    }
}