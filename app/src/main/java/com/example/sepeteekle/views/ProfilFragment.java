package com.example.sepeteekle.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class ProfilFragment extends Fragment {

    BottomNavigationView bottomNav;
    SharedPreferences sharedPreferences;

    Button btnCikisYap;
    TextView tvHosGeldin;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil, container, false);

        sharedPreferences=getContext().getSharedPreferences("kullaniciId", Context.MODE_PRIVATE);

        bottomNav=getActivity().findViewById(R.id.bottomNavigation);

        tvHosGeldin=view.findViewById(R.id.tvHosGeldin);
        btnCikisYap=view.findViewById(R.id.btnCikisYap);

        db myDb = new db(getContext());
        SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+db.KULLANICI_TABLE_NAME,null);
        while (cursor.moveToNext()){
            if ( sharedPreferences.getInt("kullaniciId",0) == cursor.getInt(cursor.getColumnIndex("k_Id")) ){
                tvHosGeldin.setText("Hoş geldin, "+cursor.getString(cursor.getColumnIndex("k_Email")));
            }
            else if(sharedPreferences.getInt("kullaniciId",0) == -1){
                tvHosGeldin.setText("Hoş geldin Admin");
            }
        }

        btnCikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences.edit().clear().apply();
                bottomNav.getMenu().findItem(R.id.miEkle).setVisible(false);
                bottomNav.getMenu().findItem(R.id.miProfil).setVisible(false);
                bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(true);
                bottomNav.getMenu().findItem(R.id.miSepetim).setVisible(true);

                bottomNav.setSelectedItemId(R.id.miAnasayfa);
                Snackbar.make(getActivity().findViewById(R.id.lyMain),"Çıkış yapıldı.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();

            }
        });

        return view;
    }
}