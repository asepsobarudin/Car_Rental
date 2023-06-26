package com.example.carrental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Mobil> listMobil;
    private ArrayList<User> listUser;
    private Context context;
    DatabaseReference database;
    DBHelper mydb;

    public RecyclerViewAdapter(ArrayList<Mobil> listMobils, Context ctx, DatabaseReference dbRef){
        listMobil = listMobils;
        context = ctx;
        database = dbRef;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtDetail;
        Button btnDetail;
        ViewHolder(View v){
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtName);
            txtDetail = (TextView) v.findViewById(R.id.txtDetail);
            btnDetail = (Button) v.findViewById(R.id.btnDetail);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_car, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        final Mobil mobil = listMobil.get(holder.getAdapterPosition());
        final int adapterPosition = holder.getAdapterPosition();

        holder.txtName.setText(mobil.getNama());
        holder.txtDetail.setText(mobil.getJenisMesin() + " | " + mobil.getJumlahKursi() + " Kursi | " + mobil.getSpeed() + " Km");

        mydb =  new DBHelper(context);
        Cursor db = mydb.getUser("1");
        if(db != null) {
            db.moveToFirst();
            String id = db.getString(db.getColumnIndex("id"));
            String nama = db.getString(db.getColumnIndex("nama"));
            String no = db.getString(db.getColumnIndex("nomor"));
            String alamat = db.getString(db.getColumnIndex("alamat"));
            String password = db.getString(db.getColumnIndex("password"));
            String status = db.getString(db.getColumnIndex("status"));

            User user = new User(id ,nama, no, alamat, password, status);

            if(user.getStatus().equals("admin")) {
                holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CreateData.class);
                        intent.putExtra("data", listMobil.get(adapterPosition));
                        v.getContext().startActivity(intent);
                    }
                });
            } else {
                holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PageDetail.class);
                        intent.putExtra("data", listMobil.get(adapterPosition));
                        intent.putExtra("order", "detail");
                        v.getContext().startActivity(intent);
                        ((Activity) v.getContext()).finish();
                    }
                });
            }
        }
    }

    public int getItemCount(){
        return listMobil.size();
    }
}
