package id.kelompok3.bookspace.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.buku.BukuBisnisActivity;
import id.kelompok3.bookspace.activity.buku.BukuEdukasiActivity;
import id.kelompok3.bookspace.activity.buku.BukuFiksiActivity;
import id.kelompok3.bookspace.activity.buku.BukuIlmiahActivity;
import id.kelompok3.bookspace.activity.buku.BukuMajalahActivity;
import id.kelompok3.bookspace.activity.buku.BukuNovelActivity;
import id.kelompok3.bookspace.activity.buku.TambahBukuActivity;
import id.kelompok3.bookspace.activity.buku.BukuSejarahActivity;
import id.kelompok3.bookspace.activity.landing.LoginActivity;
import id.kelompok3.bookspace.activity.pinjam.ListPinjamActivity;
import id.kelompok3.bookspace.activity.pinjam.PinjamActivity;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.PenggunaAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.PenggunaHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LobbyActivity extends AppCompatActivity {
    private TextView label_name, label_alamat;
    private String no_telp, nama, username, jenis_kelamin, email, alamat, minat_baca;
    private ImageView profile, edukasi, ilmiah, fiksi, sejarah, bisnis, novel, majalah, data_pinjam, lihat_pinjam, add_category, about, logout;
    private List<PenggunaHandler> penggunaHandler = new ArrayList<>();
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        profile = (ImageView)findViewById(R.id.display_picture);
        label_name = (TextView)findViewById(R.id.display_nama);
        label_alamat = (TextView)findViewById(R.id.display_alamat);
        edukasi = (ImageView)findViewById(R.id.cover_edukasi);
        ilmiah = (ImageView)findViewById(R.id.cover_ilmiah);
        fiksi = (ImageView)findViewById(R.id.cover_fiksi);
        sejarah = (ImageView)findViewById(R.id.cover_sejarah);
        bisnis = (ImageView)findViewById(R.id.cover_bisnis);
        novel = (ImageView)findViewById(R.id.cover_novel);
        majalah = (ImageView)findViewById(R.id.cover_majalah);
        data_pinjam = (ImageView)findViewById(R.id.btn_pinjam);
        lihat_pinjam = (ImageView)findViewById(R.id.btn_list);
        about = (ImageView)findViewById(R.id.btn_about);
        add_category = (ImageView)findViewById(R.id.btn_tambah);
        logout = (ImageView)findViewById(R.id.icon_logout);

        Intent getData = getIntent();
        String strId = getData.getStringExtra("id");
        id = Integer.valueOf(strId);

//        DBHelper dbHelper = new DBHelper(this);
//
//        Cursor cursor = dbHelper.tampilkanPenggunaDariID(id);
//
//        while (cursor.moveToNext()) {
//            nama = cursor.getString(1);
//            alamat = cursor.getString(2);
//        }
//
//        label_name.setText(nama);
//        label_alamat.setText(alamat);

        retrieveData();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(LobbyActivity.this, ProfileActivity.class);
                gotoProfile.putExtra("id", String.valueOf(id));
                startActivity(gotoProfile);
            }
        });

        edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListEdukasi = new Intent(LobbyActivity.this, BukuEdukasiActivity.class);
                startActivity(goListEdukasi);
            }
        });

        ilmiah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListIlmiah = new Intent(LobbyActivity.this, BukuIlmiahActivity.class);
                startActivity(goListIlmiah);
            }
        });

        fiksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListFiksi = new Intent(LobbyActivity.this, BukuFiksiActivity.class);
                startActivity(goListFiksi);
            }
        });

        sejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListSejarah = new Intent(LobbyActivity.this, BukuSejarahActivity.class);
                startActivity(goListSejarah);
            }
        });

        bisnis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListBisnis = new Intent(LobbyActivity.this, BukuBisnisActivity.class);
                startActivity(goListBisnis);
            }
        });

        novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListNovel = new Intent(LobbyActivity.this, BukuNovelActivity.class);
                startActivity(goListNovel);
            }
        });

        majalah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListMajalah = new Intent(LobbyActivity.this, BukuMajalahActivity.class);
                startActivity(goListMajalah);
            }
        });

        data_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAddPinjam = new Intent(LobbyActivity.this, PinjamActivity.class);
                startActivity(goAddPinjam);
            }
        });

        lihat_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goListPinjam = new Intent(LobbyActivity.this, ListPinjamActivity.class);
                startActivity(goListPinjam);
            }
        });

        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAddKategori = new Intent(LobbyActivity.this, TambahBukuActivity.class);
                startActivity(goAddKategori);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAbout = new Intent(LobbyActivity.this, AboutActivity.class);
                startActivity(gotoAbout);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                Intent goLogin = new Intent(LobbyActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });
    }

    public void retrieveData(){
        PenggunaAPIHelper penggunaRequestData = RetroHelper.connectRetrofit().create(PenggunaAPIHelper.class);
        Call<List<PenggunaHandler>> getPengguna = penggunaRequestData.penggunaRetrieveData(id);

        getPengguna.enqueue(new Callback<List<PenggunaHandler>>() {
            @Override
            public void onResponse(Call<List<PenggunaHandler>> call, Response<List<PenggunaHandler>> response) {
                penggunaHandler = response.body();

                label_name.setText(penggunaHandler.get(0).getNama_lengkap());
                label_alamat.setText(penggunaHandler.get(0).getAlamat());
            }

            @Override
            public void onFailure(Call<List<PenggunaHandler>> call, Throwable t) {
                Toast.makeText(LobbyActivity.this, "Gagal mengambil data pengguna : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume(){
        Toast.makeText(getApplicationContext(), "Selamat datang di BookSpace", Toast.LENGTH_SHORT).show();
        Log.i("Status", "Selamat datang di BookSpace");
        super.onResume();
    }

    protected void onPause(){
        Toast.makeText(getApplicationContext(), "Halaman berpindah", Toast.LENGTH_SHORT).show();
        Log.i("State", "Halaman berpindah");
        super.onPause();
    }

    protected void onStop(){
        Toast.makeText(getApplicationContext(), "Halaman berpindah", Toast.LENGTH_SHORT).show();
        Log.i("State", "Halaman berpindah");
        super.onStop();
    }

    protected void onDestroy(){
        Toast.makeText(getApplicationContext(), "Aplikasi ditutup", Toast.LENGTH_SHORT).show();
        Log.i("State", "Aplikasi ditutup");
        super.onDestroy();
    }
}