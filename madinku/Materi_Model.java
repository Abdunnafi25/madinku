package com.siupindo.madinku;

public class Materi_Model {
    private String id_materi;
    private String judul_materi;




    public Materi_Model(String id_materi,String judul_materi) {
        this.judul_materi = judul_materi;
        this.id_materi = id_materi;


    }

    public String getJudul_materi() {
        return judul_materi;
    }

    public String getId_materi() {
        return id_materi;
    }
}
