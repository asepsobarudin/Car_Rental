package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailUser extends AppCompatActivity {

    ImageButton btnBack;

    EditText edNama;
    EditText edNo;
    EditText edPassword;
    EditText edAlamat;
    Spinner edStatus;
    Button btnDelete;
    Button btnUpdate;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        database = FirebaseDatabase.getInstance().getReference();

        btnBack = findViewById(R.id.btnBack);
        edNama = findViewById(R.id.edNama);
        edNo = findViewById(R.id.edNo);
        edPassword = findViewById(R.id.edPassword);
        edAlamat = findViewById(R.id.edAlamat);
        edStatus = findViewById(R.id.edStatus);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        final User user = (User) getIntent().getSerializableExtra("data");

        edNama.setText(user.getNama());
        edNo.setText(user.getNo());
        edAlamat.setText(user.getAlamat());
        edPassword.setText(user.getPassword());

        int status;
        if(user.getStatus().equals("admin")){
            status = 0;
        } else {
            status = 1;
        }

        edStatus.setSelection(status);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailUser.this, Home.class);
                intent.putExtra("fragment", "page_user");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                deleteUser(user.getId());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                updateUser(new User(
                        user.getId(), edNama.getText().toString().trim(),
                        edNo.getText().toString().trim(),
                        edAlamat.getText().toString().trim(),
                        edPassword.getText().toString().trim(),
                        edStatus.getSelectedItem().toString().trim()));
            }
        });
    }

    private void deleteUser (String id) {
        DatabaseReference dataRef = database.child("user").child(id);
        dataRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(DetailUser.this, Home.class);
                intent.putExtra("fragment", "page_user");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

                Snackbar.make(findViewById(R.id.btnDelete), "User berhasil dihapus", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(R.id.btnDelete), "Gagal menghapus user", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void updateUser (User user) {
        DatabaseReference dataRef = database.child("user").child(user.getId());
        String orderKey = dataRef.getKey();

        dataRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent intent = new Intent(DetailUser.this, Home.class);
                intent.putExtra("fragment", "page_user");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                Snackbar.make(findViewById(R.id.btnUpdate), "Opdate Berhasil", Snackbar.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailUser.this, Home.class);
        intent.putExtra("fragment", "page_user");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}