package com.kretek.erab.managementwarung.helper;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kretek.erab.managementwarung.R;

/**
 * Created by erab on 23/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class DataBarangHolder extends RecyclerView.ViewHolder {

    //Properties
    public TextView namaBarang,hargaBarang,jumlahBarang;

    //Constructor
    public DataBarangHolder(View v) {
        super(v);

        namaBarang = v.findViewById(R.id.display_nama_barang);
        hargaBarang = v.findViewById(R.id.display_harga_barang);
        jumlahBarang = v.findViewById(R.id.display_total_barang);


    }

}
