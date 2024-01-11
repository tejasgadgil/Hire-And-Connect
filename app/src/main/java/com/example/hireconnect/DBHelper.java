package com.example.hireconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.example.hireconnect.params.Params;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Params.USER_TABLE_NAME);

        String createUserTableQuery = "CREATE TABLE " + Params.USER_TABLE_NAME + "("
                + Params.USER_KEY_ID + " INTEGER PRIMARY KEY, "
                + Params.USER_KEY_USERNAME + " TEXT NOT NULL UNIQUE, "
                + Params.USER_KEY_PASSWORD + " TEXT NOT NULL, "
                + Params.USER_KEY_NAME + " TEXT, "
                + Params.USER_KEY_ADDRESS + " TEXT, "
                + Params.USER_KEY_PHNO + " TEXT UNIQUE)";

        db.execSQL(createUserTableQuery);

        String createTraderTableQuery = "CREATE TABLE " + Params.TRADER_TABLE_NAME + "("
                + Params.TRADER_KEY_ID + " INTEGER PRIMARY KEY, "
                + Params.TRADER_KEY_USERNAME + " TEXT NOT NULL UNIQUE, "
                + Params.TRADER_KEY_PASSWORD + " TEXT NOT NULL, "
                + Params.TRADER_KEY_NAME + " TEXT, "
                + Params.TRADER_KEY_PHNO + " TEXT UNIQUE,"
                + Params.TRADER_KEY_COST_UB + " FLOAT, "
                + Params.TRADER_KEY_COST_LB + " FLOAT, "
                + Params.TRADER_KEY_PLUMBER + " TEXT, "
                + Params.TRADER_KEY_ELECTRICIAN + " TEXT, "
                + Params.TRADER_KEY_BEAUTICIAN + " TEXT, "
                + Params.TRADER_KEY_LAUNDRY + " TEXT" +
                ")";

        db.execSQL(createTraderTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("drop Table if exists " + Params.USER_TABLE_NAME);
    }


    //insert username and password in user table
    public boolean insertNotNullInUserTable(String usernameTXT, String passwordTXT) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.USER_KEY_USERNAME, usernameTXT);
        values.put(Params.USER_KEY_PASSWORD, passwordTXT);
        long result = DB.insert(Params.USER_TABLE_NAME, null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    //insert username and password in user table
    public boolean insertNotNullInTraderTable(String usernameTXT, String passwordTXT) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.TRADER_KEY_USERNAME, usernameTXT);
        values.put(Params.TRADER_KEY_PASSWORD, passwordTXT);
        long result = DB.insert(Params.TRADER_TABLE_NAME, null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    //insert profile details in trader table (update profile details when username is given)
    public boolean insertNullInUserTable(String usernameTXT,String nameTXT, String phnoTXT, String addressTXT) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.USER_KEY_NAME, nameTXT);
        values.put(Params.USER_KEY_PHNO, phnoTXT);
        values.put(Params.USER_KEY_ADDRESS, addressTXT);
        String whereClause = Params.USER_KEY_USERNAME+ " = ?";
        String[] whereArgs = {usernameTXT};
        int result = DB.update(Params.USER_TABLE_NAME, values, whereClause, whereArgs);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    //insert profile details in trader table (update profile details when username is given)
    public boolean insertNullInTraderTable(String usernameTXT, String nameTXT, String phnoTXT, float costRangeUB, float costRangeLB, Boolean isPlumberSelected, Boolean isElectricianSelected, Boolean isBeauticianSelected, Boolean isLaundrySelected) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.TRADER_KEY_NAME, nameTXT);
        values.put(Params.TRADER_KEY_PHNO, phnoTXT);
        values.put(Params.TRADER_KEY_COST_UB, costRangeUB);
        values.put(Params.TRADER_KEY_COST_LB, costRangeLB);
        values.put(Params.TRADER_KEY_PLUMBER, isPlumberSelected);
        values.put(Params.TRADER_KEY_ELECTRICIAN, isElectricianSelected);
        values.put(Params.TRADER_KEY_BEAUTICIAN, isBeauticianSelected);
        values.put(Params.TRADER_KEY_LAUNDRY, isLaundrySelected);
        String whereClause = Params.TRADER_KEY_USERNAME+ " = ?";
        String[] whereArgs = {usernameTXT};
        int result = DB.update(Params.TRADER_TABLE_NAME, values, whereClause, whereArgs);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }


    //validates to check if password is correct in user table
    public int validateUserPassword(String usernameTXT, String passwordTXT) {
        String pass = getData(Params.USER_KEY_PASSWORD, Params.USER_TABLE_NAME, Params.TRADER_KEY_USERNAME, usernameTXT);
        if(pass==null){
            return -1;
        }else if(pass.equals(passwordTXT)){
            return 1;
        }else{
            return 0;
        }
    }

    //validates to check if password is correct in user table
    public int validateTraderPassword(String usernameTXT, String passwordTXT) {
        String pass = getData(Params.TRADER_KEY_PASSWORD, Params.TRADER_TABLE_NAME, Params.TRADER_KEY_USERNAME, usernameTXT);
        if(pass==null){
            return -1;
        }else if(pass.equals(passwordTXT)){
            return 1;
        }else {
            return 0;
        }
    }


    //return correct password
    public String getData(String toSelect, String table, String column, String specificName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String query = "SELECT "+ toSelect + " FROM " + table + " WHERE " + column + " = ?";
        String[] selectionArgs = {specificName};
        Cursor cursor = DB.rawQuery(query, selectionArgs);
        String pass = null;
        while(cursor.moveToNext()){
            Log.d("tejas", "Password retrieved by tejas is : " +cursor.getString(0));
            pass = cursor.getString(0);
        }
        return pass;
        //pass = -1 -> entry not found
        //pass = 1  -> correct password
        //pass = 0  -> wrong password
    }



}
