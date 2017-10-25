package com.kretek.erab.managementwarung;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kretek.erab.managementwarung.model.Session;

public class AdminActivity extends AppCompatActivity {
    Button logout;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        session = new Session(this);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    session.logOut();
                Intent back = new Intent(AdminActivity.this,AuthActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                Toast.makeText(getApplicationContext(), "Anda berhasil logout", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
