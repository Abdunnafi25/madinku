package com.siupindo.madinku;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

public class UjianActivity extends AppCompatActivity {
    RecyclerView list;
    Ujian_Adapter adapter;
    ArrayList<Ujian_Model> arraylist = new ArrayList<>();

    public TextView txtJumsoal, txtJudul, txtPertanyaan, txtJawaban1, txtJawaban2, txtJawaban3, txtJawaban4;

    public String jumSoal, jumJawab;
    public String jawaban, jawaban_anda;
    public String nomor, nomor_baru, nilai;
    TextView btViewNilai;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.belajar_materi);
        bacaPreferensi();
        list = findViewById(R.id.recycler_view);
        txtJumsoal = findViewById(R.id.txtJumsoal);
        txtJudul = findViewById(R.id.txtJudul);
        txtPertanyaan = findViewById(R.id.txtPertanyaan);
        txtJawaban1 = findViewById(R.id.txtJawaban1);
        txtJawaban2 = findViewById(R.id.txtJawaban2);
        txtJawaban3 = findViewById(R.id.txtJawaban3);
        txtJawaban4 = findViewById(R.id.txtJawaban4);
        btViewNilai = findViewById(R.id.btViewNilai);
        btViewNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UjianActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.dialog_tes);
                TextView btLanjutkan = dialog.findViewById(R.id.btLanjutkan);
                TextView txtNilai = dialog.findViewById(R.id.txtNilai);
                TextView txtKet = dialog.findViewById(R.id.txtKet);

                txtNilai.setText(nilai);
                txtKet.setText("Anda telah menyelesaikan tes pada materi, " + txtJudul.getText().toString());
                btLanjutkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        adapter = new Ujian_Adapter(arraylist, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(mLayoutManager);
        getNomor();

        txtJawaban1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jawaban_anda.equalsIgnoreCase("0")) {
                    postJawaban("pilihan_1");
                }
            }
        });
        txtJawaban2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jawaban_anda.equalsIgnoreCase("0")) {
                    postJawaban("pilihan_2");
                }
            }
        });
        txtJawaban3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jawaban_anda.equalsIgnoreCase("0")) {
                    postJawaban("pilihan_3");
                }
            }
        });
        txtJawaban4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jawaban_anda.equalsIgnoreCase("0")) {
                    postJawaban("pilihan_4");
                }
            }
        });
    }

    String id_pengguna;

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        id_pengguna = pref.getString("id_pengguna", "0");
    }

    public void getNomor_baru(String id_soal) {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_data_soal")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(UjianActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("id_materi", getIntent().getStringExtra("id_materi"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                JSONArray jsonArray1 = json.getJSONArray("list_soal");
                                txtJumsoal.setText(json.getString("jumlah_jawaban") + " / " + json.getString("jumlah_soal"));
                                txtJudul.setText(json.getString("kategori") + " : " + json.getString("nama_materi"));
                                jumJawab = json.getString("jumlah_jawaban");
                                jumSoal = json.getString("jumlah_soal");
                                nilai = json.getString("hasil");
                                // nomor = json.getString("tampilkan_idsoal");
                                arraylist.clear();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    Ujian_Model wp = new Ujian_Model(data.getString("nomor"), data.getString("id_soal"), data.getString("jawaban"));
                                    arraylist.add(wp);
                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);
                                getSoal(id_soal);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UjianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(UjianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getNomor() {
        //sh_menu_loading.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        AndroidNetworking.post(Const.BASE_URL + "view_data_soal")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(UjianActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("id_materi", getIntent().getStringExtra("id_materi"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                JSONArray jsonArray1 = json.getJSONArray("list_soal");
                                txtJumsoal.setText(json.getString("jumlah_jawaban") + " / " + json.getString("jumlah_soal"));
                                txtJudul.setText(json.getString("kategori") + " : " + json.getString("nama_materi"));
                                jumJawab = json.getString("jumlah_jawaban");
                                jumSoal = json.getString("jumlah_soal");
                                nomor = json.getString("tampilkan_idsoal");
                                nilai = json.getString("hasil");
                                nomor_baru = nomor;
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject data = jsonArray1.getJSONObject(i);
                                    Ujian_Model wp = new Ujian_Model(data.getString("nomor"), data.getString("id_soal"), data.getString("jawaban"));
                                    arraylist.add(wp);
                                }
                                list.setAdapter(adapter);
                                list.setVisibility(View.VISIBLE);
                                getSoal(nomor);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UjianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(UjianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void postJawaban(String jawaban) {
        AndroidNetworking.post(Const.BASE_URL + "simpan_jawaban")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(UjianActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("id_materi", getIntent().getStringExtra("id_materi"))
                .addBodyParameter("id_soal", nomor_baru)
                .addBodyParameter("jawaban", jawaban)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                Toast.makeText(UjianActivity.this, json.getString("pesan"), Toast.LENGTH_SHORT).show();
                                getNomor_baru(nomor_baru);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UjianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(UjianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getSoal(String id_soal) {
        AndroidNetworking.post(Const.BASE_URL + "cari_data_soal")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(UjianActivity.this))
                .addBodyParameter("id_pengguna", id_pengguna)
                .addBodyParameter("id_soal", id_soal)
                .addBodyParameter("id_materi", getIntent().getStringExtra("id_materi"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {

                                txtPertanyaan.setText(json.getString("pertanyaan"));
                                txtJawaban1.setText(json.getString("pilihan_1"));
                                txtJawaban2.setText(json.getString("pilihan_2"));
                                txtJawaban3.setText(json.getString("pilihan_3"));
                                txtJawaban4.setText(json.getString("pilihan_4"));
                                jawaban = json.getString("jawaban");
                                jawaban_anda = json.getString("jawaban_anda");


                                if (jawaban_anda.equalsIgnoreCase("pilihan_1")) {
                                    txtJawaban1.setBackgroundResource(R.drawable.shape_bt_login1);
                                    txtJawaban1.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    txtJawaban1.setBackgroundResource(R.drawable.shape_edittext_login);
                                    txtJawaban1.setTextColor(getResources().getColor(R.color.color1));
                                }
                                if (jawaban_anda.equalsIgnoreCase("pilihan_2")) {
                                    txtJawaban2.setBackgroundResource(R.drawable.shape_bt_login1);
                                    txtJawaban2.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    txtJawaban2.setBackgroundResource(R.drawable.shape_edittext_login);
                                    txtJawaban2.setTextColor(getResources().getColor(R.color.color1));
                                }
                                if (jawaban_anda.equalsIgnoreCase("pilihan_3")) {
                                    txtJawaban3.setBackgroundResource(R.drawable.shape_bt_login1);
                                    txtJawaban3.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    txtJawaban3.setBackgroundResource(R.drawable.shape_edittext_login);
                                    txtJawaban3.setTextColor(getResources().getColor(R.color.color1));
                                }
                                if (jawaban_anda.equalsIgnoreCase("pilihan_4")) {
                                    txtJawaban4.setBackgroundResource(R.drawable.shape_bt_login1);
                                    txtJawaban4.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    txtJawaban4.setBackgroundResource(R.drawable.shape_edittext_login);
                                    txtJawaban4.setTextColor(getResources().getColor(R.color.color1));
                                }


//                                if (arrayJenis.get(position).getJawaban().equalsIgnoreCase("pilihan_1") && arrayJenis.get(position).getNomor().equalsIgnoreCase("1")) {
//                                    ((UjianActivity) mContext).txtJawaban1.setBackgroundResource(R.drawable.shape_bt_login1);
//                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(UjianActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(UjianActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
