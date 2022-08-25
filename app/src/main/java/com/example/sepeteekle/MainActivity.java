package com.example.sepeteekle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sepeteekle.views.AnasayfaFragment;
import com.example.sepeteekle.views.EkleFragment;
import com.example.sepeteekle.views.GirisYapFragment;
import com.example.sepeteekle.views.ProfilFragment;
import com.example.sepeteekle.views.SepetimFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    SharedPreferences sharedPreferences;

    LinearLayout lyMain;

    ImageView ivRemoveAll;

    public static TextView tvBaslik;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBaslik=findViewById(R.id.tvBaslik);

        ivRemoveAll=findViewById(R.id.ivRemoveAll);

        bottomNav=findViewById(R.id.bottomNavigation);
        lyMain=findViewById(R.id.lyMain);
        cardView=findViewById(R.id.cardView);

        sharedPreferences=getSharedPreferences("kullaniciId", Context.MODE_PRIVATE);

        tvBaslik.setText("Anasayfa");
        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new AnasayfaFragment(),null).commit();

        if (sharedPreferences.getInt("kullaniciId",0)==-1){
            bottomNav.getMenu().findItem(R.id.miEkle).setVisible(true);
            bottomNav.getMenu().findItem(R.id.miSepetim).setVisible(false);
            bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(false);
            bottomNav.getMenu().findItem(R.id.miProfil).setVisible(true);
            Toast.makeText(this, "Hoş geldin Admin", Toast.LENGTH_SHORT).show();
        }
        else if(sharedPreferences.getInt("kullaniciId",0)>=1){
            bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(false);
            bottomNav.getMenu().findItem(R.id.miProfil).setVisible(true);
        }
        else{
            bottomNav.getMenu().findItem(R.id.miGirisYap).setVisible(true);
            bottomNav.getMenu().findItem(R.id.miProfil).setVisible(false);
            bottomNav.getMenu().findItem(R.id.miEkle).setVisible(false);
        }

        ivRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miAnasayfa:
                        ivRemoveAll.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        tvBaslik.setText("Anasayfa");
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new AnasayfaFragment(),null).commit();
                        break;
                    case R.id.miSepetim:
                        if (sharedPreferences.getInt("kullaniciId",0)>=1){
                            tvBaslik.setText("Sepetim");
                            ivRemoveAll.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new SepetimFragment(),null).commit();
                        }
                        else{
                            Snackbar.make(lyMain,"Sepeti görmek için giriş yapmalısın.",1000).setBackgroundTint(Color.parseColor("#DD2500")).show();
                            tvBaslik.setText("Giriş Yap");
                            getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new GirisYapFragment(),null).commit();
                            bottomNav.setSelectedItemId(R.id.miGirisYap);
                            return false;
                        }
                        break;
                    case R.id.miEkle:
                        cardView.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        tvBaslik.setText("Ekle");
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new EkleFragment(),null).commit();
                        break;
                    case R.id.miGirisYap:
                        cardView.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        tvBaslik.setText("Giriş Yap");
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new GirisYapFragment(),null).commit();
                        break;
                    case R.id.miProfil:
                        cardView.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        tvBaslik.setText("Profilim");
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new ProfilFragment(),null).commit();
                        break;
                }
                return true;
            }
        });

    }
}