package com.kretek.erab.managementwarung.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kretek.erab.managementwarung.model.User;

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

    //TABLE AND COLLUMS LAPORAN KEUANGAN
    private static final String DT_TABLE_LAP = "laporan";
    private static final String LAP_TABLE_NO = "no";
    private static final String LAP_TABLE_NAME = "nl";
    private static final String LAP_TABLE_JENIS = "jl";
    private static final String LAP_TABLE_TOT =" tl";
    private static final String LAP_TABLE_DAY = "hl";
    private static final String LAP_TABLE_DATE = "dt";

    //TABLE AND COLLUMS CONTACT SALES
    private static final String DT_TABLE_CONTACT = "contact";
    private static final String noSales = "no";
    private static final String namaSales = "ns";
    private static final String companySales = "cs";
    private static final String phoneSales = "ps";

    //SQL STRING TO MAKE TABLE DATA, USER, LAPORAN AND CONTACT
    private static final String MAKETABLEDATA =  DT_TABLE_DATA + "(" + DATA_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + DATA_COL_NAME + " TEXT NOT NULL, " + DATA_COL_PRICE + " TEXT NOT NULL, " + DATA_COL_TOT + " TEXT NOT NULL);";
    private static final String MAKETABLEUSER = DT_TABLE_USER + "(" + USER_TABLE_NO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + USER_TABLE_USER +" TEXT NOT NULL, "+ USER_TABLE_MAIL + " TEXT NULL, " + USER_TABLE_PASSWORD +" TEXT NOT NULL);";
    private static final String MAKETABLELAP = DT_TABLE_LAP + "(" + LAP_TABLE_NO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + LAP_TABLE_NAME + " TEXT NOT NULL," + LAP_TABLE_JENIS + " TEXT NOT NULL, " + LAP_TABLE_TOT + " TEXT NOT NULL,"+ LAP_TABLE_DAY + " DATETIME DEFAULT CURRENT_DATE, " + LAP_TABLE_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
    private static final String MAKETABLECONTACT =  DT_TABLE_CONTACT + "(" + noSales + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + namaSales +" TEXT NOT NULL, " + companySales +" TEXT NOT NULL, "+ phoneSales +" TEXT NOT NULL);";

    //SAMPLE
    private static final int no = 0;
    private static final String example = "Dji Sam Soe";
    private static final String harga = "Rp. 12000";
    private static final String total = "10 Slop @10 Bks.";

    //DataHelper
    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //THIS FUNCTION TO CHECK DATA USER
    public boolean checkUser(String user,String pass){
        String[] kolom = {USER_TABLE_NO};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = USER_TABLE_USER +  " = ?"+ " AND " + USER_TABLE_PASSWORD + " = ?";

        String[] selectionArgs = {user,pass};

        Cursor cursor = db.query(DT_TABLE_USER,kolom,selection,selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();
;
        cursor.close();

        if (cursorCount > 0 ) {
            return true;
        }
        return false;
    }

    //ONCREATE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL to make table data


        Log.d("Data" , "onCreate : " + MAKETABLEDATA);
        db.execSQL("CREATE TABLE " + MAKETABLEDATA);
        //execSQL to make table user
        Log.d("User","onCreate : " + MAKETABLEUSER);
        db.execSQL("CREATE TABLE " + MAKETABLEUSER);
        //execSQL to make table laporan
        Log.d("Laporan", "onCreate : " + MAKETABLELAP);
        db.execSQL("CREATE TABLE " + MAKETABLELAP);
        //execSQL to make table contact
        Log.d("Contact", "onCreate : " + MAKETABLECONTACT);
        db.execSQL("CREATE TABLE " + MAKETABLECONTACT);

        //execSQL to make sample data
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
        db.execSQL("DROP TABLE IF EXISTS " + MAKETABLEDATA);
        db.execSQL("DROP TABLE IF EXISTS " + MAKETABLEUSER);
        db.execSQL("DROP TABLE IF EXISTS " + MAKETABLELAP);
        db.execSQL("DROP TABLE IF EXISTS " + MAKETABLECONTACT);

        onCreate(db);
    }
}
