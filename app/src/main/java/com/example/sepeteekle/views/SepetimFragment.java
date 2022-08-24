package com.example.sepeteekle.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepeteekle.adapters.sepetAdapter;

import com.example.sepeteekle.R;
import com.example.sepeteekle.adapters.urunAdapter;
import com.example.sepeteekle.models.db;
import com.example.sepeteekle.models.sepet;
import com.example.sepeteekle.models.urun;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SepetimFragment extends Fragment {

    RecyclerView rvSepets;
    sepetAdapter sepetAdapter;
    ArrayList<urun> urunList;
    ArrayList<sepet> sepetLists;

    SharedPreferences sharedPreferences;

    double toplamFiyat;
    TextView tvToplamFiyat;

    ImageView ivRemoveAll;

    boolean sepetDolu=false;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sepetim, container, false);

        sharedPreferences = getContext().getSharedPreferences("kullaniciId",Context.MODE_PRIVATE);
        DecimalFormat decimalFormat;
        rvSepets=view.findViewById(R.id.rvSepets);
        tvToplamFiyat=getActivity().findViewById(R.id.tvToplamFiyat);
        ivRemoveAll=getActivity().findViewById(R.id.ivRemoveAll);

        db myDb = new db(getContext());
        SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
        Cursor cursorSepet = sqLiteDatabase.rawQuery("SELECT * FROM "+db.SEPET_TABLE_NAME,null);
        urunList=new ArrayList<>();
        sepetLists=new ArrayList<>();
        while (cursorSepet.moveToNext()){

            sepetLists.add(new sepet(cursorSepet.getInt(cursorSepet.getColumnIndex("s_Id")),cursorSepet.getInt(cursorSepet.getColumnIndex("s_UrunId")),
                    cursorSepet.getInt(cursorSepet.getColumnIndex("s_KullaniciId"))));

           if (sharedPreferences.getInt("kullaniciId",0)==cursorSepet.getInt(cursorSepet.getColumnIndex("s_KullaniciId"))){
               Cursor cursorUrun = sqLiteDatabase.rawQuery("SELECT * FROM "+db.URUN_TABLE_NAME,null);
               while (cursorUrun.moveToNext()){
                   if ( cursorUrun.getInt(cursorUrun.getColumnIndex("u_Id")) == cursorSepet.getInt(cursorSepet.getColumnIndex("s_UrunId")) ){
                       int u_Id = cursorUrun.getInt(cursorUrun.getColumnIndex("u_Id"));
                       String u_Ad = cursorUrun.getString(cursorUrun.getColumnIndex("u_Ad"));
                       String u_Aciklama = cursorUrun.getString(cursorUrun.getColumnIndex("u_Aciklama"));
                       double u_Fiyat = cursorUrun.getDouble(cursorUrun.getColumnIndex("u_Fiyat"));
                       int u_Sepette = cursorUrun.getInt(cursorUrun.getColumnIndex("u_Sepette"));

                       toplamFiyat+=u_Fiyat;
                       urun myUrun = new urun(u_Id,u_Ad,u_Aciklama,u_Fiyat,u_Sepette);
                       urunList.add(myUrun);

                       sepetDolu=true;
                   }
               }
           }
        }


        if (sepetDolu==false){
            ivRemoveAll.setVisibility(View.GONE);
        }
        else{
            ivRemoveAll.setVisibility(View.VISIBLE);

        }

        decimalFormat = new DecimalFormat("###,###.##");
        tvToplamFiyat.setText("Toplam Fiyat: "+decimalFormat.format(toplamFiyat)+" ₺");

        ivRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db myDb = new db(getContext());
                    SQLiteDatabase sqLiteDatabase = myDb.getReadableDatabase();
                    Cursor cursom = sqLiteDatabase.rawQuery("SELECT * FROM "+db.SEPET_TABLE_NAME,null);
                    while (cursom.moveToNext()){
                        sqLiteDatabase.delete(db.SEPET_TABLE_NAME,"s_Id="+sepetLists.get(cursom.getPosition()).getS_Id(),null);
                    }
                    tvToplamFiyat.setText("Toplam Fiyat: 0 ₺");
                }
                catch (Exception ex){
                    System.out.print(ex);
                }

                getFragmentManager().beginTransaction().replace(R.id.Frame, new SepetimFragment(),null).commit();

            }
        });

        sepetAdapter=new sepetAdapter(urunList,sepetLists,getContext(),toplamFiyat);
        rvSepets.setAdapter(sepetAdapter);
        sepetAdapter.notifyDataSetChanged();
        rvSepets.setLayoutManager(new LinearLayoutManager(getContext()));




        return view;
    }
}