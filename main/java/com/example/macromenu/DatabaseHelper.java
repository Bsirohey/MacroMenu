package com.example.macromenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String macroMenuDB = "MacroMenuDB.db";

    public static final String userTable = "UserTable";
    public static final String userName = "Name";
    public static final String userEmail = "Email";
    public static final String userPass = "Password";

    public static final String itemTable = "ItemTable";
    public static final String itemName = "Name";
    public static final String itemDesc = "Description";
    public static final String itemPrice = "Price";
    public static final String itemType = "Type";

    public static final String cartTable = "CartTable";
    public static final String cartItemName = "FoodName";
    public static final String cartItemPrice = "FoodPrice";



    public DatabaseHelper(@Nullable Context context) {
        super(context, macroMenuDB, null, 1);

//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + userTable + "(Email Text Primary key, Name Text, Password Text)");
        db.execSQL("Create Table " + itemTable + "(Name Text Primary key, Description Text, Price Text, Type Text)");
        db.execSQL("Create Table " + cartTable + "(FoodName Text, FoodPrice Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + userTable);
        db.execSQL("Drop Table if Exists " + itemTable);
        db.execSQL("Drop Table if Exists " + cartTable);
        onCreate(db);
    }

    public boolean insertData(String Name, String Email, String Password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(userName, Name);
        contentValues.put(userEmail, Email);
        contentValues.put(userPass, Password);

        long result = db.insert(userTable, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void deleteDataFromCart(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("Delete From CartTable");
    }

    public boolean insertDataIntoProduct(String Name, String Desc, String Price, String Type){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(itemName, Name);
        contentValues.put(itemDesc, Desc);
        contentValues.put(itemPrice, Price);
        contentValues.put(itemType, Type);

        long result = db.insert(itemTable, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getItemName(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Name From ItemTable", null);

        return cursor;
    }

    public Cursor getItemDescription(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Description From ItemTable", null);

        return cursor;
    }

    public Cursor getItemPrice(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Price From ItemTable", null);

        return cursor;
    }

    public Cursor getItemsByType(String Type){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From ItemTable Where Type = '" + Type + "'", null);

        return cursor;
    }

    public Cursor getItemTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Distinct Type From ItemTable", null);

        return cursor;
    }

    public boolean deleteDataFromProduct(String Name){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(itemTable,"Name=?",new String[]{Name});
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertDataIntoCart(String FoodName, String FoodPrice){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(cartItemName, FoodName);
        contentValues.put(cartItemPrice, FoodPrice);

        long result = db.insert(cartTable, null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getDataFromCart(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * From CartTable", null);
        return cursor;
    }

    public Cursor checkForRepetition(String emailCheck){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From UserTable Where Email like '" + emailCheck + "'", null);

        return cursor;
    }

    public Cursor checkSignInCredentials(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From UserTable Where Email = '" + email + "' AND Password = '" + password + "'", null);

        return cursor;
    }
}
