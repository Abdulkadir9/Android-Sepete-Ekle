package com.example.sepeteekle.views;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sepeteekle.R;
import com.example.sepeteekle.adapters.urunAdapter;
import com.example.sepeteekle.models.db;
import com.example.sepeteekle.models.urun;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AnasayfaFragment extends Fragment {

    RecyclerView rvUruns;
    urunAdapter urunAdapter;
    ArrayList<urun> urunList;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        rvUruns=view.findViewById(R.id.rvUruns);

        db myDb = new db(getContext());
        SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+db.URUN_TABLE_NAME,null);
        urunList=new ArrayList<>();

        while (cursor.moveToNext()){
            int u_Id = cursor.getInt(cursor.getColumnIndex("u_Id"));
            String u_Ad = cursor.getString(cursor.getColumnIndex("u_Ad"));
            String u_Aciklama = cursor.getString(cursor.getColumnIndex("u_Aciklama"));
            double u_Fiyat = cursor.getDouble(cursor.getColumnIndex("u_Fiyat"));
            int u_Sepette = cursor.getInt(cursor.getColumnIndex("u_Sepette"));

            urun myUrun = new urun(u_Id,u_Ad,u_Aciklama,u_Fiyat,u_Sepette);
            urunList.add(myUrun);
        }
        urunAdapter=new urunAdapter(urunList,getContext());
        rvUruns.setAdapter(urunAdapter);
        urunAdapter.notifyDataSetChanged();
        rvUruns.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        return view;
    }
}