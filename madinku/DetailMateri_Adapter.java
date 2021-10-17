package com.siupindo.madinku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DetailMateri_Adapter extends RecyclerView.Adapter<DetailMateri_Adapter.MyViewHolder> {
    private ArrayList<DetailMateri_Model> arrayJenis;
    private Context mContext;
    Intent intent;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaModul;
        ImageView imgcek;
        LinearLayout btPindah;


        MyViewHolder(View view) {
            super(view);
            txtNamaModul = view.findViewById(R.id.txtNamaModul);
            imgcek = view.findViewById(R.id.imgcek);
            btPindah = view.findViewById(R.id.btPindah);

        }
    }

    public DetailMateri_Adapter(ArrayList<DetailMateri_Model> arrayJenis, Context context) {
        this.arrayJenis = arrayJenis;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_materi_modul, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtNamaModul.setText(arrayJenis.get(position).getNama_modul());
        if (arrayJenis.get(position).getTonton().equals("1")) {
            holder.imgcek.setBackgroundResource(R.drawable.ic_ceklis21);
        } else {
            holder.imgcek.setBackgroundResource(R.drawable.ic_ceklis22);
        }

        holder.btPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayJenis.get(position).getPembelian().equalsIgnoreCase("no")) {
                    Toast.makeText(mContext, "Silahkan lakukan pembelian terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent= new Intent(mContext,VideoMateriActivity.class);
                    intent.putExtra("content",arrayJenis.get(position).getContent());
                    intent.putExtra("id_module",arrayJenis.get(position).getId_module());
                    intent.putExtra("id_materi",((DetailMateriActivity)mContext).id_materi);
                    mContext.startActivity(intent);
                    ((DetailMateriActivity)mContext).finish();
                }
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
