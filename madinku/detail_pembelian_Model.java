package com.siupindo.madinku;

public class detail_pembelian_Model {
    private String nama_kategori;
    private String listmateri;




    public detail_pembelian_Model(String nama_kategori, String listmateri) {
        this.nama_kategori = nama_kategori;
        this.listmateri = listmateri;


    }

    public String getListmateri() {
        return listmateri;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }
}
