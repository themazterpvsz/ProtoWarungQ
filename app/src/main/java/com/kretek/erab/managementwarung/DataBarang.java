package com.kretek.erab.managementwarung;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kretek.erab.managementwarung.adapter.DataBarangAdapter;
import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.helper.DataHelper;
import com.kretek.erab.managementwarung.model.Barang;

import java.util.ArrayList;

public class DataBarang extends Fragment {
    private RecyclerView recyclerView;
    private DataHelper dbHelper;
    private ArrayList<Barang> arrayList = new ArrayList<Barang>();
    protected Cursor cursor;
    public TextView no;
    public static DataBarang ma;
    private DataBarangAdapter adapter;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_data_barang, container, false);
        recyclerView = vg.findViewById(R.id.recycler_data_barang);
        fab = vg.findViewById(R.id.addData);

        ma = this;
        RefreshList();
        AddData();
        return vg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Data Barang");
    }

    public void RefreshList() {
        dbHelper = new DataHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM data ORDER BY 1 ASC", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Barang barang = new Barang();
                    barang.setId(cursor.getString(0));
                    barang.setNamaBarang(cursor.getString(1));
                    barang.setHargaBarang(cursor.getString(2));
                    barang.setJumlahBarang(cursor.getString(3));
                    arrayList.add(barang);
                } while (cursor.moveToNext());
            }
        }

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        adapter = new DataBarangAdapter(getActivity(), arrayList);
        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(final int position, final String nama) {
                //TODO 24/10/17 : membuat 2 menu, yaitu Update Data dan Delete Data
                //TODO : HARUS SELESAI DALAM WAKTU 2 HARI KEDEPAN!!
                //UPDATE : KELAR!

                final CharSequence[] dialogitem = {"Update Data", "Delete Data"};
                AlertDialog.Builder option = new AlertDialog.Builder(getActivity());
                option.setTitle("Pilihan");
                option.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            //Ketika Dialog Update Diklik
                            case 0:
                                AlertDialog.Builder update = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View view = inflater.inflate(R.layout.dialog_update_data, null);

                                final EditText upNamaBarang = view.findViewById(R.id.inputNamaBarang);
                                final EditText upHargaBarang = view.findViewById(R.id.inputHargaBarang);
                                final EditText upTotalBarang = view.findViewById(R.id.inputJumlahBarang);

                                no = view.findViewById(R.id.idBarang);
                                SQLiteDatabase db = dbHelper.getReadableDatabase();
                                cursor = db.rawQuery("SELECT * FROM data WHERE nb = '" + nama + "'", null);
                                cursor.moveToFirst();

                                if (cursor.getCount() > 0) {
                                    cursor.moveToPosition(0);
                                    no.setText(cursor.getString(0));
                                    upNamaBarang.setText(cursor.getString(1));
                                    upHargaBarang.setText(cursor.getString(2));
                                    upTotalBarang.setText(cursor.getString(3));
                                }

                                update.setTitle("Update Data Barang");
                                update.setView(view);
                                update.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("UPDATE data SET nb='" +
                                                upNamaBarang.getText().toString() + "', hb='" +
                                                upHargaBarang.getText().toString() + "', tb='" +
                                                upTotalBarang.getText().toString() + "' WHERE no ='" +
                                                no.getText().toString() + "'");

                                        Toast.makeText(getActivity(), "Sukses Update Data", Toast.LENGTH_SHORT).show();
                                        resetData();
                                        DataBarang.ma.RefreshList();
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });
                                update.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                update.create().show();
                                break;
                            //Ketika Dialog delete Diklik
                            case 1:
                                AlertDialog.Builder delete = new AlertDialog.Builder(getActivity());
                                delete.setTitle("Konfirmasi");
                                delete.setMessage(R.string.konfirmasi_delete);
                                delete.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("DELETE FROM data WHERE nb='" + nama + "'");
                                        resetData();
                                        DataBarang.ma.RefreshList();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Sukses Delete Data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                delete.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    public void AddData() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inf = getActivity().getLayoutInflater();
                final View view = inf.inflate(R.layout.dialog_tambah_data, null);

                final EditText inpNamaBarang = view.findViewById(R.id.inputNamaBarang);
                final EditText inpHargaBarang = view.findViewById(R.id.inputHargaBarang);
                final EditText inpJumlahBarang = view.findViewById(R.id.inputJumlahBarang);

                builder.setTitle("Tambah Data Barang");
                builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("INSERT INTO data(nb,hb,tb) VALUES('" +
                                inpNamaBarang.getText().toString() + "','" +
                                inpHargaBarang.getText().toString() + "','" +
                                inpJumlahBarang.getText().toString() + "')");

                        Toast.makeText(getContext(), "SUKSES TAMBAH DATA", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        resetData();
                        DataBarang.ma.RefreshList();

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(view);
                builder.create().show();
            }
        });
    }

    //Untuk mereset data ketika user telah menambahkan data baru
    public void resetData() {
        arrayList.clear();
    }

}