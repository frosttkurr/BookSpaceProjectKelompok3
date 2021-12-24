package id.kelompok3.bookspace.activity.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.buku.DetailBukuActivity;
import id.kelompok3.bookspace.activity.landing.LoginActivity;
import id.kelompok3.bookspace.activity.pinjam.DetailPinjamActivity;
import id.kelompok3.bookspace.activity.pinjam.ListPinjamActivity;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.database.DetailBukuAPIHelper;
import id.kelompok3.bookspace.database.PenggunaAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.model.PenggunaHandler;
import id.kelompok3.bookspace.model.SessionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private String strNo_telp,strNama, strUsername, strJenisKelamin, strEmail, strAlamat, strMinatBaca;
    private TextView no_telp, nama, username, jenis_kelamin, email, alamat, minat_baca;
    private Button btnHapus;
    private ImageView profile;
    private Integer id;
    private List<PenggunaHandler> penggunaHandler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = (ImageView)findViewById(R.id.icon_profile);
        no_telp = (TextView)findViewById(R.id.profile_noTelp);
        nama = (TextView)findViewById(R.id.profile_nama);
        username = (TextView)findViewById(R.id.profile_username);
        jenis_kelamin = (TextView)findViewById(R.id.profile_jk);
        email = (TextView)findViewById(R.id.profile_email);
        alamat = (TextView)findViewById(R.id.profile_alamat);
        minat_baca = (TextView)findViewById(R.id.profile_minatbaca);
        btnHapus = (Button)findViewById(R.id.btn_hapus_profile);

        id = this.getSharedPreferences("pref_name", 0).getInt("key_id", 0);

        retrieveData();

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(ProfileActivity.this);
                dialogAlertBuilder.setTitle("Konfirmasi");
                dialogAlertBuilder
                        .setMessage("Yakin menghapus profile?")
                        .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteData();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
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

                nama.setText(penggunaHandler.get(0).getNama_lengkap());
                username.setText(penggunaHandler.get(0).getUsername());
                jenis_kelamin.setText(penggunaHandler.get(0).getJenis_kelamin());
                no_telp.setText(penggunaHandler.get(0).getNo_telpon());
                email.setText(penggunaHandler.get(0).getEmail());
                alamat.setText(penggunaHandler.get(0).getAlamat());
                minat_baca.setText(penggunaHandler.get(0).getMinat_membaca());
            }

            @Override
            public void onFailure(Call<List<PenggunaHandler>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Anda offline, hubungkan ulang ke server!", Toast.LENGTH_SHORT).show();
                SessionHandler session = new SessionHandler();
                session.logout(ProfileActivity.this);
                Intent goLogin = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });
    }

    public void deleteData(){
        PenggunaAPIHelper penggunaRequestData = RetroHelper.connectRetrofit().create(PenggunaAPIHelper.class);
        Call<PenggunaHandler> getPengguna = penggunaRequestData.penggunaDeleteData(id);

        getPengguna.enqueue(new Callback<PenggunaHandler>() {
            @Override
            public void onResponse(Call<PenggunaHandler> call, Response<PenggunaHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();

                if (statusAPI) {
                    Toast.makeText(ProfileActivity.this, ""+ message, Toast.LENGTH_LONG).show();
                    SessionHandler session = new SessionHandler();
                    session.logout(ProfileActivity.this);
                    Intent goLogin = new Intent(ProfileActivity .this, LoginActivity.class);
                    startActivity(goLogin);
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Gagal menghapus data pengguna", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<PenggunaHandler> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Gagal menghubungkan ke server : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    protected void onResume(){
        Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
        Log.i("Status", "Profile");
        super.onResume();
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