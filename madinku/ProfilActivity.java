package com.siupindo.madinku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfilActivity extends AppCompatActivity {
    TextView txtNama, txtEmail,btLogout;

    ImageView btEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        btLogout = findViewById(R.id.btLogout);
        bacaPreferensi();

        getDashboard();
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_pengguna", "0");
                editor.apply();
                startActivity(new Intent(ProfilActivity.this,MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                finish();
            }
        });

        LinearLayout btHome = findViewById(R.id.btHome);
        LinearLayout btBelajar = findViewById(R.id.btBelajar);
        LinearLayout btBeli = findViewById(R.id.btBeli);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this,BalajarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this,Beli_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btEdit = findViewById(R.id.btEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this,EditProfilActivity.class);
                intent.putExtra("nama",txtNama.getText().toString());
                intent.putExtra("email",txtEmail.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    public void getDashboard() {
        AndroidNetworking.post(Const.BASE_URL + "info_profil")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(ProfilActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                txtNama.setText(json.getString("nama_pengguna"));
                                txtEmail.setText(json.getString("email_pengguna"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ProfilActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(ProfilActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    Boolean doubleBackToExitPressedOnce = false;

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
}
