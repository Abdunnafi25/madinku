package com.siupindo.madinku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

public class Detail_pembelianActivity extends AppCompatActivity {
    RecyclerView list;
    detail_pembelian_Adapter adapter;
    ArrayList<detail_pembelian_Model> arraylist = new ArrayList<>();
    TextView btBeli;
    TextView txtHarga, txtTotalHarga;
    String harga;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pembelian);
        bacaPreferensi();
        list = findViewById(R.id.recycler_view);
        adapter = new detail_pembelian_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLayoutManager);
        getDashboard();

        btBeli = findViewById(R.id.btBeli);
        txtTotalHarga = findViewById(R.id.txtTotalHarga);
        txtHarga = findViewById(R.id.txtHarga);

        btBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Detail_pembelianActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.detail_pembelian_dialog);
//
                TextView btLanjutkan = dialog.findViewById(R.id.btLanjutkan);
                TextView txtHarga = dialog.findViewById(R.id.txtHarga);

                txtHarga.setText("Untuk Melanjutkan Pembelian Paket, Anda Harus Membayar Sebesar " + harga);
//                text.setText("Terima kasih Donasi anda sudah terkirim !\nHarap tetap menghidupkan handphone Anda!!!\nKami akan segera menghubungi anda.");
//                ImageView img1 = dialog.findViewById(R.id.a);
//                img1.setImageResource(R.drawable.ic_logo_golib);
//
//                Button dialogButton = dialog.findViewById(R.id.btn_dialog);
//                Button dialogButton1 = dialog.findViewById(R.id.btn_dialog1);
//                dialogButton1.setVisibility(View.GONE);
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        startActivity(new Intent(Upload_Donasi_Buku.this, Histori_Activity.class));
//                        finish();
//
//                    }
//                });
                btLanjutkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        postKirim();
                    }
                });

                dialog.show();
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
        //list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_detail_paket")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(Detail_pembelianActivity.this))
                .addBodyParameter("id_paket", getIntent().getStringExtra("idpaket"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {
                                if (json.getString("info_paket").equalsIgnoreCase("sukses")) {
                                    txtHarga.setText(json.getString("harga"));
                                    txtTotalHarga.setText(json.getString("harga"));

                                    harga = json.getString("harga");

                                    JSONArray jsonArray1 = json.getJSONArray("data_paket");
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        JSONObject data = jsonArray1.getJSONObject(i);
                                        detail_pembelian_Model wp = new detail_pembelian_Model(data.getString("nama_kategori"), data.getString("listmateri"));
                                        arraylist.add(wp);
                                    }
                                    list.setAdapter(adapter);
                                    list.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Detail_pembelianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(Detail_pembelianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public void postKirim() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mohon Tunggu ...");
        showDialog();
        AndroidNetworking.post(Const.BASE_URL + "simpan_transaksi_pemesanan")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(Detail_pembelianActivity.this))
                .addBodyParameter("id_paket", getIntent().getStringExtra("idpaket"))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("nominal", harga)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {
                                hideDialog();
                                Toast.makeText(Detail_pembelianActivity.this, json.getString("pesan"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Detail_pembelianActivity.this,DetailPembelianTransferActivity.class);
                                intent.putExtra("idpaket",getIntent().getStringExtra("idpaket"));
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                hideDialog();
                                e.printStackTrace();
                                Toast.makeText(Detail_pembelianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        hideDialog();
                        Toast.makeText(Detail_pembelianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
