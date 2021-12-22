package id.kelompok3.bookspace.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinjamHandler {
    private int id;
    private String judul, no_telp, nama, jenis_kelamin, alamat, tgl_pinjam, tgl_kembali, minat_baca, syarat_pinjam, status, created_at, updated_at;
    private boolean statusAPI;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getMinat_baca() {
        return minat_baca;
    }

    public void setMinat_baca(String minat_baca) {
        this.minat_baca = minat_baca;
    }

    public String getSyarat_pinjam() {
        return syarat_pinjam;
    }

    public void setSyarat_pinjam(String syarat_pinjam) {
        this.syarat_pinjam = syarat_pinjam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isStatusAPI() {
        return statusAPI;
    }

    public void setStatusAPI(boolean statusAPI) {
        this.statusAPI = statusAPI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
