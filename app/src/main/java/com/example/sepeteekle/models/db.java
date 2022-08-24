package com.example.sepeteekle.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db extends SQLiteOpenHelper {

    static final int DATABASE_VARSION = 1;
    static final String DATABASE_NAME = "SepeteEkleDb.db";

    public static final String KULLANICI_TABLE_NAME = "UserTbl";
    public static final String SEPET_TABLE_NAME = "BasketTbl";
    public static final String URUN_TABLE_NAME = "ProductTbl";

    static String SQL_KULLANICI_CREATE_TABLE="CREATE TABLE "+KULLANICI_TABLE_NAME+
            "(k_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "k_Email TEXT," +
            "k_Sifre TEXT)";

    static String SQL_SEPET_CREATE_TABLE="CREATE TABLE "+SEPET_TABLE_NAME+
            "(s_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "s_UrunId INTEGER," +
            "s_KullaniciId INTEGER)";

    static String SQL_URUN_CREATE_TABLE="CREATE TABLE "+URUN_TABLE_NAME+
            "(u_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "u_Ad TEXT," +
            "u_Aciklama TEXT," +
            "u_Fiyat REAL," +
            "u_Sepette INTEGER)";


    public db(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VARSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_KULLANICI_CREATE_TABLE);
        sqLiteDatabase.execSQL(SQL_SEPET_CREATE_TABLE);
        sqLiteDatabase.execSQL(SQL_URUN_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+KULLANICI_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SEPET_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+URUN_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
