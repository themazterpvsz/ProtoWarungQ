package com.kretek.erab.managementwarung.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.R;
import com.kretek.erab.managementwarung.helper.DataBarangHolder;
import com.kretek.erab.managementwarung.model.Barang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by erab on 23/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class DataBarangAdapter extends RecyclerView.Adapter<DataBarangHolder> {
    private Activity activity;
    List<Barang> barang = Collections.emptyList();
    private OnTapListener onTapListener;
    private AdapterView.OnItemClickListener itemClickListener;

    public DataBarangAdapter(Activity activity, List<Barang> barang) {
        this.setHasStableIds(true);
        this.activity = activity;
        this.barang = barang;
    }

    @Override
    public DataBarangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_barang, parent, false);
        return new DataBarangHolder(v);
    }

    //    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
//        itemClickListener = onItemClickListener;
//    }
    @Override
    public void onBindViewHolder(final DataBarangHolder holder, int position) {
        holder.namaBarang.setText(barang.get(position).getNamaBarang());
        holder.hargaBarang.setText(barang.get(position).getHargaBarang());
        holder.jumlahBarang.setText(barang.get(position).getJumlahBarang());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onTapListener != null) {
                    onTapListener.OnTapView(holder.getAdapterPosition(), barang.get(holder.getAdapterPosition()).getNamaBarang());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return barang != null ? barang.size() : 0;
    }

    public void setOnTapListener(OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }

    public void updateList(List<Barang> list){
        barang = list;
        notifyDataSetChanged();
    }

}