package com.example.sepeteekle.views;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.google.android.material.snackbar.Snackbar;

public class KayitOlFragment extends Fragment {

    EditText etKyEmail,etKySifre,etKySifreTkr;
    Button btnGoGirisSyf,btnKayitOl;

    db myDb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    TextView tvBaslik;

    boolean readerUserTable = false, firstRegister = false;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kayit_ol, container, false);

        tvBaslik=getActivity().findViewById(R.id.tvBaslik);

        etKyEmail=view.findViewById(R.id.etKyEmail);
        etKySifre=view.findViewById(R.id.etKySifre);
        etKySifreTkr=view.findViewById(R.id.etKySifreTkr);

        btnGoGirisSyf=view.findViewById(R.id.btnGoGirisSyf);
        btnKayitOl=view.findViewById(R.id.btnKayitOl);

        btnKayitOl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!etKyEmail.getText().toString().isEmpty() && !etKySifre.getText().toString().isEmpty() &&
                        !etKySifreTkr.getText().toString().isEmpty()){

                    if (etKySifre.getText().toString().equals(etKySifreTkr.getText().toString())){

                        if (!etKyEmail.getText().toString().equals("admin")){
                            myDb = new db(getContext());
                            sqLiteDatabase=myDb.getReadableDatabase();
                            cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+db.KULLANICI_TABLE_NAME,null);

                            while (cursor.moveToNext()){
                                firstRegister=true;
                                readerUserTable=false;
                                if (cursor.getString(cursor.getColumnIndex("k_Email")).equals(etKyEmail.getText().toString())){
                                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Email zaten sistemde kayıtlı.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();

                                    etKyEmail.setText("");
                                    etKySifre.setText("");
                                    etKySifreTkr.setText("");
                                    etKyEmail.requestFocus();

                                    tvBaslik.setText("Giriş Yap");

                                    break;
                                }
                                readerUserTable=true;
                            }

                            if (readerUserTable==true){
                                register();
                            }

                            if (firstRegister==false){
                                register();
                                firstRegister=true;
                            }
                        }
                        else{
                            Snackbar.make(getActivity().findViewById(R.id.lyMain),"Seni gidi akıllı çılgın :)",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();
                            etKyEmail.setText("");
                            etKySifre.setText("");
                            etKySifreTkr.setText("");

                            etKyEmail.requestFocus();
                        }

                    }
                    else{
                        Snackbar.make(getActivity().findViewById(R.id.lyMain),"Girilen şifreler uyuşmuyor.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();


                        etKySifre.setText("");
                        etKySifreTkr.setText("");
                        etKySifre.requestFocus();
                    }
                }
                else{
                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Email zaten sistemde kayıtlı.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();
                }
            }
        });

        btnGoGirisSyf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBaslik.setText("Giriş Yap");
                getFragmentManager().beginTransaction().replace(R.id.Frame, new GirisYapFragment(),null).commit();
            }
        });

        return view;
    }

    void register(){
        myDb=new db(getContext());
        sqLiteDatabase=myDb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("k_Email",etKyEmail.getText().toString());
        contentValues.put("k_Sifre",etKySifre.getText().toString());
        sqLiteDatabase.insert(db.KULLANICI_TABLE_NAME,null,contentValues);

        getFragmentManager().beginTransaction().replace(R.id.Frame,new GirisYapFragment(),null).commit();

        etKyEmail.setText("");
        etKySifre.setText("");
        etKySifreTkr.setText("");

        Snackbar.make(getActivity().findViewById(R.id.lyMain),"Kayıt olundu.",1000).setBackgroundTint(Color.parseColor("#30A300")).show();
    }
}