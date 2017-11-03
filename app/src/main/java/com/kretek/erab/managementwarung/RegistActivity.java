package com.kretek.erab.managementwarung;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kretek.erab.managementwarung.helper.DataHelper;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText admin,password;
    private Button add;
    private DataHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        getSupportActionBar().hide();

        admin =  findViewById(R.id.createAdmin);
        admin.setTag(admin.getKeyListener());
        admin.setKeyListener(null);
        password =  findViewById(R.id.createPassword);

        add =  findViewById(R.id.buatAdmin);
        add.setOnClickListener(this);

        dbHelper = new DataHelper(this);

    }

    @Override
    public void onClick(View v) {
        registerProcess();
    }

    public void registerProcess(){
        String pw = password.getText().toString().trim();
        if (pw.matches("")){
            Toast.makeText(getApplicationContext(), "Isi Terlebih Dahulu Password nya", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO main (User,Password) VALUES('"+
            admin.getText().toString().toLowerCase() +"','"+
            password.getText().toString().toLowerCase()+"');");

            Toast.makeText(getApplicationContext(), "Berhasil Konfigurasi Password Admin! , Silahkan Login!", Toast.LENGTH_SHORT).show();

            Intent login = new Intent(this,AuthActivity.class);
            startActivity(login);
            finish();
        }
    }

}
