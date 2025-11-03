package com.faiyaz.myapplication.dbUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.faiyaz.myapplication.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil {

    private SqLiteDatabase dbHelper;



    public ProductUtil(Context context) {
        dbHelper = new SqLiteDatabase(context);
    }

    public long insert(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqLiteDatabase.COL_NAME, product.getName());
        values.put(SqLiteDatabase.COL_EMAIL, product.getEmail());
        values.put(SqLiteDatabase.COL_PRICE, product.getPrice());
        values.put(SqLiteDatabase.COL_QUANTITY, product.getQuantity());

        long id = db.insert(SqLiteDatabase.TBL_NAME, null, values);
        db.close();
        return id;
    }

    // 🔹 READ ALL
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + SqLiteDatabase.TBL_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                p.setName(cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_NAME)));
                p.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_EMAIL)));
                p.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_PRICE)));
                p.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_QUANTITY)));

                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // 🔹 UPDATE
    public int update(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqLiteDatabase.COL_NAME, product.getName());
        values.put(SqLiteDatabase.COL_EMAIL, product.getEmail());
        values.put(SqLiteDatabase.COL_PRICE, product.getPrice());
        values.put(SqLiteDatabase.COL_QUANTITY, product.getQuantity());

        int rows = db.update(SqLiteDatabase.TBL_NAME, values, "ID = ?", new String[]{String.valueOf(product.getId())});
        db.close();
        return rows;
    }

    // 🔹 DELETE
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(SqLiteDatabase.TBL_NAME, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }



}
