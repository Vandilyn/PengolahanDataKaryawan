package com.example.pengolahandatakaryawan.Data;

public class Karyawan {
    private String id, nik,nama,departemen,divisi,jenisKelamin;

    public Karyawan(String nik, String nama, String departemen, String divisi, String jenisKelamin) {
        this.nik = nik;
        this.nama = nama;
        this.departemen = departemen;
        this.divisi = divisi;
        this.jenisKelamin = jenisKelamin;
    }

    public Karyawan(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
