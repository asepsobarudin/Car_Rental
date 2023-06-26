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

public class RecyclerViewAdapterUser extends RecyclerView.Adapter<RecyclerViewAdapterUser.ViewHolder> {

    private ArrayList<User> listUser;
    private Context context;
    DatabaseReference database;

    public RecyclerViewAdapterUser(ArrayList<User> users, Context ctx, DatabaseReference dbRef){
        listUser = users;
        context = ctx;
        database = dbRef;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtNo;
        Button btnDetail;

        ViewHolder(View v){
            super(v);
            txtName = v.findViewById(R.id.txtNama);
            txtNo = v.findViewById(R.id.txtNo);
            btnDetail = v.findViewById(R.id.btnDetail);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_user, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        final User user = listUser.get(holder.getAdapterPosition());

        holder.txtName.setText(user.getNama());
        holder.txtNo.setText(user.getNo());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = holder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(), DetailUser.class);
                intent.putExtra("data", listUser.get(adapterPosition));
                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });
    }

    public int getItemCount(){
        return listUser.size();
    }
}

