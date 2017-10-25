package com.kretek.erab.managementwarung.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kretek.erab.managementwarung.model.User;

/**
 * Created by Bare on 16/10/17.
 */

public class DataHelper extends SQLiteOpenHelper {

    //DATABASE NAME AND DATABASE VERSION
    private static final String DB_NAME = "WarungQ.db";
    private static final int DB_VER = 1;

    //TABLE AND COLLUMS DATA
    private static final String DT_TABLE_DATA = "data";
    private static final String DATA_COL_ID = "no";
    private static final String DATA_COL_NAME = "nb";
    private static final String DATA_COL_PRICE = "hb";
    private static final String DATA_COL_TOT = "tb";

    //TABLE AND COLLUMS LOGIN
    private static final String DT_TABLE_USER = "main";
    private static final String USER_TABLE_NO = "no";
    private static final String USER_TABLE_USER = "User";
    private static final String USER_TABLE_MAIL = "Email";
    private static final String USER_TABLE_PASSWORD = "Password";

    //SQL STRING TO MAKE TABLE DATA
    private static final String MAKETABLEDATA =  DT_TABLE_DATA + "(" + DATA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + DATA_COL_NAME + " TEXT NOT NULL, " + DATA_COL_PRICE + " TEXT NOT NULL, " + DATA_COL_TOT + " TEXT NOT NULL);";

    //SQL STRING TO MAKE TABLE USER
    private static final String MAKETABLEUSER = DT_TABLE_USER + "(" + USER_TABLE_NO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + USER_TABLE_USER +" TEXT NOT NULL, "+ USER_TABLE_MAIL + " TEXT NOT NULL, " + USER_TABLE_PASSWORD +" TEXT NOT NULL);";

    //STRING TO ADD GOODS EXAMPLE
    private static final int no = 0;
    private static final String example = "Dji Sam Soe";
    private static final String harga = "Rp. 12000";
    private static final String total = "10 Slop @10 Bks.";
    //STRING ADMIN AND PASSWORD
    private static final String ADMINUSER = "admin";
    private static final String ADMINEMAIL = "generationoftroll@gmail.com";
    private static final String ADMINPASS = "admon";

    //STRING USER AND PASSWORD (Optional, research purpose only)
    private static final String USER = "user";
    private static final String EMAIL = "aaaa@aaa.com";
    private static final String PASSWORD = "aaaaaa";

    //DataHelper
    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //THIS FUNCTION TO CHECK DATA USER
    public boolean checkUser(String user){
        String[] kolom = {USER_TABLE_NO};

        SQLiteDatabase db = this.getReadableDatabase();

        String selecton = USER_TABLE_MAIL + " = ?";
        String[] selectionArgs = {user};

        Cursor cursor = db.query(DT_TABLE_USER,kolom,selecton,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0 ){
            return true;
        }
        return false;
    }

    //THIS TOO
    public boolean checkUser(String user,String pass){
        String[] kolom = {USER_TABLE_NO};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = USER_TABLE_USER +  " = ?"+ " AND " + USER_TABLE_PASSWORD + " = ?";

        String[] selectionArgs = {user,pass};

        Cursor cursor = db.query(DT_TABLE_USER,kolom,selection,selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();

        cursor.close();

        if (cursorCount > 0 ) {
            return true;
        }
        return false;
    }

    //ONCREATE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL to make table
        Log.d("Data" , "onCreate : " + MAKETABLEDATA);
        db.execSQL("CREATE TABLE " + MAKETABLEDATA);
        Log.d("User","onCreate : " + MAKETABLEUSER);
        db.execSQL("CREATE TABLE " + MAKETABLEUSER);
        //execSQL to make admin
        Log.d("User" , "onCreate: ADMIN" );
        db.execSQL("INSERT INTO " + DT_TABLE_USER +"("+ USER_TABLE_USER + "," + USER_TABLE_MAIL +"," + USER_TABLE_PASSWORD +") VALUES('"+ ADMINUSER + "','"+ ADMINEMAIL + "','"+ ADMINPASS + "');");
        //example some data
        db.execSQL("INSERT INTO " + DT_TABLE_DATA +"(" + DATA_COL_ID + "," + DATA_COL_NAME + "," + DATA_COL_PRICE +"," + DATA_COL_TOT+") VALUES('" + no + "','" + example + "','" + harga + "','" + total + "');");
    }

    //THIS METHOD WILL USE TO CHANGE PASSWORD ADMIN
    public void updatePassword(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_TABLE_PASSWORD,user.getPassword());

        //UPDATING ROW
        db.execSQL("UPDATE main SET ('" + USER_TABLE_PASSWORD +"') VALUE('" + values +"') WHERE no =" + user.getId());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int usang, int lawas) {
        db.execSQL("CREATE TABLE DROP IF EXISTS " + MAKETABLEDATA);

        onCreate(db);
    }
}
