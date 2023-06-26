package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PageDetail extends AppCompatActivity {

    ImageButton btnBack;
    TextView txtNama;
    TextView txtKursi;
    TextView txtMesin;
    TextView txtSpeed;
    TextView txtHargaHari;

    TableRow hargaPerHari;
    TableRow waktuPinjam;
    TableRow namaUser;
    TableRow noHp;
    TableRow alamat;
    TableRow totHarga;

    EditText edOrder;
    EditText edTaggal;
    TextView txtOrder;
    TextView txtTanggal;
    TextView txtNamaUser;
    TextView txtNo;
    TextView txtAlamat;
    TextView txtHarga;
    Button btnOrder;
    Button btnBatal;
    DBHelper mydb;
    User user;
    private String getHarga;
    private DatabaseReference database;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        mydb = new DBHelper(this);
        database = FirebaseDatabase.getInstance().getReference();

        Cursor db = mydb.getUser("1");
        if(db != null) {
            db.moveToFirst();
            String id = db.getString(db.getColumnIndex("idU"));
            String nama = db.getString(db.getColumnIndex("nama"));
            String nomor = db.getString(db.getColumnIndex("nomor"));
            String alamat = db.getString(db.getColumnIndex("alamat"));
            String password = db.getString(db.getColumnIndex("password"));
            String status = db.getString(db.getColumnIndex("status"));
            user = new User(id ,nama, nomor, alamat, password, status);
        }

        btnBack = findViewById(R.id.btnBack);
        txtNama = findViewById(R.id.txtNama);
        txtKursi = findViewById(R.id.txtKursi);
        txtMesin = findViewById(R.id.txtMesin);
        txtSpeed = findViewById(R.id.txtSpeed);
        txtHargaHari = findViewById(R.id.txtHargaPerHari);
        btnOrder = findViewById(R.id.btnOrder);
        btnBatal = findViewById(R.id.btnBatal);

        hargaPerHari = findViewById(R.id.hargaPerHari);
        waktuPinjam = findViewById(R.id.waktuPinjam);
        namaUser = findViewById(R.id.namaUser);
        noHp = findViewById(R.id.noHp);
        alamat = findViewById(R.id.alamat);
        totHarga = findViewById(R.id.totHarga);

        edOrder = findViewById(R.id.edOrder);
        edTaggal = findViewById(R.id.edTanggal);
        txtOrder = findViewById(R.id.txtOrder);
        txtTanggal = findViewById(R.id.txtTanggal);
        txtNamaUser = findViewById(R.id.txtNamaUser);
        txtNo = findViewById(R.id.txtNo);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtHarga = findViewById(R.id.txtHarga);

        String od = getIntent().getStringExtra("order");

        if(od.equals("order")){
            final Order order = (Order) getIntent().getSerializableExtra("data");
            edOrder.setVisibility(View.GONE);
            edTaggal.setVisibility(View.GONE);
            key = order.getKey();

            txtNama.setText(order.getNama());
            txtKursi.setText(order.getJumlahKursi());
            txtMesin.setText(order.getJenisMesin());
            txtSpeed.setText(order.getSpeed());
            txtHargaHari.setText(order.getHargaPerHari());
            getHarga = order.getHargaPerHari();
            txtOrder.setText(order.getWaktuPinjam() + " hari");
            txtTanggal.setText(order.getTanggal());
            txtNamaUser.setText(order.getNamaUser());
            txtNo.setText(order.getNo());
            txtAlamat.setText(order.getAlamat());
            txtHarga.setText("Rp. " + order.getTotalHarga());

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PageDetail.this, Home.class);
                    intent.putExtra("fragment", "page_order");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            final Mobil mobil = (Mobil) getIntent().getSerializableExtra("data");
            namaUser.setVisibility(View.GONE);
            noHp.setVisibility(View.GONE);
            alamat.setVisibility(View.GONE);
            totHarga.setVisibility(View.GONE);
            txtTanggal.setVisibility(View.GONE);

            txtNama.setText(mobil.getNama());
            txtKursi.setText(mobil.getJumlahKursi() + " kursi");
            txtMesin.setText(mobil.getJenisMesin());
            txtSpeed.setText(mobil.getSpeed());
            txtHargaHari.setText("Rp. " + mobil.getHargaPerHari());
            getHarga = mobil.getHargaPerHari();

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PageDetail.this, Home.class);
                    intent.putExtra("fragment", "page_home");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (!od.equals("order")){
            btnBatal.setVisibility(View.GONE);
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RandomText randomText = new RandomText();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    String getHari = edOrder.getText().toString();

                    int hari = Integer.parseInt(getHari + "");
                    int hargaHari = Integer.parseInt(getHarga);
                    int totalHarga = hargaHari * hari;
                    String harga = "" + totalHarga;

                    String No = "" + randomText.getRandomNumberInRange(1, 100);
                    String Cha = "" + randomText.getRandomCharacter();
                    String idU = No + Cha + No + Cha + No + Cha;

                    if(!isEmpty(txtNama.getText().toString().trim())) {
                        orderMobil(new Order(
                                idU,
                                txtNama.getText().toString().trim(),
                                txtKursi.getText().toString().trim(),
                                txtMesin.getText().toString().trim(),
                                txtHargaHari.getText().toString().trim(),
                                edOrder.getText().toString().trim(),
                                txtSpeed.getText().toString().trim(),
                                harga,
                                user.getNama(),
                                user.getNo(),
                                user.getAlamat(),
                                txtNama.getText().toString().trim() +
                                        harga + user.getNo() +
                                        randomText.getRandomCharacter() +
                                        randomText.getRandomNumberInRange(1, 100),
                                edTaggal.getText().toString().trim()));
                    } else {
                        Snackbar.make(v, "Order Gagal", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            btnOrder.setVisibility(View.GONE);
            btnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    batalOrder(key);
                }
            });
        }
    }

    public void setTanggal(View view){
        final Calendar c = Calendar.getInstance();
        int tahun = c.get(Calendar.YEAR);
        int bulan = c.get(Calendar.MONTH);
        int tanggal = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PageDetail.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int tahun, int bulan, int tanggal) {
                edTaggal.setText(tanggal + "-" + (bulan + 1) + "-" + tahun);
            }
        }, tahun, bulan, tanggal);
        datePickerDialog.show();
    }

    private void orderMobil(Order order) {
        DatabaseReference dataRef = database.child("order").child(order.getKey());
        String orderKey = dataRef.getKey();

        dataRef.setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar.make(findViewById(R.id.btnOrder), "Order Berhasil", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(PageDetail.this, Home.class);
                intent.putExtra("fragment", "page_order");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    private void batalOrder (String id) {
        DatabaseReference dataRef = database.child("order").child(id);
        dataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent intent = new Intent(PageDetail.this, Home.class);
                intent.putExtra("fragment", "page_order");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                Snackbar.make(findViewById(R.id.btnBatal), "Data berhasil dihapus", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PageDetail.this, Home.class);
        intent.putExtra("fragment", "page_user");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }
}