package com.kretek.erab.managementwarung.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kretek.erab.managementwarung.R;
import com.kretek.erab.managementwarung.annotation.OnTapListener;
import com.kretek.erab.managementwarung.helper.ContactSalesHolder;
import com.kretek.erab.managementwarung.model.Contact;

import java.util.Collections;
import java.util.List;

/**
 * Created by erab on 28/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class ContactSalesAdapter extends RecyclerView.Adapter<ContactSalesHolder> {
    private Activity activity;
    List<Contact> contact = Collections.emptyList();
    private OnTapListener onTapListener;
    private AdapterView.OnClickListener itemClickListener;

    public ContactSalesAdapter(Activity activity, List<Contact> contact){
            this.setHasStableIds(true);
            this.activity = activity;
            this.contact = contact;
    }

    @Override
    public ContactSalesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_sales,parent,false);
        return new ContactSalesHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactSalesHolder holder, int position) {
        holder.namaSales.setText(contact.get(position).getNs());
        holder.perusahaanSales.setText(contact.get(position).getCs());
        holder.nomorSales.setText(contact.get(position).getTelcs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTapListener != null){
                    onTapListener.OnTapView(holder.getAdapterPosition(), contact.get(holder.getAdapterPosition()).getNs());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contact != null ? contact.size() : 0;
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
