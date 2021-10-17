package com.siupindo.madinku;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    String success;

    EditText edUsername, edPass;

    TextView btDaftar,btMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        bacaPreferensi();
        edUsername = findViewById(R.id.edUsername);
        edPass = findViewById(R.id.edPass);
        TextView btShare =findViewById(R.id.btshare);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent txtIntent = new Intent(Intent.ACTION_SEND);
                txtIntent .setType("text/plain");
                txtIntent .putExtra(Intent.EXTRA_TEXT, PrefUtils.getCertificate(MainActivity.this));
                startActivity(Intent.createChooser(txtIntent ,"Share"));
            }
        });
        if (!id_pengguna.equalsIgnoreCase("0")){
            startActivity(new Intent(MainActivity.this, DashboardActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

            finish();
        }
        btDaftar = findViewById(R.id.btDaftar);
        btDaftar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DaftarActivity.class));
            }
        });
        btMasuk = findViewById(R.id.btMasuk);
        btMasuk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                requestLogin(edUsername, edPass);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestLogin(final EditText username, final EditText pass) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mohon Tunggu ...");
        showDialog();
        AndroidNetworking.post(Const.BASE_URL + "login_system")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(MainActivity.this))
                .addBodyParameter("email_data", username.getText().toString())
                .addBodyParameter("password_data", pass.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        hideDialog();
                        try {
                            success = json.getString("sukses");
                            if (success.equalsIgnoreCase("ya")) {

                                Toast.makeText(MainActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("id_pengguna", json.getString("id_pengguna"));
                                editor.apply();
                                startActivity(new Intent(MainActivity.this, DashboardActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                finish();
                            } else {
                                hideDialog();
                                Toast.makeText(MainActivity.this, "UserName atau Password salah", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideDialog();
                            Toast.makeText(MainActivity.this, "GAGAL KONESI", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        hideDialog();
                        Toast.makeText(MainActivity.this, String.valueOf(anError), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}