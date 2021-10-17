package com.siupindo.madinku;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import im.delight.android.webview.AdvancedWebView;

public class VideoMateriActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    private AdvancedWebView mWebView;
    TextView txtDes, txtJudul, tbSudahNonton;
    String id_module;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.belajar_materi_yt);
bacaPreferensi();
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        txtDes = findViewById(R.id.txtDes);
        txtJudul = findViewById(R.id.txtJudul);
        tbSudahNonton = findViewById(R.id.tbSudahNonton);

        mWebView.setListener(this, this);
        mWebView.setMixedContentAllowed(false);
        getDashboard();
        tbSudahNonton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSudahNonton();
            }
        });
        txtJudul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VideoMateriActivity.this,DetailMateriActivity.class);
                intent.putExtra("id_materi",getIntent().getStringExtra("id_materi"));
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(VideoMateriActivity.this,DetailMateriActivity.class);
        intent.putExtra("id_materi",getIntent().getStringExtra("id_materi"));
        startActivity(intent);
        finish();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    public void getDashboard() {
        AndroidNetworking.post(Const.BASE_URL + "view_detail_module")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(VideoMateriActivity.this))
                .addBodyParameter("id_module", getIntent().getStringExtra("id_module"))
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {


                                mWebView.loadUrl(json.getString("module_content"));
                                txtDes.setText(json.getString("module_deskripsi"));
                                txtJudul.setText(json.getString("nama_module"));
                                id_module = json.getString("id_module");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(VideoMateriActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(VideoMateriActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
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

    public void postSudahNonton() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Mohon Tunggu ...");
        showDialog();
        AndroidNetworking.post(Const.BASE_URL + "simpan_menonton")
                .addBodyParameter("api-madinku", PrefUtils.getCertificate(VideoMateriActivity.this))
                .addBodyParameter("id_module", id_module)
                .addBodyParameter("id_pengguna", id_pengguna)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject json) {
                        {
                            try {
                                hideDialog();

                                Intent intent= new Intent(VideoMateriActivity.this,DetailMateriActivity.class);
                                intent.putExtra("id_materi",getIntent().getStringExtra("id_materi"));
                                startActivity(intent);
                                finish();
                                Toast.makeText(VideoMateriActivity.this, json.getString("pesan"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                hideDialog();
                                e.printStackTrace();
                                Toast.makeText(VideoMateriActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        hideDialog();
                        Toast.makeText(VideoMateriActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
