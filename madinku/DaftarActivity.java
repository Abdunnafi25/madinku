package com.siupindo.madinku;

import android.os.Bundle;
import android.view.View;
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

public class DaftarActivity extends AppCompatActivity {

    TextView btDaftar,txtNama,txtEmail,txtPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btDaftar = findViewById(R.id.btDaftar);

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJawaban();
            }
        });
    }
    public void postJawaban() {
        AndroidNetworking.post(Const.BASE_URL + "tambah_data_pengguna")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(DaftarActivity.this))
                .addBodyParameter("nama_pengguna",txtNama.getText().toString())
                .addBodyParameter("email_data", txtEmail.getText().toString())
                .addBodyParameter("password_data", txtPass.getText().toString())
                .addBodyParameter("ulangi_password_data", txtPass.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                Toast.makeText(DaftarActivity.this, json.getString("pesan"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DaftarActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DaftarActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
