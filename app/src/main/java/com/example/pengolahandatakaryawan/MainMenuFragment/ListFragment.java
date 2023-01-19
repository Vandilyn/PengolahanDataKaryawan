package com.example.pengolahandatakaryawan.MainMenuFragment;

import static com.example.pengolahandatakaryawan.LaporanAkhir.ArrayListDepartemen;
import static com.example.pengolahandatakaryawan.MainMenuFragment.DataFragment.departemenMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pengolahandatakaryawan.Data.Karyawan;
import com.example.pengolahandatakaryawan.EditActivity;
import com.example.pengolahandatakaryawan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    static ArrayList<Karyawan> karyawanList;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    recyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);;
        if(karyawanList==null) karyawanList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new recyclerViewAdapter(karyawanList);

        adapter.setDialog(new recyclerViewAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit karyawan", "Hapus karyawan"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                updateData(pos);
                                break;
                            case 1:
                                deleteData(karyawanList.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        recyclerView.setAdapter(adapter);
        getData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search by NIK!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                if(s.isEmpty()) getData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                if(s.isEmpty()) getData();
                return false;
            }
        });
    }

    void searchData(String query){
        database.collection("Karyawan").whereEqualTo("nik",query.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                karyawanList.clear();
                for(DocumentSnapshot document: task.getResult()){
                    Karyawan karyawan = new Karyawan(document.getString("nik"), document.getString("nama"),
                            document.getString("departemen"), document.getString("divisi"), document.getString("jenisKelamin"));
                    karyawan.setId(document.getId());
                    karyawanList.add(karyawan);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to search", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settingsId){
            Toast.makeText(getContext(), "Setting", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    void getData(){
        database.collection("Karyawan").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                karyawanList.clear();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Karyawan karyawan = new Karyawan(document.getString("nik"), document.getString("nama"),
                                document.getString("departemen"), document.getString("divisi"), document.getString("jenisKelamin"));
                        karyawan.setId(document.getId());
                        karyawanList.add(karyawan);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    void updateData(int pos){
        startActivity(new Intent(getContext(), EditActivity.class)
                .putExtra("id",karyawanList.get(pos).getId())
                .putExtra("nik", karyawanList.get(pos).getNik())
                .putExtra("nama", karyawanList.get(pos).getNama())
                .putExtra("departemen", karyawanList.get(pos).getDepartemen())
                .putExtra("divisi", karyawanList.get(pos).getDivisi())
                .putExtra("jenisKelamin", karyawanList.get(pos).getJenisKelamin()));
    }

    void deleteData(String id){
        database.collection("Karyawan").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    getData();
                }else {
                    Toast.makeText(getContext(), "Data gagal di hapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}