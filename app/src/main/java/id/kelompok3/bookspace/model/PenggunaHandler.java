package id.kelompok3.bookspace.model;

import java.util.ArrayList;
import java.util.List;

public class PenggunaHandler {
    private Integer id;
    private String nama_lengkap, alamat, jenis_kelamin, no_telpon, email, username, password, minat_membaca;
    private Boolean statusAPI;
    private String message;
    private List<PenggunaHandler> data = new ArrayList<>();
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public void setNo_telpon(String no_telpon) {
        this.no_telpon = no_telpon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMinat_membaca() {
        return minat_membaca;
    }

    public void setMinat_membaca(String minat_membaca) {
        this.minat_membaca = minat_membaca;
    }

    public Boolean getStatusAPI() {
        return statusAPI;
    }

    public void setStatusAPI(Boolean statusAPI) {
        this.statusAPI = statusAPI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PenggunaHandler> getPenggunaHandler() {
        return data;
    }

    public void setPenggunaHandler(List<PenggunaHandler> penggunaHandler) {
        this.data = penggunaHandler;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
