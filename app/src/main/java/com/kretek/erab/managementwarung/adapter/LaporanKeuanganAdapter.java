package com.kretek.erab.managementwarung.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kretek.erab.managementwarung.R;
import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.helper.LaporanKeuanganHolder;
import com.kretek.erab.managementwarung.model.Laporan;

import java.util.Collections;
import java.util.List;

/**
 * Created by erab on 27/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class LaporanKeuanganAdapter extends RecyclerView.Adapter<LaporanKeuanganHolder> {
    private Activity activity;
    List<Laporan> laporan = Collections.emptyList();
    private OnTapListener onTapListener;
    public AdapterView.OnClickListener itemClickListener;

    public LaporanKeuanganAdapter(Activity activity, List<Laporan> laporan){
        this.setHasStableIds(true);
        this.activity = activity;
        this.laporan = laporan;
    }
    @Override
    public LaporanKeuanganHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_laporan_keuangan,parent,false);
        return new LaporanKeuanganHolder(v);
    }

    @Override
    public void onBindViewHolder(final LaporanKeuanganHolder holder, int position) {

        holder.nama.setText(laporan.get(position).getNamaLaporan());
        holder.jenis.setText(laporan.get(position).getJenisLaporan());
        holder.total.setText(laporan.get(position).getTotal());
        holder.hari.setText(laporan.get(position).getHariInput());
        holder.lastEdit.setText(laporan.get(position).getLastEdit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTapListener != null) {
                    onTapListener.OnTapView(holder.getAdapterPosition(), laporan.get(holder.getAdapterPosition()).getNamaLaporan());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return laporan != null ? laporan.size() : 0;
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
