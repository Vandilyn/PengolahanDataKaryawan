package com.example.pengolahandatakaryawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    Button saveBtn;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String id, jenisKelamin;
    EditText editNIK, editNama, editDepartemen, editDivisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveBtn = findViewById(R.id.saveBtn);
        editNIK = findViewById(R.id.nikField);
        editNama = findViewById(R.id.nameField);
        editDepartemen = findViewById(R.id.departemenField);
        editDivisi = findViewById(R.id.divisiField);

        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getStringExtra("id");
            editNIK.setText(intent.getStringExtra("nik"));
            editNama.setText(intent.getStringExtra("nama"));
            editDepartemen.setText(intent.getStringExtra("departemen"));
            editDivisi.setText(intent.getStringExtra("divisi"));
            jenisKelamin = intent.getStringExtra("jenisKelamin");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(editNIK.getText().toString(),editNama.getText().toString(),editDepartemen.getText().toString(),editDivisi.getText().toString(),jenisKelamin);
            }
        });
    }

    void saveData(String nik, String nama, String departemen, String divisi, String jenisKelamin){
        Map<String, Object> karyawan = new HashMap<>();
        karyawan.put("nik",nik);
        karyawan.put("nama",nama);
        karyawan.put("departemen",departemen);
        karyawan.put("divisi",divisi);
        karyawan.put("jenisKelamin",jenisKelamin);

        if(!id.isEmpty()){
            database.collection("Karyawan").document(id).set(karyawan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(EditActivity.this, "Berhasil edit data", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else{
                        Toast.makeText(EditActivity.this, "Gagal edit data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}