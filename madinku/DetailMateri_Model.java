package com.siupindo.madinku;

public class DetailMateri_Model {
    private String id_module;
    private String nama_modul;
    private String content;
    private String pembelian;
    private String tonton;


    public DetailMateri_Model(String id_module,String nama_modul, String content, String pembelian, String tonton) {
        this.id_module = id_module;
        this.nama_modul = nama_modul;
        this.content = content;
        this.pembelian = pembelian;
        this.tonton = tonton;


    }

    public String getId_module() {
        return id_module;
    }

    public String getContent() {
        return content;
    }

    public String getNama_modul() {
        return nama_modul;
    }

    public String getPembelian() {
        return pembelian;
    }

    public String getTonton() {
        return tonton;
    }
}
