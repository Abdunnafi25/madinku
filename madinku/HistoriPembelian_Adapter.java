package com.siupindo.madinku;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HistoriPembelian_Adapter extends RecyclerView.Adapter<HistoriPembelian_Adapter.MyViewHolder> {
    private ArrayList<historipembelian_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal;
        TextView txtStatus;
        TextView txtPaket;
        TextView txtHarga;


        MyViewHolder(View view) {
            super(view);
            txtTanggal = view.findViewById(R.id.txtTanggal);
            txtStatus = view.findViewById(R.id.txtStatus);
            txtPaket = view.findViewById(R.id.txtPaket);
            txtHarga = view.findViewById(R.id.txtHarga);

        }
    }

    public HistoriPembelian_Adapter(ArrayList<historipembelian_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_histori_pembelian, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
       holder.txtTanggal.setText(arrayJenis.get(position).getTanggal_pesan());
       holder.txtStatus.setText(arrayJenis.get(position).getStatus());
       holder.txtPaket.setText(arrayJenis.get(position).getNama_paket());
       holder.txtHarga.setText(arrayJenis.get(position).getNominal());


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
