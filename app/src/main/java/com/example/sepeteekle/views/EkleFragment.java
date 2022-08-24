package com.example.sepeteekle.views;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sepeteekle.R;
import com.example.sepeteekle.models.db;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class EkleFragment extends Fragment {

    EditText etProdName,etProdDescription,etProdPrice;
    Button btnAddProd;
    BottomNavigationView bottomNav;
    TextView tvBaslik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ekle, container, false);

        etProdName=view.findViewById(R.id.etProdName);
        etProdDescription=view.findViewById(R.id.etProdDescription);
        etProdPrice=view.findViewById(R.id.etProdPrice);

        btnAddProd=view.findViewById(R.id.btnAddProd);
        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etProdName.getText().toString().isEmpty() && !etProdDescription.getText().toString().isEmpty() &&
                        !etProdPrice.getText().toString().isEmpty()){

                    db myDb = new db(getContext());
                    SQLiteDatabase sqLiteDatabase = myDb.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("u_Ad",etProdName.getText().toString());
                    contentValues.put("u_Aciklama",etProdDescription.getText().toString());
                    contentValues.put("u_Fiyat",Double.valueOf(etProdPrice.getText().toString()));

                    sqLiteDatabase.insert(db.URUN_TABLE_NAME,null,contentValues);

                    getFragmentManager().beginTransaction().replace(R.id.Frame,new AnasayfaFragment(),null).commit();
                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Ürün eklendi.",1000).setBackgroundTint(Color.parseColor("#30A300")).show();
                    bottomNav = getActivity().findViewById(R.id.bottomNavigation);
                    tvBaslik = getActivity().findViewById(R.id.tvBaslik);
                    bottomNav.setSelectedItemId(R.id.miAnasayfa);
                    tvBaslik.setText("Anasayfa");
                }
                else{
                    Snackbar.make(getActivity().findViewById(R.id.lyMain),"Boş alanlar var.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();
                }
            }
        });


        return view;
    }
}