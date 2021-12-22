package id.kelompok3.bookspace.model;

public class BukuHandler {
    private int id;
    private String judul, kategori;
    private Boolean statusAPI;
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

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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
}
