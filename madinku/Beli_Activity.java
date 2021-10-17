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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Beli_Activity extends AppCompatActivity {
    LinearLayout btHistori;

    TextView txtKelas1, txtKelas2, txtKelas3, txtKelas4;
    TextView txtKet1, txtKet2, txtKet3, txtKet4;

    String id_kelas1, id_kelas2, id_kelas3, id_kelas4;

    LinearLayout btPaket1,btPaket2,btPaket3,btPaket4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        bacaPreferensi();

        btHistori = findViewById(R.id.btHistori);
        btHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Beli_Activity.this, histori_pembeli.class));
            }
        });
        txtKelas1 = findViewById(R.id.txtPaket1);
        txtKelas3 = findViewById(R.id.txtPaket3);
        txtKelas2 = findViewById(R.id.txtPaket2);
        txtKelas4 = findViewById(R.id.txtPaket4);
        txtKet1 = findViewById(R.id.txtKet1);
        txtKet2 = findViewById(R.id.txtKet2);
        txtKet3 = findViewById(R.id.txtKet3);
        txtKet4 = findViewById(R.id.txtKet4);

        btPaket1 = findViewById(R.id.btPaket1);
        btPaket2 = findViewById(R.id.btPaket2);
        btPaket3 = findViewById(R.id.btPaket3);
        btPaket4 = findViewById(R.id.btPaket4);
        btPaket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,Detail_pembelianActivity.class);
                intent.putExtra("idpaket",id_kelas1);
                startActivity(intent);
            }
        });

        btPaket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,Detail_pembelianActivity.class);
                intent.putExtra("idpaket",id_kelas2);
                startActivity(intent);
            }
        });

        btPaket3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,Detail_pembelianActivity.class);
                intent.putExtra("idpaket",id_kelas3);
                startActivity(intent);
            }
        });

        btPaket4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,Detail_pembelianActivity.class);
                intent.putExtra("idpaket",id_kelas4);
                startActivity(intent);
            }
        });

        getKelas();


        LinearLayout btHome = findViewById(R.id.btHome);
        LinearLayout btBelajar = findViewById(R.id.btBelajar);
        LinearLayout btProfil = findViewById(R.id.btProfil);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,BalajarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beli_Activity.this,ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

    public void getKelas() {
        AndroidNetworking.post(Const.BASE_URL + "view_menu_beli")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(Beli_Activity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {


                                JSONArray jsonArray1 = json.getJSONArray("data_paket");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data1 = jsonArray1.getJSONObject(0);
                                    txtKelas1.setText(data1.getString("nama_paket"));
                                    txtKet1.setText(data1.getString("deskripsi"));
                                    id_kelas1 = data1.getString("id_paket");
                                    JSONObject data2 = jsonArray1.getJSONObject(1);
                                    txtKelas2.setText(data2.getString("nama_paket"));
                                    txtKet2.setText(data2.getString("deskripsi"));
                                    id_kelas2 = data2.getString("id_paket");

                                    JSONObject data3 = jsonArray1.getJSONObject(2);
                                    txtKelas3.setText(data3.getString("nama_paket"));
                                    txtKet3.setText(data3.getString("deskripsi"));
                                    id_kelas3 = data3.getString("id_paket");

                                    JSONObject data4 = jsonArray1.getJSONObject(3);
                                    txtKelas4.setText(data4.getString("nama_paket"));
                                    txtKet4.setText(data4.getString("deskripsi"));
                                    id_kelas4 = data4.getString("id_paket");



                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Beli_Activity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(Beli_Activity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
