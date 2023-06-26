package com.example.carrental;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PageUser extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

//    ADD
    private DatabaseReference database;
    private RecyclerView viewAdmin;
    private RecyclerViewAdapterUser adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> listUser;
    DBHelper mydb;
    User user;

    Button btnLogOut;
    TextView txtNamaUser;
    LinearLayout viewUser;

    public PageUser() {
    }

    public static PageUser newInstance(String param1, String param2) {
        PageUser fragment = new PageUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_page_admin, container, false);

        viewAdmin = (RecyclerView) view.findViewById(R.id.viewAdmin);
        viewAdmin.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        viewAdmin.setLayoutManager(layoutManager);
        listUser = new ArrayList<>();

        btnLogOut = view.findViewById(R.id.btnLogOut);
        txtNamaUser = view.findViewById(R.id.txtNamaUser);
        viewUser = view.findViewById(R.id.viewUser);


        mydb = new DBHelper(getContext());
        Cursor db = mydb.getUser("1");
        if(db != null) {
            db.moveToFirst();
            String id = db.getString(db.getColumnIndex("id"));
            String nama = db.getString(db.getColumnIndex("nama"));
            String nomor = db.getString(db.getColumnIndex("nomor"));
            String alamat = db.getString(db.getColumnIndex("alamat"));
            String password = db.getString(db.getColumnIndex("password"));
            String status = db.getString(db.getColumnIndex("status"));

            user = new User(id, nama, nomor, alamat, password, status);
        }

        if(user.getStatus().equals("admin")){
            viewUser.setVisibility(View.GONE);
            database = FirebaseDatabase.getInstance().getReference("user");
             database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                        User user = noteDataSnapshot.getValue(User.class);
                        listUser.add(user);
                    }
                    adapter = new RecyclerViewAdapterUser(listUser, getContext(), database);
                    viewAdmin.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getDetails() + "" + error.getMessage());
                }
            });
        } else {
            viewAdmin.setVisibility(View.GONE);
            txtNamaUser.setText(user.getNama());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User("","", "", "", "", "");
                Intent intent = new Intent(getActivity(), PageLogin.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}