package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PageRegister extends AppCompatActivity {

    EditText edNama;
    EditText edNo;
    EditText edAlamat;
    EditText edPassword;
    Button btnRegister;
    TextView btnLogin;

    private DatabaseReference database;
    private ProgressBar progressBar;
    private final String status = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);

        database = FirebaseDatabase.getInstance().getReference();

        edNama = findViewById(R.id.edNama);
        edNo = findViewById(R.id.edNo);
        edAlamat = findViewById(R.id.edAlamat);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomText randomText = new RandomText();

                String No = "" + randomText.getRandomNumberInRange(1, 100);
                String cha = "" + randomText.getRandomCharacter();

                String id = No + cha + No + cha + No;
                String nama = edNama.getText().toString().trim();
                String no = edNo.getText().toString().trim();
                String alamat = edAlamat.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (!isEmpty(nama) && !isEmpty(no) && !isEmpty(password)) {
                    Register(new User(id, nama, no, alamat, password, status));
                } else {
                    Snackbar.make(v, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageRegister.this, PageLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Register(User user) {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = database.child("user").child(user.getId());
        databaseReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar.make(findViewById(R.id.btnRegister), "Register Berhasil", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(PageRegister.this, PageLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }
}
