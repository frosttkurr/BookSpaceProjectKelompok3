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
        kelompok.setText("Kelompok 3:\n1905551009 - I Gusti Ayu Mirah\n1905551031 - Desak Made Ari Puspa Yanti\n1905551038 - Luh Putu Murniasih Pertiwi\n1905551077 - Muhammad Syakurrahman\n1905554117 - I Kadek Arya Dilaga\n1905551130 - Arsyi Reyhan Ramadhan");
    }
}