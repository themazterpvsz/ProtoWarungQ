package com.kretek.erab.managementwarung;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kretek.erab.managementwarung.adapter.ContactSalesAdapter;
import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.helper.DataHelper;
import com.kretek.erab.managementwarung.model.Contact;

import java.util.ArrayList;

public class ContactSales extends Fragment {
    private RecyclerView recyclerView;
    private DataHelper dbHelper;
    private ArrayList<Contact> arrayList = new ArrayList<Contact>();
    protected Cursor cursor;
    public static ContactSales ma;
    public TextView no;
    private ContactSalesAdapter adapter;
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_contact_sales,container,false);

        fab = v.findViewById(R.id.addContact);
        recyclerView = v.findViewById(R.id.recycler_contact_sales);

        ma = this;
        refreshList();
        addData();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Kontak Sales");
    }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    public void refreshList() {
        dbHelper = new DataHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact ORDER BY 1 ASC", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setId(cursor.getString(0));
                    contact.setNs(cursor.getString(1));
                    contact.setCs(cursor.getString(2));
                    contact.setTelcs(cursor.getString(3));
                    arrayList.add(contact);
                } while (cursor.moveToNext());
            }
        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        adapter = new ContactSalesAdapter(getActivity(), arrayList);
        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position, @Nullable final String nama) {
                final CharSequence[] dialogitem = {"Telepon " + nama ,"SMS " + nama ,"Edit Kontak", "Delete Kontak"};
                AlertDialog.Builder option = new AlertDialog.Builder(getActivity());
                option.setTitle("Pilihan");
                option.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                String numberTelp;

                                SQLiteDatabase n = dbHelper.getReadableDatabase();
                                cursor = n.rawQuery("SELECT * FROM contact WHERE ns= '" + nama +"'",null);
                                cursor.moveToFirst();
                                if (cursor.getCount()>0){
                                    cursor.moveToPosition(0);

                                    numberTelp = cursor.getString(3);

                                    Intent telp = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + numberTelp));
                                    startActivity(telp);
                                }
                                break;
                            case 1:
                                String numberSMS;

                                SQLiteDatabase a = dbHelper.getReadableDatabase();
                                cursor = a.rawQuery("SELECT * FROM contact WHERE ns= '" + nama +"'",null);
                                cursor.moveToFirst();
                                if (cursor.getCount()>0){
                                    cursor.moveToPosition(0);

                                    numberSMS = cursor.getString(3);

                                    Intent SMS = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: " + numberSMS));
                                    startActivity(SMS);
                                }
                                break;
                            case 2:
                                AlertDialog.Builder update = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View v = inflater.inflate(R.layout.dialog_update_contact, null);

                                final EditText inputNamaSales = v.findViewById(R.id.inputNamaSales);
                                final EditText inputNamaPerusahaan = v.findViewById(R.id.inputNamaPerusahaan);
                                final EditText inputNoTel = v.findViewById(R.id.inputNomorTelepon);

                                no = v.findViewById(R.id.idContact);

                                SQLiteDatabase db = dbHelper.getReadableDatabase();
                                cursor = db.rawQuery("SELECT * FROM contact WHERE ns ='" + nama + "'", null);
                                cursor.moveToFirst();
                                if (cursor.getCount() > 0) {
                                    cursor.moveToPosition(0);
                                    no.setText(cursor.getString(0));
                                    inputNamaSales.setText(cursor.getString(1));
                                    inputNamaPerusahaan.setText(cursor.getString(2));
                                    inputNoTel.setText(cursor.getString(3));

                                }
                                update.setTitle("Update Kontak");
                                update.setView(v);
                                update.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("UPDATE contact SET ns = '" +
                                                inputNamaSales.getText() + "', cs = '" +
                                                inputNamaPerusahaan.getText() + "', ps = '" +
                                                inputNoTel.getText() + "' WHERE no = '" +
                                                no.getText() + "'");

                                        Toast.makeText(getActivity(), "Sukses Update Data", Toast.LENGTH_SHORT).show();
                                        arrayList.clear();
                                        refreshList();
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });
                                update.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                update.create().show();
                                break;
                            case 3:
                                AlertDialog.Builder delete = new AlertDialog.Builder(getActivity());
                                delete.setTitle("Konfirmasi");
                                delete.setMessage(R.string.konfirmasi_delete);
                                delete.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("DELETE FROM contact WHERE ns = '" + nama +"'");

                                        Toast.makeText(getActivity(), "Berhasil Delete Contact!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                delete.create().show();
                                break;
                        }
                    }
                });
                option.create().show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

    }
    public void addData(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View vi = inflater.inflate(R.layout.dialog_add_contact,null);

                final EditText inputNamaSales = vi.findViewById(R.id.inputContactName);
                final EditText inputSalesComp = vi.findViewById(R.id.inputCompanySales);
                final EditText inputNumSales = vi.findViewById(R.id.inputContactNumber);

                AlertDialog.Builder create = new AlertDialog.Builder(getActivity());
                create.setTitle("Tambah Data");
                create.setView(vi);
                create.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("INSERT INTO contact (ns,cs,ps) VALUES('" +
                        inputNamaSales.getText().toString() + "', '" +
                        inputSalesComp.getText().toString() + "', '" +
                        inputNumSales.getText().toString() + "');");

                        Toast.makeText(getActivity(), "Sukses Tambah Kontak!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        arrayList.clear();
                        ContactSales.ma.refreshList();
                    }
                });
                create.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                create.create().show();
            }
        });
    }

}
