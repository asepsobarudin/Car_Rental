package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class CreateData extends AppCompatActivity {

    ImageButton btnBack;
    ImageButton addImage;
    EditText edNama;
    EditText edKursi;
    EditText edSpeed;
    Spinner edMesin;
    EditText edHargaPerHari;
    Button btnSimpan;
    Button btnDelete;
    private DatabaseReference database;
    private String idU;

    private String status = "Data Berhasil Ditambahkan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        database = FirebaseDatabase.getInstance().getReference();

        btnBack = findViewById(R.id.btnBack);
        addImage = findViewById(R.id.addImage);
        edNama = findViewById(R.id.edNama);
        edKursi = findViewById(R.id.edKursi);
        edMesin = findViewById(R.id.edMesin);
        edSpeed = findViewById(R.id.edSpeed);
        edHargaPerHari = findViewById(R.id.edHargaPerHari);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnDelete = findViewById(R.id.btnDelete);

        final Mobil mobil = (Mobil) getIntent().getSerializableExtra("data");

        if(mobil != null) {

            int ja;

            if(mobil.getJenisMesin().toString().trim().equals("Bensin")){
                ja = 1;
            } else if(mobil.getJenisMesin().toString().trim().equals("Disel")){
                ja = 2;
            } else if(mobil.getJenisMesin().toString().trim().equals("Listrik")){
                ja = 3;
            } else {
                ja = 0;
            }

            idU = mobil.getId();
            edNama.setText(mobil.getNama());
            edKursi.setText(mobil.getJumlahKursi());
            edMesin.setSelection(ja);
            edSpeed.setText(mobil.getSpeed());
            edHargaPerHari.setText(mobil.getHargaPerHari());

            status = "Data Berhasl Di Update";
            btnDelete.setVisibility(View.VISIBLE);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMobil(idU);
                }
            });
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
            RandomText randomText = new RandomText();
            String No = "" + randomText.getRandomNumberInRange(1, 100);
            String Cha = "" +randomText.getRandomCharacter();
            idU = No + Cha + No + Cha + No + Cha;
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateData.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (!isEmpty(edNama.getText().toString())) {
                    simpanMobil(new Mobil(idU, edNama.getText().toString().trim(), edKursi.getText().toString().trim() ,edMesin.getSelectedItem().toString(), edSpeed.getText().toString().trim(), edHargaPerHari.getText().toString().trim()));
                } else {
                    Snackbar.make(v, "Nama mobil harus diisi", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void simpanMobil(Mobil mobil) {
        DatabaseReference dataRef = database.child("mobil").child(idU);
        String mobilKey = dataRef.getKey();

        dataRef.setValue(mobil).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                edNama.setText("");
                edKursi.setText("");
                edMesin.setSelection(0);
                edSpeed.setText("");
                edHargaPerHari.setText("");
                Snackbar.make(findViewById(R.id.btnSimpan), status, Snackbar.LENGTH_LONG).show();
                startActivity(new Intent(CreateData.this, Home.class));
                finish();
            }
        });
    }

    private void deleteMobil(String nm){
        DatabaseReference dataRef = database.child("mobil").child(idU);
        dataRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(CreateData.this, Home.class);
                intent.putExtra("fragment", "page_home");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                Snackbar.make(findViewById(R.id.btnSimpan), "Data berhasil dihapus", Snackbar.LENGTH_LONG).show();
                finish();
            } else {
                Snackbar.make(findViewById(R.id.btnSimpan), "Gagal menghapus data", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateData.this, Home.class);
        intent.putExtra("fragment", "page_home");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }
}
