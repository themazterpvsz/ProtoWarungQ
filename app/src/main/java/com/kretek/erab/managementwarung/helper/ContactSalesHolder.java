package com.kretek.erab.managementwarung.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kretek.erab.managementwarung.R;

/**
 * Created by erab on 28/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class ContactSalesHolder extends RecyclerView.ViewHolder {

    //Properties
    public TextView namaSales,perusahaanSales,nomorSales;

    //Constructor
    public ContactSalesHolder(View v) {
        super(v);

        namaSales = v.findViewById(R.id.display_nama_sales);
        perusahaanSales = v.findViewById(R.id.display_sales_from);
        nomorSales = v.findViewById(R.id.display_nomor_kontak);


    }
}
