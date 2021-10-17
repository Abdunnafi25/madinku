package com.siupindo.madinku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Ujian_Adapter extends RecyclerView.Adapter<Ujian_Adapter.MyViewHolder> {
    private ArrayList<Ujian_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomor;


        MyViewHolder(View view) {
            super(view);
            txtNomor = view.findViewById(R.id.txtNomor);

        }
    }

    public Ujian_Adapter(ArrayList<Ujian_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nomor, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtNomor.setText(arrayJenis.get(position).getNomor());
        if (arrayJenis.get(position).getJawaban().equalsIgnoreCase("0")) {
            holder.txtNomor.setBackgroundResource(R.drawable.shape_edittext_login);
            holder.txtNomor.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            holder.txtNomor.setBackgroundResource(R.drawable.shape_bt_login1);
            holder.txtNomor.setTextColor(mContext.getResources().getColor(R.color.white));
        }


            holder.txtNomor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((UjianActivity) mContext).nomor_baru = arrayJenis.get(position).getId_soal();
                    ((UjianActivity) mContext).getSoal(arrayJenis.get(position).getId_soal());
                }
            });



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
