package com.kretek.erab.managementwarung.custompreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kretek.erab.managementwarung.R;
import com.kretek.erab.managementwarung.helper.DataHelper;

import java.lang.reflect.Field;

/**
 * Created by erab on 01/11/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class UpdatePassword extends DialogPreference implements DialogInterface.OnClickListener{

    private EditText password;
    private EditText rPassword;
    private DataHelper dbHelper;

    public UpdatePassword(Context context, AttributeSet attrs) {
        super(context, attrs);


    }
    protected View onCreateDialogView(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_password,null);


        password = v.findViewById(R.id.updatePassword);
        rPassword = v.findViewById(R.id.updatePasswordRepeat);

        return v;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {


        dbHelper = new DataHelper(getContext());
        if (which == DialogInterface.BUTTON_POSITIVE && !validation(password.getText().toString().trim(),rPassword.getText().toString().trim())){

            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                field.set(dialog,false);
            } catch (NoSuchFieldException e){
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }

        } else {

            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                field.set(dialog,true);
            } catch (NoSuchFieldException e){
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }


        } if(which == DialogInterface.BUTTON_NEGATIVE){
            dialog.dismiss();
        }


    }



    private boolean validation(String newPassword, String repeatPassword){

        if (!newPassword.equals(repeatPassword)){
            Toast.makeText(getContext(), "Password Tidak Sama Mas'e", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(getContext(), "Isi Datanya Dlu Mas'e", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE main SET Password='" + newPassword + "' WHERE User ='admin'");

            Toast.makeText(getContext(), "Berhasil Update Password!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
