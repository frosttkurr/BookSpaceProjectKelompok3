package id.kelompok3.bookspace.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import id.kelompok3.bookspace.R;

public class AboutActivity extends AppCompatActivity {
    private TextView about, kelompok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about = (TextView)findViewById(R.id.txt_about);
        kelompok = (TextView)findViewById(R.id.about_kelompok);

        about.setText("BookSpace adalah aplikasi pencatatan peminjaman buku");
        kelompok.setText("Kelompok 3");
    }
}