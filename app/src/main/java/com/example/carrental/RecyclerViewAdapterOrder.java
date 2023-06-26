package com.example.carrental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class RecyclerViewAdapterOrder extends RecyclerView.Adapter<RecyclerViewAdapterOrder.ViewHolder> {

    private ArrayList<Order> listOrder;
    private Context context;
    DatabaseReference database;

    public RecyclerViewAdapterOrder(ArrayList<Order> orders, Context ctx, DatabaseReference dbRef){
        listOrder = orders;
        context = ctx;
        database = dbRef;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername;
        TextView txtName;
        TextView txtDetail;
        Button btnDetail;

        ViewHolder(View v){
            super(v);
            txtUsername = (TextView) v.findViewById(R.id.txtNameUser);
            txtName = (TextView) v.findViewById(R.id.txtName);
            txtDetail = (TextView) v.findViewById(R.id.txtDetail);
            btnDetail = (Button) v.findViewById(R.id.btnDetail);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_order, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        final Order order = listOrder.get(holder.getAdapterPosition());

        holder.txtUsername.setText(order.getNamaUser());
        holder.txtName.setText(order.getNama());
        holder.txtDetail.setText(order.getJenisMesin() + " | " + order.getJumlahKursi() + " Kursi | " + order.getSpeed() + " Km");

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = holder.getAdapterPosition();
                Intent intent = new Intent(v.getContext(), PageDetail.class);
                intent.putExtra("data", listOrder.get(adapterPosition));
                intent.putExtra("order", "order");
                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).finish();
            }
        });
    }

    public int getItemCount(){
        return listOrder.size();
    }
}

