package com.siupindo.madinku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class detail_pembelian_Adapter extends RecyclerView.Adapter<detail_pembelian_Adapter.MyViewHolder> {
    private ArrayList<detail_pembelian_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKet;


        MyViewHolder(View view) {
            super(view);
            txtKet = view.findViewById(R.id.txtKet);

        }
    }

    public detail_pembelian_Adapter(ArrayList<detail_pembelian_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_pembelian, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtKet.setText(arrayJenis.get(position).getNama_kategori()+":\n"+arrayJenis.get(position).getListmateri().replace("-","\n"));


    }

    @Override
    public int getItemCount() {
        return arrayJenis.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
