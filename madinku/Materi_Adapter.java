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


public class Materi_Adapter extends RecyclerView.Adapter<Materi_Adapter.MyViewHolder> {
    private ArrayList<Materi_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama;
        LinearLayout btPindah;


        MyViewHolder(View view) {
            super(view);
            txtNama = view.findViewById(R.id.txtNama);
            btPindah = view.findViewById(R.id.btPindah);

        }
    }

    public Materi_Adapter(ArrayList<Materi_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_materi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtNama.setText(arrayJenis.get(position).getJudul_materi());
        holder.btPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailMateriActivity.class);
                intent.putExtra("id_materi", arrayJenis.get(position).getId_materi());
                mContext.startActivity(intent);
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
