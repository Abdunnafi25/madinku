package com.siupindo.madinku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class DashboardActivity extends AppCompatActivity {
    RecyclerView list;
    Materi_Adapter adapter;
    ArrayList<Materi_Model> arraylist = new ArrayList<>();

    TextView txtNama, txtVidioDilihat, txtSelei, txtNilai, txtKelasPengguna;
    LinearLayout btBelajar,btBeli;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bacaPreferensi();
        txtNama = findViewById(R.id.txtNama);
        txtVidioDilihat = findViewById(R.id.txtVidioDilihat);
        txtKelasPengguna = findViewById(R.id.txtKelasPengguna);
        txtSelei = findViewById(R.id.txtSelei);
        txtNilai = findViewById(R.id.txtNilai);
        list = findViewById(R.id.recycler_view);

        btBelajar = findViewById(R.id.btBelajar);
        btBeli = findViewById(R.id.btBeli);

        btBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,BalajarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,Beli_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout btProfil = findViewById(R.id.btProfil);
        btProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });
        adapter = new Materi_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        getDashboard();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }

    public void getDashboard() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_beranda")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(DashboardActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {
                                txtNama.setText(json.getString("nama_pengguna"));
                                txtKelasPengguna.setText(json.getString("kelas_pengguna"));

                                JSONObject responsedilihat = json.getJSONObject("info_atas");
                                txtVidioDilihat.setText(responsedilihat.getString("vidiodilihat") + "/" + responsedilihat.getString("vidio_total"));
                                txtSelei.setText(responsedilihat.getString("materibelum") + "/" + responsedilihat.getString("materitotal"));
                                txtNilai.setText(responsedilihat.getString("nilairata"));

                                JSONArray jsonArray1 = json.getJSONArray("materi");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    Materi_Model wp = new Materi_Model(data.getString("id_materi"),data.getString("judul"));
                                    arraylist.add(wp);

                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DashboardActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DashboardActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
