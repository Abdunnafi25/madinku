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


public class BelajarSemuaMateri_Adapter extends RecyclerView.Adapter<BelajarSemuaMateri_Adapter.MyViewHolder> {
    private ArrayList<BelajarSemuaMateri_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul;
        TextView txtKet;
        LinearLayout btPindah;


        MyViewHolder(View view) {
            super(view);
            txtJudul = view.findViewById(R.id.txtJudul);
            txtKet = view.findViewById(R.id.txtKet);
            btPindah = view.findViewById(R.id.btPindah);

        }
    }

    public BelajarSemuaMateri_Adapter(ArrayList<BelajarSemuaMateri_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itembelajarsemuamateri, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtJudul.setText(arrayJenis.get(position).getJudul_materi());
        holder.txtKet.setText(arrayJenis.get(position).getKet_materi());
//        holder.btPindah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext,DetailMateriActivity.class);
//                intent.putExtra("id_materi",arrayJenis.get(position).getId_materi());
//                mContext.startActivity(intent);
//            }
//        });

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
