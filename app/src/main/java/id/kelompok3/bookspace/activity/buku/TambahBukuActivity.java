package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.R;

public class TambahBukuActivity extends AppCompatActivity {
    private String judul_buku, kategori_buku;
    private EditText judul, kategori;
    private Button btnTambahBuku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_buku);

        judul = (EditText)findViewById(R.id.input_buku);
        kategori = (EditText)findViewById(R.id.input_kategori);
        btnTambahBuku = (Button)findViewById(R.id.btn_tambah_buku);

        btnTambahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul_buku = judul.getText().toString();
                kategori_buku = kategori.getText().toString();

                dialogAlert();
            }
        });
    }

    private void dialogAlert(){
        AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(TambahBukuActivity.this);
        dialogAlertBuilder.setTitle("Konfirmasi Data");
        dialogAlertBuilder
                .setMessage("Judul : " +judul_buku+ "\n" +
                        "Kategori : " +kategori_buku+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        BukuHandler bukuHandler = new BukuHandler();
                        bukuHandler.setJudul(judul_buku.toUpperCase());
                        bukuHandler.setKategori(kategori_buku.toUpperCase());

                        boolean tambahBuku = dbHelper.tambahBuku(bukuHandler);

                        if (tambahBuku) {
                            Toast.makeText(TambahBukuActivity.this, "Tambah Buku Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TambahBukuActivity.this, "Tambah Buku Gagal", Toast.LENGTH_SHORT).show();
                        }
                        dbHelper.close();

                        judul.getText().clear();
                        kategori.getText().clear();
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
}