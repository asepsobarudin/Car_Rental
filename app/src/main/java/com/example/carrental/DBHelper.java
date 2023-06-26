package com.example.carrental;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "DatabaseLogin", null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_login (id INTEGER PRIMARY KEY, idU VARCHAR ,nama VARCHAR, nomor VARCHAR, alamat VARCHAR, password VARCHAR, status VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_login");
        onCreate(db);
    }

    public boolean insertUser (String id, String idU,String nama, String no, String alamat, String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("idU", idU);
        contentValues.put("nama", nama);
        contentValues.put("nomor", no);
        contentValues.put("alamat", alamat);
        contentValues.put("password", password);
        contentValues.put("status", status);

        db.insert("tbl_login", null, contentValues);
        return true;
    }

    public Cursor getUser (String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM tbl_login WHERE id = " + id + "", null);
        return res;
    }

    public boolean deleteUser (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tbl_login", "id = ?", new String[]{String.valueOf(id)});
        return true;
    }
}
