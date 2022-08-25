package com.example.sepeteekle.adapters;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.example.sepeteekle.models.urun;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class urunAdapter extends RecyclerView.Adapter<urunAdapter.UrunViewHolder> {

    private ArrayList<urun> uruns;
    Context context;
    SharedPreferences sharedPreferences;

    public urunAdapter(ArrayList<urun> uruns, Context context) {
        this.uruns = uruns;
        this.context = context;
    }

    @NonNull
    @Override
    public UrunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.urun_single_item,parent,false);
        return new UrunViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull UrunViewHolder holder,  int position) {
        holder.ivProdImg.setImageResource(R.drawable.ic_product);
        holder.tvProdName.setText(uruns.get(position).getU_Ad());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        holder.tvProdPrice.setText(decimalFormat.format(uruns.get(position).getU_Fiyat())+" ₺");
        holder.btnSepeteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (uruns.get(position).getU_Ad()+", Sepete eklendi"), Toast.LENGTH_SHORT).show();

                db myDb = new db(context);
                SQLiteDatabase sqLiteDatabase = myDb.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put("s_UrunId",uruns.get(position).getU_Id());
                contentValues.put("s_KullaniciId",sharedPreferences.getInt("kullaniciId",0));

                sqLiteDatabase.insert(db.SEPET_TABLE_NAME,null,contentValues);

                contentValues=new ContentValues();
                contentValues.put("u_Id",1);
                sqLiteDatabase.update(db.URUN_TABLE_NAME,contentValues,"u_Id=0",null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return uruns.size();
    }

    public class UrunViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProdImg;
        TextView tvProdName,tvProdPrice;
        Button btnSepeteEkle;

        @SuppressLint("Range")
        public UrunViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProdImg=itemView.findViewById(R.id.ivProdImg);
            tvProdName=itemView.findViewById(R.id.tvProdName);
            tvProdPrice=itemView.findViewById(R.id.tvProdPrice);
            btnSepeteEkle=itemView.findViewById(R.id.btnSepeteEkle);

            sharedPreferences=context.getSharedPreferences("kullaniciId",Context.MODE_PRIVATE);
            if (sharedPreferences.getInt("kullaniciId",0)>=1){
                btnSepeteEkle.setVisibility(View.VISIBLE);
            }
            else{
                btnSepeteEkle.setVisibility(View.GONE);

            }

            db myDb = new db(context);
            SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+db.SEPET_TABLE_NAME,null);
            while (cursor.moveToNext()){
                if ((cursor.getInt(cursor.getColumnIndex("s_KullaniciId"))==sharedPreferences.getInt("kullaniciId",0))){
                    db myDb2 = new db(context);
                    SQLiteDatabase sqLiteDatabase2 = myDb2.getReadableDatabase();
                    Cursor cursor2 = sqLiteDatabase2.rawQuery("SELECT * FROM "+db.URUN_TABLE_NAME,null);
                    while (cursor2.moveToNext()){
                        if (cursor2.getInt(cursor2.getColumnIndex("u_Sepette"))==1){
                            Toast.makeText(context, "sa", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

        }
    }
}
