package com.siupindo.madinku;

public class BelajarSemuaMateri_Model {
    private String id_materi;
    private String judul_materi;
    private String ket_materi;


    public BelajarSemuaMateri_Model(String id_materi, String judul_materi,String ket_materi) {
        this.judul_materi = judul_materi;
        this.id_materi = id_materi;
        this.ket_materi = ket_materi;


    }

    public String getKet_materi() {
        return ket_materi;
    }

    public String getJudul_materi() {
        return judul_materi;
    }

    public String getId_materi() {
        return id_materi;
    }
}
