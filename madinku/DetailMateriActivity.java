package com.siupindo.madinku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class DetailMateriActivity extends AppCompatActivity {
    RecyclerView list;
    DetailMateri_Adapter adapter;
    ArrayList<DetailMateri_Model> arraylist = new ArrayList<>();
    TextView txtMateri, txtModulSelesai;
    TextView btLanjutkan;

    String jumlah_module_selesai, jumlah_module;

    LinearLayout card;
    public String deskripsi;
    TextView btBack;
    String id_materi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_modul);
        bacaPreferensi();
//        btBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        btLanjutkan = findViewById(R.id.btLanjutkan);
        card = findViewById(R.id.card);
        txtMateri = findViewById(R.id.txtMateri);
        txtModulSelesai = findViewById(R.id.txtModulSelesai);
        list = findViewById(R.id.recycler_view);
        adapter = new DetailMateri_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        getDashboard();

        btLanjutkan = findViewById(R.id.btLanjutkan);
        btLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlah_module_selesai.equals(jumlah_module)) {
                    Intent intent = new Intent(DetailMateriActivity.this,UjianActivity.class);
                    intent.putExtra("id_materi",getIntent().getStringExtra("id_materi"));
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailMateriActivity.this, "Belum bisa mengikuti ujian", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }

    public void getDashboard() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        card.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_detail_materi")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(DetailMateriActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("id_materi", getIntent().getStringExtra("id_materi"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {
                                id_materi = getIntent().getStringExtra("id_materi");
                                txtMateri.setText(json.getString("kategori_materi") + ": " + json.getString("nama_materi"));
                                txtModulSelesai.setText(json.getString("jumlah_module_selesai") + "/" + json.getString("jumlah_module"));
                                deskripsi = json.getString("deskripsi");
                                jumlah_module_selesai = json.getString("jumlah_module_selesai");
                                jumlah_module = json.getString("jumlah_module");

                                JSONArray jsonArray1 = json.getJSONArray("data_module");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    DetailMateri_Model wp = new DetailMateri_Model(data.getString("id_module"), data.getString("nama_module"), data.getString("content"), data.getString("pembelian"), data.getString("tonton"));
                                    arraylist.add(wp);

                                }

                                if (jumlah_module_selesai.equals(jumlah_module)) {
                                    card.setVisibility(View.VISIBLE);
                                } else {
                                    card.setVisibility(View.GONE);
                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DetailMateriActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DetailMateriActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
