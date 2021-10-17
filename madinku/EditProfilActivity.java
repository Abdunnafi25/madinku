package com.siupindo.madinku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class EditProfilActivity extends AppCompatActivity {
    EditText edNama, edEmail;
    TextView btLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_edit);
        edNama = findViewById(R.id.edNama);
        edEmail = findViewById(R.id.edEmail);
        btLogout = findViewById(R.id.btLogout);
        bacaPreferensi();
        edNama.setText(getIntent().getStringExtra("nama"));
        edEmail.setText(getIntent().getStringExtra("email"));

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getDashboard();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfilActivity.this, ProfilActivity.class));

        finish();
    }

    public void getDashboard() {
        AndroidNetworking.post(Const.BASE_URL + "update_profil")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(EditProfilActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("email_pengguna", edEmail.getText().toString())
                .addBodyParameter("nama_pengguna", edNama.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                Toast.makeText(EditProfilActivity.this, json.getString("pesan"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditProfilActivity.this, ProfilActivity.class));

                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(EditProfilActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(EditProfilActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }
}
