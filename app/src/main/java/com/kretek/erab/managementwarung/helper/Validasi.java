package com.kretek.erab.managementwarung.helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by erab on 16/10/17.
 */

public class Validasi {
    private Context context;


public Validasi(Context context){
    this.context = context;
    }

    public boolean checkInputUser(EditText user){
        String value = user.getText().toString().trim();
        if (value.isEmpty()) {
            Toast.makeText(context, "Tolong Datanya Diisi Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            hideKeyboardFrom(user);
            return false;
        }
        return true;
    }
    public boolean checkInputPassword(EditText password){
        String value = password.getText().toString().trim();
        if (value.isEmpty()) {
            hideKeyboardFrom(password);
            return false;
        }
        return true;
    }
    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}