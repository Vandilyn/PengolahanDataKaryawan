package com.example.pengolahandatakaryawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pengolahandatakaryawan.Data.Database;
import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenambahData extends AppCompatActivity {

    Button simpan;
    EditText namaTxt, divisiTxt, departemenTxt, nikTxt;
    RadioGroup radioGroup;
    RadioButton jenisKelamin;
    String nik,nama,departemen,divisi, kelamin;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menambah_data);

        namaTxt = findViewById(R.id.nameField);
        nikTxt = findViewById(R.id.nikField);
        departemenTxt = findViewById(R.id.departemenField);
        divisiTxt = findViewById(R.id.divisiField);
        radioGroup = findViewById(R.id.opsiKelamin);

        simpan = findViewById(R.id.saveBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
    }

    void validasi(){
        nik = nikTxt.getText().toString();
        nama = namaTxt.getText().toString();
        departemen = departemenTxt.getText().toString();
        divisi = divisiTxt.getText().toString();

        if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Pilih kelamin!", Toast.LENGTH_SHORT).show();
        }else{
            int selected = radioGroup.getCheckedRadioButtonId();
            jenisKelamin = findViewById(selected);
            kelamin = jenisKelamin.getText().toString();
        }

        if(nik.isEmpty()){
            nikTxt.setError("Masukkan NIK!");
        }else if(nama.isEmpty()){
            namaTxt.setError("Masukkan nama!");
        }else if(departemen.isEmpty()){
            departemenTxt.setError("Masukkan departemen!");
        }else if(divisi.isEmpty()) {
            divisiTxt.setError("Masukkan divisi!");
        }else if(kelamin.isEmpty()){
            Toast.makeText(this, "Pilih kelamin!", Toast.LENGTH_SHORT).show();
        }else {
            database.collection("Karyawan").add(new Karyawan(nik, nama, departemen, divisi, kelamin)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(MenambahData.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MenambahData.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}