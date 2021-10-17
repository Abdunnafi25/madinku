package com.siupindo.madinku;

public class Bank_Model {
    private String nama_bank;
    private String nomor_rekening;
    private String nama;


    public Bank_Model(String nama_bank, String nomor_rekening,String nama) {
        this.nama_bank = nama_bank;
        this.nomor_rekening = nomor_rekening;
        this.nama = nama;


    }

    public String getNama() {
        return nama;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public String getNomor_rekening() {
        return nomor_rekening;
    }
}
