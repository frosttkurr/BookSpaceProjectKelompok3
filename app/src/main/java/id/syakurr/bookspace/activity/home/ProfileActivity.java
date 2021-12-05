package id.syakurr.bookspace.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.syakurr.bookspace.R;

public class ProfileActivity extends AppCompatActivity {
    private String strNik,strNama, strUsername, strJenisKelamin, strEmail, strAlamat, strMinatBaca;
    private TextView nik, nama, username, jenis_kelamin, email, alamat, minat_baca;
    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = (ImageView)findViewById(R.id.icon_profile);
        nik = (TextView)findViewById(R.id.profile_nik);
        nama = (TextView)findViewById(R.id.profile_nama);
        username = (TextView)findViewById(R.id.profile_username);
        jenis_kelamin = (TextView)findViewById(R.id.profile_jk);
        email = (TextView)findViewById(R.id.profile_email);
        alamat = (TextView)findViewById(R.id.profile_alamat);
        minat_baca = (TextView)findViewById(R.id.profile_minatbaca);


        Intent getData = getIntent();
        strNik = getData.getStringExtra("nik");
        strNama = getData.getStringExtra("nama");
        strUsername = getData.getStringExtra("username");
        strJenisKelamin = getData.getStringExtra("jeniskelamin");
        strEmail = getData.getStringExtra("email");
        strAlamat = getData.getStringExtra("alamat");
        strMinatBaca = getData.getStringExtra("minatbaca");

        nik.setText(strNik);
        nama.setText(strNama);
        username.setText(strUsername);
        jenis_kelamin.setText(strJenisKelamin);
        email.setText(strEmail);
        alamat.setText(strAlamat);
        minat_baca.setText(strMinatBaca);
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