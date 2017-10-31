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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kretek.erab.managementwarung.adapter.LaporanKeuanganAdapter;
import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.helper.DataHelper;
import com.kretek.erab.managementwarung.model.Laporan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LaporanKeuangan extends Fragment {
    private RecyclerView recyclerView;
    private DataHelper dbHelper;
    private ArrayList<Laporan> arrayList = new ArrayList<Laporan>();
    protected Cursor cursor;
    public static LaporanKeuangan ma;
    private LaporanKeuanganAdapter adapter;
    FloatingActionButton fab;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_laporan_keuangan,container,false);

        recyclerView = vg.findViewById(R.id.recycler_laporan_keuangan);

        fab = vg.findViewById(R.id.addLaporan);

        ma = this;
        refreshList();
        addData();
        return vg;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Laporan Keuangan");
    }

    private void refreshList() {
        dbHelper = new DataHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM laporan ORDER BY 1 ASC",null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    Laporan laporan = new Laporan();
                    laporan.setId(cursor.getString(0));
                    laporan.setNamaLaporan(cursor.getString(1));
                    laporan.setJenisLaporan(cursor.getString(2));
                    laporan.setTotal(cursor.getString(3));;
                    laporan.setLastEdit(cursor.getString(5));
                    arrayList.add(laporan);
                } while  (cursor.moveToNext());
            }
        }

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        adapter = new LaporanKeuanganAdapter(getActivity(),arrayList);
        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position, @Nullable final String nama) {
                final CharSequence[] dialogitem = {"Update Laporan", "Delete Laporan"};
                AlertDialog.Builder option = new AlertDialog.Builder(getActivity());
                option.setTitle("Pilihan");
                option.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //TODO : urang lemas.. urang lapar... tolong :"(

                        switch (which) {
                            case 0:
                                AlertDialog.Builder update = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View v = inflater.inflate(R.layout.dialog_update_laporan, null);

                                final EditText updateNamaLap = v.findViewById(R.id.inputNamaLaporan);
                                final Spinner updateJenLap = v.findViewById(R.id.spinner);
                                final EditText updateTotLap = v.findViewById(R.id.inputTotalLaporan);

                                final TextView no = v.findViewById(R.id.idLaporan);

                                SQLiteDatabase db = dbHelper.getReadableDatabase();
                                cursor = db.rawQuery("SELECT * FROM laporan WHERE nl = '" + nama +"'",null);
                                cursor.moveToFirst();

                                if (cursor.getCount()>0){
                                    cursor.moveToPosition(0);
                                    no.setText(cursor.getString(0));
                                    updateNamaLap.setText(cursor.getString(1));
                                    updateTotLap.setText(cursor.getString(3));
                                }

                                update.setTitle("Update Data");
                                update.setView(v);
                                update.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("UPDATE laporan SET nl = '" +
                                        updateNamaLap.getText().toString() +"', jl = '" +
                                        updateJenLap.getSelectedItem().toString() + "', tl='" +
                                        updateTotLap.getText().toString() + "', dt = datetime('now','localtime') WHERE no = '" +
                                        no.getText().toString() + "'");

                                        Toast.makeText(getActivity(), "Sukses Update Data", Toast.LENGTH_SHORT).show();
                                        arrayList.clear();
                                        LaporanKeuangan.ma.refreshList();
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });
                                update.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                update.create().show();
                                break;
                            case 1:
                                AlertDialog.Builder delete = new AlertDialog.Builder(getActivity());
                                delete.setTitle("Konfirmasi");
                                delete.setMessage(R.string.konfirmasi_delete);
                                delete.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("DELETE FROM laporan WHERE nl = '" + nama + "'");
                                        arrayList.clear();
                                        LaporanKeuangan.ma.refreshList();
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
    private String getDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void addData(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View vi = inflater.inflate(R.layout.dialog_tambah_laporan,null);

                final EditText inpNamaLaporan = vi.findViewById(R.id.inputNamaLaporan);
                final Spinner inpJenisLaporan = vi.findViewById(R.id.spinner);
                final EditText inpTotalLaporan = vi.findViewById(R.id.inputTotalLaporan);

                AlertDialog.Builder create = new AlertDialog.Builder(getActivity());
                create.setTitle("Buat Laporan");
                create.setView(vi);
                create.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("INSERT INTO laporan(nl,jl,tl,hl,dt) VALUES('" +
                        inpNamaLaporan.getText().toString() +"','"+
                        inpJenisLaporan.getSelectedItem().toString() + "','" +
                        inpTotalLaporan.getText().toString() + "','"+
                        getDay() + "',datetime('now','localtime'));");

                        Toast.makeText(getActivity(), "Sukses Tambah Laporan!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        arrayList.clear();
                        LaporanKeuangan.ma.refreshList();
                    }
                });
                create.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
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
