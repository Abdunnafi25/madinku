package com.siupindo.madinku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


public class Bank_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Bank_Model> data_beritaList;
    private ArrayList<Bank_Model> arraylist;

    public Bank_Adapter(Context context, List<Bank_Model> worldpopulationlist) {
        this.data_beritaList = worldpopulationlist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView txtNamaBank;
        TextView txtNama;
        TextView txtNorek;
    }

    @Override
    public int getCount() {
        return data_beritaList.size();
    }

    @Override
    public Bank_Model getItem(int position) {
        return data_beritaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_bank, null);
            // Locate the TextViews in listview_item.xml
            holder.txtNama = view.findViewById(R.id.txtNama);
            holder.txtNamaBank = view.findViewById(R.id.txtNamaBank);
            holder.txtNorek = view.findViewById(R.id.txtNorek);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtNama.setText(data_beritaList.get(position).getNama());
        holder.txtNamaBank.setText(data_beritaList.get(position).getNama_bank());
        holder.txtNorek.setText(data_beritaList.get(position).getNomor_rekening());


        return view;
    }
}
