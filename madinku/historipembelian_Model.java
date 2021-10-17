package com.siupindo.madinku;

public class historipembelian_Model {
    private String tanggal_pesan;
    private String status;
    private String nominal;
    private String nama_paket;


    public historipembelian_Model(String tanggal_pesan, String status, String nominal, String nama_paket) {
        this.tanggal_pesan = tanggal_pesan;
        this.status = status;
        this.nominal = nominal;
        this.nama_paket = nama_paket;


    }

    public String getNama_paket() {
        return nama_paket;
    }

    public String getNominal() {
        return nominal;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal_pesan() {
        return tanggal_pesan;
    }
}
