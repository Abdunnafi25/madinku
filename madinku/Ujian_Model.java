package com.siupindo.madinku;

public class Ujian_Model {
    private String nomor;
    private String id_soal;
    private String jawaban;




    public Ujian_Model(String nomor, String id_soal,String jawaban) {
        this.nomor = nomor;
        this.id_soal = id_soal;
        this.jawaban = jawaban;


    }

    public String getJawaban() {
        return jawaban;
    }

    public String getId_soal() {
        return id_soal;
    }

    public String getNomor() {
        return nomor;
    }
}
