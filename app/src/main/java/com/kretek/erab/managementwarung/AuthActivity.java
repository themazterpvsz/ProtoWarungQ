package com.kretek.erab.managementwarung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kretek.erab.managementwarung.helper.DataHelper;
import com.kretek.erab.managementwarung.helper.Validasi;
import com.kretek.erab.managementwarung.model.Session;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AuthActivity.this;
    public EditText user, password;
    private Button login;
    private DataHelper dbHelper;
    private Validasi validasi;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (session.getAlreadyLoggedInAsAdmin()){
                Intent su = new Intent(AuthActivity.this, UserActivity.class);
                su.putExtra("main", session.getUser());
                startActivity(su);
                finish();
            } else if (session.getAlreadyLoggedIn()){
            Intent us = new Intent(AuthActivity.this, UserActivity.class);
            us.putExtra("main", session.getUser());
            startActivity(us);
            finish();
        }
        getSupportActionBar().hide();

        initView();
        initListener();
        initObject();
    }

    //This method to initialize View
    private void initView() {
        user = (EditText) findViewById(R.id.input_user);
        password = (EditText) findViewById(R.id.input_password);

        login = (Button) findViewById(R.id.login);
    }

    //This method to initialize Listener
    private void initListener() {
        login.setOnClickListener(this);
    }

    //This method to initialize object to be used
    private void initObject() {
        dbHelper = new DataHelper(activity);
        validasi = new Validasi(activity);
    }

    @Override
    public void onClick(View arg0) {
        verifyFromSQLite();
    }

    private void verifyFromSQLite() {

        String id = user.getText().toString().trim();
        String pw = password.getText().toString().trim();

        if (id.equals("admin")) {
            if (dbHelper.checkUser(id, pw)) {
                session.saveBooleanAsAdmin(Session.loggedInAsAdmin, true);
                Intent a = new Intent(AuthActivity.this, UserActivity.class);
                a.putExtra("main", user.getText().toString().trim());
                startActivity(a);
                Toast.makeText(getApplicationContext(), "Berhasil Masuk sebagai Admin", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                emptyEditText();
                Toast.makeText(getApplicationContext(), "Gagal untuk login, Coba cek User atau Password anda", Toast.LENGTH_LONG).show();
            }
        } else if (dbHelper.checkUser(id, pw)) {
//                session.saveBoolean(Session.loggedIn, true);
                Intent b = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(b);
            Toast.makeText(getApplicationContext(), "Account User belum bisa diberlakukan.. mohon maaf atas ketidaknyamananya", Toast.LENGTH_SHORT).show();
                finish();
        } else {
            emptyEditText();
            Toast.makeText(getApplicationContext(), "Gagal untuk login, Coba cek User atau Password anda", Toast.LENGTH_LONG).show();
        }
    }

    private void emptyEditText() {
        user.setText(null);
        password.setText(null);
    }
}
