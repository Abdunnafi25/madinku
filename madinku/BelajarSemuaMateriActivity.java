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

public class BelajarSemuaMateriActivity extends AppCompatActivity {

    RecyclerView list;
    BelajarSemuaMateri_Adapter adapter;
    ArrayList<BelajarSemuaMateri_Model> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.belajar_search);
        bacaPreferensi();

        list = findViewById(R.id.recycler_view);
        adapter = new BelajarSemuaMateri_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        getDashboard();
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }

    public void getDashboard() {
        list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_semua_materi")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(BelajarSemuaMateriActivity.this))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                JSONArray jsonArray1 = json.getJSONArray("materi");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    BelajarSemuaMateri_Model wp = new BelajarSemuaMateri_Model(data.getString("id_materi"), data.getString("kategori")+":"+data.getString("judul"), data.getString("deskripsi"));
                                    arraylist.add(wp);
                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(BelajarSemuaMateriActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(BelajarSemuaMateriActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
