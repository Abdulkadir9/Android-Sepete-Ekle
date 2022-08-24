package com.example.sepeteekle.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepeteekle.MainActivity;
import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;


public class GirisYapFragment extends Fragment {

    SharedPreferences sharedPreferences;

    Button btnGoKayitSyf;
    EditText etGrEmail,etGrSifre;
    Button btnGirisYap;

    LinearLayout lyGiris;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giris_yap, container, false);

        btnGoKayitSyf=view.findViewById(R.id.btnGoKayitSyf);
        etGrEmail=view.findViewById(R.id.etGrEmail);
        etGrSifre=view.findViewById(R.id.etGrSifre);
        btnGirisYap=view.findViewById(R.id.btnGirisYap);
        lyGiris=view.findViewById(R.id.lyGiris);

        sharedPreferences=getContext().getSharedPreferences("kullaniciId",Context.MODE_PRIVATE);

        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavigation);
        TextView tvBaslik = getActivity().findViewById(R.id.tvBaslik);

        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etGrEmail.getText().toString().isEmpty() && !etGrSifre.getText().toString().isEmpty()){
                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Girilen bilgiler sistemde kayıtlı değil.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();
                    if (etGrEmail.getText().toString().equals("admin") && etGrSifre.getText().toString().equals("admin")){
                        sharedPreferences.edit().putInt("kullaniciId",-1).apply();

                        bottomNav.setSelectedItemId(R.id.miEkle);
                        tvBaslik.setText("Ekle");
                        bottomNav.getMenu().findItem(R.id.miEkle).setVisible(true);
                        bottomNav.getMenu().findItem(R.id.miSepetim).setVisible(false);
                        bottomNav.getMenu().findItem(R.id.miProfil).setVisible(true);
                        bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(false);

                        Snackbar.make(getActivity().findViewById(R.id.lyMain),"Hoş geldin Admin beys.",1000).setBackgroundTint(Color.parseColor("#30A300")).show();
                    }

                    db myDb = new db(getContext());
                    SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+db.KULLANICI_TABLE_NAME,null);

                    while (cursor.moveToNext()){

                        if (cursor.getString(cursor.getColumnIndex("k_Email")).equals(etGrEmail.getText().toString()) &&
                                cursor.getString(cursor.getColumnIndex("k_Sifre")).equals(etGrSifre.getText().toString()) ){

                            sharedPreferences.edit().putInt("kullaniciId",cursor.getInt(cursor.getColumnIndex("k_Id"))).apply();

                            bottomNav.setSelectedItemId(R.id.miAnasayfa);
                            tvBaslik.setText("Anasayfa");

                            bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(false);
                            bottomNav.getMenu().findItem(R.id.miProfil).setVisible(true);

                            Snackbar.make(getActivity().findViewById(R.id.lyMain),"Giriş yapıldı.",1000).setBackgroundTint(Color.parseColor("#30A300")).show();
                        }
                    }
                    etGrEmail.setText("");
                    etGrSifre.setText("");
                    etGrEmail.requestFocus();
                }
                else{
                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Boş alanlar var.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();

                    etGrEmail.requestFocus();
                }
            }
        });

        btnGoKayitSyf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBaslik.setText("Kayıt Ol");
                getFragmentManager().beginTransaction().replace(R.id.Frame,new KayitOlFragment(),null).commit();
            }
        });

        return view;
    }


}