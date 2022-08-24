package com.example.sepeteekle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.example.sepeteekle.models.sepet;
import com.example.sepeteekle.models.urun;

import java.util.ArrayList;

public class sepetAdapter extends RecyclerView.Adapter<sepetAdapter.sepetViewHolder> {

    ArrayList<urun> uruns;
    ArrayList<sepet> sepets;
    Context context;
    double totalFiyat;

    public sepetAdapter(ArrayList<urun> uruns, ArrayList<sepet> sepets, Context context, Double totalFiyat) {
        this.uruns = uruns;
        this.sepets = sepets;
        this.context = context;
        this.totalFiyat = totalFiyat;
    }

    @NonNull
    @Override
    public sepetAdapter.sepetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sepet_single_item,parent,false);
        return new sepetViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull sepetAdapter.sepetViewHolder holder, int position) {

        holder.ivProductImg.setImageResource(R.drawable.ic_product);
        holder.tvProductName.setText(uruns.get(position).getU_Ad());
        holder.tvProductDescription.setText(uruns.get(position).getU_Aciklama());
        holder.tvProductPrice.setText(uruns.get(position).getU_Fiyat()+" ₺");

        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    db myDb = new db(context);
                    SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
                    sqLiteDatabase.delete(db.SEPET_TABLE_NAME,"s_Id="+sepets.get(position).getS_Id(),null);
                    totalFiyat-=uruns.get(position).getU_Fiyat();
                    uruns.remove(position);
                    notifyDataSetChanged();

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return uruns.size();
    }

    public class sepetViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName,tvProductPrice,tvProductDescription;
        ImageView ivProductImg,ivRemove;

        public sepetViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImg=itemView.findViewById(R.id.ivProductImg);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvProductPrice=itemView.findViewById(R.id.tvProductPrice);
            tvProductDescription=itemView.findViewById(R.id.tvProductDescription);
            ivRemove=itemView.findViewById(R.id.ivRemove);
        }
    }
}
