package com.example.mobileappdev_nt118n11.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.mobileappdev_nt118n11.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="LocalFoodDB.db";
    private static final int DB_VERSION=2;

    public Database(Context context) {
        super(context,DB_NAME,null, DB_VERSION);
    }

    public ArrayList<Order> getCart(){
        SQLiteDatabase sqlDB = getReadableDatabase();
        SQLiteQueryBuilder queryBuild = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ID","ProductID","ProductName","Quantity","Price","Image"};
        String sqlTable = "OrderDetail";

        queryBuild.setTables(sqlTable);
        Cursor c = queryBuild.query(sqlDB,sqlSelect,null,null,null,null,null);

        final ArrayList<Order> result = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                int idTableCol = c.getColumnIndex("ID");
                int idProductCol = c.getColumnIndex("ProductId");
                int nameCol = c.getColumnIndex("ProductName");
                int quantityCol = c.getColumnIndex("Quantity");
                int priceCol = c.getColumnIndex("Price");
                int imgCol = c.getColumnIndex("Image");

                if (idTableCol!=-1 && idProductCol != -1 && nameCol!=-1 && quantityCol!=-1 && priceCol!=-1 && imgCol!=-1)
                    result.add(new Order(c.getInt(idTableCol),
                            c.getString(idProductCol),
                            c.getString(nameCol),
                            c.getString(quantityCol),
                            c.getString(priceCol),
                            c.getString(imgCol)));
                else
                    Log.e("databases", "cant get a product in order because column equal -1");
            }while(c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase sqlDB = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (ProductId,ProductName,Quantity,Price,Image) VALUES ('%s','%s','%s','%s','%s')",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getImage());
        sqlDB.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase sqlDB = getReadableDatabase();
        String query = "DELETE FROM OrderDetail";
        sqlDB.execSQL(query);
    }

    public void updateCart(Order order) {
        SQLiteDatabase sqlDB = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity= %s WHERE ID = %d",
                order.getQuantity(),
                order.getID());
        sqlDB.execSQL(query);
    }
}
