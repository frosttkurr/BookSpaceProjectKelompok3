package id.kelompok3.bookspace.activity.buku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.kelompok3.bookspace.activity.pinjam.PinjamActivity;
import id.kelompok3.bookspace.database.PinjamAPIHelper;
import id.kelompok3.bookspace.database.RetroHelper;
import id.kelompok3.bookspace.database.TambahBukuAPIHelper;
import id.kelompok3.bookspace.model.BukuHandler;
import id.kelompok3.bookspace.database.DBHelper;
import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.model.PinjamHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                .setMessage("Judul      : " +judul_buku+ "\n" +
                        "Kategori : " +kategori_buku.toUpperCase()+ "\n")
                .setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DBHelper dbHelper = new DBHelper(getApplicationContext());
//                        BukuHandler bukuHandler = new BukuHandler();
//                        bukuHandler.setJudul(judul_buku.toUpperCase());
//                        bukuHandler.setKategori(kategori_buku.toUpperCase());
//
//                        boolean tambahBuku = dbHelper.tambahBuku(bukuHandler);
//
//                        if (tambahBuku) {
//                            Toast.makeText(TambahBukuActivity.this, "Tambah Buku Berhasil", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(TambahBukuActivity.this, "Tambah Buku Gagal", Toast.LENGTH_SHORT).show();
//                        }
//                        dbHelper.close();

                        createData();

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

    public void createData(){
        TambahBukuAPIHelper bukuCreateData = RetroHelper.connectRetrofit().create(TambahBukuAPIHelper.class);
        Call<BukuHandler> addBuku = bukuCreateData.bukuInsertData(judul_buku, kategori_buku.toUpperCase());

        addBuku.enqueue(new Callback<BukuHandler>() {
            @Override
            public void onResponse(Call<BukuHandler> call, Response<BukuHandler> response) {
                Boolean statusAPI = response.body().getStatusAPI();
                String message = response.body().getMessage();
                if (statusAPI != null) {
                    Toast.makeText(TambahBukuActivity.this, ""+ message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TambahBukuActivity.this, "Gagal menambah data buku", Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<BukuHandler> call, Throwable t) {
                Toast.makeText(TambahBukuActivity.this, "Gagal menambah data buku : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}