package id.syakurr.bookspace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private TextView about, nim, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about = (TextView)findViewById(R.id.txt_about);
        nim = (TextView)findViewById(R.id.about_nim);
        nama = (TextView)findViewById(R.id.about_nama);

        about.setText("BookSpace adalah aplikasi pencatatan peminjaman buku");
        nim.setText("1905551077");
        nama.setText("Muhammad Syakurrahman");
    }
}