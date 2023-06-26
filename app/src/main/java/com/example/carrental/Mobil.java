package com.example.carrental;

import java.io.Serializable;

public class Mobil implements Serializable {

    private String id ;
    private String nama;
    private String jumlahKursi;
    private String jenisMesin;
    private String speed;
    private String hargaPerHari;

    public Mobil() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void setSpeed(String speed) {this.speed = speed;}

    public String getHargaPerHari() {
        return hargaPerHari;
    }

    public void setHargaPerHari(String hargaPerHari) {
        this.hargaPerHari = hargaPerHari;
    }


    public String toString(){
        return "" + nama + "" + jumlahKursi + "" + jenisMesin + "" + speed + "" + hargaPerHari;
    }

    public Mobil(String Id ,String Nama, String JumlahKursi, String JenisMesin, String Speed, String HargaPerHari){
        this.id = Id;
        this.nama = Nama;
        this.jumlahKursi = JumlahKursi;
        this.jenisMesin = JenisMesin;
        this.speed = Speed;
        this.hargaPerHari = HargaPerHari;
    }
}

