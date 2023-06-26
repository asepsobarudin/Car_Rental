package com.example.carrental;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PageOrder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //    ADD
    private DatabaseReference database;
    private RecyclerView viewOrder;
    private RecyclerViewAdapterOrder adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Order> listOrder;
    DBHelper mydb;
    User user;

    public PageOrder() {
    }

    public static PageOrder newInstance(String param1, String param2) {
        PageOrder fragment = new PageOrder();
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
        View view = inflater.inflate(R.layout.fragment_order_page_admin, container, false);

        viewOrder = (RecyclerView) view.findViewById(R.id.viewOrder);
        viewOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        viewOrder.setLayoutManager(layoutManager);
        listOrder = new ArrayList<>();

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
            user = new User(id ,nama, nomor, alamat, password, status);
        }

        database = FirebaseDatabase.getInstance().getReference("order");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()) {
                    Order order = noteDataSnapshot.getValue(Order.class);
                    if(user.getStatus().equals("admin")){
                        listOrder.add(order);
                    } else {
                        String namaUser = order.getNamaUser();
                        if (namaUser.equals(user.getNama())) {
                            listOrder.add(order);
                        }
                    }
                }
                adapter = new RecyclerViewAdapterOrder(listOrder, getContext(), database);
                viewOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + "" + error.getMessage());
            }
        });

        return view;
    }
}