package com.example.pengolahandatakaryawan.MainMenuFragment;


import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.example.pengolahandatakaryawan.R;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter <recyclerViewAdapter.ViewHolder> {

    private ArrayList<Karyawan> karyawanArrayList;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public recyclerViewAdapter(ArrayList<Karyawan> karyawanArrayList) {
        this.karyawanArrayList = karyawanArrayList;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.ViewHolder holder, int position) {
        Karyawan karyawan = karyawanArrayList.get(position);
        holder.nik.setText(karyawan.getNik());
        holder.nama.setText(karyawan.getNama());
        holder.departemen.setText(karyawan.getDepartemen());
        holder.divisi.setText(karyawan.getDivisi());
        holder.jenisKelamin.setText(karyawan.getJenisKelamin());
    }

    @Override
    public int getItemCount() {
        return karyawanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama, nik, departemen, divisi, jenisKelamin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nik = itemView.findViewById(R.id.nik);
            nama = itemView.findViewById(R.id.namaKaryawan);
            departemen = itemView.findViewById(R.id.departemen);
            divisi = itemView.findViewById(R.id.divisi);
            jenisKelamin = itemView.findViewById(R.id.jenisKelamin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
