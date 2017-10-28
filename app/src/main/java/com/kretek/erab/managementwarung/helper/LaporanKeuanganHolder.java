package com.kretek.erab.managementwarung.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kretek.erab.managementwarung.R;

/**
 * Created by erab on 27/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class LaporanKeuanganHolder extends RecyclerView.ViewHolder {
    public TextView nama,jenis,total,hari,lastEdit;

    public LaporanKeuanganHolder(View v) {
        super(v);

        nama = v.findViewById(R.id.display_nama_laporan);
        jenis = v.findViewById(R.id.display_jenis_laporan);
        total = v.findViewById(R.id.display_total_laporan);
        hari = v.findViewById(R.id.display_hari_laporan);
        lastEdit = v.findViewById(R.id.display_terakhir_edit);
    }
}
