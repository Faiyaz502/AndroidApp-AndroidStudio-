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
        values.put("IMAGE_URI", product.getImageUri()); // new field

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
                Product p = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_EMAIL)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_QUANTITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow("IMAGE_URI"))
                );

                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // 🔹 GET PRODUCT BY ID (for edit)
    public Product getProductById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SqLiteDatabase.TBL_NAME + " WHERE ID = ?", new String[]{String.valueOf(id)});
        Product p = null;
        if (cursor.moveToFirst()) {
            p = new Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                    cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_EMAIL)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_PRICE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(SqLiteDatabase.COL_QUANTITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow("IMAGE_URI"))
            );
        }
        cursor.close();
        db.close();
        return p;
    }

    // 🔹 UPDATE
    public int update(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqLiteDatabase.COL_NAME, product.getName());
        values.put(SqLiteDatabase.COL_EMAIL, product.getEmail());
        values.put(SqLiteDatabase.COL_PRICE, product.getPrice());
        values.put(SqLiteDatabase.COL_QUANTITY, product.getQuantity());
        values.put("IMAGE_URI", product.getImageUri()); // new field

        int rows = db.update(SqLiteDatabase.TBL_NAME, values, "ID = ? ", new String[]{String.valueOf(product.getId())});
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
