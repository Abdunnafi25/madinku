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

public class BalajarActivity extends AppCompatActivity {
    TextView txtKelas1, txtKelas2, txtKelas3;
    TextView txtKet1, txtKet2, txtKet3;

    RecyclerView list;
    Materi_Adapter adapter;
    ArrayList<Materi_Model> arraylist = new ArrayList<>();
    String id_kelas1, id_kelas2, id_kelas3;

    TextView txtNama, txtVidioDilihat, txtSelei, txtNilai, txtKelasPengguna, btShearch;

    LinearLayout btKelas1,btKelas2,btKelas3;
    LinearLayout btHome,btBeli;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belajar);
        bacaPreferensi();
        txtNama = findViewById(R.id.txtNama);
        txtKelasPengguna = findViewById(R.id.txtKelasPengguna);
        txtKelas1 = findViewById(R.id.txtKelas1);
        txtKelas3 = findViewById(R.id.txtKelas3);
        txtKelas2 = findViewById(R.id.txtKelas2);
        txtKet1 = findViewById(R.id.txtKet1);
        txtKet2 = findViewById(R.id.txtKet2);
        txtKet3 = findViewById(R.id.txtKet3);
        btShearch = findViewById(R.id.btShearch);
        list = findViewById(R.id.recycler_view);

        adapter = new Materi_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        getKelas();
        getDashboard();

        btShearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BalajarActivity.this, BelajarSemuaMateriActivity.class));
            }
        });

        btKelas1 = findViewById(R.id.btKelas1);
        btKelas2 = findViewById(R.id.btKelas2);
        btKelas3 = findViewById(R.id.btKelas3);

        btKelas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,BelajarKelasActivity.class);
                intent.putExtra("idkelas",id_kelas1);
                startActivity(intent);
            }
        });

        btKelas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,BelajarKelasActivity.class);
                intent.putExtra("idkelas",id_kelas2);
                startActivity(intent);
            }
        });
        btKelas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,BelajarKelasActivity.class);
                intent.putExtra("idkelas",id_kelas3);
                startActivity(intent);
            }
        });

        btHome = findViewById(R.id.btHome);
        btBeli = findViewById(R.id.btBeli);
        LinearLayout btProfil = findViewById(R.id.btProfil);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,Beli_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalajarActivity.this,ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
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

    public void getDashboard() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_beranda")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(BalajarActivity.this))
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

                                JSONArray jsonArray1 = json.getJSONArray("materi");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    Materi_Model wp = new Materi_Model(data.getString("id_materi"), data.getString("judul"));
                                    arraylist.add(wp);

                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(BalajarActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(BalajarActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getKelas() {
        AndroidNetworking.post(Const.BASE_URL + "view_menu_belajar")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(BalajarActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {


                                JSONArray jsonArray1 = json.getJSONArray("data_kelas");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data1 = jsonArray1.getJSONObject(0);
                                    txtKelas1.setText(data1.getString("nama_kelas"));
                                    txtKet1.setText(data1.getString("keterangan"));
                                    id_kelas1 = data1.getString("id_kelas");

                                    JSONObject data2 = jsonArray1.getJSONObject(1);
                                    txtKelas2.setText(data2.getString("nama_kelas"));
                                    txtKet2.setText(data2.getString("keterangan"));
                                    id_kelas2 = data2.getString("id_kelas");

                                    JSONObject data3 = jsonArray1.getJSONObject(2);
                                    txtKelas3.setText(data3.getString("nama_kelas"));
                                    txtKet3.setText(data3.getString("keterangan"));
                                    id_kelas3 = data3.getString("id_kelas");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(BalajarActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(BalajarActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
