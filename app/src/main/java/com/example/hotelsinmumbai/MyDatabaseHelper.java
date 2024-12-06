package com.example.hotelsinmumbai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "HotelDatabase.db";

    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "FoodTable";

    public static final String COLUMN_ID = "food_id";
    public static final String COLUMN_NAME = "food_name";
    public static final String COLUMN_CATEGORY = "food_category";
    public static final String COLUMN_PRICE = "food_price";
    public static final String COLUMN_IS_VEGETARIAN = ("food_is_vegetarian");


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME +" (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_IS_VEGETARIAN + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addFood(String name, String category, int price, int isVegetarian){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_PRICE,price);
        cv.put(COLUMN_IS_VEGETARIAN,isVegetarian);

        long result = myDB.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Added Successfully!",Toast.LENGTH_LONG).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db !=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    void updateRowData(String row_id,String name,String category,int price,int is_vegetarian){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_PRICE,price);
        cv.put(COLUMN_IS_VEGETARIAN,is_vegetarian);

        long result = db.update(TABLE_NAME,cv,"food_id=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(context,"Updation Failed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Updated Successfully",Toast.LENGTH_LONG).show();
        }
    }
    void deleteRowData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result  = db.delete(TABLE_NAME,"food_id=?",new String[]{row_id});
        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();
        }
    }
}
