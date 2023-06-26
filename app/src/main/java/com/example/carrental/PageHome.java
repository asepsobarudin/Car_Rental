package com.example.carrental;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PageHome extends Fragment {
    private DatabaseReference database;
    private RecyclerView viewMobil;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mobil> listMobil;
    Button btnTambahData;

    private String mParam1;
    private String mParam2;

    DBHelper mydb;
    User user;

    public PageHome() {
    }

    public static PageHome newInstance(String param1, String param2) {
        PageHome fragment = new PageHome();
        Bundle args = new Bundle();
        args.putString(param1, param1);
        args.putString(param2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(mParam1);
            mParam2 = getArguments().getString(mParam2);
        }

        mydb = new DBHelper(getContext());
        new LoadUserDataTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page_admin, container, false);
        btnTambahData = rootView.findViewById(R.id.btnTambahData);
        btnTambahData.setVisibility(View.GONE);

        viewMobil = rootView.findViewById(R.id.viewMobil);
        viewMobil.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        viewMobil.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference("mobil");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMobil = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                    Mobil mobil = noteDataSnapshot.getValue(Mobil.class);
                    listMobil.add(mobil);
                }

                adapter = new RecyclerViewAdapter(listMobil, getContext(), database);
                viewMobil.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + " " + error.getMessage());
            }
        });

        return rootView;
    }

    private class LoadUserDataTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            Cursor db = mydb.getUser("1");
            if (db != null && db.moveToFirst()) {
                String id = db.getString(db.getColumnIndex("id"));
                String idU = db.getString(db.getColumnIndex("idU"));
                String nama = db.getString(db.getColumnIndex("nama"));
                String no = db.getString(db.getColumnIndex("nomor"));
                String alamat = db.getString(db.getColumnIndex("alamat"));
                String password = db.getString(db.getColumnIndex("password"));
                String status = db.getString(db.getColumnIndex("status"));

                return new User(idU, nama, no, alamat, password, status);
            }
            return null;
        }

        @Override
        protected void onPostExecute(User result) {
            if (result != null) {
                user = result;
                if (user.getStatus().equals("admin")) {
                    btnTambahData.setVisibility(View.VISIBLE);
                    btnTambahData.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), CreateData.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    });
                } else {
                    btnTambahData.setVisibility(View.GONE);
                }
            }
        }
    }
}
