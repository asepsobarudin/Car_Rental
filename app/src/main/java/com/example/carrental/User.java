package com.example.carrental;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String nama;
    private String no;
    private String alamat;
    private String password;
    private String status;

    public User (){}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo() {
        return no;
    }
    public void setNo(String no) {this.no = no;}

    public String getAlamat() {return alamat;}
    public void setAlamat(String alamat) {this.alamat = alamat;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public User (String id, String nama, String no, String alamat, String password, String status){
        this.id = id;
        this.nama = nama;
        this.no = no;
        this.alamat = alamat;
        this.password = password;
        this.status = status;
    }
}
