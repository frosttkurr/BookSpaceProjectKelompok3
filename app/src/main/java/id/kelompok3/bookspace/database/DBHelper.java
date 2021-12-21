package id.kelompok3.bookspace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;

import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.model.PenggunaHandler;
import id.kelompok3.bookspace.model.PinjamHandler;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper (Context context) {
        super(context, "bookspace.db", null, 1);
//        context.deleteDatabase("bookspace.db"); // hapus database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_buku(id INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, kategori TEXT)");
        db.execSQL("CREATE TABLE tb_pinjam(id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, judul TEXT, alamat TEXT, no_telpon TEXT, tgl_pinjam TEXT, tgl_kembali TEXT, status TEXT)");
        db.execSQL("CREATE TABLE tb_pengguna(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_lengkap TEXT, alamat TEXT, jenis_kelamin TEXT, no_telpon TEXT, email TEXT, username TEXT, password TEXT, minat_membaca TEXT, deleted_at TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_buku");
        db.execSQL("DROP TABLE IF EXISTS tb_pinjam");
        db.execSQL("DROP TABLE IF EXISTS tb_pengguna");
    }

    public boolean tambahPengguna(PenggunaHandler penggunaHandler){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_lengkap", penggunaHandler.getNama_lengkap());
        values.put("alamat", penggunaHandler.getAlamat());
        values.put("jenis_kelamin", penggunaHandler.getJenis_kelamin());
        values.put("no_telpon", penggunaHandler.getNo_telpon());
        values.put("email", penggunaHandler.getEmail());
        values.put("username", penggunaHandler.getUsername());
        values.put("password", penggunaHandler.getPassword());
        values.put("minat_membaca", penggunaHandler.getMinat_membaca());
        return db.insert("tb_pengguna", null, values) > 0;
    }

    public boolean cekUsername(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_pengguna WHERE deleted_at is null AND username = ?", new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean cekEmail(String email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_pengguna WHERE deleted_at is null AND email = ?", new String[]{email});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public int cekUsernameDanPassword(String username, String password){
        int id = -1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_pengguna WHERE username = ? AND password = ?", new String[]{username, password});
        while (cursor.moveToNext()) {
            id = Integer.valueOf(cursor.getString(0));
        }
        return id;
    }

    public Cursor tampilkanPenggunaDariID(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_pengguna WHERE id = ?", new String[]{id});
        return cursor;
    }

    public boolean hapusPengguna (String id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("deleted_at", DateFormat.getDateInstance().format(new Date()));
        return db.update("tb_pengguna", values, "id" + "=" + id, null) > 0;
    }

    public boolean tambahBuku(BukuHandler bukuHandler) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("judul", bukuHandler.getJudul());
        values.put("kategori", bukuHandler.getKategori());
        return db.insert("tb_buku", null, values) > 0;
    }

    public Cursor tampilkanBukuEdukasi() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'EDUKASI'", null);
    }

    public Cursor tampilkanBukuIlmiah() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'ILMIAH'", null);
    }

    public Cursor tampilkanBukuFiksi() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'FIKSI'", null);
    }

    public Cursor tampilkanBukuSejarah() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'SEJARAH'", null);
    }

    public Cursor tampilkanBukuBisnis() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'BISNIS'", null);
    }

    public Cursor tampilkanBukuNovel() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'NOVEL'", null);
    }

    public Cursor tampilkanBukuMajalah() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_buku where kategori = 'MAJALAH'", null);
    }

    public boolean tambahPinjam(PinjamHandler pinjamHandler) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", pinjamHandler.getNama());
        values.put("judul", pinjamHandler.getJudul());
        values.put("alamat", pinjamHandler.getAlamat());
        values.put("no_telpon", pinjamHandler.getNo_telp());
        values.put("tgl_pinjam", pinjamHandler.getTgl_pinjam());
        values.put("tgl_kembali", pinjamHandler.getTgl_kembali());
        values.put("status", pinjamHandler.getStatus());
        return db.insert("tb_pinjam", null, values) > 0;
    }

    public Cursor tampilkanPinjam() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_pinjam order by tgl_pinjam", null);
    }

    public Cursor detailPinjam(int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from tb_pinjam where id = " + id_pinjam, null);
    }

    public boolean suntingPinjam(PinjamHandler pinjamHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tgl_kembali", pinjamHandler.getTgl_kembali());
        return db.update("tb_pinjam", values, "id" + "=" + id_pinjam, null) > 0;
    }

    public boolean hapusPinjam (int id_pinjam) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete("tb_pinjam", "id" + "=" + id_pinjam, null) > 0;
    }

    public boolean kembaliPinjam(PinjamHandler pinjamHandler, int id_pinjam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", pinjamHandler.getStatus());
        return db.update("tb_pinjam", values, "id" + "=" + id_pinjam, null) > 0;
    }
}
