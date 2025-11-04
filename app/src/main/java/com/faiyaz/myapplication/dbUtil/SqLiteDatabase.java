package com.faiyaz.myapplication.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SqLiteDatabase extends SQLiteOpenHelper {

    public static final String NAME = "INVENTORY.db";
    public static final int VERSION = 1;

    public static final String TBL_NAME = "PRODUCT";

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PRICE = "PRICE";
    public static final String COL_QUANTITY = "QUANTITY";

    public SqLiteDatabase(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TBL_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PRICE + " REAL, " +
                COL_QUANTITY + " INTEGER, " + "IMAGE_URI TEXT" +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < 2){

            db.execSQL("ALTER TABLE "+TBL_NAME +" ADD COLUMN IMAGE_URI TEXT");
        }

    }
}
