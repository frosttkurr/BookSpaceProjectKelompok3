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

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.landing.LoginActivity;
import id.kelompok3.bookspace.activity.pinjam.DetailPinjamActivity;
import id.kelompok3.bookspace.activity.pinjam.ListPinjamActivity;
import id.kelompok3.bookspace.database.DBHelper;

public class ProfileActivity extends AppCompatActivity {
    private String strNo_telp,strNama, strUsername, strJenisKelamin, strEmail, strAlamat, strMinatBaca;
    private TextView no_telp, nama, username, jenis_kelamin, email, alamat, minat_baca;
    private Button btnHapus;
    private ImageView profile;

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

        Intent getData = getIntent();
        String strId = getData.getStringExtra("id");

        DBHelper dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.tampilkanPenggunaDariID(strId);

        while (cursor.moveToNext()) {
            strNama = cursor.getString(1);
            strUsername = cursor.getString(6);
            strJenisKelamin = cursor.getString(3);
            strNo_telp = cursor.getString(4);
            strEmail = cursor.getString(5);
            strAlamat = cursor.getString(2);
            strMinatBaca = cursor.getString(8);
        }

        nama.setText(strNama);
        username.setText(strUsername);
        jenis_kelamin.setText(strJenisKelamin);
        no_telp.setText(strNo_telp);
        email.setText(strEmail);
        alamat.setText(strAlamat);
        minat_baca.setText(strMinatBaca);

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
                                DBHelper dbHelper = new DBHelper(getApplicationContext());

                                boolean hapusPengguna = dbHelper.hapusPengguna(strId);

                                if (hapusPengguna) {
                                    Toast.makeText(ProfileActivity.this, "Hapus Profile Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent goLogin = new Intent(ProfileActivity.this, LoginActivity.class);
                                    startActivity(goLogin);
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Hapus Profile Gagal", Toast.LENGTH_SHORT).show();
                                }
                                dbHelper.close();
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