package com.siupindo.madinku;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class histori_pembeli extends AppCompatActivity {
    RecyclerView list;
    HistoriPembelian_Adapter adapter;
    ArrayList<historipembelian_Model> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histori_pembelian);

        list = findViewById(R.id.recycler_view);
        adapter = new HistoriPembelian_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        bacaPreferensi();
        getDashboard();
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }

    public void getDashboard() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        //list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "history_semua_pemesanan_paket")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(histori_pembeli.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                JSONArray jsonArray1 = json.getJSONArray("data_pesanan");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    historipembelian_Model wp = new historipembelian_Model(data.getString("tanggal_pesan"), data.getString("status"),data.getString("nominal"), data.getString("nama_paket"));
                                    arraylist.add(wp);
                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(histori_pembeli.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(histori_pembeli.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
