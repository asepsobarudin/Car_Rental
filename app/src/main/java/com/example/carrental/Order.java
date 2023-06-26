package com.example.carrental;

import java.io.Serializable;

public class Order implements Serializable {

    private String id;
    private String nama;
    private String jumlahKursi;
    private String jenisMesin;
    private String speed;
    private String hargaPerHari;
    private String waktuPinjam;
    private String totalHarga;
    private String namaUser;
    private String no;
    private String alamat;

    private String key;
    private String tanggal;

    public Order () {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlahKursi() {
        return jumlahKursi;
    }
    public void setJumlahKursi(String jumlahKursi) {
        this.jumlahKursi = jumlahKursi;
    }

    public String getJenisMesin() {
        return jenisMesin;
    }
    public void setJenisMesin(String jenisMesin) {
        this.jenisMesin = jenisMesin;
    }

    public String getSpeed() {return speed;}
    public void setSpeed(String speed) { this.speed = speed; }

    public String getHargaPerHari() {
        return hargaPerHari;
    }
    public void setHargaPerHari(String hargaPerHari) {
        this.hargaPerHari = hargaPerHari;
    }

    public String getWaktuPinjam() {return waktuPinjam;}
    public void setWaktuPinjam(String waktuPinjam) {this.waktuPinjam = waktuPinjam;}

    public String getTotalHarga() {
        return totalHarga;
    }
    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getNamaUser() { return namaUser; }
    public void setNamaUser(String namaUser) { this.namaUser = namaUser; }

    public String getNo() {return no;}
    public void setNo(String no) {this.no = no;}

    public String getAlamat() {return alamat;}
    public void setAlamat(String alamat) {this.alamat = alamat;}

    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getTanggal() {return tanggal;}
    public void setTanggal(String tanggal) {this.tanggal = tanggal;}

    public Order(String Id, String Nama, String JumlahKursi, String JenisMesin, String HargaPerHari, String WaktuPinjam, String Speed, String TotalHarga, String NamaUser, String No, String Alamat, String Key, String Tanggal){
        this.id = Id;
        this.nama = Nama;
        this.jumlahKursi = JumlahKursi;
        this.jenisMesin = JenisMesin;
        this.hargaPerHari = HargaPerHari;
        this.waktuPinjam = WaktuPinjam;
        this.speed = Speed;
        this.totalHarga = TotalHarga;
        this.namaUser = NamaUser;
        this.no = No;
        this.alamat = Alamat;
        this.key = Key;
        this.tanggal = Tanggal;
    }
}
