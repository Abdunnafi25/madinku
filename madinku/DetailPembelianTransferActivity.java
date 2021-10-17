package com.siupindo.madinku;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class DetailPembelianTransferActivity extends AppCompatActivity {
    Spinner spBank;

    Bank_Adapter adapter;
    TextView txtnamaPaket,txtHarga;
    ArrayList<Bank_Model> arraylist = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pembelian_transfer);
        spBank = findViewById(R.id.SpBank);
        txtHarga = findViewById(R.id.txtHarga);
        txtnamaPaket = findViewById(R.id.txtnamaPaket);
        bacaPreferensi();
        getLog();
    }
    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }
    public void getLog() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Const.BASE_URL + "view_informasi_pemesanan")
                .setPriority(Priority.HIGH)
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(DetailPembelianTransferActivity.this))
                .addBodyParameter("id_paket", getIntent().getStringExtra("idpaket"))
                .addBodyParameter("id_pengguna", id_pengguna)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        {
                            try {
                                txtnamaPaket.setText(response.getString("nama_paket"));
                                txtHarga.setText(response.getString("bayar"));
                                JSONArray jsonArray1 = response.getJSONArray("data_rekening");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    Bank_Model wp = new Bank_Model(data.getString("nama_bank"), data.getString("nomor_rekening"),data.getString("nama"));
                                    arraylist.add(wp);
                                }
                                adapter = new Bank_Adapter(DetailPembelianTransferActivity.this, arraylist);
                                spBank.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DetailPembelianTransferActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(DetailPembelianTransferActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
