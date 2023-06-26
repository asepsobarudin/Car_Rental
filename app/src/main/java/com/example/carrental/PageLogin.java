package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PageLogin extends AppCompatActivity {

    private DatabaseReference database;
    EditText edNo;
    EditText edPassword;
    Button btnLogin;
    TextView btnRegister;
    DBHelper mydb;
    private ProgressBar progressBar;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);

        edNo = findViewById(R.id.edNo);
        edPassword = findViewById(R.id.edPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String no = edNo.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                Login(no, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageLogin.this, PageRegister.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void Login(String no, String password) {
        progressBar.setVisibility(View.VISIBLE);

        mydb = new DBHelper(this);
        SQLiteDatabase db = mydb.getWritableDatabase();
        mydb.onUpgrade(db, 1, 2);

        database = FirebaseDatabase.getInstance().getReference("user");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isUserFound = false;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String id = userSnapshot.getKey();
                    String nama = userSnapshot.child("nama").getValue(String.class);
                    String userNo = userSnapshot.child("no").getValue(String.class);
                    String userAlamat = userSnapshot.child("alamat").getValue(String.class);
                    String userPassword = userSnapshot.child("password").getValue(String.class);
                    String status = userSnapshot.child("status").getValue(String.class);

                    user = new User(id, nama, userNo, userAlamat ,userPassword, status);

                    if (userNo != null && userNo.equals(no) && userPassword != null && userPassword.equals(password)) {
                        isUserFound = true;
                        break;
                    }
                }

                if (isUserFound) {
                    if (mydb.insertUser("1", user.getId(), user.getNama(), user.getNo(), user.getAlamat(), user.getPassword(), user.getStatus())) {
                        Snackbar.make(PageLogin.this.findViewById(android.R.id.content), "Login Berhasil", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(PageLogin.this, Home.class);
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Snackbar.make(PageLogin.this.findViewById(android.R.id.content), "Login gagal", Snackbar.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar.make(PageLogin.this.findViewById(android.R.id.content), "User tidak ditemukan", Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(PageLogin.this.findViewById(android.R.id.content), error.getMessage(), Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}
