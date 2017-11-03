package com.kretek.erab.managementwarung;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kretek.erab.managementwarung.helper.DataHelper;

public class MainActivity extends AppCompatActivity {
    Button start;
    DataHelper dbHelper;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataHelper(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdminIsRegistered()){
                    AlertDialog.Builder setPassword = new AlertDialog.Builder(MainActivity.this);
                    setPassword.setTitle("Buat Password Admin");
                    setPassword.setCancelable(false);
                    setPassword.setMessage("Tampaknya anda baru disini, anda harus mengkonfigurasi akun terlebih dahulu sebelum melanjutkan");
                    setPassword.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent b = new Intent(MainActivity.this,RegistActivity.class);
                            MainActivity.this.startActivity(b);
                            MainActivity.this.finish();
                        }
                    });
                    setPassword.create().show();
                } else {
                    Intent a = new Intent(MainActivity.this,AuthActivity.class);
                    MainActivity.this.startActivity(a);
                    MainActivity.this.finish();
                }

            }
        }, 2000);

        }

        public boolean isAdminIsRegistered(){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM main",null);
            if (cursor.getCount()>0){
                return false;
            }

            return true;
        }



}
